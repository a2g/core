package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;

public interface SayActionObjectAPI {

	int getNumberOfFramesForAnimation(String animId);

	double getDurationForAnimation(String animId);

	void setCurrentAnimation(String animId);

	void setVisible(boolean b);

	ColorEnum getTalkingColor();

	void setCurrentFrame(int frame);

	void setToInitialAnimationWithoutChangingFrame();

}
