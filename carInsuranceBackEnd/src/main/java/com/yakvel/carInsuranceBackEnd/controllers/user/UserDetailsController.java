package com.yakvel.carInsuranceBackEnd.controllers.user;


import com.yakvel.carInsuranceBackEnd.controllers.user.service.ContactDto;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonChangePassRequest;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonDto;
import com.yakvel.carInsuranceBackEnd.mappers.ContactMapper;
import com.yakvel.carInsuranceBackEnd.mappers.PersonMapper;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Person;
import com.yakvel.carInsuranceBackEnd.repositories.ContactRepository;
import com.yakvel.carInsuranceBackEnd.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserDetailsController {
    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<PersonDto> currentUserDetails() {
        Person person = getPerson();
        return  ResponseEntity.ok(personMapper.toDto(person));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserContact(@RequestBody ContactDto dto) {
        Person person = getPerson();
        Contact updatedPersonContact = contactMapper.toEntity(dto);
        person.setContactInfo(updatedPersonContact);
        personRepository.save(person);
        return ResponseEntity.ok("Contact information was updated!");
    }


    @PutMapping("/profile/change-pass")
    public ResponseEntity changePersonPassword(@RequestBody PersonChangePassRequest request) {
        Person person = getPerson();

        boolean isPassCorrect = checkPassword(request.getOldPassword(), person.getPassword());

        boolean isPasswordsDiff = checkTwoEnteredPasswords(request.getOldPassword(), request.getNewPassword());

        if (!isPassCorrect) {
            return ResponseEntity.badRequest().body("Entered actual password is not correct");
        } else if (!isPasswordsDiff) {
            return ResponseEntity.badRequest().body("Entered passwords are identical!");
        }

        person.setPassword(passwordEncoder.encode(request.getNewPassword()));
        personRepository.save(person);
        return ResponseEntity.ok("Password was changed");

    }

    private boolean checkTwoEnteredPasswords(String oldPassword, String newPassword) {
        return !oldPassword.equals(newPassword);
    }

    private boolean checkPassword(String enteredOldPassword, String oldPassword) {
        return passwordEncoder.matches(enteredOldPassword, oldPassword);
    }


    private Person getPerson() {
        return (Person) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }
}
