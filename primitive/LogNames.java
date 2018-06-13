package com.github.a2g.core.primitive;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum LogNames 
{
	MOVE_PERFORMER(true), // = "MOVE";
	LOADING(true),// = "Loading";
	LOADING_ANIM(true),// = "LOADING_ANIM";
	RUNNER(true), // = "RUNNER";
	RUNNER_REFCOUNT(true),// = "RUNNER_REFCOUNT";
	COMMANDS_VIA_GUI(true),// = "COMMAND_MANUAL";
	COMMANDS_AUTOPLAY(true),// = "COMMAND_AUTOPLAY";
	HTML5CANVAS(true),// = "HTML5CANVAS";
	ADDANIMATION(true),// = "ADDING_ANIM_TO_SOC_MAP";
	IMAGE_DUMP(true),// = "IMAGE_DUMP";
	ACTIONS_AS_THEY_ARE_EXECUTED(true),// = "ACTIONS_EXECUTED";
	KEY_ENTRY(true),// = "KEY_ENTRY";
	ACTIONS_FLATTENED_B4_EXECUTION(true),// = "ACTIONS_FLATTENED_B4_EXECUTION";
	WALK_MULTI_PERFORMER(true),// = "WALK_MULTI_PERFORMER";
	MULTIWALKER(true),// = "A";
	B(true),// = "B";
	C(true),// = "C";
	D(true),// = "D";
	GETBYATID(true),// = "ANIMATIONS_AS_THEY_ARE_INDEXED";
	LOADNEXT(true),// = "LOADNEXT";
	ADD_SCENEOBJECT(true),
	MERGEWITHSCENE(false), 
	HEAD_RECT_PROBLEMS(true),
    SET_STATE_OF_POPUP(true);

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

			}
		}
	}
};
