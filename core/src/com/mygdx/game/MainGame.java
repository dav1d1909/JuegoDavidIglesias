package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class MainGame extends Game {

    public GameScreen gameScreen;
    public AssetManager manager;
    public MenuScreen menuScreen;

    @Override
    public void create() {

        manager = new AssetManager();

        manager.load("esquiva.png", Texture.class);
        manager.load("tirador.png", Texture.class);
        manager.load("floor.png", Texture.class);
        manager.load("fondo1.png", Texture.class);
        manager.load("fondo2.png", Texture.class);
        manager.load("mago1.png", Texture.class);
        manager.load("mago2.png", Texture.class);
        manager.finishLoading();


        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);

        setScreen(menuScreen);

    }
}
