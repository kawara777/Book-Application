package com.ookawara.book.application.controller.request;

import com.ookawara.book.application.util.DateTimeFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class BookCreateRequest {
    @NotBlank(message = "書籍名を入力してください")
    private String name;

    @NotBlank(message = "発売日を入力してください")
    private String releaseDate;

    private Boolean isPurchased;

    @NotNull(message = "カテゴリーIDを入力してください")
    @Min(value = 1, message = "1 以上の値にしてください")
    private Integer categoryId;

    public BookCreateRequest(String name, String releaseDate, Boolean isPurchased, Integer categoryId) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        DateTimeFormat dateTimeFormat = new DateTimeFormat(releaseDate);
        return dateTimeFormat.getFormat();
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
