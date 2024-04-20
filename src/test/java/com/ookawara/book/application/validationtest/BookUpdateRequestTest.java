package com.ookawara.book.application.validationtest;

import com.ookawara.book.application.controller.request.BookUpdateRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    void カテゴリーIDが1より小さい数字のときバリーデーションエラーが発生すること() {
        BookUpdateRequest bookRequest = new BookUpdateRequest("鬼滅の刃・1", "2000/01/01", false, 0);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(tuple("categoryId", "1 以上の値にしてください"));
    }

    @Test
    void 全てのフィールドに正常値があるときバリデーションエラーとならないこと() {
        BookUpdateRequest bookRequest = new BookUpdateRequest(null, "2000/01/01", null, 1);
        Set<ConstraintViolation<BookUpdateRequest>> violations = validator.validate(bookRequest);
        assertThat(violations).isEmpty();
    }
}
