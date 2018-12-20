package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.SprButton;

public class ScrVictory implements Screen, InputProcessor {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txTitle, txNext, txBack;
    SprButton sprNext, sprBack;
    int nW, nH;

    public ScrVictory(ConcreteJungle _ConcreteJungle) {  //Referencing the main class.
        concreteJungle = _ConcreteJungle;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        nW = Gdx.graphics.getWidth();
        nH = Gdx.graphics.getHeight();

        txTitle = new Texture ("TitleScreen.jpg");
        txNext = new Texture ("Next.png");
        txBack = new Texture ("Back.png");

        sprNext = new SprButton(txNext);
        sprNext.setPosition(nW / 2 - 150, nH / 2 - 100);
        sprNext.setSize(300, 200);

        sprBack = new SprButton(txBack);
        sprBack.setSize(300, 100);
        sprBack.setPosition((nW / 2) - (sprBack.getWidth() / 2), 0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(txTitle, 0, 0, nW, nH);
        sprNext.draw(batch);
        sprBack.draw(batch);

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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(sprNext.isClicked(screenX, screenY)) {
            concreteJungle.setChosen(concreteJungle.getLatestLevel());
            concreteJungle.updateState(1);
        }
        if (sprBack.isClicked(screenX, screenY)) concreteJungle.updateState(0);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

