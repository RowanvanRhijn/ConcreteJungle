
package gdx.ConcreteJungle.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    //Level currentLevel = concreteJungle.getLevel();
    int nDirection = 0, nHitDirection = 0;
    long lTimeStart;
    boolean hasZoomed = false;

    int mapWidth;
    int mapHeight;
    int tilePixelWidth;
    int tilePixelHeight;
    int mapTotalWidth;
    int mapTotalHeight;

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
        txUser = new Texture ("MainCharacter.png");
        txFinish = new Texture ("FinishPoint.png");
        sprUser = new Sprite (txUser);
        sprUser.setSize(32, 32);
        sprUser.setPosition(currentLevel.getStartX(), currentLevel.getStartY());
        sprFinish = new Sprite (txFinish);
        sprFinish.setSize(32, 256);
        sprFinish.setPosition(currentLevel.getFinishX(), currentLevel.getFinishY());

        //orthCam = new OrthographicCamera(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
        //orthCam.position.set(orthCam.viewportWidth / 2f, orthCam.viewportHeight / 2f, 0);

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

        //Once-a-frame checks and operations
        if(hasZoomed) moveUser();
        if (isBuildingHit(sprUser) && nHitDirection == 0) {
            if (nDirection == 1) sprUser.translateX(3);
            if (nDirection == 2) sprUser.translateX(-3);
            if (nDirection == 3) sprUser.translateY(-3);
            if (nDirection == 4) sprUser.translateY(3);
            nHitDirection = nDirection;
            nDirection = 0;
        }
        if (!isBuildingHit(sprUser)) nHitDirection = 0;
        if (hasZoomed) {
            orthCam.position.set(sprUser.getX() + sprUser.getWidth() / 2, sprUser.getY() + sprUser.getHeight() / 2, 0);

            //Some values here look weird because the camera is moving based on its centrepoint
            if (orthCam.position.x > mapTotalWidth - orthCam.viewportWidth / 2){
                orthCam.position.set(mapTotalWidth - orthCam.viewportWidth / 2, orthCam.position.y, orthCam.position.z);
            }
            if (orthCam.position.x < orthCam.viewportWidth / 2){
                orthCam.position.set(orthCam.viewportWidth / 2, orthCam.position.y, orthCam.position.z);
            }

            if (orthCam.position.y > mapTotalHeight - orthCam.viewportHeight / 2){
                orthCam.position.set(orthCam.position.x, mapTotalHeight - orthCam.viewportHeight / 2, orthCam.position.z);
            }
            if (orthCam.position.y < orthCam.viewportHeight / 2){
                orthCam.position.set(orthCam.position.x, orthCam.viewportHeight / 2, orthCam.position.z);
            }
        }

        //For debugging:
        if (Gdx.input.isKeyPressed(Input.Keys.S)) nDirection = 0;

        batch.begin();
        sprUser.draw(batch);
        sprFinish.draw(batch);
        batch.end();

        //Learned some of this from https://www.baeldung.com/java-measure-elapsed-time
        if (currentTimeMillis() - lTimeStart > 5000 && hasZoomed == false){
            orthCam.setToOrtho(false,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() /2);
            hasZoomed = true;
        }
        if (isFinished(sprUser, sprFinish)) {
            nDirection = 0;
            nHitDirection = 0;
            hasZoomed = false;
            concreteJungle.updateState(2);
        }
    }


    public static boolean isFinished (Sprite spr1, Sprite spr2){
        return spr1.getBoundingRectangle().overlaps(spr2.getBoundingRectangle());
    }

    public boolean isBuildingHit(Sprite spr1){
        //Stolen from https://stackoverflow.com/questions/20063281/libgdx-collision-detection-with-tiledmap
        int objectLayerId = 2;
        //Was TiledMapTileLayer and trying to cast a MapLayer to it, can't do that but works just as MapLayers
        MapLayer collisionObjectLayer = map.getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

        //there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            //System.out.println("Run once");
            Rectangle rectangle = rectangleObject.getRectangle();
            if (spr1.getBoundingRectangle().overlaps(rectangle)){
                return true;
            }
        }
        return false;
    }

    public void moveUser(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if (nHitDirection != 1) nDirection = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (nHitDirection != 2) nDirection = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            if (nHitDirection != 3) nDirection = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if (nHitDirection != 4) nDirection = 4;
        }
        if (nDirection == 1) {
            sprUser.translateX(-3);
        }
        if (nDirection == 2) {
            sprUser.translateX(3);
        }
        if (nDirection == 3) {
            sprUser.translateY(3);
        }
        if (nDirection == 4) {
            sprUser.translateY(-3);
        }
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
