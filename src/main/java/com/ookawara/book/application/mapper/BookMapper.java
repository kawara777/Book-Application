package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    //    GET
    @Select("select * from books join categories on books.category_id = categories.category_id")
    List<Book> findAll();

    @SelectProvider(BookSqlProvider.class)
    List<Book> findBy(@Param("name") String name,
                      @Param("releaseDate") String releaseDate,
                      @Param("isPurchased") Boolean isPurchased,
                      @Param("category") String category);

    @Select("select * from books join categories on books.category_id = categories.category_id where book_id = #{bookId}")
    Optional<Book> findByBookId(int bookId);

    @Select("select * from categories where category_id = #{categoryId}")
    Optional<Category> findByCategoryId(int categoryId);

    //    POST
    @Insert("insert into books (name, release_date, is_purchased, category_id) values (#{name}, #{releaseDate}, #{isPurchased}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "bookId")
    void insertBook(Book book);

    @Insert("insert into categories (category) values (#{category})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    void insertCategory(Category category);

    @SelectProvider(BookSqlProvider.class)
    Optional<Book> findBookBy(@Param("name") String name,
                              @Param("releaseDate") LocalDate releaseDate,
                              @Param("isPurchased") Boolean isPurchased,
                              @Param("categoryId") int categoryId);

    @Select("select * from categories where category like #{category}")
    Optional<Category> findCategory(String category);

    //    PATCH
    @UpdateProvider(BookSqlProvider.class)
    void updateBook(Book book);

    //    DELETE
    @Delete("delete from books where book_id = #{bookid}")
    void deleteBook(int bookId);
}
