package TestDataSet;

import API.AdminResource;
import API.HotelResource;

public class TestData {
    public static void main(String[] args) {
        // Insert Customer
        HotelResource.getInstance().createCustomer("James" , "Lee" , "james@gmail.com");
        HotelResource.getInstance().createCustomer("Joshua" , "Khoo" , "joshua_khoo@gmail.com");
        HotelResource.getInstance().createCustomer("Banjemin" , "White" , "ban.white@gmail.com");

        // Insert Rooms
        AdminResource.getInstance().addRoom("A0101" , 100.0 , "double");
        AdminResource.getInstance().addRoom("A0102" , 60.0 , "single");
        AdminResource.getInstance().addRoom("A0103" , 120.0 , "double");
    }
}
