package com.yakvel.carInsuranceBackEnd;

import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.repositories.PersonRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDataLoader implements ApplicationRunner {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    public PersonDataLoader(PersonRepository personRepository, PasswordEncoder passwordEncoder){
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (personRepository.count() < 5) {
            List<Person> people = List.of(
                    new Person("John", "Doe", "po1@g.com", passwordEncoder.encode("123"), "USER"),
                    new Person("Mary", "Doe", "po2@g.com", passwordEncoder.encode("123"), "USER"),
                    new Person("Steven", "Doe", "po3@g.com", passwordEncoder.encode("123"), "USER"),
                    new Person("Jason", "Sparkles", "mn1@g.com", passwordEncoder.encode("123"), "MANAGER"),
                    new Person("Misery", "Sparkles", "mn2@g.com", passwordEncoder.encode("123"), "MANAGER"),
                    new Person("Egor", "Sparkles", "mn3@g.com", passwordEncoder.encode("123"), "MANAGER")
            );
            personRepository.saveAll(people);
        }
    }
}
