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



public class IOnFillLoadListImpl
{
	IOnFillLoadList implementation;
	public IOnFillLoadListImpl(IOnFillLoadList impl)
	{
		this.implementation = impl;
	}

	public void addEssential(ILoad imageBundle)
	{
		this.implementation.addEssential(imageBundle);
	}


	public void setScenePixelSize(int width, int height)
	{
		this.implementation.setScenePixelSize(width,height);
	}
	public LoadKickStarter createLoadKickStarter()
	{
		this.implementation.kickStartLoading();
		return new LoadKickStarter();
	}

	public class LoadKickStarter
	{
		private LoadKickStarter(){};
	}

	public IGameScene getSceneByName(String string) {
		return this.implementation.getSceneByName(string);
	}

	public void setValue(Object string, int value)
	{
		this.implementation.setValue(string, value);
	}

	public void clearAllLoadedLoads() {
		this.implementation.clearAllLoadedLoads();
	}

	public void setContinueAfterLoad(boolean isContinueImmediately) {
		this.implementation.setContinueAfterLoad(isContinueImmediately);
		
	}
}


