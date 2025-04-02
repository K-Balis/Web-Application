package barber_shop_application.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import barber_shop_application.entities.Appointment;
import barber_shop_application.entities.Customer;
import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void testGetAvailableEmployees(){
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(11, 0);
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

        employeeRepository.save(employee1);

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

        employeeRepository.save(employee2);

        EmployeeSchedule schedule1 = EmployeeSchedule.builder()
                                                        .employee(employee1)
                                                        .date(date)
                                                        .scheduleStartTime(LocalTime.of(8, 0))
                                                        .scheduleEndTime(LocalTime.of(13, 0))
                                                        .build();
        employeeScheduleRepository.save(schedule1);

        EmployeeSchedule schedule2 = EmployeeSchedule.builder()
                                                        .employee(employee2)
                                                        .date(date)
                                                        .scheduleStartTime(LocalTime.of(8, 0))
                                                        .scheduleEndTime(LocalTime.of(13, 0))
                                                        .build();
        employeeScheduleRepository.save(schedule2);

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

        Appointment appointment = Appointment.builder()
                                                .employee(employee1)
                                                .customer(customer)
                                                .employeeSchedule(schedule1)
                                                .date(date)
                                                .appointmentStart(startTime)
                                                .appointmentEnd(endTime)
                                                .build();
        appointmentRepository.save(appointment);
        
        List<Employee> availableEmployees = employeeRepository.getAvailableEmployees(date, startTime, endTime);

        Assertions.assertThat(availableEmployees).isNotNull();
        Assertions.assertThat(availableEmployees).hasSize(1);
        Assertions.assertThat(availableEmployees.get(0).getId()).isEqualTo(employee2.getId());
    }

    @Test
    public void testFindByUsername(){
        Employee employee = Employee.builder()
                                    .firstname("Test")
                                    .lastname("Employee")
                                    .username("test_employee")
                                    .password("password")
                                    .email("testemployee@email")
                                    .phone("123456789")
                                    .role("Employee")
                                    .bio("Test Employee's bio")
                                    .build();
        
        employeeRepository.save(employee);
        Employee foundEmployee = employeeRepository.findByUsername("test_employee");

        Assertions.assertThat(foundEmployee).isNotNull();
        Assertions.assertThat(foundEmployee.getUsername()).isEqualTo("test_employee");
    }
}
