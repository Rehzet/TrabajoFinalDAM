package com.ninjagame.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ninjagame.entradas.ProcesadorEntrada;

import static com.ninjagame.utils.Constantes.*;

public class Jugador extends Actor implements InputProcessor{

    private static final int VELOCIDAD = 300;
    private static final int SALTO = 55;
    private static final int VOLAR = 250;

    private Texture textura;

    private Sound sonidoSaltar;

    private World mundo;

    public Body cuerpo;

    private Fixture fixture;

    private boolean alive = true;

    private boolean jumping = false;

    private boolean attacking = false;

    private boolean godMode = false;


    private boolean left = false,
                    right = false,
                    space = false,
                    f = false,
                    v = false;


    private TextureRegion regionDefault;
    private TextureRegion regionAtaque1;
    private TextureRegion regionAtaque2;
    private TextureRegion regionSalto;
    private TextureRegion regionEmbestida;
    private TextureRegion regionMuerte1;
    private TextureRegion regionMuerte2;

    private TextureRegion[] frames;

    private float duracion = 0;
    private float dur = 0;

    private TextureRegion frame;

    private Animation animacion;

    public Jugador(World mundo, Texture textura, Vector2 posicion){
        this.mundo = mundo;
        this.textura = textura;

        BodyDef definicion = new BodyDef();
        definicion.position.set(posicion);
        definicion.type = BodyDef.BodyType.DynamicBody;
        cuerpo = mundo.createBody(definicion);

        PolygonShape forma = new PolygonShape();
        forma.setAsBox(0.4f, 0.95f);
        fixture = cuerpo.createFixture(forma, 3);
        fixture.setUserData(this);
        forma.dispose();

        setSize(2 * PX_M, 2 * PX_M);

        cuerpo.setFixedRotation(true);

        regionDefault = new TextureRegion(textura, REGION_DEFAULT_WIDTH, REGION_DEFAULT_HEIGHT);
        regionAtaque1 = new TextureRegion(textura, REGION_ATAQUE1_X, REGION_ATAQUE1_Y, REGION_ATAQUE1_WIDTH, REGION_ATAQUE_HEIGHT);
        regionAtaque2 = new TextureRegion(textura, REGION_ATAQUE2_X, REGION_ATAQUE2_Y, REGION_ATAQUE2_WIDTH, REGION_ATAQUE_HEIGHT);
        regionSalto = new TextureRegion(textura, REGION_SALTO_X, REGION_SALTO_Y, REGION_SALTO_WIDTH, REGION_SALTO_HEIGHT);
        regionEmbestida = new TextureRegion(textura, REGION_EMBESTIDA_X, REGION_EMBESTIDA_Y, REGION_EMBESTIDA_WIDTH, REGION_EMBESTIDA_HEIGHT);
        regionMuerte1 = new TextureRegion(textura, REGION_MUERTE1_X, REGION_MUERTE1_Y, REGION_MUERTE1_WIDTH, REGION_MUERTE1_HEIGHT);
        regionMuerte2 = new TextureRegion(textura, REGION_MUERTE2_X, REGION_MUERTE2_Y, REGION_MUERTE2_WIDTH, REGION_MUERTE2_HEIGHT);

        cambiarAnimacion(0);
//        cambiarAnimacion(regionDefault, REGION_DEFAULT_WIDTH, REGION_DEFAULT_HEIGHT, REGION_DEFAULT_PARTES, 0.19f);
//        cambiarAnimacion(regionAtaque, REGION_ATAQUE_WIDTH, REGION_ATAQUE_HEIGHT, REGION_ATAQUE_PARTES, 0.19f);
//        cambiarAnimacion(regionSalto, REGION_SALTO_WIDTH, REGION_SALTO_HEIGHT, REGION_SALTO_PARTES, 0.19f);
//        cambiarAnimacion(regionEmbestida, REGION_EMBESTIDA_WIDTH, REGION_EMBESTIDA_HEIGHT, REGION_EMBESTIDA_PARTES, 0.19f);
//        cambiarAnimacion(regionMuerte1, REGION_MUERTE1_WIDTH, REGION_MUERTE1_HEIGHT, REGION_MUERTE1_PARTES, 0.19f);
//        cambiarAnimacion(regionMuerte2, REGION_MUERTE2_WIDTH, REGION_MUERTE2_HEIGHT, REGION_MUERTE2_PARTES, 0.19f);

        Gdx.input.setInputProcessor(this);


        sonidoSaltar = Gdx.audio.newSound(Gdx.files.internal("Jugador/jump.wav"));

    }


