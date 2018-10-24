package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;
import com.google.gwt.touch.client.Point;

public interface IWalkNeverSwitch {
    IChainBase walkNeverSwitch(double x, double y);
    IChainBase walkNeverSwitch(Point pt);
    IChainBase walkNeverSwitch(short obj, double x, double y);
    IChainBase walkNeverSwitch(short obj, Point pt);
    IChainBase walkNeverSwitchNonBlocking(short obj, double x, double y);

}
