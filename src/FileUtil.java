import java.io.*;
import java.util.Scanner;

import static java.lang.String.*;

class FileUtil {
    /**
     * File operator utilites
     * saving to sile
     * and reading from files
     */
    private String name;
    //private static String path = "F:\\IEIT\\matrix\\";
    private static String path = "F:\\IEIT\\results\\";
    private static String classpath = "F:\\IEIT\\classes\\";
    FileUtil(String name) {
        this.name = name;
            }

        void WriteFile(int[][] array) throws IOException {
        FileWriter filewriter = new FileWriter(new File(path+name));
        for (int i = 0; i < array.length; i++){
            for (int j = 0; j < array.length; j++){
                filewriter.write(array[i][j] + " ");
        filewriter.flush();}
    }
    }
public static void checkdel(String name){
        File f= new File(path+name);
        if(f.length()!=0){
            f.delete();
        }
}
    int[][] ReadFile(int size) throws IOException {
        int[][] array = new int[size][size];
        Scanner scanner = new Scanner(new File(path + name));
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                        array[i][j] = scanner.nextInt();
                }
            }
        return array;
    }
    void StreamOut(String out) throws IOException {
        try {

            RandomAccessFile file = new RandomAccessFile(path+name, "rw");


            file.skipBytes((int)file.length()); //skip to the end of the file

            file.writeBytes(out);

            file.close();

        }catch(Exception e){

            System.out.print(e.getMessage());

        }
    }

}