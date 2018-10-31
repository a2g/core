package com.github.a2g.core.interfaces.game.singles;
 
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.primitive.A2gException;
import com.google.gwt.touch.client.Point;

public interface IWalkAlwaysSwitch {
    IBaseChain walkAlwaysSwitch(double x, double y, String sceneName, int entrySegment) throws A2gException ;
    IBaseChain walkAlwaysSwitch(Point point, String sceneName, int entrySegment) throws A2gException ;
}

