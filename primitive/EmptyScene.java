package com.github.a2g.core.primitive;


import com.github.a2g.core.interfaces.IGameScene;
import com.github.a2g.core.interfaces.IOnQueueResourcesImpl;
import com.github.a2g.core.interfaces.IOnQueueResourcesImpl.LoadKickStarter;




public class EmptyScene implements IGameScene
{
	@Override
	public LoadKickStarter onFillLoadList(IOnQueueResourcesImpl api) {
		// TODO Auto-generated method stub
		return null;
	}
}
