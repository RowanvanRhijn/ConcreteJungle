package gdx.ConcreteJungle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SprRectangle extends Sprite{

    public SprRectangle(Texture txImg) {
        super(txImg);
    }

    public boolean isClicked(){
        //Flipped the Y coordinates so that it works properly because Y is down for the mouse???
        if(Gdx.input.justTouched() && Gdx.input.getX() >= this.getX() && Gdx.input.getX() <= this.getX() + this.getWidth()
                && (Gdx.graphics.getHeight() - Gdx.input.getY()) >= this.getY() && (Gdx.graphics.getHeight() - Gdx.input.getY()) <= this.getY() + this.getHeight()) {
            return true;
        }
        return false;
    }
}
