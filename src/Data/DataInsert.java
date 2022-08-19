package Data;

import API.AdminResource;
import API.HotelResource;
import Model.*;

import java.util.ArrayList;
import java.util.Date;

public class DataInsert {
    public static void main(String[] args) {
        insertCustomer("Files/Customer.txt");
        insertRoom("Files/IRoom.txt");
        insertReservation("Files/Reservation.txt");

        System.out.println("Data loaded successful!");
    }

    private static void insertCustomer(String fileName) {
        ArrayList<String[]> dataArrayList = FileFunction.getInstance().fileReading(fileName);

        int count =1;
        for (String[] stringArray : dataArrayList) {
            String firstName = stringArray[0];
            String lastName = stringArray[1];
            String email = stringArray[2];

            Customer customer = new Customer(firstName , lastName , email);

            if (customer.emailPattern.matcher(customer.getEmail()).matches() && AdminResource.getInstance().getCustomer(email) == null) {
                Collection.getInstance().customerArrayList.add(customer);
                Collection.getInstance().customerMap.put(email , customer);
            } else if (customer.emailPattern.matcher(customer.getEmail()).matches()) {
                System.out.printf("ERROR: Email is invalid in line %d!\nCustomer %s %s is added unsuccessful.\n\n" , count , fileName , lastName);
            }
        }

        count++;
    }

    private static void insertRoom(String fileName) {
        ArrayList<String[]> dataArrayList = FileFunction.getInstance().fileReading(fileName);

        int count = 1;
        for (String[] dataArray : dataArrayList) {
            String roomNumber = dataArray[0];
            Double roomPrice = Double.parseDouble(dataArray[1]);
            RoomType roomType = null;
            switch (dataArray[2].toLowerCase()) {
                case "single" -> {roomType = RoomType.SINGLE;}
                case "double" -> {roomType = RoomType.DOUBLE;}
                default -> {
                    System.out.printf("ERROR: Room Type for line %d is invalid.\n%s is added unsuccessful!\n\n" , count , roomNumber);
                }
            }

            IRoom room = null;
            if (roomPrice == 0 && roomType != null) {
                room = new FreeRoom(roomNumber , roomType);
            } else if (roomPrice > 0 && roomType != null) {
                room = new Room(roomNumber , roomPrice , roomType);
            } else {
                System.out.printf("ERROR: Room Price for line %d is invalid.\n%s is added unsuccessful!\n\n" , count , roomNumber);
            }

            if (room != null) {
                Collection.getInstance().roomArrayList.add(room);
                Collection.getInstance().roomMap.put(roomNumber , room);
            }

            count++;
        }

    }

    private static void insertReservation(String fileName) {
        ArrayList<String[]> dataArrayList = FileFunction.getInstance().fileReading(fileName);

        int count = 1;
        for (String[] dataArray : dataArrayList) {
            String email = dataArray[0];
            String roomNumber = dataArray[1];
            long checkInDateInLong = Long.parseLong(dataArray[2]);
            long checkOutDateInLong = Long.parseLong(dataArray[3]);

            Date checkInDate = new Date();
            checkInDate.setTime(checkInDateInLong);

            Date checkOutDate = new Date();
            checkOutDate.setTime(checkOutDateInLong);

            Reservation reservation = new Reservation(AdminResource.getInstance().getCustomer(email) , HotelResource.getInstance().getRoom(roomNumber), checkInDate , checkOutDate);

            if (reservation.getRoom() != null && reservation.getCustomer() != null && reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
                Collection.getInstance().reservationArrayList.add(reservation);
                Collection.getInstance().reservationMap.put(reservation.hashCode() , reservation);
            } else  {
                System.out.printf("ERROR: Invalid data in line %d. Reservation added unsuccessful!" , count);
            }

            count++;
        }
    }
}
