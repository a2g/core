package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IDoSwitchBeyondGateIfSetUp;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IIsInANoGoZone;

public interface IScenePresenterFromSwitchPerformer 
extends
IGetOtidByCode
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IIsInANoGoZone
, IDoSwitchBeyondGateIfSetUp
  
{

}
