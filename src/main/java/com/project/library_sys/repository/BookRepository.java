package com.project.library_sys.repository;

import com.project.library_sys.model.Book;
import com.project.library_sys.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwner(AppUser owner);
    Optional<Book> findByIdAndOwner(Long id, AppUser owner);
}
