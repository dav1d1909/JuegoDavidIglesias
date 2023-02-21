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

public class GameWinScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;

    private TextButton play;
    private TextButton play2;
    private Image esquiva;


    public GameWinScreen(final MainGame game) {
        super(game);

        stage = new Stage(new FitViewport(640,320));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        esquiva = new Image(game.manager.get("win.png", Texture.class));

        play = new TextButton("Play again",skin);
        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        play2 = new TextButton("Back to menu",skin);
        play2.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.menuScreen);
            }
        });

        esquiva.setPosition(320f-(esquiva.getWidth()/2),250f-(esquiva.getHeight()/2));

        play.setSize(200f,60f);
        play.setPosition(320f-(play.getWidth()/2),130f-(play.getHeight()/2));
        play2.setSize(200f,60f);
        play2.setPosition(320f-(play.getWidth()/2),30f-(play.getHeight()/2));

        stage.addActor(play);
        stage.addActor(play2);
        stage.addActor(esquiva);

    }
    @Override
    public void show() {
        super.show();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.27f,0.1f,0.55f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        super.hide();

    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
    }
}
