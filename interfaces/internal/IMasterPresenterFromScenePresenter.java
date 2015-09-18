package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.methods.game.ISwitchToScene;

public interface IMasterPresenterFromScenePresenter
extends ISwitchToScene
{

	IFactory getFactory();

	void shareWinning(String token);

	void setValue(String name, int i);


}
