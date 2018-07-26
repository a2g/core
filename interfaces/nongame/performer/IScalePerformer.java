package com.github.a2g.core.interfaces.nongame.performer;

import com.github.a2g.core.interfaces.nongame.presenter.IScenePresenterFromScalePerformer;

public interface IScalePerformer {

	void setStartScaleForScaler(double startScale);

	void setEndScaleForScaler(double endScale);

	void setSceneForScaler(IScenePresenterFromScalePerformer scene);

	void runForScaler();
	
	void onCompleteForScaler();

	void onUpdateForScaler(double progress);

}
