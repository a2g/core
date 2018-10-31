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

package com.github.a2g.core.chain;

import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.interfaces.game.chainables.IBaseChain;
/**
 * documentation for BaseAction
 * @author Admin
 *
 */
public class BaseChain implements IBaseChain
{
	protected IBaseChain parent;
	private BaseAction action;

	protected BaseChain(BaseChain parent) {
	    this.parent = parent;
	    this.action = null;
	} 
	
	protected BaseChain(BaseChain parent, BaseAction action) {
	    this.parent = parent;
	    this.action = action;
	} 

	public IBaseChain getParent() {
	    return this.parent;
	}

	public void setParent(IBaseChain parent) {
	    this.parent = parent;
	}

	public BaseAction getAction()
	{ 
	    return action;
	}
	public void setAction(BaseAction a){
	    action = a;
	}

 
}
