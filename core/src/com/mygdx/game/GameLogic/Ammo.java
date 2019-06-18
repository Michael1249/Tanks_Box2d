package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.DestroyController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 17.10.2017.
 */

public class Ammo extends Bonus {

    public Ammo(ManagerList parent, World W) {
        super(parent, W, new Sprite(parent.atlas.getAtlas().findRegion("Amo")));
    }
}
