package Data;

import API.AdminResource;
import API.HotelResource;
import Model.Collection;
import Model.Customer;
import Model.IRoom;
import Model.Reservation;


import java.util.ArrayList;

public class DataWriting {
    public static void main(String[] args) {
        writeCustomerRecord();
        writeRoomRecord();
        writeReservationRecord();

        System.out.println("Files updated successful!");
    }

    public static void writeCustomerRecord() {
        ArrayList<String[]> dataArrayList = new ArrayList<>();

        for (Customer customer : Collection.getInstance().customerArrayList) {
            String[] dataArray = new String[3];

            dataArray[0] = customer.getFirstName();
            dataArray[1] = customer.getLastName();
            dataArray[2] = customer.getEmail();

            dataArrayList.add(dataArray);
        }

        FileFunction.getInstance().fileWriting(dataArrayList , "Files/Customer.txt");
    }

    public static void writeRoomRecord() {
        ArrayList<String[]> dataArrayList = new ArrayList<>();

        for (IRoom room : Collection.getInstance().roomArrayList) {
            String[] dataArray = new String[3];

            dataArray[0] = room.getRoomNumber();
            dataArray[1] = String.valueOf(room.getRoomPrice());
            dataArray[2] = String.valueOf(room.getRoomType());

            dataArrayList.add(dataArray);
        }

        FileFunction.getInstance().fileWriting(dataArrayList , "Files/IRoom.txt");
    }

    public static void writeReservationRecord() {
        ArrayList<String[]> dataArrayList = new ArrayList<>();

        for (Reservation reservation : Collection.getInstance().reservationArrayList) {
            String[] dataArray = new String[4];

            dataArray[0] = reservation.getCustomer().getEmail();
            dataArray[1] = reservation.getRoom().getRoomNumber();
            dataArray[2] = String.valueOf(reservation.getCheckInDate().getTime());
            dataArray[3] = String.valueOf(reservation.getCheckOutDate().getTime());

            dataArrayList.add(dataArray);
        }

        FileFunction.getInstance().fileWriting(dataArrayList , "Files/Reservation.txt");
    }
}
