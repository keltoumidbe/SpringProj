package ma.kelly.hospitalapp.web;

import jakarta.validation.Valid;

import ma.kelly.hospitalapp.entities.Doctor;
import ma.kelly.hospitalapp.repository.DoctorRepository;
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

import java.util.List;

@Controller
public class DoctorController {
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/user/index/doctors")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") Integer  page,
                        @RequestParam(name = "size",defaultValue = "5") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String kw
    ){
        Page<Doctor> pageDoctors = doctorRepository.findByNomContains(kw, PageRequest.of(page,size));
        model.addAttribute("listDoctors",pageDoctors.getContent());
        model.addAttribute("pages",new int[pageDoctors.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);
        return "doctors";
    }

    public Doctor getDoc (){
        List <Doctor> Doctors = doctorRepository.findAll();
        return (Doctor) Doctors;
    }

    @GetMapping("/admin/deleteDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteDoctor(@RequestParam(name = "id") Long id){
        doctorRepository.deleteById(id);
        return "redirect:/user/index/doctors" ;
    }
    @GetMapping("/admin/formDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formDoctor(Model model ){
        model.addAttribute("doctor",new Doctor());
        return "formDoctor";
    }
    @PostMapping("/admin/saveDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveDoctor(@Valid Doctor doctor, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formDoctor";
        doctorRepository.save(doctor);
        return "formDoctor";
    }
    @GetMapping("/admin/editDoctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editDoctor(@RequestParam(name = "id") Long id, Model model){
        Doctor doctor=doctorRepository.findById(id).get();
        model.addAttribute("doctor",doctor);
        return "editDoctor";
    }
    @GetMapping("/doctor-home")
    public String home() {
        return "redirect:/user/index/doctors";
    }
}
