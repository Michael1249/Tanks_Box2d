package com.mygdx.game.GameLogic.Custom;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Controllers.Custom.CustomController;
import com.mygdx.game.Controllers.ManagerController;
import com.mygdx.game.Controllers.TankController;
import com.mygdx.game.Controllers.TankPlayerController_Android;
import com.mygdx.game.Controllers.TankPlayerController_Desktop;
import com.mygdx.game.GameLogic.AI_TankController;
import com.mygdx.game.GameLogic.GameType;
import com.mygdx.game.GameLogic.Tank;
import com.mygdx.game.Controllers.TeamVsTeam;
import com.mygdx.game.GameLogic.Walls;
import com.mygdx.game.Utils.Atlas;

/**
 Список всех существующих в логике игры обьектов
  - Игровые обьекты
  - Визуальные
  - Контроллеры
 */

public class ManagerList {
    //списки обьектов
    public Array<GameObject> GameObjList;
    public Array<ViewObject> ViewList;
    public Array<CustomController> ControlList;
    //игровой режим. управляет внутриигровыми событиями
    public GameType GameManager;
    public Tank Player;
    public OrthographicCamera Camera;
    public World world;
    //стены
    public Walls GameWalls;
    //атлас текстур
    public Atlas atlas;
    //режим прорисовки
    public Byte RenderIndex=1;
    //инструмент отладки,расширяет возможности
    public ManagerController Controller;

    public ManagerList(OrthographicCamera C, World W) {
        Camera = C;
        world = W;
        atlas = new Atlas();

        GameObjList = new Array<GameObject>();
        ViewList = new Array<ViewObject>();
        ControlList = new Array<CustomController>();

        GameManager = new TeamVsTeam(this);
        createplayer((byte) (Math.random()*2));

    }

    public void update(float dt) {
        System.out.println("Simulate_Info_=========================");
        System.out.println("_FPS: "+1/dt);

        if (Controller != null) Controller.handle(dt);
        GameManager.handle(dt);

        for (CustomController CO : ControlList) {
            CO.handle(dt);
        }

        if (Player !=null)Player.update(dt);

        for (GameObject GO : GameObjList) {
            if (GO instanceof GameControlledObject) ((GameControlledObject) GO).update(dt);
        }

        for (ViewObject VO : ViewList) {
            if (VO instanceof ViewControlledObject) ((ViewControlledObject) VO).update(dt);
        }


    }

    public void draw(SpriteBatch batch) {
        GameWalls.draw(batch);
        for (GameObject GO : GameObjList) {
            GO.draw(batch);
        }
        if (Player !=null)Player.draw(batch);
        for (ViewObject VO : ViewList) {
            VO.draw(batch);
        }
    }
    public void createplayer(byte team){
        int x=0,y=0;
        while (!GameWalls.walls[x][y]) {
            x = (int) ((Math.random()-0.5)*GameWalls.SizeX+GameWalls.SizeX/2);
            y = (int) ((Math.random()-0.5)*GameWalls.SizeY+GameWalls.SizeY/2);
        }
        Tank tank = new Tank(this,world,x+0.5f,y+0.5f);
        tank.controller = new TankPlayerController_Desktop(this,tank.body);
        Player = tank;
        switch (team){
            case 0:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Red")); break;
            case 1:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Blue")); break;
            case 2:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Green")); break;
            case 3:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Grey")); break;
        }
        ((TankController)tank.controller).Team=team;
        Camera.zoom = 1;
    }
    public void createAI(byte team,float level){
        int x=0,y=0;
        while (!GameWalls.walls[x][y]) {
            x = (int) ((Math.random()-0.5)*GameWalls.SizeX+GameWalls.SizeX/2);
            y = (int) ((Math.random()-0.5)*GameWalls.SizeY+GameWalls.SizeY/2);
        }
        Tank tank = new Tank(this,world,x+0.5f,y+0.5f);
        GameObjList.add(tank);
        switch (team){
            case 0:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Red")); break;
            case 1:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Blue")); break;
            case 2:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Green")); break;
            case 3:tank.sprite.setRegion(atlas.getAtlas().findRegion("Tank_Grey")); break;
        }
        tank.controller = new AI_TankController(this,tank.body,level);
        ((TankController)tank.controller).Team=team;
    }
}
