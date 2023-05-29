package ma.enset.hospitalapp.repository;

import ma.enset.hospitalapp.entities.Materiel;
import ma.enset.hospitalapp.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterielRepository extends JpaRepository<Materiel,Long> {
    Page<Materiel> findByNomContains(String kw, Pageable pageable);
}
