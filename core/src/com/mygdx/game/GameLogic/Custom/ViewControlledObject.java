package com.mygdx.game.GameLogic.Custom;

import com.mygdx.game.Controllers.AnimationController;
import com.mygdx.game.Controllers.Custom.GameObjectController;

/**
 * Created by DELL on 19.10.2017.
 */

public class ViewControlledObject extends ViewObject {
    public AnimationController controller;


    public ViewControlledObject(ManagerList parent,int N,int W,float FPS) {
        super(parent);
        controller = new AnimationController(Parent,N,W,FPS,this);
    }
    public void update(float dt){
        controller.handle(dt);
    }
}
