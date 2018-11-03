package com.github.a2g.core.interfaces.game.singles;

import com.google.gwt.touch.client.Point;

public interface IAddEdgeRectangle {
    void addEdgeRectangle(Point topLeft, Point topRight);
	void addEdgeRectangle(double x, double y, double right, double bottom);
	void addEdgeRectangle(double x, double y, int helper);
	void addEdgeRectangle(int helper, double x, double y);
	void addEdgeRectangle(int helper, int helper2);
}
