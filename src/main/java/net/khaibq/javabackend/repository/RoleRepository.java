package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
