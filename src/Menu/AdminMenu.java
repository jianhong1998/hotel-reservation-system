package Menu;

import API.AdminResource;
import TestDataSet.TestData;

import java.util.Scanner;

public class AdminMenu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            AdminMenu.getInstance().displayAdminMenu();
            int adminMenuOption = input.nextInt();

            try {
                switch (adminMenuOption) {
                    // See all customers
                    case 1 -> {
                        AdminMenu.getInstance().displayAllCustomer();
                    }

                    // See all rooms
                    case 2 -> {
                        AdminMenu.getInstance().displayAllRooms();
                    }

                    // See all reservation
                    case 3 -> {
                        AdminMenu.getInstance().displayAllReservation();
                    }

                    // Add room
                    case 4 -> {
                        System.out.println("How many rooms you want to add:");
                        try {
                            int numberOfRooms = input.nextInt();

                            for (int i = 0; i < numberOfRooms; i++) {
                                System.out.printf("Room %d:\n", i + 1);
                                System.out.println("Room Number:");
                                String roomNumber = input.next();
                                System.out.println("Room Price:");
                                Double roomPrice = input.nextDouble();
                                System.out.println("Room Type (Single / Double):");
                                String roomTypeInString = input.next();
                                AdminMenu.getInstance().addRoom(roomNumber, roomPrice, roomTypeInString);
                            }
                        } catch (Exception e) {
                            System.err.println("ERROR: Invalid input.\nPlease enter integer number.\n");
                        }

                    }

                    // Insert Test Data
                    case 5 -> {
                        AdminMenu.getInstance().addTestData();
                    }

                    // Back to Main Menu
                    case 6 -> {
                        backToMainMenu = true;
                    }

                    default -> {
                        System.err.println("ERROR: Invalid input.\nPlease enter integer number (1 to 6).\n");
                    }
                }
            } catch (Exception e) {
                System.err.println("ERROR: Invalid input.\nPlease enter integer number.\n");
            }
        }
    }

    // Static Reference
    private static final AdminMenu instance;
    private AdminMenu() {}
    static {instance = new AdminMenu();}
    public static AdminMenu getInstance() {
        return instance;
    }

    // Display Admin Menu
    public void displayAdminMenu() {
        System.out.println("\n<==== Admin Menu ====>");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add room");
        System.out.println("5. Insert test data");
        System.out.println("6. Back to Main Menu");
        System.out.println("Please select a number for the menu option: ");
    }

    // 1. See all customers
    public void displayAllCustomer() {
        AdminResource.getInstance().displayAllCustomers();
    }

    // 2. See all rooms
    public void displayAllRooms() {
        AdminResource.getInstance().displayAllRooms();
    }

    // 3. See all reservation
    public void displayAllReservation() {
        AdminResource.getInstance().displayAllReservation();
    }

    // 4. Add room
    public void addRoom(String roomNumber , Double roomPrice , String roomTypeInString) {
        AdminResource.getInstance().addRoom(roomNumber, roomPrice, roomTypeInString);
    }

    // 5. Insert test data
    public void addTestData() {
        TestData.main(null);
    }

}
