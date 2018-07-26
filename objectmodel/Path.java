package com.github.a2g.core.objectmodel;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import com.github.a2g.core.interfaces.nongame.IGetNeighbours;


class PathComparator implements Comparator<Path<?>>
{

	@Override
	public int compare(Path<?> a, Path<?> b) {
		
		double result =  a.getPriority()*1000 - b.getPriority()*1000;
		return (int)result;
	}
	
}

public class Path<TNode extends IGetNeighbours<TNode>> implements
		Iterable<TNode>, Comparable<Path<TNode>>{
	private TNode lastStep;
	private Path<TNode> previousSteps;
	private double totalCost;
	private double priority;

	public Path(double priority, TNode start) {
		this.priority = priority;
		this.lastStep = start;
		this.previousSteps = null;
		this.totalCost = 0;
	}

	public double getPriority() {
		return priority;
	}

	private Path(double priority, TNode lastStep, Path<TNode> previousSteps,
			double totalCost) {
		this.priority = priority;
		this.lastStep = lastStep;
		this.previousSteps = previousSteps;
		this.totalCost = totalCost;
	}

	public TNode getLastStep() {
		return lastStep;
	}

	public Path<TNode> getPreviousSteps() {
		return previousSteps;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public Path<TNode> addStep(double priority, TNode step, double stepCost) {
		return new Path<TNode>(priority, step, this, totalCost + stepCost);
	}

	@Override
	public Iterator<TNode> iterator() {

		return new PathIterator(this);
	}

	public class PathIterator implements Iterator<TNode> {
		Path<TNode> p;

		PathIterator(Path<TNode> p) {
			this.p = p;
		}

		@Override
		public boolean hasNext() {

			return p.getLastStep() != null;
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

	public interface IDistanceFunc<TNode> {
		double distance(TNode lastStep, TNode n);
	}

	public interface IEstimateFunc<TNode> {
		double estimate(TNode n, TNode destination);
	}

	public static <TNode extends IGetNeighbours<TNode>> Path<TNode> findPath(
			TNode start, TNode destination, IDistanceFunc<TNode> d,
			IEstimateFunc<TNode> e) {
		HashSet<TNode> closed = new HashSet<TNode>();
		java.util.PriorityQueue<Path<TNode>> queue = new java.util.PriorityQueue<Path<TNode>>();
		queue.add(new Path<TNode>(0.0, start));
		while (!queue.isEmpty()) {

			Path<TNode> path = queue.poll();
			if (closed.contains(path.getLastStep()))
				continue;
			if (path.getLastStep().equals(destination))
				return path;
			closed.add(path.getLastStep());

			Iterator<TNode> neighbours = path.getLastStep().getNeighbours();
			while (neighbours.hasNext()) {
				TNode n = neighbours.next();
				double dist = d.distance(path.getLastStep(), n);
				double newEstimate = path.getTotalCost() + dist
						+ e.estimate(n, destination);
				Path<TNode> newPath = path.addStep(newEstimate, n, dist);
				queue.add(newPath);
			}
		}
		return null;
	}

	@Override
	public int compareTo(Path<TNode> arg0) {
		double result =  this.getPriority()*1000 - arg0.getPriority()*1000;
		return (int)result;
	}
}
