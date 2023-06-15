package com.yakvel.carInsuranceBackEnd.mappers;

import com.yakvel.carInsuranceBackEnd.controllers.user.service.PersonDto;
import com.yakvel.carInsuranceBackEnd.models.Contact;
import com.yakvel.carInsuranceBackEnd.models.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper implements ItemMapper<PersonDto,Person>{

    @Override
    public Person toEntity(PersonDto dto) {
        return null;
    }

    @Override
    public PersonDto toDto(Person person) {
        Contact contactInfo = person.getContactInfo();
        if (contactInfo != null) {
            return PersonDto.builder()
                    .email(person.getEmail())
                    .insuranceCompany(person.getInsuranceCompany())
                    .firstName(contactInfo.getFirstName())
                    .lastName(contactInfo.getLastName())
                    .phones(contactInfo.getPhones())
                    .addresses(contactInfo.getAddresses())
                    .notes(contactInfo.getNotes())
                    .build();
        }
        return PersonDto
                .builder()
                .email(person.getEmail())
                .build();
    }
}
