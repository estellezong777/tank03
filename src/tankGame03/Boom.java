package tankGame03;

public class Boom {
    private int x;

    private int y;

    private boolean isAlive = false;

    private int life = 9;


    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void lifeDown(){

  /*      life--;               //bug 不严谨， = 0 后可继续--
        System.out.println("BoomLife ==" + life);
        if(life == 0){
            isAlive = false;
            System.out.println("爆炸结束");
            System.out.println("isAlive = " + isAlive);
        }*/

        if(life > 0){
            life--;
            System.out.println("BoomLife ==" + life);
        } else{
            isAlive = false;
            System.out.println("爆炸结束");
            System.out.println("isAlive = " + isAlive);
        }
    }
}
