package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.rating.RatingDto;

public interface RatingService {
    void handleRating(Integer value);

    RatingDto getDataRating();
}
