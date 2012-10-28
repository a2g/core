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

package com.github.a2g.core.objectmodel;


import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.MergeSceneAndStartAPI;
import com.google.gwt.event.shared.EventBus;


public class LoaderPresenter 
{
	private Loaders loaders;
	private LoaderPanelAPI view;
	int current;
	int total;
	private String name;

	public LoaderPresenter(final HostingPanelAPI panel, EventBus bus, InternalAPI api, MergeSceneAndStartAPI master, MasterPresenterHostAPI parent) 
	{
		this.loaders = new Loaders(master);
		this.name = "";
		this.view = api.getFactory().createLoaderPanel();
		panel.setThing(view);
		this.current = 0;
		this.total = 0;
	}

	void incrementProgress()
	{
		current = current +1;
		view.update(current, total, name);
	}

	void setTotal(int total)
	{
		this.total = total;
		this.current = 0;
		view.update(current, total, name);
	}

	public void setName(String string) {
		name = string;
	}

	public void clear() {
		name = "";
		total = 1;
		current =1;

	}

	public void setPixelSize(int width, int height) 
	{
		this.view.setSize(width , height);

	}

	public Loaders getLoaders()
	{
		return loaders;
	}
	
}
