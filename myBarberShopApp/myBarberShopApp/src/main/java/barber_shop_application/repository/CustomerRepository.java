package barber_shop_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import barber_shop_application.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{

    Customer findByUsername(String username);
}
