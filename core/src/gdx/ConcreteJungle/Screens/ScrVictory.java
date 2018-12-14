package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.SprRectangle;


public class ScrVictory implements Screen {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txTitle, txNext;
    SprRectangle sprNext;

    public ScrVictory(ConcreteJungle _ConcreteJungle) {  //Referencing the main class.
        concreteJungle = _ConcreteJungle;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        txTitle = new Texture ("TitleScreen.jpg");
        txNext = new Texture ("Next.png");
        sprNext = new SprRectangle(txNext);
        sprNext.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        sprNext.setSize(300, 200);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Buttons checked
        if(sprNext.isClicked()) {
            concreteJungle.setChosen(concreteJungle.getLatestLevel());
            concreteJungle.updateState(1);
        }

        batch.begin();

        batch.draw(txTitle, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprNext.draw(batch);

        batch.end();
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

