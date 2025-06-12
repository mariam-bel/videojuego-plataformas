package io.github.practicas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class Fondo implements Disposable {
    private Animation<TextureRegion> animacion;
    private TextureRegion[] fondos;
    private float tiempo = 0f;
    private float duracion = 0.3f;

    public Fondo() {
        fondos = new TextureRegion[] {
            new TextureRegion(new Texture(Gdx.files.internal("fondo1.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("fondo2.png"))),
            new TextureRegion(new Texture(Gdx.files.internal("fondo3.png")))
        };
        animacion = new Animation<>(0.2f,fondos);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void render(SpriteBatch batch, float delta) {
        tiempo+=delta;
        TextureRegion frameActual = animacion.getKeyFrame(tiempo,true);
        float imagenAncho = frameActual.getRegionWidth();
        float imagenAlto = frameActual.getRegionHeight();
        float proporcion = imagenAncho/imagenAlto;
        float altoDeseado = 7.5f;
        float anchoDeseado = proporcion*altoDeseado;
        batch.draw(frameActual,0f,0f,10,7.5f);
    }

    public void dispose(){
        for (TextureRegion fondo:fondos){
            fondo.getTexture().dispose();
        }
    }
}
