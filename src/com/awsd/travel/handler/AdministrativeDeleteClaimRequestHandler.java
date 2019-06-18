package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;
import com.esdnl.util.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class AdministrativeDeleteClaimRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    Personnel p = null;
    String path = "";
    StringTokenizer str_tok = null;
    TreeMap map = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-ADMINISTRATIVE-DELETE")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    String id = request.getParameter("id");
    String srch_txt = request.getParameter("txt");
    String srch_type = request.getParameter("type");
    
    if(StringUtils.isEmpty(id))
    {
      path = "claim_error.jsp?msg=Claim id is required for administrative delete.";
    }
    else
      TravelClaimDB.deleteClaim(TravelClaimDB.getClaim(Integer.parseInt(id)));
    
    if((srch_txt != null) && !srch_txt.equals(""))
    {
      if((srch_type != null) && !srch_type.equals(""))
      {
        if(srch_type.equalsIgnoreCase("NAME"))
        {
          String[] names = srch_txt.trim().split(" ");
          System.out.println("#Names: " + names.length);
          if(names.length <= 1)
          {
             map = TravelClaimDB.searchByName(srch_txt.trim().toUpperCase(), srch_txt.trim().toUpperCase());
             
             if(map.size() < 1)
                path = "claim_error.jsp?msg=Search found no matches.";
             else
              path = "searchresults.jsp";
            
          }
          else if(names.length == 2)
          {
            map = TravelClaimDB.searchByName(names[0].trim().toUpperCase(), names[1].trim());
            
            if(map.size() < 1)
              path = "claim_error.jsp?msg=Search found no matches.";
            else
              path = "searchresults.jsp";
          }
          else
            path = "claim_error.jsp?msg=Only FIRSTNAME, LASTNAME, or FIRST and LASTNAME can be entered in the search text.";
            
          
          if(map != null)
            request.setAttribute("SEARCH_RESULTS", map);
        }
        else if(srch_type.equalsIgnoreCase("VENDOR"))
        {
          map = TravelClaimDB.searchByVendorNumber(srch_txt.trim().toUpperCase());
             
          if(map.size() < 1)
            path = "claim_error.jsp?msg=Search found no matches.";
          else
            path = "searchresults.jsp";
          
          if(map != null)
            request.setAttribute("SEARCH_RESULTS", map);
        }
        else
          path = "claim_error.jsp?msg=Invalid search type.";
      }
      else
      {
        path = "claim_error.jsp?msg=Search type must be selected.";
      }
    }
    else
    {
      path = "claim_error.jsp?msg=Search text is required.";
    }
    
    return path;
  }
}