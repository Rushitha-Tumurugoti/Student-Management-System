// Console-based Student Management System
// Features: CRUD, Semester Results, Subject Marks
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Class to store subject-wise marks
class SubjectMark {
    private String subjectName;
    private int marks;

    public SubjectMark(String subjectName, int marks) {
        this.subjectName = subjectName;
        this.marks = marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        return subjectName + " : " + marks;
    }
}

// Class to store semester-wise result
class SemesterResult {
    private int semester;
    private double totalMarks;   // total marks or percentage
    private double gradePoint;   // SGPA / grade points
    private List<SubjectMark> subjectMarks = new ArrayList<>();

    public SemesterResult(int semester, double totalMarks, double gradePoint) {
        this.semester = semester;
        this.totalMarks = totalMarks;
        this.gradePoint = gradePoint;
    }

    public int getSemester() {
        return semester;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public void addSubjectMark(String subjectName, int marks) {
        subjectMarks.add(new SubjectMark(subjectName, marks));
    }

    public void printSubjectMarks() {
        if (subjectMarks.isEmpty()) {
            System.out.println("   No subject-wise marks added.");
            return;
        }
        for (SubjectMark sm : subjectMarks) {
            System.out.println("   " + sm);
        }
    }

    @Override
    public String toString() {
        return "Sem " + semester +
               " | Total Marks: " + totalMarks +
               " | Grade Points: " + gradePoint;
    }
}

// Model class to represent a Student
class Student {
    private int id;
    private String name;
    private int age;
    private String grade; // or department

    private List<SemesterResult> semesterResults = new ArrayList<>();

    public Student(int id, String name, int age, String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void addSemesterResult(int semester, double totalMarks, double gradePoint) {
        SemesterResult existing = getSemesterResult(semester);
        if (existing != null) {
            existing.setTotalMarks(totalMarks);
            existing.setGradePoint(gradePoint);
        } else {
            semesterResults.add(new SemesterResult(semester, totalMarks, gradePoint));
        }
    }

    public SemesterResult getOrCreateSemesterResult(int semester) {
        SemesterResult sr = getSemesterResult(semester);
        if (sr != null) {
            return sr;
        }
        SemesterResult newSr = new SemesterResult(semester, 0.0, 0.0);
        semesterResults.add(newSr);
        return newSr;
    }

    public SemesterResult getSemesterResult(int semester) {
        for (SemesterResult sr : semesterResults) {
            if (sr.getSemester() == semester) {
                return sr;
            }
        }
        return null;
    }

    public void printSemesterResults() {
        if (semesterResults.isEmpty()) {
            System.out.println("No semester data available.");
            return;
        }
        for (SemesterResult sr : semesterResults) {
            System.out.println("  " + sr);
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Name: " + name +
                " | Age: " + age +
                " | Class/Dept: " + grade;
    }
}

// Main System class
public class StudentManagementSystem {

    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextId = 1; // auto-increment student ID

    public static void main(String[] args) {
        int choice;

        System.out.println("===== Student Management System =====");

        do {
            showMenu();
            choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    addSemesterResultToStudent();
                    break;
                case 7:
                    viewSemesterResultsOfStudent();
                    break;
                case 8:
                    addSubjectMarksToSemester();
                    break;
                case 9:
                    viewSubjectMarksOfSemester();
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Add Semester Result to Student");
        System.out.println("7. View Semester Results of Student");
        System.out.println("8. Add Subject-wise Marks to Semester");
        System.out.println("9. View Subject-wise Marks of Semester");
        System.out.println("0. Exit");
    }

    private static void addStudent() {
        System.out.println("\n--- Add Student ---");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        int age = getIntInput("Enter age: ");

        System.out.print("Enter class/department: ");
        String grade = scanner.nextLine();

        Student student = new Student(nextId++, name, age, grade);
        students.add(student);

        System.out.println("Student added successfully with ID: " + student.getId());
    }

    private static void viewAllStudents() {
        System.out.println("\n--- Student List ---");

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void searchStudent() {
        System.out.println("\n--- Search Student ---");
        int id = getIntInput("Enter student ID to search: ");

        Student student = findStudentById(id);

        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int id = getIntInput("Enter student ID to update: ");

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Current details: " + student);

        System.out.print("Enter new name (leave empty to keep same): ");
        String newName = scanner.nextLine();
        if (newName != null && !newName.trim().isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Enter new age (leave empty to keep same): ");
        String ageInput = scanner.nextLine();
        if (ageInput != null && !ageInput.trim().isEmpty()) {
            try {
                int newAge = Integer.parseInt(ageInput);
                student.setAge(newAge);
            } catch (NumberFormatException e) {
                System.out.println("Invalid age. Keeping old value.");
            }
        }

        System.out.print("Enter new class/department (leave empty to keep same): ");
        String newGrade = scanner.nextLine();
        if (newGrade != null && !newGrade.trim().isEmpty()) {
            student.setGrade(newGrade);
        }

        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int id = getIntInput("Enter student ID to delete: ");

        Student student = findStudentById(id);

        if (student != null) {
            students.remove(student);
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Add semester result: total marks + grade points
    private static void addSemesterResultToStudent() {
        System.out.println("\n--- Add Semester Result ---");
        int id = getIntInput("Enter student ID: ");

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int sem = getIntInput("Enter semester number: ");
        double totalMarks = getDoubleInput("Enter total marks or percentage: ");
        double gradePoint = getDoubleInput("Enter grade points / SGPA: ");

        student.addSemesterResult(sem, totalMarks, gradePoint);
        System.out.println("Semester result added/updated successfully.");
    }

    // View semester-wise results of a student
    private static void viewSemesterResultsOfStudent() {
        System.out.println("\n--- View Semester Results ---");
        int id = getIntInput("Enter student ID: ");

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student: " + student);
        System.out.println("Semester-wise Results:");
        student.printSemesterResults();
    }

    // Add subject-wise marks for a student's semester
    private static void addSubjectMarksToSemester() {
        System.out.println("\n--- Add Subject-wise Marks ---");
        int id = getIntInput("Enter student ID: ");

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int sem = getIntInput("Enter semester number: ");
        SemesterResult sr = student.getOrCreateSemesterResult(sem);

        int subjectCount = getIntInput("How many subjects you want to add? ");

        for (int i = 0; i < subjectCount; i++) {
            System.out.print("Enter subject name " + (i + 1) + ": ");
            String subjectName = scanner.nextLine();

            int marks = getIntInput("Enter marks for " + subjectName + ": ");

            sr.addSubjectMark(subjectName, marks);
        }

        System.out.println("Subject-wise marks added successfully for Sem " + sem + ".");
    }

    // View subject-wise marks for a student's semester
    private static void viewSubjectMarksOfSemester() {
        System.out.println("\n--- View Subject-wise Marks ---");
        int id = getIntInput("Enter student ID: ");

        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        int sem = getIntInput("Enter semester number: ");

        SemesterResult sr = student.getSemesterResult(sem);
        if (sr == null) {
            System.out.println("No data found for that semester.");
            return;
        }

        System.out.println("Student: " + student);
        System.out.println("Semester " + sem + " subject-wise marks:");
        sr.printSubjectMarks();
    }

    private static Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    private static int getIntInput(String message) {
        int value;

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                value = Integer.parseInt(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String message) {
        double value;

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                value = Double.parseDouble(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}