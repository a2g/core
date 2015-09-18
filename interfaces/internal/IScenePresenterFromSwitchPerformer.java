package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.action.IDoSwitchIfBeyondGate;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.action.IIsInANoGoZone;

public interface IScenePresenterFromSwitchPerformer
extends
IGetOtidByCode
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IIsInANoGoZone
, IDoSwitchIfBeyondGate

{

}
