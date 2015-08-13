package com.github.a2g.core.objectmodel;

public class InsertionPointCalculator {

	private Integer[] theListOfIndexesToInsertAt;

	public InsertionPointCalculator()
	{
		clear();
	}

	public int getIndexToInsertAt(int numberPrefix) {
		int i = theListOfIndexesToInsertAt[numberPrefix];
		return i==0?0:i-1;
	}

	public void updateTheListOfIndexesToInsertAt(int numberPrefix) {
		for (int i = numberPrefix; i <= 99; i++) {
			theListOfIndexesToInsertAt[i]++;
		}
	}

	public void clear() {
		this.theListOfIndexesToInsertAt = new Integer[100];
		for (int i = 0; i < 100; i++)
			theListOfIndexesToInsertAt[i] = new Integer(0);
	}
}
