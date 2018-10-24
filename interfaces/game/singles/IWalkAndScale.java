package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public interface IWalkAndScale {
    
    IChainBase walkAndScaleAlwaysSwitch(short obj, Point pt, double startScale, double endScale, String sceneName, int entrySegment)throws A2gException;
    IChainBase walkAndScaleNeverSwitch(short obj, Point pt, double startScale, double endScale);
}
