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
    private Caer plataforma = null;
    Texture spriteDerecha;
    Texture spriteIzquierda;
    Animation<TextureRegion> caminarDerecha;
    Animation<TextureRegion> caminarIzquierda;
    float stateTime = 0f;
    Rectangle bounds;
    float velocityY = 0;
    float velocityX = 3.5f;
    float gravity = -20f;
    boolean parado = false;
    boolean caminando = false;
    private float nivelSuelo = 0.3f;
    private float ancho = 1.0f;
    private float alto = 1.2f;
    TextureRegion quietoDerecha;
    TextureRegion quietoIzquierda;
    Texture saltoDerecha;
    Texture saltoIzquierda;
    TextureRegion saltandoDerecha;
    TextureRegion saltandoIzquierda;
    enum Direccion {Derecha, Izquierda};
    Direccion direccion;
    private boolean controlesInvertidos = false;
    private int vida = 10;
    private boolean invulnerable = false;
    private  float tiempoInvulnerable = 0f;
    private final float duracionInvulnerable = 1f;

    public void quitarVida() {
        if (!invulnerable) {
            vida--;
            System.out.println("Vida restante: "+vida);
            invulnerable = true;
            tiempoInvulnerable = 0f;
        }
    }

    public int getVida() {
        return vida;
    }

    public void alternarControles() {
        controlesInvertidos = !controlesInvertidos;
    }

    public void montar(Caer pez) {
        plataforma = pez;
        parado = true;
        velocityY = 0;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public float getX(){
        return bounds.x;
    }

    public void setX(float x) {
        bounds.x = x;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean estaParado() {
        return parado;
    }

    public void saltar() {
        if (parado){
            velocityY = 4f;
            parado = false;
        }
    }

    public void rebotar(){
        velocityY = 3.5f;
        parado = false;
    }

    public personaje(float x, float y) {
        spriteDerecha = new Texture("cat_sprite_scaled_transparent.png");
        spriteIzquierda = new Texture("gato_caminando_izquierda_flipped_360x360.png");
        quietoDerecha = new TextureRegion(spriteDerecha,0,0,360,360);
        quietoIzquierda = new TextureRegion(spriteIzquierda,0,0,360,360);
        saltoDerecha = new Texture("saltoDerecha.png");
        saltoIzquierda = new Texture("saltoIzquierda.png");
        saltandoDerecha = new TextureRegion(saltoDerecha);
        saltandoIzquierda = new TextureRegion(saltoIzquierda);
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
        direccion = Direccion.Derecha;
    }
    public void actualizarCaminata(float delta) {
        if(plataforma != null) {
            if (bounds.overlaps(plataforma.getBounds())){
                bounds.x += (plataforma.getBounds().x-bounds.x)*5f*delta;
                bounds.y = plataforma.getBounds().y + plataforma.getBounds().height;
                parado = true;
            } else {
                plataforma = null;
                parado = false;
            }
        }

        if ((!parado || plataforma == null) && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            velocityY = 4.5f;
            parado = false;
            plataforma = null;
        }

        caminando = false;
        if (!controlesInvertidos){
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                bounds.x -= velocityX * delta;
                caminando = true;
                direccion = Direccion.Izquierda;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                bounds.x += 2f * delta;
                caminando = true;
                direccion = Direccion.Derecha;
            }
        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                bounds.x -= velocityX * delta;
                caminando = true;
                direccion = Direccion.Derecha;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                bounds.x += 2f * delta;
                caminando = true;
                direccion = Direccion.Izquierda;
            }
        }
        velocityY += gravity*delta;
        bounds.y += velocityY*delta;
        if (parado && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            velocityY = 4.5f;
            parado = false;
        }
        /*velocityY += -8f*delta;*/

        if (bounds.y <= nivelSuelo){
            bounds.y = nivelSuelo;
            velocityY = 0;
            parado = true;
        }
        if (caminando) {
            stateTime += delta;
        }
        if (invulnerable) {
            tiempoInvulnerable += delta;
            if (tiempoInvulnerable >= duracionInvulnerable) {
                invulnerable = false;
            }
        }
        TextureRegion[] framesDerecha = new TextureRegion[3];
    }
    public void render(SpriteBatch batch) {
        TextureRegion frame;
        if (!parado) {
            frame = (direccion == Direccion.Derecha) ? saltandoDerecha : saltandoIzquierda;
        } else if (caminando) {
            frame = (direccion == Direccion.Derecha) ? caminarDerecha.getKeyFrame(stateTime) : caminarIzquierda.getKeyFrame(stateTime);
        }else {
            frame = (direccion == Direccion.Derecha) ? quietoDerecha : quietoIzquierda;
        }
        if (invulnerable && ((int)(tiempoInvulnerable*10)%2 == 0)){
            return;
        }
        batch.draw(frame,bounds.x,bounds.y,(frame == saltandoDerecha || frame == saltandoIzquierda) ? 2 : bounds.width, (frame == saltandoDerecha || frame == saltandoIzquierda) ? 2.5f : bounds.height);
    }
    @Override
    public void dispose() {
        saltoDerecha.dispose();
        saltoIzquierda.dispose();
        spriteDerecha.dispose();
        spriteIzquierda.dispose();
    }
}
