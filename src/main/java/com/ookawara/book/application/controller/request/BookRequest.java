package com.ookawara.book.application.controller.request;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class BookRequest {
    @NotBlank(message = "書籍名を入力してください")
    private String name;

    @PastOrPresent(message = "現在もしくは過去の日付を入力してください")
    private LocalDate releaseDate;

    private Boolean isPurchased;

    @Min(value = 1, message = "1 以上の値にしてください")
    private int categoryId;

    public BookRequest(String name, LocalDate releaseDate, Boolean isPurchased, int categoryId) {
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

    public int getCategoryId() {
        return categoryId;
    }
}
