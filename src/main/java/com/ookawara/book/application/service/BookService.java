package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.exception.CategoryNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> findBy(String category, String name, Boolean isPurchased) {
        if (category.isEmpty() && name.isEmpty() && isPurchased == null) {
            return bookMapper.findAll();
        } else {
            return bookMapper.findBy(category, name, isPurchased);
        }
    }

    public Book findBook(int bookId) {
        Optional<Book> book = this.bookMapper.findByBookId(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("book：" + bookId + " のデータはありません。");
        }

    }

    public Category findCategory(int categoryId) {
//        32~37行目を以下のように記述できる
        return bookMapper.findByCategoryId(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("category：" + categoryId + " のデータはありません。"));
    }

}
