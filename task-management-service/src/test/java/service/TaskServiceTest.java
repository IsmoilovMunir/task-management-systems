package service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.task.management.dto.CommentDto;
import com.task.management.dto.TaskDto;
import com.task.management.entity.Comment;
import com.task.management.entity.Task;
import com.task.management.mapper.CommentMapper;
import com.task.management.mapper.TaskMapper;
import com.task.management.mapper.TaskMapperHelper;
import com.task.management.repository.CommentRepository;
import com.task.management.repository.TaskRepository;
import com.task.management.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    private static final long TASK_ID = 1L;
    private static final String TASK_TITLE = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";
    private static final long AUTHOR_ID = 1L;
    private static final long ASSIGNEE_ID = 2L;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private TaskMapperHelper taskMapperHelper;

    @InjectMocks
    private TaskService taskService;

    private TaskDto taskDto;
    private Task task;

    @BeforeEach
    void setUp() {
        taskDto = new TaskDto();
        taskDto.setTitle(TASK_TITLE);
        taskDto.setDescription(TASK_DESCRIPTION);
        taskDto.setAuthorId(AUTHOR_ID);
        taskDto.setAssigneeId(ASSIGNEE_ID);

        task = new Task();
        task.setId(TASK_ID);
        task.setTitle(TASK_TITLE);
        task.setDescription(TASK_DESCRIPTION);

    }

    @Test
    @DisplayName("Create task successful")
    void testCreateTaskSuccessful() {
        // Given
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        // When
        TaskDto createdTask = taskService.createTask(taskDto);

        // Then
        verify(taskRepository).save(task);
        verify(taskMapper).toDto(task);

        assertNotNull(createdTask);
        assertEquals(TASK_TITLE, createdTask.getTitle());
        assertEquals(TASK_DESCRIPTION, createdTask.getDescription());
    }

    @Test
    @DisplayName("Get task by ID successful")
    void testGetTaskByIdSuccessful() {
        // Given
        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        // When
        TaskDto foundTask = taskService.getTaskById(TASK_ID);

        // Then
        verify(taskRepository).findById(TASK_ID);
        verify(taskMapper).toDto(task);

        assertNotNull(foundTask);
        assertEquals(TASK_TITLE, foundTask.getTitle());
        assertEquals(TASK_DESCRIPTION, foundTask.getDescription());
    }



    @Test
    @DisplayName("Get all tasks successful")
    void testGetAllTasksSuccessful() {
        // Given
        List<Task> tasks = List.of(task);
        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        // When
        List<TaskDto> allTasks = taskService.getAllTasks();

        // Then
        verify(taskRepository).findAll();
        verify(taskMapper).toDto(task);

        assertNotNull(allTasks);
        assertEquals(1, allTasks.size());
        assertEquals(TASK_TITLE, allTasks.get(0).getTitle());
        assertEquals(TASK_DESCRIPTION, allTasks.get(0).getDescription());
    }

    @Test
    @DisplayName("Update task successful")
    void testUpdateTaskSuccessful() {
        // Given
        TaskDto updatedTaskDto = new TaskDto();
        updatedTaskDto.setTitle("Updated Task");
        updatedTaskDto.setDescription("Updated Description");
        updatedTaskDto.setAuthorId(AUTHOR_ID);
        updatedTaskDto.setAssigneeId(ASSIGNEE_ID);

        when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(updatedTaskDto);

        // When
        TaskDto updatedTask = taskService.updateTask(TASK_ID, updatedTaskDto);

        // Then
        verify(taskRepository).save(any(Task.class));
        verify(taskMapper).toDto(task);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getTitle());
        assertEquals("Updated Description", updatedTask.getDescription());
    }



    @Test
    @DisplayName("Get comments by task ID successful")
    void testGetCommentsByTaskSuccessful() {
        // Given
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Test Comment");
        List<CommentDto> commentDtos = List.of(commentDto);
        when(commentRepository.findByTaskId(TASK_ID)).thenReturn(List.of(new Comment()));
        when(commentMapper.toDto(any(Comment.class))).thenReturn(commentDto);

        // When
        List<CommentDto> comments = taskService.getCommentsByTask(TASK_ID);

        // Then
        verify(commentRepository).findByTaskId(TASK_ID);
        verify(commentMapper).toDto(any(Comment.class));

        assertNotNull(comments);
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.get(0).getText());
    }
}