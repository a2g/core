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

package com.github.a2g.core.canvas.factory;


import javax.swing.JOptionPane;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MouseToVerbsPresenterAPI;
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;
import com.github.a2g.core.swing.factory.SwingSystemAnimation;
import com.github.a2g.core.swing.factory.SwingTimer;
import com.github.a2g.core.interfaces.TimerAPI;
import com.github.a2g.core.interfaces.TimerCallbackAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.swing.panel.CommandLinePanel;
import com.github.a2g.core.swing.panel.DialogTreePanel;
import com.github.a2g.core.swing.panel.InventoryPanel;
import com.github.a2g.core.swing.panel.LoadingPanel;
import com.github.a2g.core.swing.panel.MasterPanel;
import com.github.a2g.core.swing.panel.PopupPanel;
import com.github.a2g.core.swing.panel.ScenePanel;
import com.github.a2g.core.swing.panel.TitleCardPanel;
import com.github.a2g.core.swing.panel.VerbsPanel;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class SwingFactory
implements FactoryAPI
{

	private EventBus bus;
	private MasterPresenter master;

	public SwingFactory(EventBus bus, MasterPresenter master)
	{
		this.bus = bus;
		this.master = master;
	}
	@Override
	public CommandLinePanelAPI createCommandLinePanel(ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		return new CommandLinePanel(fore,back,roll);
	}

	@Override
	public DialogTreePanelAPI createDialogTreePanel(EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		return new DialogTreePanel(bus, fore, back, roll);
	}


	@Override
	public LoaderPanelAPI createLoaderPanel(ColorEnum fore, ColorEnum back) {
		return new LoadingPanel(master, fore, back);
	}

	@Override
	public MasterPanelAPI createMasterPanel(int width,int height,ColorEnum back) {
		return new MasterPanel(width,height,back);
	}

	@Override
	public ScenePanelAPI createScenePanel() {
		return new ScenePanel(bus, master);
	}

	@Override
	public TitleCardPanelAPI createTitleCardPanel(ColorEnum fore, ColorEnum back)
	{
		return new TitleCardPanel(master, fore, back);
	}



	@Override
	public PopupPanelAPI createPopupPanel(String string, ColorEnum color, BaseAction toCancel)
	{
		return new PopupPanel(string, color, toCancel);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel(MouseToVerbsPresenterAPI api, ColorEnum fore, ColorEnum back)
	{
		return new VerbsPanel(master, api, fore, back);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks, boolean isLinear)
	{
		return new SwingSystemAnimation(callbacks, isLinear);
	}
	@Override
	public TimerAPI createSystemTimer(TimerCallbackAPI cbs) {
		return new SwingTimer(cbs);
	}

	void alert(String text)
	{
		JOptionPane.showMessageDialog(null, "alert", text,JOptionPane.ERROR_MESSAGE);
	}
	@Override
	public InventoryPanelAPI createInventoryPanel(
			MouseToInventoryPresenterAPI api, ColorEnum fore, ColorEnum back)
	{
		return new InventoryPanel(bus, master, api, fore, back);
	}

}
