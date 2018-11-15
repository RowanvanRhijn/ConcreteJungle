package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import gdx.ConcreteJungle.*;

import static java.lang.System.currentTimeMillis;

public class ScrPlay implements Screen {
    ConcreteJungle concreteJungle;
    SpriteBatch batch;
    String strUserTx, strFinishTx;
    //Sprite sprUser, sprFinish;
    public static SprUser sprUser;
    SprFinish sprFinish;

    InputListener inputListener;

    //Sprite movement stuff
    int nDirection;
    int nDX[] = new int[5];
    int nDY[] = new int[5];

    //Zooming stuff
    long lTimeStart;
    boolean hasZoomed;

    //Map dimensions
    MapProperties mapProperties;
    int nMapWidth;
    int nMapHeight;
    int nTilePixelWidth;
    int nTilePixelHeight;
    int nMapTotalWidth;
    int nMapTotalHeight;

    //Tiled + camera
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

        nDirection = 0;
        hasZoomed = false;

        strUserTx = "MainCharacter.png";
        strFinishTx = "FinishPoint.png";
        sprUser = new SprUser(new Texture(strUserTx));
        sprUser.setSize(32, 32);
        sprUser.setPosition(currentLevel.getStartX(), currentLevel.getStartY());
        sprFinish = new SprFinish(new Texture(strFinishTx));
        sprFinish.setSize(32, 256);
        sprFinish.setPosition(currentLevel.getFinishX(), currentLevel.getFinishY());

        map = new TmxMapLoader().load(currentLevel.getMap());
        renderer = new OrthogonalTiledMapRenderer(map);

        //Stolen from a response at https://gamedev.stackexchange.com/questions/57325/how-to-get-width-and-height-of-tiledmap-in-the-latest-version-of-libgdx
        mapProperties = map.getProperties();

        nMapWidth = mapProperties.get("width", Integer.class);
        nMapHeight = mapProperties.get("height", Integer.class);
        nTilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        nTilePixelHeight = mapProperties.get("tileheight", Integer.class);

        nMapTotalWidth = nMapWidth * nTilePixelWidth;
        nMapTotalHeight = nMapHeight * nTilePixelHeight;

        orthCam = new OrthographicCamera(nMapTotalWidth, nMapTotalHeight);
        orthCam.position.set(nMapTotalWidth / 2, nMapTotalHeight / 2, 0);
        orthCam.update();
        lTimeStart = currentTimeMillis();

        nDX[0] = 0;
        nDX[1] = -3;
        nDX[2] = 3;
        nDX[3] = 0;
        nDX[4] = 0;
        nDY[0] = 0;
        nDY[1] = 0;
        nDY[2] = 0;
        nDY[3] = 3;
        nDY[4] = -3;

        inputListener = new InputListener();
        Gdx.input.setInputProcessor(inputListener);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(orthCam);
        renderer.render();
        orthCam.update();
        batch.setProjectionMatrix(orthCam.combined);

        //Once-a-frame checks and operations
        if(hasZoomed) sprUser.moveUser(1);

        if (sprUser.isBuildingHit(map)) sprUser.moveUser(-1);

        if (hasZoomed) {
            orthCam.position.set(sprUser.getX() + sprUser.getWidth() / 2, sprUser.getY() + sprUser.getHeight() / 2, 0);
            //Some values here look weird because the camera is moving based on its centrepoint
            if (orthCam.position.x > nMapTotalWidth - orthCam.viewportWidth / 2){
                orthCam.position.set(nMapTotalWidth - orthCam.viewportWidth / 2, orthCam.position.y, orthCam.position.z);
            }
            if (orthCam.position.x < orthCam.viewportWidth / 2){
                orthCam.position.set(orthCam.viewportWidth / 2, orthCam.position.y, orthCam.position.z);
            }
            if (orthCam.position.y > nMapTotalHeight - orthCam.viewportHeight / 2){
                orthCam.position.set(orthCam.position.x, nMapTotalHeight - orthCam.viewportHeight / 2, orthCam.position.z);
            }
            if (orthCam.position.y < orthCam.viewportHeight / 2){
                orthCam.position.set(orthCam.position.x, orthCam.viewportHeight / 2, orthCam.position.z);
            }
        }

        batch.begin();
        sprUser.draw(batch);
        sprFinish.draw(batch);
        batch.end();

        //Learned some of this from https://www.baeldung.com/java-measure-elapsed-time
        if (currentTimeMillis() - lTimeStart > 5000 && hasZoomed == false){
            orthCam.setToOrtho(false,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
            hasZoomed = true;
            sprUser.setDirection(0);
        }

        //This is the only part that still uses the LibGdx input processing,
        //As updateState cannot be called from the inputListener
        if (sprUser.isFinished(sprFinish) || Gdx.input.isKeyPressed(Input.Keys.N)) concreteJungle.updateState(2);
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
