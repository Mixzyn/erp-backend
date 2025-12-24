package br.com.mixzyn.erp.service;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.dto.CreateUserDTO;
import br.com.mixzyn.erp.model.Role;
import br.com.mixzyn.erp.model.User;
import br.com.mixzyn.erp.repository.RoleRepository;
import br.com.mixzyn.erp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(CreateUserDTO userDto) {
        Role basicRole = this.findRoleByName(Role.Values.BASIC.name());
        User newUser = new User();

        newUser.setUsername(normalizeUsername(userDto.username()));
        newUser.setPassword(passwordEncoder.encode(userDto.password()));
        newUser.setRoles(Set.of(basicRole));

        return repository.save(newUser);
    }

    public boolean updateUser(User user) {
        User updatedUser = user;

        if (repository.existsById(user.getId())) {
            updatedUser.setUsername(normalizeUsername(user.getUsername()));
            updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(updatedUser);
            return true;
        }

        return false;
    }

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public boolean update(User user) {
        if (repository.existsById(user.getId())) {
            repository.save(user);
            return true;
        }
        return false;
    }

    public boolean delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> findUsersByUsername(String username) {
        return repository.findByUsernameContainingIgnoreCase(username);
    }

    public String normalizeUsername(String username) {
        return Normalizer.normalize(username, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9]", "")
                .toLowerCase(Locale.ROOT);
    }
}
