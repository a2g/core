package com.github.a2g.core.interfaces.performer;

import com.github.a2g.core.interfaces.internal.IScenePresenterFromScalePerformer;

public interface IScalePerformer {

	void setStartScaleForScaler(double startScale);

	void setEndScaleForScaler(double endScale);

	void setSceneForScaler(IScenePresenterFromScalePerformer scene);

	void runForScaler();
	
	void onCompleteForScaler();

	void onUpdateForScaler(double progress);

}