    /**
     * 0 -> Animación por defecto.
     * 1 -> Animación de ataque.
     * 2 -> Animación de salto.
     * 3 -> Animación de embestida.
     * 4 -> Animación de muerte 1.
     * 5 -> Animación de muerte 2.
     * @param animacion
     */
    public void cambiarAnimacion(int animacion){

        switch (animacion){
            case 0:
                cambiarAnimacion(regionDefault, REGION_DEFAULT_WIDTH, REGION_DEFAULT_HEIGHT, REGION_DEFAULT_PARTES, 0.19f);
                break;
            case 1:
//                cambiarAnimacion(regionAtaque, REGION_ATAQUE1_WIDTH, REGION_ATAQUE_HEIGHT, REGION_ATAQUE_PARTES, 0.19f);
                cambiarAnimacion(regionAtaque1, REGION_ATAQUE1_WIDTH, REGION_ATAQUE_HEIGHT, REGION_ATAQUE1_PARTES, 1);
                break;
            case 2:
                cambiarAnimacion(regionSalto, REGION_SALTO_WIDTH, REGION_SALTO_HEIGHT, REGION_SALTO_PARTES, 0.19f);
                break;
            case 3:
                cambiarAnimacion(regionEmbestida, REGION_EMBESTIDA_WIDTH, REGION_EMBESTIDA_HEIGHT, REGION_EMBESTIDA_PARTES, 0.19f);
                break;
            case 4:
                cambiarAnimacion(regionMuerte1, REGION_MUERTE1_WIDTH, REGION_MUERTE1_HEIGHT, REGION_MUERTE1_PARTES, 0.19f);
                break;
            case 5:
                cambiarAnimacion(regionMuerte2, REGION_MUERTE2_WIDTH, REGION_MUERTE2_HEIGHT, REGION_MUERTE2_PARTES, 0.19f);
                break;
            case 6:
//                cambiarAnimacion(regionAtaque, REGION_ATAQUE1_WIDTH, REGION_ATAQUE_HEIGHT, REGION_ATAQUE_PARTES, 0.19f);
                cambiarAnimacion(regionAtaque2, REGION_ATAQUE2_WIDTH, REGION_ATAQUE_HEIGHT, REGION_ATAQUE2_PARTES, 0.19f);
                break;
        }

    }

    private void cambiarAnimacion(TextureRegion region, int ancho, int alto, int partes, float tiempoFotograma){

        TextureRegion[][] temp =  region.split(ancho / partes, alto);
        frames = new TextureRegion[temp.length * temp[0].length];

        int indice = 0;
        for(int i=0;i<temp.length; i++){
            for(int j=0;j< temp[i].length; j++){
                frames[indice++] = temp[i][j];
            }
        }

        animacion = new Animation(tiempoFotograma, frames);

    }

