package Menu;

import API.AdminResource;
import API.HotelResource;
import Model.*;

import java.util.*;

public class MainMenu {

    // Static Reference
    private static final MainMenu instance;
    private MainMenu() {}
    static {instance = new MainMenu();}
    public static MainMenu getInstance() {
        return instance;
    }

    // Menu
    public void displayMainMenu() {
        System.out.println("\n<==== Main Menu ====>");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("Please select a number for the menu option: ");
    }

    // 1. Find and reserve a room
    public Map<String , IRoom> findAvailableRoom(Date checkInDate , Date checkOutDate) {
        return HotelResource.getInstance().getAvailableRoomMap(checkInDate , checkOutDate);
    }

    public void bookRoom(Map<String , IRoom> roomMap , String roomNumber , Customer customer , Date checkInDate , Date checkOutDate) {
        HotelResource.getInstance().bookRoom(roomMap , roomNumber , customer , checkInDate , checkOutDate);
    }

    // 2. See my reservation
    public void displayCustomerReservationList (Customer customer) {
        HotelResource.getInstance().getCustomerReservation(customer.getEmail());
    }

    //3. Create an account
    public void createCustomerAccount (String firstName , String lastName , String email) {
        HotelResource.getInstance().createCustomer(firstName, lastName, email);
    }


    // Method - Customer Log In
    public Customer getCustomer(String email) {
        return HotelResource.getInstance().getCustomer(email);
    }

    // Method - Date Plus
    public Date datePlusSeven(Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(calendar.DATE , 7);
        return calendar.getTime();
    }


}
