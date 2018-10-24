package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface IMoveWhilstAnimating {
    IChainBase moveWhilstAnimating(short objId, double x, double y);
    IChainBase moveWhilstAnimatingInY(short objId, double y);
    IChainBase moveWhilstAnimatingNonBlocking(short objId, double x,double y);
    IChainBase moveWhilstAnimatingLinearNonBlocking(short objId, double x,double y);
    IChainBase moveWhilstAnimatingLinear(short objId, double x, double y);
}
