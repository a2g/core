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

package com.github.a2g.core.platforms.html5;

import com.github.a2g.core.interfaces.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.interfaces.ISound;
import com.github.a2g.core.interfaces.ICommandLinePanelFromCommandLinePresenter;
import com.github.a2g.core.interfaces.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IInventoryPanelFromInventoryPresenter;
import com.github.a2g.core.interfaces.IHostFromMasterPresenter;
import com.github.a2g.core.interfaces.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.ILoaderPanelFromLoaderPresenter;
import com.github.a2g.core.interfaces.IMasterPanelFromMasterPresenter;
import com.github.a2g.core.interfaces.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.interfaces.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.interfaces.IScenePanelFromScenePresenter;
import com.github.a2g.core.interfaces.ISystemAnimation;
import com.github.a2g.core.interfaces.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.ITimer;
import com.github.a2g.core.interfaces.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.ITitleCardPanelFromTitleCardPresenter;
import com.github.a2g.core.interfaces.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.CommandLinePanel;
import com.github.a2g.core.objectmodel.DialogTreePanel;
import com.github.a2g.core.objectmodel.InventoryPanel;
import com.github.a2g.core.objectmodel.LoaderPanel;
import com.github.a2g.core.objectmodel.MasterPanel;
import com.github.a2g.core.objectmodel.TitleCardPanel;
import com.github.a2g.core.objectmodel.VerbsPanel;
import com.github.a2g.core.platforms.html4.SoundForHtml4;
import com.github.a2g.core.platforms.html4.SystemAnimationForHtml4;
import com.github.a2g.core.platforms.html4.TimerForHtml4;
import com.github.a2g.core.platforms.html5.panel.ScenePanelForHtml5;
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
	public ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanel(fore, back, roll);
	}

	

	@Override
	public ILoaderPanelFromLoaderPresenter createLoaderPanel(
			final IMasterPresenterFromLoaderMouse api, ColorEnum fore,
			ColorEnum back) {
		return new LoaderPanel(api, fore, back);
	}

	@Override
	public IMasterPanelFromMasterPresenter createMasterPanel(int width,
			int height, ColorEnum back) {
		return new MasterPanel(width, height, back);
	}

	@Override
	public IScenePanelFromScenePresenter createScenePanel(IScenePresenterFromScenePanel scenePres) {
		return new ScenePanelForHtml5(bus, master.getScenePresenter(),
				master.getCommandLinePresenter());
	}

	@Override
	public ITitleCardPanelFromTitleCardPresenter createTitleCardPanel(
			ColorEnum fore, ColorEnum back) {
		return new TitleCardPanel(fore, back);
	}

	@Override
	public IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back) {
		return new VerbsPanel(api, fore, back);
	}

	@Override
	public ISystemAnimation createSystemAnimation(
			IBaseActionFromSystemAnimation callbacks, boolean isLinear) {
		return new SystemAnimationForHtml4(callbacks, isLinear);
	}

	@Override
	public ITimer createSystemTimer(IMasterPresenterFromTimer cbs) {
		return new TimerForHtml4(cbs);
	}

	/*
	 * void alert(String text) { JOptionPane.showMessageDialog(null, "alert",
	 * text,JOptionPane.ERROR_MESSAGE); }
	 */

	@Override
	public IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanel(api, fore, back, rollover);
	}
	
	@Override
	public ISound createSound(String url) {
		return new SoundForHtml4(url);
	}

	@Override
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			IMasterPresenterFromDialogTreeMouse master, ColorEnum fore,
			ColorEnum back, ColorEnum roll) {
		return new DialogTreePanel(master, fore, back, roll);
		
	}

}
