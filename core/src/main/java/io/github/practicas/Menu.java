package io.github.practicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Menu implements Screen {
    final Main juego;
    SpriteBatch batch;
    BitmapFont font;
    GlyphLayout layout;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    FitViewport viewport;

    public Menu(Main juego) {
        this.juego = juego;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10, 7.5f);
        viewport = new FitViewport(10, 7.5f, camera);
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(0.1f);
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        float centrarX = viewport.getWorldWidth() / 2f;
        float botonAncho = 4.5f;
        float botonAlto = 1.2f;
        float playY = 4f;
        float exitY = 2f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);
        shapeRenderer.rect(centrarX - botonAncho / 2f, playY, botonAncho, botonAlto);
        shapeRenderer.rect(centrarX - botonAncho / 2f, exitY, botonAncho, botonAlto);
        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);

        layout.setText(font, "PLAY");
        font.draw(batch, "PLAY", centrarX - layout.width / 2f, playY + botonAlto / 2f + layout.height / 2f);
        layout.setText(font, "EXIT");
        font.draw(batch, "EXIT",centrarX - layout.width / 2f,exitY + botonAlto / 2f + layout.height / 2f);
        batch.end();

        if (Gdx.input.justTouched()) {
            float x = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
            float y = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
            if (x > centrarX - botonAncho / 2f && x < centrarX + botonAncho / 2f) {
                if (y > playY && y < playY + botonAlto) {
                    juego.setScreen(new Juego(juego));
                    dispose();
                } else if (y > exitY && y < exitY + botonAlto) {
                    Gdx.app.exit();
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
