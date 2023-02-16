package com.mygdx.game;


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

public class MenuScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;

    private TextButton play;
    private TextButton play2;
    private Image esquiva;
    private Image tirador;


    public MenuScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640,320));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        esquiva = new Image(game.manager.get("esquiva.png", Texture.class));
        tirador = new Image(game.manager.get("tirador.png", Texture.class));

        play = new TextButton("Jugar",skin);
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        play2 = new TextButton("Jugar",skin);
        play2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });

        esquiva.setPosition(170f-(esquiva.getWidth()/2),220f-(esquiva.getHeight()/2));
        tirador.setPosition(470f-(tirador.getWidth()/2),220f-(tirador.getHeight()/2));

        play.setSize(200f,60f);
        play.setPosition(170f-(play.getWidth()/2),120f-(play.getHeight()/2));
        play2.setSize(200f,60f);
        play2.setPosition(470f-(play.getWidth()/2),120f-(play.getHeight()/2));

        stage.addActor(play);
        stage.addActor(play2);
        stage.addActor(esquiva);
        stage.addActor(tirador);

    }
    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        super.hide();

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
    }
}
