package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.category.CategoryDto;
import net.khaibq.javabackend.dto.category.CreateCategoryDto;
import net.khaibq.javabackend.dto.category.UpdateCategoryDto;
import net.khaibq.javabackend.entity.Category;
import net.khaibq.javabackend.exception.BaseException;
import net.khaibq.javabackend.repository.CategoryRepository;
import net.khaibq.javabackend.service.CategoryService;
import net.khaibq.javabackend.ultis.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public List<CategoryDto> getListCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(x -> mapper.map(x, CategoryDto.class))
                .toList();
    }

    @Override
    public CategoryDto createCategory(CreateCategoryDto dto) {
        Category category = new Category();
        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new BaseException("Chuyên mục cha không tồn tại"));
            category.setParent(parent);
        }
        category.setName(dto.getName());
        category.setSlug(CommonUtils.toSlug(dto.getName()));
        category.setPriority(dto.getPriority());
        category.setIsDeleted(Objects.equals(dto.getIsDeleted(), 1) ? 1 : 0);
        categoryRepository.save(category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(UpdateCategoryDto dto) {
        Category category = categoryRepository.findById(dto.getId())
                .orElseThrow(() -> new BaseException("Chuyên mục không tồn tại"));

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new BaseException("Chuyên mục cha không tồn tại"));
            category.setParent(parent);
        }

        category.setName(dto.getName());
        category.setSlug(CommonUtils.toSlug(dto.getName()));
        category.setPriority(dto.getPriority());
        category.setIsDeleted(Objects.equals(dto.getIsDeleted(), 1) ? 1 : 0);
        categoryRepository.save(category);

        return mapper.map(category, CategoryDto.class);
    }
}
