package com.ninjagame.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ninjagame.MainGame;

public class PantallaGameOver extends PantallaBase{

    private Stage stage;

    private Skin skin;


    private TextButton retry, menu;

    public PantallaGameOver(final MainGame game) {
        super(game);

        // Create a new stage, as usual.
        stage = new Stage(new FitViewport(1280, 720));

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        retry = new TextButton("REINTENTAR", skin);
        menu = new TextButton("MENU", skin);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.pantalla1);
            }
        });

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.pantallaMenu);
            }
        });

        retry.setSize(200, 80);
        menu.setSize(200, 80);
        retry.setPosition(360, 350);
        menu.setPosition(580, 350);

        stage.addActor(retry);
        stage.addActor(menu);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        // Just render things.
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
