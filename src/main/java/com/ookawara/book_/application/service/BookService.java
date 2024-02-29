package com.ookawara.book_.application.service;

import com.ookawara.book_.application.entity.Book;
import com.ookawara.book_.application.entity.BookAllData;
import com.ookawara.book_.application.entity.Category;
import com.ookawara.book_.application.exception.BookNotFoundException;
import com.ookawara.book_.application.exception.BooksOkException;
import com.ookawara.book_.application.exception.CategoryNotFoundException;
import com.ookawara.book_.application.mapper.BookMapper;
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
            throw new BooksOkException("category：" + category + ",name：" + name + " に該当するデータはありません。");
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
