package com.project.library_sys.controller;

import com.project.library_sys.dto.BookDTO;
import com.project.library_sys.model.AppUser;
import com.project.library_sys.model.Book;
import com.project.library_sys.repository.AppUserRepository;
import com.project.library_sys.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;
    private final AppUserRepository userRepository;

    public BookController(BookRepository bookRepository, AppUserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private Optional<AppUser> getCurrentUser(Authentication auth) {
        if (auth == null || auth.getName() == null) return Optional.empty();
        return userRepository.findByUsername(auth.getName());
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO dto, Authentication auth) {
        Optional<AppUser> userOpt = getCurrentUser(auth);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build();
        AppUser user = userOpt.get();
        Book book = new Book(dto.title, dto.author, dto.isbn, dto.description, user);
        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> listBooks(Authentication auth) {
        Optional<AppUser> userOpt = getCurrentUser(auth);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build();
        List<BookDTO> books = bookRepository.findByOwner(userOpt.get())
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable Long id, Authentication auth) {
        Optional<AppUser> userOpt = getCurrentUser(auth);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build();
        Optional<BookDTO> dtoOpt = bookRepository.findByIdAndOwner(id, userOpt.get()).map(this::toDto);
        return ResponseEntity.of(dtoOpt);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO dto, Authentication auth) {
        Optional<AppUser> userOpt = getCurrentUser(auth);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build();
        Optional<Book> bookOpt = bookRepository.findByIdAndOwner(id, userOpt.get());
        if (bookOpt.isPresent()) {
            Book b = bookOpt.get();
            b.setTitle(dto.title);
            b.setAuthor(dto.author);
            b.setIsbn(dto.isbn);
            b.setDescription(dto.description);
            Book updated = bookRepository.save(b);
            return ResponseEntity.ok(toDto(updated));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id, Authentication auth) {
        Optional<AppUser> userOpt = getCurrentUser(auth);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).build();
        Optional<Book> bookOpt = bookRepository.findByIdAndOwner(id, userOpt.get());
        if (bookOpt.isPresent()) {
            bookRepository.delete(bookOpt.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private BookDTO toDto(Book b) {
        return new BookDTO(b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getDescription());
    }
}
