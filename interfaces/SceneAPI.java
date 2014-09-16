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

package com.github.a2g.core.interfaces;


import com.github.a2g.core.action.BaseDialogTreeAction;
import com.github.a2g.core.action.ChainRootAction;
import com.github.a2g.core.action.ChainedAction;
import com.github.a2g.core.interfaces.OnFillLoadListAPIImpl.LoadKickStarter;
import com.github.a2g.core.objectmodel.SentenceItem;

public interface SceneAPI  extends ConstantsForAPI
{
	public LoadKickStarter onFillLoadList(OnFillLoadListAPIImpl api);

	public void onPreEntry(OnPreEntryAPI api);

	public ChainedAction onEntry(OnEntryAPI api, ChainRootAction ba);

	public void onEveryFrame(OnEveryFrameAPI api);

	public ChainedAction onDoCommand(OnDoCommandAPI api, ChainRootAction ba, int verb, SentenceItem itemA, SentenceItem itemB, double x, double y);

	public BaseDialogTreeAction onDialogTree(OnDialogTreeAPI api, ChainedAction ba, int branch);

	public void onMovementBeyondAGate(OnMovementBeyondAGateAPI api, int id);
}
