package com.github.a2g.core.interfaces.action;

import com.github.a2g.core.action.SayAction;
import com.github.a2g.core.primitive.ColorEnum;

public interface ISetStateOfPopup {
	void setStateOfPopup(boolean isVisible, double x, double y, ColorEnum talkingColor,
			String speech, SayAction sayAction);
			
}
