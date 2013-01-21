package com.github.a2g.core.interfaces;

import com.github.a2g.core.objectmodel.SentenceItem;

public interface CommandLineCallbackAPI {

	FactoryAPI getFactory();

	void doCommand(int verbAsCode, int verbAsVerbEnumeration,
			SentenceItem sentenceA, SentenceItem sentenceB, double x, double y);
	boolean isCommandLineActive();
}
