package com.test.graphql.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String author;
    private Integer publishYear;
    private String genre;
    private Double price;

    public Book(){

    }

    public Book(String id, String title, String author, Integer publishYear, String genre, Double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.genre = genre;
        this.price = price;
    }

    public Book(String title, String author, Integer publishYear, String genre, Double price) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.genre = genre;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}