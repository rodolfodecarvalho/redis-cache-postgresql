package com.rodolfo.rediscachepostgresql.repository;

import com.rodolfo.rediscachepostgresql.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByProductId(@Param("productId") UUID productId);
}
