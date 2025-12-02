package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import lab4.backend.data.repositories.user.postgres.PostgresUserRepository;
import lab4.backend.dto.UserDTO;
import lab4.backend.utils.mapping.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class UserService {
    @EJB
    private PostgresUserRepository postgresUserRepository;

    public UserDTO createUser(UserDTO userDTO) {
        return UserMapper.entityToDTO(
                postgresUserRepository.createUser(UserMapper.dtoToEntity(userDTO)));
    }

    public UserDTO getUserByName(String name) {
        return UserMapper.entityToDTO(
                postgresUserRepository.findUserByName(name));
    }

    public List<UserDTO> getAllUsers() {
        return postgresUserRepository.getAllUsers().stream()
                .map(UserMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
