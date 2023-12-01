package ar.edu.unnoba.concursomateriales.controller;

import ar.edu.unnoba.concursomateriales.model.Material;
import ar.edu.unnoba.concursomateriales.model.User;
import ar.edu.unnoba.concursomateriales.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping()
    public String showMaterials(Model model) {
        model.addAttribute("materials", materialService.findByStatusApproved());
        return "user/approved-materials";
    }

    @GetMapping("/{id}")
    public String showMaterial(@PathVariable Long id, Model model) {
        Material material = materialService.findById(id);
        if (material != null) {
            model.addAttribute("material", material);
            return "user/material";
        }
        return "redirect:/materials";
    }

    @GetMapping("/user")
    public String showUserMaterial(Model model, @AuthenticationPrincipal User user) {
        Material userMaterial = materialService.findByOwnerId(user.getId());
        if (userMaterial != null) {
            model.addAttribute("material", userMaterial);
            model.addAttribute("user", user);
            return "user/user-material";
        }
        return "redirect:/material/new";
    }

    @GetMapping("/new")
    public String createMaterial(Model model, @AuthenticationPrincipal User user) {
        Material userMaterial = materialService.findByOwnerId(user.getId());
        if (userMaterial == null) {
            model.addAttribute("material", new Material());
            return "user/new-material";
        }
        return "redirect:/";
    }

    @PostMapping("/new")
    public String createMaterial(@Valid @ModelAttribute("material") Material material, @AuthenticationPrincipal User user, Errors errors) {
        // Data validation
        if (errors.hasErrors()) return "user/new-material";

        material.setOwner(user);
        material.setDate(new Date());
        materialService.create(material);
        return "redirect:/material/user";
    }

}
