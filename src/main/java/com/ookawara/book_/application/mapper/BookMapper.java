package com.ookawara.book_.application.mapper;

import com.ookawara.book_.application.entity.Book;
import com.ookawara.book_.application.entity.BookAllData;
import com.ookawara.book_.application.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select * from books join categories on books.categories_id = categories.categories_id")
    List<BookAllData> findAll();

    @Select("select * from books join categories on books.categories_id = categories.categories_id" +
            " where category like concat('%',#{category},'%') and name like concat('%',#{name},'%')")
    List<BookAllData> findBy(String category, String name);

    @Select("select * from books join categories on books.categories_id = categories.categories_id" +
            " where is_purchased = #{status}")
    List<BookAllData> findByPurchaseStatus(boolean status);

    @Select("select * from books where books_id = #{bookId}")
    Optional<Book> findByBookId(int bookId);

    @Select("select * from categories where categories_id = #{categoryId}")
    Optional<Category> findByCategoryId(int categoryId);

}
