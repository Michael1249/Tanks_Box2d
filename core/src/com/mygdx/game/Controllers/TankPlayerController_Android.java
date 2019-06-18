package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 15.10.2017.
 */

public class TankPlayerController_Android extends TankController {
    Vector2 touch0, center0, touch1, center1;

    public TankPlayerController_Android(ManagerList parent, Body body) {
        super(parent, body);
        TankVericaty = 0;
        touch0 = new Vector2();
        center0 = new Vector2();
        touch1 = new Vector2();
        center1 = new Vector2();
        //Weapon=new ShotGun(this);
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        if (FlagLive) {
            System.out.println("Tank_Player_Info_======================");
            System.out.println("_Health: " + NowHealth);
            System.out.println("_Fitness: " + getFitness());
            System.out.println("_Age: " + Age);
            System.out.println("_Score: " + Score);
            //NowHealth = 1000;
            Parent.Camera.position.set(this.body.getPosition().x, this.body.getPosition().y, 0);
            if (Gdx.input.isTouched(0)) {
                if (center0.len() == 0) center0.set(Gdx.input.getX(0), Gdx.input.getY(0));
                touch0.set(-center0.x + Gdx.input.getX(0), center0.y - Gdx.input.getY(0));
                if (touch0.len() != 0) {
                    Move(dt, (float) (Math.toRadians(touch0.angle())), 1);
                    if (touch1.len()==0) RotateBody((float) (Math.toRadians(touch0.angle())));
                }
            } else {
                center0.set(0, 0);
                touch0.set(0,0);
            }

            if (Gdx.input.isTouched(1)) {
                if (center1.len() == 0) center1.set(Gdx.input.getX(1), Gdx.input.getY(1));
                touch1.set(-Gdx.graphics.getWidth() / 2 + Gdx.input.getX(1), Gdx.graphics.getHeight() / 2 - Gdx.input.getY(1));
                if (touch1.len() != 0) {
                    RotateBody((float) (Math.toRadians(touch1.angle())));
                    Weapon.shot(dt);
                }
            } else {
                center1.set(0, 0);
                touch1.set(0,0);
            }

//                Weapon.shot(dt);
//            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) KikcBomb();
//            if (Gdx.input.isKeyJustPressed(Input.Keys.R)&&!Weapon.fullPak()) Weapon.reload();
//            boolean U, D, L, R;
//            touch0.set(-Gdx.graphics.getWidth() / 2 + Gdx.input.getX(), Gdx.graphics.getHeight() / 2 - Gdx.input.getY());
//            RotateBody((float) Math.toRadians(touch0.angle()));
//            D = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
//            U = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
//            L = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
//            R = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
//            byte Ang = -1;
//            if (U && !D && !L && !R) Ang = 6;
//            if (!U && !D && !L && R) Ang = 0;
//            if (!U && D && !L && !R) Ang = 2;
//            if (!U && !D && L && !R) Ang = 4;
//            if (U && !D && !L && R) Ang = 7;
//            if (!U && D && !L && R) Ang = 1;
//            if (!U && D && L && !R) Ang = 3;
//            if (U && !D && L && !R) Ang = 5;
//            if (Ang != -1) Move(dt, (float) (45 * Math.toRadians(Ang)), 1);
        }
    }


    @Override
    public void Dead() {
        super.Dead();
        Parent.Player = null;
        Parent.createplayer((byte) (Math.random()*2));
        //Parent.createplayer();
    }

    @Override
    public void PreSolve(GameObject Object) {
        super.PreSolve(Object);
    }

    @Override
    public void PostSolve(GameObject Object) {
    }
}

