package com.ookawara.book.application.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank(message = "カテゴリー名を入力してください")
    private String category;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryRequest(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
