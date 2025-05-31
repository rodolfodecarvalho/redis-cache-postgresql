package com.rodolfo.rediscachepostgresql.mapper;

import com.rodolfo.rediscachepostgresql.dto.request.CommentRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.CommentResponseDto;
import com.rodolfo.rediscachepostgresql.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequestDto dto) {
        return Comment.builder()
                .content(dto.content())
                .build();
    }

    public CommentResponseDto toResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent()
        );
    }

    public void updateEntity(Comment comment, CommentRequestDto dto) {
        comment.setContent(dto.content());
    }
}