package com.github.a2g.core.interfaces.nongame;

import com.github.a2g.core.interfaces.game.handlers.IOnPreEntry;
import com.github.a2g.core.interfaces.game.scene.ConstantsForAPI;
import com.github.a2g.core.objectmodel.AutoplayCommand;

public interface ISolution
extends ConstantsForAPI
{
	AutoplayCommand getNext(int i);
	void onPreEntry(IOnPreEntry api);
}
