package lab4.backend.data.repositories.user.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lab4.backend.data.entities.UserEntity;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.exceptions.DatabaseException;
import lab4.backend.utils.mapping.UserMapper;
import lombok.extern.java.Log;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Singleton
@Log
public class StandartPostgresUserRepository implements PostgresUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public UserEntity createUser(UserEntity user) {
        em.persist(user);
        em.flush();
        return user;
    }

    @Override
    public Optional<UserEntity> findUserByName(String name) {
        try {
            UserEntity userEntity = em
                    .createQuery("select u from UserEntity u where u.username=:username", UserEntity.class)
                    .setParameter("username", name)
                    .getSingleResult();
            return Optional.ofNullable(userEntity);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> entities = em.createQuery("select u from UserEntity u", UserEntity.class).getResultList();
        return entities;
    }
}
