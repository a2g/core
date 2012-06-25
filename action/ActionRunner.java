package com.github.a2g.core.action;

import java.util.ArrayList;

public class ActionRunner implements ICallbacksFromGameAction
{
	protected ArrayList<BaseAction> list;
	private ArrayList<BaseAction> parallelActionsToWaitFor;
	private int numberOfParallelActionsToWaitFor;
	public ActionRunner()
	{
		list = new ArrayList<BaseAction>();
		parallelActionsToWaitFor = new ArrayList<BaseAction>();
		numberOfParallelActionsToWaitFor = 0;
	}

	public int runAction(BaseAction grandChildOfActionChain) 
	{
		this.list = new ArrayList<BaseAction>();
		BaseAction a = grandChildOfActionChain;

		while (a.getParent() != null) {
			this.list.add(0, a);
			a = a.getParent();
		}

		processNextListOfParallelActions();

		return 0;
	}


	void executeParallelActions() {
		this.numberOfParallelActionsToWaitFor = this.parallelActionsToWaitFor.size();
		for (int i = 0; i
		< this.parallelActionsToWaitFor.size(); i++) 
		{
			BaseAction a = this.parallelActionsToWaitFor.get(i);

			a.setCallbacks(this);
			a.runGameAction();
		}
	}

	protected void processNextListOfParallelActions() {
		this.parallelActionsToWaitFor.clear();
		if (!this.list.isEmpty()) {
			// add next batch of contigous parallel actions
			while (!this.list.isEmpty()
					&& this.list.get(0).isParallel()) {
				BaseAction theAction = this.list.get(
						0);

				this.list.remove(0);
				this.parallelActionsToWaitFor.add(
						theAction);
			}

			// if there was no parallel actions then add the non parallel one 
			if (this.parallelActionsToWaitFor.isEmpty()) {
				BaseAction theAction = this.list.get(
						0);

				this.list.remove(0);
				this.parallelActionsToWaitFor.add(
						theAction);
			}

			// execute them	
			executeParallelActions();
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
	public void onGameActionComplete(BaseAction a) {
		this.numberOfParallelActionsToWaitFor--;
		if (this.numberOfParallelActionsToWaitFor
				== 0) {
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