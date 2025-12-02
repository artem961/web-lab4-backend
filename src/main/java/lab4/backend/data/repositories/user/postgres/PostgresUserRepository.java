package lab4.backend.data.repositories.user.postgres;

import lab4.backend.data.entities.UserEntity;
import lab4.backend.dto.UserDTO;

import java.util.List;

public interface PostgresUserRepository {
    UserEntity createUser(UserEntity user);
    UserEntity findUserByName(String name);
    List<UserEntity> getAllUsers();
}
