package tankGame03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    //add new enemyTank in vector: enemyTanks



    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemySize = 10;

    boolean moveRandom = false;

    Graphics g = null;

    Thread thread = null;

    Vector<Boom> booms = new Vector<>();

    //定义爆炸图片
    Image imageBoom1 = null;
    Image imageBoom2 = null;
    Image imageBoom3 = null;

    int nbEnemyDead= 0;


    public MyPanel(){




        Recorder.setEnemytanks(enemyTanks);   //？？ set的enemytanks是否会更新？ I think yes cuz it's a thread in the loop
        hero = new Hero(100,300);
        hero.setSpeed(3); //attribute 不同都写入constructor, 若不set则使用默认值

        //初始化敌军位置
        if (!isContinue()) {    //难道每一次都要问？？
            for (int i = 0; i < enemySize; i++) {
                EnemyTank enemyTank = new EnemyTank(100 * (i + 1), getY());
                //将从Panel创建的enemyTanks传入（本类方法中需要使用）+搭配set方法使用 （外面拿本类属性--get， 本类拿本类（在外面创造的）属性，用set）
                enemyTank.setEnemyTanks(enemyTanks);
                enemyTank.setDir("d");
                //在此开启enemyTank线程
                Thread threadEnemy = new Thread(enemyTank);
                threadEnemy.start();
                System.out.println("enemy Tank " + "x=" + enemyTank.getX() + "y=" + enemyTank.getY());
                enemyTanks.add(enemyTank);


            }
        }else {       //继续上次enemyTanks的Vector
            System.out.println("继续执行");
            enemyTanks = Recorder.keepRead();

            for(EnemyTank enemyTank: enemyTanks){
                System.out.println("再打印一下下Enemytanks"+enemyTank);
                //在此开启enemyTank线程
                Thread threadEnemy = new Thread(enemyTank);
                threadEnemy.start();
                System.out.println("enemy Tank " + "x=" + enemyTank.getX() + "y=" + enemyTank.getY());
            }




        }



        // bullet = new Bullet(hero.getX(), hero.getY());
        // thread = new Thread(bullet); BUG 如果直接在这里new! 初始炸弹位置对，但后来改变方向怎么办
        //thread.start();

        //initiate pictures
        imageBoom1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_1.gif"));
        imageBoom2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_2.gif"));
        imageBoom3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/bomb_3.gif"));




    }


    public void paint(Graphics g){
        super.paint(g);
        //System.out.println(enemyTanks.size());
        g.fillRect(0,0,1000,750);

        showInfo(g);

        if(hero.getAlive()){
        drawTank(hero.getX(),hero.getY(),g, hero.getDir(), 1);}  //必须要改成get，否则repaint后触发paint还是画同样的


        //draw every enemyTank in the vector: encmytanks (初始化位置)
        /*for(int i = 0; i< enemySize; i++){*  enemy 可能死亡 */
        for(int i = 0; i< enemyTanks.size(); i++) {
            //bug!!!! drawTank(enemyTanks[i]);-----------enemyTank.get(i)
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断当前敌方坦克是否存活，存活再画
            if(enemyTank.getAlive() ){
                /*Thread threadEnemy = new Thread(enemyTank);
                threadEnemy.start();*/   ///bug!!! 不能放在paint 里！ 因为paint在循环里？ run里面appelle了repaint(); 所以在循环的启动
                //threadEnemy的线程，造成移动混乱不smoothly
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDir(), 0);}
          //  System.out.println("raaaaandom"+ enemyTank.getX() +"\t"+ enemyTank.getY());




            //画出enemy所射出的子弹
            //取出Vector：enemyBullets里的子弹


            for (int j = 0; j < enemyTank.enemyBullets.size(); j++) {
                Bullet enemyBullet = enemyTank.enemyBullets.get(j);

                if (enemyBullet.getAlive() /*&& enemyTank.enemyBullets.get(j-1).getAlive() == false*/) {
                    //System.out.println("敌人子弹："+ (j) + enemyBullet);
                    drawBullets(enemyBullet.getX(), enemyBullet.getY(), g, "d", 0);

                } else {
                    enemyTank.enemyBullets.remove(enemyBullet);
                }
            }
       /*     if(hero.bullet != null) {  //否则一开始空指针异常
                if (hero.bullet.getX() >= enemyTank.getX() || hero.bullet.getX() <= enemyTank.getX()) {
                    if (hero.bullet.getY() >= enemyTank.getY() || hero.bullet.getY() <= enemyTank.getY()) {
                        System.out.println("booooom");
                        drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDir(),2);
                    }
                }

            }*/
        }







        //-------如何获取Hero 类里新建的bullet呢？？？ --  ！！！
       /* drawBullets(bullet.getX() + 17, bullet.getY(), g, hero.getDir(), 1);*/

        //Bug！Cannot invoke "tankGame02.Bullet.getX()" because "this.hero.bullet" is null 因为这里还没有appeler hero enemy Tank!! 但又必须写在paint里
        /*drawBullets(hero.bullet.getX(),hero.bullet.getY(),g,hero.bullet.getDir(),1); */ //这就是为什么要把bullet 写进Hero的attribute;)
        //解决：通过if条件保证生成Bullet对象后才画！
        /*if(hero.bullet != null*//*否则空指针，因为按j才有对象*//* && hero.bullet.getAlive() == true*//*击中敌方坦克便不用再画*//* ){
            drawBullets(hero.bullet.getX(),hero.bullet.getY(),g,hero.bullet.getDir(),0);
        }*/
        //==初期可以，但只针对一个hero bullet objet，当放入hero.bullets vectors 后要遍历hero.bullets
        //Vector 版：
        if (hero.bullets != null /*否则空指针，因为按j才有Vector*/){
            //前一个if保证已经创建hero.bullets vectors
            for(int i = 0; i < hero.bullets.size();i++){
                Bullet heroBullet = hero.bullets.get(i);  //取出Vector里的其中一个对象
                if (heroBullet.getAlive()&& hero.getAlive()== true)/*击中敌方坦克便不用再画*/{
                    drawBullets(heroBullet.getX(),heroBullet.getY(),g,heroBullet.getDir(),0);

                } else{  //若改bullet.getLive = false
                    hero.bullets.remove(heroBullet);

                }
            }

        }


        // System.out.println(bullet.getY());
            //while(bullet.getY() > 30);

        drawBoom(g);
       /* repaint(); *///或者写在run里，但注意在while里！！！ 但在methode paint里的repaint（）本身内置在不停重绘，底层里可能有while





    }

    //method which returns a boolean, the user can choose whether to restart or continue the last game
    public boolean isContinue(){
        System.out.println("Restart or continue?");
        System.out.println("Restart: type '1'");
        System.out.println("Continue: type '2'");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        boolean continu = true;

        switch (choice){
            case 1:       //Restart
                continu = false;   //????
                break;    //nothing to do
            case 2:       //Continue
                //call the methode to read to stocked data of enemytanks
                enemyTanks = Recorder.keepRead();
                continu = true;
                break;

        }
        return continu;//should return here

    }


    public void showInfo(Graphics g){
        g.setColor(Color.BLACK);
        Font font = new Font("Arial",Font.BOLD,25);
        g.setFont(font);

        g.drawString("Destroyed enemytanks: ",1020,30);
        drawTank(1020,60,g,"u",0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getDestroyedEne() +"",1080,100);
    }



    public void hitTank(Tank enemyTank, Bullet heroBullet ){  //只针对一个hero对象，需要放在一个遍历hero.bullets的Vector的方法(hitEnemyTank(向下修改))里被调用
        int bulletX = heroBullet.getX();
        int bulletY = heroBullet.getY();
       // int nbEnemyDead = 0;   if it's initialised here, nbDead++ is never used


        switch (enemyTank.getDir()){
            //switch 穿透性
            case "u":
            case "d":
                if(bulletX > enemyTank.getX() && bulletX < enemyTank.getX() + 40 && bulletY > enemyTank.getY() &&
                        bulletY < enemyTank.getY() + 60 && enemyTank.getAlive()){
                    /*enemyTank.getAlive() = false;*/ //bug
                    System.out.println("打洗你ud");
                    enemyTank.setAlive(false); //不是取参数值，而是改变参数
                    /*nbEnemyDead ++;*/
                    Recorder.addDestroyedEne();   //静态方法，直接调用
                    enemyTanks.remove(enemyTank); //若打中enemyTank，将其从Vector 中移除
                   /* Recorder.setEnemytanks(enemyTanks);*/ //将Vector 传入Recorder类来储存  //注：应该放在my Panel构造器里，保证一个没打死退出时也能write Object


                    hero.bullet.setAlive(false); //是打出子弹的也结束绘画

                    //击中后创建boom的objet,并放入booms Vector 里便于管理,每一个boom对象会调用drawBoom呈现在界面
                    enemyTank.boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    enemyTank.boom.setAlive(true);
                    booms.add(enemyTank.boom);

                }
                break;
            case "l":
            case "r":
                if(bulletX > enemyTank.getX() && bulletX < enemyTank.getX() + 60 && bulletY > enemyTank.getY() &&
                        bulletY < enemyTank.getY() + 40 && enemyTank.getAlive()/*否则打死后打同样位置还是会boom，而且boom会一直画，加上此条件敌人坦克死的一瞬间alive = false 便停止画了wow!*/){
                    System.out.println("打洗你lr");
                    enemyTank.setAlive(false); //不是取参数值，而是改变参数
                    enemyTanks.remove(enemyTank); //若打中enemyTank，将其从Vector 中移除
                    /*nbEnemyDead ++;*/            //
                    /* Recorder.setEnemytanks(enemyTanks);*/ //将Vector 传入Recorder类来储存  //注：应该放在my Panel构造器里，保证一个没打死退出时也能write Object// 注：应该放在my Panel构造器里，保证一个没打死退出时也能write Object
                    Recorder.addDestroyedEne();   //静态方法，直接调用
                    hero.bullet.setAlive(false); //是打出子弹的也结束绘画

                    //击中后创建boom的objet,并放入booms Vector 里便于管理
                    enemyTank.boom = new Boom(enemyTank.getX(), enemyTank.getY());
                    enemyTank.boom.setAlive(true);
                    booms.add(enemyTank.boom);

                }
                break;

        }
    }

    //hitTankHero
    public void hitTankHE(Hero tank, Bullet bullet ){  //只针对一个hero对象，需要放在一个遍历hero.bullets的Vector的方法(hitEnemyTank(向下修改))里被调用
        int bulletX = bullet.getX();    //tank 被击方   bullet 击炮方
        int bulletY = bullet.getY();

        switch (tank.getDir()){
            //switch 穿透性
            case "u":
            case "d":
                if(bulletX > tank.getX() && bulletX < tank.getX() + 40 && bulletY > tank.getY() &&
                        bulletY < tank.getY() + 60 && tank.getAlive()){
                    /*enemyTank.getAlive() = false;*/ //bug
                    System.out.println("打洗你ud");
                    tank.setAlive(false); //不是取参数值，而是改变参数
                    bullet.setAlive(false); //是打出子弹的也结束绘画

                    //击中后创建boom的objet,并放入booms Vector 里便于管理,每一个boom对象会调用drawBoom呈现在界面
                    tank.boom = new Boom(tank.getX(), tank.getY());
                    tank.boom.setAlive(true);
                    booms.add(tank.boom);

                }
                break;
            case "l":
            case "r":
                if(bulletX > tank.getX() && bulletX < tank.getX() + 60 && bulletY > tank.getY() &&
                        bulletY < tank.getY() + 40 && tank.getAlive()/*否则打死后打同样位置还是会boom，而且boom会一直画，加上此条件敌人坦克死的一瞬间alive = false 便停止画了wow!*/){
                    System.out.println("打洗你lr");
                    tank.setAlive(false); //不是取参数值，而是改变参数
                    tank.setAlive(false); //不是取参数值，而是改变参数
                    bullet.setAlive(false); //是打出子弹的也结束绘画

                    //击中后创建boom的objet,并放入booms Vector 里便于管理
                    tank.boom = new Boom(tank.getX(), tank.getY());
                    tank.boom.setAlive(true);
                    booms.add(tank.boom);
                }
                break;

        }
    }






    public void hitEnemyTank() {
        if (hero.bullets != null /*否则空指针，因为按j才有Vector*/) {//保证能进入以下for循环
            {
                for (Bullet heroBullet : hero.bullets) {
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);
                        hitTank(enemyTank, heroBullet);   //if yes, enemyTank.isAlive = false;
                        //loop Vector heroBullets and Vector enemytanks to compare the position of each herobullet and enemytank

                    }
                }
            }
        }
    }

    public void hitHeroTank(){
        //遍历敌方坦克及其bullets Vector(每次就一个objet)
        for(int i = 0; i< enemyTanks.size(); i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.enemyBullets.size() != 0){
            Bullet enemyBullet = enemyTank.enemyBullets.get(0); //(每次就一个objet) //但size==0 才创建新的enemyBullet，所有有可能有一段时间空指针
            hitTankHE(hero, enemyBullet);}
        }
    }

    public void drawTank(int x, int y, Graphics g, String dir, int type){

        //according to color
        switch (type){
            case 0:
                g.setColor(Color.CYAN);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;

        }

        //according to direction
        switch (dir){
            case "u":
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case "r":
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case "d":
                g.fill3DRect(x,y,10,60,false);
                g.fill3DRect(x+30,y,10,60,false);
                g.fill3DRect(x+10,y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case "l":
                g.fill3DRect(x,y,60,10,false);
                g.fill3DRect(x,y+30,60,10,false);
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;

            default:
                System.out.println("暂时pass");

        }
    }

    public void drawBullets(int x, int y, Graphics g, String dir, int type ){
        switch (type){
            case 0:
                g.setColor(Color.gray);
                break;
            case 1:
                g.setColor(Color.pink);
                break;
        }

        g.fillOval(x,y,5,5);
        }


    public void drawBoom(Graphics g){
        for( int i = 0; i < booms.size(); i++){
            Boom boom = booms.get(i);
            System.out.println("boom"+ boom + "活着吗" + boom.isAlive());

            //bug--只能看见最后一张，而且不动
            /*g.drawImage(imageBoom1,boom.getX(), boom.getY(),60,60,this);
            g.drawImage(imageBoom2,boom.getX(), boom.getY(),60,60,this);
            g.drawImage(imageBoom3,boom.getX(), boom.getY(),60,60,this);*/
            //做出爆炸渐变动作，需要一个递减的过程！！且放在线程run里sleep执行（hitEnemyTank()在run里）
            System.out.println("boom"+ boom + "活着吗" + boom.isAlive());
            //while(boom.isAlive()){
                if (boom.getLife() >=6){
                    System.out.println("xiao boom");
                    g.drawImage(imageBoom3,boom.getX(), boom.getY(),60,60,this);
                } else if (boom.getLife() >=3) {
                    g.drawImage(imageBoom2,boom.getX(), boom.getY(),60,60,this);
                    System.out.println("zhong boom");
                } /*else*///!!bug 还包含小于的情况！！
                  else if(boom.getLife() > 0)
                 {
                    g.drawImage(imageBoom1,boom.getX(), boom.getY(),60,60,this);
                    System.out.println("da boom");
                }
                boom.lifeDown();
                if (boom.isAlive()== false || boom.getLife() == 0){
                    booms.remove(boom);
                    //break;
                }
            }

       // }
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }



    @Override
    public void keyPressed(KeyEvent e) {
        //改变方向wsad
        if (e.getKeyCode() == KeyEvent.VK_W && hero.getAlive()== true) {
            hero.setDir("u");
            hero.moveUp();

            // enemyTanks.get(1).getDir() works for one tanks, but doesn't work in the loop:switch (enemyTanks.get(i).getDir()
            //也许因为敌方坦克线程+sleep 和hero（非线程）不相符
           /* System.out.println("enTTanksSIze" + enemyTanks.size());
            //draw tankGame02.Hero    //hero和敌方坦克不碰撞条件不应该放这，碰到后坦克不会消失，只是不会move!!!
            for (int i = 0; i < enemyTanks.size(); i++) { //The value changed at 'i++' is never used。。。Why??????
                EnemyTank enemyTank = enemyTanks.get(i);
                System.out.println(enemyTank);
                System.out.println("hero.getX()" + hero.getX());
                System.out.println("这1enemyTank.getX()" + "enemy=" + enemyTank + "x坐标=" + enemyTank.getX());
                System.out.println("hero.getX()-enemyTank.getX() = " + (hero.getX() - enemyTank.getX()));
                System.out.println("hero.getY()" + hero.getY());
                System.out.println("这2enemyTank.getY()" + "enemy=" + enemyTank + "y坐标=" + enemyTank.getY());
                System.out.println("hero.getY()-enemyTank.getY() = " + (hero.getY() - enemyTank.getY()));

               *//* switch (enemyTanks.get(1).getDir()) {   //*//*
                    *//*case "u":
                    case "d":*//*                 //因为每个tank是不同线程，即使碰到一个，遍历到其他其他坦克不碰到还是可以走aaaa
                        *//*if (!((hero.getX() - enemyTank.getX() < 40 | enemyTank.getX() - hero.getX() < 40)  //可能包含负数  hero.getX() - enemyTank.getX() < -150&& hero.getY() - enemyTank.getY() < -200
                                & (hero.getY() - enemyTank.getY() < 60 | enemyTank.getY() - hero.getY() < 60))) {*//*
                   if (!((Math.abs(hero.getX() - enemyTank.getX( ))< 40 )&&  //可能包含负数  hero.getX() - enemyTank.getX() < -150&& hero.getY() - enemyTank.getY() < -200
                     Math.abs(hero.getY() - enemyTank.getY()) < 60 )){
                            *//*System.out.println("hero.getX()"+ hero.getX() );
                            System.out.println("这1enemyTank.getX()"+ "enemy="+enemyTank +"x坐标="+enemyTank.getX() );
                            System.out.println("hero.getX()-enemyTank.getX() = " + (hero.getX()-enemyTank.getX()));
                            System.out.println("hero.getY()"+ hero.getY() );
                            System.out.println("这2enemyTank.getY()"+ "enemy="+enemyTank +"y坐标="+ enemyTank.getY() );
                            System.out.println("hero.getY()-enemyTank.getY() = " + (hero.getY()-enemyTank.getY() ));*//*
                            hero.moveUp();
                        }else{
                       System.out.println("啊呀呀被挡住了");
                   }*/


                   /* case "l":*/
                     /*   if (!((enemyTank.getX() - hero.getX() < 60) && (hero.getY() - enemyTank.getY() < 60 *//*|| hero.getY() - enemyTank.getY() < 60*//*))) {
                            hero.moveUp();  //必须要改成get，否则repaint后触发paint还是画同样的
                        }
                        break;
                  *//*  case "r":*//*
                        if (!((hero.getX() - enemyTank.getX() < 40)
                                && (hero.getY() - enemyTank.getY() < 60 *//*|| hero.getY() - enemyTank.getY() < 60)*//*))) {
                            hero.moveUp();  //必须要改成get，否则repaint后触发paint还是画同样的
                        }
                        break;


                }*/



        } else if (e.getKeyCode() == KeyEvent.VK_S && hero.getAlive()== true) {
            hero.setDir("d");
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A && hero.getAlive()== true) {
            hero.setDir("l");
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D && hero.getAlive()== true) {
            hero.setDir("r");
            hero.moveRight();
        }


      //  System.out.println("hero: x"+ hero.getX() + "\t"+"y"+ hero.getY());
        //marche，mais un peu low
        /*int y = hero.getY();     //思路，直接把方法封装到tank类里，因为直接可以y++, x++
        int x = hero.getX();
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            hero.setY(--y);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.setY(++y);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.setX(--x);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.setX(++x);}*/


        //按下j 就发射子弹
        /*if(e.getKeyCode() == KeyEvent.VK_J){
            //画出子弹
           *//* while(bullet.getY()>0) {
                //bullet.moveUp();
                thread.start(); // Cannot resolve symbol 'thread'  解决—！！将bullet start 封装dhero的ShotEnemy方法内
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
*//*
            }*/

        //按下j 就发射子弹
        if(e.getKeyCode() == KeyEvent.VK_J) {
            System.out.println("Key J is pressed");
            //for (Bullet bullet : hero.bullets ){   BUG!!! 此刻还没调用hero.shotEnemyTank();hero.bullets还没有生成，永远进不来此循环，
            //所以用此种循环时要考虑清楚是否objet are created.

                /*if (hero.bullet == null/*防止按j前空指针异常|| hero.bullet.getAlive() == false)*/ //只针对一个hero对象，初期用法，在将hero。bullet放入vectors
                //if (bullet == null || hero.bullet.getAlive() == false)
                //后需要遍历来画，一次可发五次子弹，第一颗消亡后才能重新new bullet 并paint--(maximum 5 bullets on the screen)
               // {
                    hero.shotEnemyTank();  //每按一次创建一个bullet objet+线程, 在其中一个线程消亡前可创建5个放在hero.bullets Vectors 里
               // }

            }

        }







    @Override
    public void keyReleased(KeyEvent e) {

    }



    //保持游戏进行时不停刷新屏幕，不只为了刷新bullet
    @Override
    public void run() {
        //注意加while!!!!
        while (true) {
            repaint();
            //判断hero子弹是否打到敌方坦克
            /*if(hero.bullet != null){     */   //只针对一个hero对象，初期用法，在将hero。bullet放入vectors
                //后需要遍历来画，一次可发五次子弹，第一颗消亡后才能重新new bullet 并paint--(maximum 5 bullets on the screen)
           /*     for(int i = 0; i < enemyTanks.size(); i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(enemyTank, hero);   //if yes, enemyTank.isAlive = false;
                }*/

            hitEnemyTank();
            /*System.out.println("死的坦克数Number of dead tanks："+ nbEnemyDead);*/
            hitHeroTank();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            }

        }
    }

