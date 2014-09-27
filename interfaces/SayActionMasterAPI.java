package com.github.a2g.core.interfaces;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.primitive.ColorEnum;

public interface SayActionMasterAPI {

	void displayTitleCard(String string);

	int getPopupDelay();

	void setStateOfPopup(boolean visible, double d, double e, ColorEnum color,
			String string, BaseAction sayAction);



}
