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

    public ScrLevels(ConcreteJungle _ConcreteJungle) {concreteJungle = _ConcreteJungle;}

    SprRectangle arRectangle[];

    @Override
    public void show() {
        batch = new SpriteBatch();
        txTitle = new Texture ("TitleScreen.jpg");
        txBack = new Texture ("Back.png");
        txLevel = new Texture ("LevelIcon.png");
        sprBack = new SprRectangle(txBack);
        sprBack.setSize(300, 100);
        sprBack.setPosition((Gdx.graphics.getWidth() / 2) - (sprBack.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (sprBack.getHeight() / 2));
        arRectangle = new SprRectangle[concreteJungle.getLatestLevel()];
        for (int i = arRectangle.length; i > 0 ; i++){
            arRectangle[i] = new SprRectangle(txLevel);
        }
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(txTitle, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprBack.draw(batch);
//        for (int i = arRectangle.length; i > 0 ; i++){
//
//        }
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
        System.out.println("called");
        if (sprBack.isClicked()) concreteJungle.updateState(0);
        //does not work, isn't even getting called
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
