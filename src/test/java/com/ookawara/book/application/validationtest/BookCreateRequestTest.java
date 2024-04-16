package com.ookawara.book.application.validationtest;

import com.ookawara.book.application.controller.request.BookCreateRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BookCreateRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void nameがnullのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest(null, LocalDate.now(), false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("name", "書籍名を入力してください"));
    }

    @Test
    void nameが空文字のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("", LocalDate.now(), false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("name", "書籍名を入力してください"));
    }

    @Test
    void nameが半角スペースのみのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest(" ", LocalDate.now(), false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("name", "書籍名を入力してください"));
    }

    @Test
    void releaseDateが未来の年月日のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", LocalDate.of(9999, 12, 31), false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("releaseDate", "現在もしくは過去の日付を入力してください"));
    }

    @Test
    void releaseDateがnullのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", null, false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("releaseDate", "発売日を入力してください"));
    }

    @Test
    void categoryIdがnullのときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", LocalDate.now(), false, null);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("categoryId", "カテゴリーIDを入力してください"));
    }

    @Test
    void categoryIdが1より小さい数字のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", LocalDate.now(), false, 0);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("categoryId", "1 以上の値にしてください"));
    }

    @Test
    void nameとreleaseDateとcategoryIdが1より小さい数字のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", LocalDate.now(), false, 0);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("categoryId", "1 以上の値にしてください"));
    }

    @Test
    void 全てのフィールドに正常値があるときバリデーションエラーとならないこと() {
        BookCreateRequest bookRequest = new BookCreateRequest("鬼滅の刃・1", LocalDate.now(), false, 1);
        Set<ConstraintViolation<BookCreateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).isEmpty();
    }
}
