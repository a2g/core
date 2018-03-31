package com.github.a2g.core.interfaces.methods.action;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.primitive.RectAndLeaderLine;

public interface ISetStateOfPopup {
	void setStateOfPopup(boolean isVisible, String speech, String atid, RectAndLeaderLine rectAndLeaderLine, TalkPerformer talkPerformer);

}
