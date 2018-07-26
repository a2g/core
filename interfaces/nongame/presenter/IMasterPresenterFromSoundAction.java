package com.github.a2g.core.interfaces.nongame.presenter;

import com.github.a2g.core.interfaces.nongame.action.IGetSoundDurationByStid;
import com.github.a2g.core.interfaces.nongame.action.IPlaySoundByStid;
import com.github.a2g.core.interfaces.nongame.action.IStopSoundByStid;

public interface IMasterPresenterFromSoundAction
extends
IPlaySoundByStid
, IGetSoundDurationByStid
, IStopSoundByStid
{


}
