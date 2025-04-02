package barber_shop_application.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.service.EmployeeScheduleService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class EmployeeScheduleController {

    private EmployeeScheduleService employeeScheduleService;

    @PostMapping
    public ResponseEntity<EmployeeSchedule> saveEmployeeSchedule(@RequestBody EmployeeScheduleRequestBody employeeScheduleRequestBody){
        EmployeeSchedule savedEmployeeSchedule = employeeScheduleService.saveEmployeeSchedule(employeeScheduleRequestBody.getEmployeeId(), 
                                                                                                employeeScheduleRequestBody.getDate(), 
                                                                                                employeeScheduleRequestBody.getStartTime(), 
                                                                                                employeeScheduleRequestBody.getEndTime());
        return new ResponseEntity<>(savedEmployeeSchedule, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeSchedule>> getAllEmployeeSchedules(){
        List<EmployeeSchedule> employeeSchedules = employeeScheduleService.getAllEmployeeSchedules();
        return ResponseEntity.ok(employeeSchedules);
    }

    @GetMapping("/scheduleForAppointment")
    public ResponseEntity<Integer> getScheduleIdForAppointment(@RequestParam("employeeId") Integer employeeId, @RequestParam("date") LocalDate date, @RequestParam("time") LocalTime time){
        Integer scheduleIdForAppointment = employeeScheduleService.scheduleIdForAppointment(employeeId, date, time);
        return ResponseEntity.ok(scheduleIdForAppointment);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<EmployeeSchedule>> getSchedulesByDate(@RequestParam("date") LocalDate date) {
        List<EmployeeSchedule> employeeScheduleByDate = employeeScheduleService.getEmployeeScheduleByDate(date);

        return ResponseEntity.ok(employeeScheduleByDate);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeSchedule>> getSchedulesByEmployeeId(@PathVariable("employeeId") Integer employeeId) {
        List<EmployeeSchedule> employeeScheduleByEmployeeId = employeeScheduleService.getEmployeeScheduleByEmployeeId(employeeId);

        return ResponseEntity.ok(employeeScheduleByEmployeeId);
    }

    @GetMapping("/by-dates")
    public ResponseEntity<List<EmployeeSchedule>> getSchedulesByDates(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate) {
        List<EmployeeSchedule> employeeScheduleByDates = employeeScheduleService.getEmployeeScheduleByDates(startDate, endDate);
        return ResponseEntity.ok(employeeScheduleByDates);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeSchedule(@PathVariable("id") Integer scheduleId){
        employeeScheduleService.deleteEmployeeSchedule(scheduleId);
        return ResponseEntity.ok("Employee Schedule deleted successfully!");
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Builder
class EmployeeScheduleRequestBody{
    private Integer employeeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}