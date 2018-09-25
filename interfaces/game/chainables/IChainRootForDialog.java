package com.github.a2g.core.interfaces.game.chainables;

import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainableAction;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISleep;
import com.google.gwt.touch.client.Point;

/** @brief This facilitates the returning of the chain in @ref IOnDialogTree */
public interface IChainRootForDialog extends IChainBase
, ISleep
, ISetValue
, ISetInventoryItemVisible
, ISetAnimationAsObjectInitial
{
	/**   @name Start with dialog only stuff */
	//@{
	DialogChainableAction them(String speech);
	DialogChainableAction us(String speech);
	DialogChainableAction branchNormal(int branchId, final boolean isOkToAdd, String text);
	DialogChainableAction branchNormal(int branchId, String text) ;
	DialogChainableAction branchSticky(int branchId, String text) ;
	DialogChainableAction branchSticky(int nativ2, boolean b, String string);
	//@}
	
	/**   @name include a bunch of methods also found on the scene chains.... */
	//@{
	@Override DialogChainableAction setValue(Object key, int value) ;
	@Override DialogChainableAction setInventoryItemVisible(int redNote, boolean b);
	@Override DialogChainableAction setAnimationAsObjectInitial(String atid);
	@Override DialogChainableAction sleep(int milliseconds);
	//@}

	/**   @name ..and a whole heap of dialog specific exits */
	//@{
	DialogChainEndAction endDialogTree();
	DialogChainEndAction chainTo(int branchId);
	DialogChainEndAction switchTo(String string, int entrySegment);
	DialogChainEndAction branchTheObligatoryExit(String string);
	DialogChainEndAction walkAlwaysSwitch(Point p, String sceneName, int entrySegment);
	DialogChainEndAction walkAlwaysSwitch(double x, double y, String sceneName,	int entrySegment);
	//@}
}
