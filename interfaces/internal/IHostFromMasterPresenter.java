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

package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.IGameSceneLoader;
import com.github.a2g.core.interfaces.methods.IOnPreEntry;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.AutoplayCommand;
import com.google.gwt.event.shared.EventBus;

public interface IHostFromMasterPresenter {

	public void setValue(String name, Integer value);

	public int getValue(String name);
	
	void clearValueRegistry();

	public void instantiateSceneAndCallSetSceneBackOnTheMasterPresenter(
			String scene);

	public void setLastCommand(double x, double y, int verb, String a, String b);

	void alert(String string);

	IFactory getFactory(EventBus bus, MasterPresenter mp);

	public IGameSceneLoader getSceneViaCache(String string);

	void shareWinning(String token);

	void log(String message);

	public AutoplayCommand getNextAutoplayAction(IOnPreEntry api);

	boolean isAutoplay();

	void onFinishedAutoplay(AutoplayCommand lastCommand, String reasonForFinish);


}
