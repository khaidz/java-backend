package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsername(String username);
}
