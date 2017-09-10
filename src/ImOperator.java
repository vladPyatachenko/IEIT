import java.awt.image.BufferedImage;

class ImOperator {
    private BufferedImage img;
    private int xCoord;
    private int yCoord;

    ImOperator(BufferedImage img, int x, int y)
    {
        super();
        this.img = img;
        this.xCoord = x;
        this.yCoord = y;

    }

    int getX()
    {
        return xCoord;
    }

    int getY()
    {
        return yCoord;
    }

    BufferedImage getSurfaceImage()
    {
        return img;
    }
}
