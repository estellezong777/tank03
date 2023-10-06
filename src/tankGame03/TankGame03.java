package tankGame03;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class TankGame03 extends JFrame {
    MyPanel mp = null;

    public static void main(String[] args) {
        TankGame03 tankGame02 = new TankGame03();


    }

    public TankGame03() {
        mp = new MyPanel();

        Thread threadMp = new Thread(mp);
        threadMp.start();

        //works,但放入Recorder类里面更好
       /* //IOstream
        String filePath = "d:\\tank.dat";
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            ois = new ObjectInputStream(new FileInputStream(filePath));
            oos.writeObject(mp.nbEnemyDead);  //记录击败敌方坦克数
            System.out.println("读取的死亡坦克数"+ ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                oos.close();
                ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/
        this.add(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);

        //监听关闭窗口并传输数据
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                Recorder.keepRecord();
                System.exit(0);


            }

        });
    }
}

