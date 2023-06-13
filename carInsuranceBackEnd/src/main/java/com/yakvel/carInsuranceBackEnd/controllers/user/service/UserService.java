package com.yakvel.carInsuranceBackEnd.controllers.user.service;


import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    public PersonDetailsResponse getPersonDetailsResponse(Person person, Contact contactInfo) {
        if (contactInfo != null) {
            return PersonDetailsResponse.builder()
                    .email(person.getEmail())
                    .firstName(contactInfo.getFirstName())
                    .lastName(contactInfo.getLastName())
                    .phones(contactInfo.getPhones())
                    .addresses(contactInfo.getAddresses())
                    .notes(contactInfo.getNotes())
                    .build();
        }
        return PersonDetailsResponse
                .builder()
                .email(person.getEmail())
                .build();
    }

    public Contact buildContactFromRequest(PersonContactRequest request) {
        return Contact
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phones(request.getPhones())
                .addresses(request.getAddresses())
                .notes(request.getNotes())
                .build();
    }
}

