package com.github.a2g.core.primitive;


import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.IOnDialogTree;
import com.github.a2g.core.interfaces.IOnDoCommand;
import com.github.a2g.core.interfaces.IOnEntry;
import com.github.a2g.core.interfaces.IOnEveryFrame;
import com.github.a2g.core.interfaces.IOnFillLoadListImpl;
import com.github.a2g.core.interfaces.IOnFillLoadListImpl.LoadKickStarter;
import com.github.a2g.core.interfaces.IOnPreEntry;
import com.github.a2g.core.interfaces.internal.IChainRootForDialog;
import com.github.a2g.core.interfaces.internal.IChainRootForScene;
import com.github.a2g.core.objectmodel.SentenceItem;




public class EmptyScene implements IGameScene
{
	@Override
	public LoadKickStarter onFillLoadList(IOnFillLoadListImpl api) {
		// TODO Auto-generated method stub
		return null;
	}
}
