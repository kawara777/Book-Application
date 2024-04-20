package com.ookawara.book.application.controller.request;

import com.ookawara.book.application.util.DateTimeFormat;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public class BookUpdateRequest {
    private String name;

    private String releaseDate;

    private Boolean isPurchased;

    @Min(value = 1, message = "1 以上の値にしてください")
    private Integer categoryId;

    public BookUpdateRequest(String name, String releaseDate, Boolean isPurchased, Integer categoryId) {
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
