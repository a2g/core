package com.github.a2g.core.interfaces.action;

import com.github.a2g.core.action.SayAction;

public interface ISetStateOfPopup {
	void setStateOfPopup(String atid, boolean isVisible, String speech, SayAction sayAction);
			
}
