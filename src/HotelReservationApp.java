import Data.DataInsert;
import Data.DataWriting;
import Menu.AdminMenu;
import Menu.MainMenu;
import Model.Customer;
import Model.IRoom;
import Model.RoomType;

import java.util.*;

public class HotelReservationApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        DataInsert.main(null);

        boolean endProgram = false;

        while (!endProgram) {
                MainMenu.getInstance().displayMainMenu();
                int mainMenuOption = input.nextInt();

                switch (mainMenuOption) {
                    // 1. Find and reserve a room
                    case 1 -> {
                        // Log In
                        Customer customer = customerLogInFunction();
                        System.out.printf("\nLog in successful.\nWelcome, %s %s.\n\n" , customer.getFirstName() , customer.getLastName());

                        // GET checkInDate
                        System.out.println("Please enter check in date:");
                        Date checkInDate = getDateFromUser();

                        // GET checkOutDate
                        System.out.println("Please enter check out date:");
                        Date checkOutDate = getDateFromUser();

                        Map<String , IRoom> availableRoomMap = MainMenu.getInstance().findAvailableRoom(checkInDate , checkOutDate);

                        // If no room available
                        int datePlus = 0;
                        boolean stopSearching = false;
                        while (availableRoomMap.size() == 0 && !stopSearching) {
                            if (datePlus > 23) {
                                stopSearching = true;
                            } else {
                                datePlus = datePlus + 7;
                                Date checkInDate2 = datePlusMethod(checkInDate , datePlus);
                                Date checkOutDate2 = datePlusMethod(checkOutDate , datePlus);
                                availableRoomMap = MainMenu.getInstance().findAvailableRoom(checkInDate2 , checkOutDate2);
                            }
                        }

                        // DISPLAY availableRoomMap
                        System.out.println("==== Available Rooms ====");
                        if (availableRoomMap.size() > 0) {
                            if (datePlus != 0) {
                                System.out.printf("There is no room available on the period.\nBelow are the available rooms for %d days after the period.\n" , datePlus);
                            }

                            checkInDate = datePlusMethod(checkInDate , datePlus);
                            checkOutDate = datePlusMethod(checkOutDate , datePlus);

                            System.out.println(
                                "Total Rooms: " + availableRoomMap.size()
                                + "\nCheck In Date: " + checkInDate
                                + "\nCheck Out Date: " + checkOutDate
                                + "\n"
                            );
                            for (int i = 0 ; i < availableRoomMap.size() ; i++) {
                                Object key = availableRoomMap.keySet().toArray()[i];
                                IRoom room = availableRoomMap.get(key);
                                String roomNumber = room.getRoomNumber();
                                Double roomPrice = room.getRoomPrice();
                                RoomType roomType = room.getRoomType();

                                System.out.printf("Room %d: [%s, %,.2f, %s]\n" , i + 1 , roomNumber , roomPrice , roomType);
                            }
                        } else  {
                            System.out.printf("There is no room available on the period until %d days after.\nPlease choose another period.\n\n" , datePlus);
                        }

                        // Book Room
                        System.out.println("Do you want to book room? [ Y / N ]");
                        String bookRoom = input.next().toUpperCase();
                        if (bookRoom.equals("Y")) {
                            System.out.println("Room Number:");
                            String roomNumber = input.next();
                            System.out.println("");
                            MainMenu.getInstance().bookRoom(availableRoomMap , roomNumber , customer , checkInDate , checkOutDate);
                        }
                    }

                    // 2. See my reservations
                    case 2 -> {
                        Customer customer = customerLogInFunction();
                        MainMenu.getInstance().displayCustomerReservationList(customer);
                    }

                    // 3. Create an account
                    case 3 -> {
                        System.out.println("First Name:");
                        String firstName = input.next();
                        System.out.println("Last Name:");
                        String lastName = input.next();
                        System.out.println("Email:");
                        String email = input.next();

                        MainMenu.getInstance().createCustomerAccount(firstName , lastName , email);
                    }

                    // 4. Admin
                    case 4 -> {
                        AdminMenu.main(null);
                    }

                    // 5. Exit
                    case 5 -> {
                        System.out.println("\nThank you for using Hotel Reservation App!\nTake care and see you next time.\n");

                        input.close();

                        DataWriting.main(null);

                        endProgram = true;
                    }

                    default -> {
                        System.err.println("ERROR: Invalid input.\nPlease enter integer number (1 to 5).\n");
                    }
                }


        }
    }


    public static Date getDateFromUser() {
        Scanner inputInMethod = new Scanner(System.in);
        System.out.println("Year:");
        int year = inputInMethod.nextInt();
        System.out.println("Month:");
        int month = inputInMethod.nextInt() - 1;
        System.out.println("Date:");
        int date = inputInMethod.nextInt();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTime();
    }

    public static Date datePlusMethod(Date date , int datePlus) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, datePlus);
        return calendar.getTime();
    }

    public static Customer customerLogInFunction() {
        Scanner input = new Scanner(System.in);
        Customer customer = null;

        while (customer == null) {
            System.out.println("Do you have account [ Y / N ]:");
            String haveAccount = input.next().toUpperCase();
            switch (haveAccount) {
                case "Y" -> {
                    System.out.println("\nEnter your email to log in:");
                    String email = input.next();
                    customer = MainMenu.getInstance().getCustomer(email);
                }
                case "N" -> {
                    System.out.println("\nPlease register an account.\nFirst Name:");
                    String firstName = input.next();
                    System.out.println("LastName:");
                    String lastName = input.next();
                    System.out.println("Email:");
                    String email = input.next();
                    MainMenu.getInstance().createCustomerAccount(firstName , lastName , email);
                    customer = MainMenu.getInstance().getCustomer(email);
                    if (customer != null) {
                        if (!customer.getFirstName().equals(firstName) || !customer.getLastName().equals(lastName)) {
                            customer = null;
                        }
                    }
                }
                default -> {
                    System.out.println("ERROR: Invalid input!\nPlease enter Y for yes, N for no\n");
                }
            }
            if (customer == null) {
                System.out.println("Customer is not found!\n");
            }
        }
        return customer;
    }
}
