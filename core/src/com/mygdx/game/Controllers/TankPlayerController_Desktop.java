package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.MashineGun;
import com.mygdx.game.GameLogic.ShotGun;
import com.mygdx.game.GameLogic.Tank;

/**
 * Created by DELL on 15.10.2017.
 */

public class TankPlayerController_Desktop extends TankController {
    Vector2 mouse;

    public TankPlayerController_Desktop(ManagerList parent, Body body) {
        super(parent, body);
        TankVericaty=0;
        mouse = new Vector2();
        Weapon=new  ShotGun(this);//MashineGun(this);
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
            if (Gdx.input.isTouched())
                Weapon.shot(dt);
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) KikcBomb();
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)&&!Weapon.fullPak()) Weapon.reload();
            boolean U, D, L, R;
            mouse.set(-Gdx.graphics.getWidth() / 2 + Gdx.input.getX(), Gdx.graphics.getHeight() / 2 - Gdx.input.getY());
            RotateBody((float) Math.toRadians(mouse.angle()));
            D = Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
            U = Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S);
            L = Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
            R = Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
            byte Ang = -1;
            if (U && !D && !L && !R) Ang = 6;
            if (!U && !D && !L && R) Ang = 0;
            if (!U && D && !L && !R) Ang = 2;
            if (!U && !D && L && !R) Ang = 4;
            if (U && !D && !L && R) Ang = 7;
            if (!U && D && !L && R) Ang = 1;
            if (!U && D && L && !R) Ang = 3;
            if (U && !D && L && !R) Ang = 5;
            if (Ang != -1) Move(dt, (float) (45 * Math.toRadians(Ang)), 1);
        }
    }


    @Override
    public void Dead() {
        super.Dead();
        Parent.Player = null;
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
