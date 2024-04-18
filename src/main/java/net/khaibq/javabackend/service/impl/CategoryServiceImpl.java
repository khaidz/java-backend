package net.khaibq.javabackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.khaibq.javabackend.dto.category.CategoryDto;
import net.khaibq.javabackend.repository.CategoryRepository;
import net.khaibq.javabackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Override
    public List<CategoryDto> getListCategory() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(x -> mapper.map(x, CategoryDto.class))
                .toList();
    }
}
