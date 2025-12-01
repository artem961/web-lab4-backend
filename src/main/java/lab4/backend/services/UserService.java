package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import lab4.backend.data.repositories.user.postgres.PostgresUserRepository;
import lab4.backend.dto.UserDTO;

import java.util.List;

@Singleton
public class UserService {
    @EJB
    private PostgresUserRepository postgresUserRepository;

    public UserDTO createUser(UserDTO userDTO) {
        return postgresUserRepository.createUser(userDTO);
    }

    public UserDTO getUserByName(String name) {
        return postgresUserRepository.getUserByName(name);
    }

    public List<UserDTO> getAllUsers() {
        return postgresUserRepository.getAllUsers();
    }
}
