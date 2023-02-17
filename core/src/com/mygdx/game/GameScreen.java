package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

import entities.BatEntity;
import entities.FloorEntity;
import entities.PlayerEntity;


public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    PlayerEntity player;
    FloorEntity floor;
    FloorEntity[] paredes;
    BatEntity[] bats;
    private Image fondo;
    private Image fondo2;
    float color1;
    float color2;


    float stateTime;

    //ArrayList<Body> cuerposABorrar = new ArrayList<Body>();

    public GameScreen(MainGame game){
        super(game);

        stage = new Stage(new FitViewport(640,320));
        world = new World(new Vector2(0,-10),true);

        fondo = new Image(game.manager.get("fondo1.png", Texture.class));
        fondo.setPosition(0,0);
        fondo.setSize(640f,320f);

        fondo2 = new Image(game.manager.get("fondo2.png", Texture.class));
        fondo2.setPosition(0,0);
        fondo2.setSize(640f,320f);
        color1 = fondo.getColor().a;
        color2 = fondo2.getColor().a;
        fondo2.getColor().a = 0f;

        stateTime = 0f;

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact,"player","bat")){
                    playerDie();


                }
                if (areCollided(contact,"player","floor")){
                    player.setJumping(false);
                }
                if (areCollided(contact,"player","pipe")){
                    playerWin();
                }


            }

            @Override
            public void endContact(Contact contact) {
             //   if (areCollided(contact,"player","floor")){
                    player.setJumping(true);
               // }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

    }
    @Override
    public void show() {
        super.show();

        //para ver el cuadradito verde de las texturas
        stage.setDebugAll(true);

        Texture texturaPlayer =game.manager.get("mago1.png");
        Texture texturaPlayer2 =game.manager.get("mago2.png");
        Texture texturaPlayer3 =game.manager.get("magoDie.png");
        ArrayList<Texture> arrayTexturaPlayer = new ArrayList<Texture>();
        arrayTexturaPlayer.add(texturaPlayer);
        arrayTexturaPlayer.add(texturaPlayer2);
        arrayTexturaPlayer.add(texturaPlayer3);
        player = new PlayerEntity(arrayTexturaPlayer,world,new Vector2(5f,1f));

        Texture texturaFloor = game.manager.get("floor.png");
        floor = new FloorEntity(texturaFloor,world,new Vector2(3.56f,0),7.12f,1);
        paredes = new FloorEntity[2];
        paredes[0] = new FloorEntity(texturaFloor,world,new Vector2(2.56f,0),1f,7.20f);
        paredes[1] = new FloorEntity(texturaFloor,world,new Vector2(10.67f,0),1f,7.20f);

        Texture texturaBat = game.manager.get("bat.png");
        bats = new BatEntity[2];
        bats[0] = new BatEntity(texturaBat,world,new Vector2(5f,6f));
        bats[1] = new BatEntity(texturaBat,world,new Vector2(9f,6f));




        stage.addActor(fondo);
        //stage.addActor(fondo2);

        stage.addActor(player);
        stage.addActor(floor);
        for (FloorEntity f:
             paredes) {
            stage.addActor(f);
        }
        for (BatEntity b:
        bats){
            stage.addActor(b);
        }


    }

    public int randomWithRange(int min, int max){
        int range = (max - min) +1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //cambiarFondo();

        stage.act();
        world.step(delta,6,2);
        stage.draw();



    }

    @Override
    public void hide() {
        super.hide();
        player.detach();
        player.remove();

        fondo.remove();
        fondo.clear();
        //fondo2.remove();
        //fondo2.clear();

        floor.detach();
        floor.detach();

        for (int j = 0;j <paredes.length;j++){
            paredes[j].detach();
            paredes[j].remove();
        }

        for (int j = 0;j <bats.length;j++){
            bats[j].detach();
            bats[j].remove();
        }


    }

    @Override
    public void dispose() {
        super.dispose();

        stage.dispose();
        world.dispose();

    }

    public void playerDie(){

        player.setDie(true);

        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameOverScreen);
                    }
                })
        ));
    }
    public synchronized void cambiarFondo(){

            if (fondo2.getColor().a == 0f){
                fondo2.addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                fondo2.getColor().a = color2;
                                fondo.getColor().a = 0f;
                            }
                        })
                ));

            } else if(fondo.getColor().a == 0f){
                fondo.addAction(Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                fondo.getColor().a = color1;
                                fondo2.getColor().a = 0f;
                            }
                        })
                ));
            }
    }
    public void playerWin(){

        player.setWin(true);

        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                    //    game.setScreen(game.gameWinScreen);
                    }
                })
        ));
    }


}
