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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

import entities.BatEntity;
import entities.FloorEntity;
import entities.FondoEntity;
import entities.PlayerEntity2;
import entities.SkeletonEntity;
import entities.ZombieEntity;


public class GameScreen2 extends BaseScreen{

    private Skin skin;
    public TextArea eRestantes;
    private Stage stage;
    private World world;
    PlayerEntity2 player;
    FloorEntity floor;
    FloorEntity[] paredes;
    ArrayList<BatEntity> bats;
    ArrayList<SkeletonEntity> skeletons;

    FondoEntity fondo;
    int enemigos = 30;

    ArrayList<Body> batsABorrar = new ArrayList<Body>();
    ArrayList<Body> skeletonsABorrar = new ArrayList<Body>();

    public GameScreen2(MainGame game){
        super(game);

        stage = new Stage(new FitViewport(640,320));
        world = new World(new Vector2(0,-10),true);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        eRestantes = new TextArea("Enemigos"+enemigos,skin);
        eRestantes.setX(5);
        eRestantes.setY(270);
        eRestantes.setHeight(30);
        eRestantes.setWidth(190);

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
                        enemigos--;
                        if (contact.getFixtureA().getUserData().equals("player")){
                            batsABorrar.add(contact.getFixtureB().getBody());
                        }else{
                            batsABorrar.add(contact.getFixtureA().getBody());
                        }
                    }

                }
                if (areCollided(contact,"bat","floor")){
                    if (contact.getFixtureA().getUserData().equals("floor")){
                        batsABorrar.add(contact.getFixtureB().getBody());
                    }else{
                        batsABorrar.add(contact.getFixtureA().getBody());
                    }
                }
                if (areCollided(contact,"player","skeleton")){
                    if(!player.isShooting()){
                        playerDie();
                    } else {
                        enemigos--;
                        if (contact.getFixtureA().getUserData().equals("player")) {
                            skeletonsABorrar.add(contact.getFixtureB().getBody());
                        } else {
                            skeletonsABorrar.add(contact.getFixtureA().getBody());
                        }
                    }
                }
                if (areCollided(contact,"skeleton","floor")){
                    if (contact.getFixtureA().getUserData().equals("floor")){
                        skeletonsABorrar.add(contact.getFixtureB().getBody());
                    }else{
                        skeletonsABorrar.add(contact.getFixtureA().getBody());
                    }
                }


            }

            @Override
            public void endContact(Contact contact) {
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
        this.enemigos = 30;
        stage.setDebugAll(false);

        Texture texturaPlayer =game.manager.get("mago1.png");
        Texture texturaPlayer2 =game.manager.get("mago2.png");
        Texture texturaPlayer3 =game.manager.get("magoDie.png");
        Texture texturaPlayer4 =game.manager.get("magoShoot.png");
        Texture texturaPlayer5 =game.manager.get("magoDesliz.png");
        Texture texturaPlayer6 =game.manager.get("magoWin.png");
        Texture texturaPlayer7 =game.manager.get("mago3.png");
        Texture texturaPlayer8 =game.manager.get("mago4.png");
        ArrayList<Texture> arrayTexturaPlayer = new ArrayList<Texture>();
        arrayTexturaPlayer.add(texturaPlayer);
        arrayTexturaPlayer.add(texturaPlayer2);
        arrayTexturaPlayer.add(texturaPlayer3);
        arrayTexturaPlayer.add(texturaPlayer4);
        arrayTexturaPlayer.add(texturaPlayer5);
        arrayTexturaPlayer.add(texturaPlayer6);
        arrayTexturaPlayer.add(texturaPlayer7);
        arrayTexturaPlayer.add(texturaPlayer8);
        player = new PlayerEntity2(game,stage,arrayTexturaPlayer,world,new Vector2(5f,1f));

        Texture texturaFondo =game.manager.get("fondo.png");
        fondo = new FondoEntity(texturaFondo,world,new Vector2(0,0),640f,320);

        Texture texturaFloor = game.manager.get("floor.png");
        floor = new FloorEntity(texturaFloor,world,new Vector2(3.56f,0),7.12f,1);
        paredes = new FloorEntity[2];
        paredes[0] = new FloorEntity(texturaFloor,world,new Vector2(2.56f,0),1f,7.20f);
        paredes[1] = new FloorEntity(texturaFloor,world,new Vector2(10.67f,0),1f,7.20f);

        Texture texturaBat1 = game.manager.get("bat1.png");
        Texture texturaBat2 = game.manager.get("bat2.png");
        Texture texturaBat3 = game.manager.get("bat3.png");
        ArrayList<Texture> arrayTexturaBat = new ArrayList<Texture>();
        arrayTexturaBat.add(texturaBat1);
        arrayTexturaBat.add(texturaBat2);
        arrayTexturaBat.add(texturaBat3);

        Texture texturaSkeleton1 = game.manager.get("skeleton1.png");
        Texture texturaSkeleton2 = game.manager.get("skeleton2.png");
        Texture texturaSkeleton3 = game.manager.get("skeleton3.png");
        ArrayList<Texture> arrayTexturaSkeleton = new ArrayList<Texture>();
        arrayTexturaSkeleton.add(texturaSkeleton1);
        arrayTexturaSkeleton.add(texturaSkeleton2);
        arrayTexturaSkeleton.add(texturaSkeleton3);

        bats = new ArrayList<BatEntity>();
        skeletons = new ArrayList<SkeletonEntity>();
        for (int i = 0; i< 30; i++){
            bats.add(new BatEntity(arrayTexturaBat,world,new Vector2(randomWithRange(5,9),randomWithRange(6,100)))) ;
            skeletons.add(new SkeletonEntity(arrayTexturaSkeleton,world,new Vector2(randomWithRange(5,9),randomWithRange(6,100)))) ;
        }


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
        for (SkeletonEntity z:
             skeletons) {
            stage.addActor(z);
        }
        stage.addActor(eRestantes);


    }

    public int randomWithRange(int min, int max){
        int range = (max - min) +1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.27f,0.1f,0.55f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        world.step(delta,6,2);
        stage.draw();
        eRestantes.setText("Enemigos restantes: "+enemigos);
        if (enemigos == 0){
            playerWin();
        } else if (enemigos ==25){
            Constants.setEnemySpeed(1.5f);
        } else if (enemigos == 15){
            Constants.setEnemySpeed(2f);
        } else if (enemigos == 8){
            Constants.setEnemySpeed(2.5f);
        }

        for (Body b:
             batsABorrar) {
            for (int i = 0;i< bats.size();i++){
                if (bats.get(i).body.equals(b)){
                    bats.get(i).die = true;
                    bats.get(i).detach();
                    bats.get(i).remove();
                    bats.remove(i);
                }
            }
        }
        batsABorrar.clear();

        for (Body b:
                skeletonsABorrar) {
            for (int i = 0;i< skeletons.size();i++){
                if (skeletons.get(i).body.equals(b)){
                    skeletons.get(i).die = true;
                    skeletons.get(i).detach();
                    skeletons.get(i).remove();
                    skeletons.remove(i);
                }
            }
        }
        skeletonsABorrar.clear();





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
        eRestantes.remove();


        for (int j = 0;j <paredes.length;j++){
            paredes[j].detach();
            paredes[j].remove();
        }

        for (int j = 0;j <bats.size();j++){
            bats.get(j).detach();
            bats.get(j).remove();
        }

        for (int j = 0;j <skeletons.size();j++){
            skeletons.get(j).detach();
            skeletons.get(j).remove();
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
        Constants.setEnemySpeed(1f);
        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameOverScreen2);
                        enemigos = 30;
                    }
                })
        ));
    }

    public void playerWin(){

        player.setWin(true);
        Constants.setEnemySpeed(1f);
        stage.addAction(Actions.sequence(
                Actions.delay(1.5f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(game.gameWinScreen2);
                        enemigos = 30;
                    }
                })
        ));
    }



}
