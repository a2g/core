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

@Override
public void onPreEntry(IOnPreEntry api) {
	// TODO Auto-generated method stub
	
}

@Override
public ChainEndAction onEntry(IOnEntry api, IChainRootForScene ba) {
	return ba.showTitleCard("EmptyScene");
}

@Override
public void onEveryFrame(IOnEveryFrame api) {
	// TODO Auto-generated method stub
	
}

@Override
public ChainEndAction onDoCommand(IOnDoCommand api, IChainRootForScene ba,
		int verb, SentenceItem itemA, SentenceItem itemB, double x, double y) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public DialogChainEndAction onDialogTree(IOnDialogTree api,
		IChainRootForDialog ba, int branch) {
	// TODO Auto-generated method stub
	return null;
}
}
