package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.exceptions.ResourseNotFoundException;
import barber_shop_application.entities.Employee;
import barber_shop_application.repository.EmployeeRepository;
import barber_shop_application.repository.EmployeeScheduleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeScheduleServiceImplementation implements EmployeeScheduleService{
    
    private EmployeeRepository employeeRepository;
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Override
    public EmployeeSchedule saveEmployeeSchedule(Integer employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        Employee employee = employeeRepository.findById(employeeId)
                                        .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        EmployeeSchedule newEmployeeSchedule = new EmployeeSchedule(null , employee, date, startTime, endTime);
        EmployeeSchedule savedNewEmployeeSchedule = employeeScheduleRepository.save(newEmployeeSchedule);

        return savedNewEmployeeSchedule;
    }

    @Override
    public List<EmployeeSchedule> getAllEmployeeSchedules() {
        List<EmployeeSchedule> employeeSchedules = employeeScheduleRepository.findAll();
        return employeeSchedules;
    }

    @Override
    public Integer scheduleIdForAppointment(Integer employeeId, LocalDate date, LocalTime time) {
        Integer scheduleId = employeeScheduleRepository.getScheduleIdForAppointment(employeeId, date, time);
        return scheduleId;
    }

    @Override
    public List<EmployeeSchedule> getEmployeeScheduleByDate(LocalDate date) {
        List<EmployeeSchedule> scheduleByDate = employeeScheduleRepository.findByDate(date);
        
        return scheduleByDate;
    }

    @Override
    public List<EmployeeSchedule> getEmployeeScheduleByEmployeeId(Integer employeeId) {
        List<EmployeeSchedule> scheduleByEmployeeId = employeeScheduleRepository.findByEmployeeId(employeeId);
        
        return scheduleByEmployeeId;
    }

    @Override
    public List<EmployeeSchedule> getEmployeeScheduleByDates(LocalDate startDate, LocalDate endDate) {
        List<EmployeeSchedule> scheduleByDates = employeeScheduleRepository.findByDateBetween(startDate, endDate);
        
        return scheduleByDates;
    }

    @Override
    public void deleteEmployeeSchedule(Integer scheduleId) {
        employeeScheduleRepository.findById(scheduleId)
                            .orElseThrow(() -> new ResourseNotFoundException("There is no schedule with id: " + scheduleId));
        employeeScheduleRepository.deleteById(scheduleId);
    }

}
