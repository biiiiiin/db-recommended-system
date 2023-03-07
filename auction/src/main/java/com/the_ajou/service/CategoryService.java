package com.the_ajou.service;

import com.the_ajou.model.category.Category;
import com.the_ajou.model.category.CategoryRepository;
import com.the_ajou.web.dao.category.CategoryDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public int createCategory(String name){
        Category category = Category.builder()
                .name(name)
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .status('N')
                .build();
        categoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public int deleteCategory(int id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.setStatus('Y');
        category.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        categoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public List<CategoryDAO> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDAO> categoryDAOS = new LinkedList<>();

        for(Category category : categories){
            if(category.getStatus() == 'N') {
                CategoryDAO categoryDAO = CategoryDAO.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .build();
                categoryDAOS.add(categoryDAO);
            }
        }
        return categoryDAOS;
    }
}
