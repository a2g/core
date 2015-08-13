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

package com.github.a2g.core.action;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.a2g.core.interfaces.IActionRunnerFromBaseAction;
import com.github.a2g.core.interfaces.IDialogTreePresenterFromActions;
import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IInventoryPresenterFromActions;
import com.github.a2g.core.interfaces.IMasterPresenterFromActionRunner;
import com.github.a2g.core.interfaces.IMasterPresenterFromActions;
import com.github.a2g.core.interfaces.IScenePresenterFromActions;
import com.github.a2g.core.interfaces.ITitleCardPresenterFromActions;
import com.github.a2g.core.primitive.LogNames;

public class ActionRunner implements IActionRunnerFromBaseAction {

	private static final Logger RUNNER = Logger.getLogger(LogNames.RUNNER);
	private static final Logger ACTIONS_EXECUTED = Logger.getLogger(LogNames.ACTIONS_EXECUTED);


	private static final Logger RUNNER_REFCOUNT = Logger.getLogger(LogNames.RUNNER_REFCOUNT);

	protected ArrayList<ArrayList<BaseAction>> list;
	private ArrayList<BaseAction> parallelActionsToWaitFor;
	private int numberOfParallelActionsToWaitFor;
	private IMasterPresenterFromActionRunner callback;
	final private int id;
	private IMasterPresenterFromActions master;
	private IScenePresenterFromActions scene;
	private IDialogTreePresenterFromActions dt;
	private ITitleCardPresenterFromActions tc;
	private IInventoryPresenterFromActions inv;

	private IFactory factory;

	public ActionRunner(IFactory factory,
			IMasterPresenterFromActions master,
			IScenePresenterFromActions scene,
			IDialogTreePresenterFromActions dt,
			ITitleCardPresenterFromActions tc,
			IInventoryPresenterFromActions inv,
			IMasterPresenterFromActionRunner callback, int id) {
		this.id = id;
		this.master = master;
		this.callback = callback;
		this.scene = scene;
		this.inv = inv;
		this.dt = dt;
		this.tc = tc;
		this.factory = factory;
		list = new ArrayList<ArrayList<BaseAction>>();
		parallelActionsToWaitFor = new ArrayList<BaseAction>();
		numberOfParallelActionsToWaitFor = 0;
	}

	static ArrayList<BaseAction> flattenChainAndEnsureTitleCardAtStart(
			BaseAction grandChildOfActionChain) {
		ArrayList<BaseAction> toReturn = new ArrayList<BaseAction>();
		BaseAction a = grandChildOfActionChain;

		// flatten chain
		while (a != null) {
			toReturn.add(0, a);
			a = a.getParent();
		}

		
		// ensure titlecard at start
		if (toReturn.size() > 0
				&& toReturn.get(0).getClass() != TitleCardAction.class) {
			toReturn.add(0, new TitleCardAction(a, ""));
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

	public static ArrayList<ArrayList<BaseAction>> getListOfListsFromChain(
			BaseAction grandChildOfActionChain) {
		ArrayList<BaseAction> flatlist = flattenChainAndEnsureTitleCardAtStart(grandChildOfActionChain);
		return getGroupsOfConsecutiveParallelActions(flatlist);
	}

	public int runAction(BaseAction grandChildOfActionChain) {
		ArrayList<BaseAction> flatlist = flattenChainAndEnsureTitleCardAtStart(grandChildOfActionChain);
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

			String name = a.toString();
			String prefix = "com.github.a2g.core.action.";
			name = name.substring(prefix.length());
			if(name.startsWith("MakeSingleCallAction"))
			{
				SingleCallAction b = (SingleCallAction)a;
				name = b.getType().toString();
			}
			RUNNER.log( Level.FINE, "execute parallel actions "+i+" "+name );
			ACTIONS_EXECUTED.log(Level.FINE, name);
			a.setCallbacks(this);

			a.setSystemAnimation(factory.createSystemAnimation(a));
			a.setAll(master, scene, dt, tc, inv);
			a.runGameAction();
		}
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
