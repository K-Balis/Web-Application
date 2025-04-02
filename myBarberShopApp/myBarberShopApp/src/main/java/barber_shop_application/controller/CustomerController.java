package barber_shop_application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import barber_shop_application.entities.Customer;
import barber_shop_application.service.CustomerService;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
	
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer){
		Customer savedCustomer = customerService.saveCustomer(newCustomer);
		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer customerId){
		Customer customer = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(customer);
	}

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok(customers);
	}

	@PutMapping("{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer customerId, @RequestBody Customer customerToUpdate){
		Customer updatedCustomer = customerService.updateCustomer(customerId, customerToUpdate);
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Integer customerId){
		customerService.deleteCustomer(customerId);
		return ResponseEntity.ok("Customer deleted successfully!");
	}
}
