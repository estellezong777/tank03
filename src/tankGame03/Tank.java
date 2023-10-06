package tankGame03;

import java.io.Serializable;

public class Tank implements Serializable {
    private int x;
    private int y;


    //bug！！！private String dir;
    private String dir = "u";   //have to set a default value, otherwise it's null-NullPointer exception in the switch
                               // case except that there's default in the switch case


    private int speed =3;

    private Boolean isAlive = true;

    Boom boom = null;





    //private tankGame02.Tank(int x, int y)  若是private, 子类Hero don't have the access to this constructor with super(x,y)
    public Tank(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveUp(){
        if(y>0) {
            y -= speed;}
    }

    public void moveDown(){
        if(y<650){
        y += speed;}
    }

    public void moveLeft(){
        if(x>0){
        x -= speed;}
    }

    public void moveRight(){
        if(x<920) {
            x += speed;}
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
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", dir='" + dir + '\'' +
                ", speed=" + speed +
                ", isAlive=" + isAlive +
                ", boom=" + boom +
                '}';
    }
}

