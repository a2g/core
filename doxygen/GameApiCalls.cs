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

@page GameApiCalls Game API Calls

This was going to be one of those framework diagrams with 
@ref Boundary "Boundary" sitting on top of @ref Walking "Walking", sitting on top
of @ref Animation "Animation". But there are not too many dependent
systems - so its really just a link page.
 



@dotfile DotTalking.dot
@dotfile DotAnimation.dot
@dotfile DotBoundary.dot
@dotfile DotExecutingChains.dot
@dotfile DotGui.dot
@dotfile DotInventory.dot
@dotfile DotSceneSwitching.dot
@dotfile DotTalking.dot
@dotfile DotValueStore.dot
@dotfile DotVerbs.dot
@dotfile DotWalking.dot

@dot
digraph G {
  compound=true;
  subgraph cluster0 {
    a -> b;
    a -> c;
    b -> d;
    c -> d;
  }
  subgraph cluster1 {
    e -> g;
    e -> f;
  }
  b -> f [lhead=cluster1];
  d -> e;
  c -> g [ltail=cluster0,lhead=cluster1];
  c -> e [ltail=cluster0];
  d -> h;
}
@enddot
*/