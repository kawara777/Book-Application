package com.ookawara.book.application.controller;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.BookAllData;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookAllData> getBooksAllData(@RequestParam(required = false, defaultValue = "") String category,
                                             @RequestParam(required = false, defaultValue = "") String name,
                                             @RequestParam(required = false, defaultValue = "") Boolean isPurchased) {
        return bookService.findBy(category, name, isPurchased);
    }

    @GetMapping("/book/{bookId}")
    public Book getBook(@PathVariable int bookId) {
        return bookService.findBook(bookId);
    }

    @GetMapping("/category/{categoryId}")
    public Category getCategory(@PathVariable int categoryId) {
        return bookService.findCategory(categoryId);
    }

}
