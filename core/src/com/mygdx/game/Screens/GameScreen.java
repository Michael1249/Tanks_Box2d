package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.ManagerController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.Controllers.Custom.GameColisionController;

/*
 Игровой экран связывающий логику игры и ввод, а также отрисовывает мир
 Game Screen which associate Graphic, Game logic, I/O
 */

public class GameScreen implements Screen {
    OrthographicCamera Camera;
    SpriteBatch Batch;//Painter
    public final int ViewPortSize = 240; //Size of gameworld window
    World world;
    Box2DDebugRenderer renderer; //Test Painter
    ManagerList Manager;
    float DeviseScreanRatio;

    //AudioFiles
    //REWRITE==================
    final static public Sound SndShot = Gdx.audio.newSound(Gdx.files.internal("Shot.mp3"));
    final static public Sound SndReload = Gdx.audio.newSound(Gdx.files.internal("Reload.mp3"));
    final static public Sound SndUpWoop = Gdx.audio.newSound(Gdx.files.internal("UpWoop.mp3"));
    final static public Sound SndChika = Gdx.audio.newSound(Gdx.files.internal("Chika.mp3"));
    final static public Sound SndBoom = Gdx.audio.newSound(Gdx.files.internal("Boom.wav"));
    final static public Sound SndGoal = Gdx.audio.newSound(Gdx.files.internal("Goal.wav"));
    final static public Sound SndAlert = Gdx.audio.newSound(Gdx.files.internal("Alert.wav"));
    final static public Sound SndHaha = Gdx.audio.newSound(Gdx.files.internal("Haha.wav"));
    final static public Music SndBGM = Gdx.audio.newMusic(Gdx.files.internal("BackGround Music.mp3"));
    //REWRITE==================


    //Create Screan and Show
    @Override
    public void show() {
        SndBGM.setLooping(true);
        SndBGM.setVolume(0.2f);
        SndBGM.play();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                GameObject Obj1, Obj2;
                Obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
                Obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
                if (Obj1 instanceof GameControlledObject)
                    if (((GameControlledObject) Obj1).controller instanceof GameColisionController)
                        ((GameColisionController) ((GameControlledObject) Obj1).controller).PreSolve(Obj2);
                if (Obj2 instanceof GameControlledObject)
                    if (((GameControlledObject) Obj2).controller instanceof GameColisionController)
                        ((GameColisionController) ((GameControlledObject) Obj2).controller).PreSolve(Obj1);
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                GameObject Obj1, Obj2;
                Obj1 = (GameObject) contact.getFixtureA().getBody().getUserData();
                Obj2 = (GameObject) contact.getFixtureB().getBody().getUserData();
                if (Obj1 instanceof GameControlledObject)
                    if (((GameControlledObject) Obj1).controller instanceof GameColisionController)
                        ((GameColisionController) ((GameControlledObject) Obj1).controller).PostSolve(Obj2);
                if (Obj2 instanceof GameControlledObject)
                    if (((GameControlledObject) Obj2).controller instanceof GameColisionController)
                        ((GameColisionController) ((GameControlledObject) Obj2).controller).PostSolve(Obj1);
            }
        });
        renderer = new Box2DDebugRenderer();
        Camera = new OrthographicCamera(20, 12);
        Camera.zoom = 1;
        Manager = new ManagerList(Camera, world);
        Batch = new SpriteBatch();
        ManagerController Listcontroller = new ManagerController(Manager);
        Manager.Controller = Listcontroller;
    }

    //Draw the Scene
    @Override
    public void render(float delta) {
            world.step(delta, 20, 20);

            Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            Manager.update(delta);

            Camera.update();

            Batch.setProjectionMatrix(Camera.combined);
            Batch.begin();
            if (Manager.RenderIndex == 1 || Manager.RenderIndex == 2)
                Manager.draw(Batch);
            Batch.end();

            if (Manager.RenderIndex == 0 || Manager.RenderIndex == 2)
                renderer.render(world, Camera.combined);


    }

    //Set-up under the device screen or program window
    @Override
    public void resize(int width, int height) {
        float X, Y;
        X = width;
        Y = height;
        DeviseScreanRatio = X / Y;
        Y = (float) Math.sqrt(ViewPortSize / DeviseScreanRatio);
        X = DeviseScreanRatio * Y;
        Camera.setToOrtho(false, X, Y);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    //hide screan, but not destroy
    @Override
    public void hide() {

    }

    //Destroy and free up memory
    @Override
    public void dispose() {
        Manager.atlas.getAtlas().dispose();
        SndUpWoop.dispose();
        SndChika.dispose();
        SndReload.dispose();
        SndShot.dispose();
    }
}
