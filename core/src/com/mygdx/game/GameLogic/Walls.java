package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameLogic.Custom.GameObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 16.10.2017.
 */

public class Walls extends GameObject {
    private FixtureDef fdef;
    final int Dirt = 9;
    public int SizeX;
    public int SizeY;
    private int RX, RY;
    public boolean[][] walls;


    public Walls(ManagerList parent, World W, float x, float y) {
        super(parent, W);
        SizeX = (int) x;
        SizeY = (int) y;
        walls = new boolean[SizeY][SizeY];
        sprite.setRegion(Parent.atlas.getAtlas().findRegion("Walls"));
        RX = Parent.atlas.getAtlas().findRegion("Walls").getRegionX();
        RY = Parent.atlas.getAtlas().findRegion("Walls").getRegionY();
        createwalls();

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.49f, 0.49f);
        fdef.shape = shape;
        fdef.restitution = 0.5f;
        fdef.friction = 0.0f;
        for (int i = 0; i < SizeX; i++) {
            for (int j = 0; j < SizeY; j++) {
                if (!walls[i][j]) CreateBody(W, i, j);
            }
        }
    }

    private void createwalls() {
        for (int i = 0; i < SizeX; i++) {
            for (int j = 0; j < SizeY; j++)
                walls[i][j] = false;
        }

        for (int i = 0; i < SizeX * SizeY / 18; i++) {
            int x,y,w,h;
            do {
                float d = (float) Math.random();
                w = 1 + (int) (d * Dirt);
                h = 1 + (int) ((1 - d) * Dirt);
                x = 2 + (int) (Math.random() * (SizeX - w - 3));
                y = 2 + (int) (Math.random() * (SizeY - h - 3));
            }while (i > 0&&!Goodplace(x,y,w,h));
            for (int k = x; k < x + w; k++)
                for (int l = y; l < y + h; l++) {
                    walls[k][l] = true;
                }
        }
    }
    private boolean Goodplace(int x, int y, int w, int h){
        boolean flag=false;
        int c=0;
        for (int i=x-1;i<x+w+1;i++)
         for (int j=y-1;j<y+h+1;j++){
             if (!((i==x-1&&j==y-1)||(i==x+w&&j==y-1)||(i==x-1&&j==y+h)||(i==x+w&&j==y+h)))
             if (walls[i][j]) c++;
         }
        if (c<(w+2)*(h+2)*0.5&&c!=0) flag = true;
        return flag;
    }
    private void CreateBody(World W, float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x + 0.5f, y + 0.5f);


        Body B;
        B = W.createBody(def);
        B.setUserData(this);
        B.createFixture(fdef);
    }

    private Vector2 GetAtlasPos(int x, int y) {
        Vector2 r = new Vector2(0, 0);
        boolean[] f = new boolean[8];
        boolean w1, w2, w3, w4;
        f[0] = walls[x + 1][y];
        f[1] = walls[x + 1][y + 1];
        f[2] = walls[x][y + 1];
        f[3] = walls[x - 1][y + 1];
        f[4] = walls[x - 1][y];
        f[5] = walls[x - 1][y - 1];
        f[6] = walls[x][y - 1];
        f[7] = walls[x + 1][y - 1];
        w1 = !f[0] && f[1] && !f[2];
        w2 = !f[2] && f[3] && !f[4];
        w3 = !f[4] && f[5] && !f[6];
        w4 = !f[6] && f[7] && !f[0];
        if (w1 || w2 || w3 || w4) {
            r.y = 4;

            byte i = 0;
            if (f[0]) i++;
            if (f[2]) i++;
            if (f[4]) i++;
            if (f[6]) i++;
            if (i == 0) {
                if (w4) r.x += 2;
                if (w1) r.x += 1;
                if (w2) r.y += 2;
                if (w3) r.y += 1;
            } else if (i == 1) {
                if (f[0]) {
                    r.y = 9;
                    if (w2) r.x += 2;
                    if (w3) r.x += 1;
                } else if (f[2]) {
                    r.y = 8;
                    if (w3) r.x += 2;
                    if (w4) r.x += 1;
                } else if (f[4]) {
                    r.y = 11;
                    if (w4) r.x += 2;
                    if (w1) r.x += 1;
                } else if (f[6]) {
                    r.y = 10;
                    if (w1) r.x += 2;
                    if (w2) r.x += 1;
                }
            } else {
                r.x = 0;
                if (w1) r.y = 8;
                else if (w4) r.y = 9;
                else if (w3) r.y = 10;
                else if (w2) r.y = 11;
            }
        } else {
            if (f[0]) r.x += 2;
            if (f[2]) r.x += 1;
            if (f[4]) r.y += 2;
            if (f[6]) r.y += 1;
        }
        return r;
    }

    @Override
    public void draw(SpriteBatch batch) {
        float X = Parent.Camera.position.x;
        float Y = Parent.Camera.position.y;
        float DX = Parent.Camera.viewportWidth * Parent.Camera.zoom + 1;
        float DY = Parent.Camera.viewportHeight * Parent.Camera.zoom + 1;
        for (int i = (int) (X - DX); i < X + DX; i++) {
            for (int j = (int) (Y - DY); j < Y + DY; j++) {
                if ((i >= 1) && (i < SizeX - 1) && (j >= 1) && (j < SizeY - 1)) {
                    if (walls[i][j])
                        batch.draw(Parent.atlas.getAtlas().findRegion("Tile"), i, j, 1, 1);
                    else {
                        Vector2 V;
                        V = GetAtlasPos(i, j);

                        sprite.setRegion((int) (RX + V.x * 8), (int) (RY + V.y * 8), 8, 8);
                        batch.draw(sprite, i, j, 1, 1);
                    }

                } else {
                    sprite.setRegion(RX, RY, 8, 8);
                    batch.draw(sprite, i, j, 1, 1);
                }
            }
        }
    }
}
