package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;
import java.util.Objects;

public class BookSqlProvider implements ProviderMethodResolver {
    public String findBy(@Param("name") String name,
                         @Param("releaseDate") String releaseDate,
                         @Param("isPurchased") Boolean isPurchased,
                         @Param("category") String category) {
        return new SQL() {
            {
                SELECT("*");
                FROM("books");
                JOIN("categories on books.category_id = categories.category_id");
                if (Objects.nonNull(name) && !name.isEmpty()) {
                    WHERE("name like concat('%',#{name},'%')");
                }
                if (Objects.nonNull(releaseDate) && !releaseDate.isBlank()) {
                    WHERE("release_date like concat('%',#{releaseDate},'%')");
                }
                if (Objects.nonNull(isPurchased)) {
                    WHERE("is_purchased = #{isPurchased}");
                }
                if (Objects.nonNull(category) && !category.isEmpty()) {
                    WHERE("category like concat('%',#{category},'%')");
                }
            }
        }.toString();
    }

    public String findBookBy(@Param("name") String name,
                             @Param("releaseDate") LocalDate releaseDate,
                             @Param("isPurchased") Boolean isPurchased,
                             @Param("categoryId") int categoryId) {
        return new SQL() {
            {
                if (Objects.nonNull(name) && !name.isBlank() && Objects.nonNull(releaseDate)) {
                    SELECT("*");
                    FROM("books");
                    WHERE("name = #{name} and release_date = #{releaseDate}");
                    if (Objects.nonNull(isPurchased)) {
                        WHERE("is_purchased = #{isPurchased}");
                    } else {
                        WHERE("is_purchased = 0");
                    }
                    if (categoryId >= 1) {
                        WHERE("category_id = #{categoryId}");
                    } else {
                        throw new IllegalArgumentException("categoryIdに1以上の整数を入力してください。");
                    }
                } else {
                    throw new IllegalArgumentException("nameとreleaseDateに値を入力してください。");
                }
            }
        }.toString();
    }

    public String updateBook(Book book) {
        return new SQL() {
            {
                UPDATE("books");
                if (Objects.nonNull(book.getName()) && !book.getName().isBlank()) {
                    SET("name = #{name}");
                }
                if (Objects.nonNull(book.getReleaseDate())) {
                    SET("release_date = #{releaseDate}");
                }
                if (Objects.nonNull(book.getIsPurchased())) {
                    SET("is_purchased = #{isPurchased}");
                }
                if (Objects.nonNull(book.getCategoryId())) {
                    if (book.getCategoryId() >= 1) {
                        SET("category_id = #{categoryId}");
                    } else {
                        throw new IllegalArgumentException("categoryIdに1以上の整数を入力してください。");
                    }
                }
                WHERE("book_id = #{bookId}");
            }
        }.toString();
    }
}
