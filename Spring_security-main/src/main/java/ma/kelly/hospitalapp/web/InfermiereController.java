package ma.kelly.hospitalapp.web;

import jakarta.validation.Valid;

import ma.kelly.hospitalapp.entities.Infermiere;
import ma.kelly.hospitalapp.repository.InfermiereRepository;
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
public class InfermiereController {
    @Autowired
    private InfermiereRepository infermiereRepository;
    @GetMapping("/user/index/infermieres")

    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Infermiere> pageInfermieres = infermiereRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listInfermieres",pageInfermieres.getContent());
        model.addAttribute("pages",new int[pageInfermieres.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "infermieres";
    }
    @GetMapping("/admin/deleteInfermiere")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteInfermiere(@RequestParam(name = "id") Long id){
        infermiereRepository.deleteById(id);
        return "redirect:/user/index/infermieres" ;
    }
    @GetMapping("/admin/formInfermiere")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formInfermiere(Model model ){
        model.addAttribute("infermiere",new Infermiere());
        return "formInfermiere";
    }
    @PostMapping("/admin/saveInfermiere")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveInfermiere(@Valid Infermiere infermiere, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formInfermiere";
        infermiereRepository.save(infermiere);
        return "formInfermiere";
    }
    @GetMapping("/admin/editInfermiere")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editInfermiere(@RequestParam(name = "id") Long id, Model model){
        Infermiere infermiere=infermiereRepository.findById(id).get();
        model.addAttribute("infermiere",infermiere);
        return "editInfermiere";
    }
    @GetMapping("/infermiere-home")
    public String home() {
        return "redirect:/user/index/infermieres";
    }
}
