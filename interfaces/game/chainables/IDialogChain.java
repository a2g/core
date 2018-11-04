package com.github.a2g.core.interfaces.game.chainables;

import com.github.a2g.core.interfaces.game.singles.IDoNothing;
import com.github.a2g.core.interfaces.game.singles.IMoveCamera;
import com.github.a2g.core.interfaces.game.singles.IPlayAnimation;
import com.github.a2g.core.interfaces.game.singles.IPlaySound;
import com.github.a2g.core.interfaces.game.singles.IPlayTitleCard;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectCurrent;
import com.github.a2g.core.interfaces.game.singles.ISetAnimationAsObjectInitial;
import com.github.a2g.core.interfaces.game.singles.ISetInventoryItemVisible;
import com.github.a2g.core.interfaces.game.singles.ISetValue;
import com.github.a2g.core.interfaces.game.singles.ISetVisible;
import com.github.a2g.core.interfaces.game.singles.ISleep;
import com.github.a2g.core.interfaces.game.singles.ISwapVisibility;
import com.github.a2g.core.interfaces.game.singles.IWaitForFrame;
import com.github.a2g.core.interfaces.game.singles.IWalkAndScale;
import com.github.a2g.core.interfaces.game.singles.IWalkAndTalk;
import com.github.a2g.core.interfaces.game.singles.IWalkNeverSwitch;
import com.google.gwt.touch.client.Point;

/** @brief This facilitates the returning of the chain in @ref IOnDialogTree */
public interface IDialogChain extends IDialogChainEnd
, ISleep
, ISetValue
, ISetInventoryItemVisible
, ISetAnimationAsObjectInitial
, ISetAnimationAsObjectCurrent
, IMoveCamera
, IPlayAnimation
, IPlaySound
, IPlayTitleCard
, ISwapVisibility
, IWaitForFrame
, IWalkAndTalk
, IWalkAndScale
, IWalkNeverSwitch
, ISetVisible
, IDoNothing
{
	/**   @name Start with dialog only stuff */
	//@{
	IDialogChain them(String speech);
	IDialogChain us(String speech);
	IDialogChain branchNormal(int branchId, final boolean isOkToAdd, String text);
	IDialogChain branchNormal(int branchId, String text) ;
	IDialogChain branchSticky(int branchId, String text) ;
	IDialogChain branchSticky(int nativ2, boolean b, String string);
	//@}
	
	/**   @name include a bunch of methods also found on the scene chains.... */
	//@{
	@Override IDialogChain setValue(Object key, int value) ;
	@Override IDialogChain setInventoryItemVisible(int redNote, boolean b);
	@Override IDialogChain setAnimationAsObjectInitial(String atid);
	@Override IDialogChain sleep(int milliseconds);
	//@}

	/**   @name ..and a whole heap of dialog specific exits */
	//@{
	IDialogChainEnd endDialogTree();
	IDialogChainEnd chainTo(int branchId);
	IDialogChainEnd switchTo(String string, int entrySegment);
	IDialogChainEnd branchTheObligatoryExit(String string);
	IDialogChainEnd walkAlwaysSwitch(Point p, String sceneName, int entrySegment);
	IDialogChainEnd walkAlwaysSwitch(double x, double y, String sceneName,	int entrySegment);
	//@}
	
	@Override IDialogChain setAnimationAsObjectCurrent(String atid);
	@Override IDialogChain playSound(String sid);
	@Override IDialogChain playSoundNonBlocking(String sid);
}
