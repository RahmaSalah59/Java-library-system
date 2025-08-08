package com.project.library_sys.dto;

public class BookDTO {
    public Long id;
    public String title;
    public String author;
    public String isbn;
    public String description;

    public BookDTO() {}
    public BookDTO(Long id, String title, String author, String isbn, String description) {
        this.id = id; this.title = title; this.author = author; this.isbn = isbn; this.description = description;
    }
}
