package net.khaibq.javabackend.service;


import net.khaibq.javabackend.dto.category.CategoryDto;
import net.khaibq.javabackend.dto.category.CreateCategoryDto;
import net.khaibq.javabackend.dto.category.UpdateCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getListCategory();
    CategoryDto createCategory(CreateCategoryDto dto);

    CategoryDto updateCategory(UpdateCategoryDto dto);
}
