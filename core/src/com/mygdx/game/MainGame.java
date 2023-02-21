package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    public GameScreen gameScreen;
    public GameScreen2 gameScreen2;
    public GameWinScreen gameWinScreen;
    public GameWinScreen2 gameWinScreen2;
    public GameOverScreen gameOverScreen;
    public GameOverScreen2 gameOverScreen2;
    public AssetManager manager;
    public MenuScreen menuScreen;

    @Override
    public void create() {

        manager = new AssetManager();

        manager.load("title.png", Texture.class);
        manager.load("esquiva.png", Texture.class);
        manager.load("tirador.png", Texture.class);
        manager.load("floor.png", Texture.class);
        manager.load("fondo.png", Texture.class);
        manager.load("mago1.png", Texture.class);
        manager.load("mago2.png", Texture.class);
        manager.load("mago3.png", Texture.class);
        manager.load("mago4.png", Texture.class);
        manager.load("magoDie.png", Texture.class);
        manager.load("magoJump.png", Texture.class);
        manager.load("magoDesliz.png", Texture.class);
        manager.load("magoShoot.png", Texture.class);
        manager.load("magoWin.png", Texture.class);
        manager.load("win.png", Texture.class);
        manager.load("bat1.png", Texture.class);
        manager.load("bat2.png", Texture.class);
        manager.load("bat3.png", Texture.class);
        manager.load("zombie1.png", Texture.class);
        manager.load("zombie2.png", Texture.class);
        manager.load("zombie3.png", Texture.class);
        manager.load("skeleton1.png", Texture.class);
        manager.load("skeleton2.png", Texture.class);
        manager.load("skeleton3.png", Texture.class);

        manager.finishLoading();


        gameScreen = new GameScreen(this);
        gameScreen2 = new GameScreen2(this);
        gameWinScreen2 = new GameWinScreen2(this);
        gameWinScreen = new GameWinScreen(this);
        gameOverScreen2 = new GameOverScreen2(this);
        gameOverScreen = new GameOverScreen(this);
        menuScreen = new MenuScreen(this);

        setScreen(menuScreen);

    }
}
