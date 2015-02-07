package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public interface FactoryAPI {
	CommandLinePanelAPI createCommandLinePanel(ColorEnum foreground, ColorEnum background, ColorEnum rollover);
	InventoryPanelAPI createInventoryPanel(MouseToInventoryPresenterAPI api, ColorEnum fore, ColorEnum back, ColorEnum rollover);
	ScenePanelAPI createScenePanel();
	TitleCardPanelAPI createTitleCardPanel(ColorEnum fore, ColorEnum back);
	VerbsPanelAPI createVerbsPanel(MouseToVerbsPresenterAPI api, ColorEnum fore, ColorEnum back);
	SystemAnimationAPI createSystemAnimation(SystemAnimationCallbackAPI cbs, boolean isLinear);
	TimerAPI createSystemTimer(TimerCallbackAPI cbs);
	MasterPanelAPI createMasterPanel(int width, int height, ColorEnum color);
	DialogTreePanelAPI createDialogTreePanel(EventBus bus, ColorEnum fore, ColorEnum back, ColorEnum rollover);
	LoaderPanelAPI createLoaderPanel(MouseToLoaderPresenterAPI api,
			ColorEnum fore, ColorEnum back);
	PopupPanelAPI createPopupPanel(InternalAPI api, int sceneWidth,
			int sceneHeight);
}
