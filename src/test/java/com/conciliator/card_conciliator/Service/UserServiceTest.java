package com.conciliator.card_conciliator.Service;

import com.conciliator.card_conciliator.Repository.EnterpriseRepository;
import com.conciliator.card_conciliator.Repository.UserRepository;
import com.conciliator.card_conciliator.dto.UserRequestDTO;
import com.conciliator.card_conciliator.dto.UserResponseDTO;
import com.conciliator.card_conciliator.entity.Enterprise;
import com.conciliator.card_conciliator.entity.User;
import com.conciliator.card_conciliator.exception.EmailAlreadyExistsException;
import com.conciliator.card_conciliator.exception.UserNotFoundException;
import com.conciliator.card_conciliator.mapper.UserMapper;
import com.conciliator.card_conciliator.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EnterpriseRepository enterpriseRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    //Teste: criar usuário com sucesso
    @Test
    void shouldCreateUserSuccessfully() {

        UserRequestDTO dto = new UserRequestDTO("test@email.com", "123456", 1L);

        Enterprise enterprise = new Enterprise();
        enterprise.setId(1L);

        User user = User.builder()
                .email(dto.email())
                .password("encodedPassword")
                .enterprise(enterprise)
                .active(true)
                .build();

        User savedUser = User.builder()
                .id(1L)
                .email(dto.email())
                .password("encodedPassword")
                .enterprise(enterprise)
                .active(true)
                .build();

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                dto.email(),
                true,
                null,
                null
        );

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(enterpriseRepository.findById(1L)).thenReturn(Optional.of(enterprise));
        when(passwordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toResponseDTO(savedUser)).thenReturn(responseDTO);

        UserResponseDTO result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals(dto.email(), result.email());

        verify(userRepository).save(any(User.class));
    }

    //Teste: email duplicado
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        UserRequestDTO dto = new UserRequestDTO("test@email.com", "123456", 1L);

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.createUser(dto));

        verify(userRepository, never()).save(any());
    }

    //Teste: empresa não encontrada
    @Test
    void shouldThrowExceptionWhenEnterpriseNotFound() {

        UserRequestDTO dto = new UserRequestDTO("test@email.com", "123456", 1L);

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(enterpriseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.createUser(dto));
    }

    //Teste: buscar usuário por ID
    @Test
    void shouldReturnUserWhenFound() {

        User user = new User();
        user.setId(1L);
        user.setEmail("test@email.com");

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                user.getEmail(),
                true,
                null,
                null
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.findById(1L);

        assertEquals("test@email.com", result.email());
    }

    //Teste: usuário não encontrado
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.findById(1L));
    }

    //Teste: deletar usuário
    @Test
    void shouldDeleteUserSuccessfully() {

        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    //Teste: delete usuário inexistente
    @Test
    void shouldThrowExceptionWhenDeletingNonExistingUser() {

        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(1L));
    }
}
