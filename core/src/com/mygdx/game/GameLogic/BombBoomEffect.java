package com.mygdx.game.GameLogic;

import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Custom.ViewControlledObject;
import com.mygdx.game.GameLogic.Custom.ViewObject;

/**
 * Created by DELL on 20.10.2017.
 */

public class BombBoomEffect extends ViewControlledObject {
    public BombBoomEffect(ManagerList parent,float X,float Y) {
        super(parent, 6, 6 ,12f);

        sprite.setRegion(Parent.atlas.getAtlas().findRegion("Boom"));
        sprite.setRegionWidth(128);
        sprite.setRegionHeight(128);
        sprite.setSize(5f,5f);
        sprite.setPosition(X - sprite.getWidth() / 2f,Y - sprite.getHeight() / 2);


    }
}
