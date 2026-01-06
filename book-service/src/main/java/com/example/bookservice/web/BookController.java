package com.example.bookservice.web;

import com.example.bookservice.domain.Book;
import com.example.bookservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> all() { return service.all(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@RequestBody Book b) { return service.create(b); }

    @PostMapping("/{id}/rent") // Change path from /borrow
    public BookService.BorrowResult rent(@PathVariable long id) { // Renamed method
        return service.rentBook(id); // Called renamed service method
    }
}
