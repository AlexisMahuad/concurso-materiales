package ar.edu.unnoba.concursomateriales.repository;

import ar.edu.unnoba.concursomateriales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
