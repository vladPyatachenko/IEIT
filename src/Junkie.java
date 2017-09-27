import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Junkie {


    //Для створення звітів
    private static boolean Zvit_Radius_Center;
    private boolean Zvit_Delta;
    String FT, FT_EXM;
    String FileName;

    private static int Kol_class = 4;
    private static int Kol_oznak = 100;
    private static int Kol_realiz = 100;

    //Для оптимізації системи допусків
    private static int[] delta = new int[Kol_oznak];
    private static int[] tclass = new int[Kol_class];
    int tdelta;
    private static int[] Dopusk_ver = new int[Kol_oznak];
    private static int[] Dopusk_niz = new int[Kol_oznak];
    // Для оптимізації контейнерів
    static int[][] Center = new int[Kol_class][Kol_oznak];
    static int[] Radius = new int[Kol_class];
    static int[] no_rab_obl_Radius = new int[Kol_class];
    static double[] pomylka_betta = new double[Kol_class];
    static double[] dostovirn_D1 = new double[Kol_class];
    static double[] max_KFE = new double[Kol_class];
    private static double[] no_rab_obl_pomylka_betta = new double[Kol_class];
    static double[] no_rab_obl_dostovirn_D1 = new double[Kol_class];
    static double[] no_rab_obl_max_KFE = new double[Kol_class];
    static int[][][] Nav4_matr = new int[Kol_class][Kol_oznak][Kol_realiz];
    static int[][][] Bin_nav4_matr = new int[Kol_class][Kol_oznak][Kol_realiz];
    static int[][] Kodova_vidstan = new int[2][Kol_realiz];
    static int[] Class_sosed = new int[Kol_class];
    static int[] do_soseda = new int[Kol_class];
    //Лічильники
    static int Oznaka;
    static int Realiz;
    static int Klass;
    int kl;
    int t;
    static double t_D1;
    static double t_Betta;
    double e0;
    int i_ds, j_ds;
    boolean a;
    int my_i, my_j, my_ji, delta_i;
    //file f1, f2, f3;
    double sum_real, em_avg;
    int ff_x;
    String out;

    public static double getT_D1() {
        return t_D1;
    }

    public static void setT_D1(double t_d1) {
        t_D1 = t_d1;
    }

    public static double getT_Betta() {
        return t_Betta;
    }

    public static void setT_Betta(double t_betta) {
        t_Betta = t_betta;
    }



    /*
        Procedure Poslidov_System_Dopusk(MY_k:integer;delta_max:integer);
        var
          e0:real;
          i_ds,j_ds,ii:integer;
          e_step:real;
          a:boolean;
          my_i,my_j,my_ji:integer;
          sum_real, em_avg:real;
          f2:text;
        begin
        if Zvit_Delta then
        begin assign(f2,concat('E_delta(последовательный)X',inttostr(kl),'.dat'));
        rewrite(f2);end;
          em_avg:=0;
        repeat
          e_step:=em_avg;
          for ii:= 1 to Kol_oznak do
            begin
            if trunc(ii/30)=ii/30 then write(ii,' - ');
            j_ds:=0;
            e0:=0;
            for i_ds:=0 to delta_max do
              begin
              delta[ii]:=i_ds;
              Oznaka_Dopusk(ii);
              Nav4;
              a:=true;
              for my_i:= 1 to Kol_class do
                if Radius[my_i]=0 then a:=false;
              em_avg:=0;
              for my_i:= 1  to Kol_class do
                em_avg:=em_avg+max_KFE[my_i];
              em_avg:=em_avg/Kol_class;
              if (em_avg>e0)and (a) then begin e0:=em_avg; j_ds:=i_ds;end;
              end;
            delta[ii]:=j_ds;
            Oznaka_Dopusk(ii);
            Nav4;
            em_avg:=0;
            for my_i:= 1 to Kol_class do
              em_avg:=em_avg+max_KFE[my_i];
            em_avg:=em_avg/Kol_class;
            if Zvit_Delta then
        begin
          write(f2,Oznaka,' ',(Dopusk_ver[ii]-Dopusk_niz[ii])/2,' ',a,' ',em_avg);
          for my_i:=1 to Kol_class do
           write(f2,' ',max_KFE[my_i],' ',Radius[my_i],' ',dostovirn_D1[my_i],' ',pomylka_betta[my_i],' ',Class_sosed[my_i],' ',do_soseda[my_i]);
          writeln(f2);
        end;
            end;
            writeln;
        until em_avg=e_step;
        if Zvit_Delta then closefile(f2);
        end;





        begin
         { TODO -oUser -cConsole Main : Insert code here }
        Zvit_Radius_Center:=true;
        Zvit_Delta:=true;
        for Klass:=1 to Kol_class do
         Zavant_Nav4_Matr(Klass,IntToStr(Klass)+'.txt');
        for t := 1 to Kol_oznak do
          tclass[t]:=1;
        Zvit_Radius_Center:=false;
        Parallel_System_Dopusk(kl,200);
        //Poslidov_System_Dopusk(kl,200);
        Zvit_Radius_Center:=true;
        System_Dopuskov;
        Nav4;
        Writeln('TEACH..Done');
        Readln;
        end.
        */
    static void Nav4(){
    double te, td1, tbetta;
    int t,ti,sum,kc;
        for (Klass=0;Klass< Kol_class;Klass++){
            for (Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
            for (Realiz=0;Realiz<Kol_realiz;Realiz++){
            if((Nav4_matr[Klass][Oznaka][Realiz]>=Dopusk_niz[Oznaka])&&
                    (Nav4_matr[Klass][Oznaka][Realiz]<=Dopusk_ver[Oznaka])){
                Bin_nav4_matr[Klass][Oznaka][Realiz]=1;}
                else {Bin_nav4_matr[Klass][Oznaka][Realiz]=0;}
            }
            }
        }
//формируем єталонные вектора
for (Klass=0;Klass<Kol_class;Klass++){
            for (Oznaka=0;Oznaka< Kol_oznak;Oznaka++){
                sum=0;
  for (Realiz=0;Realiz<Kol_realiz;Realiz++){
    sum=sum+Bin_nav4_matr[Klass][Oznaka][Realiz];
        if((sum/Kol_realiz)>=0.5){
        Center[Klass][Oznaka]=1;}
        else {Center[Klass][Oznaka]=0;}
        }
      }
   }
    // поиск ближайшего класса
  for (Klass=0;Klass<Kol_class;Klass++){
    Class_sosed[Klass]=1;
    do_soseda[Klass]=Kol_oznak;
 for (kc=0;kc<Kol_class;kc++){
    if(Klass!= kc){
    sum=0;
    for (Oznaka=0;Oznaka<Kol_oznak;Oznaka++) {
        sum = sum + abs(Center[Klass][Oznaka] - Center[kc][Oznaka]);
        if (sum <= do_soseda[Klass]) {
            Class_sosed[Klass]=kc;
            do_soseda[Klass]=sum;
            }
        }
     }
 }
}
//    AssignFile(FT_EXM,'REZ.TXT');

  //  Rewrite(FT_EXM);
  if(Zvit_Radius_Center){
   for (ti=0;ti<Kol_oznak;ti++) {
     //  Write(FT_EXM, Dopusk_ver[ti], ' ', Dopusk_niz[ti], chr(9));
     //Writeln(FT_EXM);

   }
 for (Klass=0;Klass<Kol_class;Klass++){
 if(Zvit_Radius_Center) {
    // AssignFile(FT, 'REZ' + inttostr(Klass) + '.TXT');
    // Rewrite(FT);
     Result_Nav4(Klass);
 }
 max_KFE[Klass]=0;
 no_rab_obl_max_KFE[Klass]=0;
 Radius[Klass]=0;

   for (Realiz=0;Realiz < Kol_realiz;Realiz++) {
       Kodova_vidstan[1][Realiz] = 0;
       Kodova_vidstan[2][Realiz] = 0;
       for (Oznaka = 0; Oznaka < Kol_oznak; Oznaka++) {
           Kodova_vidstan[1][Realiz] = Kodova_vidstan[1][Realiz] +
                   abs(Center[Klass][Oznaka] - Bin_nav4_matr[Klass][Oznaka][Realiz]);
           Kodova_vidstan[2][Realiz] = Kodova_vidstan[2][Realiz] +
                   abs(Center[Klass][Oznaka] - Bin_nav4_matr[Class_sosed[Klass]][Oznaka][Realiz]);

       }
   }
   for (t=0;t<Kol_oznak;t++){
    te=KFE(t);
    td1=getT_D1();
    tbetta=getT_Betta();
      if(Zvit_Radius_Center){
          /*
    Writeln(FT, t, chr(9),te:0:5,

    chr(9),td1:0:3,

    chr(9),tbetta:0:3,

    chr(9),1-td1:0:3,

    chr(9),1-tbetta:0:3,

    chr(9),round(td1*Kol_realiz),

    chr(9),round((1-td1)*Kol_realiz),

    chr(9),round((1-tbetta)*Kol_realiz),

    chr(9),round(tbetta*Kol_realiz));*/
      }
      if (te>no_rab_obl_max_KFE[Klass]) {
          no_rab_obl_max_KFE[Klass] = te;
          no_rab_obl_Radius[Klass] = t;
          no_rab_obl_dostovirn_D1[Klass] = td1;
          no_rab_obl_pomylka_betta[Klass] = tbetta;
      }
      if((td1>=0.5)&&(tbetta<0.5)&&(t<do_soseda[Klass])){
      if (te>max_KFE[Klass]) {
          max_KFE[Klass] = te;
          Radius[Klass] = t;
          dostovirn_D1[Klass] = td1;
          pomylka_betta[Klass] = tbetta;
      }
      }
   }
   if(Zvit_Radius_Center){
  /*  Writeln(FT,'Class ',Klass, chr(9),'Em= ',max_KFE[Klass]:0:5,

    chr(9),'do= ',Radius[Klass],

    chr(9),'dc=',do_soseda[Klass],

    chr(9),'D1= ',dostovirn_D1[Klass]:0:2,

    chr(9),'Betta= ',pomylka_betta[Klass]:0:2);

    Writeln(FT_EXM, Klass);
   */
   for(ti=0;ti< Kol_oznak;ti++){
   /* Write(FT_EXM, Center[Klass, ti],' ');

    Writeln(FT_EXM);

    Writeln(FT_EXM, Radius[Klass]);

    closefile(FT);
*/
}
}
  //  closefile(FT_EXM);

}
  }
    }
    static void System_Dopuskov() {
        for (int Oznaka = 0; Oznaka < Kol_oznak; Oznaka++)
            Oznaka_Dopusk(Oznaka);
    }

    static double KFE(int t) {

        int k1, k2, k3, k4;
        double d1_b;
        double KFE;

            k1 = 0;
            k2 = 0;
            k3 = 0;
            k4 = 0;
            for (Realiz = 0; Realiz < Kol_realiz; Realiz++) {
                if (Kodova_vidstan[1][Realiz] <= t) {
                    k1++;
                }
                if (Kodova_vidstan[2][Realiz] <= t) {
                    k3++;
                }
            }
            k4 = Kol_realiz - k3;
            k2 = Kol_realiz - k1;
            t_D1 = k1 / Kol_realiz;
            t_Betta = k3 / Kol_realiz;
            d1_b = t_D1 - t_Betta;
            setT_Betta(t_Betta);
            setT_D1(t_D1);
            //KFE:=d1_b*ln((1+d1_b+0.1)/(1-d1_b+0.1))/ln(2);
            KFE = 1 + 0.5 * (log_(k3, k1) + log_(k4, k2) + log_(k2, k4) + log_(k1, k3));

        return KFE;
    }

    static void Result_Nav4(int num){
   // Writeln(FT,'Class ',num);
   // Writeln(FT,'BM[',num,']');
    for (Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
    for (Realiz=0;Realiz<Kol_realiz;Realiz++) {
        //Write(FT,Bin_nav4_matr[num,Oznaka,Realiz],chr(9));
        //Writeln(FT);
    }
    }
    //Writeln(FT,'EV[',num,']');
    for (Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
    //Write(FT,Center[num,Oznaka],chr(9));
    //Writeln(FT);
    //Writeln(FT,'PARA[',num,']=',Class_sosed[num]);
    //Writeln(FT,'dc[',num,']=',do_soseda[num]);
    //Writeln(FT,'d',chr(9),'E',chr(9),'D1',chr(9),'Betta',chr(9),'Alfa',chr(9),'D2',chr(9),'K1',chr(9),'K2',chr(9),'K3',chr(9),'K4');
    }
    }

    static double  log_(double k1_log, double k2_log) {
        double log_;
        if (k1_log == 0){ log_=0;}

          else {log_=(k1_log / (k1_log + k2_log)) * (Math.log(k1_log / (k1_log + k2_log)) / Math.log(2));}

        return log_;
    }

    static void Oznaka_Dopusk(int il) {
       int sum = 0;
       for (int Realiz=0;Realiz< Kol_realiz;Realiz++) {
           sum=sum + Nav4_matr[tclass[il]][il][Realiz];
           sum=round(sum / Kol_realiz);
       }
       Dopusk_ver[il]=sum + delta[il];
       Dopusk_niz[il]=sum - delta[il];

   }
    static void Parallel_System_Dopusk(int delta_max){
        double e0;
        int i_ds, j_ds;
        boolean a;
        int my_i, my_j, my_ji, delta_i;
        //file f1, f2, f3;
        double sum_real, em_avg;
        int ff_x;
        String out;

        FileUtil fileWriter = new FileUtil("E_delta(параллельный)X.txt");
        j_ds=0;
        e0=0;
        for (i_ds=0; i_ds < delta_max; i_ds++){
            if ((i_ds / 20)==(int)(i_ds / 20)){
                out=i_ds+"-";
                try {
                    fileWriter.StreamOut(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (delta_i = 0; delta_i < Kol_oznak; delta_i++){
                delta[delta_i]=i_ds;
                      System_Dopuskov();
                         Nav4();
            }
                a=true;
                for (my_i=0;my_i< Kol_class; my_i++){
                    if(Radius[my_i] == 0) {a=false;}
                    out= (Dopusk_ver[1] - Dopusk_niz[1])/2+" "+a+"\n";
                    try {
                        fileWriter.StreamOut(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (my_i=0; my_i< Kol_class; my_i++) {
                        out=" "+max_KFE[my_i]+" "+Radius[my_i]+" "+dostovirn_D1[my_i]+" "+pomylka_betta[my_i]+" "+ Class_sosed[my_i]+" "+do_soseda[my_i];
                        try {
                            fileWriter.StreamOut(out);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                em_avg=0;
                for (my_i=0;my_i< Kol_class;my_i++) {
                    em_avg=em_avg + max_KFE[my_i];
                    em_avg=em_avg / Kol_class;
                    if ((em_avg > e0)&&(a)) {
                        e0=em_avg;
                        j_ds=i_ds;
                    }
                }
            }
            for (delta_i=0;delta_i< Kol_oznak;delta_i++)
                delta[delta_i]=j_ds;
                 System_Dopuskov();
                 Nav4();
        }


    public static void main(String args[]) throws IOException{
       Junkie a = new Junkie();
        a.Zvit_Radius_Center=true;
        a.Zvit_Delta=true;
        //ArrayList<int[][]> matrix = new ArrayList();
        for(int i=0; i< a.Kol_class;i++){
            FileUtil reader=new FileUtil("Matrix"+i+".txt");
            int[][]array= reader.ReadFile(a.Kol_realiz);
            //matrix.add(array);
            for(int o=0; o< a.Kol_oznak;o++){
                for(int r=0; r< a.Kol_realiz;r++){
                a.Nav4_matr[i][o][r]=array[o][r];
            }
            }
        }

        a.Zvit_Radius_Center=false;
        Parallel_System_Dopusk(200);
        a.Zvit_Radius_Center=true;
        a.System_Dopuskov();
        a.Nav4();
    }
}

