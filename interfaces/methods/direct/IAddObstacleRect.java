package com.github.a2g.core.interfaces.methods.direct;


public interface IAddObstacleRect {
	void addObstacleRect(double x, double y, double right, double bottom);
	void addObstacleRect(double x, double y, int helper);
	void addObstacleRect(int helper, double x, double y);
	void addObstacleRect(int helper, int helper2);
}
