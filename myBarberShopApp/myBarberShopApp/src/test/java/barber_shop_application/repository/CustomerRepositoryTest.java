package barber_shop_application.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import barber_shop_application.entities.Customer;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByUsername(){
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("test_customer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("User")
                                    .build();
        
        customerRepository.save(customer);
        Customer foundCustomer = customerRepository.findByUsername("test_customer");

        Assertions.assertThat(foundCustomer).isNotNull();
        Assertions.assertThat(foundCustomer.getUsername()).isEqualTo("test_customer");
    }
}
