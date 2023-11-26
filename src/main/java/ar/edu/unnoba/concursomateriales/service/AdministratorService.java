package ar.edu.unnoba.concursomateriales.service;

import ar.edu.unnoba.concursomateriales.model.Administrator;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdministratorService extends UserDetailsService {
    Administrator create(Administrator admin);
}
