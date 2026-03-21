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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EnterpriseRepository enterpriserepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser (UserRequestDTO dto) {
        validateEmail(dto.email());

        Enterprise enterprise = enterpriserepository.findById(dto.enterpriseId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        User user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .enterprise(enterprise)
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO findById(Long id) {
        User user = getUserOrThrow(id);
        return userMapper.toResponseDTO(user);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {

        User user = getUserOrThrow(id);

        if (!user.getEmail().equals(dto.email())) {
            validateEmail(dto.email());
        }

        user.setEmail(dto.email());

        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    public void toggleUserStatus(Long id) {
        User user = getUserOrThrow(id);
        user.setActive(!user.getActive());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
