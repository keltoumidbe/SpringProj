package ma.kelly.hospitalapp.web;

import jakarta.validation.Valid;
import ma.kelly.hospitalapp.entities.Materiel;

import ma.kelly.hospitalapp.repository.MaterielRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MaterielController {
    @Autowired
    private MaterielRepository materielRepository;
    @GetMapping("/user/index/materiels")

    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Materiel> pageMateriels = materielRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listMateriels",pageMateriels.getContent());
        model.addAttribute("pages",new int[pageMateriels.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "materiels";
    }
    @GetMapping("/admin/deleteMateriel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMateriel(@RequestParam(name = "id") Long id){
        materielRepository.deleteById(id);
        return "redirect:/user/index/materiels";
    }
    @GetMapping("/admin/formMateriel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formMateriel(Model model ){
        model.addAttribute("materiel",new Materiel());
        return "formMateriel";
    }
    @PostMapping("/admin/saveMateriel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveMateriel(@Valid Materiel materiel, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formMateriel";
        materielRepository.save(materiel);
        return "formMateriel";
    }
    @GetMapping("/admin/editMateriel")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editMateriel(@RequestParam(name = "id") Long id, Model model){
        Materiel materiel=materielRepository.findById(id).get();
        model.addAttribute("materiel",materiel);
        return "editMateriel";
    }
    @GetMapping("/materiel-home")
    public String home(){
        return "redirect:/user/index/materiels";
    }
}
