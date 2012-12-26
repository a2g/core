package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public interface FactoryAPI {
	CommandLinePanelAPI createCommandLinePanel(ColorEnum foreground, ColorEnum background, ColorEnum rollover);
	InventoryPanelAPI createInventoryPanel(MouseToInventoryPresenterAPI api, ColorEnum purple, ColorEnum black);
	LoaderPanelAPI createLoaderPanel(ColorEnum fore, ColorEnum back);
	ScenePanelAPI createScenePanel();
	TitleCardPanelAPI createTitleCardPanel(ColorEnum foreground, ColorEnum background);
	VerbsPanelAPI createVerbsPanel(ColorEnum foreground, ColorEnum background);
	PopupPanelAPI createPopupPanel(String string, ColorEnum color);
	SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI cbs);
	TimerAPI createSystemTimer(TimerCallbackAPI cbs);
	MasterPanelAPI createMasterPanel(int width, int height, ColorEnum color);
	DialogTreePanelAPI createDialogTreePanel(EventBus bus, ColorEnum foreground, ColorEnum background, ColorEnum rollover);
}
