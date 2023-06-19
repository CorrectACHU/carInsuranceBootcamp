package com.yakvel.carInsuranceBackEnd.mappers;

import com.yakvel.carInsuranceBackEnd.controllers.manager.dto.CommentDto;
import com.yakvel.carInsuranceBackEnd.models.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements ItemMapper<CommentDto, Comment> {
    @Override
    public Comment toEntity(CommentDto commentDto) {
        return Comment
                .builder()
                .body(commentDto.getBody())
                .build();
    }

    @Override
    public CommentDto toDto(Comment comment) {
        return null;
    }
}
