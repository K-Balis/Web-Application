package barber_shop_application.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import barber_shop_application.entities.Employee;
import barber_shop_application.service.EmployeeService;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {
	
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee){
		Employee savedEmployee = employeeService.saveEmployee(newEmployee);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer employeeId){
		Employee employee = employeeService.getEmployeeById(employeeId);
		return ResponseEntity.ok(employee);
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/availableEmployees")
	public ResponseEntity<List<Employee>> getAvailableEmployees(@RequestParam("date") LocalDate appointmentDate, @RequestParam("time") LocalTime appointmentTime) {
		List<Employee> availableEmployees = employeeService.getAvailableEmployees(appointmentDate, appointmentTime);
		return ResponseEntity.ok(availableEmployees);
	}

	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Integer employeeId, @RequestBody Employee employeeToUpdate){
		Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeToUpdate);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Integer employeeId){
		employeeService.deleteEmployee(employeeId);
		return ResponseEntity.ok("Employee deleted successfully!");
	}
}
