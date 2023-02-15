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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;


import javax.naming.ldap.ManageReferralControl;

import entities.PlayerEntity;

public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    PlayerEntity player;
    private Image[] images;
    private Animation<Image> fondos;

    float stateTime;

    //ArrayList<Body> cuerposABorrar = new ArrayList<Body>();

    public GameScreen(MainGame game){
        super(game);

        stage = new Stage(new FitViewport(640,320));
        world = new World(new Vector2(0,-10),true);

        world.setContactListener(new ContactListener() {

            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB) ||
                        contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                if (areCollided(contact,"player","goomba")){
                    float playerY;
                    float goombaY;
                    if(contact.getFixtureA().getUserData().equals("player")){
                        playerY = contact.getFixtureA().getBody().getPosition().y;
                        goombaY = contact.getFixtureB().getBody().getPosition().y;
                    } else{
                        playerY = contact.getFixtureB().getBody().getPosition().y;
                        goombaY = contact.getFixtureA().getBody().getPosition().y;
                    }
                    if ((playerY-goombaY)> 0.8){
                        if(contact.getFixtureA().getUserData().equals("goomba")){
                            cuerposABorrar.add(contact.getFixtureA().getBody());
                        }
                        if(contact.getFixtureB().getUserData().equals("goomba")){
                            cuerposABorrar.add(contact.getFixtureB().getBody());
                        }
                    }else{
                        playerDie();
                    }


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
                if (areCollided(contact,"player","floor")){
                    player.setJumping(true);
                }
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

        stage.setDebugAll(true);

        Texture texturaPlayer =game.manager.get("mago1.png");
        Texture texturaPlayer2 =game.manager.get("mago2.png");
        Texture texturaPlayerDie= game.manager.get("mariodie.png");
        Texture texturaPlayerWin= game.manager.get("mariowin.png");
        ArrayList<Texture> arrayTexturaPlayer = new ArrayList<Texture>();
        arrayTexturaPlayer.add(texturaPlayer);
        arrayTexturaPlayer.add(texturaPlayer2);
        arrayTexturaPlayer.add(texturaPlayerDie);
        arrayTexturaPlayer.add(texturaPlayerWin);
        images = new Image[2];
        images[0] = new Image(game.manager.<NinePatch>get("fondo1.png"));
        images[1] = new Image(game.manager.<NinePatch>get("fondo2.png"));
        fondos = new Animation<Image>(0.5f,images);


        player = new PlayerEntity(arrayTexturaPlayer,world,new Vector2(0,5f));
        stage.addActor(player);
    }

    public int randomWithRange(int min, int max){
        int range = (max - min) +1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public void render(float delta) {
        super.render(delta);


        stage.act();
        world.step(delta,6,2);
        stateTime += Gdx.graphics.getDeltaTime();
        Image imageActual = fondos.getKeyFrame(stateTime,true);

        stage.draw();


    }

    @Override
    public void hide() {
        super.hide();
        player.detach();
        player.remove();

        floor.detach();
        floor.remove();
        for (int j = 0;j <bloques.length;j++){
            bloques[j].detach();
            bloques[j].remove();
        }

        for (int i = 0;i<goomba.size();i++){
            goomba.get(i).detach();
            goomba.get(i).remove();
        }
        goomba.clear();


        pipe.detach();
        pipe.remove();
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
    public void playerWin(){

        player.setWin(true);

        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameWinScreen);
                    }
                })
        ));
    }


}
