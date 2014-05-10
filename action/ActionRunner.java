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

import com.github.a2g.core.interfaces.ActionCallbackAPI;
import com.github.a2g.core.interfaces.ActionRunnerCallbackAPI;

public class ActionRunner implements ActionCallbackAPI
{
	protected ArrayList<ArrayList<BaseAction>>  list;
	private ArrayList<BaseAction> parallelActionsToWaitFor;
	private int numberOfParallelActionsToWaitFor;
	private ActionRunnerCallbackAPI api;
	final private int id;

	public ActionRunner(ActionRunnerCallbackAPI api, int id)
	{
		this.id = id;
		this.api=api;
		list = new ArrayList<ArrayList<BaseAction>>();
		parallelActionsToWaitFor = new ArrayList<BaseAction>();
		numberOfParallelActionsToWaitFor = 0;
	}
	
	static ArrayList<BaseAction> flattenChainAndEnsureTitleCardAtStart(BaseAction grandChildOfActionChain)
	{
		ArrayList<BaseAction> toReturn = new ArrayList<BaseAction>();
		BaseAction a = grandChildOfActionChain;

		// flatten chain
		while (a.getParent() != null) {
			toReturn.add(0, a);
			a = a.getParent();
		}
		
		// ensure titlecard at start
		if(toReturn.size()>0 && toReturn.get(0).getClass()!=TitleCardAction.class)
		{
			toReturn.add(0,new TitleCardAction(a,""));
		}
		
		return toReturn;
	}
	
	static ArrayList<ArrayList<BaseAction>> getGroupsOfConsecutiveParallelActions(ArrayList<BaseAction> flatlist)
	{
		ArrayList<ArrayList<BaseAction>> toReturn = new ArrayList<ArrayList<BaseAction>>();
	
		while (!flatlist.isEmpty())
		{
			ArrayList<BaseAction> groupOfParallelActions = new ArrayList<BaseAction>();
			groupOfParallelActions.add(flatlist.get(0));
			flatlist.remove(0);
			
			// whilst the last item added was parallel, then add the next one..
			while (!flatlist.isEmpty() && groupOfParallelActions.get(groupOfParallelActions.size()-1).isParallel()) 
			{
				groupOfParallelActions.add(flatlist.get(0));
				flatlist.remove(0);
			}
			toReturn.add(groupOfParallelActions);
		}
		
		return toReturn;
	}
	
	static ArrayList<ArrayList<BaseAction>> getListOfListsFromChain(BaseAction grandChildOfActionChain)
	{
		ArrayList<BaseAction> flatlist = flattenChainAndEnsureTitleCardAtStart(grandChildOfActionChain);
		return getGroupsOfConsecutiveParallelActions(flatlist);	
	}

	public int runAction(BaseAction grandChildOfActionChain)
	{
		ArrayList<BaseAction> flatlist = flattenChainAndEnsureTitleCardAtStart(grandChildOfActionChain);
		this.list = getGroupsOfConsecutiveParallelActions(flatlist);
		
		processNextListOfParallelActions();

		return 0;
	}


	void executeParallelActions()
	{
		this.numberOfParallelActionsToWaitFor = this.parallelActionsToWaitFor.size();

		// having count as a local variable prevents a nasty bug - and I'm not sure why.
		// this only happens in gwt.
		int count = this.parallelActionsToWaitFor.size();
		for (int i = 0; i < count ;i++)
		{
			BaseAction a = this.parallelActionsToWaitFor.get(i);

			System.out.println("ActionRunner::executeParallelActions " + i+ " " + a.toString() );

			a.setCallbacks(this);
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
		}
		else
		{
			api.actionFinished(this.id);
		}
	}

	public void skip()
	{
		numberOfParallelActionsToWaitFor = 100;
		for(int i=0;i < parallelActionsToWaitFor.size();i++)
		{
			parallelActionsToWaitFor.get(i).cancel();
		}

		numberOfParallelActionsToWaitFor = parallelActionsToWaitFor.size();
		for(int i=0;i < parallelActionsToWaitFor.size();i++)
		{
			parallelActionsToWaitFor.get(i).run(0);
		}
	}

	@Override
	public void startTheNextAction(BaseAction a) {
		this.numberOfParallelActionsToWaitFor--;
		System.out.println("Release " + numberOfParallelActionsToWaitFor );

		if (this.numberOfParallelActionsToWaitFor
				== 0)
		{
			System.out.println("Starting next action " + this.toString() );
			processNextListOfParallelActions();
		}

	}

	public void cancel() {
		// first clear the list, just incase cancelling the animation
		// triggers ongameactioncomplete (above)
		list.clear();
		numberOfParallelActionsToWaitFor=0;

		// now cancel the action(s) that are running
		for(int i=0;i<parallelActionsToWaitFor.size();i++)
		{
			parallelActionsToWaitFor.get(i).cancel();
		}
	}
}
