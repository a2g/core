package com.github.a2g.core.interfaces;

import com.github.a2g.core.interfaces.action.IGetSoundDurationByStid;
import com.github.a2g.core.interfaces.action.IPlaySoundByStid;
import com.github.a2g.core.interfaces.action.IStopSoundByStid;

public interface IMasterPresenterFromSoundAction
extends		
IPlaySoundByStid
, IGetSoundDurationByStid
, IStopSoundByStid
{

	
}
