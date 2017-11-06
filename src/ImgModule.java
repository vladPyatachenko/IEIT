import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImgModule {


    static BufferedImage loadImage(String path) throws IOException
    {
        return ImageIO.read(new File(path));
    }

    static int[][] imageToMatrix(BufferedImage img)
    {
        int h = img.getHeight();
        int w = img.getWidth();
        int[][] matrix = new int [h][w];
        Color color;
        for(int i = 0; i< h; i++)
        {
            for(int k = 0; k < w; k++)
            {
                color = new Color(img.getRGB(k, i));
                matrix[i][k] = (color.getRed()+color.getGreen()+color.getBlue())/3;
            }
        }
        return matrix;
    }

    public static ArrayList imageSplit(BufferedImage img, int size)
    {
        int height = img.getHeight()/size;
        int width = img.getWidth()/size;

        ArrayList<ImOperator> images = new ArrayList<>();

        for(int h = 0; h < height; h++)
        {
            for(int w = 0; w < width; w++)
            {
                BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
                int t = 0;
                for(int i = 0; i < size; i++)
                {
                    for(int j = 0; j < size; j++)
                    {
                        image.setRGB(i, j, img.getRGB( (w*size + i), (h*size + j) )  );
                    }
                }

                images.add(new ImOperator(image, w, h));
            }
        }

        return images;
    }

    public static BufferedImage imageBuild(ArrayList<ImOperator> surfaces, int sizeX, int sizeY)
    {
        BufferedImage map = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        for (ImOperator s : surfaces)
        {
            BufferedImage img = s.getSurfaceImage();
            for (int x = 0; x < img.getWidth(); x++)
            {
                for (int y = 0; y < img.getHeight(); y++)
                {
                    map.setRGB(s.getX()*img.getWidth() + x, s.getY()*img.getHeight() + y, img.getRGB(x, y));
                }
            }
        }

        return map;
    }

    public static void imageWrite(BufferedImage img, String path) throws IOException
    {
        ImageIO.write(img, "png", new File(path));
    }

    public static void drawBorder(BufferedImage img, Color color)
    {
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                if (i == 0 || j == 0 || i == img.getWidth()-1 || j == img.getHeight()-1)
                {
                    img.setRGB(i, j, color.getRGB());
                }
            }
        }
    }

    public static void drawText(BufferedImage img, String text, Color color, int x, int y)
    {
        Graphics graphics = img.getGraphics();
        graphics.setColor(color);
        graphics.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        graphics.drawString(text, 45, 50);
    }
}
