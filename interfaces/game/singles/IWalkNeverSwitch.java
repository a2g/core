package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.google.gwt.touch.client.Point;

public interface IWalkNeverSwitch {
    IBaseChain walkNeverSwitch(double x, double y);
    IBaseChain walkNeverSwitch(Point pt);
    IBaseChain walkNeverSwitch(short obj, double x, double y);
    IBaseChain walkNeverSwitch(short obj, Point pt);
    IBaseChain walkNeverSwitchNonBlocking(short obj, double x, double y);

}
