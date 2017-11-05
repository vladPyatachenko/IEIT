import java.util.ArrayList;

public class Exam {

        private ArrayList<ClassRec> EXAM_CLASSES = new ArrayList<>();

        public void addExamClass(ClassRec c)
        {
            EXAM_CLASSES.add(c);
        }

        public ClassRec recognize(int [] realization, ArrayList<ClassRec> examClasses)
        {
            double minDistance = Integer.MAX_VALUE;
            ClassRec recognizedClass = null;

            for (ClassRec examinee : examClasses)
            {
                double dst = ClassRec.calculateDistance(realization, examinee.getEtalonVector());
                if (dst < minDistance && ClassRec.calculateDistance(realization, examinee.getEtalonVector())<examinee.getRadius())
                {
                    minDistance = dst;
                    recognizedClass = examinee;
                }
            }

            return recognizedClass;
        }

        public ClassRec recognize(int [] realization)
        {
            return recognize(realization, EXAM_CLASSES);
        }

        public static class ExamPair
        {
            int[] vector;
            ClassRec expectedClass;

            public ExamPair(int [] vector, ClassRec expectedClass)
            {
                this.vector = vector;
                this.expectedClass = expectedClass;
            }

            int[] getVector()
            {
                return vector;
            }

            ClassRec getExpectedClass()
            {
                return expectedClass;
            }
        }
    }
}
