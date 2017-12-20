/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.interfaces;

import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.interfaces.internal.IChainRootForDialog;
import com.github.a2g.core.interfaces.internal.IChainRootForScene;
import com.github.a2g.core.interfaces.internal.ILoad;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;
import com.scenes.mission.B.SharedBrightHeroB;

/**  
 * 
 * @author Admin
 *
 */
public class IOnQueueResourcesImpl {
	IOnQueueResources implementation;

	public IOnQueueResourcesImpl(IOnQueueResources impl) {
		this.implementation = impl;
	}

	public void queueMixinResources(ILoad imageBundle) {
		this.implementation.queueSingleBundle(imageBundle);
	}

	public void queueMP3ForASoundObject(String name, String location)
	{
		this.implementation.queueMP3ForASoundObject(name, location);
	}

	public IAuxGameScene queueMixinStuffAndReturnScene(IMixin loader, IOnQueueResourcesImpl api) {
		IAuxGameScene scene =  this.implementation.queueMixinStuffAndReturnScene(loader, api);
		return scene;
	}
	
	public LoadKickStarter createMainReturnObject(IAuxGameScene scene) {
		this.implementation.setSceneAsActiveAndKickStartLoading(scene);
		return new LoadKickStarter(null);
	}
	
	public LoadKickStarter createSharedSceneReturnObject(IAuxGameScene scene) {
		return new LoadKickStarter(scene);
	}

	public void setValue(Object string, int value) {
		this.implementation.setValue(string, value);
	}

	public void setContinueAfterLoad(boolean isContinueImmediately) {
		this.implementation.setContinueAfterLoad(isContinueImmediately);
	}
	
	public void clearAllLoadedLoads() {
		this.implementation.clearAllLoadedLoads();
	}

	// making the creation methods (above) as the only way to create the 
	// class with the private constructor (below)
	// and then making the user return an object of that class
	// is a neat trick that enables us to ensure that a particular method
	// is called just before the method returns
	// Note: we only need to do this because GWT's split points mean the return value is ignored.
	// Note: a lazy person could return null, but that's asking for trouble.
	public class LoadKickStarter implements IAuxGameScene{
		IAuxGameScene wrapped;
		private LoadKickStarter(IAuxGameScene wrapped) {
			this.wrapped = wrapped;
		}
		@Override
		public void onPreEntry(IOnPreEntry api) {
			wrapped.onPreEntry(api);
		}
		@Override
		public ChainEndAction onEntry(IOnEntry api, IChainRootForScene ba) throws A2gException {
			return wrapped.onEntry(api, ba);
		}
		@Override
		public void onEveryFrame(IOnEveryFrame api) {
			wrapped.onEveryFrame(api);
		}
		@Override
		public ChainEndAction onDoCommand(IOnDoCommand api, IChainRootForScene ba, int verb, SentenceItem itemA,
				SentenceItem itemB, double x, double y) throws A2gException 
		{
			return wrapped.onDoCommand(api, ba, verb, itemA, itemB, x, y);
		}
		@Override
		public DialogChainEndAction onDialogTree(IOnDialogTree api, IChainRootForDialog ba, int branch)
				throws A2gException 
		{
			return wrapped.onDialogTree(api, ba, branch);
		};
		
		@Override
		public String toString()
		{
			return wrapped.toString();
		};

	}
}
