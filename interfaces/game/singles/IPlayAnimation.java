package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IChainBase;

public interface IPlayAnimation {
    IChainBase playAnimation(String animationCode);

    /** @name ..simple backwards ...*/
    //@{
    IChainBase playAnimationBackwards(String animationCode);
    //@}

    /** @name ..simple hold last frame ... */
    //@{
    IChainBase playAnimationHoldLastFrame(String animationCode);
    //@}

    /** @name ... simple non blocking ... */
    //@{
    IChainBase playAnimationNonBlocking(String animationCode);
    //@}

    /** @name ... double combo1of3: backwards + hold last frame ... */
    //@{
    IChainBase playAnimationBackwardsHoldLastFrame(String animationCode);
    //@}

    /** @name ... double combo2of3: backwards + nonblocking ... */
    //@{
    IChainBase playAnimationBackwardsNonBlocking(String animationCode);
    //@}
    
    /** @name ... double combo2of3: holdLastFrame + nonblocking ... */
    //@{
    IChainBase playAnimationHoldLastFrameNonBlocking(String animationCode);
    IChainBase playAnimationNonBlockingHoldLastFrame(String animationCode);
    //@}
    
    /** @name ..and one method with the whole lot! */
    //@{
    IChainBase playAnimationBackwardsHoldLastFrameNonBlocking(String animationCode);
}
