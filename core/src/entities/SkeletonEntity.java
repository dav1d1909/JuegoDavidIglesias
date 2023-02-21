package entities;

import com.badlogic.gdx.Gdx;
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

public class SkeletonEntity extends Actor{

    private ArrayList<Texture> texturas;
    private World world;
    public Body body;
    private Fixture fixture;

    public boolean die = false;


    public float h_player = 0.75f;
    public float w_player = 0.75f;

    Animation<Texture> animacion;

    float stateTime;

    public SkeletonEntity(ArrayList<Texture> texturas, World world, Vector2 position){
        this.texturas = texturas;
        this.world = world;
        Texture[]texturass = new Texture[3];
        texturass[0] = texturas.get(0);
        texturass[1] = texturas.get(1);
        texturass[2] = texturas.get(2);

        animacion = new Animation<Texture>(0.25f,texturass);

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);
        body.setGravityScale(0);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w_player,h_player);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("skeleton");
        shape.dispose();

        setSize(1.5f*Constants.PIXELS_IN_METERS,1.5f*Constants.PIXELS_IN_METERS); //45 pixeles son 1 metro real en 640

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        setPosition((body.getPosition().x-w_player)*Constants.PIXELS_IN_METERS,
                    (body.getPosition().y-h_player)*Constants.PIXELS_IN_METERS);

            stateTime += Gdx.graphics.getDeltaTime();
            Texture texturaActual = animacion.getKeyFrame(stateTime, true);
            batch.draw(texturaActual, getX(), getY(), getWidth(), getHeight());

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
