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

import java.awt.Point;

import static com.ninjagame.utils.Constantes.PX_M;

public class Moneda extends Actor {

    private Texture textura;

    private World mundo;

    private Body cuerpo;

    private Fixture fixture;

    private AssetManager manager;

    private float ancho = 0.5f;

    public Moneda(World mundo, Texture textura, float x, float y, AssetManager manager){

        this.mundo = mundo;
        this.textura = textura;
        this.manager = manager;

        BodyDef def = new BodyDef();
        def.position.set(x+ancho / 2, y);
        def.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(def);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(ancho / 2, 0.25f);
        fixture = cuerpo.createFixture(forma, 1);
        fixture.setUserData(this);
        forma.dispose();

        setSize(ancho * PX_M / 2, PX_M / 2);
        setPosition(x * PX_M, (y - 0.65f) * PX_M);

    }

    @Override
    public void draw(Batch batch, float parentAlpha){

//        batch.draw(textura, getX(), getY(), getWidth(), getHeight());
        batch.draw(textura, getX(), getY(), getWidth()/ancho, getHeight());

    }

    public void detach(){
        cuerpo.destroyFixture(fixture);
        mundo.destroyBody(cuerpo);
    }


    public Body getCuerpo() {
        return cuerpo;
    }

    public void mover(){
        System.out.println(getX());
        remove();
    }

}
