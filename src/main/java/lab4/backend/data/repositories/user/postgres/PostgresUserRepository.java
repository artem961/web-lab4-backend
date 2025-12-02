package lab4.backend.data.repositories.user.postgres;

import lab4.backend.data.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface PostgresUserRepository {
    UserEntity createUser(UserEntity user);
    Optional<UserEntity> findUserByName(String name);
    List<UserEntity> getAllUsers();
}
