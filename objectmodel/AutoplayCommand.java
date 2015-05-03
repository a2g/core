package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class AutoplayCommand {

	 int verb;
	 int obj1;
	 int obj2;
	 String str;
	private int branch;
	 
	public AutoplayCommand(int verb, int obj1, int obj2)
	{
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	public AutoplayCommand(int verb, int obj1) {
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = 1;
	}
	public AutoplayCommand(int verb) {
		this.verb = verb;
		this.obj1 = 1;
		this.obj2 = 1;
	}
	public AutoplayCommand(String level)
	{
		this.verb = ConstantsForAPI.SWITCH;
		this.str = level;
		this.obj1 = 1;
		this.obj2 = 1;
	}
	public AutoplayCommand(int branchId, String text)
	{
		this.verb = ConstantsForAPI.DIALOG;
		this.str = text;
		this.branch = branchId;
	}
	
	
	int getVerb(){ return verb;}
	int getObj1(){ return obj1;}
	int getObj2(){ return obj2;}
	int getBranch(){ return branch;}

	public String getString() {
		return str;
	}
}