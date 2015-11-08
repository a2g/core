package com.github.a2g.core.platforms.java;

import java.util.HashSet;
import java.util.Iterator;  

class Path<Node extends IGetNeighbours<Node>> implements Iterable<Node>
{
	private Node lastStep;
	private Path<Node> previousSteps;
	private double totalCost;

	public Path(Node start)
	{
		this.lastStep = start;
		this.previousSteps = null;
		this.totalCost = 0;
	}
	private Path(Node lastStep, Path<Node> previousSteps, double totalCost)
	{
		this.lastStep = lastStep;
		this.previousSteps = previousSteps;
		this.totalCost = totalCost;
	}


	public Node getLastStep(){ return lastStep;}
	public Path<Node> getPreviousSteps(){ return previousSteps;}
	public double getTotalCost(){return totalCost;}


	public Path<Node> addStep(Node step, double stepCost)
	{
		return new Path<Node>(step, this, totalCost + stepCost);
	}


	@Override
	public Iterator<Node> iterator() {

		return new PathIterator(this);
	}

	public class PathIterator implements Iterator<Node> 
	{
		Path<Node> p;

		PathIterator(Path<Node> p)
		{
			this.p = p;
		}

		@Override
		public boolean hasNext() {

			return p.getLastStep()!=null;
		}

		@Override
		public Node next() {
			Node toReturn = p.getLastStep();
			p = p.getPreviousSteps();
			return toReturn;
		}

		@Override
		public void remove() {

		}

	}

	interface IDistanceFunc<Node>
	{
		double distance(Node lastStep, Node n);
	}

	interface IEstimateFunc<Node>
	{
		double estimate(Node n);
	}

	public Path<Node> findPath(
			Node start, 
			Node destination, IDistanceFunc<Node> d, IEstimateFunc<Node> e) 
			{
		HashSet<Node> closed = new HashSet<Node>();
		PriorityQueue<Double, Path<Node>> queue = new PriorityQueue<Double, Path<Node>>();
		queue.enqueue(0.0, new Path<Node>(start));
		while(!queue.isEmpty())
		{

			Path<Node> path = queue.dequeue();
			if (closed.contains(path.getLastStep()))
				continue;
			if (path.getLastStep().equals(destination))
				return path;
			closed.add(path.getLastStep());

			Iterator<Node> neighbours = path.getLastStep().getNeighbours();
			while(neighbours.hasNext())
			{
				Node n = neighbours.next();
				double dist = d.distance(path.getLastStep(), n);
				Path<Node> newPath = path.addStep(n, dist);
				queue.enqueue(newPath.getTotalCost() + e.estimate(n), newPath);
			}
		}
		return null;
	}
}

