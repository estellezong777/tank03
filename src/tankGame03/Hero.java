package tankGame03;

import java.util.Vector;

public class Hero extends Tank implements Runnable{
    //定义一个bullet对象（线程）
    Bullet bullet = null;

    Vector<Bullet> bullets = new Vector<>();

    int nbBullets = 5;

    //int count = 0;
    public Hero(int x, int y){
        super(x,y);



    }

    public void shotEnemyTank(){
        //根据当前hero方向来创建bullet对象
        /*if (bullet == null) {*/  //new 了以后就线程结束也不会=null    注意！！bullet 线程消亡不等于bullet对象== null！！
      /*  if(bullet.getAlive()==false) {*/  //此刻还没new bullet,不能放在方法里面
        /*if (count < nbBullets){*/        //用计数器也可，但最好用vector长度
        if(bullets.size() < nbBullets){    //只有5发子弹第一个消亡 变成4个时bullet 才可以重新new bullet
          switch (getDir()) {
                case "u":
                    bullet = new Bullet(getX() + 17, getY(), "u");
                    // bullets.add(bullet); 整体写在外面就行了
                    //count ++;
                    break;
                case "d":
                    bullet = new Bullet(getX() + 17, getY() + 60, "d");
                    // bullets.add(bullet); 整体写在外面就行了
                    //count ++;
                    break;
                case "l":
                    bullet = new Bullet(getX(), getY() + 17, "l");
                    // bullets.add(bullet); 整体写在外面就行了
                   // count ++;
                    break;
                case "r":
                    bullet = new Bullet(getX() + 60, getY() + 17, "r");
                    // bullets.add(bullet); 整体写在外面就行了
                  //  count ++;
                    break;
            }bullets.add(bullet);

        } else{
            System.out.println("hero ran out of bullets");
        }
      /*  }

        */

        //启动Bullet线程
        new Thread(bullet).start();
    }


    @Override
    public void run() {

    }
}
