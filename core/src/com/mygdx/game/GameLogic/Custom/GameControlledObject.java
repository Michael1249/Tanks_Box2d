package com.mygdx.game.GameLogic.Custom;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.Custom.GameObjectController;

/**
 Контролируемый игровой обьект имеет свой контроллер
 */

public abstract class GameControlledObject extends GameObject {
    public GameObjectController controller;

    public GameControlledObject(ManagerList parent, World W) {
        super(parent, W);
    }

    public void update(float dt) {
        if (controller != null)
        controller.handle(dt);
    }
}
