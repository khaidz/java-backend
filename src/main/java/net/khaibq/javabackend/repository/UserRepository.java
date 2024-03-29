package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
    User findByEmailIgnoreCase(String email);
}
