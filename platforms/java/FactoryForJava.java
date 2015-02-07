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

import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MouseToLoaderPresenterAPI;
import com.github.a2g.core.interfaces.MouseToVerbsPresenterAPI;
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;
import com.github.a2g.core.interfaces.TimerAPI;
import com.github.a2g.core.interfaces.TimerCallbackAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.platforms.java.SystemAnimationForJava;
import com.github.a2g.core.platforms.java.TimerForJava;
import com.github.a2g.core.platforms.java.panel.CommandLinePanelForJava;
import com.github.a2g.core.platforms.java.panel.DialogTreePanelForJava;
import com.github.a2g.core.platforms.java.panel.InventoryPanelForJava;
import com.github.a2g.core.platforms.java.panel.LoaderPanelForJava;
import com.github.a2g.core.platforms.java.panel.MasterPanelForJava;
import com.github.a2g.core.platforms.java.panel.PopupPanelForJava;
import com.github.a2g.core.platforms.java.panel.ScenePanelForJava;
import com.github.a2g.core.platforms.java.panel.TitleCardPanelForJava;
import com.github.a2g.core.platforms.java.panel.VerbsPanelForJava;
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class FactoryForJava
implements FactoryAPI
{

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForJava(EventBus bus, MasterPresenter master)
	{
		this.bus = bus;
		this.master = master;
	}
	@Override
	public CommandLinePanelAPI createCommandLinePanel(ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		return new CommandLinePanelForJava(fore,back,roll);
	}

	@Override
	public DialogTreePanelAPI createDialogTreePanel(EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum roll)
	{
		return new DialogTreePanelForJava(bus, fore, back, roll);
	}


	@Override
	public LoaderPanelAPI createLoaderPanel(final MouseToLoaderPresenterAPI api, ColorEnum fore, ColorEnum back) {
		return new LoaderPanelForJava(api, fore, back);
	}

	@Override
	public MasterPanelAPI createMasterPanel(int width,int height,ColorEnum back) {
		return new MasterPanelForJava(width,height,back);
	}

	@Override
	public ScenePanelAPI createScenePanel() {
		return new ScenePanelForJava(bus, master);
	}

	@Override
	public TitleCardPanelAPI createTitleCardPanel(ColorEnum fore, ColorEnum back)
	{
		return new TitleCardPanelForJava(master, fore, back);
	}



	@Override
	public PopupPanelAPI createPopupPanel(InternalAPI api, int sceneWidth, int sceneHeight)
	{
		return new PopupPanelForJava(api, sceneWidth, sceneHeight);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel(MouseToVerbsPresenterAPI api, ColorEnum fore, ColorEnum back)
	{
		return new VerbsPanelForJava(master, api, fore, back);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks, boolean isLinear)
	{
		return new SystemAnimationForJava(callbacks, isLinear);
	}
	@Override
	public TimerAPI createSystemTimer(TimerCallbackAPI cbs) {
		return new TimerForJava(cbs);
	}

	void alert(String text)
	{
		JOptionPane.showMessageDialog(null, "alert", text,JOptionPane.ERROR_MESSAGE);
	}
	@Override
	public InventoryPanelAPI createInventoryPanel(
			MouseToInventoryPresenterAPI api, ColorEnum fore, ColorEnum back, ColorEnum rollover)
	{
		return new InventoryPanelForJava(bus, master, api, fore, back);
	}

}
