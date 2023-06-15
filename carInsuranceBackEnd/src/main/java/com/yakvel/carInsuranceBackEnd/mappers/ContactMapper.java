package com.yakvel.carInsuranceBackEnd.mappers;

import com.yakvel.carInsuranceBackEnd.controllers.user.service.ContactDto;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper implements ItemMapper<ContactDto, Contact>{
    @Override
    public ContactDto toDto(Contact contact) {
        return null;
    }

    @Override
    public Contact toEntity(ContactDto request) {
        return Contact.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phones(request.getPhones())
                .addresses(request.getAddresses())
                .notes(request.getNotes())
                .build();
    }
}
