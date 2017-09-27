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
* program Teach;

{$APPTYPE CONSOLE}

uses
  Windows,  Messages,  SysUtils,  Variants,  Classes,  Graphics,  Controls,
  Forms,  Dialogs,  ExtCtrls,  Grids,  StdCtrls,  ImgList,  Menus,  ComCtrls,
  ExtDlgs;

const
  Kol_realiz=50;
  Kol_class=3;
  Kol_oznak=50;

var
  //Для створення звітів
  Zvit_Radius_Center,Zvit_Delta:boolean;
  FT,FT_EXM:Text;
  FileName:string;
  //Для оптимізації системи допусків
  delta,tclass:array [1..Kol_oznak] of integer;
  tdelta:integer;
  Dopusk_ver,Dopusk_niz:array[1..Kol_oznak] of integer;
  // Для оптимізації контейнерів
  Center:array[1..Kol_class,1..Kol_oznak] of integer;
  Radius, no_rab_obl_Radius:array[1..Kol_class]of integer;
  pomylka_betta, dostovirn_D1, max_KFE , no_rab_obl_pomylka_betta, no_rab_obl_dostovirn_D1, no_rab_obl_max_KFE:array[1..Kol_class]of real;
  Nav4_matr,Bin_nav4_matr:array[1..Kol_class,1..Kol_oznak,1..Kol_realiz] of integer;
  Kodova_vidstan:array [1..2, 1..Kol_realiz] of integer;
  Class_sosed,do_soseda:array[1..Kol_class] of integer;
  //Лічильники
  Oznaka,Realiz,Klass,kl,t:integer;

Procedure Result_Nav4(num:integer);
begin
Writeln(FT,'Class ',num);
Writeln(FT,'BM[',num,']');
for Oznaka:=1 to Kol_oznak do begin
for Realiz:=1 to Kol_realiz do
Write(FT,Bin_nav4_matr[num,Oznaka,Realiz],chr(9));
Writeln(FT);
end;
Writeln(FT,'EV[',num,']');
for Oznaka:=1 to Kol_oznak do
Write(FT,Center[num,Oznaka],chr(9));
Writeln(FT);
Writeln(FT,'PARA[',num,']=',Class_sosed[num]);
Writeln(FT,'dc[',num,']=',do_soseda[num]);
Writeln(FT,'d',chr(9),'E',chr(9),'D1',chr(9),'Betta',chr(9),'Alfa',chr(9),'D2',chr(9),'K1',chr(9),'K2',chr(9),'K3',chr(9),'K4');
end;


Procedure Oznaka_Dopusk(il:integer);
 var
  Sum:integer;
begin
  sum:=0;
  for Realiz:=1 to Kol_realiz do
   sum:=sum+Nav4_matr[tclass[il],il,Realiz];
  sum:=round(sum/Kol_realiz);
  Dopusk_ver[il]:= sum+delta[il];
  Dopusk_niz[il]:= sum-delta[il];
end;


Procedure System_Dopuskov;
begin
 for Oznaka:=1 to Kol_oznak do
   Oznaka_Dopusk(Oznaka);
end;


function KFE(t_:integer;var t_D1:real; var t_betta:real):real;
    function log_(k1_log, k2_log: double): double;
       begin
          if (k1_log=0) then log_:=0 {обрабатываем специальный случай}
          else log_:=(k1_log/(k1_log+k2_log))*(ln(k1_log/(k1_log+k2_log))/ln(2));
       end;

var k1,k2,k3,k4:integer;
    d1_b:double;
begin
  k1:=0;k2:=0;k3:=0;k4:=0;
  for Realiz:=1 to Kol_realiz do
  begin
       if Kodova_vidstan[1,Realiz]<=t_ then inc(k1);
       if Kodova_vidstan[2,Realiz]<=t_ then inc(k3);
  end;
   k4:=Kol_realiz-k3;
   k2:=Kol_realiz-k1;
   t_D1:=k1/Kol_realiz;
   t_Betta:=k3/Kol_realiz;
   d1_b:=t_D1-t_Betta;
   //KFE:=d1_b*ln((1+d1_b+0.1)/(1-d1_b+0.1))/ln(2);
   KFE:=1+0.5*(log_(k3, k1)+log_(k4, k2)+log_(k2, k4)+log_(k1, k3));
end;

Procedure Nav4;
var te,tD1,tbetta:real;
    t,ti,sum,kc:integer;
