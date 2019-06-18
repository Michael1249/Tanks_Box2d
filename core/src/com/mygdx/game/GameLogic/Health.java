package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.DestroyController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 20.10.2017.
 */

public class Health  extends Bonus{

    public Health(ManagerList parent, World W) {
        super(parent, W, new Sprite(parent.atlas.getAtlas().findRegion("Health")));
    }
}
