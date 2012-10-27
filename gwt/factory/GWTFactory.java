package com.github.a2g.core.gwt.factory;

import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.PopupPanelAPI;
import com.github.a2g.core.interfaces.ScenePanelAPI;
import com.github.a2g.core.interfaces.SystemAnimationAPI;
import com.github.a2g.core.interfaces.SystemAnimationCallbackAPI;
import com.github.a2g.core.gwt.factory.SystemTimer;
import com.github.a2g.core.interfaces.SystemTimerAPI;
import com.github.a2g.core.interfaces.SystemTimerCallbackAPI;
import com.github.a2g.core.interfaces.TitleCardPanelAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.objectmodel.CommandLinePanel;
import com.github.a2g.core.objectmodel.DialogTreePanel;
import com.github.a2g.core.objectmodel.InventoryPanel;
import com.github.a2g.core.objectmodel.LoadingPanel;
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
	public CommandLinePanelAPI createCommandLinePanel() {
		return new CommandLinePanel();
	}

	@Override
	public DialogTreePanelAPI createDialogTreePanel() {
		return new DialogTreePanel();
	}

	@Override
	public InventoryPanelAPI createInventoryPanel() {
		return new InventoryPanel(master);
	}

	@Override
	public LoaderPanelAPI createLoaderPanel() {
		return new LoadingPanel(master);
	}

	@Override
	public MasterPanelAPI createMasterPanel() {
		return new MasterPanel(host);
	}

	@Override
	public ScenePanelAPI createScenePanel() {
		return new ScenePanel(bus, master);
	}

	@Override
	public TitleCardPanelAPI createTitleCardPanel() 
	{
		return new TitleCardPanel(master);
	}

	

	@Override
	public PopupPanelAPI createPopupPanel(String string, ColorEnum color) 
	{
		return new PopupPanel(string, color);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel() 
	{
		return new VerbsPanel(bus, master);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks) {
		return new SystemAnimation(callbacks);
	}
	@Override
	public SystemTimerAPI createSystemTimer(SystemTimerCallbackAPI cbs) {
		return new SystemTimer(cbs);
	}
	
}
