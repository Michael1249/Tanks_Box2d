package com.mygdx.game.Controllers.Custom;

import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 Корневой контроллер
 */

public abstract class CustomController {
    public ManagerList Parent;

    public CustomController(ManagerList parent) {
        Parent = parent;
    }

    public abstract void handle(float dt);
}
