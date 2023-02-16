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
import com.mygdx.game.Constants;

import java.util.ArrayList;

import sun.management.Sensor;

public class PlayerEntity extends Actor{

    private ArrayList<Texture> texturas;
    private World world;
    public Body body;
    private Fixture fixture;

    private boolean die = false;
    private boolean jumping = true;
    private boolean win = false;

    public float h_player = 0.75f;
    public float w_player = 0.75f;

    Animation<Texture> animacion;

    float stateTime;


    public PlayerEntity(ArrayList<Texture> texturas, World world, Vector2 position){
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

                stateTime += Gdx.graphics.getDeltaTime();
                Texture texturaActual = animacion.getKeyFrame(stateTime,true);
                batch.draw(texturaActual,getX(),getY(),getWidth(),getHeight());
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
                    if(!isJumping() ) {
                        jump();
                    }
                }//else { la otra accion }

            }}


            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(Constants.PLAYER_SPEED,velocidadY));
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                    float velocidadY = body.getLinearVelocity().y;
                    body.setLinearVelocity(new Vector2(-Constants.PLAYER_SPEED,velocidadY));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
                    if(!isJumping() ) {
                        jump();
                    }
                }
            }


        }



    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }

    public void setDie(boolean die) {
        this.die = die;
    }
    public boolean isDie() {
        return die;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;

    }

    public void jump(){
        body.applyLinearImpulse(0,1,body.getPosition().x,body.getPosition().y,true);
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
