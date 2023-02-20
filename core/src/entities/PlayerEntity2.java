package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;

import java.util.ArrayList;

public class PlayerEntity2 extends Actor{

    private ArrayList<Texture> texturas;
    private World world;
    public Body body;
    private Fixture fixture;

    private boolean die = false;
    private boolean shooting = false;
    private boolean win = false;
    private boolean deslizando = false;

    public float h_player = 0.75f;
    public float w_player = 0.75f;
    private Stage stage;
    private MainGame game;

    Animation<Texture> animacion;

    float stateTime;


    public PlayerEntity2(MainGame game, Stage stage, ArrayList<Texture> texturas, World world, Vector2 position){
        this.game = game;
        this.stage = stage;
        this.texturas = texturas;
        this.world = world;
        //para hacer que cambie de sprite
        Texture[]texturass = new Texture[2];
        texturass[0] = texturas.get(0);
        texturass[1] = texturas.get(1);
        animacion = new Animation<Texture>(0.25f,texturass);

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w_player,h_player);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("player");
        shape.dispose();

        setSize(1.5f*Constants.PIXELS_IN_METERS,1.5f*Constants.PIXELS_IN_METERS); //45 pixeles son 1 metro real en 640
        stateTime = 0f;

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        setPosition((body.getPosition().x-w_player)*Constants.PIXELS_IN_METERS,
                    (body.getPosition().y-h_player)*Constants.PIXELS_IN_METERS);
        if(!die && !shooting && !deslizando && !win) {
            stateTime += Gdx.graphics.getDeltaTime();
            Texture texturaActual = animacion.getKeyFrame(stateTime,true);
            batch.draw(texturaActual,getX(),getY(),getWidth(),getHeight());
        }else if(die){
                    batch.draw(texturas.get(2),getX(),getY(),getWidth(),getHeight());
        }else if(shooting){
            batch.draw(texturas.get(3),getX(),getY(),getWidth(),getHeight());
        }else if(deslizando){
            batch.draw(texturas.get(4),getX(),getY(),getWidth(),getHeight());
        } else if (win){
            batch.draw(texturas.get(5),getX(),getY(),getWidth(),getHeight());
        }

        }


    @Override
    public void act(float delta) {
        super.act(delta);
        if (!die && !win){
            if (Gdx.input.isTouched()){
                if (Gdx.input.getX()> 320){
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(Constants.PLAYER_SPEED,velocidadY));
                } else{
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(-Constants.PLAYER_SPEED,velocidadY));
                }
            }
            if (Gdx.input.justTouched()){
            if (Gdx.input.getY()<160){
                if (Gdx.input.getX()> 320) {
                    if (!shooting && !deslizando){
                        shoot();
                    }

                }else if(Gdx.input.getX()< 320){
                    if(!deslizando && !shooting){
                        deslizar();
                    }
                }

            }}


            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(Constants.PLAYER_SPEED,velocidadY));
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(-Constants.PLAYER_SPEED,velocidadY));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                    if(!shooting && !deslizando) {
                        shoot();
                    }
                }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
                if(!deslizando && !shooting) {
                    deslizar();
                }
            }
            }

    }






    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }

    public boolean isDeslizando() {
        return deslizando;
    }

    public void setDeslizando(boolean deslizando) {
        this.deslizando = deslizando;
    }

    public void setDie(boolean die) {
        this.die = die;
    }
    public boolean isDie() {
        return die;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;

    }

    public synchronized void shoot(){
        setShooting(true);
        addAction(Actions.sequence(Actions.delay(0.7f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setShooting(false);
                    }
                })));
    }
    public void deslizar(){
        setDeslizando(true);
        addAction(Actions.sequence(Actions.delay(0.7f),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        setDeslizando(false);
                    }
                })));
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public Vector2 getPosition(){
        Vector2 v = new Vector2(getX(),getY()+3f);
        return v;
    }
}
