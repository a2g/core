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

package com.github.a2g.core.gwt.factory;

import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.gwt.factory.GWTTimer;
import com.github.a2g.core.interfaces.TimerAPI;
import com.github.a2g.core.interfaces.TimerCallbackAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.objectmodel.CommandLinePanel;
import com.github.a2g.core.objectmodel.DialogTreePanel;
import com.github.a2g.core.objectmodel.InventoryPanel;
import com.github.a2g.core.objectmodel.LoaderPanel;
import com.github.a2g.core.objectmodel.MasterPanel;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.PopupPanel;
import com.github.a2g.core.objectmodel.ScenePanel;
import com.github.a2g.core.objectmodel.TitleCardPanel;
import com.github.a2g.core.objectmodel.VerbsPanel;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;

public class GWTFactory
implements FactoryAPI
{

	private EventBus bus;
	private MasterPresenter master;
	private MasterPresenterHostAPI host;

	public GWTFactory(EventBus bus, MasterPresenter master, MasterPresenterHostAPI host)
	{
		this.bus = bus;
		this.master = master;
		this.host = host;
	}

	@Override
	public CommandLinePanelAPI createCommandLinePanel(ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		return new CommandLinePanel(fore, back, roll);
	}

	@Override
	public DialogTreePanelAPI createDialogTreePanel(EventBus bus, ColorEnum foreground, ColorEnum background, ColorEnum rollover)
	{
		return new DialogTreePanel(bus, foreground, background, rollover);
	}

	@Override
	public InventoryPanelAPI createInventoryPanel(MouseToInventoryPresenterAPI api, ColorEnum fore, ColorEnum back) {
		return new InventoryPanel(master, bus, api, fore, back);
	}

	@Override
	public LoaderPanelAPI createLoaderPanel(ColorEnum fore, ColorEnum back) {
		return new LoaderPanel(master, fore,back);
	}

	@Override
	public MasterPanelAPI createMasterPanel(int width, int height, ColorEnum back) {
		return new MasterPanel(host, back);
	}

	@Override
	public ScenePanelAPI createScenePanel() {
		return new ScenePanel(bus, master);
	}

	@Override
	public TitleCardPanelAPI createTitleCardPanel(ColorEnum foreground, ColorEnum background)
	{
		return new TitleCardPanel(master, foreground, background);
	}



	@Override
	public PopupPanelAPI createPopupPanel(String string, ColorEnum color, BaseAction toCancel)
	{
		return new PopupPanel(string, color, toCancel);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel(ColorEnum foreground, ColorEnum background)
	{
		return new VerbsPanel(bus, master, foreground, background);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks) {
		return new GWTSystemAnimation(callbacks);
	}
	@Override
	public TimerAPI createSystemTimer(TimerCallbackAPI cbs) {
		return new GWTTimer(cbs);
	}

	public void alert(String text)
	{
		Window.alert(text);
	}

}
