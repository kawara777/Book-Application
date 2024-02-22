package com.ookawara.book_.application.Controller;

import com.ookawara.book_.application.Mapper.BookMapper;
import com.ookawara.book_.application.Entity.Book;
import com.ookawara.book_.application.Entity.BookAllData;
import com.ookawara.book_.application.Entity.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/all")
public class BookController {

    private final BookMapper bookMapper;

    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @GetMapping
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

    @GetMapping("/names")
    public List<Book> findByName(@RequestParam String startsWith) {
        return bookMapper.findByNameStartingWith(startsWith);
    }

    @GetMapping("/isPurchased")
    public List<Book> findByISPurchased(@RequestParam int judgment) {
        return bookMapper.findByIsPurchased(judgment);
    }

    @GetMapping("/categories/{categoryId}")
    public List<BookAllData> findByCategory(@PathVariable int categoryId) {
        return bookMapper.findByCategory(categoryId);
    }

}
