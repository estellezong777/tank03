package tankGame03;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{


    private int bulletSize = 1;

   // private Boolean isAlive = true; 写在父类

    Vector<Bullet> enemyBullets =new Vector<>();

    Bullet bullet = null;

    Vector<EnemyTank> enemyTanks = null;
    //将从Panel创建的enemyTanks传入（本类方法中需要使用）+搭配set方法使用 （外面拿本类属性--get， 本类拿本类（在外面创造的）属性，用set）

    static int y = 0;

    /*static */int x;

    boolean move = false;

    int nbMove = 30;

    public EnemyTank(int x, int y) {
        super(x, y);

        //若写在构造器里而不是下面方法里，则在创建敌人坦克对象时就会自动创建bullet
        /*for(int i =  0; i <= bulletSize; i++){
*/
            //注，enemy tank 的direction是后面set的，所以可能初始化是父类TANK里的“u”！！
          /*  switch (getDir()) {
                case "u":
                    bullet = new Bullet(x+ 17,y , "u");
                    break;
                case "d":*/
                  //  bullet = new Bullet(x+ 17,y + 60, "d");
            /*        break;
                case "l":
                    bullet = new Bullet(x, y +17, "l" );
                    break;
                case "r":
                    bullet = new Bullet(x + 60 , + 17, "r");
                    break;
            }*/

            //每new一个对象便放入vector中
           /* enemyBullets.add(bullet);
            //System.out.println("敌人子弹X ="+ bullet.getX() + "\t Y =" + bullet.getY());


            //在循环里启动敌人Bullet线程
            new Thread(bullet).start();
        }*/

    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //move randomly
   // public void moveRandom(){ 方法体最好直接写进run; 不然可能sleep后此方程执行次数和run里面的不同，因为是外面panel里还套了个for循环vector

    public boolean isTouchEnemyTank() {

        switch (this.getDir()) {
            //1
            case "u":
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case "u":
                            case "d":
                                //enemyTank !=this的碰撞范围
                                //this左上角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //this右上角点进入enemyTank
                                if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                            case "l":
                            case "r":
                                //enemyTank !=this的碰撞范围
                                //this左上角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                //this右上角点进入enemyTank
                                if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                        }
                    }
                }

                     //2
            case "d":
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case "u":
                            case "d":
                                //enemyTank !=this的碰撞范围
                                //this左下角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        (this.getY()+60 >= enemyTank.getY()) && (this.getY()+60 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //this右下角点进入enemyTank
                                if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 40 &&
                                        (this.getY()+60 >= enemyTank.getY()) && (this.getY() +60 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                            case "l":
                            case "r":
                                //enemyTank !=this的碰撞范围
                                //this左下角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        (this.getY() + 60 >= enemyTank.getY()) && (this.getY()+60 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                //this右下角点进入enemyTank
                                if (this.getX() + 40 >= enemyTank.getX() && this.getX() + 40 <= enemyTank.getX() + 60 &&
                                        (this.getY()+60 >= enemyTank.getY()) && (this.getY()+60 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                        }
                    }
                }
                //3
            case "l":
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case "u":
                            case "d":
                                //enemyTank !=this的碰撞范围
                                //this左上角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //this左下角点进入enemyTank
                                if (this.getX()>= enemyTank.getX() && this.getX() <= enemyTank.getX() + 40 &&
                                        (this.getY()+40 >= enemyTank.getY()) && (this.getY() +40 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                            case "l":
                            case "r":
                                //enemyTank !=this的碰撞范围
                                //this左上角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                //this左下角点进入enemyTank
                                if (this.getX() >= enemyTank.getX() && this.getX() <= enemyTank.getX() + 60 &&
                                        (this.getY()+40  >= enemyTank.getY()) && (this.getY()+40 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                        }
                    }
                }
            //4
            case "r":
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        switch (enemyTank.getDir()) {
                            case "u":
                            case "d":
                                //enemyTank !=this的碰撞范围
                                //this右上角点进入enemyTank
                                if (this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX() + 40 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                                //this右下角点进入enemyTank
                                if (this.getX() +60 >= enemyTank.getX() && this.getX() +60 <= enemyTank.getX() + 40 &&
                                        (this.getY()+40 >= enemyTank.getY()) && (this.getY() +40 <= enemyTank.getY() + 60)) {
                                    return true;
                                }
                            case "l":
                            case "r":
                                //enemyTank !=this的碰撞范围
                                //this右上角点进入enemyTank
                                if (this.getX() +60  >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX() + 60 &&
                                        (this.getY() >= enemyTank.getY()) && (this.getY() <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                                //this右下角点进入enemyTank
                                if (this.getX()+60 >= enemyTank.getX() && this.getX()+60 <= enemyTank.getX() + 60 &&
                                        (this.getY()+40  >= enemyTank.getY()) && (this.getY()+40 <= enemyTank.getY() + 40)) {
                                    return true;
                                }
                        }
                    }
                }
        }

        //otherwise: Missing return statement
        return false; //若都不满足（没有触碰）
    }

    @Override
    public void run() {
      while (true){    //必须放到while里！！！不然只执行一次然后线程停止工作。

          if(enemyBullets.size() < bulletSize){    //保证 Vector<Bullet> enemyBullets 每次只有一个element,当vector.size() = 0 时再new objet
              //Bullet bullet = null;
              switch (getDir()) {
                case "u":
                    bullet = new Bullet(getX()+ 17,getY() , "u");   //父类属性要用getX 来获取最新数据
                    break;
                case "d":
                    bullet = new Bullet(getX()+ 17,getY() + 60, "d");
                    break;
                case "l":
                    bullet = new Bullet(getX(), getY() +17, "l" );
                    break;
                case "r":
                    bullet = new Bullet(getX() + 60 , getY()+ 17, "r");
                    break;
            }
            enemyBullets.add(bullet);
          }
          new Thread(bullet).start();


            int dirRandom = new Random().nextInt(4);//ramdom int : 0-3
            System.out.println(dirRandom);


            // System.out.println("初始:"+ x + "\t" + y);
            System.out.println("初始:" + this + "\t"+getX() + "\t" + getY());

          //先改变方向
          String stringDir = "d" ;
          if (dirRandom == 0){
              stringDir = "u";      //以后方向还是做成数字吧。。。
          }else if (dirRandom == 1){
              stringDir = "d";
          }else if (dirRandom == 2 ){
              stringDir = "l";
          }else if (dirRandom == 3){
              stringDir = "f";
          }
          setDir(stringDir);

          //再按抽的方向移动
            switch (stringDir){
                case "u":   //up
                    for(int i = 0; i < nbMove; i++){/*for loop 必须写外面，否则每次都可能不同方向*/
                        if(!isTouchEnemyTank()) {  //****还是要写进run里面！！ 不然敌方坦克sleep，但keyPressed没有？加上vector loop就会出错？
                            moveUp(); }   //直接父类方法,就不用下面蓝色的了
                        /*int newY1 = getY();
                        setY(--newY1);*/   //为什么这样可以！ 可以但没必要
                            //setY(--y);    //为什么这个不行！  可能和vector 循环依次改变有关；之后研究我累了。。。。
                            //y--;  //bug！！！ why?? -- 父类private属性要get才行！！！，不然就此类y一直==0
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                        }
                    }
                    break;

                case "d":   //down
                    for(int i = 0; i < nbMove; i++){
                        if(!isTouchEnemyTank()) {
                            // setY(++y);
                            moveDown();}
                       /* int newY2 = getY();
                        setY(++ newY2);*/
                            // y++;
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                    }
                    break;
                case "l":   //left

                    for(int i = 0; i < nbMove; i++) {
                        //setY(--x);  //
                        /*int newX1 = getX();
                        setX(--newX1);*/
                        //x--;
                        if(!isTouchEnemyTank()) {
                        moveLeft();}
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;

                case "r":   //right
                    for(int i = 0; i < nbMove; i++) {
                        if(!isTouchEnemyTank()) {
                        /*int newX2 = getX();
                        setX(++ newX2);*/
                        moveRight();}
                        //x++;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }

            }//持续朝一个方向移动一段时间再换方向



          //  System.out.println("之后:"+this+ "\t"+ getX() + "\t" + getY());
            // moveRandom(); //最好直接写进run; 不然可能sleep后此方程执行次数和run里面的不同


          //  System.out.println("move ba 1 :"+ this+ "\t" +x + "\t" + y);

            //写并发程序时，一定要考虑该线程什么时候结束
            if(getAlive()!= true){
                break;
            }

        }


    }

        }






