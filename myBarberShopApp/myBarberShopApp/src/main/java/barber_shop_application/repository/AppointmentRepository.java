package barber_shop_application.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import barber_shop_application.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

    List<Appointment> findByDate(LocalDate date);

    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Appointment> findByEmployeeId(Integer employeeId);

    List<Appointment> findByCustomerId(Integer customerId);
}
