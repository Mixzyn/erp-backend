package br.com.mixzyn.erp.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.mixzyn.erp.model.Role;
import br.com.mixzyn.erp.model.User;
import br.com.mixzyn.erp.repository.RoleRepository;
import br.com.mixzyn.erp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, RoleRepository roleRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
    }

    public User create(User user) {
        User newUser = user;
        return repository.save(newUser);
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
}
