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


import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
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
import com.github.a2g.core.objectmodel.MasterPresenter;
import com.github.a2g.core.objectmodel.CommandLinePanel;
import com.github.a2g.core.objectmodel.DialogTreePanel;
import com.github.a2g.core.objectmodel.InventoryPanel;
import com.github.a2g.core.objectmodel.LoaderPanel;
import com.github.a2g.core.objectmodel.MasterPanel;
import com.github.a2g.core.objectmodel.PopupPanel;
import com.github.a2g.core.objectmodel.TitleCardPanel;
import com.github.a2g.core.objectmodel.VerbsPanel;
import com.github.a2g.core.platforms.html4.SystemAnimationForHtml4;
import com.github.a2g.core.platforms.html4.TimerForHtml4;
import com.github.a2g.core.platforms.html5.panel.ScenePanelForHtml5;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public class FactoryForHtml5
implements FactoryAPI
{

	private EventBus bus;
	private MasterPresenter master;

	public FactoryForHtml5(EventBus bus, MasterPresenter master, MasterPresenterHostAPI api)
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
	public LoaderPanelAPI createLoaderPanel(final MouseToLoaderPresenterAPI api, ColorEnum fore, ColorEnum back) {
		return new LoaderPanel(api, fore, back);
	}

	@Override
	public MasterPanelAPI createMasterPanel(int width,int height,ColorEnum back) {
		return new MasterPanel(width,height,back);
	}

	@Override
	public ScenePanelAPI createScenePanel() {
		return new ScenePanelForHtml5(bus, master);
	}

	@Override
	public TitleCardPanelAPI createTitleCardPanel(ColorEnum fore, ColorEnum back)
	{
		return new TitleCardPanel(master, fore, back);
	}



	@Override
	public PopupPanelAPI createPopupPanel(InternalAPI api, int sceneWidth, int sceneHeight)
	{
		return new PopupPanel( sceneWidth,sceneHeight);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel(MouseToVerbsPresenterAPI api, ColorEnum fore, ColorEnum back)
	{
		return new VerbsPanel(master, api, fore, back);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks, boolean isLinear)
	{
		return new SystemAnimationForHtml4(callbacks, isLinear);
	}
	@Override
	public TimerAPI createSystemTimer(TimerCallbackAPI cbs) {
		return new TimerForHtml4(cbs);
	}

	/*void alert(String text)
	{
		JOptionPane.showMessageDialog(null, "alert", text,JOptionPane.ERROR_MESSAGE);
	}*/

	@Override
	public InventoryPanelAPI createInventoryPanel(
			MouseToInventoryPresenterAPI api, ColorEnum fore, ColorEnum back, ColorEnum rollover)
	{
		return new InventoryPanel(master, api, fore, back, rollover);
	}

}
