package com.ninjagame.actores;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CreaActores {

    private AssetManager manager;

    public CreaActores(AssetManager manager){
        this.manager = manager;
    }


    public Jugador crearJugador(World mundo, Vector2 posicion){
        Texture texturaJugador = manager.get("Jugador/specterKnight.png");
        return new Jugador(mundo, texturaJugador, posicion);
    }

    /**
     *
     * @param mundo Mundo en el que se va a crear el suelo.
     * @param x Posición X del suelo.
     * @param y Posición Y del suelo.
     * @param ancho Ancho del actor
     * @param tipoSuelo 0  --> Suelo normal
     *                  1  --> Suelo inicial
     *                  -1 --> Suelo final
     * @return Devuelve un suelo ya creado
     */
    public Suelo crearSuelo(World mundo, float x, float y, float ancho, int tipoSuelo){
        Texture textura = null;
        switch (tipoSuelo){
            case 0:
                textura = manager.get("Entorno/pantalla1/suelo.png");
                break;
            case 1:
                textura = manager.get("Entorno/pantalla1/suelo-inicio.png");
                break;
            case -1:
                textura = manager.get("Entorno/pantalla1/suelo-fin.png");
                break;
        }

        return new Suelo(mundo, textura, x, y, ancho, manager);
    }

    /**
     *
     * @param mundo Mundo en el que se va a crear el suelo.
     * @param x Posición X del suelo.
     * @param y Posición Y del suelo.
     * @param ancho Ancho del actor
     * @return Devuelve un suelo ya creado
     */
    public Suelo crearSuelo(World mundo, float x, float y, float ancho){
        Texture textura = manager.get("Entorno/pantalla1/suelo.png");
        return new Suelo(mundo, textura, x, y, ancho, manager);
    }

    public Pinchos crearPinchos(World mundo, float x, float y, float ancho){
        Texture textura = manager.get("Entorno/pantalla1/pinchos.png");
        return new Pinchos(mundo, textura, x, y, ancho, manager);
    }

    public Moneda crearMoneda(World mundo, float x, float y){
        Texture textura = manager.get("Entorno/moneda.png");
        return new Moneda(mundo, textura, x, y, manager);
    }
}
