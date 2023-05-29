package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Receptionist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReceptionistRepository extends JpaRepository<Receptionist,Long> {
    //Page<Receptionist> findByNomContains(String kw, Pageable pageable);
}
