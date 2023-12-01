package ar.edu.unnoba.concursomateriales.controller;

import ar.edu.unnoba.concursomateriales.model.Administrator;
import ar.edu.unnoba.concursomateriales.model.Material;
import ar.edu.unnoba.concursomateriales.model.User;
import ar.edu.unnoba.concursomateriales.service.MaterialService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdministratorController {

    private final MaterialService materialService;

    @Autowired
    public AdministratorController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping("/login")
    public String showLoginForm(Principal principal) {
        // Check if user is already logged in
        if (principal != null) return "redirect:/admin/home";
        return "admin/login";
    }

    @GetMapping("/material")
    public String showApprovedMaterials(Model model) {
        model.addAttribute("materials", materialService.findByStatusApproved());
        return "admin/approved-materials";
    }

    @GetMapping("/material/{id}")
    public String showMaterial(@PathVariable Long id, HttpServletRequest request, Model model) {
        Material material = materialService.findById(id);
        if (material != null) {
            model.addAttribute("material", material);
            return "admin/material";
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/material/pending")
    public String showPendingMaterials(Model model) {
        model.addAttribute("materials", materialService.findByStatusPending());
        return "admin/pending-materials";
    }

    @PostMapping("/material/{id}/approve")
    public String approveMaterial(@PathVariable Long id) {
        Material material = materialService.findById(id);
        if (material != null && material.getStatus() == Material.Status.PENDING) {
            materialService.approveMaterial(material);
        }
        return "redirect:/admin/material/pending";
    }

    @PostMapping("/material/{id}/reject")
    public String rejectMaterial(@PathVariable Long id) {
        Material material = materialService.findById(id);
        if (material != null && material.getStatus() == Material.Status.PENDING) {
            materialService.rejectMaterial(material);
        }
        return "redirect:/admin/material/pending";
    }

}
