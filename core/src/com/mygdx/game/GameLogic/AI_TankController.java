package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.mygdx.game.Controllers.BombController;
import com.mygdx.game.Controllers.BulletController;
import com.mygdx.game.Controllers.TankController;
import com.mygdx.game.Controllers.TankPlayerController_Desktop;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.Screens.GameScreen;

/**
 * Created by DELL on 22.10.2017.
 */

public class AI_TankController extends TankController {
    private float[] GuardRays = new float[20];
    private float SeeRay = 0;
    private GameObject AIsee = null;
    private Tank Target = null;
    private Vector2 LenToTarget = new Vector2();
    private GameObject MoveTarget = null;
    private boolean toTarget = true;

    //Коэффициент уровня сложности
    private float AILevel = 1;

    private float slice = 0;
    private boolean underWalls;

    private RayCastCallback callback;
    private float Alert = 0;
    private float Radius;
    private float AngMove = 0;
    Vector2 p1 = new Vector2();
    Vector2 p2 = new Vector2();
    Vector2 p3 = new Vector2();
    ShapeRenderer renderer = new ShapeRenderer();

    public AI_TankController(ManagerList parent, Body body, float level) {
        super(parent, body);
        AILevel = level;
        TankVericaty = 1.5f - level * 1.5f;
        Health = (int) (70 + AILevel * 30);
        NowHealth = Health;
        float x = GuardRays.length;
        for (int i = 0; i < GuardRays.length; i++) {
            GuardRays[i] = (i / x - (1 - 1 / x) / 2) * 1.5f;
        }
        callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (Radius < 1 - fraction) {
                    Radius = 1 - fraction;
                    p3.set(point);
                    AIsee = (GameObject) fixture.getBody().getUserData();
                }
                return 1;
            }
        };
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        if (FlagLive) {
            if (MoveTarget != null) {
                if (MoveTarget.Parent == null) MoveTarget = null;
                else if (MoveTarget instanceof Bomb) {
                    Vector2 V = new Vector2(MoveTarget.body.getPosition().x - this.body.getPosition().x,
                            MoveTarget.body.getPosition().y - this.body.getPosition().y);
                    Cast(V.len(), (float) Math.toRadians(V.angle()));
                    if (!(AIsee instanceof Bomb) || V.len() > 8) MoveTarget = null;
                }
            }
            if (Target != null)
                if (Target.Parent == null) {
                    Alert = 0;
                    LenToTarget.set(0, 0);
                    Target = null;
                }
            if (Alert > 0) {
                Alert -= dt;
                slice += (Math.random() - slice / 2) / (10 + 20 * AILevel);
            } else {
                Alert = 0;
                slice = 0;
                Target = null;
            }
            UpDate(dt);
        }
    }

    private void UpDate(float dt) {
        float Rot = 0;
        renderer.setProjectionMatrix(Parent.Camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        if (Math.random() > 0.99) SeeRay = (float) ((Math.random() - 0.5) * Math.PI);

        if (Alert > 0) {
            LenToTarget.set(Target.body.getPosition().x + Target.body.getLinearVelocity().x / 5 - this.body.getPosition().x,
                    Target.body.getPosition().y + Target.body.getLinearVelocity().y / 5 - this.body.getPosition().y);
            SeeRay = (float) Math.toRadians(LenToTarget.angle()) - AngMove;
        }
        RotateBody(SeeRay + AngMove);

        for (float i = -3; i < 4; i++) {
            Cast(8 + 2 * AILevel, (float) (SeeRay + AngMove + i / 10 + Math.cos(Age * 8 * (0.5 + AILevel / 2)) * 0.5));
            CheckTarget(dt);
            renderer.line(p1.x, p1.y, p3.x, p3.y, Color.RED, Color.RED);
        }
        underWalls = false;
        for (int i = 0; i < GuardRays.length; i++) {
            AIsee = null;
            Cast(3, (float) (GuardRays[i] * Math.PI + AngMove));
            if (Radius > 0.7) underWalls = true;
            CheckTarget(dt);
            renderer.line(p1.x, p1.y, p3.x, p3.y, Color.DARK_GRAY, Color.GREEN);
            Rot -= Math.abs(GuardRays[i]) / GuardRays[i] * Math.pow(Radius, 5) / 16;
        }
        AngMove += Rot;
        if (MoveTarget == null)
            if (Alert > 0 && LenToTarget.len() > 8) {
                Move(dt, (float) Math.toRadians(LenToTarget.angle()), 1);
                AngMove = LenToTarget.angle();
            } else
                Move(dt, (float) (AngMove + slice * Math.sin(Age * 5)), 1);
        else {
            if (toTarget)
                Move(dt, (float) Math.toRadians(new Vector2(MoveTarget.body.getPosition().x - this.body.getPosition().x,
                        MoveTarget.body.getPosition().y - this.body.getPosition().y).angle()), 1);
            else
                Move(dt, (float) Math.toRadians(new Vector2(MoveTarget.body.getPosition().x - this.body.getPosition().x,
                        MoveTarget.body.getPosition().y - this.body.getPosition().y).angle()), -1);
        }
        renderer.end();
    }

    private void Cast(float L, float Ang) {
        p1.set(this.body.getPosition().x, this.body.getPosition().y);
        p2.set((float) (this.body.getPosition().x + L * Math.cos(Ang)),
                (float) (this.body.getPosition().y + L * Math.sin(Ang)));
        p3.set(p2);
        Radius = 0;
        this.body.getWorld().rayCast(callback, p1, p2);
    }

    private void CheckTarget(float dt) {
        if (AIsee instanceof Tank)
            if (((TankController) ((Tank) AIsee).controller).Team != this.Team) {
                if (Alert > 0&&Math.abs(AngleMinus(this.body.getAngle(),Math.toRadians(LenToTarget.angle())))<0.5) {
                    Weapon.shot(dt);
                    if (!underWalls && LenToTarget.len() < 8 && LenToTarget.len() > 3 && Math.random() < 1.5 * dt * AILevel)
                        KikcBomb();
                }
                if (Alert == 0) GameScreen.SndAlert.play();
                Target = (Tank) AIsee;
                Alert = 10;
            }
        if (((AIsee instanceof Health && NowHealth < Health) || AIsee instanceof Ammo &&
                (!Weapon.fullAmmo() || NowBombs < Bombs)) && MoveTarget == null) {
            MoveTarget = AIsee;
            toTarget = true;
        }
        if (AIsee instanceof Bomb) {
            MoveTarget = AIsee;
            toTarget = false;
            if (((BombController)((Bomb)AIsee).controller).Owner!=null)
            if (((TankController)((BombController)((Bomb)AIsee).controller).Owner.controller).Team!=this.Team){
            if (Alert == 0)
                GameScreen.SndAlert.play();
            Alert = 10;
            Target = ((BombController) ((Bomb) AIsee).controller).Owner;}
        }
    }

    @Override
    public void Dead() {
        if (Math.random() > 0.7) GameScreen.SndHaha.play();
        super.Dead();
    }

    @Override
    public void PreSolve(GameObject Object) {
        if (Object instanceof GameControlledObject)
            if (((GameControlledObject) Object).controller instanceof BulletController)
                if (((BulletController) ((GameControlledObject) Object).controller).Owner.controller instanceof TankPlayerController_Desktop) {
                    if (Alert == 0 && ((BulletController) ((GameControlledObject) Object).controller).Owner != null)
                    if (((TankPlayerController_Desktop) ((BulletController) ((GameControlledObject) Object).controller).Owner.controller).Team!=this.Team){
                        GameScreen.SndAlert.play();
                        Alert = 10;
                        Target = ((BulletController) ((GameControlledObject) Object).controller).Owner;
                    }

                }
        super.PreSolve(Object);
    }
}
