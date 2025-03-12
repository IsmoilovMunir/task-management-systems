package com.task.management.service;

import com.task.management.dto.CommentDto;
import com.task.management.entity.Comment;
import com.task.management.entity.Task;
import com.task.management.entity.User;
import com.task.management.mapper.CommentMapper;
import com.task.management.repository.CommentRepository;
import com.task.management.repository.TaskRepository;
import com.task.management.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.toEntity(commentDto);
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Comment not found"));
        return commentMapper.toDto(comment);
    }

    public List<CommentDto> getAllComment() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment not fount with ID" + id);
        }
        commentRepository.deleteById(id);
    }

    public CommentDto updateComment(Long id, CommentDto commentDto) {

        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Comment not fount with ID" + id));

        comment.setText(commentDto.getText());
       // commentMapper.updateUserFromDto(commentDto, comment);
        Comment update = commentRepository.save(comment);
        return commentMapper.toDto(update);
    }

}
