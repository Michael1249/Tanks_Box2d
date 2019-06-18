package com.mygdx.game.GameLogic;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Controllers.TankController;

/**
 * Created by DELL on 29.10.2017.
 */

public abstract class Gun {
    public TankController Parent;
    public Body body;

    public Gun(TankController parent) {
        Parent = parent;
        body = Parent.body;
    }

    abstract public void shot(float dt);
    abstract public void update(float dt);
    abstract public boolean addpak();
    abstract public boolean fullAmmo();
    abstract public boolean fullPak();
    abstract public void reload();
}
