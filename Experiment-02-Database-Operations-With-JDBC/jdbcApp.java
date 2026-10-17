
import java.sql.*;
import java.util.Scanner;

public class Jdbcapp {

    private static final String SERVER_URL =
            "jdbc:mysql://localhost:3306/";
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create database if it does not exist
            try (Connection con = DriverManager.getConnection(
                    SERVER_URL, USER, PASSWORD);
                 Statement st = con.createStatement()) {
                st.executeUpdate(
                        "CREATE DATABASE IF NOT EXISTS college");
            }

            // Create table if it does not exist
            try (Connection con = DriverManager.getConnection(
                    DB_URL, USER, PASSWORD);
                 Statement st = con.createStatement()) {
                st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS students (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "branch VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(150) UNIQUE" +
                    ")");
            }

            System.out.println("Database connected successfully!");
            runMenu();

        } catch (ClassNotFoundException e) {
            System.out.println(
                    "MySQL JDBC driver not found. Check Maven dependency.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n=== STUDENT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 0 -> {
                        System.out.println("Program closed.");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void addStudent() throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter branch: ");
        String branch = sc.nextLine().trim();

        System.out.print("Enter email (optional): ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || branch.isEmpty()) {
            System.out.println("Name and branch cannot be empty.");
            return;
        }

        String sql =
                "INSERT INTO students (name, branch, email) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, branch);
            ps.setString(3, email.isEmpty() ? null : email);

            ps.executeUpdate();
            System.out.println("Student added successfully.");
        }
    }

    private static void viewStudents() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY id";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            System.out.println("\nID | NAME | BRANCH | EMAIL");
            System.out.println("----------------------------------------");

            while (rs.next()) {
                found = true;
                System.out.printf("%d | %s | %s | %s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("branch"),
                        rs.getString("email"));
            }

            if (!found) {
                System.out.println("No students found.");
            }
        }
    }

    private static void searchStudent() throws SQLException {
        int id = readId("Enter student ID: ");

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Branch: " + rs.getString("branch"));
                    System.out.println("Email: " + rs.getString("email"));
                } else {
                    System.out.println("Student not found.");
                }
            }
        }
    }

    private static void updateStudent() throws SQLException {
        int id = readId("Enter student ID to update: ");

        System.out.print("Enter new name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter new branch: ");
        String branch = sc.nextLine().trim();

        System.out.print("Enter new email (optional): ");
        String email = sc.nextLine().trim();

        if (name.isEmpty() || branch.isEmpty()) {
            System.out.println("Name and branch cannot be empty.");
            return;
        }

        String sql =
                "UPDATE students SET name = ?, branch = ?, email = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, branch);
            ps.setString(3, email.isEmpty() ? null : email);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0
                    ? "Student updated successfully."
                    : "Student not found.");
        }
    }

    private static void deleteStudent() throws SQLException {
        int id = readId("Enter student ID to delete: ");

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0
                    ? "Student deleted successfully."
                    : "Student not found.");
        }
    }

    private static int readId(String message) {
        while (true) {
            System.out.print(message);
            try {
                int id = Integer.parseInt(sc.nextLine().trim());
                if (id > 0) {
                    return id;
                }
            } catch (NumberFormatException ignored) {
                // Ask again for valid input
            }
            System.out.println("Enter a positive numeric ID.");
        }
    }
                }
