import java.util.ArrayList;

public class Exam {

        private static ArrayList<ExamData> EXAM_CLASSES = new ArrayList<>();

        public static void addExamClass(ExamData c)
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
            static int[] vector;
            int radius;

            public ExamData(String name, int[][] array) {

            }

            public ExamData(String name, int[] vector, int radius) {
                this.name = name;
                this.vector = vector;
                this.radius=radius;
            }


            public String getName() {
                return name;
            }

            public static int[] getVector() {
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
        }
}

