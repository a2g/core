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
 
/*! 
@page Actions
Most of the classes in @ref com.github.a2g.core.action are represented here.
<br>

@dot
digraph example 
{
	graph[splines=ortho, rankdir=LR, compound="true"
	node [shape=record, fontname=Helvetica, fontsize=10, style=filled, rankdir=LR  ];


	BaseAction[  label="\nBaseAction\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" 
	URL="@ref com.github.a2g.core.action.BaseAction" color="deeppink" fillcolor="lightpink"];
	TitleCardAction[  label="TitleCardAction" URL="@ref com.github.a2g.core.action.TitleCardAction" color="deeppink" fillcolor="lightpink"];
	NullParentAction[  label="NullParentAction" URL="@ref com.github.a2g.core.action.NullParentAction" color="deeppink" fillcolor="lightpink"];
	BaseDialogTreeAction[  label="BaseDialogTreeAction" URL="@ref com.github.a2g.core.action.BaseDialogTreeAction" color="deeppink" fillcolor="lightpink"];
	DialogTreeBranchAction[  label="DialogTreeBranchAction" URL="@ref com.github.a2g.core.action.DialogTreeBranchAction" color="deeppink" fillcolor="lightpink"];
	DialogTreeEndAction[  label="DialogTreeEndAction" URL="@ref com.github.a2g.core.action.DialogTreeEndAction" color="deeppink" fillcolor="lightpink"];
	DialogTreeDoDialogBranchAction[  label="DialogTreeDoDialogBranchAction" URL="@ref com.github.a2g.core.action.DialogTreeDoDialogBranchAction" color="deeppink" fillcolor="lightpink"];
	PlayAnimationAction[  label="PlayAnimationAction" URL="@ref com.github.a2g.core.action.PlayAnimationAction" color="deeppink" fillcolor="lightpink"];
	PlayAnimationRepeatWhilstVisibleAction[  label="PlayAnimationRepeatWhilstVisibleAction" URL="@ref com.github.a2g.core.action.PlayAnimationRepeatWhilstVisibleAction" color="deeppink" fillcolor="lightpink"];
	SayAction[  label="SayAction" URL="@ref com.github.a2g.core.action.SayAction" color="deeppink" fillcolor="lightpink"];
	SayWithoutAnimationAction[  label="SayWithoutAnimationAction" URL="@ref com.github.a2g.core.action.SayWithoutAnimationAction" color="deeppink" fillcolor="lightpink"];
	SetActiveAnimationAction[  label="SetActiveAnimationAction" URL="@ref com.github.a2g.core.action.SetActiveAnimationAction" color="deeppink" fillcolor="lightpink"];
	SetActiveFrameAction[  label="SetActiveFrameAction" URL="@ref com.github.a2g.core.action.SetActiveFrameAction" color="deeppink" fillcolor="lightpink"];
	SetBaseMiddleXAction[  label="SetBaseMiddleXAction" URL="@ref com.github.a2g.core.action.SetBaseMiddleXAction" color="deeppink" fillcolor="lightpink"];
	SetBaseMiddleYAction[  label="SetBaseMiddleYAction" URL="@ref com.github.a2g.core.action.SetBaseMiddleYAction" color="deeppink" fillcolor="lightpink"];
	SetDisplayNameAction[  label="SetDisplayNameAction" URL="@ref com.github.a2g.core.action.SetDisplayNameAction" color="deeppink" fillcolor="lightpink"];
	SetHomeAnimationAction[  label="SetHomeAnimationAction" URL="@ref com.github.a2g.core.action.SetHomeAnimationAction" color="deeppink" fillcolor="lightpink"];
	SetInventoryVisibleAction[  label="SetInventoryVisibleAction" URL="@ref com.github.a2g.core.action.SetInventoryVisibleAction" color="deeppink" fillcolor="lightpink"];
	SetTalkingAnimationAction[  label="SetTalkingAnimationAction" URL="@ref com.github.a2g.core.action.SetTalkingAnimationAction" color="deeppink" fillcolor="lightpink"];
	SetTalkingAnimationDelayAction[  label="SetTalkingAnimationDelayAction" URL="@ref com.github.a2g.core.action.SetTalkingAnimationDelayAction" color="deeppink" fillcolor="lightpink"];
	SetVisibleAction[  label="SetVisibleAction" URL="@ref com.github.a2g.core.action.SetVisibleAction" color="deeppink" fillcolor="lightpink"];
	SleepAction[  label="SleepAction" URL="@ref com.github.a2g.core.action.SleepAction" color="deeppink" fillcolor="lightpink"];
	SwapPropertyAction[  label="SwapPropertyAction" URL="@ref com.github.a2g.core.action.SwapPropertyAction" color="deeppink" fillcolor="lightpink"];
	SwitchToAction[  label="SwitchToAction" URL="@ref com.github.a2g.core.action.SwitchToAction" color="deeppink" fillcolor="lightpink"];
	WaitForFrameAction[  label="WaitForFrameAction" URL="@ref com.github.a2g.core.action.WaitForFrameAction" color="deeppink" fillcolor="lightpink"];
	WalkToAction[  label="WalkToAction" URL="@ref com.github.a2g.core.action.WalkToAction" color="deeppink" fillcolor="lightpink"];
	
	BaseAction->BaseDialogTreeAction[ arrowhead="none"];
	BaseDialogTreeAction->DialogTreeEndAction[ arrowhead="none"];
	BaseDialogTreeAction->DialogTreeBranchAction[ arrowhead="none"];
	BaseDialogTreeAction->DialogTreeDoDialogBranchAction[ arrowhead="none"];
	BaseAction->TitleCardAction[ arrowhead="none"];
	BaseAction->NullParentAction[ arrowhead="none"];
	BaseAction->PlayAnimationAction[ arrowhead="none"];
	BaseAction->PlayAnimationRepeatWhilstVisibleAction[ arrowhead="none"];
	BaseAction->SayAction[ arrowhead="none"];
	BaseAction->SayWithoutAnimationAction[ arrowhead="none"];
	BaseAction->SetActiveAnimationAction[ arrowhead="none"];
	BaseAction->SetActiveFrameAction[ arrowhead="none"];
	BaseAction->SetBaseMiddleXAction[ arrowhead="none"];
	BaseAction->SetBaseMiddleYAction[ arrowhead="none"];
	BaseAction->SetDisplayNameAction[ arrowhead="none"];
	BaseAction->SetHomeAnimationAction[ arrowhead="none"];
	BaseAction->SetInventoryVisibleAction[ arrowhead="none"];
	BaseAction->SetTalkingAnimationAction[ arrowhead="none"];
	BaseAction->SetTalkingAnimationDelayAction[ arrowhead="none"];
	BaseAction->SetVisibleAction[ arrowhead="none"];
	BaseAction->SleepAction[ arrowhead="none"];
	BaseAction->SwapPropertyAction[ arrowhead="none"];
	BaseAction->SwitchToAction[ arrowhead="none"];
	BaseAction->WaitForFrameAction[ arrowhead="none"];
	BaseAction->WalkToAction[ arrowhead="none"];
}
@enddot

*/


