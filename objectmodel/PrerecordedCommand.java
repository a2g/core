package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class PrerecordedCommand {

	 int verb;
	 int obj1;
	 int obj2;
	 String str;
	 
	public PrerecordedCommand(int verb, int obj1, int obj2)
	{
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	public PrerecordedCommand(int verb, int obj1) {
		this.verb = verb;
		this.obj1 = obj1;
		this.obj2 = 1;
	}
	public PrerecordedCommand(int verb) {
		this.verb = verb;
		this.obj1 = 1;
		this.obj2 = 1;
	}
	public PrerecordedCommand(String level)
	{
		this.verb = ConstantsForAPI.SWITCH;
		this.str = level;
		this.obj1 = 1;
		this.obj2 = 1;
	}
	
	
	int getVerb(){ return verb;}
	int getObj1(){ return obj1;}
	int getObj2(){ return obj2;}

	public String getString() {
		return str;
	}
}
