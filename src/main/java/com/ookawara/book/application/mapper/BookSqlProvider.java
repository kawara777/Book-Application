package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;

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
                if (name != null && !name.isEmpty()) {
                    WHERE("name like concat('%',#{name},'%')");
                }
                if (releaseDate != null && !releaseDate.isBlank()) {
                    WHERE("release_date like concat('%',#{releaseDate},'%')");
                }
                if (isPurchased != null) {
                    WHERE("is_purchased = #{isPurchased}");
                }
                if (category != null && !category.isEmpty()) {
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
                if (name != null && !name.isBlank() && releaseDate != null) {
                    SELECT("*");
                    FROM("books");
                    WHERE("name = #{name} and release_date = #{releaseDate}");
                    if (isPurchased != null) {
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
                        throw new IllegalArgumentException("categoryIdに1以上の整数を入力してください。");
                    }
                }
                WHERE("book_id = #{bookId}");
            }
        }.toString();
    }
}
