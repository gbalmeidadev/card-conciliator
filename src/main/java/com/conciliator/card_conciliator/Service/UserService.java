package com.conciliator.card_conciliator.Service;

import com.conciliator.card_conciliator.Repository.EnterpriseRepository;
import com.conciliator.card_conciliator.Repository.UserRepository;
import com.conciliator.card_conciliator.dto.UserRequestDTO;
import com.conciliator.card_conciliator.dto.UserResponseDTO;
import com.conciliator.card_conciliator.entity.Enterprise;
import com.conciliator.card_conciliator.entity.User;
import com.conciliator.card_conciliator.exception.EmailAlreadyExistsException;
import com.conciliator.card_conciliator.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }
}
