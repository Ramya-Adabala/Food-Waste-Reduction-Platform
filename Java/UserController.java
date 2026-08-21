import java.util.Scanner;

public class UserController {
    private UserService userService;
    private Scanner scanner;
    private User loggedInUser;

    public UserController() {
        this.userService = new UserService();
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Food Waste Reduction Platform ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            String input = scanner.nextLine();
            if (input.equals("1")) register();
            else if (input.equals("2")) {
                login();
                if (loggedInUser != null) showUserDashboard();
            }
            else if (input.equals("3")) {
                System.out.println("Exiting system. Goodbye!");
                break;
            }
        }
    }

    private void register() {
        System.out.println("\n--- User Registration ---");
        System.out.print("Username: "); String username = scanner.nextLine();
        System.out.print("Password: "); String password = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Phone: "); String phone = scanner.nextLine();
        System.out.print("Role (ADMIN, RESTAURANT, NGO, VOLUNTEER): "); String role = scanner.nextLine().toUpperCase();
        System.out.print("Address: "); String address = scanner.nextLine();

        if (userService.register(username, password, email, phone, role, address)) {
            System.out.println("Registration Successful! You can now login.");
        } else {
            System.out.println("Registration Failed. Email/username may exist.");
        }
    }

    private void login() {
        System.out.println("\n--- User Login ---");
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Password: "); String password = scanner.nextLine();

        loggedInUser = userService.login(email, password);
        
        if (loggedInUser != null) {
            System.out.println("Login Successful! Welcome, " + loggedInUser.getUsername());
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void showUserDashboard() {
        while (loggedInUser != null) {
            System.out.println("\n--- Dashboard (" + loggedInUser.getRole() + ") ---");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            
            boolean isRestaurant = loggedInUser.getRole().equalsIgnoreCase("RESTAURANT") || loggedInUser.getRole().equals("2");
            if (isRestaurant) {
                System.out.println("3. Manage Food Donations");
            }
            
            System.out.println("8. Delete Account");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("Name: " + loggedInUser.getUsername() + " | Phone: " + loggedInUser.getPhone());
            } else if (choice.equals("2")) {
                System.out.print("New Username: "); String name = scanner.nextLine();
                System.out.print("New Phone: "); String phone = scanner.nextLine();
                System.out.print("New Address: "); String address = scanner.nextLine();
                if (userService.updateProfile(loggedInUser.getUserId(), name, phone, address)) {
                    System.out.println("Profile updated!");
                    loggedInUser = userService.getUserDetails(loggedInUser.getUserId());
                }
            } else if (isRestaurant && choice.equals("3")) {
                // Pass control to the RestaurantController!
                RestaurantController restController = new RestaurantController(loggedInUser, scanner);
                restController.showMenu();
            } else if (choice.equals("8")) {
                if (userService.deleteUser(loggedInUser.getUserId())) {
                    System.out.println("Account deleted.");
                    loggedInUser = null;
                }
            } else if (choice.equals("9")) {
                loggedInUser = null;
            }
        }
    }
}