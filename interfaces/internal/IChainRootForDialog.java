package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainableAction;
import com.github.a2g.core.primitive.PointF;

public interface IChainRootForDialog {
	DialogChainableAction them(String speech);
	DialogChainableAction us(String speech);
	DialogChainableAction branchNormal(int branchId, final boolean isOkToAdd, String text);
	DialogChainableAction branchNormal(int branchId, String text) ;
	DialogChainableAction branchSticky(int branchId, String text) ;
	DialogChainableAction branchSticky(int nativ2, boolean b, String string);
	
	DialogChainableAction setValue(String key, int value) ;
	DialogChainableAction setInventoryVisible(int redNote, boolean b);
	DialogChainableAction sleep(int milliseconds);
	DialogChainableAction setAnimationAsObjectInitial(String atid);

	DialogChainEndAction endDialogTree();
	DialogChainEndAction chainTo(int branchId);
	DialogChainEndAction switchTo(String string, int arrivalSegment);
	DialogChainEndAction branchTheObligatoryExit(String string);
	DialogChainEndAction walkAlwaysSwitch(PointF p, String sceneName,
			int arrivalSegment);
	DialogChainEndAction walkAlwaysSwitch(double x, double y, String sceneName,
			int arrivalSegment);
}
