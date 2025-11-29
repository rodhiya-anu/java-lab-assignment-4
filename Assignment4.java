import java.io.*;
import java.util.*;

class Student {
    int roll;
    String name, email, course;
    double marks;

    public Student(int roll, String name, String email, String course, double marks) {
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public String toString() {
        return roll + "," + name + "," + email + "," + course + "," + marks;
    }
}

class FileUtil {
    public static List<Student> readFromFile(String fileName) {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 5) {
                    int roll = Integer.parseInt(p[0]);
                    String name = p[1];
                    String email = p[2];
                    String course = p[3];
                    double marks = Double.parseDouble(p[4]);
                    list.add(new Student(roll, name, email, course, marks));
                }
            }
        } catch (Exception e) {}
        return list;
    }

    public static void writeToFile(String fileName, List<Student> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Student s : list) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (Exception e) {}
    }
}

public class Assignment4 {
    static List<Student> students = new ArrayList<>();
    static final String FILE = "students.txt";

    public static void loadRandomAccess() {
        try (RandomAccessFile raf = new RandomAccessFile(FILE, "r")) {
            if (raf.length() > 0) {
                raf.seek(0);
            }
        } catch (Exception e) {}
    }

    public static void viewAll() {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            System.out.println("Roll: " + s.roll);
            System.out.println("Name: " + s.name);
            System.out.println("Email: " + s.email);
            System.out.println("Course: " + s.course);
            System.out.println("Marks: " + s.marks);
            System.out.println();
        }
    }

    public static void searchByName(String n) {
        for (Student s : students) {
            if (s.name.equalsIgnoreCase(n)) {
                System.out.println("Roll: " + s.roll);
                System.out.println("Name: " + s.name);
                System.out.println("Email: " + s.email);
                System.out.println("Course: " + s.course);
                System.out.println("Marks: " + s.marks);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public static void deleteByName(String n) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().name.equalsIgnoreCase(n)) {
                it.remove();
                System.out.println("Deleted.");
                return;
            }
        }
        System.out.println("No match found.");
    }

    public static void sortByMarks() {
        students.sort(Comparator.comparingDouble(s -> s.marks));
        System.out.println("Sorted by marks.");
        viewAll();
    }

    public static void addStudent(Scanner sc) {
        System.out.print("Roll: ");
        int r = sc.nextInt();
        sc.nextLine();
        System.out.print("Name: ");
        String n = sc.nextLine();
        System.out.print("Email: ");
        String e = sc.nextLine();
        System.out.print("Course: ");
        String c = sc.nextLine();
        System.out.print("Marks: ");
        double m = sc.nextDouble();

        students.add(new Student(r, n, e, c, m));
    }

    public static void main(String[] args) {
        students = FileUtil.readFromFile(FILE);
        loadRandomAccess();

        Scanner sc = new Scanner(System.in);

        System.out.println("Loaded Students:");
        viewAll();

        while (true) {
            System.out.println("===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save & Exit");
            System.out.print("Choice: ");
            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> addStudent(sc);
                case 2 -> viewAll();
                case 3 -> {
                    System.out.print("Enter Name: ");
                    searchByName(sc.nextLine());
                }
                case 4 -> {
                    System.out.print("Enter Name: ");
                    deleteByName(sc.nextLine());
                }
                case 5 -> sortByMarks();
                case 6 -> {
                    FileUtil.writeToFile(FILE, students);
                    System.out.println("Saved. Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
