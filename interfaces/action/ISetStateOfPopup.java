package com.github.a2g.core.interfaces.action;

import com.github.a2g.core.action.TalkAction;
import com.github.a2g.core.action.performer.TalkPerformer;

public interface ISetStateOfPopup {
	void setStateOfPopup(String atid, boolean isVisible, String speech, TalkPerformer talkPerformer);

}
