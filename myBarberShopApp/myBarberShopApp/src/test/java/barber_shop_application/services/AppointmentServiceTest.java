package barber_shop_application.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import barber_shop_application.entities.Appointment;
import barber_shop_application.entities.Customer;
import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.repository.AppointmentRepository;
import barber_shop_application.repository.CustomerRepository;
import barber_shop_application.repository.EmployeeRepository;
import barber_shop_application.repository.EmployeeScheduleRepository;
import barber_shop_application.service.AppointmentServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @InjectMocks
    private AppointmentServiceImplementation appointmentServiceImplementation;

    @Test
    public void testSaveAppointment() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));
        when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(customer));
        when(employeeScheduleRepository.findById(1)).thenReturn(Optional.ofNullable(schedule));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment savedAppointment = appointmentServiceImplementation.saveAppointment(1, 1, 1, date, appointmentStartTime);

        Assertions.assertThat(savedAppointment).isNotNull();
        Assertions.assertThat(savedAppointment).isEqualTo(appointment);
        
    }

    @Test
    public void testUpdateAppointment() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);

        LocalDate updatedDate = LocalDate.of(2025, 2, 1);
        LocalTime updatedTimeStart = LocalTime.of(11, 0);
        LocalTime updatedTimeEnd = updatedTimeStart.plusMinutes(30);
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
                                        .firstname("Test_update")
                                        .lastname("Employee_update")
                                        .username("test_employee_update")
                                        .password("password")
                                        .email("testemployee_update@email")
                                        .phone("987654321")
                                        .role("Employee")
                                        .bio("Test Employee update's bio..")
                                        .build();
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        when(employeeRepository.findById(2)).thenReturn(Optional.ofNullable(employeeToUpdate));
        when(customerRepository.findById(1)).thenReturn(Optional.ofNullable(customer));
        when(employeeScheduleRepository.findById(1)).thenReturn(Optional.ofNullable(schedule));
        when(appointmentRepository.findById(1)).thenReturn(Optional.ofNullable(appointment));
        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        Appointment updatedAppointment = appointmentServiceImplementation.updateAppointment(1, 2, 1, 1, updatedDate, updatedTimeStart);

        Assertions.assertThat(updatedAppointment).isNotNull();
        Assertions.assertThat(updatedAppointment.getEmployee().getUsername()).isEqualTo(employeeToUpdate.getUsername());
        Assertions.assertThat(updatedAppointment.getDate()).isEqualTo(updatedDate);
        Assertions.assertThat(updatedAppointment.getAppointmentStart()).isEqualTo(updatedTimeStart);
        Assertions.assertThat(updatedAppointment.getAppointmentEnd()).isEqualTo(updatedTimeEnd);
    }

    @Test
    public void testGetAllAppointments() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        List<Appointment> appointments = Arrays.asList(appointment);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> allAppointments = appointmentServiceImplementation.getAllAppointments();

        Assertions.assertThat(allAppointments).isNotNull();
        Assertions.assertThat(allAppointments).hasSize(1);
    }

    @Test
    public void testDeleteAppointment() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        when(appointmentRepository.findById(1)).thenReturn(Optional.ofNullable(appointment));

        appointmentServiceImplementation.deleteAppointment(1);
        verify(appointmentRepository).deleteById(1);
    }

    @Test
    public void testGetAppointmentById() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        when(appointmentRepository.findById(1)).thenReturn(Optional.ofNullable(appointment));

        Appointment appointmentById = appointmentServiceImplementation.getAppointmentById(1);

        Assertions.assertThat(appointmentById).isNotNull();
        Assertions.assertThat(appointmentById.getId()).isEqualTo(appointment.getId());
    }

    @Test
    public void testGetAppointmentsByDate() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        List<Appointment> appointmentsByDate = Arrays.asList(appointment);
        when(appointmentRepository.findByDate(date)).thenReturn(appointmentsByDate);

        List<Appointment> allAppointmentsByDate = appointmentServiceImplementation.getAppointmentsByDate(date);

        Assertions.assertThat(allAppointmentsByDate).isNotNull();
        Assertions.assertThat(allAppointmentsByDate).hasSize(1);
        Assertions.assertThat(allAppointmentsByDate.get(0).getDate()).isEqualTo(appointment.getDate());
    }

    @Test
    public void testGetAppointmentsByDates() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalDate startDate = date.minusDays(1);
        LocalDate endDate = date.plusDays(1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        List<Appointment> appointmentsByDates = Arrays.asList(appointment);
        when(appointmentRepository.findByDateBetween(startDate, endDate)).thenReturn(appointmentsByDates);

        List<Appointment> allAppointmentsByDates = appointmentServiceImplementation.getAppointmentsByDates(startDate, endDate);

        Assertions.assertThat(allAppointmentsByDates).isNotNull();
        Assertions.assertThat(allAppointmentsByDates).hasSize(1);
        Assertions.assertThat(allAppointmentsByDates.get(0).getDate()).isBetween(startDate, endDate);
    }

    @Test
    public void testGetAppointmentsByEmployeeId() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        List<Appointment> appointmentsByEmployeeId = Arrays.asList(appointment);
        when(appointmentRepository.findByEmployeeId(1)).thenReturn(appointmentsByEmployeeId);

        List<Appointment> allAppointmentsByEmployeeId = appointmentServiceImplementation.getAppointmentsByEmployeeId(1);

        Assertions.assertThat(allAppointmentsByEmployeeId).isNotNull();
        Assertions.assertThat(allAppointmentsByEmployeeId).hasSize(1);
        Assertions.assertThat(allAppointmentsByEmployeeId.get(0).getEmployee().getId()).isEqualTo(appointment.getEmployee().getId());
    }
    
    @Test
    public void testGetAppointmentsByCustomerId() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime scheduleStartTime = LocalTime.of(8, 0);
        LocalTime scheduleEndTime = LocalTime.of(13, 0);
        LocalTime appointmentStartTime = LocalTime.of(10, 0);
        LocalTime appointmentEndTime = appointmentStartTime.plusMinutes(30);
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
        Customer customer = Customer.builder()
                                    .firstname("Test")
                                    .lastname("Customer")
                                    .username("testcustomer")
                                    .password("password")
                                    .email("testcustomer@email")
                                    .phone("123456789")
                                    .role("user")
                                    .build();
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(scheduleStartTime)
                                                    .scheduleEndTime(scheduleEndTime)
                                                    .build();
        Appointment appointment = Appointment.builder()
                                                .employee(employee)
                                                .customer(customer)
                                                .employeeSchedule(schedule)
                                                .date(date)
                                                .appointmentStart(appointmentStartTime)
                                                .appointmentEnd(appointmentEndTime)
                                                .build();
        List<Appointment> appointmentsByCustomerId = Arrays.asList(appointment);
        when(appointmentRepository.findByCustomerId(1)).thenReturn(appointmentsByCustomerId);

        List<Appointment> allAppointmentsByCustomerId = appointmentServiceImplementation.getAppointmentsByCustomerId(1);

        Assertions.assertThat(allAppointmentsByCustomerId).isNotNull();
        Assertions.assertThat(allAppointmentsByCustomerId).hasSize(1);
        Assertions.assertThat(allAppointmentsByCustomerId.get(0).getEmployee().getId()).isEqualTo(appointment.getCustomer().getId());
    }
    
}