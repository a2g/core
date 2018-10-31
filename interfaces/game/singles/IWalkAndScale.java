package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public interface IWalkAndScale {
    
    IBaseChain walkAndScaleAlwaysSwitch(short obj, Point pt, double startScale, double endScale, String sceneName, int entrySegment)throws A2gException;
    IBaseChain walkAndScaleNeverSwitch(short obj, Point pt, double startScale, double endScale);
}
