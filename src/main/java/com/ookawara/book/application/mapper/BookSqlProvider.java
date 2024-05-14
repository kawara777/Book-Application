package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.apache.commons.lang3.StringUtils;
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
                if (StringUtils.isNotEmpty(name)) {
                    WHERE("name like concat('%',#{name},'%')");
                }
                if (StringUtils.isNotBlank(releaseDate)) {
                    WHERE("release_date like concat('%',#{releaseDate},'%')");
                }
                if (Objects.nonNull(isPurchased)) {
                    WHERE("is_purchased = #{isPurchased}");
                }
                if (StringUtils.isNotEmpty(category)) {
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
                if (StringUtils.isNotBlank(name) && Objects.nonNull(releaseDate)) {
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
                if (StringUtils.isNotBlank(book.getName())) {
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
