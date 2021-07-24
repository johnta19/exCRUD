package ua.com.alevel.Dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import ua.com.alevel.Author;
import ua.com.alevel.Book;
import ua.com.alevel.Config.FileType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class LibraryJsonDao implements BookDao, AuthorDao {

    List<Book> bookList = Collections.emptyList();
    List<Author> authorList = Collections.emptyList();

    public LibraryJsonDao() {
        System.out.println("LibraryJsonDao.LibraryJsonDao");
    }

    @Override
    public void create(Book book) {
        this.bookList = getBooksFromFile();
        book.setId(generateId());
        this.bookList.add(book);
        writeBooksToFile();
    }

    @Override
    public void addAuthor(Author author) {
        this.authorList.add(author);
        writeBooksToFile();
    }


    @Override
    public void update(Book book) {
        bookList = getBooksFromFile();
        if (CollectionUtils.isEmpty(bookList)) {
            throw new RuntimeException("empty list");
        }
        Book current = bookList.stream().filter(b -> b.getId().equals(book.getId())).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            throw new RuntimeException("book not found");
        }
        current.setTitle(book.getTitle());
        current.setAuthors(book.getAuthors());
        writeBooksToFile();
    }

    @Override
    public void deleteBook(String title) {
        bookList = getBooksFromFile();
        if (CollectionUtils.isEmpty(bookList)) {
            System.out.println("empty list");
        }
        Book current = bookList.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
        if (Objects.isNull(current)) {
            System.out.println("book not found");
        }
        bookList.removeIf(b -> b.getTitle().equals(title));
        writeBooksToFile();
    }



    @Override
    public void findAuthorsByBook(String title) {
        bookList = getBooksFromFile();
        if (CollectionUtils.isEmpty(bookList)) {
            System.out.println("empty list");
        }
        Book current = bookList.stream().filter(b -> b.getTitle().equals(title)).findFirst().orElse(null);
        bookList.stream().filter(b -> b.getTitle().equals(title)).forEach(System.out::println);
        if (Objects.isNull(current)) {
            System.out.println("book bot found");
        }
    }

    @Override
    public void findBooksByAuthor(String author) {
        bookList = getBooksFromFile();
        if (CollectionUtils.isEmpty(bookList)) {
            System.out.println("empty list");
        }
        Book current = bookList.stream().filter(b -> b.getAuthors().contains(author)).findAny().orElse(null);
        bookList.stream().filter(b -> b.getAuthors().contains(author)).forEach(System.out::println);
        if (Objects.isNull(current)) {
            System.out.println("book bot found");
        }
    }


    @Override
    public List<Book> findAll() {
        return getBooksFromFile();
    }


    private List<Book> getBooksFromFile() {
        try {
            String booksJson = FileUtils.readFileToString(new File(FileType.JSON_TYPE.getPath()), "UTF-8");
            if (StringUtils.isBlank(booksJson)) {
                return new ArrayList<>();
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(booksJson, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("some problem from file");
    }

    private void writeBooksToFile() {
        Gson gson = new Gson();
        String booksOut = gson.toJson(this.bookList);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FileType.JSON_TYPE.getPath()))) {
            writer.append(booksOut);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (bookList.stream().anyMatch(u -> u.getId().equals(id))) {
            return generateId();
        }
        return id;
    }


}
