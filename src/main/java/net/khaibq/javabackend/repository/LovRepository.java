package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.Lov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LovRepository extends JpaRepository<Lov, Long> {
    @Query(value = "SELECT nextval('seq_lov')", nativeQuery = true)
    Long getSequence();
}
