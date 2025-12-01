package lab4.backend.data.repositories.user.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.UserEntity;
import lab4.backend.dto.UserDTO;
import lab4.backend.utils.mapping.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class StandartPostgresUserRepository implements PostgresUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.dtoToEntity(userDTO);
        em.persist(userEntity);
        return UserMapper.entityToDTO(userEntity);
    }

    @Override
    public UserDTO getUserByName(String name) {
        UserEntity userEntity = em
                .createQuery("select u from UserEntity u where u.username=:username", UserEntity.class)
                .setParameter("username", name)
                .getSingleResult();

        return UserMapper.entityToDTO(userEntity);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserEntity> entities = em.createQuery("select u from UserEntity u", UserEntity.class).getResultList();

        return entities.stream()
                .map(UserMapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
