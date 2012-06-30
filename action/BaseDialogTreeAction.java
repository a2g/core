/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.action;


import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.authoredroom.InternalAPI;


public abstract class BaseDialogTreeAction extends BaseAction {

    protected BaseDialogTreeAction(BaseAction parent, InternalAPI api) {
        super(parent, api);

    }	

}

