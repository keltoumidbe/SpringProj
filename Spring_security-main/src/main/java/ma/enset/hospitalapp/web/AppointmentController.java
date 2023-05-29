package ma.enset.hospitalapp.web;

import ch.qos.logback.core.model.Model;
import jakarta.validation.Valid;
import ma.enset.hospitalapp.entities.Appointment;
import ma.enset.hospitalapp.entities.Doctor;
import ma.enset.hospitalapp.entities.Patient;
import ma.enset.hospitalapp.repository.AppointmentRepository;
import ma.enset.hospitalapp.repository.DoctorRepository;
import ma.enset.hospitalapp.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;



import java.util.List;

    @Controller
    public class AppointmentController {
        @Autowired
        private AppointmentRepository appointmentRepository;

        @Autowired
        private DoctorRepository doctorRepository;

        @Autowired
        private PatientRepository patientRepository;

        // Afficher la liste des rendez-vous
        @GetMapping("admin/index/appointments")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String listAppointments(ModelMap  model) {
            List<Appointment> appointments = appointmentRepository.findAll();
            model.addAttribute("appointments", appointments);
            return "appointments";
        }

        // Afficher le formulaire d'ajout d'un rendez-vous
        @GetMapping("admin/index/create-appointment")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String showAddAppointmentForm(ModelMap  model) {
            Appointment appointment = new Appointment();
            List<Doctor> doctors = doctorRepository.findAll();
            List<Patient> patients = patientRepository.findAll();
            model.addAttribute("appointment", appointment);
            model.addAttribute("doctors", doctors);
            model.addAttribute("patients", patients);
            return "create-appointment";
        }
        @PostMapping("admin/index/saveRDV")
        @PreAuthorize("hasRole('ROLE_ADMIN')")
        public String saveRDV(@Valid Appointment appointment, BindingResult bindingResult){
            if (bindingResult.hasErrors()) return "formDoctor";
            appointmentRepository.save(appointment);
            return "create-appointment";
        }


        // Afficher le formulaire de mise à jour d'un rendez-vous
        @GetMapping("user/index/appointments/edit/{id}")
        public String showEditAppointmentForm(@PathVariable("id") Long id, ModelMap  model) {
            Appointment appointment = appointmentRepository.findById(id).orElse(null);
            List<Doctor> doctors = doctorRepository.findAll();
            List<Patient> patients = patientRepository.findAll();

            model.addAttribute("appointment", appointment);
            model.addAttribute("doctors", doctors);
            model.addAttribute("patients", patients);

            return "edit-appointment";
        }

        // Mettre à jour un rendez-vous existant
        @PostMapping("/appointments/update/{id}")
        public String updateAppointment(@PathVariable("id") Long id, @ModelAttribute("appointment") @Valid Appointment updatedAppointment, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                // Gérer les erreurs de validation, si nécessaire
                return "edit-appointment";
            }

            Appointment appointment = appointmentRepository.findById(id).orElse(null);

            Doctor doctor = doctorRepository.findById(updatedAppointment.getDoctor().getId()).orElse(null);
            Patient patient = patientRepository.findById(updatedAppointment.getPatient().getId()).orElse(null);

            appointment.setDate(updatedAppointment.getDate());
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);

            appointmentRepository.save(appointment);
            return "redirect:/appointments";
        }

        // Supprimer un rendez-vous
        @GetMapping("/appointments/delete/{id}")
        public String deleteAppointment(@PathVariable("id") Long id) {
            appointmentRepository.deleteById(id);
            return "redirect:/appointments";
        }
    }


