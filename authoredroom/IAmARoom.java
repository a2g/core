/* Copyright 2011 Anthony Cassidy
 * Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.
 *  DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.
 */
package com.github.a2g.core.authoredroom;


import com.github.a2g.core.SentenceUnit;
import com.github.a2g.core.action.BaseAction;
import com.github.a2g.core.action.ChoicesBaseAction;
import com.github.a2g.core.authoredroom.LoaderAPI.LoadKickStarter;

public interface IAmARoom {
  /**
     * You don't need to implement this. This one is implemented for you by @ref RoomBase.
     */
    public void onReceiveGameAPIObject(IAmTheMainAPI api);
  /**
     * Is there to load resources. You're probably wondering why we don't just make it jso  
     * this method just returns an array or list of resources. Or maybe you're thinking why it
     * doesn't just allow the returning of a chained series of commands like in OnEnterRoom.
     * The reason is because classes that implement this interface (ie Rooms) generally 
     * benefit from @ref CodeSplitting. And any calls to methods on code-split classes will
     * just return null and keep processing. And the code inside the method will get executed
     * during a free cycle, and any return value will be be lost.
     * So instead of all that, we provide all possibe commands you can call on the 
     * IAmTheLoaderAPI interface, and we @a require the implementor to call to
     * createLoadKickStarter when it's done. Pretty messy.	
     */
    public LoadKickStarter onSpecifyResourcesAndKickStart(LoaderAPI api);
  /** Next up is our last chance to change things before the lights are turned on and
     * everything is displayed. So here you should hide the things you don't want to be
     * seen. And set the display names of all the objects.
     */
    public void onPrepareRoomForFocus();
    /** This is called just after the lights go on, and then 25 times per second for the rest
        * of the duration of the @ref Room. It is the place where you perform animation.  
        */
    public void onEveryFrame();
    /** onEnterRoom is for the cut-scenes to be performed at the start of the scene.
        * There isn't really a provision for cut scenes, in the middle of the scene. So
        * this is as good as it gets.
        */ 
    public BaseAction onEnterRoom();
    /** onCommandLineExecute is executed when the user constructs a @ref Sentence and 
        * executes it.
        */
    public BaseAction onCommandLineExecute(int verb, SentenceUnit objA, SentenceUnit objB, double x, double y);  
    /** In A2g, conversation trees are called @ref Choices. This method is ths special 
         * method that is called whenever one of the subclasses of @ref ChoicesBaseAction
        *  are called.
        */
    public ChoicesBaseAction onChoice(int i);
}
