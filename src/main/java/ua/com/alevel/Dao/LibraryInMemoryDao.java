package ua.com.alevel.Dao;

import org.apache.commons.collections4.CollectionUtils;
import ua.com.alevel.Author;
import ua.com.alevel.Book;

import java.util.*;


@Deprecated
public class LibraryInMemoryDao implements BookDao, AuthorDao {

    private List<Book> books = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    public LibraryInMemoryDao() {
        System.out.println("LibraryInMemoryDao.LibraryInMemoryDao");
    }

    @Override
    public void create(Book book) {
        book.setTitle(book.getTitle());
        book.setId(generateId());
        books.add(book);
    }


    @Override
    public void addAuthor(Author author) {
        authors.add(author);
    }

    @Override
    public void update(Book book) {
        if (CollectionUtils.isEmpty(books)) {
            throw new RuntimeException("empty list");
        }
        Book current = books.stream().filter(b -> b.getId().equals(book.getId())).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            throw new RuntimeException("book not found");
        }
        current.setTitle(book.getTitle());
        current.setAuthors(book.getAuthors());
    }

    @Override
    public void deleteBook(String title) {
        if (CollectionUtils.isEmpty(books)) {
            System.out.println("empty list");
        }
        Book current = books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            System.out.println("book not found");
        }
        books.removeIf(b -> b.getTitle().equals(title));
    }

    @Override
    public void findBooksByAuthor(String author) {
        if (CollectionUtils.isEmpty(books)) {
            System.out.println("empty list");
        }
        Book current = books.stream().filter(u -> u.getAuthors().contains(author)).findAny().orElse(null);
        books.stream().filter(b -> b.getAuthors().contains(author)).forEach(System.out::println);
        if (Objects.isNull(current)) {
            System.out.println("book bot found");
        }
    }

    @Override
    public void findAuthorsByBook(String title) {
        if (CollectionUtils.isEmpty(books)) {
            System.out.println("empty list");
        }
        Book current = books.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
        books.stream().filter(b -> b.getTitle().equals(title)).forEach(System.out::println);
        if (Objects.isNull(current)) {
            System.out.println("book bot found");
        }
    }


    @Override
    public List<Book> findAll() {
        return books;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (books.stream().anyMatch(b -> b.getId().equals(id))) {
            return generateId();
        }
        return id;
    }

}
