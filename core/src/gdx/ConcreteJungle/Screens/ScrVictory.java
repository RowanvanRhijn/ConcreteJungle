package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;


public class ScrVictory implements Screen {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txTitle, txNext;
    Sprite sprNext;

    public ScrVictory(ConcreteJungle _ConcreteJungle) {  //Referencing the main class.
        concreteJungle = _ConcreteJungle;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        txTitle = new Texture ("TitleScreen.jpg");
        txNext = new Texture ("Next.png");
        sprNext = new Sprite (txNext);
        sprNext.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        sprNext.setSize(300, 200);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Buttons checked
        if(isClicked(sprNext)) concreteJungle.updateState(1);

        batch.begin();

        batch.draw(txTitle, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprNext.draw(batch);

        batch.end();
    }

    public boolean isClicked(Sprite sprCheck){
        if(Gdx.input.justTouched() && Gdx.input.getX() >= sprCheck.getX() && Gdx.input.getX() <= sprCheck.getX() + sprCheck.getWidth()
                && Gdx.input.getY() >= sprCheck.getY() && Gdx.input.getY() <= sprCheck.getY() + sprCheck.getHeight()){
            return true;
        }
        return false;
    }

    @Override
    public void dispose () {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}

