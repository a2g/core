package com.github.a2g.core.primitive;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum LogNames 
{
	MOVE_PERFORMER(true), // = "MOVE";
	LOADING(false),// = "Loading";
	LOADING_ANIM(false),// = "LOADING_ANIM";
	RUNNER(false), // = "RUNNER";
	RUNNER_REFCOUNT(false),// = "RUNNER_REFCOUNT";
	COMMANDS_VIA_GUI(false),// = "COMMAND_MANUAL";
	COMMANDS_AUTOPLAY(false),// = "COMMAND_AUTOPLAY";
	HTML5CANVAS(false),// = "HTML5CANVAS";
	ADDING_ANIM_TO_SOC_MAP(true),// = "ADDING_ANIM_TO_SOC_MAP";
	IMAGE_DUMP(false),// = "IMAGE_DUMP";
	ACTIONS_AS_THEY_ARE_EXECUTED(false),// = "ACTIONS_EXECUTED";
	KEY_ENTRY(false),// = "KEY_ENTRY";
	ACTIONS_FLATTENED_B4_EXECUTION(false),// = "ACTIONS_FLATTENED_B4_EXECUTION";
	WALK_MULTI_PERFORMER(false),// = "WALK_MULTI_PERFORMER";
	MULTIWALKER(false),// = "A";
	B(false),// = "B";
	C(false),// = "C";
	D(false),// = "D";
	ANIMATIONS_AS_THEY_ARE_GOT(true),// = "ANIMATIONS_AS_THEY_ARE_INDEXED";
	LOADNEXT(true);// = "LOADNEXT";

	public final boolean isOn;
	LogNames(boolean isOn)
	{
		this.isOn = isOn;
	}

	public static void registerLoggers() {
		for (LogNames d : LogNames.values()) {
			if(d.isOn)
			{
				final Logger log = Logger.getLogger(d.toString());
				log.setLevel(Level.ALL);

				//ConsoleHandler handler = new ConsoleHandler();
				//handler.setLevel(Level.ALL);
				//Formatter f = new SimpleFormatter();
				//handler.setFormatter(f);
				//log.addHandler(handler);
			}
		}
	}
};
