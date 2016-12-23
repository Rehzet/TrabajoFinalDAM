package com.ninjagame.entradas;


import com.badlogic.gdx.InputAdapter;

public class ProcesadorEntrada extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }



}
