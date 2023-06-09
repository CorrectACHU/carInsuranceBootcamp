package com.yakvel.carInsuranceBackEnd.repositories;

import com.yakvel.carInsuranceBackEnd.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
