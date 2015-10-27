/*
p- * Copyright 2012 Anthony Cassidy
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

package com.github.a2g.core.platforms.html4;

import com.github.a2g.core.objectmodel.BoundaryCalculator;
import com.github.a2g.core.objectmodel.CommandLinePanel;
import com.github.a2g.core.objectmodel.DialogTreePanel;
import com.github.a2g.core.objectmodel.InventoryPanel;
import com.github.a2g.core.objectmodel.LoaderPanel;
import com.github.a2g.core.objectmodel.MasterPanel;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.ScenePanel;
import com.github.a2g.core.objectmodel.TitleCardPanel;
import com.github.a2g.core.objectmodel.VerbsPanel;
import com.github.a2g.core.platforms.html4.TimerForHtml4;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
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
import com.github.a2g.core.interfaces.internal.ISound;
import com.github.a2g.core.interfaces.internal.ISystemAnimation;
import com.github.a2g.core.interfaces.internal.ITimer;
import com.github.a2g.core.interfaces.internal.ITitleCardPanelFromTitleCardPresenter;
import com.github.a2g.core.interfaces.internal.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.interfaces.internal.IVerbsPresenterFromVerbsPanel;

public class FactoryForHtml4
implements IFactory {

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForHtml4(EventBus bus, MasterPresenter master,
			IHostFromMasterPresenter api) {
		this.bus = bus;
		this.master = master;
		api = null;
	}

	@Override
	public ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanel(fore, back, roll);
	}



	@Override
	public IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanel(api, fore, back, rollover);
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
		return new ScenePanel(bus, master.getScenePresenter());
	}

	@Override
	public ITitleCardPanelFromTitleCardPresenter createTitleCardPanel(
			ColorEnum foreground, ColorEnum background) {
		return new TitleCardPanel(foreground, background);
	}

	@Override
	public IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum foreground,
			ColorEnum background) {
		return new VerbsPanel(api, foreground,
				background);
	}

	@Override
	public ISystemAnimation createSystemAnimation(
			IBaseActionFromSystemAnimation callbacks) {
		return new SystemAnimationForHtml4(callbacks);
	}

	@Override
	public ITimer createSystemTimer(IMasterPresenterFromTimer cbs) {
		return new TimerForHtml4(cbs);
	}

	public void alert(String text) {
		Window.alert(text);
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

	@Override
	public IBoundaryCalculator createBoundaryCalculator(
			IScenePresenterFromBoundaryCalculator callbacks) {
		return new BoundaryCalculator(callbacks);
	}

}
