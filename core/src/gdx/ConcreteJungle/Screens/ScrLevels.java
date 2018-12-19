package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.SprRectangle;


public class ScrLevels implements Screen, InputProcessor {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txTitle, txBack, txLevel;
    SprRectangle sprBack;
    int nScroll;

    public ScrLevels(ConcreteJungle _ConcreteJungle) {concreteJungle = _ConcreteJungle;}

    SprRectangle arRectangle[];

    @Override
    public void show() {
        batch = new SpriteBatch();
        nScroll = 0;

        txTitle = new Texture ("TitleScreen.jpg");
        txBack = new Texture ("Back.png");
        txLevel = new Texture ("LevelIcon.png");

        sprBack = new SprRectangle(txBack);
        sprBack.setSize(300, 100);
        sprBack.setPosition((Gdx.graphics.getWidth() / 2) - (sprBack.getWidth() / 2), 0);

        arRectangle = new SprRectangle[concreteJungle.getLatestLevel() + 3];
        //Only 3 to test the scrolling for now, set to 1 once more levels are added
        for (int i = arRectangle.length - 1; i >= 0 ; i--){
            arRectangle[i] = new SprRectangle(txLevel);
            arRectangle[i].setSize(100, 100);
            arRectangle[i].setPosition(Gdx.graphics.getWidth() - arRectangle[i].getWidth(),
                    Gdx.graphics.getHeight() - ((i + 1) * ((arRectangle[i].getHeight() * 3) / 2) - (arRectangle[i].getHeight() / 2)));
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(txTitle, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        sprBack.draw(batch);

        for (int i = arRectangle.length - 1; i >= 0 ; i--){
            arRectangle[i].draw(batch);
            arRectangle[i].setPosition(Gdx.graphics.getWidth() - arRectangle[i].getWidth(),
                    Gdx.graphics.getHeight() + nScroll - ((i + 1) * ((arRectangle[i].getHeight() * 3) / 2) - (arRectangle[i].getHeight() / 2)));
        }

        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        txTitle.dispose();
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
        if (nScroll - 3 * amount >= 0 &&
                arRectangle[arRectangle.length - 1].getY() + (nScroll - 3 * amount) + (arRectangle[arRectangle.length - 1].getHeight())
                        <= Gdx.graphics.getHeight()) {
            nScroll -= 3 * amount;
        }
        return true;
    }
}
