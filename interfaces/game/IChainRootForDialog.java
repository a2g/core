package com.github.a2g.core.interfaces.game;

import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainableAction;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISleep;
import com.google.gwt.touch.client.Point;

public interface IChainRootForDialog extends IChainRootCommon
, ISleep
, ISetValue
, ISetInventoryItemVisible
, ISetAnimationAsObjectInitial
{
	/*@ Start with dialog only stuff */
	//@{
	DialogChainableAction them(String speech);
	DialogChainableAction us(String speech);
	DialogChainableAction branchNormal(int branchId, final boolean isOkToAdd, String text);
	DialogChainableAction branchNormal(int branchId, String text) ;
	DialogChainableAction branchSticky(int branchId, String text) ;
	DialogChainableAction branchSticky(int nativ2, boolean b, String string);
	//@}
	
	/*@ include a bunch of methods also found on the scene chains.... */
	//@{
	@Override DialogChainableAction setValue(Object key, int value) ;
	@Override DialogChainableAction setInventoryItemVisible(int redNote, boolean b);
	@Override DialogChainableAction setAnimationAsObjectInitial(String atid);
	@Override DialogChainableAction sleep(int milliseconds);
	//@}

	/*@ ..and a whole heap of dialog specific exits */
	//@{
	DialogChainEndAction endDialogTree();
	DialogChainEndAction chainTo(int branchId);
	DialogChainEndAction switchTo(String string, int arrivalSegment);
	DialogChainEndAction branchTheObligatoryExit(String string);
	DialogChainEndAction walkAlwaysSwitch(Point p, String sceneName, int arrivalSegment);
	DialogChainEndAction walkAlwaysSwitch(double x, double y, String sceneName,	int arrivalSegment);
	//@}
}
