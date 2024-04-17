package com.ookawara.book.application.validationtest;

import com.ookawara.book.application.controller.request.BookUpdateRequest;
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

class BookUpdateRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void releaseDateが未来の年月日のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookUpdateRequest bookRequest = new BookUpdateRequest("鬼滅の刃・1", LocalDate.of(9999, 12, 31), false, 1);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("releaseDate", "現在もしくは過去の日付を入力してください"));
    }

    @Test
    void categoryIdが1より小さい数字のときバリーデーションエラーとなりエラーメッセージが設定したものになっていること() {
        BookUpdateRequest bookRequest = new BookUpdateRequest("鬼滅の刃・1", LocalDate.now(), false, 0);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("categoryId", "1 以上の値にしてください"));
    }

    @Test
    void releaseDateとcategoryIdがバリーデーションエラーの時各項目のエラーメッセージが設定したものになって全て返されること() {
        BookUpdateRequest bookRequest = new BookUpdateRequest("", LocalDate.of(9999, 12, 31), false, 0);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("releaseDate", "現在もしくは過去の日付を入力してください"),
                        tuple("categoryId", "1 以上の値にしてください"));
    }

    @Test
    void 全てのフィールドに正常値があるときバリデーションエラーとならないこと() {
        BookUpdateRequest bookRequest = new BookUpdateRequest(null, LocalDate.now(), null, 1);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).isEmpty();
    }
}
