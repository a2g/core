package com.github.a2g.core.interfaces.nongame.action;

import java.util.List;

import com.google.gwt.touch.client.Point;

public interface IGetPath{
	List<Point> findPath(Point rawStart, Point rawEnd);
}
