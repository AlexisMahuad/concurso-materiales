package ar.edu.unnoba.concursomateriales.repository;

import ar.edu.unnoba.concursomateriales.model.Material;
import ar.edu.unnoba.concursomateriales.model.Material.Status;
import ar.edu.unnoba.concursomateriales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    List<Material> findByStatus(Status status);

    Material findByOwnerId(Long id);

}
