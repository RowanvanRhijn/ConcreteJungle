package gdx.ConcreteJungle;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import gdx.ConcreteJungle.Screens.*;

public class InputListener implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        System.out.println ("Called");
        switch (keycode)
        {
            case Input.Keys.LEFT:
                ScrPlay.sprUser.setDirection(1);
                System.out.println ("1 set");
                break;
            case Input.Keys.RIGHT:
                ScrPlay.sprUser.setDirection(2);
                System.out.println ("2 set");
                break;
            case Input.Keys.UP:
                ScrPlay.sprUser.setDirection(3);
                System.out.println ("3 set");
                break;
            case Input.Keys.DOWN:
                ScrPlay.sprUser.setDirection(4);
                System.out.println ("4 set");
                break;
            case Input.Keys.S:
                ScrPlay.sprUser.setDirection(0);
                break;
        }
        return true;
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
        return false;
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
