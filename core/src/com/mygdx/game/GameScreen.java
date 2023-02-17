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
import entities.FondoEntity;
import entities.PlayerEntity;


public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    PlayerEntity player;
    FloorEntity floor;
    FloorEntity[] paredes;
    ArrayList<BatEntity> bats;
    FondoEntity fondo;

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
                if (areCollided(contact,"player","bat")){
                    if (!player.isDeslizando()){
                        playerDie();
                    }else{
                        if (contact.getFixtureA().getUserData().equals("player")){
                            cuerposABorrar.add(contact.getFixtureB().getBody());
                        }else{
                            cuerposABorrar.add(contact.getFixtureA().getBody());
                        }
                    }

                }
                if (areCollided(contact,"bat","floor")){
                    if (contact.getFixtureA().getUserData().equals("floor")){
                        cuerposABorrar.add(contact.getFixtureB().getBody());
                    }else{
                        cuerposABorrar.add(contact.getFixtureA().getBody());
                    }
                }
                //if (areCollided(contact,"player","floor")){
                //    player.setJumping(false);
                //}
               // if (areCollided(contact,"player","pipe")){
               //     playerWin();
                //}


            }

            @Override
            public void endContact(Contact contact) {
             //   if (areCollided(contact,"player","floor")){
                   // player.setJumping(true);
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

        stage.setDebugAll(false);

        Texture texturaPlayer =game.manager.get("mago1.png");
        Texture texturaPlayer2 =game.manager.get("mago2.png");
        Texture texturaPlayer3 =game.manager.get("magoDie.png");
        Texture texturaPlayer4 =game.manager.get("magoJump.png");
        Texture texturaPlayer5 =game.manager.get("magoDesliz.png");
        Texture texturaPlayer6 =game.manager.get("magoWin.png");
        ArrayList<Texture> arrayTexturaPlayer = new ArrayList<Texture>();
        arrayTexturaPlayer.add(texturaPlayer);
        arrayTexturaPlayer.add(texturaPlayer2);
        arrayTexturaPlayer.add(texturaPlayer3);
        arrayTexturaPlayer.add(texturaPlayer4);
        arrayTexturaPlayer.add(texturaPlayer5);
        arrayTexturaPlayer.add(texturaPlayer6);
        player = new PlayerEntity(arrayTexturaPlayer,world,new Vector2(5f,1f));

        Texture texturaFondo =game.manager.get("fondo1.png");
        Texture texturaFondo2 =game.manager.get("fondo2.png");
        ArrayList<Texture> arrayTexturaFondo = new ArrayList<Texture>();
        arrayTexturaFondo.add(texturaFondo);
        arrayTexturaFondo.add(texturaFondo2);
        fondo = new FondoEntity(arrayTexturaFondo,world,new Vector2(0,0),640f,320);

        Texture texturaFloor = game.manager.get("floor.png");
        floor = new FloorEntity(texturaFloor,world,new Vector2(3.56f,0),7.12f,1);
        paredes = new FloorEntity[2];
        paredes[0] = new FloorEntity(texturaFloor,world,new Vector2(2.56f,0),1f,7.20f);
        paredes[1] = new FloorEntity(texturaFloor,world,new Vector2(10.67f,0),1f,7.20f);

        Texture texturaBat = game.manager.get("bat.png");
        bats = new ArrayList<BatEntity>();
        bats.add(new BatEntity(texturaBat,world,new Vector2(5f,6f))) ;
        bats.add(new BatEntity(texturaBat,world,new Vector2(9f,6f)));

        stage.addActor(fondo);

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

        stage.act();
        world.step(delta,6,2);
        stage.draw();

        for (Body b:
             cuerposABorrar) {
            for (int i = 0;i< bats.size();i++){
                if (bats.get(i).body.equals(b)){
                    bats.get(i).die = true;
                    bats.get(i).detach();
                    bats.get(i).remove();
                    bats.remove(i);
                }
            }
        }
        cuerposABorrar.clear();




    }

    @Override
    public void hide() {
        super.hide();

        player.detach();
        player.remove();

        fondo.remove();
        fondo.clear();

        floor.detach();
        floor.remove();

        for (int j = 0;j <paredes.length;j++){
            paredes[j].detach();
            paredes[j].remove();
        }

        for (int j = 0;j <bats.size();j++){
            bats.get(j).detach();
            bats.get(j).remove();
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
