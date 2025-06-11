package io.github.practicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Fondo implements Disposable {
    private Animation<TextureRegion> animacion;
    private float stateTime = 0f;
    private Texture fondoSheet;
    private int cant = 3;

    public Fondo() {
        fondoSheet = new Texture(Gdx.files.internal("fondo.png"));
        int anchoFrame = fondoSheet.getWidth();
        int altoFrame = fondoSheet.getHeight();
        TextureRegion[][] tmp = TextureRegion.split(fondoSheet,anchoFrame,altoFrame);
        TextureRegion[] frames = new TextureRegion[cant];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < cant; j++) {
                frames[j] = tmp[0][j];
            }
        }
        animacion = new Animation<>(0.1f, frames);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch, float delta) {
        stateTime += delta;
        TextureRegion frameActual = animacion.getKeyFrame(stateTime);
        batch.draw(frameActual,0,0, 10, 7.5f);
    }

    public void dispose(){
        fondoSheet.dispose();
    }
}
