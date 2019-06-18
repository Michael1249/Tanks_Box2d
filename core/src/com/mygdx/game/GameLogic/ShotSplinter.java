package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.BulletController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 18.10.2017.
 */

public class ShotSplinter extends GameControlledObject {
    public ShotSplinter(ManagerList parent, World W, float x, float y, Tank Owner,float damag) {
        super(parent, W);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x,y);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.05f);
        fdef.shape = shape;
        fdef.density = 40f;
        fdef.restitution = 1f;
        fdef.friction=1;
        fdef.filter.maskBits=1;
        fdef.filter.categoryBits=2;
        create(W,def,fdef);
        body.setBullet(true);
        body.setLinearDamping(6);
        sprite = new Sprite(Parent.atlas.getAtlas().findRegion("Bullet2"));
        sprite.setSize(2,0.2f);
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
