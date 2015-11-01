package com.github.a2g.core.platforms.java;

import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;

public class PriorityQueue<P, V>
{
	private TreeMap<P, Queue<V>> list = new TreeMap<P, Queue<V>>();
	
	public void enqueue(P priority, V value)
	{
    	Queue<V> q;
    	if (!list.containsKey(priority))
    	{
        	q = new LinkedList<V>();
        	list.put(priority, q);
    	}else
    	{
    		q = list.get(priority);
    	}
    	q.add(value);
	}
	
	public V dequeue()
	{
    	// will throw if there isn’t any first element!
    	Entry<P, Queue<V>> pair = list.firstEntry();
    	V v = pair.getValue().remove();
    	if (pair.getValue().size() == 0) // nothing left of the top priority.
        	list.remove(pair.getKey());
    	return v;
	}
	
	public boolean isEmpty()
	{
    	return list.isEmpty();
	}
}

