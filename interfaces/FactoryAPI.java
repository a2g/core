package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;

public interface FactoryAPI {
	CommandLinePanelAPI createCommandLinePanel();
	DialogTreePanelAPI createDialogTreePanel();
	InventoryPanelAPI createInventoryPanel(MouseToInventoryPresenterAPI api);
	LoaderPanelAPI createLoaderPanel();
	ScenePanelAPI createScenePanel();
	TitleCardPanelAPI createTitleCardPanel();
	VerbsPanelAPI createVerbsPanel();
	PopupPanelAPI createPopupPanel(String string, ColorEnum color);
	SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI cbs);
	TimerAPI createSystemTimer(TimerCallbackAPI cbs);
	MasterPanelAPI createMasterPanel(int width, int height);
	

}
