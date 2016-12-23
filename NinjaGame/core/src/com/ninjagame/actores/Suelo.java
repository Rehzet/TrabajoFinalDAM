package com.ninjagame.actores;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.ninjagame.utils.Constantes.*;

public class Suelo extends Actor {

    private Texture textura;

    private World mundo;

    private Body cuerpo, cuerpoI, cuerpoD;

    private Fixture fixture, fixtureI, fixtureD;

    private float ancho;

    private AssetManager manager;

    public Suelo(World mundo, Texture textura, float x, float y, float ancho, AssetManager manager){
        this.mundo = mundo;
        this.textura = textura;
        this.ancho = ancho;
        this.manager = manager;

        BodyDef def = new BodyDef();
        def.position.set(x+ancho / 2, y);
        def.type = BodyDef.BodyType.StaticBody;
        cuerpo = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(ancho / 2, 0.5f);
        fixture = cuerpo.createFixture(forma, 1);
        fixture.setUserData(this);
        forma.dispose();

        BodyDef defI = new BodyDef();
        defI.position.set(x, y);
        defI.type = BodyDef.BodyType.StaticBody;
        cuerpoI = mundo.createBody(defI);

        PolygonShape formaI = new PolygonShape();
        formaI.setAsBox(0.02f, 0.5f);
        fixtureI = cuerpoI.createFixture(formaI, 1);
        fixtureI.setUserData("lado");
        formaI.dispose();

        BodyDef defD = new BodyDef();
        defD.position.set(x+ancho, y);
        defD.type = BodyDef.BodyType.StaticBody;
        cuerpoD = mundo.createBody(defD);

        PolygonShape formaD = new PolygonShape();
        formaD.setAsBox(0.02f, 0.5f);
        fixtureD = cuerpoD.createFixture(formaD, 1);
        fixtureD.setUserData("lado");
        formaD.dispose();


        setSize(ancho * PX_M, PX_M);
        setPosition(x * PX_M, (y - 1) * PX_M);

    }

    @Override
    public void draw(Batch batch, float parentAlpha){

        TextureRegion region;

        for(int i=0; i<ancho; i++) {

            if(i==0)
                textura = manager.get("Entorno/pantalla1/suelo-inicio.png");
            else if(i==ancho-1)
                textura = manager.get("Entorno/pantalla1/suelo-fin.png");
            else
                textura = manager.get("Entorno/pantalla1/suelo.png");

            region = new TextureRegion(textura, 16, 15);

            batch.draw(region, getX() + i*PX_M, getY(), getWidth()/ancho, getHeight());
        }

    }

    public void detach(){
        cuerpo.destroyFixture(fixture);
        mundo.destroyBody(cuerpo);
    }

}
