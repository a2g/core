package com.github.a2g.core.swing.factory;

import javax.swing.JOptionPane;

import com.github.a2g.core.interfaces.CommandLinePanelAPI;
import com.github.a2g.core.interfaces.DialogTreePanelAPI;
import com.github.a2g.core.interfaces.FactoryAPI;
import com.github.a2g.core.interfaces.InventoryPanelAPI;
import com.github.a2g.core.interfaces.MouseToInventoryPresenterAPI;
import com.github.a2g.core.interfaces.LoaderPanelAPI;
import com.github.a2g.core.interfaces.MasterPanelAPI;
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
	public PopupPanelAPI createPopupPanel(String string, ColorEnum color) 
	{
		return new PopupPanel(string, color);
	}



	@Override
	public VerbsPanelAPI createVerbsPanel(ColorEnum fore, ColorEnum back) 
	{
		return new VerbsPanel(bus, master, fore, back);
	}
	@Override
	public SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI callbacks)
	{
		return new SwingSystemAnimation(callbacks);
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
