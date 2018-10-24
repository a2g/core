package com.github.a2g.core.interfaces.game.singles;
 
import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public interface IWalkAlwaysSwitch {
    IChainBase walkAlwaysSwitch(double x, double y, String sceneName, int entrySegment) throws A2gException ;
    IChainBase walkAlwaysSwitch(Point point, String sceneName, int entrySegment) throws A2gException ;
}