    @Override
    public void act(float delta) {

        dur += delta;

        if(dur>=0.38f && attacking){
            attacking = false;
            dur = 0;
            cambiarAnimacion(0);
//            setWidth(getWidth()/2);

        }

        duracion += delta;
        frame = animacion.getKeyFrame(duracion, true);


        

//        /* ----- CONTROLES TÁCTILES----- */
//        if(Gdx.input.isTouched(0)) {
//            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 )
//                mover(true, delta);
//            if ((Gdx.input.isTouched() && Gdx.input.getX() <= Gdx.graphics.getWidth() / 2) )
//                mover(false, delta);
//        }
//        if(!Gdx.input.isTouched(0)){
//            cuerpo.setLinearVelocity(0, cuerpo.getLinearVelocity().y);
//        }
//
//        /* ----- CONTROLES ESCRITORIO ----- */
        if(right)
            mover(true, delta);
        if(left)
            mover(false, delta);
        if(f) {
            cambiarAnimacion(1);
            atacar();
        }
//
        if(Gdx.input.isKeyJustPressed(Input.Keys.G) && Gdx.input.isKeyJustPressed(Input.Keys.M)) {
           godMode = !godMode;
            System.out.println("GODMODE " + godMode);
        }
//
        if (godMode && Gdx.input.isKeyPressed(Input.Keys.V))
            volar(delta);

        if (jumping) {
            cuerpo.applyForceToCenter(cuerpo.getLinearVelocity().x, -SALTO * 1.1f, true);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (frame.isFlipX())
            setPosition((cuerpo.getPosition().x - 0.65f) * PX_M, (cuerpo.getPosition().y - 1.55f) * PX_M);
        else
            setPosition((cuerpo.getPosition().x - 1.4f) * PX_M, (cuerpo.getPosition().y - 1.55f) * PX_M);

        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        sonidoSaltar.dispose();
        cuerpo.destroyFixture(fixture);
        mundo.destroyBody(cuerpo);
    }

    /**
     *
     * @param direccion true si se mueve a la derecha, false si se mueve a la izquierda.
     * @param delta
     */
    private void mover(boolean direccion, float delta){

        if (direccion){
            cuerpo.setLinearVelocity(VELOCIDAD * delta, cuerpo.getLinearVelocity().y);
            if (frame.isFlipX())
                cambiarDireccion(frames);
        }else{
            cuerpo.setLinearVelocity(-VELOCIDAD * delta, cuerpo.getLinearVelocity().y);
            if (!frame.isFlipX())
                cambiarDireccion(frames);
        }

    }

    private void volar(float delta){
            cuerpo.setLinearVelocity(cuerpo.getLinearVelocity().x, VOLAR * delta);
    }

    private void saltar(){
        sonidoSaltar.play();
        jumping = true;
        cambiarAnimacion(2);
        if(frame.isFlipX())
            cambiarDireccion(frames);
        Vector2 position = cuerpo.getPosition();
        cuerpo.applyLinearImpulse(0, SALTO, position.x, position.y, true);

    }

    private void atacar(){
        attacking = true;
        if(frame.isFlipX())
            cambiarDireccion(frames);
        dur = 0;
    }

    public void cambiarDireccion(TextureRegion[] region){

        for(int i=0; i<region.length; i++){
            region[i].flip(true, false);
        }
    }

    public boolean isAlive(){
        return alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public boolean isJumping(){
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public TextureRegion[] getFrames(){
        return frames;
    }

    public TextureRegion getFrame(){
        return frame;
    }










    @Override
    public boolean keyDown(int keycode) {

        float delta = Gdx.graphics.getDeltaTime();

         /* ----- CONTROLES TÁCTILES----- */
//        if(Gdx.input.isTouched(0)) {
//            if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2 )
//                mover(true, delta);
//            if ((Gdx.input.isTouched() && Gdx.input.getX() <= Gdx.graphics.getWidth() / 2) )
//                mover(false, delta);
//        }
//        if(!Gdx.input.isTouched(0)){
//            cuerpo.setLinearVelocity(0, cuerpo.getLinearVelocity().y);
//        }

        /* ----- CONTROLES ESCRITORIO ----- */
        if(keycode == Input.Keys.RIGHT)
            right = true;
        if(keycode == Input.Keys.LEFT)
            left = true;
        if(keycode == Input.Keys.SPACE && !jumping)
            saltar();
        if(keycode == Input.Keys.F && !attacking)
            f = true;

        if(keycode == Input.Keys.G && keycode == Input.Keys.M) {
            godMode = !godMode;
            System.out.println("GODMODE " + godMode);
        }

        if (godMode && keycode == Input.Keys.V)
            v = true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        if(keycode == Input.Keys.RIGHT) {
            right = false;
            cuerpo.setLinearVelocity(0, cuerpo.getLinearVelocity().y);
        }
        if(keycode == Input.Keys.LEFT) {
            left = false;
            cuerpo.setLinearVelocity(0, cuerpo.getLinearVelocity().y);
        }
        if(keycode == Input.Keys.F){
            f = false;
//            setWidth(getWidth()*2);
            cambiarAnimacion(6);
        }

        if (godMode && keycode == Input.Keys.V)
            v = false;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
