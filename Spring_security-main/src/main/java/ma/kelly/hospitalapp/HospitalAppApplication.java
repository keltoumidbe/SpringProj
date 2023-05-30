package ma.kelly.hospitalapp;

import ma.kelly.hospitalapp.entities.Doctor;
import ma.kelly.hospitalapp.entities.Patient;
import ma.kelly.hospitalapp.repository.DoctorRepository;
import ma.kelly.hospitalapp.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class HospitalAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"Mohamed",new Date(),false,null));
        };

    }
    CommandLineRunner start(DoctorRepository doctorRepository){
        return args -> {
            doctorRepository.save(new Doctor(null,"Mohamed","sabiri","Dermato",null));
        };

    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
