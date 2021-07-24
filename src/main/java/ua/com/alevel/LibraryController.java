package ua.com.alevel;

import ua.com.alevel.Config.ObjectFactory;
import ua.com.alevel.Dao.AuthorDao;
import ua.com.alevel.Dao.BookDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LibraryController {

    private final BookDao bookDao = ObjectFactory.getInstance().getImplClass(BookDao.class);
    private final AuthorDao authorDao = ObjectFactory.getInstance().getImplClass(AuthorDao.class);


    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("select your option");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                crud(position, reader);
                position = reader.readLine();
                if (position.equals("0")) {
                    System.exit(0);
                }
                crud(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void crud(String position, BufferedReader reader) {
        switch (position) {
            case "1" : createBook(reader); break;
            case "2" : update(reader); break;
            case "3" : delete(reader); break;
            case "4" : findBooksByAuthor(reader); break;
            case "5" : findAuthorsByBook(reader); break;
            case "6" : findAll(); break;
        }
        runNavigation();
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("if you want create book, please enter 1");
        System.out.println("if you want update book, please enter 2");
        System.out.println("if you want delete book, please enter 3");
        System.out.println("if you want findBooksByAuthor, please enter 4");
        System.out.println("if you want findAuthorsByBook, please enter 5");
        System.out.println("if you want findAllBooks, please enter 6");
        System.out.println("if you want exit, please enter 0");
        System.out.println();
    }

    private void createBook(BufferedReader reader) {
        System.out.println("LibraryController.createBook");
        try {
            System.out.println("Please, enter book title");
            String title = reader.readLine();
            System.out.println("Please, enter author firstname of book");
            String firstname = reader.readLine();
            System.out.println("Please, enter author lastname of book");
            String lastname = reader.readLine();
            Book book = new Book();
            Author author = new Author();
            List<String> authors = new ArrayList<>();
            book.setTitle(title);
            author.setFirstname(firstname);
            author.setLastname(lastname);
            authors.add(author.getFirstname() + " " + author.getLastname());
            book.setAuthors(authors);


            int count = 1;
            for(int i = 0; i < count; i++) {
                System.out.println("Do you want to add another author? Enter Y or N");
                String answer = reader.readLine();
                if(answer.equals("Y") || answer.equals("y")) {
                    System.out.println("Please, enter author firstname of book");
                    String firstname1 = reader.readLine();
                    System.out.println("Please, enter author lastname of book");
                    String lastname1 = reader.readLine();
                    author.setFirstname(firstname1);
                    author.setLastname(lastname1);
                    authors.add(author.getFirstname() + " " + author.getLastname());
                    book.setAuthors(authors);
                    authorDao.addAuthor(author);
                    count++;
                }
                else {
                    break;
                }
            }
            bookDao.create(book);

        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }


    private void update(BufferedReader reader) {
        System.out.println("LibraryController.update");
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            System.out.println("Please, enter new title");
            String newTitle = reader.readLine();
            Book book = new Book();
            Author author = new Author();
            book.setId(id);
            book.setTitle(newTitle);
            int count = 1;
            List<String> authors = new ArrayList<>();
            for(int i = 0; i < count; i++) {
                System.out.println("Do you want to update author? Enter Y or N");
                String answer = reader.readLine();
                if(answer.equals("Y") || answer.equals("y")) {
                    System.out.println("Please, enter author firstname of book");
                    String firstname1 = reader.readLine();
                    System.out.println("Please, enter author lastname of book");
                    String lastname1 = reader.readLine();
                    authors.add(firstname1 + " " + lastname1);
                    authorDao.addAuthor(author);
                    count++;
                }
                else {
                    break;
                }
            }
            book.setAuthors(authors);
            bookDao.update(book);

        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void delete(BufferedReader reader) {
        System.out.println("LibraryController.delete");
        try {
            System.out.println("Please, enter title");
            String title = reader.readLine();
            bookDao.deleteBook(title);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findBooksByAuthor(BufferedReader reader) {
        System.out.println("LibraryController.findBooksByAuthor");
        try {
            System.out.println("Please, enter full name author");
            String author = reader.readLine();
            bookDao.findBooksByAuthor(author);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void findAuthorsByBook(BufferedReader reader) {
        System.out.println("LibraryController.findAuthorsByBook");
        try {
            System.out.println("Please, enter title");
            String title = reader.readLine();
            bookDao.findAuthorsByBook(title);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }


    private void findAll() {
        System.out.println("LibraryController.findAll");
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
