package com.mygdx.game.Controllers.Custom;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 Контроллер игрового обьекта с возможностью обработки столкновений
 */

public abstract class GameColisionController extends GameObjectController{

    public GameColisionController(ManagerList parent, Body body) {
        super(parent, body);
    }

    public abstract void handle(float dt);

    public abstract void PreSolve(GameObject Object);
    public abstract void PostSolve(GameObject Object);
}
