package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.rating.RatingDto;
import net.khaibq.javabackend.entity.Rating;
import net.khaibq.javabackend.repository.CustomRepository;
import net.khaibq.javabackend.repository.RatingRepository;
import net.khaibq.javabackend.service.RatingService;
import net.khaibq.javabackend.ultis.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final CustomRepository customRepository;

    @Override
    public void handleRating(Integer value) {
        String username = SecurityUtils.getCurrentUsername().get();
        Rating rating = ratingRepository.findByCreatedBy(username);
        if (rating == null){
            rating = new Rating();
        }
        rating.setValue(value);
        ratingRepository.save(rating);
    }

    @Override
    public RatingDto getDataRating() {
        return customRepository.getDataRating();
    }
}
