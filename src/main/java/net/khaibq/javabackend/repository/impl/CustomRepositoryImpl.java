package net.khaibq.javabackend.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import net.khaibq.javabackend.repository.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {
    private final EntityManager entityManager;

    @Override
    public Long getSequence() {
        String sql = "SELECT nextval('seq_common')";
        Query query = entityManager.createNativeQuery(sql, Long.class);
        return Long.valueOf(query.getSingleResult().toString());
    }
}
