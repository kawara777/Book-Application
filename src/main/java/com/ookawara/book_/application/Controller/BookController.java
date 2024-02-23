package com.ookawara.book_.application.Controller;

import com.ookawara.book_.application.Entity.Book;
import com.ookawara.book_.application.Entity.BookAllData;
import com.ookawara.book_.application.Entity.Category;
import com.ookawara.book_.application.Mapper.BookMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookMapper bookMapper;

    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @GetMapping("/books")
    public List<BookAllData> findAll() {
        return bookMapper.findAll();
    }

    @GetMapping("/book/{bookId}")
    public Optional<Book> findByBookId(@PathVariable int bookId) {
        return bookMapper.findByBookId(bookId);
    }

    @GetMapping("/category/{categoryId}")
    public Optional<Category> findByCategoryId(@PathVariable int categoryId) {
        return bookMapper.findByCategoryId(categoryId);
    }

    @GetMapping("/books/names")
    public List<Book> findByName(@RequestParam String startsWith) {
        return bookMapper.findByNameStartingWith(startsWith);
    }

    @GetMapping("/books/unPurchased")
    public List<Book> findByUnPurchased(@RequestParam int unPurchased) {
        return bookMapper.findByUnPurchased(unPurchased);
    }

    @GetMapping("/books/isPurchased")
    public List<Book> findByIsPurchased(@RequestParam int isPurchased) {
        return bookMapper.findByIsPurchased(isPurchased);
    }

    @GetMapping("/books/category/{categoryId}")
    public List<BookAllData> findByCategory(@PathVariable int categoryId) {
        return bookMapper.findByCategory(categoryId);
    }

}
