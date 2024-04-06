package com.ookawara.book.application.validationtest;

import com.ookawara.book.application.controller.request.CategoryRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CategoryRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void categoryがnullのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        CategoryRequest categoryRequest = new CategoryRequest(null);
        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(categoryRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "カテゴリー名を入力してください"));
    }

    @Test
    void categoryが空文字のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        CategoryRequest categoryRequest = new CategoryRequest("");
        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(categoryRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "カテゴリー名を入力してください"));
    }

    @Test
    void categoryが半角スペースのみのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        CategoryRequest categoryRequest = new CategoryRequest(" ");
        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(categoryRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("category", "カテゴリー名を入力してください"));
    }

    @Test
    void 全てのフィールドに正常値があるときバリデーションエラーとならないこと() {
        CategoryRequest categoryRequest = new CategoryRequest("漫画");
        Set<ConstraintViolation<CategoryRequest>> violations = validator.validate(categoryRequest);
        assertThat(violations).isEmpty();
    }
}
