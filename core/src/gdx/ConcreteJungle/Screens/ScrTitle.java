package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.SprButton;

public class ScrTitle implements Screen, InputProcessor {
	ConcreteJungle concreteJungle;
	SpriteBatch batch;
	Texture txTitle, txStart, txLevels;
	SprButton sprStart, sprLevels;
	int nW, nH;

	public ScrTitle(ConcreteJungle _ConcreteJungle) {concreteJungle = _ConcreteJungle;}

	@Override
	public void show() {
		batch = new SpriteBatch();

		nW = Gdx.graphics.getWidth();
		nH = Gdx.graphics.getHeight();

		txTitle = new Texture ("TitleScreen.jpg");
		txStart = new Texture ("Start.png");
		txLevels = new Texture ("Levels.png");

		sprStart = new SprButton(txStart);
		sprStart.setSize(300, 100);
		sprStart.setPosition((nW / 2) - (sprStart.getWidth() / 2), (nH / 2) - (sprStart.getHeight() / 2));

		sprLevels = new SprButton(txLevels);
		sprLevels.setSize(300, 100);
		sprLevels.setPosition((nW / 2) - (sprStart.getWidth() / 2), (nH / 2) - (sprStart.getHeight() / 2) - sprStart.getHeight());

		concreteJungle.setChosen(concreteJungle.getLatestLevel());

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.draw(txTitle, 0, 0, nW, nH);
		sprStart.draw(batch);
		sprLevels.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		txTitle.dispose();
	}

	@Override
	public void resize(int width, int height) {
		nW = width;
		nH = height;
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
		if (sprStart.isClicked(screenX, screenY)) concreteJungle.updateState(1);
		if (sprLevels.isClicked(screenX, screenY)) concreteJungle.updateState(3);
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
