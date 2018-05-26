package cphbusiness.group6.entities;

import cphbusiness.group6.interfaces.entities.I_Author;
import cphbusiness.group6.interfaces.entities.I_Book;

public class Book implements I_Book
{
    private String title;
    private String releaseDate;
    private I_Author author;
    private String textContent;

    public Book(String title, String releaseDate, I_Author author, String textContent)
    {
        this.title = title;
        this.releaseDate = releaseDate;
        this.author = author;
        this.textContent = textContent;
    }

    public Book(String title, String releaseDate, I_Author author)
    {
        this.title = title;
        this.releaseDate = releaseDate;
        this.author = author;
    }

    public Book(String title, I_Author author)
    {
        this.title = title;
        this.author = author;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    @Override
    public I_Author getAuthor()
    {
        return author;
    }

    @Override
    public String getTextContent()
    {
        return null;
    }

    public void setAuthor(I_Author author)
    {
        this.author = author;
    }


    public void setTextContent(String textContent)
    {
        this.textContent = textContent;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", author=" + author +
                ", textContent='" + textContent + '\'' +
                '}';
    }
}
