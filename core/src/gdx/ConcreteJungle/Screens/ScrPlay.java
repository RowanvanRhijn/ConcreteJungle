
package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import gdx.ConcreteJungle.ConcreteJungle;
import gdx.ConcreteJungle.Level;

//import java.time.Duration;
//import java.time.Instant;

import static java.lang.System.currentTimeMillis;

public class ScrPlay implements Screen {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    Texture txUser, txFinish;
    Sprite sprUser, sprFinish;
    long lTimeStart;
    long lTimeLimit;
    BitmapFont font = new BitmapFont();

    int mapWidth;
    int mapHeight;
    int tilePixelWidth;
    int tilePixelHeight;
    int mapTotalWidth;
    int mapTotalHeight;
    boolean hasZoomed;

    private OrthographicCamera orthCam;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public ScrPlay(ConcreteJungle _ConcreteJungle) {  //Referencing the main class.
        concreteJungle = _ConcreteJungle;
    }

    @Override
    public void show() {
        Level currentLevel = concreteJungle.getLevel();
        batch = new SpriteBatch();
        orthCam = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
        orthCam.position.set(orthCam.viewportWidth / 2f, orthCam.viewportHeight / 2f, 0);

        map = new TmxMapLoader().load(currentLevel.getMap());
        renderer = new OrthogonalTiledMapRenderer(map);

        //Stolen from a response at https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
        MapProperties prop = map.getProperties();

        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tilePixelWidth = prop.get("tilewidth", Integer.class);
        tilePixelHeight = prop.get("tileheight", Integer.class);

        mapTotalWidth = mapWidth * tilePixelWidth;
        mapTotalHeight = mapHeight * tilePixelHeight;

        orthCam = new OrthographicCamera(mapTotalWidth, mapTotalHeight);
        orthCam.position.set(mapTotalWidth / 2, mapTotalHeight / 2, 0);
        orthCam.update();

        lTimeStart = currentTimeMillis();
        lTimeLimit = 15000;

        hasZoomed = false;
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //All this has to be here:
        renderer.setView(orthCam);
        renderer.render();

        orthCam.update();
        batch.setProjectionMatrix(orthCam.combined);
        //(Until here)

        //Learned some of this from https://www.baeldung.com/java-measure-elapsed-time
        if (currentTimeMillis() - lTimeStart > 5000 && hasZoomed == false){
            orthCam.setToOrtho(false,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
            hasZoomed = true;
        }
        if (currentTimeMillis() - lTimeStart > lTimeLimit){
            concreteJungle.updateState(2);
        }
        batch.begin();
        if (hasZoomed == true) font.draw(batch, String.valueOf((float) (lTimeLimit - (System.currentTimeMillis() - lTimeStart)) / 1000), orthCam.position.x + 110, orthCam.position.y + 110);
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

