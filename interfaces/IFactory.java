package com.github.a2g.core.interfaces;

import com.github.a2g.core.primitive.ColorEnum;

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

	IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(
			IMasterPresenterFromDialogTreeMouse master, ColorEnum fore, ColorEnum back, ColorEnum roll);
		

	ILoaderPanelFromLoaderPresenter createLoaderPanel(
			IMasterPresenterFromLoaderMouse api, ColorEnum fore, ColorEnum back);
	
	ISound createSound(String url);
}
