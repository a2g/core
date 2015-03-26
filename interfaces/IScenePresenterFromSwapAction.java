package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetOtidByCode;
import com.github.a2g.core.interfaces.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromSwapAction extends IGetVisibleByOtid,
		ISetVisibleByOtid, IGetOtidByCode {

}
