package com.ookawara.book.application.controller;

import com.ookawara.book.application.controller.request.BookCreateRequest;
import com.ookawara.book.application.controller.request.BookUpdateRequest;
import com.ookawara.book.application.controller.request.CategoryRequest;
import com.ookawara.book.application.controller.response.BookResponse;
import com.ookawara.book.application.controller.response.CategoryResponse;
import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.service.BookService;
import com.ookawara.book.application.util.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public List<Book> getBooksAllData(@RequestParam(required = false, defaultValue = "") String name,
                                      @RequestParam(required = false, defaultValue = "") String releaseDate,
                                      @RequestParam(required = false, defaultValue = "") Boolean isPurchased,
                                      @RequestParam(required = false, defaultValue = "") String category) {
        DateTimeFormat formattedReleaseDate = new DateTimeFormat(releaseDate);
        return bookService.findBy(name, formattedReleaseDate.getFormatCheck(), isPurchased, category);
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
    public ResponseEntity<BookResponse> postBook(@RequestBody @Validated BookCreateRequest bookRequest, UriComponentsBuilder uriBuilder) {
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

    @PatchMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> postBook(@PathVariable int bookId, @RequestBody @Validated BookUpdateRequest bookRequest) {
        bookService.updateBook(bookId, bookRequest.getName(), bookRequest.getReleaseDate(), bookRequest.getIsPurchased(), bookRequest.getCategoryId());
        BookResponse body = new BookResponse("正常に更新されました。");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("book/{bookId}")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable int bookId) {
        bookService.deleteBook(bookId);
        BookResponse body = new BookResponse("正常に削除されました。");
        return ResponseEntity.ok(body);
    }
}
