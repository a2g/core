package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class AutoplayCommand {

	 int verb;
	 int obj1;
	 int obj2;
	 String str;
	 double double1;
	 double double2;
	 
	public AutoplayCommand(int verb, int obj1, int obj2)
	{
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	public AutoplayCommand(int verb, short obj1, short obj2) {
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	public AutoplayCommand(int verb, int obj1, short obj2) {
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public AutoplayCommand(int verb, int obj1) {
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = 1;
	}

	public AutoplayCommand(String level)
	{
		this.verb = ConstantsForAPI.SWITCH;
		this.str = level;
		this.obj1 = 1;
		this.obj2 = 1;
	}
	public AutoplayCommand(int verb, double x, double y)
	{
		this.verb = verb;
		this.double1 = x;
		this.double2 = y;
		this.obj1=1;
		this.obj2=1;
	}
	
	
	public AutoplayCommand(int verb, int object, double x, double y) {
		this.verb = verb;
		this.double1 = x;
		this.double2 = y;
		this.obj1=object;
		this.obj2=1;
	}
	int getVerb(){ return verb;}
	int getObj1(){ return obj1;}
	int getObj2(){ return obj2;}
	int getBranch(){ return obj1;}
	double getDouble1(){ return double1;}
	double getDouble2(){ return double2;}

	public String getString() {
		return str;
	}
}
