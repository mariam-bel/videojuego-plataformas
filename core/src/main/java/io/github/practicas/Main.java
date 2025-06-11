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
    final float anchoMundo = 10;
    final float altoMundo = 7.5f;
    FitViewport viewport;
    Music music;
    SpriteBatch spriteBatch;
    Sprite bucketSprite;
    Vector2 touchPos;
    personaje personaje;
    Array<Caer> peces;
    Fondo fondo;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false,anchoMundo,altoMundo);
        viewport = new FitViewport(anchoMundo, altoMundo,camera);
        fondo = new Fondo();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        touchPos = new Vector2();
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();
        personaje = new personaje(0,0);
        peces = new Array<>();
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
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);
        spriteBatch.begin();
        fondo.render(spriteBatch,delta);
        personaje.render(spriteBatch);
        for (Caer p:peces) {
            p.render(spriteBatch);
        }
        spriteBatch.end();
    }

    private void input() {
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float delta = Gdx.graphics.getDeltaTime();


        if (MathUtils.randomBoolean(0.015f)) {
            float x = MathUtils.random(0f, viewport.getWorldWidth()-1f);
            peces.add(new Caer(x,viewport.getWorldHeight()));
        }

        for (int i = peces.size-1; i>= 0; i--){
            Caer pez = peces.get(i);
            pez.actualizar(Gdx.graphics.getDeltaTime());
            if (pez.fueraPantalla()) {
                pez.dispose();
                peces.removeIndex(i);
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
        spriteBatch.dispose();
        fondo.dispose();
        personaje.dispose();
        for (Caer p:peces) {
            p.dispose();
        }
    }
}
