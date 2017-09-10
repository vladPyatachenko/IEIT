import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtil {
    /**
     * File operator utilites
     * saving to sile
     * and reading from files
     */
    private String name;
    private String path = "F:\\IEIT\\matrix\\";
    FileUtil(String name) {
        this.name = name;
    }
    public FileUtil() {
    }

    File file = new File(path+name);

    void WriteFile(int[][] array) throws IOException {
        FileWriter filewriter = new FileWriter(new File(name));

        for (int i = 0; i < array.length; i++)
            for (int j = 0; j < array.length; j++)
                filewriter.write(array[i][j] + " ");
        filewriter.flush();
    }

    public int[][] ReadFile(String name, int size) throws IOException {
        int[][] array = new int[size][size];
        Scanner scannerfile = new Scanner(name);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (scannerfile.hasNextInt())
                    array[i][j] = scannerfile.nextInt();
            }

        }
        return array;
    }
}