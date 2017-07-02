package com.github.a2g.core.interfaces.methods.action;

import com.github.a2g.core.action.performer.TalkPerformer;

public interface ISetStateOfPopup {
	void setStateOfPopup(boolean isVisible, String speech, String atid, TalkPerformer talkPerformer);

}
