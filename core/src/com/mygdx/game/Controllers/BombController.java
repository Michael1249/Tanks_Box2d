package com.mygdx.game.Controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Controllers.Custom.GameObjectController;
import com.mygdx.game.GameLogic.Bomb;
import com.mygdx.game.GameLogic.BombBoomEffect;
import com.mygdx.game.GameLogic.BoomEffect;
import com.mygdx.game.GameLogic.Bullet;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Custom.ViewObject;
import com.mygdx.game.GameLogic.Splinter;
import com.mygdx.game.GameLogic.Tank;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 18.10.2017.
 */

public class BombController extends GameObjectController {
    float TimeToLive = 100f;
    final int SplinterSpeed = 40;
    public Tank Owner;
    public BombController(ManagerList parent, Body body,Tank Owner)
    {
        super(parent, body);
        this.Owner = Owner;
    }

    @Override
    public void handle(float dt) {
        TimeToLive -=dt;
        if (TimeToLive<0) {
            float r = (float) (Math.random()*Math.PI);
            for (int i=0; i<36; i++) {
                float ang = (float) Math.toRadians(i*10+r);
                Splinter B = new Splinter(((GameObject) this.body.getUserData()).Parent, this.body.getWorld(),
                        (float) (this.body.getPosition().x ),
                        (float) (this.body.getPosition().y ),Owner,1.2f);
                B.body.setLinearVelocity((float) (SplinterSpeed*Math.cos(ang)),(float) (SplinterSpeed*Math.sin(ang)));
                ((BulletController)B.controller).TimeToLive = 0.25f;

                ((GameObject) this.body.getUserData()).Parent.GameObjList.
                        add(B);
            }
            BombBoomEffect B = new BombBoomEffect(((GameObject) this.body.getUserData()).Parent,body.getPosition().x,body.getPosition().y);
            ((GameObject) this.body.getUserData()).Parent.ViewList.add(B);

            ((GameObject)this.body.getUserData()).destroy();
            GameScreen.SndBoom.play(0.7f);
        }
    }
}
