import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<String> courseResults;

    public Student (String name) {
        this.name = name;
        this.courseResults = new ArrayList<>();
    }

    public void addCourseResult(String courseCode, double test1, double test2, double test3, double finalExam) {
        double[] grades = {test1, test2, test3, finalExam};

        //check if grades are valid numbers
        for (double g: grades) {
            if (g<0 || g > 100) {
                throw new RuntimeException("Invalid grade value detected");
            }
        }

        //Calculates final grade
        double finalGrade = ((test1 * 0.20) + (test2 * 0.20) + (test3 * 0.20)) + (finalExam * 0.40);

        //formats final grade to one decimal place
        String results = String.format("%s,%.1f", courseCode, finalGrade);

        courseResults.add(results);
    }

    public String getName() {
        return name;
    }

    public List<String> getCourseResults() {
        return courseResults;
    }


}
