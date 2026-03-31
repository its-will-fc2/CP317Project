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

        // Offensive Programming: Check if grades are valid numbers
        boolean validGrades = true;
        for (double g : grades) {
            if (g < 0 || g > 100) {
                System.err.println("Warning: Invalid grade value detected for Student ID: " + getId() + ". Skipping course " + courseCode);
                validGrades = false;
                break;
            }
        }
        
        // If a grade was invalid, exit the method without adding the course
        if (!validGrades) {
            return; 
        }

        //Calculates final grade
        double finalGrade = ((test1 * 0.20) + (test2 * 0.20) + (test3 * 0.20)) + (finalExam * 0.40);

        // Formats the course and final grade for clean column alignment
        String results = String.format("%-10s %-15.1f", courseCode, finalGrade);

        courseResults.add(results);
    }

    public List<String> getCourseResults() {
        return courseResults;
    }
}
