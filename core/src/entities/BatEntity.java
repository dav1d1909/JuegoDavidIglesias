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

public class BatEntity extends Actor{

    private Texture textura;
    private World world;
    public Body body;
    private Fixture fixture;

    public boolean die = false;


    public float h_player = 0.75f;
    public float w_player = 0.75f;

    public BatEntity(Texture textura, World world, Vector2 position){
        this.textura = textura;
        this.world = world;
        //para hacer que cambie de sprite

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setGravityScale(0);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w_player,h_player);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("bat");
        shape.dispose();

        setSize(1.5f*Constants.PIXELS_IN_METERS,1.5f*Constants.PIXELS_IN_METERS); //45 pixeles son 1 metro real en 640

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        setPosition((body.getPosition().x-w_player)*Constants.PIXELS_IN_METERS,
                    (body.getPosition().y-h_player)*Constants.PIXELS_IN_METERS);

        batch.draw(textura,getX(),getY(),getWidth(),getHeight());
        }


    @Override
    public void act(float delta) {
        super.act(delta);
                    float velocidadX = body.getLinearVelocity().x;
                    body.setLinearVelocity(new Vector2(velocidadX,-Constants.ENEMY_SPEED));

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

}
