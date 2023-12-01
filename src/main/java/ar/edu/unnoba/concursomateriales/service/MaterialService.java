package ar.edu.unnoba.concursomateriales.service;

import ar.edu.unnoba.concursomateriales.model.Material;
import ar.edu.unnoba.concursomateriales.model.User;

import java.util.List;

public interface MaterialService {
    Material create(Material material);

    List<Material> findByStatusApproved();

    List<Material> findByStatusPending();

    Material findById(Long id);

    Material findByOwnerId(Long id);

    Material approveMaterial(Material material);

    Material rejectMaterial(Material material);

}
