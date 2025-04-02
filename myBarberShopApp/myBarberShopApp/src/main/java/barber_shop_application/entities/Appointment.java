package barber_shop_application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_schedule_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private EmployeeSchedule employeeSchedule;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "appointment_start", nullable = false)
    private LocalTime appointmentStart;

    @Column(name = "appointment_end", nullable = false)
    private LocalTime appointmentEnd;

    public Appointment(Employee employee, Customer customer, EmployeeSchedule employeeSchedule, LocalDate date, LocalTime appointmentStart){
        this.employee = employee;
        this.customer = customer;
        this.employeeSchedule = employeeSchedule;
        this.date = date;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentStart.plusMinutes(30);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public EmployeeSchedule getEmployeeSchedule() {
        return employeeSchedule;
    }

    public void setEmployeeSchedule(EmployeeSchedule employeeSchedule) {
        this.employeeSchedule = employeeSchedule;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(LocalTime appointmentStart) {
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentStart.plusMinutes(30);
    }

    public LocalTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(LocalTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }
}
