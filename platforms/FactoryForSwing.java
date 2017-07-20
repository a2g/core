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

import javax.swing.JOptionPane;

import com.github.a2g.core.interfaces.internal.IBaseActionFromSystemAnimation;
import com.github.a2g.core.interfaces.internal.IBoundaryCalculator;
import com.github.a2g.core.interfaces.internal.ICommandLinePanelFromCommandLinePresenter;
import com.github.a2g.core.interfaces.internal.IDialogTreePanelFromDialogTreePresenter;
import com.github.a2g.core.interfaces.internal.IFactory;
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
import com.github.a2g.core.interfaces.internal.IAnimation;
import com.github.a2g.core.interfaces.internal.ITimer;
import com.github.a2g.core.interfaces.internal.ITitleCardPanelFromTitleCardPresenter;
import com.github.a2g.core.interfaces.internal.IVerbsPanelFromVerbsPresenter;
import com.github.a2g.core.interfaces.internal.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.objectmodel.BoundaryCalculator;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.platforms.swing.CommandLinePanelForSwing;
import com.github.a2g.core.platforms.swing.DialogTreePanelForSwing;
import com.github.a2g.core.platforms.swing.InventoryPanelForSwing;
import com.github.a2g.core.platforms.swing.LoaderPanelForSwing;
import com.github.a2g.core.platforms.swing.MasterPanelForSwing;
import com.github.a2g.core.platforms.swing.ScenePanelForSwing;
import com.github.a2g.core.platforms.swing.SoundForSwing;
import com.github.a2g.core.platforms.swing.AnimationForSwing;
import com.github.a2g.core.platforms.swing.TimerForSwing;
import com.github.a2g.core.platforms.swing.TitleCardPanelForSwing;
import com.github.a2g.core.platforms.swing.VerbsPanelForSwing;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class FactoryForSwing implements IFactory {

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForSwing(EventBus bus, MasterPresenter master) {
		this.bus = bus;
		this.master = master;
	}

	@Override
	public IAnimation createAnimation(
			IBaseActionFromSystemAnimation callbacks ) {
		return new AnimationForSwing(callbacks);
	}
	
	@Override
	public ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new CommandLinePanelForSwing(fore, back, roll);
	}

	@Override
	public IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			IMasterPresenterFromDialogTreeMouse master, ColorEnum fore, ColorEnum back, ColorEnum roll) {
		return new DialogTreePanelForSwing(master, fore, back, roll);
	}
	
	@Override
	public IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover) {
		return new InventoryPanelForSwing(bus, api, fore, back);
	}
	
	@Override
	public ILoaderPanelFromLoaderPresenter createLoaderPanel(
			final IMasterPresenterFromLoaderMouse api, ColorEnum fore,
			ColorEnum back) {
		return new LoaderPanelForSwing(api, fore, back);
	}

	@Override
	public IMasterPanelFromMasterPresenter createMasterPanel(int width,
			int height, ColorEnum back) {
		return new MasterPanelForSwing(width, height, back);
	}

	@Override
	public IScenePanelFromScenePresenter createScenePanel(IScenePresenterFromScenePanel scenePres) {
		return new ScenePanelForSwing(bus, scenePres,
				master.getCommandLinePresenter());
	}

	@Override
	public ISound createSound(String url) {
		return new SoundForSwing(url);
	}

	@Override
	public ITimer createTimer(IMasterPresenterFromTimer cbs) {
		return new TimerForSwing(cbs);
	}
	
	@Override
	public ITitleCardPanelFromTitleCardPresenter createTitleCardPanel(
			ColorEnum fore, ColorEnum back) {
		return new TitleCardPanelForSwing(fore, back);
	}

	@Override
	public IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back) {
		return new VerbsPanelForSwing(api, fore, back);
	}

	void alert(String text) {
		JOptionPane.showMessageDialog(null, "alert", text,
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public IBoundaryCalculator createBoundaryCalculator(
			IScenePresenterFromBoundaryCalculator callbacks) {
		return new BoundaryCalculator(callbacks);
	}

}
