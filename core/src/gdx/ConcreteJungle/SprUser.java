package gdx.ConcreteJungle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class SprUser extends Sprite {
    int nDirection = 0;

    int nDX[] = new int[5];
    int nDY[] = new int[5];

    int nObjectLayerId;
    MapLayer mapCollisionObjectLayer;
    MapObjects mapObjects;

    public SprUser(Texture txImg) {
        super(txImg);
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
//        System.out.println ("Arrays set");
    }

    public void setDirection(int _nDirection){
//        System.out.println ("Direction received");
        nDirection = _nDirection;
    }

    public void moveUser(int nMod){
//        System.out.println ("moveUser called");
        translate(nMod * nDX[nDirection], nMod * nDY[nDirection]);
    }

    public boolean isFinished (Sprite sprTest){
        return super.getBoundingRectangle().overlaps(sprTest.getBoundingRectangle());
    }

    public boolean isBuildingHit(TiledMap map){
        //Stolen from https://stackoverflow.com/questions/20063281/libgdx-collision-detection-with-tiledmap
        nObjectLayerId = 2;
        //Was TiledMapTileLayer and trying to cast a MapLayer to it, can't do that but works just as MapLayers
        mapCollisionObjectLayer = map.getLayers().get(nObjectLayerId);
        mapObjects = mapCollisionObjectLayer.getObjects();

        //there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : mapObjects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            if (getBoundingRectangle().overlaps(rectangle)){
                return true;
            }
        }
        return false;
    }
}
