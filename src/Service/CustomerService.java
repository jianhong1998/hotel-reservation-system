package Service;

import Model.Collection;
import Model.Customer;

import java.util.ArrayList;

public class CustomerService {

    // Add Customer
    public void addCustomer(String firstName , String lastName , String email) {
        Customer customer = new Customer(firstName , lastName , email);
        Collection.getInstance().customerArrayList.add(customer);
        Collection.getInstance().customerMap.put(email , customer);
    }

    // Get Customer
    public Customer getCustomer(String email) {
        return Collection.getInstance().customerMap.get(email);
    }

    // Get All Customer
    public ArrayList<Customer> getAllCustomers() {
        return Collection.getInstance().customerArrayList;
    }

    // Static Reference
    private static final CustomerService instance;
    private CustomerService() {}
    static {instance = new CustomerService();}
    public static CustomerService getInstance() {
        return instance;
    }
}
