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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Victoria implements Screen {
    final Main juego;
    SpriteBatch batch;
    BitmapFont font;
    GlyphLayout layout;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    FitViewport viewport;

    public Victoria(Main juego) {
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
        float botonAncho = 5f;
        float botonAlto = 1.2f;
        float botonY = 2f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(centrarX - botonAncho / 2f, botonY, botonAncho, botonAlto);
        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.WHITE);

        layout.setText(font, "¡HAS GANADO!");
        font.draw(batch, layout, centrarX - layout.width / 2f, 5.5f);

        layout.setText(font, "OTRA");
        font.draw(batch, "OTRA",
            centrarX - layout.width / 2f,
            botonY + botonAlto / 2f + layout.height / 2f);

        batch.end();

        if (Gdx.input.justTouched()) {
            float x = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
            float y = viewport.unproject(new com.badlogic.gdx.math.Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;
            if (x > centrarX - botonAncho / 2f && x < centrarX + botonAncho / 2f && y > botonY && y < botonY + botonAlto) {
                juego.setScreen(new Juego(juego));
                dispose();
            }
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        font.dispose();
        batch.dispose();
    }
}

