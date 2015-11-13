package com.github.a2g.core.interfaces.methods.action;

import java.util.List;

import com.github.a2g.core.primitive.PointF;

public interface IGetPath{
	List<PointF> findPath(PointF rawStart, PointF rawEnd);
}
