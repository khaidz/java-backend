package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles", "department"})
    User findByUsernameIgnoreCase(String username);

    @EntityGraph(attributePaths = {"roles", "department"})
    Page<User> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"roles", "department.name"})
    Optional<User> findById(Long id);
}
