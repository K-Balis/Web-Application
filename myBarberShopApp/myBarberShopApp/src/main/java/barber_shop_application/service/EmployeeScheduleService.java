package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import barber_shop_application.entities.EmployeeSchedule;

public interface EmployeeScheduleService {

    EmployeeSchedule saveEmployeeSchedule(Integer employeeId, LocalDate date, LocalTime starTime, LocalTime endTime);

    List<EmployeeSchedule> getAllEmployeeSchedules();

    Integer scheduleIdForAppointment(Integer employeeId, LocalDate date, LocalTime time);

    List<EmployeeSchedule> getEmployeeScheduleByDate(LocalDate date);

    List<EmployeeSchedule> getEmployeeScheduleByEmployeeId(Integer employeeId);

    List<EmployeeSchedule> getEmployeeScheduleByDates(LocalDate startDate, LocalDate endDate);

    void deleteEmployeeSchedule(Integer scheduleId);

}
