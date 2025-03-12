package com.task.management.service;

import com.task.management.dto.UserDto;
import com.task.management.entity.Role;
import com.task.management.entity.User;
import com.task.management.mapper.UserMapper;
import com.task.management.repository.RoleRepository;
import com.task.management.repository.UserRepository;
import com.task.management.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final Validation roleValidation;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public UserDto createUser(UserDto userDto) {
        Role role = roleValidation.findByIdRole(userDto.getRoleId());
        User user = userMapper.toEntityWithRole(userDto, role);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public UserDto getUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(""));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(long id){
        if (!userRepository.existsById(id)){
            throw new RuntimeException("User not found with ID"+id);
        }
         userRepository.deleteById(id);
    }

    public UserDto updateUser(Long id, UserDto userDto){
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException(""+id));
        Role role = roleRepository.findById(userDto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userDto.getRoleId()));
        userMapper.updateUserFromDto(userDto, user);
        user.setRole(role);

        User update = userRepository.save(user);
        return userMapper.toDto(update);
    }

}
