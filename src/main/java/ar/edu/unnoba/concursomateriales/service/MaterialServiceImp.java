package ar.edu.unnoba.concursomateriales.service;

import ar.edu.unnoba.concursomateriales.model.Material;
import ar.edu.unnoba.concursomateriales.model.Material.Status;
import ar.edu.unnoba.concursomateriales.model.User;
import ar.edu.unnoba.concursomateriales.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImp implements MaterialService {

    private final MaterialRepository repository;

    @Autowired
    public MaterialServiceImp(MaterialRepository repository) {
        this.repository = repository;
    }

    @Override
    public Material create(Material material) {
        return repository.save(material);
    }

    @Override
    public List<Material> findByStatusApproved() {
        return repository.findByStatus(Status.APPROVED);
    }

    @Override
    public List<Material> findByStatusPending() {
        return repository.findByStatus(Status.PENDING);
    }

    @Override
    public Material findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Material findByOwnerId(Long id) {
        return repository.findByOwnerId(id);
    }

    @Override
    public Material approveMaterial(Material material) {
        material.setStatus(Status.APPROVED);
        return repository.save(material);
    }

    @Override
    public Material rejectMaterial(Material material) {
        material.setStatus(Status.REJECTED);
        return repository.save(material);
    }

}
