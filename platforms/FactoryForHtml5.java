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

import com.github.a2g.core.interfaces.internal.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.internal.IBoundaryCalculator;
import com.github.a2g.core.interfaces.internal.ICommandLinePanelFromCommandLinePresenter;
import com.github.a2g.core.interfaces.internal.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IFactory;
import com.github.a2g.core.interfaces.internal.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.internal.IInventoryPanelFromInventoryPresenter;
import com.github.a2g.core.interfaces.internal.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.internal.ILoaderPanelFromLoaderPresenter;
import com.github.a2g.core.interfaces.internal.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.interfaces.internal.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.internal.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromBoundaryCalculator;
import com.github.a2g.core.interfaces.internal.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.internal.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.interfaces.internal.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.interfaces.platform.IPlatformAnimation;
import com.github.a2g.core.interfaces.platform.IPlatformSound;
import com.github.a2g.core.interfaces.platform.IPlatformTimer;
import com.github.a2g.core.objectmodel.BoundaryCalculator;
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
	public ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanelForHtml4(fore, back, roll);
	}
	
	

	@Override
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			IMasterPresenterFromDialogTreeMouse master, ColorEnum fore,
			ColorEnum back, ColorEnum roll) {
		return new DialogTreePanelForHtml4(master, fore, back, roll);

	}

	@Override
	public IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanelForHtml4(api, fore, back, rollover);
	}

	@Override
	public ILoaderPanelFromLoaderPresenter createLoaderPanel(
			final IMasterPresenterFromLoaderMouse api, ColorEnum fore,
			ColorEnum back) {
		return new LoaderPanelHtml4(api, fore, back);
	}

	@Override
	public IMasterPanelFromMasterPresenter createMasterPanel(int width,
			int height, ColorEnum back) {
		return new MasterPanelForHtml4(width, height, back);
	}

	@Override
	public IScenePanelFromScenePresenter createScenePanel(IScenePresenterFromScenePanel scenePres) {
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
	public IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back) {
		return new VerbsPanelHtml4(api, fore, back);
	}


	/*
	 * void alert(String text) { JOptionPane.showMessageDialog(null, "alert",
	 * text,JOptionPane.ERROR_MESSAGE); }
	 */


	

	@Override
	public IBoundaryCalculator createBoundaryCalculator(
			IScenePresenterFromBoundaryCalculator callbacks) {
		return new BoundaryCalculator(callbacks);
	}

}
