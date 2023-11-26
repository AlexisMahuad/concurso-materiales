package ar.edu.unnoba.concursomateriales.service;

import ar.edu.unnoba.concursomateriales.model.Administrator;
import ar.edu.unnoba.concursomateriales.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImp implements AdministratorService {

    private final AdministratorRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public AdministratorServiceImp(AdministratorRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public Administrator create(Administrator admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        repository.save(admin);
        return admin;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrator admin = repository.findByEmail(email);
        if (admin == null) throw new UsernameNotFoundException("Account with email " + email + " not found");
        return admin;
    }

}
