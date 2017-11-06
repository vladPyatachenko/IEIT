import java.util.ArrayList;

public class ClassRec {
    
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
}