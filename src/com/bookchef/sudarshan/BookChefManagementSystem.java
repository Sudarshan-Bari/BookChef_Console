package com.bookchef.sudarshan;
import java.sql.*;
import java.util.Scanner;
import com.databse1.remainder.ConnectionData;

public class BookChefManagementSystem {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        Connection con = null;

        try {
            con = ConnectionData.dbCon(); // Establish connection here

            do {
                try {
                    System.out.println("\n--- BookChef Management System ---");
                    System.out.println("1. Add Chef");
                    System.out.println("2. View All Chefs");
                    System.out.println("3. Update Chef");
                    System.out.println("4. Delete Chef");
                    System.out.println("5. Search Chef");
                    System.out.println("6. Exit");
                    System.out.print("Enter your choice: ");

                    if (scanner.hasNextInt()) { // Check if input is an integer
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume leftover newline
                    } else {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                        continue;
                    }

                    switch (choice) {
                        case 1:
                            addChef(scanner, con); // Pass the connection instance
                            break;
                        case 2:
                            viewAllChefs(con); // Pass the connection instance
                            break;
                        case 3:
                            updateChef(scanner, con); // Pass the connection instance
                            break;
                        case 4:
                            deleteChef(scanner, con); // Pass the connection instance
                            break;
                        case 5:
                            searchChef(scanner, con); // Pass the connection instance
                            break;
                        case 6:
                            System.out.println("Exiting... Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid choice! Please try again.");
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    scanner.nextLine(); // Clear the scanner buffer in case of an exception
                }
            } while (choice != 6);

        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close(); // Close connection at the end of the program
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        scanner.close(); // Close scanner at the end
    }

    public static void addChef(Scanner scanner, Connection con) {
        try {
            System.out.print("Enter Chef Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Speciality: ");
            String speciality = scanner.nextLine();

            String query = "INSERT INTO chefs (name, speciality) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, speciality);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Chef added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateChef(Scanner scanner, Connection con) {
        try {
            System.out.print("Enter Chef ID to Update: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter New Speciality: ");
            String speciality = scanner.nextLine();

            String query = "UPDATE chefs SET name = ?, speciality = ? WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, speciality);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Chef updated successfully!");
            } else {
                System.out.println("Chef not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteChef(Scanner scanner, Connection con) {
        try {
            System.out.print("Enter Chef ID to Delete: ");
            int id = scanner.nextInt();

            String query = "DELETE FROM chefs WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Chef deleted successfully!");
            } else {
                System.out.println("Chef not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void searchChef(Scanner scanner, Connection con) {
        try {
            System.out.print("Enter Chef Name to Search: ");
            String name = scanner.nextLine();

            String query = "SELECT * FROM chefs WHERE name LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Search Results ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Speciality: " + rs.getString("speciality"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewAllChefs(Connection con) {
        try {
            String query = "SELECT * FROM chefs";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Chef Details ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Speciality: " + rs.getString("speciality"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
