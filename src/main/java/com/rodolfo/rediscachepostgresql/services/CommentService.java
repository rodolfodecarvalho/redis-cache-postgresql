package com.rodolfo.rediscachepostgresql.services;

import com.rodolfo.rediscachepostgresql.dto.request.CommentRequestDto;
import com.rodolfo.rediscachepostgresql.dto.response.CommentResponseDto;
import com.rodolfo.rediscachepostgresql.exeptions.RecordNotFoundException;
import com.rodolfo.rediscachepostgresql.mapper.CommentMapper;
import com.rodolfo.rediscachepostgresql.model.Comment;
import com.rodolfo.rediscachepostgresql.model.Product;
import com.rodolfo.rediscachepostgresql.repository.CommentRepository;
import com.rodolfo.rediscachepostgresql.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CommentMapper commentMapper;

    @Cacheable(value = "comments", key = "#productId")
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByProductId(UUID productId) {
        log.debug("Fetching comments for product id: {}", productId);

        // Verificar se o produto existe
        if (!productRepository.existsById(productId)) {
            throw new RecordNotFoundException("Product", productId.toString());
        }

        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }

    @CacheEvict(value = {"products", "productsByCode", "comments"}, allEntries = true)
    public CommentResponseDto addCommentToProduct(UUID productId, CommentRequestDto request) {
        log.debug("Adding comment to product id: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RecordNotFoundException("Product", productId.toString()));

        Comment comment = commentMapper.toEntity(request);
        comment.setProduct(product);
        Comment savedComment = commentRepository.save(comment);

        log.info("Comment created with id: {} for product: {}", savedComment.getId(), productId);
        return commentMapper.toResponseDto(savedComment);
    }

    @CacheEvict(value = {"products", "productsByCode", "comments"}, allEntries = true)
    public CommentResponseDto updateComment(UUID commentId, CommentRequestDto request) {
        log.debug("Updating comment with id: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RecordNotFoundException("Comment", commentId.toString()));

        commentMapper.updateEntity(comment, request);
        Comment updatedComment = commentRepository.save(comment);

        log.info("Comment updated with id: {}", updatedComment.getId());
        return commentMapper.toResponseDto(updatedComment);
    }

    @CacheEvict(value = {"products", "productsByCode", "comments"}, allEntries = true)
    public void deleteComment(UUID commentId) {
        log.debug("Deleting comment with id: {}", commentId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RecordNotFoundException("Comment", commentId.toString()));

        commentRepository.delete(comment);
        log.info("Comment deleted with id: {}", commentId);
    }
}