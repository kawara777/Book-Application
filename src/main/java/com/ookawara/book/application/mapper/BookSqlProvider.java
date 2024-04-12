package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;

public class BookSqlProvider implements ProviderMethodResolver {
    public String findBy(@Param("category") String category,
                         @Param("name") String name,
                         @Param("isPurchased") Boolean isPurchased) {
        return new SQL() {
            {
                SELECT("*");
                FROM("books");
                JOIN("categories on books.category_id = categories.category_id");
                if (category != null && !category.isEmpty()) {
                    WHERE("category like concat('%',#{category},'%')");
                }
                if (name != null && !name.isEmpty()) {
                    WHERE("name like concat('%',#{name},'%')");
                }
                if (isPurchased != null) {
                    WHERE("is_purchased = #{isPurchased}");
                }
            }
        }.toString();
    }

    public String findBookBy(@Param("bookId") Integer bookId,
                             @Param("name") String name,
                             @Param("releaseDate") LocalDate releaseDate,
                             @Param("isPurchased") Boolean isPurchased,
                             @Param("categoryId") Integer categoryId) {
        return new SQL() {
            {
                if (bookId != null || (name != null && !name.isBlank() || releaseDate != null || isPurchased != null || categoryId != null)) {
                    SELECT("*");
                    FROM("books");
                    if (bookId != null) {
                        if (bookId >= 1) {
                            WHERE("book_id = #{bookId}");
                        } else {
                            throw new IllegalArgumentException("book_idには1以上の整数を入力してください。");

                        }
                    }
                    if (name != null && !name.isBlank()) {
                        if (bookId == null && (releaseDate == null || isPurchased == null || categoryId == null)) {
                            throw new IllegalArgumentException("book_idに1以上の整数を入力してください。");
                        }
                        WHERE("name = #{name}");
                    }
                    if (releaseDate != null) {
                        if (bookId == null && (name == null || name.isBlank())) {
                            throw new IllegalArgumentException("book_idに1以上の整数を入力してください。");
                        }
                        WHERE("release_date = #{releaseDate}");
                    }
                    if (isPurchased != null) {
                        if (bookId == null && (name == null || name.isBlank() || releaseDate == null)) {
                            throw new IllegalArgumentException("book_idに1以上の整数を入力してください。");
                        }
                        WHERE("is_purchased = #{isPurchased}");
                    }
                    if (categoryId != null) {
                        if (bookId == null && (name == null || name.isBlank() || releaseDate == null || isPurchased == null)) {
                            throw new IllegalArgumentException("book_idに1以上の整数を入力してください。");
                        }
                        if (categoryId >= 1) {
                            WHERE("category_id = #{categoryId}");
                        } else {
                            throw new IllegalArgumentException("category_idには1以上の整数を入力してください。");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("値を入力してください。");
                }
            }
        }.toString();
    }

    public String updateBook(Book book) {
        return new SQL() {
            {
                UPDATE("books");
                if (book.getName() != null && !book.getName().isBlank()) {
                    SET("name = #{name}");
                }
                if (book.getReleaseDate() != null) {
                    SET("release_date = #{releaseDate}");
                }
                if (book.getIsPurchased() != null) {
                    SET("is_purchased = #{isPurchased}");
                }
                if (book.getCategoryId() != null) {
                    if (book.getCategoryId() >= 1) {
                        SET("category_id = #{categoryId}");
                    } else {
                        throw new IllegalArgumentException("1以上の整数を入力してください。");
                    }
                }
                WHERE("book_id = #{bookId}");
            }
        }.toString();
    }
}
