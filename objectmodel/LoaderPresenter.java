/*
inve * Copyright 2012 Anthony Cassidy
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

import com.github.a2g.core.interfaces.nongame.IFactory;
import com.github.a2g.core.interfaces.nongame.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.nongame.IHostingPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformLoaderPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromLoader;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.primitive.GuiConstants;
import com.google.gwt.event.shared.EventBus;

public class LoaderPresenter implements IMasterPresenterFromLoaderMouse {
	private Loader loader;
	private IPlatformLoaderPanel view;
	int current;
	int total;
	private String name;
	private IMasterPresenterFromLoader master;
	private boolean isContinueAfterLoad;

	public LoaderPresenter(final IHostingPanel panel, EventBus bus,
			IMasterPresenterFromLoader master, IHostFromMasterPresenter parent,
			IFactory factory) {
		this.isContinueAfterLoad = true;
		this.loader = new Loader(master);
		this.name = "";
		this.view = factory.createLoaderPanel(this, GuiConstants.TEXT_NORMAL,
				GuiConstants.BACKGROUND_FILL);
		panel.setThing(view);
		this.current = 0;
		this.total = 0;
		this.master = master;
	}

	void incrementProgress() {
		current = current + 1;
		view.update(current, total, name);
	}

	void setTotal(int total) {
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
		current = 1;

	}

	public void setScenePixelSize(int width, int height) {
		this.view.setScenePixelSize(width, height);

	}

	public Loader getLoaders() {
		return loader;
	}

	public IPlatformLoaderPanel getView() {
		return view;
	}

	public void clearAllLoadedLoads() {
		loader.clearAllLoadedLoads();

	}

	@Override
	public void restartReloading() {
		master.restartReloading();
	}

	public void onLoadingComplete() {
		if (isContinueAfterLoad) {
			master.startScene();
		} else {
			view.enableClickToContinue();
		}
	}

	@Override
	public void clickToContinue() {
		master.startScene();
	}

	public void setContinueAfterLoad(boolean isIgnore) {
		this.isContinueAfterLoad = true;

	}

}
