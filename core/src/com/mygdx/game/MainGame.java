package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    public GameScreen gameScreen;
    public AssetManager manager;
    public MenuScreen menuScreen;
    public GameOverScreen gameOverScreen;
    public GameWinScreen gameWinScreen;
    @Override
    public void create() {

        manager = new AssetManager();
        manager.load("mago1.png", Texture.class);
        manager.load("mago2.png", Texture.class);
        manager.load("floor.jpg", Texture.class);
        manager.load("goomba.png", Texture.class);
        manager.load("mariodie.png", Texture.class);
        manager.load("mariowin.png", Texture.class);
        manager.load("win.png", Texture.class);
        manager.load("title.png", Texture.class);
        manager.load("gameover.png", Texture.class);
        manager.load("pipe.png", Texture.class);
        manager.finishLoading();


        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        gameOverScreen = new GameOverScreen(this);
        gameWinScreen = new GameWinScreen(this);

        setScreen(menuScreen);

    }
}
