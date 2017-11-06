import java.util.ArrayList;

public class ClassRec {
    
    static int CLASSCOUNT=0;

    private String name;
    private String basicName;
    private double radius;
    private static int[] etalonVector;
    private int[][] realizations;


    public static double calculateDistance(int[] vector1, int[] vector2)
    {
        double sum = 0;
        for (int i = 0; i < vector1.length; i++)
        {
            try
            {
                if(vector1[i]!=vector2[i]){
                    sum++ ;
                }
            } catch (ArrayIndexOutOfBoundsException e)
            {
                return sum;
            }
        }
        return sum;
    }

    public void setBasicName(String basicName)
    {
        this.basicName = basicName;
    }

    public String getBasicName()
    {
        return basicName;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public double getRadius()
    {
        return radius;
    }

    public static int[] getEtalonVector()
    {
        return etalonVector;
    }


    public void learn(ArrayList<int[]> rlz)
    {
        int[][] rArray = new int[rlz.size()][rlz.get(0).length];
        for(int i = 0; i < rlz.size(); i++)
        {
            System.arraycopy(rlz.get(i), 0, rArray[i], 0, rlz.get(i).length);
        }

        learn(rArray, rArray.length);
    }

    public void learn(int[][] learnMatrix, int limitHeight)
    {
        learn(learnMatrix, limitHeight, 0);
    }

    public void learn(int[][] learnMatrix, int limitHeight, int limitWidth) throws IllegalArgumentException
    {
        if (learnMatrix.length < limitHeight)
        {
            throw new IllegalArgumentException("param limit should be <"+learnMatrix.length);
        }

        int width = learnMatrix[0].length;
        realizations = new int[limitHeight][width - limitWidth];
        for (int i = 0; i < limitHeight; i++)
        {
            for (int j = 0; j < width - limitWidth; j ++)
            {
                realizations[i][j] = learnMatrix[i][j];
            }
        }

        calculateEtalon();
     //   realizations=ImgModule.toBinary(realizations, etalonVector, delta);
        calculateEtalon();
        //findRadius();

    }

    private void calculateEtalon()
    {
        etalonVector = new int[realizations[0].length];

        for (int w = 0; w < etalonVector.length; w++)
        {
            int sum = 0;
            for (int h = 0; h < realizations.length; h++)
            {
                sum = sum + realizations[h][w];
            }
            etalonVector[w] = sum / realizations.length;
        }
    }


}