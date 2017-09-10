import java.io.IOException;
import java.util.ArrayList;


public class Main {
    public static void main(String args[]) throws IOException
    {
        /*
          Images of classes to matrix
          Saving matrixes to .txt
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
}
}
