import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]) throws IOException
    {
        int size = 100;
        /*
          Images of classes to matrix
          Saving matrixes to .txt for safety
         */
        ArrayList<String> classes = new ArrayList<>();

                classes.add("F:\\IEIT\\classes\\1.png");
                classes.add("F:\\IEIT\\classes\\2.png");
                classes.add("F:\\IEIT\\classes\\3.png");
                classes.add("F:\\IEIT\\classes\\4.png");

        ArrayList<int[][]> matrix = new ArrayList();

            for (String path : classes) {
            int[][] mas = ImgModule.imageToMatrix(ImgModule.loadImage(path));
            matrix.add(mas);
            }

            for (int i=0;i<matrix.size();i++){
            FileUtil writter = new FileUtil("Matrix"+i+".txt");
            writter.WriteFile(matrix.get(i));
            }

        /*
        TODO: uncomment
        Reading from matrix.txt to array
        for text files classification
         *
         *
            for(int i=0; i< classes.size();i++){
            FileUtil reader=new FileUtil("Matrix"+i+".txt");
            int[][]array= reader.ReadFile(size);
            matrix.add(array);
            }
            */

}
}
