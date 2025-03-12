package service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.task.management.dto.RoleDto;
import com.task.management.entity.Role;
import com.task.management.mapper.RoleMapper;
import com.task.management.repository.RoleRepository;
import com.task.management.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    private static final long ROLE_ID = 1L;
    private static final String ROLE_NAME = "Admin";

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleService roleService;

    private RoleDto roleDto;
    private Role role;

    @BeforeEach
    void setUp() {
        roleDto = new RoleDto();
        roleDto.setName(ROLE_NAME);

        role = new Role();
        role.setId(ROLE_ID);
        role.setName(ROLE_NAME);
    }

    @Test
    @DisplayName("Create role successful")
    void testCreateRoleSuccessful() {
        // Given
        when(roleMapper.toEntity(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        // When
        RoleDto createdRole = roleService.createRole(roleDto);

        // Then
        verify(roleRepository).save(role);
        verify(roleMapper).toDto(role);

        assertNotNull(createdRole);
        assertEquals(ROLE_NAME, createdRole.getName());
    }

    @Test
    @DisplayName("Get role by ID successful")
    void testGetRoleByIdSuccessful() {
        // Given
        when(roleRepository.findById(ROLE_ID)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        // When
        RoleDto foundRole = roleService.getRoleById(ROLE_ID);

        // Then
        verify(roleRepository).findById(ROLE_ID);
        verify(roleMapper).toDto(role);

        assertNotNull(foundRole);
        assertEquals(ROLE_NAME, foundRole.getName());
    }

    @Test
    @DisplayName("Get role by ID not found")
    void testGetRoleByIdNotFound() {
        // Given
        when(roleRepository.findById(ROLE_ID)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            roleService.getRoleById(ROLE_ID);
        });

        assertEquals("Role not found", thrown.getMessage());
    }

    @Test
    @DisplayName("Get all roles successful")
    void testGetAllRolesSuccessful() {
        // Given
        List<Role> roles = List.of(role);
        when(roleRepository.findAll()).thenReturn(roles);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        // When
        List<RoleDto> allRoles = roleService.getAllRoles();

        // Then
        verify(roleRepository).findAll();
        verify(roleMapper).toDto(role);

        assertNotNull(allRoles);
        assertEquals(1, allRoles.size());
        assertEquals(ROLE_NAME, allRoles.get(0).getName());
    }

    @Test
    @DisplayName("Delete role by ID successful")
    void testDeleteByIdSuccessful() {
        // Given
        when(roleRepository.existsById(ROLE_ID)).thenReturn(true);

        // When
        roleService.deleteById(ROLE_ID);

        // Then
        verify(roleRepository).deleteById(ROLE_ID);
    }

    @Test
    @DisplayName("Delete role by ID not found")
    void testDeleteByIdNotFound() {
        // Given
        when(roleRepository.existsById(ROLE_ID)).thenReturn(false);

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            roleService.deleteById(ROLE_ID);
        });

        assertEquals("Role not found with ID" + ROLE_ID, thrown.getMessage());
    }

    @Test
    @DisplayName("Update role successful")
    void testUpdateRoleSuccessful() {
        // Given
        RoleDto updatedRoleDto = new RoleDto();
        updatedRoleDto.setName("Super Admin");

        when(roleRepository.findById(ROLE_ID)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(updatedRoleDto);
        when(roleRepository.save(role)).thenReturn(role);

        // When
        RoleDto updatedRole = roleService.updateRole(ROLE_ID, updatedRoleDto);

        // Then
        verify(roleRepository).save(role);
        verify(roleMapper).toDto(role);

        assertNotNull(updatedRole);
        assertEquals("Super Admin", updatedRole.getName());
    }

}
