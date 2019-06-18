package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Controllers.DestroyController;
import com.mygdx.game.GameLogic.Custom.GameControlledObject;
import com.mygdx.game.GameLogic.Custom.ManagerList;

/**
 * Created by DELL on 29.10.2017.
 */

public class Bonus extends GameControlledObject {
    public Bonus(ManagerList parent, World W,Sprite S) {
        super(parent, W);
        int x=0,y=0;
        while (!parent.GameWalls.walls[x][y]) {
            x = (int) ((Math.random()-0.5)*parent.GameWalls.SizeX+parent.GameWalls.SizeX/2);
            y = (int) ((Math.random()-0.5)*parent.GameWalls.SizeY+parent.GameWalls.SizeY/2);
        }
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x,y);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);
        fdef.shape = shape;
        fdef.density = 2f;
        fdef.restitution = 0f;
        create(W,def,fdef);
        body.setLinearDamping(20);
        body.setAngularDamping(20);
        sprite = S;
        sprite.setSize(1,1);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);

        controller = new DestroyController(parent,body);
    }
}
