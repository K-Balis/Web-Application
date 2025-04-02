package barber_shop_application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import barber_shop_application.entities.Appointment;
import barber_shop_application.entities.Customer;
import barber_shop_application.entities.Employee;
import barber_shop_application.entities.EmployeeSchedule;
import barber_shop_application.exceptions.ResourseNotFoundException;
import barber_shop_application.repository.AppointmentRepository;
import barber_shop_application.repository.CustomerRepository;
import barber_shop_application.repository.EmployeeRepository;
import barber_shop_application.repository.EmployeeScheduleRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImplementation implements AppointmentService{

    private AppointmentRepository appointmentRepository;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Override
    public Appointment saveAppointment(Integer employeeId, Integer customerId, Integer scheduleId, LocalDate date, LocalTime startTime) {
        Employee employee = employeeRepository.findById(employeeId)
                                                .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        Customer customer = customerRepository.findById(customerId)
                                                .orElseThrow(() -> new ResourseNotFoundException("There is no customer with id: " + customerId));
        EmployeeSchedule schedule = employeeScheduleRepository.findById(scheduleId)
                                                                .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        Appointment newAppointment = new Appointment(null, employee, customer, schedule, date, startTime, startTime.plusMinutes(30));
        Appointment savedAppointment = appointmentRepository.save(newAppointment);

        return savedAppointment;
    }

    @Override
    public Appointment updateAppointment(Integer appointmentId, Integer employeeId, Integer customerId, Integer scheduleId,
            LocalDate date, LocalTime startTime) {
        Appointment appointmentObj = appointmentRepository.findById(appointmentId)
                                                            .orElseThrow(() -> new ResourseNotFoundException("There is no appointment with id: " + appointmentId));
        Employee employee = employeeRepository.findById(employeeId)
                                                .orElseThrow(() -> new ResourseNotFoundException("There is no employee with id: " + employeeId));
        Customer customer = customerRepository.findById(customerId)
                                                .orElseThrow(() -> new ResourseNotFoundException("There is no customer with id: " + customerId));
        EmployeeSchedule schedule = employeeScheduleRepository.findById(scheduleId)
                                                                .orElseThrow(() -> new ResourseNotFoundException("There is no schedule with id: " + scheduleId));
        appointmentObj.setEmployee(employee);
        appointmentObj.setCustomer(customer);
        appointmentObj.setEmployeeSchedule(schedule);
        appointmentObj.setDate(date);
        appointmentObj.setAppointmentStart(startTime);
        appointmentObj.setAppointmentEnd(startTime.plusMinutes(30));

        Appointment updatedAppointment = appointmentRepository.save(appointmentObj);

        return updatedAppointment;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        return allAppointments;
    }

    @Override
    public void deleteAppointment(Integer appointmentId) {
        appointmentRepository.findById(appointmentId)
                                .orElseThrow(() -> new ResourseNotFoundException("There is no appointment with id:" + appointmentId));
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public Appointment getAppointmentById(Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                                                            .orElseThrow(() -> new ResourseNotFoundException("There is no appointment with id: " + appointmentId));
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointmentsByDate = appointmentRepository.findByDate(date);
        return appointmentsByDate;
    }

    @Override
    public List<Appointment> getAppointmentsByDates(LocalDate startDate, LocalDate endDate) {
        List<Appointment> appointmentsByDates = appointmentRepository.findByDateBetween(startDate, endDate);
        return appointmentsByDates;
    }

    @Override
    public List<Appointment> getAppointmentsByEmployeeId(Integer employeeId) {
        List<Appointment> appointmentsByEmployeeId = appointmentRepository.findByEmployeeId(employeeId);
        return appointmentsByEmployeeId;
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerId(Integer customerId) {
        List<Appointment> appointmentsByCustomerId = appointmentRepository.findByCustomerId(customerId);
        return appointmentsByCustomerId;
    }
    
}
