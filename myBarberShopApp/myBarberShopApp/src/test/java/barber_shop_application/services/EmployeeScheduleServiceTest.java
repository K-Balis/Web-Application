package barber_shop_application.services;

import static org.mockito.ArgumentMatchers.any;
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
import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.repository.EmployeeRepository;
import barber_shop_application.repository.EmployeeScheduleRepository;
import barber_shop_application.service.EmployeeScheduleServiceImplementation;

@ExtendWith(MockitoExtension.class)
public class EmployeeScheduleServiceTest {

    @Mock
    private EmployeeScheduleRepository scheduleRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeScheduleServiceImplementation scheduleServiceImplementation;

    @Test
    public void testSaveEmployeeSchedule() {

        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(13, 0);
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
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(startTime)
                                                    .scheduleEndTime(endTime)
                                                    .build();
        when(employeeRepository.findById(1)).thenReturn(Optional.ofNullable(employee));
        when(scheduleRepository.save(any(EmployeeSchedule.class))).thenReturn(schedule);

        EmployeeSchedule savedSchedule = scheduleServiceImplementation.saveEmployeeSchedule(1, date, startTime, endTime);

        Assertions.assertThat(savedSchedule).isNotNull();
        Assertions.assertThat(savedSchedule).isEqualTo(schedule);
    }

    @Test
    public void testGetAllEmployeeSchedules() {
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
        EmployeeSchedule schedule1 = EmployeeSchedule.builder()
                                                    .employee(employee1)
                                                    .date(LocalDate.of(2025, 2, 21))
                                                    .scheduleStartTime(LocalTime.of(9, 0))
                                                    .scheduleEndTime(LocalTime.of(14, 0)    )
                                                    .build();
        EmployeeSchedule schedule2 = EmployeeSchedule.builder()
                                                    .employee(employee2)
                                                    .date(LocalDate.of(2025, 1, 1))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        List<EmployeeSchedule> schedules = Arrays.asList(schedule1, schedule2);
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<EmployeeSchedule> allSchedules = scheduleServiceImplementation.getAllEmployeeSchedules();

        Assertions.assertThat(allSchedules).isNotNull();
        Assertions.assertThat(allSchedules).hasSize(2);
    }

    @Test
    public void testScheduleIdForAppointment() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalTime appointmentTime = LocalTime.of(10, 0);

        when(scheduleRepository.getScheduleIdForAppointment(1, date, appointmentTime)).thenReturn(1);

        Integer scheduleId = scheduleServiceImplementation.scheduleIdForAppointment(1, date, appointmentTime);

        Assertions.assertThat(scheduleId).isNotNull();
        Assertions.assertThat(scheduleId).isEqualTo(1);
    }

    @Test
    public void testGetEmployeeScheduleByDate() {

        LocalDate date = LocalDate.of(2025, 1, 1);
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
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(14, 0)    )
                                                    .build();
        List<EmployeeSchedule> schedules = Arrays.asList(schedule);
        when(scheduleRepository.findByDate(date)).thenReturn(schedules);

        List<EmployeeSchedule> allSchedulesByDate = scheduleServiceImplementation.getEmployeeScheduleByDate(date);

        Assertions.assertThat(allSchedulesByDate).isNotNull();
        Assertions.assertThat(allSchedulesByDate).hasSize(1);
        Assertions.assertThat(allSchedulesByDate.get(0).getDate()).isEqualTo(date);
    }

    @Test
    public void testGetEmployeeScheduleByDates() {
        LocalDate date = LocalDate.of(2025, 1, 1);
        LocalDate startDate = date.minusDays(1);
        LocalDate endDate = date.plusDays(1);
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
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(14, 0)    )
                                                    .build();
        List<EmployeeSchedule> schedules = Arrays.asList(schedule);
        when(scheduleRepository.findByDateBetween(startDate, endDate)).thenReturn(schedules);

        List<EmployeeSchedule> allSchedulesByDates = scheduleServiceImplementation.getEmployeeScheduleByDates(startDate, endDate);

        Assertions.assertThat(allSchedulesByDates).isNotNull();
        Assertions.assertThat(allSchedulesByDates).hasSize(1);
        Assertions.assertThat(allSchedulesByDates.get(0).getDate()).isBetween(startDate, endDate);
    }

    @Test
    public void testGetEmployeeScheduleByEmployeeId() {

        LocalDate date = LocalDate.of(2025, 1, 1);
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
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        List<EmployeeSchedule> schedules = Arrays.asList(schedule);
        when(scheduleRepository.findByEmployeeId(1)).thenReturn(schedules);

        List<EmployeeSchedule> allSchedulesByEmployeeId = scheduleServiceImplementation.getEmployeeScheduleByEmployeeId(1);

        Assertions.assertThat(allSchedulesByEmployeeId).isNotNull();
        Assertions.assertThat(allSchedulesByEmployeeId).hasSize(1);
        Assertions.assertThat(allSchedulesByEmployeeId.get(0).getEmployee().getId()).isEqualTo(employee.getId());
    }

    @Test
    public void testDeleteEmployeeSchedule() {
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
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(employee)
                                                    .date(LocalDate.of(2025, 1, 1))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        when(scheduleRepository.findById(1)).thenReturn(Optional.ofNullable(schedule));

        scheduleServiceImplementation.deleteEmployeeSchedule(1);
        verify(scheduleRepository).deleteById(1);
    }
}
