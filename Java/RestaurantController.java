import java.util.List;
import java.util.Scanner;

public class RestaurantController {
    private DonationService donationService;
    private Scanner scanner;
    private User loggedInRestaurant;

    public RestaurantController(User loggedInRestaurant, Scanner scanner) {
        this.donationService = new DonationService();
        this.scanner = scanner;
        this.loggedInRestaurant = loggedInRestaurant;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Restaurant Management Menu ---");
            System.out.println("1. Add Food Donation");
            System.out.println("2. View My Donations");
            System.out.println("3. Edit a Donation");
            System.out.println("4. Delete a Donation");
            System.out.println("5. Back to Main Dashboard");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            if (choice.equals("1")) addDonation();
            else if (choice.equals("2")) viewDonations();
            else if (choice.equals("3")) editDonation();
            else if (choice.equals("4")) deleteDonation();
            else if (choice.equals("5")) break;
        }
    }

    private void addDonation() {
        System.out.print("Food Item Name (e.g., Lasagna): ");
        String foodItem = scanner.nextLine();
        System.out.print("Quantity (Number): ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Unit (e.g., Portions, KG): ");
        String unit = scanner.nextLine();
        System.out.print("Hours until it expires: ");
        int hours = Integer.parseInt(scanner.nextLine());

        if (donationService.addDonation(loggedInRestaurant.getUserId(), foodItem, quantity, unit, hours)) {
            System.out.println("Food Donation added successfully! NGOs can now see it.");
        } else {
            System.out.println("Failed to add donation. Check your inputs.");
        }
    }

    private void viewDonations() {
        System.out.println("\n--- My Donations ---");
        List<Donation> list = donationService.getDonationsByRestaurant(loggedInRestaurant.getUserId());
        if (list.isEmpty()) {
            System.out.println("You have no donations yet.");
            return;
        }
        for (Donation d : list) {
            System.out.println("ID: " + d.getDonationId() + " | Item: " + d.getFoodItem() + 
                               " | Qty: " + d.getQuantity() + " " + d.getUnit() + 
                               " | Status: " + d.getStatus());
        }
    }

    private void editDonation() {
        viewDonations();
        System.out.print("Enter the ID of the Donation to Edit: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Food Item Name: ");
        String foodItem = scanner.nextLine();
        System.out.print("New Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        System.out.print("New Unit: ");
        String unit = scanner.nextLine();

        if (donationService.updateDonation(id, loggedInRestaurant.getUserId(), foodItem, quantity, unit)) {
            System.out.println("Donation updated successfully!");
        } else {
            System.out.println("Failed to update. Make sure the ID belongs to you.");
        }
    }

    private void deleteDonation() {
        viewDonations();
        System.out.print("Enter the ID of the Donation to Delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        if (donationService.deleteDonation(id, loggedInRestaurant.getUserId())) {
            System.out.println("Donation deleted.");
        } else {
            System.out.println("Failed to delete.");
        }
    }
}
