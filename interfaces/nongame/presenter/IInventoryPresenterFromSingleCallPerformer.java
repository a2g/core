package com.github.a2g.core.interfaces.nongame.presenter;

public interface IInventoryPresenterFromSingleCallPerformer {

	void setVisibleByItid(String itid, boolean isVisible);

	String getItidByCode(int icode);

}
