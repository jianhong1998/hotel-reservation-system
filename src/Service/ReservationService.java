package Service;

import Model.*;

import java.util.ArrayList;
import java.util.Date;

public class ReservationService {
    // Add Room
    public void addRoom(IRoom room) {
        Collection.getInstance().roomArrayList.add(room);
        Collection.getInstance().roomMap.put(room.getRoomNumber() , room);
    }

    // Get Room
    public IRoom getRoom(String roomNumber) {
        return Collection.getInstance().roomMap.get(roomNumber);
    }

    // Get All Rooms
    public ArrayList<IRoom> getAllRooms() {
        return Collection.getInstance().roomArrayList;
    }

    // Reserve Room
    public void reserveRoom(Reservation reservation) {
        Collection.getInstance().reservationArrayList.add(reservation);
        Collection.getInstance().reservationMap.put(reservation.hashCode() , reservation);

        String firstName = reservation.getCustomer().getFirstName();
        String lastName = reservation.getCustomer().getLastName();
        String email = reservation.getCustomer().getEmail();

        String roomNumber = reservation.getRoom().getRoomNumber();
        Double roomPrice = reservation.getRoom().getRoomPrice();
        RoomType roomType = reservation.getRoom().getRoomType();

        Date checkInDate = reservation.getCheckInDate();
        Date checkOutDate = reservation.getCheckOutDate();

        System.out.println(
                "Room booking successful!\n" +
                "---- Reservation" + " ----\nCustomer: " + firstName + " " + lastName + " (" + email + ")" +
                "\nRoom: " + roomNumber + ", " + "$" + roomPrice + ", " + roomType  +
                "\nFrom: " + checkInDate + "\nTo: " + checkOutDate + "\n"
        );
    }

    // Find Room
    public IRoom findRoom(String roomNumber) {
        return Collection.getInstance().roomMap.get(roomNumber);
    }

    // Get Customer Reservation
    public ArrayList<Reservation> getCustomerReservation(Customer customer) {
        ArrayList<Reservation> reservationArrayList = new ArrayList<>();
        for (Reservation reservation : Collection.getInstance().reservationArrayList) {
            if (reservation.getCustomer().equals(customer)) {
                reservationArrayList.add(reservation);
            }
        }
        return reservationArrayList;
    }

    public ArrayList<Reservation> getAllReservation() {
        return Collection.getInstance().reservationArrayList;
    }

    // Print All Reservation
    public void printAllReservation() {
        ArrayList<Reservation> reservationArrayList = Collection.getInstance().reservationArrayList;
        System.out.println("==== All Reservation =====");
        System.out.printf("Total Reservation: %d\n\n" , reservationArrayList.size());

        int i = 1;
        for (Reservation reservation : reservationArrayList) {
            Customer customer = reservation.getCustomer();
            IRoom room = reservation.getRoom();
            Date checkInDate = reservation.getCheckInDate();
            Date checkOutDate = reservation.getCheckOutDate();

            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();
            String email = customer.getEmail();

            String roomNumber = room.getRoomNumber();
            Double roomPrice = room.getRoomPrice();
            RoomType roomType = room.getRoomType();
            Boolean isFree = room.isFree();

            System.out.println(
                    "---- Reservation" + i + " ----\nCustomer: " + firstName + " " + lastName + " " + email +
                            "\nRoom: " + roomNumber + ", " + "$" + roomPrice + ", " + roomType + ", " + isFree +
                            "\nFrom: " + checkInDate + "\nTo: " + checkOutDate + "\n"
            );
            i++;
        }
        System.out.println("========\n");
    }

    // Static Reference
    private static ReservationService instance;
    private ReservationService() {}
    static {instance = new ReservationService();}
    public static ReservationService getInstance() {
        return instance;
    }
}
