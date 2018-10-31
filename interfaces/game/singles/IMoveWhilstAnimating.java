package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface IMoveWhilstAnimating {
    IBaseChain moveWhilstAnimating(short objId, double x, double y);
    IBaseChain moveWhilstAnimatingInY(short objId, double y);
    IBaseChain moveWhilstAnimatingNonBlocking(short objId, double x,double y);
    IBaseChain moveWhilstAnimatingLinearNonBlocking(short objId, double x,double y);
    IBaseChain moveWhilstAnimatingLinear(short objId, double x, double y);
}
