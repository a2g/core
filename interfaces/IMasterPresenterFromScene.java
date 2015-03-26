package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.PointF;

public interface IMasterPresenterFromScene {
	void switchToSceneFromAction(String scene);

	void switchToScene(String scene);

	IFactory getFactory();

	void shareWinning(String token);

	void setValue(String name, int i);

	void fireOnMovementBeyondAGateIfRelevant(PointF pointF);

	boolean isInANoGoZone(PointF pointF);
}
