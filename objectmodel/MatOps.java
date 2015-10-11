package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.DialogChainRootAction;

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
