package barber_shop_application.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;

@DataJpaTest
public class EmployeeScheduleRepositoryTest {

    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByDate(){

        Employee testEmployee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee")
                                        .username("test_employee")
                                        .password("password")
                                        .email("testemployee@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee's bio..")
                                        .build();
        employeeRepository.save(testEmployee);

        LocalDate date = LocalDate.of(2025, 1, 1);
        
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);
        
        List<EmployeeSchedule> schedulesByDate = employeeScheduleRepository.findByDate(date);

        Assertions.assertThat(schedulesByDate).isNotNull();
        Assertions.assertThat(schedulesByDate).hasSize(1);
        Assertions.assertThat(schedulesByDate.get(0).getDate()).isEqualTo(date);
    }

    @Test
    public void testFindByEmployeeId() {

        Employee testEmployee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee")
                                        .username("test_employee")
                                        .password("password")
                                        .email("testemployee@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee's bio..")
                                        .build();
        employeeRepository.save(testEmployee);

        LocalDate date = LocalDate.of(2025, 1, 1);
        
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        List<EmployeeSchedule> schedulesByEmployeeId = employeeScheduleRepository.findByEmployeeId(testEmployee.getId());

        Assertions.assertThat(schedulesByEmployeeId).isNotNull();
        Assertions.assertThat(schedulesByEmployeeId).hasSize(1);
        Assertions.assertThat(schedulesByEmployeeId.get(0).getEmployee().getId()).isEqualTo(testEmployee.getId());
    }

    @Test
    public void testFindByDateBetween() {
        Employee testEmployee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee")
                                        .username("test_employee")
                                        .password("password")
                                        .email("testemployee@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee's bio..")
                                        .build();
        employeeRepository.save(testEmployee);
        
        EmployeeSchedule schedule1 = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(LocalDate.of(2025, 1, 2))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule1);
        
        EmployeeSchedule schedule2 = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(LocalDate.of(2025, 1, 4))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule2);

        EmployeeSchedule schedule3 = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(LocalDate.of(2025, 1, 5))
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule3);

        LocalDate start_date = LocalDate.of(2025, 1, 1);
        LocalDate end_date = LocalDate.of(2025, 1, 4);

        List<EmployeeSchedule> schedulesByDates = employeeScheduleRepository.findByDateBetween(start_date, end_date);

        Assertions.assertThat(schedulesByDates).isNotNull();
        Assertions.assertThat(schedulesByDates).hasSize(2);
        Assertions.assertThat(schedulesByDates.get(0).getDate()).isBetween(start_date, end_date);
        Assertions.assertThat(schedulesByDates.get(1).getDate()).isBetween(start_date, end_date);        
    }

    @Test
    public void testGetScheduleIdForAppointment() {

        Employee testEmployee = Employee.builder()
                                        .firstname("Test")
                                        .lastname("Employee")
                                        .username("test_employee")
                                        .password("password")
                                        .email("testemployee@email")
                                        .phone("123456789")
                                        .role("Employee")
                                        .bio("Test Employee's bio..")
                                        .build();
        employeeRepository.save(testEmployee);

        LocalDate date = LocalDate.of(2025, 1, 1);

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                                                    .employee(testEmployee)
                                                    .date(date)
                                                    .scheduleStartTime(LocalTime.of(8, 0))
                                                    .scheduleEndTime(LocalTime.of(13, 0))
                                                    .build();
        employeeScheduleRepository.save(schedule);

        Integer validForAppointment = employeeScheduleRepository.getScheduleIdForAppointment(testEmployee.getId(), date, LocalTime.of(10, 0));

        Assertions.assertThat(validForAppointment).isNotNull();
        Assertions.assertThat(validForAppointment).isEqualTo(schedule.getId());

        Integer invalidForAppointment = employeeScheduleRepository.getScheduleIdForAppointment(testEmployee.getId(), date, LocalTime.of(14, 0));

        Assertions.assertThat(invalidForAppointment).isNull();
    }
}
