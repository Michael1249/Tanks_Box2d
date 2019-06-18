package com.mygdx.game.GameLogic;

import com.mygdx.game.Controllers.TankController;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 29.10.2017.
 */

public class MashineGun extends Gun {
    //Gun options
    protected float Vericaty = 0.1f;
    protected float Damag = 0.4f;
    protected final float BulletReload = 0.1f;
    protected final float AmoReload = 3f;
    protected final float BulletSpeed = 50;
    protected final int BulletsInPak = 25;
    protected final int AmoPaks = 2;
    protected float NowBulletReload = 1;
    protected float NowAmoReload = 0;
    protected int NowAmoPaks = 2;
    protected int NowBulletsInPak = 25;
    boolean flagreload = true;

    public MashineGun(TankController parent) {
        super(parent);
    }

    @Override
    public void shot(float dt) {
        if (NowBulletReload >= BulletReload && NowBulletsInPak > 0) {
            Parent.Score += 0.1;
            NowBulletsInPak--;
            NowBulletReload = 0;
            GameScreen.SndShot.play(0.6f);
            Bullet B = new Bullet(((GameObject) Parent.body.getUserData()).Parent, this.body.getWorld(),
                    (float) (this.body.getPosition().x + Math.cos(this.body.getAngle()) * 0.5),
                    (float) (this.body.getPosition().y + Math.sin(this.body.getAngle()) * 0.5),
                    ((Tank) this.body.getUserData()), Damag);
            float Accuraty = (float) ((Vericaty+Parent.TankVericaty) * (Math.random() - 0.5));
            B.body.setLinearVelocity((float) (BulletSpeed * Math.cos(this.body.getAngle() + Accuraty)),
                    (float) (BulletSpeed * Math.sin(this.body.getAngle() + Accuraty)));
            B.body.setBullet(true);
            ((GameObject) this.body.getUserData()).Parent.GameObjList.add(B);
            ShotEffect Sh = new ShotEffect(((GameObject) this.body.getUserData()).Parent,
                    (float) (body.getPosition().x + Math.cos(this.body.getAngle()) * 1.22 + body.getLinearVelocity().x * dt),
                    (float) (body.getPosition().y + Math.sin(this.body.getAngle()) * 1.22 + body.getLinearVelocity().y * dt));
            Sh.sprite.setRotation((float) Math.toDegrees(this.body.getAngle()));
            ((GameObject) this.body.getUserData()).Parent.ViewList.add(Sh);

            if (!Parent.Parent.GameWalls.walls[(int) B.body.getPosition().x][(int) B.body.getPosition().y])
                B.destroy();
        }
    }

    @Override
    public void update(float dt) {
        if (NowBulletReload < BulletReload) NowBulletReload += dt;
        if (NowAmoPaks > 0 && NowBulletsInPak == 0 || !flagreload) {
            if (flagreload) {
                NowAmoPaks--;
                GameScreen.SndReload.play(1);
            }
            flagreload = false;
            NowAmoReload += dt;
        }
        if (NowAmoReload > AmoReload) {
            NowAmoReload = 0;
            NowBulletsInPak = BulletsInPak;
            flagreload = true;
        }
    }

    @Override
    public boolean addpak() {
        if (NowAmoPaks < AmoPaks) {
            NowAmoPaks++;
            return true;
        }
        return false;
    }

    @Override
    public boolean fullAmmo() {
        if (NowAmoPaks==AmoPaks) return true;
        return false;
    }

    @Override
    public boolean fullPak() {
        if (NowBulletsInPak==BulletsInPak) return true;
        return false;
    }

    @Override
    public void reload() {
        NowBulletsInPak=0;
    }
}
