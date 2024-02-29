package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.BookAllData;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.exception.BooksRequestOkException;
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

    public List<BookAllData> findBy(String category, String name) {
        if (category.isEmpty() && name.isEmpty()) {
            return bookMapper.findAll();
        } else if (bookMapper.findBy(category, name).isEmpty()) {
            throw new BooksRequestOkException("category：" + category + ",name：" + name + " に該当するデータはありません。");
        } else {
            return bookMapper.findBy(category, name);
        }
    }

    public List<BookAllData> findByPurchaseStatus(boolean status) {
        if (!status) {
            return bookMapper.findByPurchaseStatus(false);
        } else {
            return bookMapper.findByPurchaseStatus(true);
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
        return bookMapper.findByCategoryId(categoryId).orElseThrow(
                () -> new CategoryNotFoundException("category：" + categoryId + " のデータはありません。"));
    }

}
