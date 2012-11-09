package com.github.a2g.core.interfaces;

import com.github.a2g.core.objectmodel.SentenceUnit;

public interface CommandLineCallbackAPI {

	FactoryAPI getFactory();

	boolean isInDialogTreeMode();

	void doCommand(int verbAsCode, int verbAsVerbEnumeration,
			SentenceUnit sentenceA, SentenceUnit sentenceB, double x, double y);

}
