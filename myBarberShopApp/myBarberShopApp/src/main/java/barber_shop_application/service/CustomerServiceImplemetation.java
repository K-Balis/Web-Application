package barber_shop_application.service;

import java.util.List;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import barber_shop_application.entities.Customer;
import barber_shop_application.exceptions.ResourseNotFoundException;
import barber_shop_application.repository.CustomerRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImplemetation implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customerToSave){
        Customer savedCustomer = customerRepository.save(customerToSave);
        return savedCustomer;
    }

    @Override
    public Customer getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> new ResourseNotFoundException("There is no customer with id: " + customerId));
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer customerToUpdate) {
        Customer customerObj = customerRepository.findById(customerId)
                                .orElseThrow(() -> new ResourseNotFoundException("There is no customer with id: " + customerId));

        customerObj.setFirstname(customerToUpdate.getFirstname());
        customerObj.setLastname(customerToUpdate.getLastname());
        customerObj.setUsername(customerToUpdate.getUsername());
        customerObj.setPassword(customerToUpdate.getPassword());
        customerObj.setEmail(customerToUpdate.getEmail());
        customerObj.setPhone(customerToUpdate.getPhone());
        customerObj.setRole(customerToUpdate.getRole());

        Customer updatedCustomer = customerRepository.save(customerObj);

        return updatedCustomer;

    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customerRepository.findById(customerId)
                            .orElseThrow(() -> new ResourseNotFoundException("There is no customer with id: " + customerId));
        customerRepository.deleteById(customerId);
    }
}
