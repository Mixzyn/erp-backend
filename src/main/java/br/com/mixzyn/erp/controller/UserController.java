package br.com.mixzyn.erp.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.mixzyn.erp.dto.CreateUserDTO;
import br.com.mixzyn.erp.model.User;
import br.com.mixzyn.erp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @PostMapping
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDTO userDto) {
        Optional<User> user = service.findByUsername(userDto.username());

        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        service.create(userDto);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Void> editUser(@PathVariable UUID id, @RequestBody CreateUserDTO userDto) {
        User updatedUser = service.findById(id).get();

        if (userDto.username() != null) {
            updatedUser.setUsername(userDto.username());
        }

        if (userDto.password() != null) {
            updatedUser.setPassword(userDto.password());
        }

        if (service.updateUser(updatedUser)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
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

    @GetMapping("/search")
    public List<User> getByUsername(@RequestParam String username) {
        return service.findUsersByUsername(username);
    }
}
