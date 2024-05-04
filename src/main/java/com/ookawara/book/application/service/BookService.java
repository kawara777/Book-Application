package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.ResourceDuplicateException;
import com.ookawara.book.application.exception.ResourceNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public List<Book> findBy(String name, String releaseDate, Boolean isPurchased, String category) {
        if (name.isEmpty() && releaseDate.isEmpty() && Objects.isNull(isPurchased) && category.isEmpty()) {
            return bookMapper.findAll();
        } else {
            return bookMapper.findBy(name, releaseDate, isPurchased, category);
        }
    }

    public Book findBook(int bookId) {
        Optional<Book> book = this.bookMapper.findByBookId(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ResourceNotFoundException("book：" + bookId + " のデータはありません。");
        }

    }

    public Category findCategory(int categoryId) {
        return bookMapper.findByCategoryId(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category：" + categoryId + " のデータはありません。"));
    }

    public Book createBook(String name, LocalDate releaseDate, Boolean isPurchased, int categoryId) {
        if (bookMapper.findByCategoryId(categoryId).isPresent()) {
            Book book = new Book(name, releaseDate, isPurchased, categoryId);
            if (bookMapper.findBookBy(book.getName(), book.getReleaseDate(), book.getIsPurchased(), book.getCategoryId()).isPresent()) {
                throw new ResourceDuplicateException("すでに登録されています。");
            } else {
                bookMapper.insertBook(book);
                return book;
            }
        } else {
            throw new ResourceNotFoundException("categoryId：" + categoryId + " のデータがありません。");
        }
    }

    public Category createCategory(String category) {
        Category categoryName = new Category(category);
        if (bookMapper.findCategory(categoryName.getCategory()).isPresent()) {
            throw new ResourceDuplicateException("すでに登録されています。");
        } else {
            bookMapper.insertCategory(categoryName);
            return categoryName;
        }
    }

    public Book updateBook(int bookId, String name, LocalDate releaseDate, Boolean isPurchased, Integer categoryId) {
        bookMapper.findByBookId(bookId).orElseThrow(
                () -> new ResourceNotFoundException("book：" + bookId + " のデータはありません。"));
        if (Objects.nonNull(categoryId) && bookMapper.findByCategoryId(categoryId).isEmpty()) {
            throw new ResourceNotFoundException("categoryId：" + categoryId + " のデータがありません。");
        } else {
            Book book = new Book(bookId, name, releaseDate, isPurchased, categoryId);
            bookMapper.updateBook(book);
            return book;
        }
    }

    public void deleteBook(int bookId) {
        bookMapper.findByBookId(bookId).orElseThrow(
                () -> new ResourceNotFoundException("book：" + bookId + " のデータはありません。"));
        bookMapper.deleteBook(bookId);
    }
}
