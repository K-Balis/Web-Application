package barber_shop_application.service;

import java.util.List;

import barber_shop_application.entities.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customerToSave);

    Customer getCustomerById(Integer customerId);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Integer customerId, Customer customerToUpdate);

    void deleteCustomer(Integer customerId);
}
