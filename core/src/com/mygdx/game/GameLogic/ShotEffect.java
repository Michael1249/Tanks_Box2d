package com.mygdx.game.GameLogic;

import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Custom.ViewControlledObject;

/**
 * Created by DELL on 20.10.2017.
 */

public class ShotEffect extends ViewControlledObject {
    public ShotEffect(ManagerList parent, float X, float Y) {
        super(parent, 3, 4 ,100f);

        sprite.setRegion(Parent.atlas.getAtlas().findRegion("ShotEffect"));
        sprite.setRegionWidth(128);
        sprite.setRegionHeight(64);
        sprite.setSize(1.5f,1f);
        sprite.setOrigin(sprite.getWidth() / 2,sprite.getHeight() / 2);
        sprite.setPosition(X - sprite.getWidth() / 2,Y - sprite.getHeight() / 2);


    }
}
