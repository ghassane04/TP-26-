package com.example.bookservice.service;

import com.example.bookservice.domain.Book;
import com.example.bookservice.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;
    private final PricingClient pricing;

    public BookService(BookRepository repo, PricingClient pricing) {
        this.repo = repo;
        this.pricing = pricing;
    }

    public List<Book> all() {
        return repo.findAll();
    }

    public Book create(Book b) {
        if (repo.findByTitle(b.getTitle()).isPresent()) { // Changed if style
            throw new IllegalArgumentException("Book title already exists: " + b.getTitle()); // Changed message
        }
        return repo.save(b);
    }

    @Transactional
    public BorrowResult rentBook(long id) { // Renamed from borrow
        // verrou DB ici
        Book book = repo.findByIdForUpdate(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + id)); // Changed message

        book.decrementStock(); // peut lancer IllegalStateException
        double price = pricing.getPrice(id);

        return new BorrowResult(book.getId(), book.getTitle(), book.getStock(), price);
    }

    public record BorrowResult(Long id, String title, int stockLeft, double price) {}
}
