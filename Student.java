import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private List<String> courseResults;

    public Student(String id, String name) {
        super(id, name); // Calls the constructor of the abstract Person class
        this.courseResults = new ArrayList<>();
    }

    public void addCourseResult(String courseCode, double test1, double test2, double test3, double finalExam) {
        double[] grades = {test1, test2, test3, finalExam};

        // OFFENSIVE PROGRAMMING: Fail fast if data is invalid.
        // Do not silently skip. Throw an exception immediately.
        for (double g : grades) {
            if (g < 0 || g > 100) {
                throw new IllegalArgumentException("CRITICAL ERROR: Invalid grade value (" + g + ") detected for Student ID: " + getId());
            }
        }
        
        double finalGrade = ((test1 * 0.20) + (test2 * 0.20) + (test3 * 0.20)) + (finalExam * 0.40);
        String results = String.format("%-10s %-15.1f", courseCode, finalGrade);
        courseResults.add(results);
    }

    public List<String> getCourseResults() {
        return courseResults;
    }
}
