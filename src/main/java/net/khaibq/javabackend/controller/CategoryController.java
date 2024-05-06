package net.khaibq.javabackend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.BaseResponse;
import net.khaibq.javabackend.dto.category.CategoryDto;
import net.khaibq.javabackend.dto.category.CreateCategoryDto;
import net.khaibq.javabackend.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public BaseResponse<List<CategoryDto>> getListCategory() {
        return BaseResponse.success(categoryService.getListCategory());
    }

    @PostMapping
    public BaseResponse<CategoryDto> createCategory(@RequestBody @Valid CreateCategoryDto dto) {

        return BaseResponse.success(categoryService.createCategory(dto));
    }
}
