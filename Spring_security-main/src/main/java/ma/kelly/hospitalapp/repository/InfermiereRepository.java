package ma.kelly.hospitalapp.repository;

import ma.kelly.hospitalapp.entities.Infermiere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfermiereRepository extends JpaRepository<Infermiere,Long> {
    Page<Infermiere> findByNomContains(String kw, Pageable pageable);
}
