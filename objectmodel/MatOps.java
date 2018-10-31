package com.github.a2g.core.objectmodel;

import com.github.a2g.core.action.DialogChainRootAction;
import com.github.a2g.core.chain.DialogChain;
import com.github.a2g.core.chain.SceneChainRoot;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainRoot;

public class MatOps {
	
	static public ISceneChainRoot createChainRootAction() {
		SceneChainRoot npa = new SceneChainRoot();
		return npa;
	}

	static public DialogChainRootAction createDialogChainRootAction() {
		DialogChainRootAction npa = new DialogChainRootAction();
		return npa;
	}
	 
}
