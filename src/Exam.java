import java.util.ArrayList;

public class Exam {

        private ArrayList<ExamData> EXAM_CLASSES = new ArrayList<>();

        public void addExamClass(ExamData c)
        {
            EXAM_CLASSES.add(c);
        }

        public ExamData recognize(int [] realization, ArrayList<ExamData> examClasses)
        {
            double minDistance = Integer.MAX_VALUE;
            ExamData recognizedClass = null;

            for (ExamData examinee : examClasses)
            {
                double dst = ClassRec.calculateDistance(realization, examinee.getVector());
                if (dst < minDistance && ClassRec.calculateDistance(realization, examinee.getVector())<examinee.getRadius())
                {
                    minDistance = dst;
                    recognizedClass = examinee;
                }
            }

            return recognizedClass;
        }

        public ExamData recognize(int[] realization)
        {
            return recognize(realization, EXAM_CLASSES);
        }


        public static class ExamData extends Exam{
            String name;
            int[][] array;
            int[] vector;
            int radius;

            public ExamData(String name, int[][] array) {
                this.name = name;
                this.array = array;
            }

            void refArray(int[][] array){
                for(int i=0;i<array.length;i++){
                    vector[i]=array[Integer.parseInt(getName())][i];
                }

                setVector(vector);
            }

            public String getName() {
                return name;
            }

            public int[] getVector() {
                refArray(getArray());
                return vector;
            }

            public void setVector(int[] vector) {
                this.vector = vector;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public int getRadius() {
                return radius;

            }

            public void setName(String name) {
                this.name = name;
            }

            public int[][] getArray() {
                return array;
            }

            public void setArray(int[][] array) {
                this.array = array;
            }
        }
}

