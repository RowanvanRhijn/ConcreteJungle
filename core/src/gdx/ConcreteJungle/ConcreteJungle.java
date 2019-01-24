package gdx.ConcreteJungle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Json;
import gdx.ConcreteJungle.Screens.*;
import com.badlogic.gdx.Gdx;

import java.io.FileWriter;
import java.io.IOException;

public class ConcreteJungle extends Game {
	ScrTitle scrTitle;
	ScrPlay scrPlay;
	ScrVictory scrVictory;
	ScrLevels scrLevels;

	int nScreen, nLatest, nChosen;
	Level arLevel[] = new Level[15];
	Json json = new Json();

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
		else if (nScreen == 3) {
			setScreen(scrLevels);
		}
		//Number screens here
	}

	@Override
	public void create() {
		//change to number of levels once more are added
		for (int i = 0; i < 2; i++){
			arLevel[i] = new Level();
			arLevel[i] = json.fromJson(Level.class, Gdx.files.internal("Level" + (i + 1) + ".json"));
		}

		nScreen = 0;
		scrTitle = new ScrTitle(this);
		scrPlay = new ScrPlay(this);
		scrVictory = new ScrVictory(this);
		scrLevels = new ScrLevels(this);

		updateState(nScreen);
	}

	public Level getLevel(int levelIndex){
		return (arLevel[levelIndex]);
	}

	public int getLatestLevel(){
		//change to number of levels once more are added
		for (int i = 0; i < 2; i++){
			if (arLevel[i].getPrevTime() == 0){
				nLatest = i;
				break;
			}
		}
		return (nLatest);
	}

	public void setChosen(int _nChosen){
		nChosen = _nChosen;
	}

	public int getChosen(){
		return nChosen;
	}

	public void writeScore(long lTime, int nLevel){
//		int nPrevTime= (int) lTime;
//		//json.writeField(nPrevTime, "nPrevTime", "Level1.json");
//		json.writeValue(nPrevTime);
//
//		try(FileWriter file = new FileWriter("Level" + nLevel + ".json");){
//
//		}
//		catch(IOException e){
//			e.printStackTrace();
//		}
//
////		try {
////			json.getWriter().set("nPrevTime", nPrevTime);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		System.out.println(nPrevTime);
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
