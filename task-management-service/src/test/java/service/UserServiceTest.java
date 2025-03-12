package service;

import com.task.management.dto.UserDto;
import com.task.management.entity.Role;
import com.task.management.entity.User;
import com.task.management.mapper.UserMapper;
import com.task.management.repository.RoleRepository;
import com.task.management.repository.UserRepository;
import com.task.management.service.UserService;
import com.task.management.validation.Validation;
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
class UserServiceTest {

    private static final long USER_ID = 1L;
    private static final String USER_EMAIL = "test@example.com";
    private static final long ROLE_ID = 1L;
    private static final String USER_NAME = "Test User";

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Validation roleValidation;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(ROLE_ID);
        role.setName("USER");

        userDto = new UserDto();
        userDto.setEmail(USER_EMAIL);
        userDto.setRoleId(ROLE_ID);

        user = new User();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setRole(role);

    }

    @Test
    @DisplayName("Create user successful")
    void testCreateUserSuccessful() {
        // Given
        when(roleValidation.findByIdRole(ROLE_ID)).thenReturn(role);
        when(userMapper.toEntityWithRole(userDto, role)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto createdUser = userService.createUser(userDto);

        // Then
        verify(userRepository).save(any(User.class));
        verify(userMapper).toDto(user);

        assertNotNull(createdUser);
        assertEquals(USER_EMAIL, createdUser.getEmail());
        assertEquals(ROLE_ID, createdUser.getRoleId());
    }

    @Test
    @DisplayName("Get user by ID successful")
    void testGetUserByIdSuccessful() {
        // Given
        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        UserDto foundUser = userService.getUserById(USER_ID);

        // Then
        verify(userRepository).findById(USER_ID);
        verify(userMapper).toDto(user);

        assertNotNull(foundUser);
        assertEquals(USER_EMAIL, foundUser.getEmail());
    }

    @Test
    @DisplayName("Get all users successful")
    void testGetAllUsersSuccessful() {
        // Given
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // When
        List<UserDto> allUsers = userService.getAllUser();

        // Then
        verify(userRepository).findAll();
        verify(userMapper).toDto(user);

        assertNotNull(allUsers);
        assertEquals(1, allUsers.size());
        assertEquals(USER_EMAIL, allUsers.get(0).getEmail());

    }

    @Test
    @DisplayName("Delete user successful")
    void testDeleteUserSuccessful() {
        // Given
        when(userRepository.existsById(USER_ID)).thenReturn(true);
        doNothing().when(userRepository).deleteById(USER_ID);

        // When
        userService.deleteById(USER_ID);

        // Then
        verify(userRepository).deleteById(USER_ID);
    }

    @Test
    @DisplayName("Delete user not found")
    void testDeleteUserNotFound() {
        // Given
        when(userRepository.existsById(USER_ID)).thenReturn(false);

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.deleteById(USER_ID);
        });

        assertEquals("User not found with ID" + USER_ID, thrown.getMessage());
    }

    @Test
    @DisplayName("Update user successful")
    void testUpdateUserSuccessful() {
        // Given
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("updated@example.com");
        updatedUserDto.setRoleId(ROLE_ID);

        Role updatedRole = new Role();
        updatedRole.setId(ROLE_ID);
        updatedRole.setName("USER");

        when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));
        when(roleRepository.findById(ROLE_ID)).thenReturn(Optional.of(updatedRole));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(updatedUserDto);

        // When
        UserDto updatedUser = userService.updateUser(USER_ID, updatedUserDto);

        // Then
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);

        assertNotNull(updatedUser);
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    @DisplayName("Update user not found")
    void testUpdateUserNotFound() {
        // Given
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setEmail("updated@example.com");
        updatedUserDto.setRoleId(ROLE_ID);

        when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(USER_ID, updatedUserDto);
        });

        assertEquals("1", thrown.getMessage());
    }
}