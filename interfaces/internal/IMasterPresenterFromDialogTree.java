package com.github.a2g.core.interfaces.internal;

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
	IDialogTreePanelFromDialogTreePresenter createDialogTreePanel(EventBus bus,
			ColorEnum purple, ColorEnum black, ColorEnum red);
}
