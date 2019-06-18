package com.mygdx.game.Utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by DELL on 18.10.2017.
 */

public class Atlas {
    private AssetManager manager;

    public Atlas() {
        manager = new AssetManager();
        manager.load("GameAtlas.atlas", TextureAtlas.class);
        manager.finishLoading();
    }

    public TextureAtlas getAtlas() {
        return manager.get("GameAtlas.atlas",TextureAtlas.class);
    }
}
