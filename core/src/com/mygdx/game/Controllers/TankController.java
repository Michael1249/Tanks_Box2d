package com.mygdx.game.Controllers;


import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Controllers.Custom.GameColisionController;
import com.mygdx.game.GameLogic.Ammo;
import com.mygdx.game.GameLogic.Bomb;
import com.mygdx.game.GameLogic.BoomEffect;
import com.mygdx.game.GameLogic.Bullet;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Gun;
import com.mygdx.game.GameLogic.Health;
import com.mygdx.game.GameLogic.MashineGun;
import com.mygdx.game.GameLogic.ShotEffect;
import com.mygdx.game.GameLogic.ShotGun;
import com.mygdx.game.GameLogic.Tank;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 18.10.2017.
 */

public class TankController extends GameColisionController {
    protected boolean FlagLive = true;
    //Tank Options
    protected int Health = 100;
    protected int Speed = 3000;
    protected int AngSpeed = 20;
    public float TankVericaty=0;

    protected Gun Weapon =new MashineGun(this);

    protected final int Bombs = 2;
    protected float NowHealth = 100;
    protected int NowBombs = 2000;
    protected float BombSpeed=50;

    //Tank Log ant Fitnes
    public byte Team = 0;
    public float Age = 0;
    public float Score = 0;

    public TankController(ManagerList parent, Body body) {
        super(parent, body);
    }
    public void Rotate(float Ang){
        this.body.setAngularVelocity(Ang);
    }
    public void Move(float dt,float Ang,float Percent){
        Score+=dt*Percent/10;
        this.body.applyForceToCenter((float) (Speed *Percent* Math.cos(Ang) * dt), (float) (Speed *Percent* Math.sin(Ang) * dt), true);
    }
    public void KikcBomb() {
        if (NowBombs > 0) {
            Score+=1;
            NowBombs--;
            GameScreen.SndChika.play(1f);
            Bomb B = new Bomb(((GameObject) this.body.getUserData()).Parent, this.body.getWorld(),
                    (float) (this.body.getPosition().x + Math.cos(this.body.getAngle()) * 0.3),
                    (float) (this.body.getPosition().y + Math.sin(this.body.getAngle()) * 0.3), ((Tank) this.body.getUserData()));
            B.body.setLinearVelocity((float) (BombSpeed * Math.cos(this.body.getAngle())), (float) (BombSpeed* Math.sin(this.body.getAngle())));
            B.body.setBullet(true);
            ((GameObject) this.body.getUserData()).Parent.GameObjList.add(B);
        }
    }


    @Override
    public void handle(float dt) {
        Age += dt;
        Weapon.update(dt);
        Rotate(0);
        this.body.setLinearDamping(900*dt);
        if (NowHealth <= 0) Dead();
    }

    public float getFitness(){
        return (float) (Score/Age*Math.tanh(Age/10));
    }
    public void Dead() {
        BoomEffect B = new BoomEffect(((GameObject) this.body.getUserData()).Parent, body.getPosition().x, body.getPosition().y);
        ((GameObject) this.body.getUserData()).Parent.ViewList.add(B);
        GameScreen.SndBoom.play(0.2f);
        ((GameObject) this.body.getUserData()).destroy();
        FlagLive = false;
    }
    public void RotateBody(float Ang){
        this.body.setAngularVelocity(AngSpeed * AngleMinus(Ang, this.body.getAngle()));
    }
    public float AngleMinus(double A, double B) {
        B = B % (2 * Math.PI);
        if (B < 0) B += 2 * Math.PI;
        A = A % (2 * Math.PI);
        if (A < 0) A += 2 * Math.PI;
        if (Math.abs(A - B) < Math.PI) return (float) (A - B);
        if (A < B) return (float) (A - B + 2 * Math.PI);
        if (A > B) return (float) (A - B - 2 * Math.PI);
        return 0;
    }
    @Override
    public void PreSolve(GameObject Object) {
        if (Object instanceof Ammo) {
            if (Weapon.addpak() || NowBombs < Bombs) {
                ((DestroyController) ((Ammo) Object).controller).Died();
                GameScreen.SndUpWoop.play(0.4f);
                Score+=10;
            }
            if (NowBombs < Bombs) NowBombs++;
        } else if (Object instanceof Health) {
            if (NowHealth < Health) {
                NowHealth += 50;
                if (NowHealth > Health) NowHealth = Health;
                ((DestroyController) ((Health) Object).controller).Died();
                GameScreen.SndUpWoop.play(0.4f);
                Score+=10;
            }
        }
    }

    @Override
    public void PostSolve(GameObject Object) {

    }
}
