package com.yakvel.carInsuranceBackEnd.controllers.user;


import com.yakvel.carInsuranceBackEnd.controllers.user.service.ContactDto;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonChangePassRequest;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonDto;
import com.yakvel.carInsuranceBackEnd.mappers.ItemMapper;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Person;
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
    private ItemMapper<ContactDto,Contact> contactMapper;
    @Autowired
    private ItemMapper<PersonDto,Person> personMapper;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<PersonDto> currentUserDetails() {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return  ResponseEntity.ok(personMapper.toDto(person));
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserContact(@RequestBody ContactDto dto) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Contact updatedPersonContact = contactMapper.toEntity(dto);
        person.setContactInfo(updatedPersonContact);
        personRepository.save(person);
        return ResponseEntity.ok("Contact information was updated!");
    }


    @PutMapping("/profile/change-pass")
    public ResponseEntity changePersonPassword(@RequestBody PersonChangePassRequest request) {
        Person person = (Person) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isPassCorrect = checkActualPasswordMatch(request.getOldPassword(), person.getPassword());
        if (!isPassCorrect) {
            return ResponseEntity.badRequest().body("Entered actual password is not correct");
        }

        boolean isPasswordsDiff = checkTwoEnteredPasswordsAreDifferent(request.getOldPassword(), request.getNewPassword());
        if (!isPasswordsDiff) {
            return ResponseEntity.badRequest().body("Entered passwords are identical!");
        }

        person.setPassword(passwordEncoder.encode(request.getNewPassword()));
        personRepository.save(person);
        return ResponseEntity.ok("Password was changed");

    }

    private boolean checkTwoEnteredPasswordsAreDifferent(String oldPassword, String newPassword) {
        return !oldPassword.equals(newPassword);
    }

    private boolean checkActualPasswordMatch(String enteredOldPassword, String oldPassword) {
        return passwordEncoder.matches(enteredOldPassword, oldPassword);
    }

}
