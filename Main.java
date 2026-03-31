import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Create a treemap that is used to map a student id to their name, course, and grades
        Map<String, Student> studentMap = new TreeMap<>();

        try {
            loadNames("NameFile.txt", studentMap);
            loadCourses("CourseFile.txt", studentMap);
            writeOutput("Output.txt", studentMap);
            System.out.println("Processing complete. Check Output.txt for results.");
        } catch (Exception e) {
            System.err.println("Execution Error: " + e.getMessage());
        }
    }

    private static void loadNames(String file, Map<String, Student> map) throws IOException {
        Scanner sc = new Scanner(new File(file));

        //read NameFile.txt
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }
            
            String[] parts = line.split(",");

            // Offensive Programming: checks there is an ID and a name, skips if not
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                System.err.println("Warning: NameFile.txt has an improper line. Skipping.");
                continue; 
            }
            
            String id = parts[0].trim();
            String name = parts[1].trim();
            
            // maps student ID to a new student object
            map.put(id, new Student(id, name));
        }
        sc.close();
    }

    private static void loadCourses(String file, Map<String, Student> map) throws IOException {
        Scanner sc = new Scanner(new File(file));

        //read CourseFile.txt
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            
            if (line.isEmpty()) {
                continue;
            }

            String[] p = line.split(",");
            String id = p[0].trim();
            
            // Offensive Programming: checks that ID is in map, skips and warns if not
            if (!map.containsKey(id)) {
                System.err.println("Warning: Student ID " + id + " not found in NameFile. Skipping course record.");
                continue;
            }
            
            try {
                // gets student object using their ID. Then calculates grade
                map.get(id).addCourseResult(
                    p[1].trim(), 
                    Double.parseDouble(p[2].trim()), 
                    Double.parseDouble(p[3].trim()), 
                    Double.parseDouble(p[4].trim()), 
                    Double.parseDouble(p[5].trim())
                );
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                 System.err.println("Warning: Malformed data for Student ID " + id + ". Skipping row.");
            }
        }
        sc.close();
    }

    private static void writeOutput(String file, Map<String, Student> map) throws IOException {
        PrintWriter out = new PrintWriter(new File(file));

        // Print header for the table
        out.println(String.format("%-15s %-25s %-10s %-15s", "Student ID", "Student Name", "Course", "Final Grade"));
        out.println("-------------------------------------------------------------------");

        for (Map.Entry<String, Student> entry : map.entrySet()) {
            String id = entry.getKey();
            Student s = entry.getValue();
            
            if (s.getCourseResults().isEmpty()) {
                // If student has no courses, print them with N/A
                out.println(String.format("%-15s %-25s %-10s %-15s", id, s.getName(), "N/A", "N/A"));
            } else {
                for (String result : s.getCourseResults()) {
                    // result string is already formatted in Student.java
                    out.println(String.format("%-15s %-25s %s", id, s.getName(), result));
                }
            }
        }
        out.close();
    }
}
