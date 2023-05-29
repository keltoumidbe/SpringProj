package ma.enset.hospitalapp.web;

import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Infermiere;
import ma.enset.hospitalapp.repository.InfermiereRepository;
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
public class ReceptionistController {
        @Autowired
        private InfermiereRepository infermiereRepository;
        @GetMapping("/user/index/Receptionist")

        public String index(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name = "size",defaultValue = "5") int size,
                            @RequestParam(name = "keyword",defaultValue = "") String kw
        ){
            Page<Infermiere> pageReceptionist = infermiereRepository.findByNomContains(kw, PageRequest.of(page,size));
            model.addAttribute("listReceptionist",pageReceptionist.getContent());
            model.addAttribute("pages",new int[pageReceptionist.getTotalPages()]);
            model.addAttribute("currentPage",page);
            model.addAttribute("keyword",kw);
            return "Receptionist";
        }
        @GetMapping("/admin/deleteReceptionist")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String deleteReceptionist(@RequestParam(name = "id") Long id){
            infermiereRepository.deleteById(id);
            return "redirect:/user/index/Receptionist" ;
        }
        @GetMapping("/admin/formReceptionist")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String formReceptionist(Model model ){
            model.addAttribute("Receptionist",new Infermiere());
            return "formReceptionist";
        }
        @PostMapping("/admin/saveReceptionist")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String saveReceptionist(@Valid Infermiere infermiere, BindingResult bindingResult){
            if (bindingResult.hasErrors()) return "formReceptionist";
            infermiereRepository.save(infermiere);
            return "formReceptionist";
        }
        @GetMapping("/admin/editReceptionist")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String editReceptionist(@RequestParam(name = "id") Long id, Model model){
            Infermiere infermiere=infermiereRepository.findById(id).get();
            model.addAttribute("Receptionist",infermiere);
            return "editReceptionist";
        }
        @GetMapping("/Receptionist-home")
        public String home() {
            return "redirect:/user/index/Receptionist";
        }
    }
