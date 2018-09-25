package com.github.a2g.core.primitive;

public class IsInventory {

	static public boolean isInventory(int code) {
		final int FIRST_INV = STARTING_ODD_INVENTORY_CODE.STARTING_ODD_INVENTORY_CODE;
	
		boolean isInventory = code >= FIRST_INV ;
		return isInventory;
	}

}
