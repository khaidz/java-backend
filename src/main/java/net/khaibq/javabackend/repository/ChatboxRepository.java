package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.Chatbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatboxRepository extends JpaRepository<Chatbox, Long> {
}
