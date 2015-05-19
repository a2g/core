package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class AutoplayCommand 
{
	int verb;
	int obj1;
	int obj2;
	String str;
	double double1;
	double double2;
	AutoplayCommand parent;

	public static AutoplayCommand start()
	{
		return new AutoplayCommand(null, -1, 1,1, "start", 0.0,0.0);
	}
	
	public AutoplayCommand(AutoplayCommand parent, int verb, int obj1, int obj2, String str,double d1, double d2)
	{
		this.parent = parent;
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.str = str;
		this.double1 = d1;
		this.double2 = d2;
	}
	
	public AutoplayCommand run(int verb, int obj1, int obj2)
	{
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,obj2,"voo",0.0,0.0);
		return a;
	}
	
	public AutoplayCommand run(int verb, short obj1, short obj2) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,obj2,"voo",0.0,0.0);
		return a;
	}
	
	public AutoplayCommand run(int verb, int obj1, short obj2) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,obj2,"voo",0.0,0.0);
		return a;
	}

	public AutoplayCommand run(int verb, int obj1) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,1,"vo",0.0,0.0);
		return a;
	
	}

	
	public AutoplayCommand run(String level)
	{
		AutoplayCommand a = new AutoplayCommand(this, ConstantsForAPI.SWITCH,1,1,level,0.0,0.0);
		return a;
	}
	
	public AutoplayCommand run(int verb, double x, double y)
	{
		AutoplayCommand a = new AutoplayCommand(this, verb,1,1,"vdd",x,y);
		return a;
	}


	public AutoplayCommand run(int verb, int obj, double x, double y) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj,1,"vodd",x,y);
		return a;
	}
	
	public AutoplayCommand run(int verb) {
		AutoplayCommand a = new AutoplayCommand(this, verb,1,1,"v",0.0,0.0);
		return a;
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
	
	public AutoplayCommand getParent()
	{ 
		return parent;
	}


}
