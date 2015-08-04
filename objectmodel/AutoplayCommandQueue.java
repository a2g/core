package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.IOnPreEntry;
import com.github.a2g.core.interfaces.ISolution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class AutoplayCommandQueue 
{
	Deque<ISolution> solutions;
	Deque<AutoplayCommand> currentSet;
	int currentIndexIntoFirst;
	public AutoplayCommandQueue()
	{
		currentIndexIntoFirst = 0 ;
		solutions = new ArrayDeque<ISolution>();
		currentSet =  new LinkedList<AutoplayCommand>();
	}
	
	public void add(ISolution sol)
	{
		solutions.add(sol);
	}
	
	void getNextSet(AutoplayCommand a)
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
		solutions.getFirst().onPreEntry(api);
		currentIndexIntoFirst = 0;
		return getNext(api);	
	}
}
