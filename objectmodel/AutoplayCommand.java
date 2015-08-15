package com.github.a2g.core.objectmodel;

import com.github.a2g.core.interfaces.ConstantsForAPI;

public class AutoplayCommand implements ConstantsForAPI
{
	private int verb;
	private int int1;
	private int int2;
	private String str;
	private double double1;
	private double double2;
	private AutoplayCommand parent;

	@Override public String toString() {
		String s = new String();
		s+=" verb="+getVerbAsString();
		s+=" obj1="+int1;
		s+=" obj2="+int2;
		s+=" str="+str;
		s+=" dou1="+double1;
		s+=" dou2="+double2;
		return s;
	}

	public static AutoplayCommand beginChain()
	{
		return new AutoplayCommand(null, -1, 1,1, "start", 0.0,0.0);
	}

	public AutoplayCommand(AutoplayCommand parent, int verb, int obj1, int obj2, String str,double d1, double d2)
	{
		this.parent = parent;
		this.verb = verb;
		this.int1 = obj1;
		this.int2 = obj2;
		this.str = str;
		this.double1 = d1;
		this.double2 = d2;
	}

	String getVerbAsString(){
		switch(verb)
		{
		case WALK: return "WALK";
		case TALK: return "TALK";
		case EXAMINE: return "EXAMINE";
		case GRAB: return "GRAB";
		case CUT: return "CUT";
		case SWING: return "SWING";
		case TURN_ON: return "TURN_ON";
		case USE: return "USE";
		case PUSH: return "PUSH";
		case PULL: return "PULL";
		case THROW: return "THROW";
		case EAT: return "EAT";
		case SLEEP: return "SLEEP";
		case SWITCH: return "SWITCH";
		case DIALOG: return "DIALOG";
		}
		return "error, see AutoplayCommand::getVerbAsString";
	}

	int getInt1(){ return int1;}
	int getInt2(){ return int2;}
	int getBranch(){ return int1;}
	double getDouble1(){ return double1;}
	double getDouble2(){ return double2;}

	public String getString() {
		return str;
	}

	public AutoplayCommand getParent()
	{
		return parent;
	}

	public int getVerb() {
		return verb;
	}

	public AutoplayCommand use(short obj1, short obj2)
	{
		return generic(ConstantsForAPI.USE, obj1, obj2);
	}

	public AutoplayCommand walk(short obj, double x, double y) {
		AutoplayCommand a = new AutoplayCommand(this, ConstantsForAPI.WALK,obj,1,"vodd",x,y);
		return a;
	}

	public AutoplayCommand walk(double x, double y) {
		AutoplayCommand a = new AutoplayCommand(this, ConstantsForAPI.WALK,1,1,"vdd",x,y);
		return a;
	}

	public AutoplayCommand switchTo(String sceneName) {
		this.verb = ConstantsForAPI.SWITCH;
		AutoplayCommand a = new AutoplayCommand(this, verb,1,1,sceneName,0.0,0.0);
		return a;
	}

	public AutoplayCommand grab(short obj) {
		return generic(ConstantsForAPI.GRAB, obj);
	}

	public AutoplayCommand talk(short obj) {
		return generic(ConstantsForAPI.TALK, obj);
	}

	public AutoplayCommand walk(short obj) {
		return generic(ConstantsForAPI.WALK, obj);
	}

	public AutoplayCommand dodialog(int branchId) {
		return generic(ConstantsForAPI.DIALOG, branchId);
	}

	public AutoplayCommand sleep(int milliseconds) {
		return generic(ConstantsForAPI.SLEEP, milliseconds);
	}

	public AutoplayCommand sleep() {
		return generic(ConstantsForAPI.SLEEP, 1000);
	}

	public AutoplayCommand examine(short obj) {
		return generic(ConstantsForAPI.EXAMINE, obj);
	}

	public AutoplayCommand push(short obj) {
		return generic(ConstantsForAPI.PUSH, obj);
	}

	public AutoplayCommand pull(short obj) {
		return generic(ConstantsForAPI.PULL, obj);
	}

	public AutoplayCommand throwAatB(short objA, short objB) {
		return generic(ConstantsForAPI.THROW, objA, objB);
	}

	public AutoplayCommand swing(short obj) {
		return generic(ConstantsForAPI.SWING, obj);
	}

	public AutoplayCommand turnOn(short obj) {
		return generic(ConstantsForAPI.TURN_ON, obj);
	}

	//private

	// eg use, throw
	private AutoplayCommand generic(int verb, short obj1, short obj2) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,obj2,"voo",0.0,0.0);
		return a;
	}

	// eg sleep, dialog
	private AutoplayCommand generic(int verb, int obj1) {
		AutoplayCommand a = new AutoplayCommand(this, verb,obj1,1,"vo",0.0,0.0);
		return a;
	}
}
