package com.awsd.security.addressbook;

import com.awsd.security.*;
import com.awsd.security.SecurityException;

import java.sql.*;

import java.util.*;


public class AddressBookDB
{
  static
  {
    try
    {
      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    }
    catch(ClassNotFoundException e)
    {
      //System.err.println("Could not find 'lotus.jdbc.domino.DominoDriver'");
      System.err.println("Could not find 'sun.jdbc.odbc.JdbcOdbcDriver'");
    }
  }

  /*
  public static String emailCheck(String email) throws SecurityException
  {
    String shortname = null;
        
    String namesURL = "jdbc:odbc:MEMBERSERVICES";
    String sql = "SELECT ShortName FROM [memberservices.csv] where InternetAddress LIKE '"
      + email.toLowerCase() + "'";

    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    
    try
    {
      con = DriverManager.getConnection(namesURL, "", "");
      stat = con.createStatement();
      rs = stat.executeQuery(sql);
      
      if(rs.next())
      {
        shortname = (rs.getString("ShortName")).toLowerCase();
      }
      else
      {
        shortname = null;
        throw new SecurityException("E-Mail address not valid");
      }
      rs.close();
      stat.close();
      con.close();
    }
    catch(SQLException e)
    {
      System.err.println(e);
      shortname=null;
    }
    finally
    {
      try
      {
        rs.close();
        stat.close();
        con.close();
      }
      catch(Exception e){}
    }
    
    return shortname;
  }
*/

/*
 * check for ' in email address and replace with *.
 */
public static String emailCheck(String email) throws SecurityException
  {
    String shortname = null;
        
    String namesURL = "jdbc:odbc:MEMBERSERVICES";
    String sql = "SELECT ShortName FROM [memberservices.csv] where InternetAddress LIKE '"
      + email.toLowerCase().replace('\'', '*') + "'";

    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    
    try
    {
      con = DriverManager.getConnection(namesURL, "", "");
      stat = con.createStatement();
      rs = stat.executeQuery(sql);
      
      if(rs.next())
      {
        shortname = (rs.getString("ShortName")).toLowerCase().replace('*', '\'');
      }
      else
      {
        shortname = null;
        throw new SecurityException("E-Mail address not valid");
      }
      rs.close();
      stat.close();
      con.close();
    }
    catch(SQLException e)
    {
      System.err.println(e);
      shortname=null;
    }
    finally
    {
      try
      {
        rs.close();
        stat.close();
        con.close();
      }
      catch(Exception e){}
    }
    
    return shortname;
  }

  public static Vector getAddressBook() throws SecurityException
  {
    Vector book = null;
    String email, firstname, lastname, shortname;
    
    //String namesURL = "jdbc:domino:" 
    //  + "/names.nsf/209.128.50.98; ShowImplicitFields=1;MaxStmtLen=8192;MaxLongVarcharLen=15000";
    String namesURL = "jdbc:odbc:MEMBERSERVICES";
    String sql = "SELECT * FROM [memberservices.csv] ORDER BY LASTNAME, FIRSTNAME";

    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    
    try
    {

      book = new Vector(500, 200);
      con = DriverManager.getConnection(namesURL, "", "");
      stat = con.createStatement();
      rs = stat.executeQuery(sql);
      
      while(rs.next())
      {
        firstname = rs.getString("FirstName");
        if(firstname != null)
          firstname = firstname.replace('*', '\'');

        lastname = rs.getString("LastName");
        if(lastname != null)
          lastname = lastname.replace('*', '\'');

        shortname = rs.getString("ShortName");
        if(shortname != null)
          shortname = shortname.replace('*', '\'');

        email = rs.getString("InternetAddress");
        if(email != null)
          email = email.replace('*', '\'');
          
        book.add(new Address(firstname, lastname, shortname, email));
      }
      
      rs.close();
      stat.close();
      con.close();
    }
    catch(SQLException e)
    {
      System.err.println(e);
    }
    finally
    {
      try
      {
        rs.close();
        stat.close();
        con.close();
      }
      catch(Exception e){}
    }
    
    return book;
  }

  public static Vector getAddressBook(String searchtxt) throws SecurityException
  {
    Vector book = null;
    String email, firstname, lastname, shortname;

    if(searchtxt != null)
      searchtxt = searchtxt.replace('\'', '*');
      
    String namesURL = "jdbc:odbc:MEMBERSERVICES";
    String sql = "SELECT * FROM [memberservices.csv] WHERE "
      + "FIRSTNAME LIKE '%"+ searchtxt + "%' OR "
      + "LASTNAME LIKE '%"+ searchtxt + "%' "
      + "ORDER BY LASTNAME, FIRSTNAME";

    Connection con = null;
    Statement stat = null;
    ResultSet rs = null;
    
    try
    {

      if(searchtxt.equals(""))
        return getAddressBook();
        
      book = new Vector(500, 200);
      con = DriverManager.getConnection(namesURL, "", "");
      stat = con.createStatement();
      rs = stat.executeQuery(sql);
      
      while(rs.next())
      {
        firstname = rs.getString("FirstName");
        if(firstname != null)
          firstname = firstname.replace('*', '\'');

        lastname = rs.getString("LastName");
        if(lastname != null)
          lastname = lastname.replace('*', '\'');

        shortname = rs.getString("ShortName");
        if(shortname != null)
          shortname = shortname.replace('*', '\'');

        email = rs.getString("InternetAddress");
        if(email != null)
          email = email.replace('*', '\'');
          
        book.add(new Address(firstname, lastname, shortname, email));
      }
      
      rs.close();
      stat.close();
      con.close();
    }
    catch(SQLException e)
    {
      System.err.println(e);
    }
    finally
    {
      try
      {
        rs.close();
        stat.close();
        con.close();
      }
      catch(Exception e){}
    }
    
    return book;
  }
}