package ar.edu.unnoba.concursomateriales.repository;

import ar.edu.unnoba.concursomateriales.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByEmail(String email);
}
