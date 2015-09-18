package com.github.a2g.core.interfaces.internal;

import com.github.a2g.core.interfaces.ConstantsForAPI;
import com.github.a2g.core.interfaces.IOnPreEntry;
import com.github.a2g.core.objectmodel.AutoplayCommand;

public interface ISolution
extends ConstantsForAPI
{
	AutoplayCommand getNext(int i);
	void onPreEntry(IOnPreEntry api);
}
