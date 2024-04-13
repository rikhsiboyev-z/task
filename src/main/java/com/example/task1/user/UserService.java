package com.example.task1.user;

import com.example.task1.group.GroupRepository;
import com.example.task1.group.entity.Group;
import com.example.task1.smtp.EmailService;
import com.example.task1.smtp.EmailTemplateType;
import com.example.task1.user.dto.ForgotPassword;
import com.example.task1.user.dto.UserCreateDto;
import com.example.task1.user.dto.UserResponseDto;
import com.example.task1.user.entity.User;
import com.example.task1.user.role.Role;
import com.example.task1.user.role.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("user email not found"));
    }

    public UserResponseDto create(UserCreateDto userCreateDto) {

        Group group = groupRepository.
                findById(userCreateDto.getGroupId()).orElseThrow(() -> new EntityNotFoundException("group id not found"));

        User user = modelMapper.map(userCreateDto, User.class);
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        group.getUsers().add(user);
        user.getGroups().add(group);

        Role role = roleRepository.findByName(userCreateDto.getRole()).orElseThrow(() -> new EntityNotFoundException("role id not found"));

        user.setRole(role);

        userRepository.save(user);

        Context context = new Context();
        context.setVariable("message", "User created successfully");

        emailService.sendEmailWithTemplateType(user.getEmail(), user.getEmail(), EmailTemplateType.REGISTRATION, context);

        return modelMapper.map(user, UserResponseDto.class);


    }

    public Page<UserResponseDto> getAll(Pageable pageable) {

        return userRepository.findAll(pageable).map(user -> modelMapper.map(user, UserResponseDto.class));
    }

    public UserResponseDto forgotPassword(ForgotPassword forgotPassword) {

        User user = userRepository.findByEmail(forgotPassword.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User email not found"));

        user.setPassword(passwordEncoder.encode(forgotPassword.getPassword()));

        Context context = new Context();
        context.setVariable("message", "Your password is incorrect");

        emailService.sendEmailWithTemplateType(user.getEmail(), user.getEmail(), EmailTemplateType.PASSWORD, context);
        return modelMapper.map(user, UserResponseDto.class);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
