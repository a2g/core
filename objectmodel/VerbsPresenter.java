/*
 * Copyright 2012 Anthony Cassidy
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.a2g.core.objectmodel;


import com.google.gwt.event.shared.EventBus;
import com.github.a2g.core.interfaces.HostingPanelAPI;
import com.github.a2g.core.interfaces.InternalAPI;
import com.github.a2g.core.interfaces.MasterPresenterHostAPI;
import com.github.a2g.core.interfaces.VerbCollectionCallbackAPI;
import com.github.a2g.core.interfaces.VerbsPanelAPI;
import com.github.a2g.core.primitive.ColorEnum;


public class VerbsPresenter implements VerbCollectionCallbackAPI
{
    private Verbs theVerbs;
    private VerbsPanelAPI view;

    public VerbsPresenter(final HostingPanelAPI panel, EventBus bus, MasterPresenterHostAPI parent, InternalAPI api) 
    {
        this.theVerbs = new Verbs(this);
        this.view = api.getFactory().createVerbsPanel(ColorEnum.Purple, ColorEnum.Black);
        panel.setThing(view);
        this.view.setVerbs(theVerbs);
        
    }

	public void clear() 
	{
		// doesn't change.
		
	}
	
	public Verbs getVerbsModel()
	{
		return theVerbs;
	}

	public VerbsPanelAPI getView() {
		return view;
	}

	@Override
	public void update() 
	{
		view.update();
	}
	
}
