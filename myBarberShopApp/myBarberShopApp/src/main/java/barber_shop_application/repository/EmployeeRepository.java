package barber_shop_application.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import barber_shop_application.entities.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query( """
            SELECT e FROM Employee e
            JOIN EmployeeSchedule es ON e.id =es.employee.id
            WHERE es.date = :appointmentDate
            AND es.scheduleStartTime <= :appointmentStartTime
            AND es.scheduleEndTime >= :appointmentEndTime
            AND e.id NOT IN (
                SELECT ap.employee.id 
                FROM Appointment ap
                WHERE ap.date = :appointmentDate
                AND :appointmentStartTime < ap.appointmentEnd
                AND ap.appointmentStart < :appointmentEndTime
            )
            """)
    List<Employee> getAvailableEmployees(   @Param("appointmentDate") LocalDate appointmentDate, 
                                            @Param("appointmentStartTime") LocalTime appointmentStartTime, 
                                            @Param("appointmentEndTime") LocalTime appointmentEndTime);

    Employee findByUsername(String username);
}
