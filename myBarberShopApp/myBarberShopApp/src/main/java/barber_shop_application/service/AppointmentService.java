package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import barber_shop_application.entities.Appointment;

public interface AppointmentService {

    Appointment saveAppointment(Integer employeeId, Integer customerId, Integer scheduleId, LocalDate date, LocalTime startTime);

    Appointment updateAppointment(Integer appointmentId, Integer employeeId, Integer customerId, Integer scheduleId, LocalDate date, LocalTime startTime);

    List<Appointment> getAllAppointments();

    void deleteAppointment(Integer appointmentId);

    Appointment getAppointmentById(Integer appointmentId);

    List<Appointment> getAppointmentsByDate(LocalDate date);

    List<Appointment> getAppointmentsByDates(LocalDate startDate, LocalDate endDate);

    List<Appointment> getAppointmentsByEmployeeId(Integer employeeId);

    List<Appointment> getAppointmentsByCustomerId(Integer customerId);
}
