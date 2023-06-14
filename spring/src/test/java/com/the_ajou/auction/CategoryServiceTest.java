package com.the_ajou.auction;

import com.the_ajou.model.category.Category;
import com.the_ajou.model.category.CategoryRepository;
import com.the_ajou.web.dao.category.CategoryDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@WebAppConfiguration
class CategoryServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Rollback
    @Test
    void createCategory(){
        Category category = Category.builder()
                .name("테스트 카테고리")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .status('N')
                .build();
        categoryRepository.save(category);

        assertThat(category.getId()).isNotZero();
    }

    @Transactional
    @Rollback
    @Test
    void deleteCategory(){
        Category category = categoryRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        category.setStatus('Y');
        category.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        assertThat(category.getStatus()).isEqualTo('Y');
    }

    @Transactional
    @Rollback
    @Test
    void getCategories(){
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

        assertThat(categoryDAOS).isNotEmpty();
    }
}
