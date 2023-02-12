package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

import java.util.ArrayList;

import entities.FloorEntity;
import entities.GoombaEntity;
import entities.PipeEntity;
import entities.PlayerEntity;

public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    PlayerEntity player;
    FloorEntity floor;
    FloorEntity[] bloques = new FloorEntity[30];

    ArrayList<GoombaEntity> goomba = new ArrayList<>();
    PipeEntity pipe;

    ArrayList<Body> cuerposABorrar = new ArrayList<Body>();

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
        Texture texturaFloor = game.manager.get("floor.jpg");
        Texture texturaPipe = game.manager.get("pipe.png");
        Texture texturaGoomba = game.manager.get("goomba.png");

        player = new PlayerEntity(arrayTexturaPlayer,world,new Vector2(0,5f));
        floor = new FloorEntity(texturaFloor,world,new Vector2(0,0),300,1);
        for (int j = 0;j <bloques.length;j++){
            int positionX  = randomWithRange(6,255);
            int positionY  = randomWithRange(3,6);
            int width  = randomWithRange(1,5);
            int height  = randomWithRange(1,2);
            bloques[j]  = new FloorEntity(texturaFloor,world,new Vector2(positionX,positionY),width,height);

        }
        for (int i = 0;i <30;i++){
            int positionX  = randomWithRange(6,255);
             GoombaEntity g = new GoombaEntity(texturaGoomba,world,new Vector2(positionX,1.5f),player);
             goomba.add(g);
        }

        pipe = new PipeEntity(texturaPipe,world,new Vector2(260f,2f));

        stage.addActor(player);
        stage.addActor(pipe);
        stage.addActor(floor);
        for (int j = 0;j <bloques.length;j++){
            stage.addActor(bloques[j]);

        }
        for (int i = 0;i <goomba.size();i++){
            stage.addActor(goomba.get(i));
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

        stage.act();
        world.step(delta,6,2);
        stage.draw();

        for (Body b: cuerposABorrar) {
            for (int i = 0;i< goomba.size();i++){
                 if (goomba.get(i).body.equals(b)) {
                     goomba.get(i).die = true;

                     goomba.get(i).detach();
                     goomba.get(i).remove();
                     goomba.remove(i);

                 }
        }
        }
        cuerposABorrar.clear();
        if (player.getX()>150){
            stage.getCamera().position.x = player.getX() + 170;
        } else{
            stage.getCamera().position.x = 320;
        }

        stage.getCamera().update();
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
