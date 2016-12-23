package com.ninjagame.actores;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.ninjagame.utils.Constantes.PX_M;

public class Pinchos extends Actor {

    private Texture textura;

    private World mundo;

    private Body cuerpo;

    private Fixture fixture;

    private float ancho;

    private AssetManager manager;

    public Pinchos(World mundo, Texture textura, float x, float y, float ancho, AssetManager manager){
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

        setSize(ancho * PX_M, PX_M);
        setPosition(x * PX_M, (y - 1) * PX_M);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i=0; i<ancho; i++) {
            batch.draw(textura, getX() + i*PX_M, getY(), getWidth()/ancho, getHeight());
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
