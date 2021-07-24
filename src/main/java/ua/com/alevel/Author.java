package ua.com.alevel;

import java.util.List;

public class Author {
    private String firstname;
    private String lastname;
    private List<String> books;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Authors{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", books='" + books + '\'' +
                '}';
    }
}
