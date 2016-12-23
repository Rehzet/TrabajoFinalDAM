package com.ninjagame.pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ninjagame.MainGame;
import com.ninjagame.actores.CreaActores;
import com.ninjagame.actores.Jugador;
import com.ninjagame.actores.Moneda;
import com.ninjagame.actores.Pinchos;
import com.ninjagame.actores.Suelo;

import java.util.ArrayList;
import java.util.List;

public class Pantalla1 extends PantallaBase {

//  Instacia del escenario Scene2D
    private Stage stage;

//  Instancia del mundo de Box2D
    private World mundo;

    private Music musica;
    private Sound sonMoneda;

//  Instancia del jugador
    private Jugador jugador;

//  Array de suelos
    private List<Suelo> suelos = new ArrayList<Suelo>();

//  Array de pinchos
    private List<Pinchos> pinchos = new ArrayList<Pinchos>();

//    Array de monedas
    private List<Moneda> monedas = new ArrayList<Moneda>();

//    Lista de monedas que se van a eliminar
    private List<Moneda> delMonedas = new ArrayList<Moneda>();

    private BitmapFont lblFPS, lblMonedas, lblVidas;

    private Box2DDebugRenderer renderer;
    private OrthographicCamera camara;

    private boolean mostrarFPS = false;
    private boolean morir = false;

    private SpriteBatch batch;

    private int puntos = 0,
                vidas = 3;

    private CreaActores actores;

    public Pantalla1(MainGame game) { // Constructor que recibe el juego como parámetro
        super(game);

//        stage = new Stage(new FitViewport(960, 540));
        stage = new Stage(new FitViewport(1280, 720));
        stage = new Stage();


        mundo = new World(new Vector2(0, -10), true);  // -10f representa la gravedad terrestre. Redondeo 9.81 a 10 para evitar operaciones con números decimales.
        mundo.setContinuousPhysics(true);
        renderer = new Box2DDebugRenderer();

        camara = new OrthographicCamera( Gdx.graphics.getWidth() /8, Gdx.graphics.getHeight()/8);


        mundo.setContactListener(new GameContactListener());

        batch = new SpriteBatch();

        lblFPS = new BitmapFont();
        lblFPS.setColor(1, 0, 0, 1);

        lblMonedas = new BitmapFont();

        lblVidas = new BitmapFont();

        musica = this.game.getManager().get("Entorno/pantalla1/background.ogg");
        sonMoneda = this.game.getManager().get("Entorno/coin.wav");

    }

    @Override
    public void show() {

        actores = new CreaActores(game.getManager());

        jugador = actores.crearJugador(mundo, new Vector2(-8, 5));

        for(int i=1; i<15; i++)
            suelos.add(actores.crearSuelo(mundo, -20, i, 10));
        suelos.add(actores.crearSuelo(mundo, -10, 1, 5));
        suelos.add(actores.crearSuelo(mundo, -4, 1, 25));
        suelos.add(actores.crearSuelo(mundo, 5, 4, 5));
        suelos.add(actores.crearSuelo(mundo, 13, 6, 1));
        suelos.add(actores.crearSuelo(mundo, 18, 5, 5));
        suelos.add(actores.crearSuelo(mundo, 22, 6, 1));
        suelos.add(actores.crearSuelo(mundo, 27, 1, 3));
        suelos.add(actores.crearSuelo(mundo, 33, 1, 2));
        suelos.add(actores.crearSuelo(mundo, 38, 1, 2));
        suelos.add(actores.crearSuelo(mundo, 43, 1, 15));
        suelos.add(actores.crearSuelo(mundo, 28, 7, 15));
        suelos.add(actores.crearSuelo(mundo, 47, 4, 5));
        suelos.add(actores.crearSuelo(mundo, 55, 7, 1));
        suelos.add(actores.crearSuelo(mundo, 59, 3, 4));
        suelos.add(actores.crearSuelo(mundo, 65, 5, 1));
        suelos.add(actores.crearSuelo(mundo, 69, 3, 4));
        suelos.add(actores.crearSuelo(mundo, 78, 3, 1));
        suelos.add(actores.crearSuelo(mundo, 84, 4, 1));
        suelos.add(actores.crearSuelo(mundo, 90, 5, 1));
        suelos.add(actores.crearSuelo(mundo, 96, 6, 1));
        suelos.add(actores.crearSuelo(mundo, 96, 4, 3));
        suelos.add(actores.crearSuelo(mundo, 98, 2, 4));
        suelos.add(actores.crearSuelo(mundo, 105, 1, 2000));




        for(Suelo suelo: suelos){
            stage.addActor(suelo);
        }

        pinchos.add(actores.crearPinchos(mundo, 35, 1, 3));
        pinchos.add(actores.crearPinchos(mundo, 40, 1, 3));
        pinchos.add(actores.crearPinchos(mundo, 60, 4, 2));
        pinchos.add(actores.crearPinchos(mundo, 70, 4, 2));
        pinchos.add(actores.crearPinchos(mundo, 96, 5, 2));
        pinchos.add(actores.crearPinchos(mundo, 98, 3, 2));

        for(Pinchos pincho: pinchos){
            stage.addActor(pincho);
        }

        monedas.add(actores.crearMoneda(mundo, 6, 5));
        monedas.add(actores.crearMoneda(mundo, 8, 5));
        monedas.add(actores.crearMoneda(mundo, 55, 8));
        monedas.add(actores.crearMoneda(mundo, 59, 4));

        for(int i=0; i<15;i+=2)
            monedas.add(actores.crearMoneda(mundo, i + 28, 8));


        for(Moneda moneda: monedas){
            stage.addActor(moneda);
        }

        stage.addActor(jugador);

        stage.getCamera().position.set(jugador.getX(), jugador.getY(), 0);
        stage.getCamera().update();

        musica.setLooping(true);
        musica.setVolume(0.4f);
        musica.play();

    }

