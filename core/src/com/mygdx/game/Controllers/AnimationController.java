package com.mygdx.game.Controllers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.Controllers.Custom.CustomController;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Custom.ViewControlledObject;

/**
 * Created by DELL on 19.10.2017.
 */

public class AnimationController extends CustomController {
    private int N, W, count = 0;
    private float FPS;
    private float time = 0;
    ViewControlledObject VO;

    public AnimationController(ManagerList parent, int n, int w, float FPS, ViewControlledObject vo) {
        super(parent);
        N = n;
        W = w;
        this.FPS = FPS;
        VO = vo;
    }

    @Override
    public void handle(float dt) {
        time += dt;
        if (1 / FPS < time) {
            time -= 1 / FPS;
            N--;
            count++;
            VO.sprite.setRegion(VO.sprite.getRegionX() + VO.sprite.getRegionWidth(), VO.sprite.getRegionY(), VO.sprite.getRegionWidth(), VO.sprite.getRegionHeight());
            if (count == W) {
                count = 0;
                VO.sprite.setRegion(VO.sprite.getRegionX()-W*VO.sprite.getRegionWidth(), VO.sprite.getRegionY() + VO.sprite.getRegionHeight(), VO.sprite.getRegionWidth(), VO.sprite.getRegionHeight());
            }
            if (N < 1) VO.Parent.ViewList.removeValue(VO, false);
        }
    }
}
