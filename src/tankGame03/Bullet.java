package tankGame03;

import java.io.Serializable;

public class Bullet implements Runnable, Serializable {
    private int x;

    private int y;

    private String dir =null;

    private int speed = 5;

    private Boolean isAlive = true;

    public Bullet(int x, int y,String dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

 /*   public void moveUp(){

        if (y > 0) {
            //while(y>0) {  bug!!!!!!!!!!!一直moveUp 到 y=0 才执行repaint == 所以getY=100, 然后直接等于0！！
            //所以需要两个线程，一个线程更新左边，一个线程不停触发repaint 来刷新屏幕
            y -= speed;
            //}
        }
    }*/
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

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    @Override
    public void run() {

        //moveUp();
        while (true){
            switch (dir) {
                case "u":
                    y -= speed;
                    break;
                case "d":
                    y += speed;
                    break;
                case "l":
                    x -= speed;
                    break;
                case "r":
                    x += speed;
                    break;
            }
            try{
                Thread.sleep(50);
                } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
         //  System.out.println("x = " + x + "\t y =" + y );

            //当我方子弹击中敌方坦克时也应该销毁子弹--子弹线程结束
          /*  if (isAlive *//*不管是哪方子弹*//* == false){
                System.out.println("This thread is dead by shot");
                break; //结束线程
            } */ //合并到下方==

            //边界时销毁，线程结束
            if(!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 /*||*/ && isAlive)){
                System.out.println("This thread is dead");
                isAlive = false;
                break;
            }
        }

    }
}
