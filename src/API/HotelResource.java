package API;

import Model.Customer;
import Model.IRoom;
import Model.Reservation;
import Model.RoomType;
import Service.CustomerService;
import Service.ReservationService;

import java.util.*;

public class HotelResource {
    // Create Customer
    public void createCustomer(String firstName , String lastName , String email) {
        Customer customer = new Customer(firstName, lastName, email);

        if (customer.emailPattern.matcher(email).matches()) {
            if (CustomerService.getInstance().getCustomer(email) == null) {
                CustomerService.getInstance().addCustomer(firstName, lastName, email);
                System.out.println("Customer creating successful!\n");
            } else {
                System.out.println("Customer creating unsuccessful! Email is used by other user!\nPlease use another email.\n");
            }


        } else {
            System.out.println("Customer creating unsuccessful! Email is invalid format.\nEmail Example: username@email.com\n");
        }


    }

    // Get Customer
    public Customer getCustomer(String email) {
        return CustomerService.getInstance().getCustomer(email);
    }

    // Get Room
    public IRoom getRoom(String roomNumber) {
        return ReservationService.getInstance().getRoom(roomNumber);
    }

    // Book Room
    public void bookRoom(Map<String , IRoom> roomMap, String roomNumber , Customer customer , Date checkInDate , Date checkOutDate) {

        if (roomMap.get(roomNumber) == null) {
            System.out.println("Room booking is unsuccessful! Room number is not in the available list.\n");
        } else {
            Reservation reservation = new Reservation(customer , roomMap.get(roomNumber) , checkInDate , checkOutDate);
            ReservationService.getInstance().reserveRoom(reservation);
        }
    }

    // Get Customer Reservation
    public void getCustomerReservation(String email) {
        Customer customer = getCustomer(email);

        if (customer == null) {
            System.out.println("Customer is not found! Please check if the email is entered wrongly.\n");
        } else {
            ArrayList<Reservation> reservationArrayList = ReservationService.getInstance().getCustomerReservation(customer);

            System.out.printf(
                    "==== Reservation List ====\nCustomer: %s %s (%s)\nTotal Reservation: %d\n\n"
                    , customer.getFirstName()
                    , customer.getLastName()
                    , customer.getEmail()
                    , reservationArrayList.size()
            );


            int i = 1;
            for (Reservation reservation : reservationArrayList) {
                String roomNumber = reservation.getRoom().getRoomNumber();
                Double roomPrice = reservation.getRoom().getRoomPrice();
                RoomType roomType = reservation.getRoom().getRoomType();

                System.out.println(
                        "-- Reservation " + i + "--"
                                + "\nRoom [ " + roomNumber + ", " + roomPrice + ", " + roomType + " ]"
                                + "\nFrom: " + reservation.getCheckInDate()
                                + "\nTo: " + reservation.getCheckOutDate()
                                + "\n"
                );
                i++;
            }

            System.out.println("========\n");
        }
    }

    // Find Available Room
    public Map<String , IRoom> getAvailableRoomMap(Date checkInDate , Date checkOutDate) {
        Map<String , IRoom> availableRoomMap = new HashMap<>();
        ArrayList<IRoom> allRooms = ReservationService.getInstance().getAllRooms();

        for (IRoom room : allRooms) {
            availableRoomMap.put(room.getRoomNumber() , room);
        }

        ArrayList<IRoom> reservedRooms = new ArrayList<>();
        for (Reservation reservation : ReservationService.getInstance().getAllReservation()) {
            int situationCode = dateComparisonMethod(checkInDate , checkOutDate , reservation.getCheckInDate() , reservation.getCheckOutDate());
            if (situationCode == 1) {
                reservedRooms.add(reservation.getRoom());
            }
        }

        for (IRoom room : reservedRooms) {
            availableRoomMap.remove(room.getRoomNumber());
        }

        return availableRoomMap;
    }

    // Method - Compare Date
    public int dateComparisonMethod(Date searchingCheckInDate , Date searchingCheckOutDate , Date reservationCheckInDate , Date reservationCheckOutDate) {
        if ((searchingCheckInDate.before(reservationCheckInDate) && searchingCheckOutDate.after(reservationCheckOutDate)) || (searchingCheckInDate.after(reservationCheckInDate) && searchingCheckOutDate.before(reservationCheckOutDate))) {
            return 1;
        } else if (searchingCheckInDate.equals(reservationCheckInDate) || searchingCheckInDate.equals(reservationCheckOutDate) || searchingCheckOutDate.equals(reservationCheckInDate) || searchingCheckOutDate.equals(reservationCheckOutDate)) {
            return 1;
        } else if ((searchingCheckInDate.after(reservationCheckInDate) && searchingCheckInDate.before(reservationCheckOutDate)) || (searchingCheckOutDate.after(reservationCheckInDate) && searchingCheckOutDate.before(reservationCheckOutDate))) {
            return 1;
        } else {
            return 0;
        }
    }

    // Static Reference
    private static final HotelResource instance;
    private HotelResource() {}
    static {instance = new HotelResource();}
    public static HotelResource getInstance() {
        return instance;
    }
}
