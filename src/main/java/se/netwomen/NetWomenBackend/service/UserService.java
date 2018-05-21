package se.netwomen.NetWomenBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.model.data.User;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User save(User user) {
        return repository.save(user);
    }

    public Optional<User> findByUserNameAndPassword(String userName, String password) {
        return repository.findByUserNameAndPassword(userName, password);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
}
