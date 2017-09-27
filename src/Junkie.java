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
    private static int[] tclass = new int[Kol_oznak];
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

    static double t_D1;
    static double t_Betta;

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


{  /*
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
        */}
    static void Nav4(){
    double te, td1, tbetta;
    int t,ti,sum,kc;
        for (int Klass=0;Klass< Kol_class;Klass++){
            for (int Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
            for (int Realiz=0;Realiz<Kol_realiz;Realiz++){
            if((Nav4_matr[Klass][Oznaka][Realiz]>=Dopusk_niz[Oznaka])&&
                    (Nav4_matr[Klass][Oznaka][Realiz]<=Dopusk_ver[Oznaka])){
                Bin_nav4_matr[Klass][Oznaka][Realiz]=1;}
                else {Bin_nav4_matr[Klass][Oznaka][Realiz]=0;}
            }
            }
        }
//формируем єталонные вектора
for (int Klass=0;Klass<Kol_class;Klass++){
            for (int Oznaka=0;Oznaka< Kol_oznak;Oznaka++){
                sum=0;
  for (int Realiz=0;Realiz<Kol_realiz;Realiz++){
    sum=sum+Bin_nav4_matr[Klass][Oznaka][Realiz];
        if((sum/Kol_realiz)>=0.5){
        Center[Klass][Oznaka]=1;}
        else {Center[Klass][Oznaka]=0;}
        }
      }
   }
    // поиск ближайшего класса
  for (int Klass=0;Klass<Kol_class;Klass++){
    Class_sosed[Klass]=1;
    do_soseda[Klass]=Kol_oznak;
 for (kc=0;kc<Kol_class;kc++){
    if(Klass!= kc){
    sum=0;
    for (int Oznaka=0;Oznaka<Kol_oznak;Oznaka++) {
        sum = sum + abs(Center[Klass][Oznaka] - Center[kc][Oznaka]);
        if (sum <= do_soseda[Klass]) {
            Class_sosed[Klass]=kc;
            do_soseda[Klass]=sum;
            }
        }
     }
 }
}
        FileUtil fW = new FileUtil("REZ.txt");
  if(Zvit_Radius_Center){
   for (ti=0;ti<Kol_oznak;ti++) {
       String out="\t" +Dopusk_ver[ti]+"\t"+ Dopusk_niz[ti]+"\n";
       try {
           fW.StreamOut(out);
       } catch (IOException e) {
           e.printStackTrace();
       }

   }
 for (int Klass=0;Klass<Kol_class;Klass++){
     FileUtil fWr = new FileUtil("REZ"+Klass+".txt");

     if(Zvit_Radius_Center) {
     Result_Nav4(Klass,fWr);
 }
 max_KFE[Klass]=0;
 no_rab_obl_max_KFE[Klass]=0;
 Radius[Klass]=0;

   for (int Realiz=0;Realiz < Kol_realiz;Realiz++) {
       Kodova_vidstan[0][Realiz] = 0;
       Kodova_vidstan[1][Realiz] = 0;
       for (int Oznaka = 0; Oznaka < Kol_oznak; Oznaka++) {
           Kodova_vidstan[0][Realiz] = Kodova_vidstan[0][Realiz] +
                   abs(Center[Klass][Oznaka] - Bin_nav4_matr[Klass][Oznaka][Realiz]);
           Kodova_vidstan[1][Realiz] = Kodova_vidstan[1][Realiz] +
                   abs(Center[Klass][Oznaka] - Bin_nav4_matr[Class_sosed[Klass]][Oznaka][Realiz]);

       }
   }
   for (t=0;t<Kol_oznak;t++){
    te=KFE(t);
    td1=getT_D1();
    tbetta=getT_Betta();
      if(Zvit_Radius_Center){
          String out = t+"\t"+te+"\t"+td1+"\t"+tbetta+"\t"+(1-td1)+"\t"+(1-tbetta)+"\t"+round(td1*Kol_realiz)+"\t"+round((1-td1)*Kol_realiz)+"\t"+round((1-tbetta)*Kol_realiz)+"\t"+round(tbetta*Kol_realiz);
          try {
              fWr.StreamOut(out);
          } catch (IOException e) {
              e.printStackTrace();
          }
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
       String out1="Class "+Klass+" Em= "+max_KFE[Klass]+" do= "+Radius[Klass]+" dc= "+do_soseda[Klass]+" D1= "+dostovirn_D1[Klass]+" Betta= "+pomylka_betta[Klass]+"\n";
       String out2="\n"+Klass+"\n";
       try {
           fWr.StreamOut(out1);
           fW.StreamOut(out2);
       } catch (IOException e) {
           e.printStackTrace();
       }
   for(ti=0;ti< Kol_oznak;ti++){
       String out3=Center[Klass][ti]+"\t"+Radius[Klass]+"\t";
       try {
           fW.StreamOut(out3);
       } catch (IOException e) {
           e.printStackTrace();
       }

}
}

}
  }
    }
    static void System_Dopuskov() {
        for (int Oznaka = 0; Oznaka < Kol_oznak; Oznaka++)
            Oznaka_Dopusk(Oznaka);
    }

    static double KFE(int t) {

        double d1_b;
        double KFE;

        int    k1 = 0;
        int    k2 = 0;
        int    k3 = 0;
        int    k4 = 0;
            for (int Realiz = 0; Realiz < Kol_realiz; Realiz++) {
                if (Kodova_vidstan[0][Realiz] <= t) {
                    k1++;
                }
                if (Kodova_vidstan[1][Realiz] <= t) {
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

    static void Result_Nav4(int num, FileUtil wrf){
    String out="Class "+num+"\n BM["+num+"] \n";
        try {
            wrf.StreamOut(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    for (int Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
    for (int Realiz=0;Realiz<Kol_realiz;Realiz++) {
        String out0=Bin_nav4_matr[num][Oznaka][Realiz]+" ";
        try {
            wrf.StreamOut(out0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    String out1="\nEV["+num+"]\n";
        try {
            wrf.StreamOut(out1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    for (int Oznaka=0;Oznaka<Kol_oznak;Oznaka++){
    String out2=Center[num][Oznaka]+"\n";
    String rout="Para["+num+"]="+Class_sosed[num]+"\n"+"dc["+num+"]="+do_soseda[num]+"\n"+"d\tE\tD1\tBetta\tAlfa\tD2\tK1\tK2\tK3\tK4\n";
        try {
            wrf.StreamOut(out2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        String rout="Para["+num+"]="+Class_sosed[num]+"\n"+"dc["+num+"]="+do_soseda[num]+"\n"+"d\tE\tD1\tBetta\tAlfa\tD2\tK1\tK2\tK3\tK4\n";
        try {
            wrf.StreamOut(rout);
        } catch (IOException e) {
            e.printStackTrace();
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
        int my_i, delta_i;
        double em_avg;
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
                    out= (Dopusk_ver[1] - Dopusk_niz[1])/2+"\t"+a+"\n";
                    try {
                        fileWriter.StreamOut(out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (my_i=0; my_i< Kol_class; my_i++) {
                        out="\t"+max_KFE[my_i]+"\t"+Radius[my_i]+"\t"+dostovirn_D1[my_i]+"\t"+pomylka_betta[my_i]+"\t"+ Class_sosed[my_i]+"\t"+do_soseda[my_i];
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
        for(int i=0; i< a.Kol_class;i++){
            FileUtil reader=new FileUtil("Matrix"+i+".txt");
            int[][]array= reader.ReadFile(a.Kol_realiz);
            for(int o=0; o< a.Kol_oznak;o++){
                a.tclass[o]=0;
                for(int r=0; r< a.Kol_realiz;r++){
                a.Nav4_matr[i][o][r]=array[o][r];
            }
            }
        }

        //a.Zvit_Radius_Center=false;
        Parallel_System_Dopusk(10);
        //a.Zvit_Radius_Center=true;
        //a.System_Dopuskov();
        //a.Nav4();
    }
}

