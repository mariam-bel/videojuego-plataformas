package io.github.practicas;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends ApplicationAdapter implements ApplicationListener {
    OrthographicCamera camera;
    FitViewport viewport;
    Music music;
    SpriteBatch spriteBatch;
    Sprite bucketSprite;
    Vector2 touchPos;
    personaje personaje;
    Array<Caer> peces;
    Fondo fondo;
    Array<PezGlobo> explosion;
    BitmapFont font;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false,8,8);
        viewport = new FitViewport(10, 7.5f,camera);
        fondo = new Fondo();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        touchPos = new Vector2();
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
        personaje = new personaje(0,0);
        peces = new Array<>();
        explosion = new Array<>();
        font = new BitmapFont();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        input();
        logic();
        personaje.actualizarCaminata(delta);
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        fondo.render(spriteBatch,delta);
        personaje.render(spriteBatch);
        for (Caer p:peces) {
            p.render(spriteBatch);
        }
        for (PezGlobo pezGlobo:explosion){
            pezGlobo.render(spriteBatch);
        }
        spriteBatch.end();
        for (int i = explosion.size-1; i>=0; i--) {
            PezGlobo pezGlobo = explosion.get(i);
            pezGlobo.actualizar(delta);
            if (pezGlobo.terminado()) {
                pezGlobo.dispose();
                explosion.removeIndex(i);
            }
        }
    }

    private void input() {
    }

    private void logic() {
        float delta = Gdx.graphics.getDeltaTime();
        for (int i = peces.size-1; i>= 0; i--){
            Caer pez = peces.get(i);
            pez.actualizar(delta);
            if (pez.fueraPantalla()) {
                pez.dispose();
                peces.removeIndex(i);
            }
        }
        for (int i = peces.size-1; i >= 0 ; i--) {
            Caer pez = peces.get(i);
            Rectangle pies = new Rectangle(personaje.getBounds().x,personaje.getBounds().y,personaje.getBounds().width,0.2f);
            if (!pez.yaColisionado() && pies.overlaps(pez.getBounds()) && personaje.getVelocityY() <= 0) {
                pez.marcarColisionado();
                if (pez.getTipo()==2) {
                    explosion.add(new PezGlobo(pez.bounds.x, pez.bounds.y));
                    personaje.quitarVida();
                    personaje.rebotar();
                } else if (pez.getTipo() == 1) {
                    personaje.alternarControles();
                    personaje.montar(pez);
                } else {
                    personaje.montar(pez);
                }
                pez.dispose();
                peces.removeIndex(i);
            }
        }
        if (MathUtils.randomBoolean(0.015f)) {
            boolean coinciden = false;
            float nuevoX = MathUtils.random(0f, viewport.getWorldWidth()-1f);
            for (Caer pez:peces) {
                if (Math.abs(pez.bounds.x-nuevoX)<1f){
                    coinciden = true;
                    break;
                }
            }
            if (!coinciden){
            peces.add(new Caer(nuevoX,viewport.getWorldHeight()));
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        music.dispose();
        fondo.dispose();
        personaje.dispose();
        for (Caer p:peces) {
            p.dispose();
        }
        font.dispose();
        spriteBatch.dispose();
    }
}
