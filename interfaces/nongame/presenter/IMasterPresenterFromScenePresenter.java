package com.github.a2g.core.interfaces.nongame.presenter;
 
import com.github.a2g.core.interfaces.nongame.IFactory;
import com.github.a2g.core.interfaces.nongame.action.ISwitchToScene;

public interface IMasterPresenterFromScenePresenter
extends ISwitchToScene
{

	IFactory getFactory();

	void shareWinning(String token);

	void setValue(String name, int i);


}
