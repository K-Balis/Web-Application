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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import barber_shop_application.entities.Appointment;
import barber_shop_application.service.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@CrossOrigin("*")
@RestController
@RequestMapping("/appointments")
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> saveAppointment(@RequestBody AppointmentRequestBody newAppointment){
        Appointment savedAppointment = appointmentService.saveAppointment(newAppointment.getEmployeeId(), 
                                                                            newAppointment.getCustomerId(), 
                                                                            newAppointment.getScheduleId(),
                                                                            newAppointment.getDate(), 
                                                                            newAppointment.getStartTime());
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") Integer appointmentId, @RequestBody AppointmentRequestBody appointmentToUpdate){
        Appointment updatedAppointment = appointmentService.updateAppointment(  appointmentId,
                                                                                appointmentToUpdate.getEmployeeId(),
                                                                                appointmentToUpdate.getCustomerId(),
                                                                                appointmentToUpdate.getScheduleId(),
                                                                                appointmentToUpdate.getDate(),
                                                                                appointmentToUpdate.getStartTime());

        return ResponseEntity.ok(updatedAppointment);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(){
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(allAppointments);
    }

    @GetMapping("{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable("id") Integer appointmentId){
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") Integer appointmentId){
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.ok("Appointment deleted successfully!");
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(@RequestParam("date") LocalDate date){
        List<Appointment> appointmentsByDate = appointmentService.getAppointmentsByDate(date);

        return ResponseEntity.ok(appointmentsByDate);
    }

    @GetMapping("by-dates")
    public ResponseEntity<List<Appointment>> getAppointmentsByDates(@RequestParam("startDate") LocalDate startDate, @RequestParam("endDate") LocalDate endDate){
        List<Appointment> appointmentsByDates = appointmentService.getAppointmentsByDates(startDate, endDate);

        return ResponseEntity.ok(appointmentsByDates);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByEmployeeId(@PathVariable Integer employeeId){
        List<Appointment> appointmentsByEmployee = appointmentService.getAppointmentsByEmployeeId(employeeId);

        return ResponseEntity.ok(appointmentsByEmployee);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByCustomerId(@PathVariable Integer customerId){
        List<Appointment> appointmentsByCustomer = appointmentService.getAppointmentsByCustomerId(customerId);

        return ResponseEntity.ok(appointmentsByCustomer);
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Builder
class AppointmentRequestBody{
    private Integer employeeId;
    private Integer customerId;
    private Integer scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public void setAppointmentStart(LocalTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(30);
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
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