package gdx.ConcreteJungle;


public class Level {
    String mapFile;

    int nStartX, nStartY, nFinishX, nFinishY;
    int nAllottedTime, nPrevTime, nEnemies;
    int arnEnemyX[] = new int [nEnemies];
    int arnEnemyY[] = new int [nEnemies];

    public int getAllottedTime(){
        return nAllottedTime;
    }

    public int getPrevTime(){
        return nPrevTime;
    }

    public int getStartX(){
        return nStartX;
    }

    public int getStartY(){
        return nStartY;
    }

    public int getFinishX(){
        return nFinishX;
    }

    public int getFinishY(){
        return nFinishY;
    }

    public String getMap() {
        return mapFile;
    }

    public int getEnemies(){
        return nEnemies;
    }

    public int getEnemyX(int nEnemyNum){
        return arnEnemyX[nEnemyNum];
    }

    public int getEnemyY(int nEnemyNum){
        return arnEnemyY[nEnemyNum];
    }
}
