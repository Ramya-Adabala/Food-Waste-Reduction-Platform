import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting Food Waste Reduction Platform...");
        Connection conn = DatabaseConnection.getConnection();

        if (conn != null) {
            System.out.println("Project Setup is Complete!");

            // Start the application UI
            UserController controller = new UserController();
            controller.showMainMenu();
        } else {
            System.out.println("Project Setup Failed. Please check DatabaseConnection.java");
        }
    }
}

// javac *.java

// java -cp ".;mysql-connector-j.jar" MainApp