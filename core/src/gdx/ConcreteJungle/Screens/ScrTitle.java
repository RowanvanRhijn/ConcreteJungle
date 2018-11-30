package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gdx.ConcreteJungle.ConcreteJungle;


public class ScrTitle implements Screen {
	ConcreteJungle concreteJungle;
	SpriteBatch batch;
	Texture txTitle, txStart;
	Sprite sprStart;

	public ScrTitle(ConcreteJungle _ConcreteJungle) {concreteJungle = _ConcreteJungle;}

	@Override
	public void show() {
		batch = new SpriteBatch();
		txTitle = new Texture ("TitleScreen.jpg");
		txStart = new Texture ("Start.png");
		sprStart = new Sprite(txStart);
		sprStart.setSize(300, 100);
		sprStart.setPosition((Gdx.graphics.getWidth() / 2) - (sprStart.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (sprStart.getHeight() / 2));
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(isClicked(sprStart)) concreteJungle.updateState(1);

		batch.begin();

		batch.draw(txTitle, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sprStart.draw(batch);

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
}
