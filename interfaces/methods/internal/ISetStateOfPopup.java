package com.github.a2g.core.interfaces.methods.internal;

import com.github.a2g.core.action.performer.TalkPerformer;
import com.github.a2g.core.primitive.SpeechBubble;

public interface ISetStateOfPopup {
	void setStateOfPopup(boolean isVisible, SpeechBubble rectAndLeaderLine, TalkPerformer talkPerformer);

}
