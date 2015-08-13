package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.game.ISwitchToScene;

public interface IMasterPresenterFromScenePresenter
extends ISwitchToScene
{

	IFactory getFactory();

	void shareWinning(String token);

	void setValue(String name, int i);


}
