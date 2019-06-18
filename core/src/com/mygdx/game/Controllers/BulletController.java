package com.mygdx.game.Controllers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.mygdx.game.Controllers.Custom.GameColisionController;
import com.mygdx.game.GameLogic.Ammo;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Health;
import com.mygdx.game.GameLogic.Tank;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 17.10.2017.
 */

public class BulletController extends GameColisionController {
    public float TimeToLive = 1;
    public Tank Owner;
    public float Damag;
    public BulletController(ManagerList parent, Body body, Tank Owner,float damag) {
        super(parent, body);
        this.Owner = Owner;
        Damag = damag;
    }

    @Override
    public void handle(float dt) {
        TimeToLive -= dt;
        if (TimeToLive < 0) ((GameObject) this.body.getUserData()).destroy();
    }

    @Override
    public void PreSolve(GameObject Object) {
        ((GameObject) body.getUserData()).sprite.setRegion(0, 0, 0, 0);
        body.getFixtureList().first().getFilterData().maskBits = 0;
        if (Object instanceof Ammo) {
            ((DestroyController) ((Ammo) Object).controller).Died();
        }
        if (Object instanceof Health) {
            ((DestroyController) ((Health) Object).controller).Died();
        }
        if (Object instanceof Tank) {
            GameScreen.SndGoal.play(0.5f);
            byte Z=0;

            //=====================================================================================
            //if (((TankController) ((Tank) Owner).controller).Team==((TankController) ((Tank) Object).controller).Team) Z=-1;
            //else Z=1;


            Z=(((TankController) ((Tank) Owner).controller).Team==((TankController) ((Tank) Object).controller).Team)
                    ?
                    (byte)-1
                    :(byte)1;
            //=====================================================================================
            ((TankController) ((Tank) Object).controller).NowHealth -=
                    this.body.getLinearVelocity().len() * Damag;
            ((TankController)Owner.controller).Score+=10*Z;
            if (((TankController) ((Tank) Object).controller).NowHealth<0) ((TankController)Owner.controller).Score+=50*Z;
        }
        TimeToLive = 0.0f;
        Filter F = new Filter();
        F.maskBits = 0;
        this.body.getFixtureList().first().setFilterData(F);
    }

    @Override
    public void PostSolve(GameObject Object) {

    }
}
