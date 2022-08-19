package Data;

import API.AdminResource;
import API.HotelResource;
import Model.Customer;
import Model.IRoom;
import Model.Reservation;

import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        DataWriting.main(null);

        ArrayList<String[]> dataArrayList = FileFunction.getInstance().fileReading("Files/Reservation.txt");

        for (String[] dataArray : dataArrayList) {
            String email = dataArray[0];
            String roomNumber = dataArray[1];
            Long checkInDateInLong = Long.parseLong(dataArray[2]);
            Long checkOutDateInLong = Long.parseLong(dataArray[3]);

            Customer customer = AdminResource.getInstance().getCustomer(email);
            System.out.println("Customer: " + customer.getFirstName() + ", " + customer.getLastName() + ", " + customer.getEmail() + "\n");

            IRoom room = HotelResource.getInstance().getRoom(roomNumber);
            System.out.println("Room: " + room.getRoomNumber() + ", " + room.getRoomPrice() + ", " + room.getRoomType() + "\n");

            System.out.println("Check In Date: " + checkInDateInLong + "\n");
            System.out.println("Check Out Date: " + checkOutDateInLong + "\n");
        }

    }
}
