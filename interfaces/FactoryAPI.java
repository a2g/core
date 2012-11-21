package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public interface FactoryAPI {
	CommandLinePanelAPI createCommandLinePanel();
	InventoryPanelAPI createInventoryPanel(MouseToInventoryPresenterAPI api);
	LoaderPanelAPI createLoaderPanel();
	ScenePanelAPI createScenePanel();
	TitleCardPanelAPI createTitleCardPanel();
	VerbsPanelAPI createVerbsPanel();
	PopupPanelAPI createPopupPanel(String string, ColorEnum color);
	SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI cbs);
	TimerAPI createSystemTimer(TimerCallbackAPI cbs);
	MasterPanelAPI createMasterPanel(int width, int height);
	DialogTreePanelAPI createDialogTreePanel(EventBus bus);
	

}
