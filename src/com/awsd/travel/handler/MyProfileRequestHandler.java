package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.personnel.profile.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyProfileRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    String path;
    HttpSession session = null;
    User usr = null;
    String op;
    Profile profile = null;
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null)){
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))){
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }else{
      throw new SecurityException("User login required.");
    }
    op = request.getParameter("op");
    if(op != null){
      if(op.equalsIgnoreCase("ADDED") || op.equalsIgnoreCase("UPDATED")){
          if((request.getParameter("cur_street_addr") != null) && (!request.getParameter("cur_street_addr").trim().equals(""))){
             if((request.getParameter("cur_community") != null) && (!request.getParameter("cur_community").trim().equals(""))){
               if((request.getParameter("cur_province") != null) && (!request.getParameter("cur_province").trim().equals(""))){
                 if((request.getParameter("cur_postal_code") != null) && (!request.getParameter("cur_postal_code").trim().equals(""))){
                   if((request.getParameter("home_phone") != null) && (!request.getParameter("home_phone").trim().equals(""))){
                      if((request.getParameter("gender") != null) && (!request.getParameter("gender").trim().equals(""))){
                        if((request.getParameter("position") != null) && (!request.getParameter("position").trim().equals(""))){
                        	profile = new Profile(usr.getPersonnel(), request.getParameter("cur_street_addr"),
                            request.getParameter("cur_community"),
                            request.getParameter("cur_province"),
                            request.getParameter("cur_postal_code"),
                            request.getParameter("home_phone"),
                            request.getParameter("fax"),
                            request.getParameter("cell_phone"),
                            request.getParameter("gender"));
                        	if(op.equalsIgnoreCase("ADDED")){
                        		profile = ProfileDB.addProfile(profile);
                        	}else if(op.equalsIgnoreCase("UPDATED")){
                            profile = ProfileDB.updateProfile(profile);
                        	}
	                        request.setAttribute("CURRENT_PROFILE", profile);
	                        usr.getPersonnel().setPersonnelCategory(PersonnelCategoryDB.getPersonnelCategory(Integer.parseInt(request.getParameter("position"))));
                        	}else
                        		request.setAttribute("msg", "Position required.");
                      		}
                      else
                        request.setAttribute("msg", "Gender required.");
                   }
                   else
                    request.setAttribute("msg", "Home Phone Number required.");
                 }
                 else
                  request.setAttribute("msg", "Postal Code required.");
               }
               else
                request.setAttribute("msg", "Province required.");
             }
             else
              request.setAttribute("msg", "City/Town required.");
          }
          else
            request.setAttribute("msg", "Street Address required.");
          path = "profile.jsp";
          if(profile != null){
            path +="?op=update";
          }
      }
      else if(op.equalsIgnoreCase("VIEW")){
        if(request.getParameter("id") != null){
          Personnel p = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("id")));
          profile = ProfileDB.getProfile(p);
          if(profile != null){
            request.setAttribute("CURRENT_PROFILE", profile);
            path = "profile.jsp?op=view";
          }else{
            path = "claim_error.jsp?msg=No Profile on record for " + p.getFullNameReverse() + ". ";
          }
        }else{
          path = "claim_error.jsp?msg=You cannot view the requested profile.";
        }
      }
      else
      {
        request.setAttribute("msg", "Invalid option.");
        path = "profile.jsp";
      }
    }
    else
    {
      path = "profile.jsp";
      profile = ProfileDB.getProfile(usr.getPersonnel());
      if(profile != null){
        request.setAttribute("CURRENT_PROFILE", profile);
        path +="?op=update";
      }
    }
    
    return path;
  }
}