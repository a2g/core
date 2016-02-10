package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainableAction;

public interface IChainRootForDialog {
	DialogChainableAction answer(String speech);
	DialogChainableAction ask(String speech);
	DialogChainableAction branchNormal(int branchId, final boolean isOkToAdd, String text);
	DialogChainableAction branchNormal(int branchId, String text) ;

	DialogChainableAction setValue(String key, int value) ;
	DialogChainableAction setInventoryVisible(int redNote, boolean b);
	DialogChainableAction sleep(int milliseconds);
	DialogChainableAction setInitialAnimation(String atid);

	DialogChainEndAction  endDialogTree();
	DialogChainEndAction  chainTo(int branchId);
	DialogChainEndAction  switchTo(String string, int arrivalSegment);
	DialogChainableAction branchSticky(int branchId, String text) ;
	DialogChainEndAction branchSticky(int nativ2, boolean b, String string);
	DialogChainEndAction branchTheObligatoryExit(String string);
}
