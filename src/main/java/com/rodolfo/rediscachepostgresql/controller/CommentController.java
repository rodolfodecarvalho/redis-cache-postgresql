package com.rodolfo.rediscachepostgresql.controller;

import com.rodolfo.rediscachepostgresql.dto.request.CommentRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.CommentResponseDto;
import com.rodolfo.rediscachepostgresql.services.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByProductId(@PathVariable UUID productId) {
        log.debug("GET /api/comments/product/{} - Fetching comments by product id", productId);
        List<CommentResponseDto> comments = commentService.getCommentsByProductId(productId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<CommentResponseDto> addCommentToProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody CommentRequestDto request) {
        log.debug("POST /api/comments/product/{} - Adding comment to product", productId);
        CommentResponseDto createdComment = commentService.addCommentToProduct(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable UUID commentId,
            @Valid @RequestBody CommentRequestDto request) {
        log.debug("PUT /api/comments/{} - Updating comment", commentId);
        CommentResponseDto updatedComment = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        log.debug("DELETE /api/comments/{} - Deleting comment", commentId);
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}