package com.mygdx.game.Controllers;
import com.mygdx.game.GameLogic.Ammo;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.GameType;
import com.mygdx.game.GameLogic.Health;
import com.mygdx.game.GameLogic.Walls;

/**
 * Created by DELL on 28.10.2017.
 */

public class TeamVsTeam extends GameType {
    float ScoreT1=0,ScoreT2=0;
    private float SpawnIndex=0.2f;

    public TeamVsTeam(ManagerList parent) {
        super(parent);
        Parent.GameWalls = new Walls(Parent,Parent.world,50,50);

    }

    @Override
    public void handle(float dt) {
        if (Math.random()<SpawnIndex*dt){
            int x=0,y=0;
            while (!Parent.GameWalls.walls[x][y]) {
                x = (int) ((Math.random()-0.5)*Parent.GameWalls.SizeX+Parent.GameWalls.SizeX/2);
                y = (int) ((Math.random()-0.5)*Parent.GameWalls.SizeY+Parent.GameWalls.SizeY/2);
            }
            Parent.GameObjList.add(new Ammo(Parent,Parent.world));
            x=0;
            y=0;
            while (!Parent.GameWalls.walls[x][y]) {
                x = (int) ((Math.random()-0.5)*Parent.GameWalls.SizeX+Parent.GameWalls.SizeX/2);
                y = (int) ((Math.random()-0.5)*Parent.GameWalls.SizeY+Parent.GameWalls.SizeY/2);
            }
            Parent.GameObjList.add(new Health(Parent,Parent.world));

            for (byte i=0;i<6;i++) {
                Parent.createAI((byte) (i%2),1.0f);
            }
        }
    }

}
