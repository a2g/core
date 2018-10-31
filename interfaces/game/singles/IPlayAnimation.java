package com.github.a2g.core.interfaces.game.singles;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;

public interface IPlayAnimation {
    IBaseChain playAnimation(String animationCode);

    /** @name ..simple backwards ...*/
    //@{
    IBaseChain playAnimationBackwards(String animationCode);
    //@}

    /** @name ..simple hold last frame ... */
    //@{
    IBaseChain playAnimationHoldLastFrame(String animationCode);
    //@}

    /** @name ... simple non blocking ... */
    //@{
    IBaseChain playAnimationNonBlocking(String animationCode);
    //@}

    /** @name ... double combo1of3: backwards + hold last frame ... */
    //@{
    IBaseChain playAnimationBackwardsHoldLastFrame(String animationCode);
    //@}

    /** @name ... double combo2of3: backwards + nonblocking ... */
    //@{
    IBaseChain playAnimationBackwardsNonBlocking(String animationCode);
    //@}
    
    /** @name ... double combo2of3: holdLastFrame + nonblocking ... */
    //@{
    IBaseChain playAnimationHoldLastFrameNonBlocking(String animationCode);
    IBaseChain playAnimationNonBlockingHoldLastFrame(String animationCode);
    //@}
    
    /** @name ..and one method with the whole lot! */
    //@{
    IBaseChain playAnimationBackwardsHoldLastFrameNonBlocking(String animationCode);
}
