package com.mygdx.game.Controllers;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Controllers.Custom.GameObjectController;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 18.10.2017.
 */

public class DestroyController extends GameObjectController {
    float Live = 40;

    public DestroyController(ManagerList parent, Body body) {
        super(parent, body);
    }

    public void Died(){Live = 0;}
    @Override
    public void handle(float dt) {
        Live -= dt;
        if (Live<0) ((GameObject) this.body.getUserData()).destroy();
    }
}
