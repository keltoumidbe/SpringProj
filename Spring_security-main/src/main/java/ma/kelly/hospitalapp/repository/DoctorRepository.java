package ma.kelly.hospitalapp.repository;

import ma.kelly.hospitalapp.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Page<Doctor> findByNomContains(String kw, Pageable pageable);
}
