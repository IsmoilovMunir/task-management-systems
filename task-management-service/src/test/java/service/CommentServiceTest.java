package service;

import com.task.management.dto.CommentDto;
import com.task.management.entity.Comment;
import com.task.management.mapper.CommentMapper;
import com.task.management.repository.CommentRepository;
import com.task.management.repository.TaskRepository;
import com.task.management.repository.UserRepository;
import com.task.management.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private static final long COMMENT_ID = 1L;
    private static final long USER_ID = 1L;
    private static final String COMMENT_TEXT = "Test Comment";

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private CommentDto commentDto;

    @BeforeEach
    void setUp() {
        commentDto = new CommentDto();
        commentDto.setText(COMMENT_TEXT);

        comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setText(COMMENT_TEXT);
    }

    @Test
    @DisplayName("Create comment successful")
    void testCreateCommentSuccessful() {
        // Given
        when(commentMapper.toEntity(any(CommentDto.class))).thenReturn(comment);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        // When
        CommentDto createdComment = commentService.createComment(commentDto);

        // Then
        verify(commentRepository).save(any(Comment.class));
        verify(commentMapper).toDto(comment);

        assertNotNull(createdComment);
        assertEquals(COMMENT_TEXT, createdComment.getText());
    }

    @Test
    @DisplayName("Get comment by ID successful")
    void testGetCommentByIdSuccessful() {
        // Given
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        // When
        CommentDto foundComment = commentService.getCommentById(COMMENT_ID);

        // Then
        verify(commentRepository).findById(COMMENT_ID);
        verify(commentMapper).toDto(comment);

        assertNotNull(foundComment);
        assertEquals(COMMENT_TEXT, foundComment.getText());
    }

    @Test
    @DisplayName("Get comment by ID not found")
    void testGetCommentByIdNotFound() {
        // Given
        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(COMMENT_ID);
        });

        assertEquals("Comment not found", thrown.getMessage());
    }

    @Test
    @DisplayName("Get all comments successful")
    void testGetAllCommentsSuccessful() {
        // Given
        List<Comment> comments = List.of(comment);
        when(commentRepository.findAll()).thenReturn(comments);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        // When
        List<CommentDto> allComments = commentService.getAllComment();

        // Then
        verify(commentRepository).findAll();
        verify(commentMapper).toDto(comment);

        assertNotNull(allComments);
        assertEquals(1, allComments.size());
        assertEquals(COMMENT_TEXT, allComments.get(0).getText());
    }

    @Test
    @DisplayName("Delete comment successful")
    void testDeleteCommentSuccessful() {
        // Given
        when(commentRepository.existsById(COMMENT_ID)).thenReturn(true);
        doNothing().when(commentRepository).deleteById(COMMENT_ID);

        // When
        commentService.deleteById(COMMENT_ID);

        // Then
        verify(commentRepository).deleteById(COMMENT_ID);
    }

    @Test
    @DisplayName("Delete comment not found")
    void testDeleteCommentNotFound() {
        // Given
        when(commentRepository.existsById(COMMENT_ID)).thenReturn(false);

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.deleteById(COMMENT_ID);
        });

        assertEquals("Comment not fount with ID1", thrown.getMessage());
    }

    @Test
    @DisplayName("Update comment successful")
    void testUpdateCommentSuccessful() {
        // Given
        CommentDto updatedDto = new CommentDto();
        updatedDto.setText("Updated Comment");

        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(commentMapper.toDto(any(Comment.class))).thenReturn(updatedDto);

        // When
        CommentDto updatedComment = commentService.updateComment(COMMENT_ID, updatedDto);

        // Then
        verify(commentRepository).save(any(Comment.class));
        verify(commentMapper).toDto(comment);

        assertNotNull(updatedComment);
        assertEquals("Updated Comment", updatedComment.getText());
    }

    @Test
    @DisplayName("Update comment not found")
    void testUpdateCommentNotFound() {
        // Given
        CommentDto updatedDto = new CommentDto();
        updatedDto.setText("Updated Comment");

        when(commentRepository.findById(COMMENT_ID)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            commentService.updateComment(COMMENT_ID, updatedDto);
        });

        assertEquals("Comment not fount with ID1", thrown.getMessage());
    }
}