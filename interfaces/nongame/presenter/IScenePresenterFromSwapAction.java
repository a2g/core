package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.nongame.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.nongame.action.ISetVisibleByOtid;

public interface IScenePresenterFromSwapAction extends IGetVisibleByOtid,
ISetVisibleByOtid, IGetOtidByCode {

}
