package com.the_ajou.web.dao.category;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CategoryDAO {
    int categoryId;
    String categoryName;

    @Builder
    public CategoryDAO(int categoryId, String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
