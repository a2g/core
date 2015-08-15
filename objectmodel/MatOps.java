package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.ActivateDialogTreeModeAction;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.action.DialogChainRootAction;
import com.github.a2g.core.action.DialogChainableAction;
import com.github.a2g.core.interfaces.IGameScene;

public class MatOps {
	
	static public ChainRootAction createChainRootAction() {
		ChainRootAction npa = new ChainRootAction();
		return npa;
	}

	static public DialogChainRootAction createDialogChainRootAction() {
		DialogChainRootAction npa = new DialogChainRootAction();
		return npa;
	}
	 
}
