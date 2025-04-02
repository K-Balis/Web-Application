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
public class AppointmentRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void findByDate(){

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

        LocalDate date = LocalDate.of(2025, 1, 1);
        
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        Appointment validAppointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(validAppointment);

        Appointment invalidAppointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(LocalDate.of(2025, 1, 2))
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(invalidAppointment);

        List<Appointment> appointments= appointmentRepository.findByDate(date);

        Assertions.assertThat(appointments).isNotNull();
        Assertions.assertThat(appointments).hasSize(1);
        Assertions.assertThat(appointments.get(0).getDate()).isEqualTo(date);
    }

    @Test
    public void testFindByDateBetween(){
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
        
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(LocalDate.of(2025, 1, 1))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        EmployeeSchedule schedule2 = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(LocalDate.of(2025, 1, 3))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule2);

        EmployeeSchedule schedule3 = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(LocalDate.of(2025, 1, 5))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule3);

        Appointment validAppointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(LocalDate.of(2025, 1, 1))
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(validAppointment);

        Appointment validAppointment2 = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(LocalDate.of(2025, 1, 3))
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(validAppointment2);

        Appointment invalidAppointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(LocalDate.of(2025, 1, 5))
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(invalidAppointment);

        LocalDate start_date = LocalDate.of(2025, 1, 1);
        LocalDate end_date = LocalDate.of(2025, 1, 4);
        List<Appointment> appointments = appointmentRepository.findByDateBetween(start_date, end_date);

        Assertions.assertThat(appointments).isNotNull();
        Assertions.assertThat(appointments).hasSize(2);
        Assertions.assertThat(appointments.get(0).getDate()).isBetween(start_date, end_date);
        Assertions.assertThat(appointments.get(1).getDate()).isBetween(start_date, end_date);
    }

    @Test
    public void testFindByEmployeeId(){

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

        LocalDate date = LocalDate.of(2025, 1, 1);

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        Appointment appointment = Appointment.builder()
                                                    .employee(employee)
                                                    .customer(customer)
                                                    .employeeSchedule(schedule)
                                                    .date(date)
                                                    .appointmentStart(LocalTime.of(9, 0))
                                                    .appointmentEnd(LocalTime.of(9, 30))
                                                    .build();
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findByEmployeeId(employee.getId());

        Assertions.assertThat(appointments).isNotNull();
        Assertions.assertThat(appointments).hasSize(1);
        Assertions.assertThat(appointments.get(0).getEmployee().getId()).isEqualTo(employee.getId());
    }

    @Test
    public void testFindByCustomerId(){
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

        LocalDate date = LocalDate.of(2025, 1, 1);

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(LocalTime.of(9, 0))
                                                .appointmentEnd(LocalTime.of(9, 30))
                                                .build();
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findByCustomerId(customer.getId());

        Assertions.assertThat(appointments).isNotNull();
        Assertions.assertThat(appointments).hasSize(1);
        Assertions.assertThat(appointments.get(0).getCustomer().getId()).isEqualTo(customer.getId());

    }
}
