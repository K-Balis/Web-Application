package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import barber_shop_application.entities.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employeeToSave);

    Employee getEmployeeById(Integer employeeId);

    List<Employee> getAllEmployees();

    List<Employee> getAvailableEmployees(LocalDate appointmentDate, LocalTime appointmentTime);

    Employee updateEmployee(Integer employeeId, Employee employeeToUpdate);

    void deleteEmployee(Integer employeeId);
}
