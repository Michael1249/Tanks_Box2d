package com.mygdx.game.GameLogic.Custom;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 Класс визуальных еффектов не взаимодействующих с игровым миром
 */

public class ViewObject {
    public Sprite sprite;
    public ManagerList Parent;
    public ViewObject(ManagerList parent) {
        this.Parent = parent;
        sprite = new Sprite();
    }
    public void draw(SpriteBatch batch){
        if (Parent.Camera.frustum.boundsInFrustum(sprite.getX(),sprite.getY(),0,sprite.getWidth(),sprite.getHeight(),0) )
        sprite.draw(batch);
    }

}
