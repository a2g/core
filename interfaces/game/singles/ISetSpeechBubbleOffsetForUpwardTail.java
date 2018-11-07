package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.google.gwt.touch.client.Point;

public interface ISetSpeechBubbleOffsetForUpwardTail {
    IBaseChain    setSpeechBubbleOffsetForUpwardTail(short ocode, Point pt);
}
