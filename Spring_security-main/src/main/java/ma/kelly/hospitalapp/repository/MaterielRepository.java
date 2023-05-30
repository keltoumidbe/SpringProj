package ma.kelly.hospitalapp.repository;

import ma.kelly.hospitalapp.entities.Materiel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterielRepository extends JpaRepository<Materiel,Long> {
    Page<Materiel> findByNomContains(String kw, Pageable pageable);
}
