package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import lab4.backend.data.entities.UserEntity;
import lab4.backend.data.repositories.user.postgres.PostgresUserRepository;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.exceptions.ServiceException;
import lab4.backend.services.utils.annotations.ExceptionMessage;
import lab4.backend.services.utils.annotations.WrapWithServiceException;
import lab4.backend.utils.mapping.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
@WrapWithServiceException
@ExceptionMessage
public class UserService {
    @EJB
    private PostgresUserRepository postgresUserRepository;

    public UserDTO createUser(UserDTO userDTO) {
        return UserMapper.entityToDTO(
                postgresUserRepository.createUser(UserMapper.dtoToEntity(userDTO)));

    }

    public UserDTO findUserByName(String name) {
        UserEntity userEntity = postgresUserRepository.findUserByName(name)
                .orElseThrow(() -> new ServiceException(String.format("User with name %s not found", name)));
        return UserMapper.entityToDTO(userEntity);
    }

    public List<UserDTO> getAllUsers() {
        return postgresUserRepository.getAllUsers().stream()
                .map(UserMapper::entityToDTO)
                .collect(Collectors.toList());

    }
}
