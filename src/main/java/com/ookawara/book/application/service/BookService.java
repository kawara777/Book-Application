package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.BookConflictException;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.exception.CategoryNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Book createBook(String name, LocalDate releaseDate, Boolean isPurchased, int categoryId) {
        Book book = new Book(name, releaseDate, isPurchased, categoryId);
        if (bookMapper.findByNameAndCategory(book.getName(), book.getCategoryId()).isPresent()) {
            throw new BookConflictException("すでに登録されています。");
        } else {
            bookMapper.insertBook(book);
            return book;
        }
    }
}
