package gdx.ConcreteJungle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Json;
import gdx.ConcreteJungle.Screens.*;
import com.badlogic.gdx.Gdx;

public class ConcreteJungle extends Game {
	ScrTitle scrTitle;
	ScrPlay scrPlay;
	ScrVictory scrVictory;

	int nScreen, nLatest;
	Level arLevel[] = new Level[15];
	Json json = new Json();

	public Level getLevel(){
		//change to number of levels once more are added
		for (int i = 0; i < 1; i++){
			if (arLevel[i].getPrevTime() == 0){
				nLatest = i;
				break;
			}
		}
		return (arLevel[nLatest]);
	}

	public void updateState(int _nScreen) {
		nScreen = _nScreen;
		if ( nScreen == 0) {
			setScreen(scrTitle);
		}
		else if (nScreen == 1) {
			setScreen(scrPlay);
		}
		else if (nScreen == 2) {
			setScreen(scrVictory);
		}
		//Number screens here
	}

	@Override
	public void create() {
		nScreen = 0;
		scrTitle = new ScrTitle(this);
		scrPlay = new ScrPlay(this);
		scrVictory = new ScrVictory(this);
		//Put screens here like this too
		updateState(nScreen);

		//change to number of levels once more are added
		for (int i = 0; i < 1; i++){
			arLevel[i] = new Level();
			arLevel[i] = json.fromJson(Level.class, Gdx.files.internal("Level" + (i + 1) + ".json"));
		}
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
