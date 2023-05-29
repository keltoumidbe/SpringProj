package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
   // Page<Appointment> findByNomContains(String kw, Pageable pageable);
}

