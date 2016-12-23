package com.ninjagame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.ninjagame.pantallas.Pantalla1;
import com.ninjagame.pantallas.PantallaBase;
import com.ninjagame.pantallas.PantallaCarga;
import com.ninjagame.pantallas.PantallaGameOver;
import com.ninjagame.pantallas.PantallaMenu;

public class MainGame extends Game {

	private AssetManager manager;

	public PantallaBase pantallaCarga, pantalla1, pantallaMenu, pantallaGameOver;

	@Override
	public void create() {

		manager = new AssetManager();
		manager.load("Jugador/specterKnight.png", Texture.class);
		manager.load("Jugador/jump.wav", Sound.class);

		manager.load("Entorno/pantalla1/background.ogg", Music.class);
		manager.load("Entorno/pantalla1/suelo.png", Texture.class);
		manager.load("Entorno/pantalla1/suelo-inicio.png", Texture.class);
		manager.load("Entorno/pantalla1/suelo-fin.png", Texture.class);
		manager.load("Entorno/pantalla1/pinchos.png", Texture.class);

		manager.load("Entorno/moneda.png", Texture.class);
		manager.load("Entorno/coin.wav", Sound.class);


		pantallaCarga = new PantallaCarga(this);
		setScreen(pantallaCarga);
	}

	public void finishLoading() {
		pantalla1 = new Pantalla1(this);
		pantallaMenu = new PantallaMenu(this);
		pantallaGameOver = new PantallaGameOver(this);
		setScreen(pantallaMenu);
	}

	public AssetManager getManager(){
		return manager;
	}
}
