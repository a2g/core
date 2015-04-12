package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.action.ISetVisibleByOtid;

public interface IScenePresenterFromHideAllAction extends IGetSceneObjectCount,
IGetOtidByIndex, ISetVisibleByOtid {
}
