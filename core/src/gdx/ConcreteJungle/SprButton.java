package gdx.ConcreteJungle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SprButton extends Sprite{

    public SprButton(Texture txImg) {
        super(txImg);
    }

    public boolean isClicked(int screenX, int screenY){
        if(screenX >= this.getX() && screenX <= this.getX() + this.getWidth()
                && (Gdx.graphics.getHeight() - screenY) >= this.getY() && (Gdx.graphics.getHeight() - screenY) <= this.getY() + this.getHeight()) {
            return true;
        }
        return false;
    }
}
