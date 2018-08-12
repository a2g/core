package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetDisplayNameByItid;

public interface IInventoryPresenterFromInventoryMouseOver extends
IGetDisplayNameByItid 
{
 void setMouseOver(double x, double y);
}
