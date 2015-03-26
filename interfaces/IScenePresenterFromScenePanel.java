package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetAtidOfCurrentAnimationByOtid;
import com.github.a2g.core.interfaces.action.IGetBoundingRectByFrameAndAtid;
import com.github.a2g.core.interfaces.action.IGetCurrentFrameByOtid;
import com.github.a2g.core.interfaces.action.IGetOtidByIndex;
import com.github.a2g.core.interfaces.action.IGetSceneObjectCount;
import com.github.a2g.core.interfaces.action.IGetVisibleByOtid;
import com.github.a2g.core.interfaces.action.IGetXByOtid;
import com.github.a2g.core.interfaces.action.IGetYByOtid;

public interface IScenePresenterFromScenePanel
		extends
		IScenePresenterFromSceneMouseOver,
		IScenePresenterFromSceneTouch,
		IScenePresenterFromJavaPopupPanel// no java stuff here, it just needs
											// getView
		, IGetSceneObjectCount, IGetOtidByIndex, IGetVisibleByOtid,
		IGetAtidOfCurrentAnimationByOtid, IGetCurrentFrameByOtid,
		IGetBoundingRectByFrameAndAtid, IGetXByOtid, IGetYByOtid {
}