begin
//формируем бинарную мартицу
for Klass:=1 to Kol_class do
 for Oznaka:=1 to Kol_oznak do
  for Realiz:=1 to Kol_realiz do
   if (Nav4_matr[Klass,Oznaka,Realiz]>=Dopusk_niz[Oznaka])and(Nav4_matr[Klass,Oznaka,Realiz]<=Dopusk_ver[Oznaka])then Bin_nav4_matr[Klass,Oznaka,Realiz]:=1 else Bin_nav4_matr[Klass,Oznaka,Realiz]:=0;
//формируем єталонные вектора
for Klass:=1 to Kol_class do
 for Oznaka:=1 to Kol_oznak do begin
  sum:=0;
  for Realiz:=1 to Kol_realiz do
    sum:=sum+Bin_nav4_matr[Klass,Oznaka,Realiz];
  if (sum/Kol_realiz)>=0.5 then Center[Klass,Oznaka]:=1 else Center[Klass,Oznaka]:=0;
  end;
 // поиск ближайшего класса
  for Klass:=1 to Kol_class do
 begin
 Class_sosed[Klass]:=1; do_soseda[Klass]:=Kol_oznak;
 for kc:=1 to Kol_class do
  if (Klass<>kc)then  begin
    sum:=0; for Oznaka:=1 to Kol_oznak do sum:=sum+abs(Center[Klass,Oznaka]-Center[kc,Oznaka]);
    if sum<=do_soseda[Klass] then begin Class_sosed[Klass]:=kc; do_soseda[Klass]:=sum; end;
   end
 end;

 AssignFile(FT_EXM,'REZ.TXT');
 Rewrite(FT_EXM);
  if Zvit_Radius_Center then begin
   for ti:=1 to Kol_oznak do
     Write(FT_EXM,Dopusk_ver[ti],' ',Dopusk_niz[ti],chr(9));
   Writeln(FT_EXM);
 end;
 for Klass:=1 to Kol_class do begin
 if Zvit_Radius_Center then begin
 AssignFile(FT,'REZ'+inttostr(Klass)+'.TXT');
 Rewrite(FT);
 Result_Nav4(Klass);
 end;
   max_KFE[Klass]:=0;
   no_rab_obl_max_KFE[Klass]:=0;
   Radius[Klass]:=0;

   for Realiz:=1 to Kol_realiz do begin
   Kodova_vidstan[1,Realiz]:=0;Kodova_vidstan[2,Realiz]:=0;
    for Oznaka:=1 to Kol_oznak do begin
    Kodova_vidstan[1,Realiz]:=Kodova_vidstan[1,Realiz]+abs(Center[Klass,Oznaka]-Bin_nav4_matr[Klass,Oznaka,Realiz]);
    Kodova_vidstan[2,Realiz]:=Kodova_vidstan[2,Realiz]+abs(Center[Klass,Oznaka]-Bin_nav4_matr[Class_sosed[Klass],Oznaka,Realiz]);
  end;
end;
   for t:=0 to Kol_oznak do begin
     te:=KFE(t,tD1,tbetta);
      if Zvit_Radius_Center then
      Writeln(FT,t,chr(9),te:0:5,chr(9),td1:0:3,chr(9),tbetta:0:3,chr(9),1-td1:0:3,chr(9),1-tbetta:0:3,chr(9),round(td1*Kol_realiz),chr(9),round((1-td1)*Kol_realiz),chr(9),round((1-tbetta)*Kol_realiz),chr(9),round(tbetta*Kol_realiz));
      if te>no_rab_obl_max_KFE[Klass] then begin no_rab_obl_max_KFE[Klass]:=te;no_rab_obl_Radius[Klass]:=t;no_rab_obl_dostovirn_D1[Klass]:=td1;no_rab_obl_pomylka_betta[Klass]:=tbetta; end;
      if (tD1>=0.5)and(tbetta<0.5)and(t<do_soseda[Klass])then begin
      if te>max_KFE[Klass] then begin max_KFE[Klass]:=te;Radius[Klass]:=t;dostovirn_D1[Klass]:=td1;pomylka_betta[Klass]:=tbetta; end;
      end;
   end;
   if Zvit_Radius_Center then begin
   Writeln(FT,'Class ',Klass,chr(9),'Em= ',max_KFE[Klass]:0:5,chr(9),'do= ',Radius[Klass],chr(9),'dc=',do_soseda[Klass],chr(9),'D1= ',dostovirn_D1[Klass]:0:2,chr(9),'Betta= ',pomylka_betta[Klass]:0:2);

   Writeln(FT_EXM,Klass);
   for ti:=1 to Kol_oznak do
     Write(FT_EXM,Center[Klass,ti],' ');
   Writeln(FT_EXM);
   Writeln(FT_EXM,Radius[Klass]);
   closefile(FT);
   end;
 end;
    closefile(FT_EXM);
end;


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