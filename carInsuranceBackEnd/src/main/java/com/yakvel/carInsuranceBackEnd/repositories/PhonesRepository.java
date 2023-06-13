package com.yakvel.carInsuranceBackEnd.repositories;

import com.yakvel.carInsuranceBackEnd.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhonesRepository extends JpaRepository<Phone, Long> {
}
