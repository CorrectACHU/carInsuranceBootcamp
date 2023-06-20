package com.yakvel.carInsuranceBackEnd.repositories;

import com.yakvel.carInsuranceBackEnd.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Id> {
    Optional<Comment> findById(long commentId);
}
