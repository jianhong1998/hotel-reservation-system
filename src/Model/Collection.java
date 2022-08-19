package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Collection {
    // Customer
    public ArrayList<Customer> customerArrayList = new ArrayList<>();
    public Map<String , Customer> customerMap = new HashMap<>();

    // IRoom
    public ArrayList<IRoom> roomArrayList = new ArrayList<>();
    public Map<String , IRoom> roomMap = new HashMap<>();

    // Reservation
    public ArrayList<Reservation> reservationArrayList = new ArrayList<>();
    public Map<Integer , Reservation> reservationMap = new HashMap<>();

    // Static Reference
    private static final Collection instance;
    private Collection() {}
    static {instance = new Collection();}
    public static Collection getInstance() {
        return instance;
    }

}
