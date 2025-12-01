package lab4.backend.data.repositories.user.postgres;

import lab4.backend.dto.UserDTO;

import java.util.List;

public interface PostgresUserRepository {
    public UserDTO createUser(UserDTO userDTO);
    public UserDTO getUserByName(String name);
    public List<UserDTO> getAllUsers();
}
