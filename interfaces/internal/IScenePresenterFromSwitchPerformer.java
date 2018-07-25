package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.internal.IDoSwitchIfBeyondGate;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleXByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetBaseMiddleYByOtid;
import com.github.a2g.core.interfaces.methods.internal.IGetOtidByCode;
import com.github.a2g.core.interfaces.methods.internal.IIsInANoGoZone;

public interface IScenePresenterFromSwitchPerformer
extends
IGetOtidByCode
, IGetBaseMiddleXByOtid
, IGetBaseMiddleYByOtid
, IIsInANoGoZone
, IDoSwitchIfBeyondGate

{

}
