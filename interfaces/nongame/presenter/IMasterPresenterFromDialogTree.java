package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.platform.IPlatformDialogTreePanel;
import com.github.a2g.core.primitive.ColorEnum;
import com.google.gwt.event.shared.EventBus;

/**  
 * 
 * @author Admin
 *
 */
public interface IMasterPresenterFromDialogTree
extends IMasterPresenterFromDialogTreeMouse
{
	IPlatformDialogTreePanel createDialogTreePanel(EventBus bus,
			ColorEnum textNormal, ColorEnum textBackground, ColorEnum textHighlight);
}
