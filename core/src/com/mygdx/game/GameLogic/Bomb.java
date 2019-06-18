package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.BombController;
import com.mygdx.game.Controllers.BulletController;
import com.mygdx.game.Controllers.Custom.GameColisionController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 18.10.2017.
 */

public class Bomb extends GameControlledObject {
    public Bomb(ManagerList parent, World W, float x, float y, Tank Owner) {
        super(parent, W);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x,y);

        def.angle = (float) (Math.random()*Math.PI*2);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);
        fdef.shape = shape;
        fdef.density = 2f;
        fdef.restitution = 1f;
        fdef.friction = 0.7f;
        create(W,def,fdef);
        body.setLinearDamping(7);
        body.setAngularDamping(1);
        body.setAngularVelocity((float) ((Math.random()-0.5)*20));
        sprite = new Sprite(Parent.atlas.getAtlas().findRegion("Bomb"));
        sprite.setSize(0.6f,0.4f);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        controller = new BombController(parent,body,Owner);
    }

}
