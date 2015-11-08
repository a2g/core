package com.github.a2g.core.platforms.java;

import java.util.HashSet;
import java.util.Iterator;  

class Path<TNode extends IGetNeighbours<TNode>> implements Iterable<TNode>
{
	private TNode lastStep;
	private Path<TNode> previousSteps;
	private double totalCost;

	public Path(TNode start)
	{
		this.lastStep = start;
		this.previousSteps = null;
		this.totalCost = 0;
	}
	private Path(TNode lastStep, Path<TNode> previousSteps, double totalCost)
	{
		this.lastStep = lastStep;
		this.previousSteps = previousSteps;
		this.totalCost = totalCost;
	}


	public TNode getLastStep(){ return lastStep;}
	public Path<TNode> getPreviousSteps(){ return previousSteps;}
	public double getTotalCost(){return totalCost;}


	public Path<TNode> addStep(TNode step, double stepCost)
	{
		return new Path<TNode>(step, this, totalCost + stepCost);
	}


	@Override
	public Iterator<TNode> iterator() {

		return new PathIterator(this);
	}

	public class PathIterator implements Iterator<TNode> 
	{
		Path<TNode> p;

		PathIterator(Path<TNode> p)
		{
			this.p = p;
		}

		@Override
		public boolean hasNext() {

			return p.getLastStep()!=null;
		}

		@Override
		public TNode next() {
			TNode toReturn = p.getLastStep();
			p = p.getPreviousSteps();
			return toReturn;
		}

		@Override
		public void remove() {

		}

	}

	public interface IDistanceFunc<TNode>
	{
		double distance(TNode lastStep, TNode n);
	}

	public interface IEstimateFunc<TNode>
	{
		double estimate(TNode n, TNode destination);
	}

	public static <TNode extends IGetNeighbours<TNode> > Path<TNode> findPath(TNode start, TNode destination, IDistanceFunc<TNode> d, IEstimateFunc<TNode> e) 
	{
		HashSet<TNode> closed = new HashSet<TNode>();
		PriorityQueue<Double, Path<TNode>> queue = new PriorityQueue<Double, Path<TNode>>();
		queue.enqueue(0.0, new Path<TNode>(start));
		while(!queue.isEmpty())
		{

			Path<TNode> path = queue.dequeue();
			if (closed.contains(path.getLastStep()))
				continue;
			if (path.getLastStep().equals(destination))
				return path;
			closed.add(path.getLastStep());

			Iterator<TNode> neighbours = path.getLastStep().getNeighbours();
			while(neighbours.hasNext())
			{
				TNode n = neighbours.next();
				double dist = d.distance(path.getLastStep(), n);
				Path<TNode> newPath = path.addStep(n, dist);
				queue.enqueue(newPath.getTotalCost() + e.estimate(n, destination), newPath);
			}
		}
		return null;
	}
}

