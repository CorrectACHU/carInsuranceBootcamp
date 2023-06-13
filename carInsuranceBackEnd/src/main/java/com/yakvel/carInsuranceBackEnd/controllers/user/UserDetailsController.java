package com.yakvel.carInsuranceBackEnd.controllers.user;


import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonChangePassRequest;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonContactRequest;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonDetailsResponse;
import com.yakvel.carInsuranceBackEnd.controllers.user.service.UserService;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserDetailsController {
    @Autowired
    private UserService userService;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<PersonDetailsResponse> currentUserDetails(HttpServletRequest request) {
        Person person = getPerson();
        Contact contactInfo = person.getContactInfo();
        return ResponseEntity.ok(userService.getPersonDetailsResponse(person, contactInfo));
    }

    @PatchMapping("/profile")
    public ResponseEntity<String> updateUserContact(@RequestBody PersonContactRequest request) {
        Person person = getPerson();
        Contact contact = userService.buildContactFromRequest(request);
        person.setContactInfo(contact);
        personRepository.save(person);
        return ResponseEntity.ok("Contact information was updated!");
    }


    @PatchMapping("/profile/change-pass")
    public ResponseEntity changePersonPassword(@RequestBody PersonChangePassRequest request) {
        Person person = getPerson();
        if (!request.getFirstPassword().equals(request.getSecondPassword())) {
            return ResponseEntity.badRequest().body("Passwords are not equal");
        }
        person.setPassword(passwordEncoder.encode(request.getFirstPassword()));
        personRepository.save(person);
        return ResponseEntity.ok("Password was changed");
    }


    private Person getPerson() {
        return (Person) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }
}
