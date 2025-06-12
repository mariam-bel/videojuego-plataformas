package io.github.practicas;

import com.badlogic.gdx.*;

public class Main extends Game {
    @Override
    public void create(){
        setScreen(new Menu(this));
    }
}
