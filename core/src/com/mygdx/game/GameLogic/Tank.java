package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.Screens.GameScreen;

import java.awt.Color;

/**
 * Created by DELL on 14.10.2017.
 */

public class Tank extends GameControlledObject {
    public Tank(ManagerList manager, World W, float x, float y) {
        super(manager,W);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x,y);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.375f);
        fdef.shape = shape;
        fdef.density = 2f;
        fdef.restitution = 0f;
        create(W,def,fdef);
        sprite = new Sprite(Parent.atlas.getAtlas().findRegion("Tank"));
        sprite.setSize(1,1);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
    }

}
