package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IDoSwitchIfBeyondGate;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IIsInANoGoZone;

public interface IScenePresenterFromSwitchPerformer
extends
IGetOtidByCode
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IIsInANoGoZone
, IDoSwitchIfBeyondGate

{

}