    @Override
    public void hide() {

        stage.clear();
        jugador.detach();

        for(Suelo suelo: suelos){
            suelo.detach();
        }

        suelos.clear();

        musica.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(vidas==0) {
            stage.addAction(
                    Actions.sequence(
                            Actions.run(new Runnable() {

                                @Override
                                public void run() {
                                    vidas = 3;
                                    game.setScreen(game.pantallaGameOver);
                                }
                            })
                    )
            );
        }

        if((Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT)) && Gdx.input.isKeyJustPressed(Input.Keys.F)){
            mostrarFPS = !mostrarFPS;
            System.out.println(mostrarFPS);
        }


//        ------------ STAGE ------------

       stage.act();

        mundo.step(delta, 6, 2);


        stage.getCamera().position.set(jugador.getX() + jugador.getWidth(), Gdx.graphics.getHeight() / 2, 0);
        stage.getCamera().update();

        renderer.render(mundo, camara.combined);

        if(jugador.getY() + jugador.getHeight()<0) {
            System.out.println("HAS MUERTO");
            morir = true;
            vidas--;
        }
        
        if(jugador.getX() > 104*90){
            System.out.println("has gabado");
        }
            
        System.out.println(jugador.getX());

        stage.draw();

        eliminarActores();


//        ------------ BATCH ------------

        batch.begin();

        if(mostrarFPS)
            lblFPS.draw(batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 10, Gdx.graphics.getHeight() - 10);

        lblVidas.draw(batch, "Vidas: " + String.valueOf(vidas), 200, Gdx.graphics.getHeight() - 10);
        lblMonedas.draw(batch, "Monedas: " + String.valueOf(puntos), 1150, Gdx.graphics.getHeight() - 10);

        batch.end();



    }

    @Override
    public void dispose() {
        stage.dispose();
        renderer.dispose();
        mundo.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camara.setToOrtho(false ,width, height);
    }

    private void eliminarActores(){
        borrarMonedas();
        if(morir)
            borrarJugador();
    }

    private void borrarMonedas(){
        for(Moneda moneda: delMonedas) {

            moneda.clear();
            moneda.remove();
            moneda.detach();
        }
        delMonedas.clear();
    }

    private void borrarJugador(){

        try{
            Thread.sleep(2000);}
        catch(InterruptedException e){
            System.out.println(e);
        }

        jugador.detach();
        jugador.remove();
        jugador.clear();
        jugador = actores.crearJugador(mundo, new Vector2(-8, 5));
        stage.addActor(jugador);
        morir = false;
    }

    private class GameContactListener implements ContactListener {

        public boolean hanColisionado(Contact contact, Object userA, Object userB){
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataA == null || userDataB == null){
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userDataB.equals(userA));
        }


        @Override
        public void beginContact(Contact contact) {

            Fixture fa = contact.getFixtureA();
            Fixture fb = contact.getFixtureB();

            // El jugador choca con el suelo.
//            if(hanColisionado(contact, "jugador" , "suelo")){
            if( (fa.getUserData() instanceof Suelo || fb.getUserData() instanceof Suelo) &&
                    (fa.getUserData() instanceof Jugador || fb.getUserData() instanceof Jugador) ) {
                jugador.setJumping(false);
                jugador.cambiarAnimacion(0);
                if(jugador.getFrame().isFlipX())
                    jugador.cambiarDireccion(jugador.getFrames());
            }

//            if(hanColisionado(contact, "jugador", "pinchos")){
            if( (fa.getUserData() instanceof Pinchos || fb.getUserData() instanceof Pinchos) &&
                    (fa.getUserData() instanceof Jugador || fb.getUserData() instanceof Jugador) ) {
                System.out.println("HAS MUERTO");
                morir = true;
                vidas--;
            }


            if( (fa.getUserData() instanceof Moneda || fb.getUserData() instanceof Moneda) &&
                    (fa.getUserData() instanceof Jugador || fb.getUserData() instanceof Jugador) ) {

                if (fa.getUserData() instanceof Moneda) {
                    Moneda m = (Moneda) fa.getUserData();
                    delMonedas.add(m);

                } else {
                    Moneda m = (Moneda) fb.getUserData();
                    delMonedas.add(m);
                }

                puntos++;
                sonMoneda.play();

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
    }




}

