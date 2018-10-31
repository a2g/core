package com.github.a2g.core.interfaces.game.singles;

 import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.google.gwt.touch.client.Point;

public interface IWalkTo {
    IBaseChain walkTo(double x, double y);
    IBaseChain walkTo(Point point);
}
