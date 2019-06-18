package com.mygdx.game.Controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Controllers.Custom.CustomController;
import com.mygdx.game.GameLogic.Custom.ManagerList;
import com.mygdx.game.GameLogic.Tank;

/**
 * Created by DELL on 21.10.2017.
 */

public class ManagerController extends CustomController {
    private OrthographicCamera Camera;

    public ManagerController(ManagerList parent) {
        super(parent);
        Camera = parent.Camera;
    }

    @Override
    public void handle(float dt) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)&&Parent.Player==null) Parent.createplayer((byte)(Math.random()*2));
        if (Gdx.input.isKeyPressed(Input.Keys.HOME)) Camera.zoom = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) Camera.zoom *= 0.95;
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) Camera.zoom /= 0.95;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_RIGHT)) {
            Parent.RenderIndex++;
            if (Parent.RenderIndex > 2) Parent.RenderIndex = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
            Camera.position.x -= Camera.zoom / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
            Camera.position.x += Camera.zoom / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W))
            Camera.position.y += Camera.zoom / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            Camera.position.y -= Camera.zoom / 3;
    }

}
