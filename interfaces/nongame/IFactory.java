package com.github.a2g.core.interfaces.nongame;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformAnimation;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformCommandLinePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformDialogTreePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformInventoryPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformLoaderPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformMasterPanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformScenePanel;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformSound;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformTimer;
import com.github.a2g.core.interfaces.nongame.platform.IPlatformVerbsPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IInventoryPresenterFromInventoryPanel;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromDialogTreeMouse;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromLoaderMouse;
import com.github.a2g.core.interfaces.nongame.presenter.IMasterPresenterFromTimer;
import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromScenePanel;
import com.github.a2g.core.interfaces.nongame.presenter.IVerbsPresenterFromVerbsPanel;
import com.github.a2g.core.primitive.ColorEnum;

public interface IFactory {
	IPlatformCommandLinePanel 	createCommandLinePanel(ColorEnum foreground, ColorEnum background, ColorEnum rollover);
	IPlatformInventoryPanel 	createInventoryPanel(IInventoryPresenterFromInventoryPanel api, ColorEnum fore,	ColorEnum back, ColorEnum rollover);
	IPlatformScenePanel 		createScenePanel(IScenePresenterFromScenePanel scenePres);
	IPlatformVerbsPanel 		createVerbsPanel(IVerbsPresenterFromVerbsPanel api, ColorEnum fore, ColorEnum back);
	IPlatformAnimation 			createAnimation(IBaseActionFromSystemAnimation cbs);
	IPlatformTimer 				createTimer(IMasterPresenterFromTimer cbs);
	IPlatformMasterPanel 		createMasterPanel(int width, int height, ColorEnum color);
	IPlatformDialogTreePanel 	createDialogTreePanel(	IMasterPresenterFromDialogTreeMouse master, ColorEnum fore, ColorEnum back, ColorEnum roll);
	IPlatformLoaderPanel 		createLoaderPanel(IMasterPresenterFromLoaderMouse api, ColorEnum fore, ColorEnum back);
	IPlatformSound 				createSound(String url);
}
