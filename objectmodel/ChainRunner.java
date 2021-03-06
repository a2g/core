/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.objectmodel;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.nongame.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.nongame.IFactory;
import com.github.a2g.core.interfaces.nongame.presenter.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActionRunner;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromActions;
import com.github.a2g.core.primitive.LogNames;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.SingleCallAction;

public class ChainRunner implements IActionRunnerFromBaseAction {

	private static final Logger RUNNER = Logger.getLogger(LogNames.RUNNER.toString());
	private static final Logger ACTIONS_EXECUTED = Logger.getLogger(LogNames.ACTIONS_AS_THEY_ARE_EXECUTED.toString());
	private static final Logger ACTIONS_FLATTENED_B4_EXECUTION = Logger.getLogger(LogNames.ACTIONS_FLATTENED_B4_EXECUTION.toString());
	private static final Logger RUNNER_REFCOUNT = Logger.getLogger(LogNames.RUNNER_REFCOUNT.toString());

	protected ArrayList<ArrayList<BaseAction>> list;
	private ArrayList<BaseAction> parallelActionsToWaitFor;
	private int numberOfParallelActionsToWaitFor;
	private IMasterPresenterFromActionRunner callback;
	final private int id;
	private IMasterPresenterFromActions master;
	private IScenePresenterFromActions scene;
	private IDialogTreePresenterFromActions dt; 
	private IInventoryPresenterFromActions inv;

	private IFactory factory;

	public ChainRunner(IFactory factory,
			IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dt,
			IInventoryPresenterFromActions inv,
			IMasterPresenterFromActionRunner callback, int id) {
		this.id = id;
		this.master = master;
		this.callback = callback;
		this.scene = scene;
		this.inv = inv;
		this.dt = dt; 
		this.factory = factory;
		list = new ArrayList<ArrayList<BaseAction>>();
		parallelActionsToWaitFor = new ArrayList<BaseAction>();
		numberOfParallelActionsToWaitFor = 0;
	}

	static ArrayList<BaseAction> flattenChainAndEnsureTitleCardAtStart(IBaseChain grandChildOfActionChain) 
	{
		ArrayList<BaseAction> toReturn = new ArrayList<BaseAction>();
		IBaseChain a = grandChildOfActionChain;

		// flatten chain
		while (a != null && a.getAction()!=null) {
			toReturn.add(0, a.getAction());
			a = a.getParent();
		}
 
		return toReturn;
	}

	static ArrayList<ArrayList<BaseAction>> getGroupsOfConsecutiveParallelActions(
			ArrayList<BaseAction> flatlist) {
		ArrayList<ArrayList<BaseAction>> toReturn = new ArrayList<ArrayList<BaseAction>>();

		while (!flatlist.isEmpty()) {
			ArrayList<BaseAction> groupOfParallelActions = new ArrayList<BaseAction>();
			groupOfParallelActions.add(flatlist.get(0));
			flatlist.remove(0);

			// whilst the last item added was parallel, then add the next one..
			while (!flatlist.isEmpty()
					&& groupOfParallelActions.get(
							groupOfParallelActions.size() - 1).isParallel()) {
				groupOfParallelActions.add(flatlist.get(0));
				flatlist.remove(0);
			}
			toReturn.add(groupOfParallelActions);
		}

		return toReturn;
	}



	public int runChain(IBaseChain a) {
		ArrayList<BaseAction> flatlist = flattenChainAndEnsureTitleCardAtStart(a);
		String list = "";
		for(int i=0;i<flatlist.size();i++)
		{
			list+= this.getNameFromBaseAction(flatlist.get(i))+ "\n";
		}
		ACTIONS_FLATTENED_B4_EXECUTION.fine(list);
		this.list = getGroupsOfConsecutiveParallelActions(flatlist);

		processNextListOfParallelActions();

		return 0;
	}

	void executeParallelActions() {
		this.numberOfParallelActionsToWaitFor = this.parallelActionsToWaitFor
				.size();

		// having count as a local variable prevents a nasty bug - and I'm not
		// sure why.
		// this only happens in gwt.
		int count = this.parallelActionsToWaitFor.size();
		RUNNER.log( Level.FINE, "processing "+count+" parallel actions" );

		for (int i = 0; i < count; i++) {
			BaseAction a = this.parallelActionsToWaitFor.get(i);

			String name = getNameFromBaseAction(a);
			RUNNER.log( Level.FINE, "execute parallel actions "+i+" "+name );
			ACTIONS_EXECUTED.log(Level.FINE, name);
			a.setCallbacks(this);

			a.setSystemAnimation(factory.createAnimation(a));
			a.setAll(master, scene, dt, inv);
			
			a.runGameAction();
		}
	}

	private String getNameFromBaseAction(BaseAction a) 
	{
		String name = a.toString();
		String prefix = "com.github.a2g.core.action.";
		name = name.substring(prefix.length());
		if(name.startsWith("MakeSingleCallAction"))
		{
			SingleCallAction b = (SingleCallAction)a;
			name = b.getType().toString();
		}
		return name;
	}

	protected void processNextListOfParallelActions() {
		this.parallelActionsToWaitFor.clear();
		if (!this.list.isEmpty()) {
			parallelActionsToWaitFor = this.list.get(0);
			this.list.remove(0);

			// execute them
			executeParallelActions();
		} else {
			callback.actionChainFinished(this.id);
		}
	}

	public void skip() {
		numberOfParallelActionsToWaitFor = 100;
		for (int i = 0; i < parallelActionsToWaitFor.size(); i++) {
			parallelActionsToWaitFor.get(i).cancel();
		}

		numberOfParallelActionsToWaitFor = parallelActionsToWaitFor.size();
		for (int i = 0; i < parallelActionsToWaitFor.size(); i++) {
			parallelActionsToWaitFor.get(i).run(0);
		}
	}

	@Override
	public void startTheNextAction(BaseAction a) {
		this.numberOfParallelActionsToWaitFor--;
		RUNNER_REFCOUNT.log( Level.FINE, "Release " +numberOfParallelActionsToWaitFor );

		if (this.numberOfParallelActionsToWaitFor == 0) {
			RUNNER.log( Level.FINE, "Starting next action "+this.toString() );

			processNextListOfParallelActions();
		}

	}

	public void cancel() {
		// first clear the list, just incase cancelling the animation
		// triggers ongameactioncomplete (above)
		list.clear();
		numberOfParallelActionsToWaitFor = 0;

		// now cancel the action(s) that are running
		for (int i = 0; i < parallelActionsToWaitFor.size(); i++) {
			parallelActionsToWaitFor.get(i).cancel();
		}
	}
}
