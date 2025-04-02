package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import barber_shop_application.entities.Employee;
import barber_shop_application.exceptions.ResourseNotFoundException;
import barber_shop_application.repository.EmployeeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImplemetation implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employeeToSave){
        Employee savedEmployee = employeeRepository.save(employeeToSave);
        return savedEmployee;
    }

    @Override
    public Employee getEmployeeById(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Override
    public List<Employee> getAvailableEmployees(LocalDate appointmentDate, LocalTime appointmentStartTime) {
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
        List<Employee> availableEmployees = employeeRepository.getAvailableEmployees(appointmentDate, appointmentStartTime, appointmentEndTime);

        return availableEmployees;
    }

    @Override
    public Employee updateEmployee(Integer employeeId, Employee employeeToUpdate) {
        Employee employeeObj = employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        
        employeeObj.setFirstname(employeeToUpdate.getFirstname());
        employeeObj.setLastname(employeeToUpdate.getLastname());
        employeeObj.setUsername(employeeToUpdate.getUsername());
        employeeObj.setPassword(employeeToUpdate.getPassword());
        employeeObj.setEmail(employeeToUpdate.getEmail());
        employeeObj.setPhone(employeeToUpdate.getPhone());
        employeeObj.setRole(employeeToUpdate.getRole());
        employeeObj.setBio(employeeToUpdate.getBio());

        Employee updatedEmployee = employeeRepository.save(employeeObj);

        return updatedEmployee;
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        employeeRepository.deleteById(employeeId);
    }
}