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

public class FondoEntity extends Actor{

    private ArrayList<Texture>  texturas;
    private World world;
    private Body body;
    private Fixture fixture;
    private float stateTime;
    Animation<Texture> animacion;


    public FondoEntity(ArrayList<Texture> texturas, World world, Vector2 position, float width, float height){

        this.texturas = texturas;
        this.world = world;

        Texture[]texturass = new Texture[2];
        texturass[0] = texturas.get(0);
        texturass[1] = texturas.get(1);
        animacion = new Animation<Texture>(0.5f,texturass);

        setSize(width,height); //45 pixeles son 1 metro real en 640
        setPosition(position.x*Constants.PIXELS_IN_METERS, position.y*Constants.PIXELS_IN_METERS);
        stateTime = 0f;

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        stateTime += Gdx.graphics.getDeltaTime();
        Texture texturaActual = animacion.getKeyFrame(stateTime,true);
        batch.draw(texturaActual,getX(),getY(),getWidth(),getHeight());

    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);

    }
}
