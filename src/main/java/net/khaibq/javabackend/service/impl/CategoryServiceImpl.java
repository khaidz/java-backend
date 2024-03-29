package net.khaibq.javabackend.service.impl;

import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.category.CategoryDto;
import net.khaibq.javabackend.entity.Category;
import net.khaibq.javabackend.repository.CategoryRepository;
import net.khaibq.javabackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getListCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList
                .stream()
                .map(item -> {
                    Long countChildren = categoryList.stream()
                            .filter(x -> Objects.equals(x.getParentId(), item.getId()))
                            .count();
                    CategoryDto dto = modelMapper.map(item, CategoryDto.class);
                    dto.setChildren(countChildren);
                    return dto;
                })
                .toList();
    }
}
