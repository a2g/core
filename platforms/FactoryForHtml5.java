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

package com.github.a2g.core.platforms;

import com.github.a2g.core.interfaces.nongame.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.nongame.IFactory;
import com.github.a2g.core.interfaces.nongame.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformAnimation;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformCommandLinePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformDialogTreePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformInventoryPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformLoaderPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformScenePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformSound;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformTimer;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformVerbsPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.platforms.html4.CommandLinePanelForHtml4;
import com.github.a2g.core.platforms.html4.DialogTreePanelForHtml4;
import com.github.a2g.core.platforms.html4.InventoryPanelForHtml4;
import com.github.a2g.core.platforms.html4.LoaderPanelHtml4;
import com.github.a2g.core.platforms.html4.MasterPanelForHtml4;
import com.github.a2g.core.platforms.html4.PlatformSoundForHtml4;
import com.github.a2g.core.platforms.html4.PlatformAnimationForHtml4;
import com.github.a2g.core.platforms.html4.PlatformTimerForHtml4;
import com.github.a2g.core.platforms.html4.VerbsPanelHtml4;
import com.github.a2g.core.platforms.html5.ScenePanelForHtml5;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class FactoryForHtml5 implements IFactory {

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForHtml5(EventBus bus, MasterPresenter master,
			IHostFromMasterPresenter api) {
		this.bus = bus;
		this.master = master;
	}


	@Override
	public IPlatformAnimation createAnimation(
			IBaseActionFromSystemAnimation callbacks) {
		return new PlatformAnimationForHtml4(callbacks);
	}
	                                                                                                                                           
	@Override
	public IPlatformCommandLinePanel createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanelForHtml4(fore, back, roll);
	}
	
	

	@Override
	public IPlatformDialogTreePanel createDialogTreePanel(
			IMasterPresenterFromDialogTreeMouse master, ColorEnum fore,
			ColorEnum back, ColorEnum roll) {
		return new DialogTreePanelForHtml4(master, fore, back, roll);

	}

	@Override
	public IPlatformInventoryPanel createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanelForHtml4(api, fore, back, rollover);
	}

	@Override
	public IPlatformLoaderPanel createLoaderPanel(
			final IMasterPresenterFromLoaderMouse api, ColorEnum fore,
			ColorEnum back) {
		return new LoaderPanelHtml4(api, fore, back);
	}

	@Override
	public IPlatformMasterPanel createMasterPanel(int width,
			int height, ColorEnum back) {
		return new MasterPanelForHtml4(width, height, back);
	}

	@Override
	public IPlatformScenePanel createScenePanel(IScenePresenterFromScenePanel scenePres) {
		return new ScenePanelForHtml5(bus, scenePres,
				master.getCommandLinePresenter());
	}



	@Override
	public IPlatformSound createSound(String url) {
		return new PlatformSoundForHtml4(url);
	}

	@Override
	public IPlatformTimer createTimer(IMasterPresenterFromTimer cbs) {
		return new PlatformTimerForHtml4(cbs);
	}
 

	@Override
	public IPlatformVerbsPanel createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back) {
		return new VerbsPanelHtml4(api, fore, back);
	}


	/*
	 * void alert(String text) { JOptionPane.showMessageDialog(null, "alert",
	 * text,JOptionPane.ERROR_MESSAGE); }
	 */


	



}
