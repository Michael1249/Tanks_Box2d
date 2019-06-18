package com.mygdx.game.Controllers.Custom;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 Контроллер игровых обьектов
 */

public abstract class GameObjectController extends CustomController {
    public Body body;

    public GameObjectController(ManagerList parent, Body body) {
        super(parent);
        this.body = body;
    }

    public abstract void handle(float dt);
}
