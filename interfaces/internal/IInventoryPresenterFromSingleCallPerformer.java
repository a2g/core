package com.github.a2g.core.interfaces.internal;

public interface IInventoryPresenterFromSingleCallPerformer {

	void setVisibleByItid(String itid, boolean isVisible);

	String getItidByCode(int icode);

}
