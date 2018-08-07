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
@page ObjectModel
All the classes in com.github.a2g.core.objectmodel are represented here.
<br>
Understand this, and your half way to understanding the a2g library.

@dot
digraph example {
node [shape=record, fontname=Helvetica, fontsize=10, style=filled ];

CommandLinePresenter[  label="CommandLinePresenter" URL="@ref com.github.a2g.core.objectmodel.CommandLinePresenter" color="deeppink" fillcolor="lightpink"];
CommandLinePanel[  label="CommandLinePanel" URL="@ref com.github.a2g.core.objectmodel.CommandLinePanel" color="deeppink" fillcolor="lightpink"];
CommandLine[  label="CommandLine" URL="@ref com.github.a2g.core.objectmodel.CommandLine" color="deeppink" fillcolor="lightpink"];
SentenceItem[  label="SentenceItem" URL="@ref com.github.a2g.core.objectmodel.SentenceItem" color="deeppink" fillcolor="lightpink"];
Sentence[  label="Sentence" URL="@ref com.github.a2g.core.objectmodel.Sentence" color="deeppink" fillcolor="lightpink"];

CommandLinePresenter->CommandLinePanel[ arrowhead="open"];
CommandLinePresenter->CommandLine[ arrowhead="open"];
CommandLine->Sentence[ arrowhead="open"];
Sentence->SentenceItem[ arrowhead="open"];

InventoryPresenter[ label="InventoryPresenter" URL="@ref com.github.a2g.core.objectmodel.InventoryPresenter" color="magenta" fillcolor="thistle"];
InventoryPanel[ label="InventoryPanel" URL="@ref com.github.a2g.core.objectmodel.InventoryPanel" color="magenta" fillcolor="thistle"];
Inventory[ label="Inventory" URL="@ref com.github.a2g.core.objectmodel.Inventory" color="magenta" fillcolor="thistle"];
InventoryItem[  label="InventoryItem" URL="@ref com.github.a2g.core.objectmodel.InventoryItem" color="magenta" fillcolor="thistle"];
InventoryItemCollection[  label="InventoryItemCollection" URL="@ref com.github.a2g.core.objectmodel.InventoryItemCollection" color="magenta" fillcolor="thistle"];

InventoryPresenter->InventoryPanel[ arrowhead="open", style="filled" ];
InventoryPresenter->Inventory [ arrowhead="open", style="filled" ];
Inventory->InventoryItemCollection [ arrowhead="open"];
InventoryItemCollection->InventoryItem [ arrowhead="open"];

VerbsPresenter[ label="VerbsPresenter" URL="@ref com.github.a2g.core.objectmodel.VerbsPresenter" color="deepskyblue" fillcolor="powderblue"];
VerbsPanel[ label="VerbsPanel" URL="@ref com.github.a2g.core.objectmodel.VerbsPanel" color="deepskyblue" fillcolor="powderblue"];
Verbs[ label="Verbs" URL="@ref com.github.a2g.core.objectmodel.Verbs" color="deepskyblue" fillcolor="powderblue"];
Verb[ label="Verb" URL="@ref com.github.a2g.core.objectmodel.Verb" color="deepskyblue" fillcolor="powderblue"];
VerbCollection[ label="VerbCollection" URL="@ref com.github.a2g.core.objectmodel.VerbCollection" color="deepskyblue" fillcolor="powderblue"];

VerbsPresenter->VerbsPanel[ arrowhead="open", style="filled" ];
VerbsPresenter->Verbs[ arrowhead="open", style="filled" ];
Verbs->VerbCollection[ arrowhead="open"];
VerbCollection->Verb[ arrowhead="open"];

ScenePresenter[  label="ScenePresenter" URL="@ref com.github.a2g.core.objectmodel.ScenePresenter"  color="green" fillcolor="darkolivegreen2"];
ScenePanel[  label="ScenePanel" URL="@ref com.github.a2g.core.objectmodel.ScenePanel"  color="green" fillcolor="darkolivegreen2"];
Scene[  label="Scene" URL="@ref com.github.a2g.core.objectmodel.Scene" color="green" fillcolor="darkolivegreen2"];
SceneObject[  label="SceneObject" URL="@ref com.github.a2g.core.objectmodel.SceneObject" color="green" fillcolor="darkolivegreen2"];
SceneObjectCollection[  label="SceneObjectCollection" URL="@ref com.github.a2g.core.objectmodel.SceneObjectCollection" color="green" fillcolor="darkolivegreen2"];
Animation [  label="Animation" URL="@ref com.github.a2g.core.objectmodel.Animation" color="green" fillcolor="darkolivegreen2"];
AnimationCollection [  label="AnimationCollection" URL="@ref com.github.a2g.core.objectmodel.AnimationCollection" color="green" fillcolor="darkolivegreen2"];
FrameAndAnimation[  label="FrameAndAnimation" URL="@ref com.github.a2g.core.objectmodel.FrameAndAnimation" color="green" fillcolor="darkolivegreen2"];
Image[  label="Image" URL="@ref com.github.a2g.core.objectmodel.Image" color="green" fillcolor="darkolivegreen2"];
ImageCollection[  label="ImageCollection" URL="@ref com.github.a2g.core.objectmodel.ImageCollection" color="green" fillcolor="darkolivegreen2"];

ScenePresenter->ScenePanel[ arrowhead="open", style="filled"];
ScenePresenter->Scene[ arrowhead="open", style="filled"];
Scene->SceneObjectCollection[ arrowhead="open"];
SceneObjectCollection->SceneObject[ arrowhead="open"];
SceneObject->FrameAndAnimation[ arrowhead="open"];
SceneObject->AnimationCollection[ arrowhead="open"];
AnimationCollection->Animation[ arrowhead="open"];
Animation->ImageCollection[ arrowhead="open"];
ImageCollection->Image[ arrowhead="open"];

DialogTreePresenter[  label="DialogTreePresenter" URL="@ref com.github.a2g.core.objectmodel.DialogTreePresenter" color="yellow" fillcolor="khaki"];
DialogTreePanel[  label="DialogTreePanel" URL="@ref com.github.a2g.core.objectmodel.DialogTreePanel" color="yellow" fillcolor="khaki"];
DialogTree[  label="DialogTree" URL="@ref com.github.a2g.core.objectmodel.DialogTree" color="yellow" fillcolor="khaki"];
DialogTreePresenter->DialogTreePanel[ arrowhead="open", style="filled" ];
DialogTreePresenter->DialogTree[ arrowhead="open", style="filled" ];

LoaderPresenter[ label="LoaderPresenter" URL="@ref com.github.a2g.core.objectmodel.LoaderPresenter" color="orangered" fillcolor="darkorange"];
LoaderPanel[ label="LoaderPanel" URL="@ref com.github.a2g.core.objectmodel.LoaderPanel" color="orangered" fillcolor="darkorange"];
Loader[ label="Loader" URL="@ref com.github.a2g.core.objectmodel.Loader" color="orangered" fillcolor="darkorange"];
LoaderItem[  label="LoaderItem" URL="@ref com.github.a2g.core.objectmodel.LoaderItem" color="orangered" fillcolor="darkorange"];
LoaderItemCollection[  label="LoaderItemCollection" URL="@ref com.github.a2g.core.objectmodel.LoaderItemCollection" color="orangered" fillcolor="darkorange"];
LoadedLoad[  label="LoadedLoad" URL="@ref com.github.a2g.core.objectmodel.LoadedLoad" color="orangered" fillcolor="darkorange"];

LoaderPresenter->LoaderPanel[ arrowhead="open", style="filled" ];
LoaderPresenter->Loader [ arrowhead="open", style="filled" ];
Loader->LoaderItemCollection [ arrowhead="open"];
LoaderItemCollection->LoaderItem [ arrowhead="open"];
LoaderItem->LoadedLoad [ arrowhead="open"];

TitleCardPresenter[  label="TitleCardPresenter" URL="@ref com.github.a2g.core.objectmodel.TitleCardPresenter" color="red" fillcolor="tomato"];
TitleCardPanel[  label="TitleCardPanel" URL="@ref com.github.a2g.core.objectmodel.TitleCardPanel" color="red" fillcolor="tomato"];
TitleCard[  label="TitleCard" URL="@ref com.github.a2g.core.objectmodel.TitleCard" color="red" fillcolor="tomato"];
TitleCardPresenter->TitleCardPanel[ arrowhead="open", style="filled" ];
TitleCardPresenter->TitleCard[ arrowhead="open", style="filled" ];



PopupPanel[ label="PopupPanel" URL="@ref com.github.a2g.core.objectmodel.PopupPanel" color="deepskyblue" fillcolor="powderblue"];

MasterPanel[  label="MasterPanel" URL="@ref com.github.a2g.core.objectmodel.MasterPanel" color="crimson" fillcolor="indianred"];


MasterPresenter[  label="MasterPresenter" URL="@ref com.github.a2g.core.objectmodel.MasterPresenter" ];
MasterPresenter->CommandLinePresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->VerbsPresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->InventoryPresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->ScenePresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->DialogTreePresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->TitleCardPresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->LoaderPresenter[ arrowhead="open", style="dashed" ];
MasterPresenter->MasterPanel[ arrowhead="open", style="dashed" ];

}
@enddot

*/


