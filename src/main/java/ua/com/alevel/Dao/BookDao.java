package ua.com.alevel.Dao;

import ua.com.alevel.Book;

import java.util.List;

public interface BookDao {
    void create(Book book);

    void update(Book book);

    void deleteBook(String title);

    void findBooksByAuthor(String author);

    void findAuthorsByBook(String title);

    List<Book> findAll();
}
