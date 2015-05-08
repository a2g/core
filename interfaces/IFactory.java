package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

public interface IFactory {
	ICommandLinePanelFromCommandLinePresenter createCommandLinePanel(
			ColorEnum foreground, ColorEnum background, ColorEnum rollover);

	IInventoryPanelFromInventoryPresenter createInventoryPanel(
			IInventoryPresenterFromInventoryPanel api, ColorEnum fore,
			ColorEnum back, ColorEnum rollover);

	IScenePanelFromScenePresenter createScenePanel(IScenePresenterFromScenePanel scenePres);

	ITitleCardPanelFromTitleCardPresenter createTitleCardPanel(ColorEnum fore,
			ColorEnum back);

	IVerbsPanelFromVerbsPresenter createVerbsPanel(
			IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back);

	ISystemAnimation createSystemAnimation(IBaseActionFromSystemAnimation cbs,
			boolean isLinear);

	ITimer createSystemTimer(IMasterPresenterFromTimer cbs);

	IMasterPanelFromMasterPresenter createMasterPanel(int width, int height,
			ColorEnum color);

	IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(EventBus bus,
			ColorEnum fore, ColorEnum back, ColorEnum rollover);

	ILoaderPanelFromLoaderPresenter createLoaderPanel(
			IMasterPresenterFromLoaderMouse api, ColorEnum fore, ColorEnum back);
	
	ISound createAudio(String url);
}
