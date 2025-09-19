package br.com.mixzyn.erp.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.mixzyn.erp.dto.CreateUserDTO;
import br.com.mixzyn.erp.model.Role;
import br.com.mixzyn.erp.model.User;
import br.com.mixzyn.erp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService service;

    public UserController(UserService service, BCryptPasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDTO userDto) {
        Role basicRole = service.findRoleByName(Role.Values.BASIC.name());
        Optional<User> user = service.findByUsername(userDto.username());

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User newUser = new User();
        newUser.setUsername(userDto.username());
        newUser.setPassword(passwordEncoder.encode(userDto.password()));
        newUser.setRoles(Set.of(basicRole));

        service.create(newUser);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = service.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
