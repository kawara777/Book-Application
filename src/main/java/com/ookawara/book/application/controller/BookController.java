package com.ookawara.book.application.controller;

import com.ookawara.book.application.controller.request.BookRequest;
import com.ookawara.book.application.controller.request.CategoryRequest;
import com.ookawara.book.application.controller.response.BookResponse;
import com.ookawara.book.application.controller.response.CategoryResponse;
import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> getBooksAllData(@RequestParam(required = false, defaultValue = "") String category,
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

    @PostMapping("/books")
    public ResponseEntity<BookResponse> postBook(@RequestBody @Validated BookRequest bookRequest, UriComponentsBuilder uriBuilder) {
        Book book = bookService.createBook(bookRequest.getName(), bookRequest.getReleaseDate(), bookRequest.getIsPurchased(), bookRequest.getCategoryId());
        URI location = uriBuilder.path("/book/{id}").buildAndExpand(book.getBookId()).toUri();
        BookResponse body = new BookResponse("正常に登録されました。");
        return ResponseEntity.created(location).body(body);
    }


    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> postCategory(@RequestBody @Validated CategoryRequest categoryRequest, UriComponentsBuilder uriBuilder) {
        Category category = bookService.createCategory(categoryRequest.getCategory());
        URI location = uriBuilder.path("/category/{categoryId}").buildAndExpand(category.getCategoryId()).toUri();
        CategoryResponse body = new CategoryResponse("正常に登録されました。");
        return ResponseEntity.created(location).body(body);
    }
}
