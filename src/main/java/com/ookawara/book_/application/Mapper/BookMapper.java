package com.ookawara.book_.application.Mapper;

import com.ookawara.book_.application.Entity.Book;
import com.ookawara.book_.application.Entity.BookAllData;
import com.ookawara.book_.application.Entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapper {
    @Select("select * from books join categories on books.category_id = categories.id")
    List<BookAllData> findAll();

    @Select("select * from books where id = #{bookId}")
    Optional<Book> findByBookId(int bookId);

    @Select("select * from categories where id = #{categoryId}")
    Optional<Category> findByCategoryId(int categoryId);

    @Select("select * from books where name like concat(#{prefix},'%')")
    List<Book> findByNameStartingWith(String prefix);

    @Select("select * from books where is_purchased = 0")
    List<Book> findByIsPurchased(int isPurchased);

    @Select("select * from books where is_purchased = 1")
    List<Book> findByUnPurchased(int unPurchased);

    @Select("select * from books join categories on books.category_id = categories.id and category_id = #{categoryId}")
    List<BookAllData> findByCategory(int categoryId);
}
