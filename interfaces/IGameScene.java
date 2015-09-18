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

import com.github.a2g.core.action.ChainEndAction;
import com.github.a2g.core.action.DialogChainEndAction;
import com.github.a2g.core.interfaces.IOnFillLoadListImpl.LoadKickStarter;
import com.github.a2g.core.interfaces.internal.IChainRootForDialog;
import com.github.a2g.core.interfaces.internal.IChainRootForScene;
import com.github.a2g.core.objectmodel.SentenceItem;
import com.github.a2g.core.primitive.PointF;

public interface IGameScene extends ConstantsForAPI {
	public LoadKickStarter onFillLoadList(IOnFillLoadListImpl api);

	public void onPreEntry(IOnPreEntry api);

	public ChainEndAction onEntry(IOnEntry api, IChainRootForScene ba);

	public void onEveryFrame(IOnEveryFrame api);

	public ChainEndAction onDoCommand(IOnDoCommand api, IChainRootForScene ba,
			int verb, SentenceItem itemA, SentenceItem itemB, double x, double y);

	public DialogChainEndAction onDialogTree(IOnDialogTree api,
			IChainRootForDialog ba, int branch);

	public void onMovementBeyondAGate(IOnMovementBeyondAGate api, PointF a, PointF b, PointF tp, int id);
}
