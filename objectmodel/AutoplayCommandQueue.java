package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.game.handlers.IOnPreEntry;
import com.github.a2g.core.interfaces.nongame.ISolution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class AutoplayCommandQueue implements ISolution
{
	Deque<ISolution> solutions;
	Deque<AutoplayCommand> currentSet;
	int currentIndexIntoFirst;
	public AutoplayCommandQueue()
	{
		currentIndexIntoFirst = 0 ;
		solutions = new ArrayDeque<ISolution>();
		solutions.add(this);//this isn't a bug, we must do this
		currentSet =  new LinkedList<AutoplayCommand>();
	}

	public void add(ISolution sol)
	{
		solutions.add(sol);
	}

	private void getNextSet(AutoplayCommand a)
	{
		if(a!=null&&a.getParent()!=null )
		{
			currentSet.addFirst(a);
			getNextSet(a.getParent());
		}
	}

	public AutoplayCommand getNext(IOnPreEntry api)
	{
		if(!currentSet.isEmpty())
			return currentSet.removeFirst();
		if(solutions.isEmpty())
			return null;
		AutoplayCommand chain = solutions.getFirst().getNext(currentIndexIntoFirst++);
		getNextSet(chain);
		if(!currentSet.isEmpty())
			return currentSet.removeFirst();
		solutions.removeFirst();
		if(!solutions.isEmpty())
		{
			api.hideInventoryItemsAllOfThem();
			api.clearValuesAndSaidSpeech();
			solutions.getFirst().onPreEntry(api);
		}
		currentIndexIntoFirst = 0;
		return getNext(api);
	}

	@Override
	public AutoplayCommand getNext(int i) {
		// this queue uses this as the getNect method
		// of the first solution in Solutions.
		// Thus triggering the second solution in solutions
		// and the subsequent call to onPreEntry
		return null;
	}

	@Override
	public void onPreEntry(IOnPreEntry api) {
		// TODO Auto-generated method stub

	}
}
