package com.ookawara.book.application.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class BookUpdateRequest {
    private String name;

    @PastOrPresent(message = "現在もしくは過去の日付を入力してください")
    private LocalDate releaseDate;

    private Boolean isPurchased;

    @Min(value = 1, message = "1 以上の値にしてください")
    private Integer categoryId;

    public BookUpdateRequest(String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.isPurchased = isPurchased;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
}
