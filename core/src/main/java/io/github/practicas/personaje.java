package io.github.practicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import com.badlogic.gdx.math.Rectangle;

public class personaje implements Disposable {
    Texture spriteDerecha;
    Texture spriteIzquierda;
    Animation<TextureRegion> caminarDerecha;
    Animation<TextureRegion> caminarIzquierda;
    float stateTime = 0f;
    Rectangle bounds;
    float velocityY = 0;
    float gravity = -20f;
    boolean parado = false;
    boolean caminando = false;
    private float nivelSuelo = 0.3f;
    private float ancho = 1.0f;
    private float alto = 1.2f;
    TextureRegion quieto;
    TextureRegion saltando;
    enum Direccion {Derecha, Izquierda};
    Direccion direccion;

    public personaje(float x, float y) {
        spriteDerecha = new Texture("personaje-gato_redimensionado.png");
        spriteIzquierda = new Texture("personaje-gato2.png");
        quieto = new TextureRegion(spriteDerecha,0,0,360,500);
        saltando = new TextureRegion(spriteDerecha, 5,5,360,360);
        TextureRegion[] framesDerecha = new TextureRegion[3];
        TextureRegion[] framesIzquierda = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            framesDerecha[i] = new TextureRegion(spriteDerecha,(i+1)*360,0,360,500);
            framesIzquierda[i] = new TextureRegion(spriteIzquierda,(i+1)*360,0,360,500);
        }
        caminarDerecha = new Animation<TextureRegion>(0.1f, framesDerecha);
        caminarDerecha.setPlayMode(Animation.PlayMode.LOOP);
        caminarIzquierda = new Animation<TextureRegion>(0.1f, framesIzquierda);
        caminarIzquierda.setPlayMode(Animation.PlayMode.LOOP);
        bounds = new Rectangle(x,y,ancho,alto);
    }
    public void actualizarCaminata(float delta) {
        caminando = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bounds.x -= 2f * delta;
            caminando = true;
            direccion = Direccion.Izquierda;
        } if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bounds.x += 2f * delta;
            caminando = true;
            direccion = Direccion.Derecha;
        }
        velocityY += gravity*delta;
        bounds.y += velocityY*delta;
        if (parado && Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            velocityY = 4f;
            bounds.y = 2f;
            parado = false;
        }
        velocityY += -8f*delta;

        if (bounds.y <= nivelSuelo){
            bounds.y = nivelSuelo;
            velocityY = 0;
            parado = true;
        }
        if (caminando) {
            stateTime += delta;
        }
    }
    public void render(SpriteBatch batch) {
        TextureRegion frame;
        if (caminando) {
            if (direccion==Direccion.Derecha){
            frame = caminarDerecha.getKeyFrame(stateTime);
            }else {
            frame = caminarIzquierda.getKeyFrame(stateTime);
            }
        } else if (parado) {
            frame = quieto;
        }else {
            frame = saltando;
        }
        batch.draw(frame, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    @Override
    public void dispose() {
        spriteDerecha.dispose();
        spriteIzquierda.dispose();
    }
}
