package com.awsd.security.addressbook.handler;

import com.awsd.security.addressbook.*;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewAddressBookRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    AddressBook ab = null;
    String path, searchtxt;

    searchtxt = request.getParameter("searchtxt");
    if(searchtxt != null)
      ab = new AddressBook(searchtxt);
    else
      ab = new AddressBook();

    request.setAttribute("AddressBook", ab);
    
    path = "addressbook.jsp";
    
    return path;
  }
}