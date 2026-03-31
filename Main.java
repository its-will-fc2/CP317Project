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
        } catch(Exception e) {
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

            //checks there is an ID and a name, throws exception if not
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                sc.close();
                throw new RuntimeException("NameFile.txt line has improper line");
            }
            //maps student ID to a new student object
            map.put(parts[0].trim(), new Student(parts[1].trim()));
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
            
            //checks that ID is in map, throws exception if not
            if (!map.containsKey(id)) {
                sc.close();
                throw new RuntimeException("Student ID: "  + id + " not found in NameFile");

            }
            //gets student object using their ID. Then calculates grade
            map.get(id).addCourseResult(p[1].trim(), Double.parseDouble(p[2].trim()), Double.parseDouble(p[3].trim()), Double.parseDouble(p[4].trim()), Double.parseDouble(p[5].trim()));

        }
        sc.close();
    }

    private static void writeOutput(String file, Map<String, Student> map) throws IOException {
        PrintWriter out = new PrintWriter(new File(file));

        for (Map.Entry<String, Student> entry: map.entrySet()) {
            String id = entry.getKey();
            Student s = entry.getValue();
            for (String result : s.getCourseResults()) {
                out.println(id + "," + s.getName() + "," + result);
            }
        }
        out.close();
    }
}

