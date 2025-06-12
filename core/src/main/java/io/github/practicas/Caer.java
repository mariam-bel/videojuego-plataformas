package io.github.practicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;



public class Caer implements Disposable {
    Texture texture;
    Rectangle bounds;
    float velocityY = -2f;
    private int tipo;

    public Caer(float x, float y) {
        tipo = MathUtils.random(1,3);
        texture = new Texture(Gdx.files.internal("pez"+tipo+".png"));
        float ancho;
        float alto;
        if (tipo==2){
            ancho = 3f;
            alto = 3f;
        } else {
            ancho = 1.2f;
            alto = 1f;
        }
        bounds = new Rectangle(x,y,ancho,alto);
        velocityY = MathUtils.random(-1.5f,-2f);
    }

    public void actualizar(float delta){
        bounds.y += velocityY*delta;
    }

    public int getTipo() {
        return tipo;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,bounds.x,bounds.y,bounds.width,bounds.height);
    }

    public boolean fueraPantalla() {
        return bounds.y + bounds.height < 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
