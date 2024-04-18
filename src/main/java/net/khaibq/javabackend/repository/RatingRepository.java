package net.khaibq.javabackend.repository;

import net.khaibq.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByCreatedBy(String username);
}
