package com.github.a2g.core.interfaces.game.singles;

 import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.google.gwt.touch.client.Point;

public interface IWalkTo {
    IChainBase walkTo(double x, double y);
    IChainBase walkTo(Point point);
}
