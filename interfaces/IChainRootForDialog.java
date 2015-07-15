package com.github.a2g.core.interfaces;

import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainableAction;

public interface IChainRootForDialog {
	DialogChainableAction talk(String anim, String speech);
	DialogChainableAction talk(String speech);
	DialogChainableAction branch(int branchId, final boolean isOkToAdd, String text);
	DialogChainableAction branch(int branchId, String text) ;
	DialogChainableAction branchSticky(int branchId, String text) ;
	
	DialogChainableAction setValue(String key, int value) ;
	DialogChainableAction setInventoryVisible(int redNote, boolean b);
	DialogChainableAction sleep(int milliseconds);
	DialogChainableAction setInitialAnimation(String atid);
	
	DialogChainEndAction  endDialogTree();
	DialogChainEndAction  chainTo(int branchId);
	DialogChainEndAction  switchTo(String string);
}
