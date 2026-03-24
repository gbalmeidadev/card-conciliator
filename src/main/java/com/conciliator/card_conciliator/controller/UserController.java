package com.conciliator.card_conciliator.controller;

import com.conciliator.card_conciliator.dto.UserRequestDTO;
import com.conciliator.card_conciliator.dto.UserResponseDTO;
import com.conciliator.card_conciliator.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO findBYId(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }

    @PutMapping("/{id}/status")
    public void toggleStatus(@PathVariable Long id) {
            userService.toggleUserStatus(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
