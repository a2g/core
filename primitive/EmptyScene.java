package com.github.a2g.core.primitive;


import com.github.a2g.core.interfaces.IGameSceneLoader;
import com.github.a2g.core.interfaces.ILoadKickStarter;
import com.github.a2g.core.interfaces.game.handlers.IOnEnqueueResources;






public class EmptyScene implements IGameSceneLoader
{
	@Override
	public ILoadKickStarter onEnqueueResources(IOnEnqueueResources api) {
		return null;
	}
}
