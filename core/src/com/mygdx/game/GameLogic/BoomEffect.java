package com.mygdx.game.GameLogic;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Custom.ViewControlledObject;

/**
 * Created by DELL on 19.10.2017.
 */

public class BoomEffect extends ViewControlledObject {

    public BoomEffect(ManagerList parent,float X,float Y) {
        super(parent, 14, 4 ,30f);

        sprite.setRegion(Parent.atlas.getAtlas().findRegion("Boom2"));
        sprite.setRegionWidth(55);
        sprite.setRegionHeight(56);
        sprite.setSize(2f,2f);
        sprite.setPosition(X - sprite.getWidth() / 2f,Y - sprite.getHeight() / 2+0.05f);


    }
}
