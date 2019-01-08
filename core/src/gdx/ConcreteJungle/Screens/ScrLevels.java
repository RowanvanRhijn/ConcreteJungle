package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.SprButton;

public class ScrLevels implements Screen, InputProcessor {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txTitle, txBack, txLevel;
    SprButton sprBack;
    SprButton arButton[];
    int nScroll, nW, nH;

    public ScrLevels(ConcreteJungle _ConcreteJungle) {concreteJungle = _ConcreteJungle;}



    @Override
    public void show() {
        batch = new SpriteBatch();

        nScroll = 0;
        nW = Gdx.graphics.getWidth();
        nH = Gdx.graphics.getHeight();

        txTitle = new Texture ("TitleScreen.jpg");
        txBack = new Texture ("Back.png");
        txLevel = new Texture ("LevelIcon.png");

        sprBack = new SprButton(txBack);
        sprBack.setSize(300, 100);
        sprBack.setPosition((nW / 2) - (sprBack.getWidth() / 2), 0);

        arButton = new SprButton[concreteJungle.getLatestLevel() + 1];
        //Only 3 to test the scrolling for now, set to 1 once more levels are added
        for (int i = arButton.length - 1; i >= 0 ; i--){
            arButton[i] = new SprButton(txLevel);
            arButton[i].setSize(100, 100);
            arButton[i].setPosition(nW - arButton[i].getWidth(),
                    nH - ((i + 1) * ((arButton[i].getHeight() * 3) / 2) - (arButton[i].getHeight() / 2)));
        }

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(txTitle, 0, 0, nW, nH);

        sprBack.draw(batch);

        for (int i = arButton.length - 1; i >= 0 ; i--){
            arButton[i].draw(batch);
            arButton[i].setPosition(nW - arButton[i].getWidth(),
                    nH + nScroll - ((i + 1) * ((arButton[i].getHeight() * 3) / 2) - (arButton[i].getHeight() / 2)));
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
        for (int i = arButton.length - 1; i >= 0 ; i--){
            if(arButton[i].isClicked(screenX, screenY)) {
                concreteJungle.setChosen(i);
                concreteJungle.updateState(1);
            }
        }
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
        //This is still weird
        if (nScroll - 3 * amount >= 0 &&
                arButton[arButton.length - 1].getY() + nScroll - (3 * amount) + (arButton[arButton.length - 1].getHeight()) < nH){
            nScroll -= 3 * amount;
        }
        return true;
    }
}
