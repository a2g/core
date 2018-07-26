package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.game.singles.ISwitchToScene;
import com.github.a2g.core.interfaces.nongame.IFactory;

public interface IMasterPresenterFromScenePresenter
extends ISwitchToScene
{

	IFactory getFactory();

	void shareWinning(String token);

	void setValue(String name, int i);


}
