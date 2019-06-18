package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.BulletController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.Screens.GameScreen;

import box2dLight.PointLight;

/**
 * Created by DELL on 17.10.2017.
 */

public class Bullet extends GameControlledObject {
    public Bullet(ManagerList parent, World W, float x, float y, Tank Owner,float damag) {
        super(parent, W);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x,y);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.05f);
        fdef.shape = shape;
        fdef.density = 7f;
        fdef.restitution = 1f;
        create(W,def,fdef);
        sprite = new Sprite(Parent.atlas.getAtlas().findRegion("Bullet"));
        sprite.setSize(1.5f,0.12f);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);


        controller = new BulletController(parent,body,Owner,damag);
    }
    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getLinearVelocity().angle());
        sprite.draw(batch);
    }

}
