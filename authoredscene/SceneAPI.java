/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredscene;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.authoredscene.OnFillLoadListAPIImpl.LoadKickStarter;
import com.github.a2g.core.objectmodel.SentenceUnit;

public interface SceneAPI  extends ConstantsForAPI
{
    public LoadKickStarter onFillLoadList(OnFillLoadListAPIImpl api);

    public void onPreEntry(OnPreEntryAPI api);
    
    public BaseAction onEntry(OnEntryAPI api, BaseAction ba);
 
    public void onEveryFrame(OnEveryFrameAPI api);
    
    public BaseAction onDoCommand(OnDoCommandAPI api, BaseAction ba, int verb, SentenceUnit objectA, SentenceUnit objectB, double x, double y);  
    
    public BaseDialogTreeAction onDialogTree(OnDialogTreeAPI api, BaseAction ba, int branch);
    
}
