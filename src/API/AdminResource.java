package API;

import Model.*;
import Service.CustomerService;
import Service.ReservationService;

import java.util.ArrayList;

public class AdminResource {
    // Get Customer
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    // Add Room
    public void addRoom(String roomNumber , Double roomPrice , String roomTypeInString) {
        RoomType roomType;
        switch (roomTypeInString.toLowerCase()) {
            case "single":
                roomType = RoomType.SINGLE;
                break;
            case "double":
                roomType = RoomType.DOUBLE;
                break;
            default:
                roomType = null;
        }

        if (roomType == null) {
            System.out.println("Room adding unsuccessful! Room type must be Single or Double.\n");
        } else {
            if (roomPrice < 0) {
                System.out.println("Room adding unsuccessful! Price must be 0 or greater than 0.\n");
            } else {
                if (roomPrice == 0.0) {
                    IRoom room = new FreeRoom(roomNumber , roomType);
                } else {
                    IRoom room = new Room(roomNumber , roomPrice , roomType);
                    if (ReservationService.getInstance().getRoom(room.getRoomNumber()) == null) {
                        ReservationService.getInstance().addRoom(room);
                        System.out.println("Room adding successful!");
                        System.out.printf("Room[%s , %,.2f , %s]\n\n" , roomNumber , roomPrice , roomType);
                    } else {
                        System.out.printf("Room adding unsuccessful! Room %s already exist.\n\n" , roomNumber);
                    }
                }
            }
        }


    }

    // Get All Rooms
    public void displayAllRooms() {
        ArrayList<IRoom> roomArrayList = ReservationService.getInstance().getAllRooms();

        System.out.println("==== All Rooms ====\n" + "Total Rooms: " + roomArrayList.size() + "\n");

        int i = 1;
        for (IRoom room : roomArrayList) {
            String roomNumber = room.getRoomNumber();
            Double roomPrice = room.getRoomPrice();
            RoomType roomType = room.getRoomType();

            System.out.printf("Room %d [%s , %,.2f , %s]\n" , i , roomNumber , roomPrice , roomType);
            i++;
        }
        System.out.println("========\n");
    }

    // Get All Customers
    public void displayAllCustomers() {
        ArrayList<Customer> customerArrayList = CustomerService.getInstance().getAllCustomers();
        System.out.println("==== All Customers ====\n" + "Total customer: " + customerArrayList.size() + "\n");

        int i = 1;
        for (Customer customer : customerArrayList) {
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            String email = customer.getEmail();

            System.out.printf("Customer %d [%s %s (%s)]\n" , i , firstName , lastName , email);
            i++;
        }
        System.out.println("========\n");
    }

    // Display All Reservation
    public void displayAllReservation() {
        ReservationService.getInstance().printAllReservation();
    }


    // Static Reference
    private static final AdminResource instance;
    private AdminResource() {}
    static {instance = new AdminResource();}
    public static AdminResource getInstance() {
        return instance;
    }
}
