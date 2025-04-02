package barber_shop_application.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import barber_shop_application.entities.Employee;
import barber_shop_application.repository.EmployeeRepository;
import barber_shop_application.service.EmployeeServiceImplemetation;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImplemetation employeeServiceImplemetation;

    @Test
    public void testSaveEmployee() {
        Employee employee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeServiceImplemetation.saveEmployee(employee);

        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee).isEqualTo(employee);
    }

    @Test
    public void testGetEmployeeById() {
        Employee employee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();

        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));

        Employee foundEmployee = employeeServiceImplemetation.getEmployeeById(1);

        Assertions.assertThat(foundEmployee).isNotNull();
        Assertions.assertThat(foundEmployee).isEqualTo(employee);
    }

    @Test
    public void testGetAllEmployees() {
        Employee employee1 = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();
        Employee employee2 = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee2")
                                        .username("test_employee2")
                                        .password("password")
                                        .email("testemployee2@email")
                                        .phone("987654321")
                                        .role("Employee")
                                        .bio("Test Employee 2's bio..")
                                        .build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> foundEmployees = employeeServiceImplemetation.getAllEmployees();

        Assertions.assertThat(foundEmployees).isNotNull();
        Assertions.assertThat(foundEmployees).hasSize(2);
        Assertions.assertThat(foundEmployees.get(0)).isEqualTo(employee1);
        Assertions.assertThat(foundEmployees.get(1)).isEqualTo(employee2);
    }

    @Test
    public void testGetAvailableEmployees() {

        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = startTime.plusMinutes(30);
        Employee employee1 = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();
        Employee employee2 = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee2")
                                        .username("test_employee2")
                                        .password("password")
                                        .email("testemployee2@email")
                                        .phone("987654321")
                                        .role("Employee")
                                        .bio("Test Employee 2's bio..")
                                        .build();
        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.getAvailableEmployees(date, startTime, endTime)).thenReturn(employees);

        List<Employee> availableEmployees = employeeServiceImplemetation.getAvailableEmployees(date, startTime);

        Assertions.assertThat(availableEmployees).isNotNull();
        Assertions.assertThat(availableEmployees).hasSize(2);
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();

        Employee employeeToUpdate = Employee.builder()
                                        .firstname("Test_updated")
                                        .lastname("Employee1_updated")
                                        .username("test_employee1_updated")
                                        .password("password")
                                        .email("testemployee1_updated@email")
                                        .phone("987654321")
                                        .role("Owner")
                                        .bio("Test Employee 1's updated bio..")
                                        .build();
                                        
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeServiceImplemetation.updateEmployee(1, employeeToUpdate);

        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getFirstname()).isEqualTo(employeeToUpdate.getFirstname());
        Assertions.assertThat(updatedEmployee.getLastname()).isEqualTo(employeeToUpdate.getLastname());
        Assertions.assertThat(updatedEmployee.getUsername()).isEqualTo(employeeToUpdate.getUsername());
        Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo(employeeToUpdate.getEmail());
        Assertions.assertThat(updatedEmployee.getPhone()).isEqualTo(employeeToUpdate.getPhone());
        Assertions.assertThat(updatedEmployee.getRole()).isEqualTo(employeeToUpdate.getRole());
        Assertions.assertThat(updatedEmployee.getBio()).isEqualTo(employeeToUpdate.getBio());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee1")
                                        .username("test_employee1")
                                        .password("password")
                                        .email("testemployee1@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee 1's bio..")
                                        .build();
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));

        employeeServiceImplemetation.deleteEmployee(1);
        verify(employeeRepository).deleteById(1);;
    }
}
