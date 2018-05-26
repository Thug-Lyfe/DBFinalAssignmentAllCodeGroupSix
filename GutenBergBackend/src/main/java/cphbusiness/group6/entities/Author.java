package cphbusiness.group6.entities;

import cphbusiness.group6.interfaces.entities.I_Author;
import cphbusiness.group6.interfaces.entities.I_Book;

import java.util.List;

public class Author implements I_Author
{
    private List<I_Book> booksWritten;
    private String name;

    public Author(List<I_Book> booksWritten, String name)
    {
        this.booksWritten = booksWritten;
        this.name = name;
    }

    public Author(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public List<I_Book> getBooksWritten()
    {
        return this.booksWritten;
    }

    @Override
    public void addBooksToAuthor(I_Book bookWrittenByAuthor)
    {

    }

    public void setBooksWritten(List<I_Book> booksWritten)
    {
        this.booksWritten = booksWritten;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
