package com.github.a2g.core.interfaces.game.chainables;


/* There already exists the ChainEnd that extends ChainBase
 * and DialogChainEnd that extends ChainEnd - that prevents the
 *  use chaining extra actions at the emd of a chain.
 *  
 *  But then we have all the api methods shared across both chains
 *  that also need to be chainable. These methods use this as a return value
 *  so that a later declaration can override the return value to whatever is needed.
 */
public interface IChainBase {

}
