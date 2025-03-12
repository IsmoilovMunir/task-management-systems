package com.task.management.controller;

import com.task.management.dto.CommentDto;
import com.task.management.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/comments")
@Tag(name = "Comment Management", description = "Operations related to comment management")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Create a new comment", description = "Creates a new comment for a given entity")
    @ApiResponse(responseCode = "201", description = "Comment successfully created")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto) {
        CommentDto createComment = commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createComment);
    }

    @Operation(summary = "Get comment by ID", description = "Retrieves a specific comment by its ID")
    @ApiResponse(responseCode = "200", description = "Comment successfully retrieved")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long id) {
        CommentDto commentDto = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDto);
    }

    @Operation(summary = "Get all comments", description = "Retrieves a list of all comments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of comments")
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> commentDto = commentService.getAllComment();
        return ResponseEntity.ok(commentDto);
    }

    @Operation(summary = "Delete comment by ID", description = "Deletes a specific comment by its ID")
    @ApiResponse(responseCode = "204", description = "Comment successfully deleted")
    @ApiResponse(responseCode = "404", description = "Comment not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            commentService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @Operation(summary = "Update comment", description = "Updates an existing comment by ID")
    @ApiResponse(responseCode = "200", description = "Comment successfully updated")
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        CommentDto updateComment = commentService.updateComment(id, commentDto);
        return ResponseEntity.ok(updateComment);
    }
}