package barber_shop_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import barber_shop_application.entities.EmployeeSchedule;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Integer> {

    List<EmployeeSchedule> findByDate(LocalDate date);

    List<EmployeeSchedule> findByEmployeeId(Integer employeeId);

    List<EmployeeSchedule> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query( """
            SELECT es.id FROM EmployeeSchedule es
            WHERE es.employee.id = :employeeId
            AND es.date = :date
            AND :time BETWEEN es.scheduleStartTime AND es.scheduleEndTime
            """)
    Integer getScheduleIdForAppointment(@Param("employeeId") Integer employeeId,
                                        @Param("date") LocalDate date,
                                        @Param("time") LocalTime time);
}
