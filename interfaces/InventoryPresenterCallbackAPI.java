package com.github.a2g.core.interfaces;

public interface InventoryPresenterCallbackAPI 
{

	FactoryAPI getFactory();

	int getValue(String string);
	
	void onClickInventory(); 
	
	void onMouseOverInventory(String displayName, String textualId, int code); 

}
