package net.khaibq.javabackend.service;

import net.khaibq.javabackend.dto.category.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getListCategory();
}
