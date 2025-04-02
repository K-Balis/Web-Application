package barber_shop_application.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import barber_shop_application.entities.Customer;
import barber_shop_application.repository.CustomerRepository;
import barber_shop_application.service.CustomerServiceImplemetation;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImplemetation customerServiceImplemetation;

    @Test
    public void testSaveCustomer() {
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer savedCustomer = customerServiceImplemetation.saveCustomer(customer);

        Assertions.assertThat(savedCustomer).isNotNull();
        Assertions.assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(customer));
        Customer foundCustomer = customerServiceImplemetation.getCustomerById(1);

        Assertions.assertThat(foundCustomer).isNotNull();
        Assertions.assertThat(foundCustomer).isEqualTo(customer);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer1")
                                    .username("testcustomer1")
                                    .password("password")
                                    .email("testcustomer1@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        Customer customer2 = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer2")
                                    .username("testcustomer2")
                                    .password("password")
                                    .email("testcustomer2@email")
                                    .phone("987654321")
                                    .role("user")
                                    .build();
        List<Customer> customers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> allCustomers = customerServiceImplemetation.getAllCustomers();

        Assertions.assertThat(allCustomers).isNotNull();
        Assertions.assertThat(allCustomers).hasSize(2);
    }

    @Test
    public void testUpdateCustomer(){
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        Customer customerToUpdate = Customer.builder()
                                    .firstname("Test_updated")
                                    .lastname("Customer_updated")
                                    .username("testcustomer_updated")
                                    .password("password")
                                    .email("testcustomer_updated@email")
                                    .phone("987654321")
                                    .role("user")
                                    .build();
        when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(customer));
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer updatedCustomer = customerServiceImplemetation.updateCustomer(1, customerToUpdate);

        Assertions.assertThat(updatedCustomer).isNotNull();
        Assertions.assertThat(updatedCustomer.getFirstname()).isEqualTo(customerToUpdate.getFirstname());
        Assertions.assertThat(updatedCustomer.getLastname()).isEqualTo(customerToUpdate.getLastname());
        Assertions.assertThat(updatedCustomer.getUsername()).isEqualTo(customerToUpdate.getUsername());
        Assertions.assertThat(updatedCustomer.getPassword()).isEqualTo(customerToUpdate.getPassword());
        Assertions.assertThat(updatedCustomer.getEmail()).isEqualTo(customerToUpdate.getEmail());
        Assertions.assertThat(updatedCustomer.getPhone()).isEqualTo(customerToUpdate.getPhone());
        Assertions.assertThat(updatedCustomer.getRole()).isEqualTo(customerToUpdate.getRole());
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(customer));

        customerServiceImplemetation.deleteCustomer(1);
        verify(customerRepository).deleteById(1);
    }
}
