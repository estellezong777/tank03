package tankGame03;

import java.io.*;
import java.util.IllegalFormatCodePointException;
import java.util.Vector;

public class Recorder {
    private static int destroyedEne = 0 ;  //must be static to be used in a static method
    /*private static FileWriter fw = null;*/
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;


    private static String recordFile = "d:\\myRecordTank.txt";

    private static String recordObject = "d:\\myRecordObject.dat";

    //private EnemyTank enemyTank = null;

    private static Vector<EnemyTank> enemytanks = null;

    private static ObjectOutputStream oos = null; /*new ObjectOutputStream(new FileOutputStream(recordFile)); */

    private static ObjectInputStream ois = null;
    public static int getDestroyedEne() {
        return destroyedEne;
    }

    public static void setDestroyedEne(int destroyedEne) {
        Recorder.destroyedEne = destroyedEne;
    }

    public static void addDestroyedEne(){
        Recorder.destroyedEne++;
    }

    public static Vector<EnemyTank> getEnemytanks() {
        return enemytanks;
    }

    public static void setEnemytanks(Vector<EnemyTank> enemytanks) {
       /* this.enemytanks = enemytanks; *////bug  this.enemytanks（需要实例化）不能放在static方法
        Recorder.enemytanks = enemytanks;

    }

    public static void keepRecord(){   //called while closing
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));

           // bw.write(destroyedEne + "\r\n");
            bw.write(enemytanks.size()+ "\r\n");
            oos = new ObjectOutputStream(new FileOutputStream(recordObject));

            if(enemytanks != null)
            {for(EnemyTank enemytank: enemytanks){     //注！！一开始为空！！因为是在hitTank function里new的，所以加if条件，
                // 打死之前关闭窗口不用write object, 否则tankGame03.Recorder.enemytanks" is null 关不了窗口
                System.out.println("啊啊啊啊我在writteeee");
                System.out.println("啊啊啊啊我在writteeee");
                System.out.println("啊啊啊啊我在writteeee" + enemytank);
                    //java.io.NotSerializableException: tankGame03.Bullet!!!!Bullet 也得Serializable！！！！
                oos.writeObject(enemytank/*+ "\r\n"*/);  //newline caracter  //+ "\r\n" or +“”， 转成String，必须要转成String？？Why？？
                //=========EnemyTank里的所有属性都必须Reliazable！！！！！！！

               /* ois = new ObjectInputStream(new FileInputStream(recordObject));
                System.out.println(ois.readObject());*/    // should be called while opening to read
            }

            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(bw != null){
                bw.close();}
                if(oos != null){
                oos.close();}
               /* if(ois!= null){
                ois.close();}*/
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //Z thoughts：
    //Stackoverflow: https://stackoverflow.com/questions/27409718/java-reading-multiple-objects-from-a-file-as-they-were-in-an-array
    //new methods to choose to restart or continue the with memerised: potition xy, enemynumber, enemy object
    //return the vector of objects: enemytank-s that i read
    public static Vector<EnemyTank>/*enemytanks*/ keepRead(){
        /*Vector<EnemyTank>*/ enemytanks = new Vector<>();
        //boolean read = true;
            int nbEnemytank = 0;
            Object enemyTank = null;
            EnemyTank enemytankE = null;

            try {
                br = new BufferedReader(new FileReader(recordFile));
                ois = new ObjectInputStream(new FileInputStream(recordObject));
                nbEnemytank = Integer.parseInt(br.readLine());
                System.out.println("nbEnemytank"+ nbEnemytank);
               /* while(true) {*/
               /* if ((enemyTank = ois.readObject()) == null) { //总会到达-1
                    System.out.println("-1" );
                    break;}*/  //=======

                for (int i =0; i<nbEnemytank; i++ ){
                    enemytankE = (EnemyTank) ois.readObject();
                    if (enemytankE != null) {
                        enemytanks.add(enemytankE);
                    }

                }
              /*  } else*//* if (ois.readObject() != null) {*/

                    /*if(ois != null) {*/
                    //  enemytankE = (EnemyTank)ois.readObject();      //Object
                    /*System.out.println("The operation type of enemytank is String?"+enemytank instanceof String ); */   //Bug!!!enemytank 的运行类型是String?(The operation type of enemytank is String?)
                    //true AaaaaaaaaaaH NO
                    /*System.out.println("classs of enemytank" + enemytank.getClass());*/  //classs of enemytankclass java.lang.String  应该是objectStream.EnemyTank才对。。。
                    // enemytankE = (EnemyTank) enemytank; //向下转型  EnemyTank enemytankE = (EnemyTank)enemytank;
                    // BUG!!!class java.lang.String cannot be cast to class tankGame03.EnemyTank
                    // (java.lang.String is in module java.base of loader 'bootstrap'; tankGame03.EnemyTank is in unnamed module of loader 'app')
                    //======FINALLY!!!/*class tankGame03.EnemyTank
                    //                Readed enemytankE null* NOWWWWW/  //终于！！！！！！！ 因为一开始EnemyTank类里的属性bullet没有Relizable!!!!所以一直是读出来sTRING!!  took me the whole afternoon and evening
                    /*class tankGame03.EnemyTank
                Readed enemytankE null* NOWWWWW/  //终于！！！！！！！ 因为一开始EnemyTank类里的属性bullet没有Relizable!!!!所以一直是读出来sTRING!!  took me the whole afternoon and evening
                    System.out.println("Readed enemytankE " + enemytankE);}
               /*}*/
                   /* enemytankE = (EnemyTank)enemyTank;
                    System.out.println(enemytankE);*/
               /* }*/


           /* }finally {
                try {
                    if (enemytankE != null){
                    ois.close();}
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }*/


            }catch(IOException e){
                throw new RuntimeException(e);
            } catch(ClassNotFoundException e){
                throw new RuntimeException(e);
            }
              try {
                        ois.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


             return enemytanks;
            }



    }



