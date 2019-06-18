package com.mygdx.game.GameLogic.Custom;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
  Корневой игровой обьект - Физическое тело+метод отрисовки
 */

public abstract class GameObject extends ViewObject {
    public Body body;

    public GameObject(ManagerList parent, World W) {
        super(parent);

    }
    protected void create(World W, BodyDef def, FixtureDef fdef){
        body=W.createBody(def);
        body.setUserData(this);
        body.createFixture(fdef);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation((float) (body.getAngle() / Math.PI * 180));
        if (Parent.Camera.frustum.boundsInFrustum(sprite.getX(),sprite.getY(),0,sprite.getWidth(),sprite.getHeight(),0) )
        sprite.draw(batch);
    }
    public void destroy(){
        body.getWorld().destroyBody(body);
        Parent.GameObjList.removeValue(this,false);
        Parent = null;
    }
}
