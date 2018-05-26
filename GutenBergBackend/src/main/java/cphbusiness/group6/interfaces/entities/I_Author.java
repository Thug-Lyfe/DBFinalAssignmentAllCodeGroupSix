package cphbusiness.group6.interfaces.entities;

import java.util.List;

public interface I_Author
{

    public String getName();

    public List<I_Book> getBooksWritten();

    public void addBooksToAuthor(I_Book bookWrittenByAuthor);
}
