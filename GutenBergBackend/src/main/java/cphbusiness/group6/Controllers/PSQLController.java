package cphbusiness.group6.Controllers;

import cphbusiness.group6.Connectors.PSQLConnector;
import cphbusiness.group6.entities.Author;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.controllers.I_DBController;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PSQLController implements I_DBController
{
    @Override
    public List<I_Book> getAllBooksThatMentionCity(String cityName)
    {
        String query1 = "Select t_book.name as bn, t_auth.name as an, t_book.release_date as rd From t_book " +
                "Join t_auth ON (t_auth.id = t_book.auth_ID) " +
                "Where t_book.id In (Select t_ment.book_ID From t_ment Where t_ment.city_ID In " +
                "(Select t_city.id from t_city Where t_city.name = '" + cityName + "' limit 1 )) " +
                "Order By t_auth.name";


        PSQLConnector pCon = new PSQLConnector();

        Connection con = null;
        try
        {
            con = pCon.getPSQLConnection("167.99.237.199:5432", "username","password");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IncorrectUsrNameOrPasswordException e)
        {
            e.printStackTrace();
        }

        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            List<I_Book> list = new ArrayList<>();

            while (rs.next())
            {

                list.add(new Book(rs.getString("bn"), rs.getString("rd"), new Author(rs.getString("an"))));

            }
            return list;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;

    }

    @Override
    public List<I_City> getAllCitiesMentionedInBook(String bookTitle)
    {
        bookTitle = bookTitle.replaceAll("'", "''");
        String query1 = "Select t_city.name,t_city.latitude,t_city.longitude From t_city " +
                "Where t_city.id In (Select t_ment.city_ID From t_ment Where t_ment.book_ID In " +
                "(Select t_book.id from t_book Where t_book.name LIKE '" + bookTitle + "')) " +
                "Order By t_city.name";


        PSQLConnector pCon = new PSQLConnector();
        Connection con = null;
        try
        {
            con = pCon.getPSQLConnection("167.99.237.199:5432","username","password");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IncorrectUsrNameOrPasswordException e)
        {
            e.printStackTrace();
        }


        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);

            List<I_City> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(new City(rs.getString("name"), new Coordinate(rs.getDouble("latitude"), rs.getDouble("longitude"))));
            }
            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<I_Book> getAllBooksWrittenByAuthor(String authorName)
    {
        String query3 = "Select t_book.name, t_book.release_date From t_book " +
                "Where t_book.auth_ID In (Select t_auth.id from t_auth Where t_auth.name Like '" + authorName + "' )" +
                "Order By t_book.name";

        PSQLConnector pCon = new PSQLConnector();
        Connection con = null;
        try
        {
            con = pCon.getPSQLConnection("167.99.237.199:5432","username","password");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IncorrectUsrNameOrPasswordException e)
        {
            e.printStackTrace();
        }

        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query3);

            List<I_Book> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(new Book(rs.getString("name"), rs.getString("release_date"), new Author(authorName)));
            }

            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<I_City> getCitiesFromManyBooks(List<I_Book> books)
    {
        String arr = "";
        for (I_Book book : books)
        {
            if (arr != "")
            {
                arr += ",'" + book.getTitle() + "'";
            }
            else
            {
                arr += "'" + book.getTitle() + "'";
            }
        }


        String query3 = "Select t_city.name,t_city.latitude,t_city.longitude From t_city " +
                "Where t_city.id In (Select t_ment.city_ID From t_ment Where t_ment.book_ID In " +
                "(Select t_book.id from t_book Where t_book.auth_ID IN " +
                "(Select t_auth.id From t_auth where t_auth.name = '" + books.get(0).getAuthor().getName() + "')))" +
                "Order By t_city.name";

        PSQLConnector pCon = new PSQLConnector();
        Connection con = null;
        try
        {
            con = pCon.getPSQLConnection("167.99.237.199:5432","username","password");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IncorrectUsrNameOrPasswordException e)
        {
            e.printStackTrace();
        }


        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query3);
            String sr = "";

            List<I_City> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(new City(rs.getString("name"), new Coordinate(rs.getDouble("latitude"), rs.getDouble("longitude"))));
            }
            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<I_Book> getCitiesCloseToGeoLocation(I_Coordinate geoLocationCoordinate)
    {
/*        String query4= "Select t_book.name as bn, t_auth.name as an, t_book.release_date From t_book " +
                "Join t_auth on (t_auth.id = t_book.auth_ID) " +
                "Where t_book.id In (Select t_ment.book_ID From t_ment Where t_ment.city_ID In " +
                "(Select t_city.id  From t_city Where Point (t_city.latitude, t_city.longitude) <@ circle '(("+geoLocationCoordinate.getLat()+","+ geoLocationCoordinate.getLang()+"), 1)' )) " +
                "Order By t_book.name";*/
        String query4 = "Select t_book.name as bn, t_auth.name as an, t_book.release_date From t_book " +
                "Join t_auth on (t_auth.id = t_book.auth_ID) Where t_book.id In " +
                "(Select t_ment.book_ID From t_ment Where t_ment.city_ID In " +
                "(select t_city.id from t_city where " +
                "asin(sqrt(sin(radians(" + geoLocationCoordinate.getLat() + " - t_city.latitude)/2)^2 + sin(radians(" + geoLocationCoordinate.getLang() + " - t_city.longitude)/2)^2 * cos(radians(t_city.latitude)) * cos(radians(" + geoLocationCoordinate.getLat() + "))))*12756.2 < 100)) " +
                "Order By t_book.name";
        PSQLConnector pCon = new PSQLConnector();
        Connection con = null;
        try
        {
            con = pCon.getPSQLConnection("167.99.237.199:5432","username","password");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (IncorrectUsrNameOrPasswordException e)
        {
            e.printStackTrace();
        }


        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query4);

            List<I_Book> list = new ArrayList<>();
            while (rs.next())
            {
                list.add(new Book(rs.getString("bn"), rs.getString("release_date"), new Author(rs.getString("an"))));
            }

            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                con.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }


        return null;
    }
}
