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
            System.err.println("PROGRAM HALTED DUE TO ERROR: " + e.getMessage());
        }
    }

    private static void loadNames(String file, Map<String, Student> map) throws IOException {
        Scanner sc = new Scanner(new File(file));

        //read NameFile.txt
       while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split(",");

            // Offensive Programming: If the line is malformed, halt execution.
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                sc.close();
                throw new IllegalArgumentException("Corrupted data in NameFile.txt. Missing ID or Name.");
            }
            
            String id = parts[0].trim();
            String name = parts[1].trim();
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
            
            // OFFENSIVE PROGRAMMING: If a course record exists for a non-existent student, crash loudly.
            if (!map.containsKey(id)) {
                sc.close();
                throw new IllegalStateException("Data Integrity Error: Student ID " + id + " found in CourseFile but not in NameFile.");
            }
            
            // Offensive Programming - If Double.parseDouble fails, it will naturally throw a NumberFormatException
            map.get(id).addCourseResult(
                p[1].trim(), 
                Double.parseDouble(p[2].trim()), 
                Double.parseDouble(p[3].trim()), 
                Double.parseDouble(p[4].trim()), 
                Double.parseDouble(p[5].trim())
            );
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
