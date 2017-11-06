import java.io.IOException;

public class MatrixUtil {

    int Kol_class = 4;
    int Kol_oznak = 100;
    int Kol_realiz = 100;

    //Для оптимізації системи допусків
    int delta[]=new int[Kol_oznak];
    int tclass[]=new int[Kol_class];
    int tdelta;
    int Dopusk_ver[]=new int[Kol_oznak];
    int Dopusk_niz[]=new int[Kol_oznak];
    // Для оптимізації контейнерів
    int Center[][]=new int[Kol_class][Kol_oznak];
    int Radius[]=new int[Kol_class];
    int no_rab_obl_Radius[]=new int[Kol_class];
    double pomylka_betta[]=new double[Kol_class];
    double dostovirn_D1[]=new double[Kol_class];
    double max_KFE[]=new double[Kol_class];
    double no_rab_obl_pomylka_betta[]=new double[Kol_class];
    double no_rab_obl_dostovirn_D1[]=new double[Kol_class];
    double no_rab_obl_max_KFE[]=new double[Kol_class];
    int Nav4_matr[][][]=new int [Kol_class][Kol_oznak][Kol_realiz];
    int Bin_nav4_matr[][][]=new int[Kol_class][Kol_oznak][Kol_realiz];
    int Kodova_vidstan[][]=new int[2][Kol_realiz];
    int Class_sosed[] =new int[Kol_class];
    int do_soseda[]=new int[Kol_class];
    //Лічильники
    int Oznaka,Realiz,Klass,kl,t;
    double e0;
    int i_ds, j_ds;
    boolean a;
    int my_i, my_j, my_ji, delta_i;
    //file f1, f2, f3;
    double sum_real, em_avg;
    int ff_x;
    String out;

/*
* TODO:Последовательный алгоритм над параллельным. Есть в примере С
* */
void Parallel_System_Dopusk(int delta_max) throws IOException {

    FileUtil fileWriter = new FileUtil("E_delta(параллельный)X.txt");
    j_ds=0;
    e0=0;
    for (i_ds=0; i_ds < delta_max; i_ds++){
        if ((i_ds / 20)==(int)(i_ds / 20)){
            out=i_ds+"-";
            fileWriter.StreamOut(out);}
        for (int delta_i=0; delta_i < Kol_oznak; delta_i++){
            delta[delta_i]=i_ds;
            //      System_Dopuskov();
            //     Nav4();}
            a=true;
            for (my_i=1;my_i< Kol_class; my_i++){
                if(Radius[my_i] == 0) {a=false;}
                out= (Dopusk_ver[1] - Dopusk_niz[1])/2+" "+a+"\n";
                fileWriter.StreamOut(out);
                for (my_i=0; my_i< Kol_class; my_i++) {
                    out=" "+max_KFE[my_i]+" "+Radius[my_i]+" "+dostovirn_D1[my_i]+" "+pomylka_betta[my_i]+" "+ Class_sosed[my_i]+" "+do_soseda[my_i];
                    fileWriter.StreamOut(out); }
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
        //     System_Dopuskov;
        //    Nav4;
    }
}
}