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

package com.github.a2g.core.interfaces.game.scene;

import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
import com.github.a2g.core.interfaces.game.chainables.IDialogChainEnd;
import com.github.a2g.core.interfaces.game.chainables.IDialogChainRoot;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainEnd;
import com.github.a2g.core.interfaces.game.chainables.ISceneChainRoot;
import com.github.a2g.core.interfaces.game.handlers.IOnDialogTree;
import com.github.a2g.core.interfaces.game.handlers.IOnDoCommand;
import com.github.a2g.core.interfaces.game.handlers.IOnEnqueueResources;
import com.github.a2g.core.interfaces.game.handlers.IOnEntry;
import com.github.a2g.core.interfaces.game.handlers.IOnEveryFrame;
import com.github.a2g.core.interfaces.game.handlers.IOnPreEntry;
import com.github.a2g.core.interfaces.nongame.platform.IBundleLoader;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformResourceBundle;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.A2gException;

/**
 * 
 * @author Admin
 *
 */
public class OnEnqueueResourcesDummyImpl implements IOnEnqueueResources
{
	MasterPresenter implementation;
 
	// constructor 
	public OnEnqueueResourcesDummyImpl(MasterPresenter impl) {
		this.implementation = impl;
	}
	
	// create methods
	@Override
	public ILoadKickStarter createReturnObject(IGameScene scene) { 
		return new LoadKickStarter(scene);
	}

	// queue methods
	@Override
	public void enqueueEntireBundleLoader(IBundleLoader imageBundle) {
		this.implementation.enqueueEntireBundleLoader(imageBundle);
	}

	@Override
	public void enqueueSingleBundle(IPlatformResourceBundle bundle) {
		this.implementation.enqueueSingleBundle(bundle);
	}
	
	@Override
	public IGameScene enqueueSharedSceneAndReturnScene(IExtendsGameSceneLoader loader) {
		IGameScene scene = this.implementation.enqueueSharedSceneAndReturnScene(loader);
		return scene;
	}

	@Override
	public void enqueueMP3ForASoundObject(String name, String location) {
		this.implementation.queueMP3ForASoundObject(name, location);
	}

	// utlity methods
	public IBaseChain setValue(Object string, int value) {
		this.implementation.setValue(string, value);
		return null;
	}
 
	public void clearAllLoadedLoads() {
		this.implementation.clearAllLoadedLoads();
	}

	// making the creation methods (above) as the only way to create the
	// class with the private constructor (below)
	// and then making the user return an object of that class
	// is a neat trick that enables us to ensure that a particular method
	// is called just before the method returns
	// Note: we only need to do this because GWT's split points mean the return
	// value is ignored.
	// Note: a lazy person could return null, but that's asking for trouble.
	public class LoadKickStarter implements  ILoadKickStarter
	{
		IGameScene wrapped;

		private LoadKickStarter(IGameScene wrapped) {
			this.wrapped = wrapped;
		}

		@Override
		public void onPreEntry(IOnPreEntry api) throws A2gException {
			wrapped.onPreEntry(api);
		}

		@Override
		public ISceneChainEnd onEntry(IOnEntry api, ISceneChainRoot ba) throws A2gException {
			return wrapped.onEntry(api, ba);
		}

		@Override
		public void onEveryFrame(IOnEveryFrame api) throws A2gException {
			wrapped.onEveryFrame(api);
		}

		@Override
		public ISceneChainEnd onDoCommand(IOnDoCommand api, ISceneChainRoot ba, int verb, SentenceItem itemA,
				SentenceItem itemB, double x, double y) throws A2gException {
			return wrapped.onDoCommand(api, ba, verb, itemA, itemB, x, y);
		}

		@Override
		public IDialogChainEnd onDialogTree(IOnDialogTree api, IDialogChainRoot ba, int branch)
				throws A2gException {
			return wrapped.onDialogTree(api, ba, branch);
		};

		@Override
		public String toString() {
			return wrapped.toString();
		};

	}
 

}
