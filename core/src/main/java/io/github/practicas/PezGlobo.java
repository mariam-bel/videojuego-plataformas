package io.github.practicas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class PezGlobo implements Disposable {
    private Texture texture;
    private Vector2 position;
    private float temporizador = 0f;
    private float duracion = 0.5f;
    private static int cont = 0;

    public PezGlobo(float x, float y) {
        texture = new Texture("kaboom.png");
        position = new Vector2(x,y);
        cont++;
        if (cont % 5 == 0) {
            duracion = 0.2f;
        }
    }

    public void actualizar(float delta) {
        temporizador+=delta;
    }

    public void render(SpriteBatch batch) {
        if (!terminado()) {
            batch.draw(texture,position.x,position.y,1.5f,1.5f);
        }
    }

    public boolean terminado() {
        return temporizador > duracion;
    }

    public void dispose() {
        texture.dispose();
    }
}
