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

package com.github.a2g.core.platforms.java;

import javax.swing.JOptionPane;

import com.github.a2g.core.interfaces.ISound;
import com.github.a2g.core.interfaces.ICommandLinePanelFromCommandLinePresenter;
import com.github.a2g.core.interfaces.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.IFactory;
import com.github.a2g.core.interfaces.IInventoryPanelFromInventoryPresenter;
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
import com.github.a2g.core.platforms.java.SystemAnimationForJava;
import com.github.a2g.core.platforms.java.TimerForJava;
import com.github.a2g.core.platforms.java.panel.CommandLinePanelForJava;
import com.github.a2g.core.platforms.java.panel.DialogTreePanelForJava;
import com.github.a2g.core.platforms.java.panel.InventoryPanelForJava;
import com.github.a2g.core.platforms.java.panel.LoaderPanelForJava;
import com.github.a2g.core.platforms.java.panel.MasterPanelForJava;
import com.github.a2g.core.platforms.java.panel.ScenePanelForJava;
import com.github.a2g.core.platforms.java.panel.TitleCardPanelForJava;
import com.github.a2g.core.platforms.java.panel.VerbsPanelForJava;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class FactoryForJava implements IFactory {

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForJava(EventBus bus, MasterPresenter master) {
		this.bus = bus;
		this.master = master;
	}

	@Override
	public ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanelForJava(fore, back, roll);
	}

	@Override
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new DialogTreePanelForJava(bus, fore, back, roll);
	}

	@Override
	public ILoaderPanelFromLoaderPresenter createLoaderPanel(
			final IMasterPresenterFromLoaderMouse api, ColorEnum fore,
			ColorEnum back) {
		return new LoaderPanelForJava(api, fore, back);
	}

	@Override
	public IMasterPanelFromMasterPresenter createMasterPanel(int width,
			int height, ColorEnum back) {
		return new MasterPanelForJava(width, height, back);
	}

	@Override
	public IScenePanelFromScenePresenter createScenePanel(IScenePresenterFromScenePanel scenePres) {
		return new ScenePanelForJava(bus, scenePres,
				master.getCommandLinePresenter());
	}

	@Override
	public ITitleCardPanelFromTitleCardPresenter createTitleCardPanel(
			ColorEnum fore, ColorEnum back) {
		return new TitleCardPanelForJava(fore, back);
	}

	@Override
	public IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back) {
		return new VerbsPanelForJava(api, fore, back);
	}

	@Override
	public ISystemAnimation createSystemAnimation(
			IBaseActionFromSystemAnimation callbacks, boolean isLinear) {
		return new SystemAnimationForJava(callbacks, isLinear);
	}

	@Override
	public ITimer createSystemTimer(IMasterPresenterFromTimer cbs) {
		return new TimerForJava(cbs);
	}

	void alert(String text) {
		JOptionPane.showMessageDialog(null, "alert", text,
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanelForJava(bus, api, fore, back);
	}

	@Override
	public ISound createAudio(String url) {
		return new SoundForJava(url);
	}

}
