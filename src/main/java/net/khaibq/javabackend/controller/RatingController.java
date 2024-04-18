package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.rating.RatingDto;
import net.khaibq.javabackend.dto.rating.RatingRequestDto;
import net.khaibq.javabackend.service.RatingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public BaseResponse<RatingDto> getDataRating() {
        return BaseResponse.success(ratingService.getDataRating());
    }

    @PostMapping
    public BaseResponse<RatingDto> handleRating(@RequestBody @Valid RatingRequestDto dto) {
        ratingService.handleRating(dto.getValue());

        return BaseResponse.success(ratingService.getDataRating());
    }

}
