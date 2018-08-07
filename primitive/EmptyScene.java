package com.github.a2g.core.primitive;


import com.github.a2g.core.interfaces.game.handlers.IOnEnqueueResources;
import com.github.a2g.core.interfaces.game.scene.IGameSceneLoader;
import com.github.a2g.core.interfaces.game.scene.ILoadKickStarter;






public class EmptyScene implements IGameSceneLoader
{
	@Override
	public ILoadKickStarter onEnqueueResources(IOnEnqueueResources api) {
		return null;
	}
}
