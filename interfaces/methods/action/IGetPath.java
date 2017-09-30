package com.github.a2g.core.interfaces.methods.action;

import java.util.List;

import com.github.a2g.core.primitive.Point;

public interface IGetPath{
	List<Point> findPath(Point rawStart, Point rawEnd);
}
