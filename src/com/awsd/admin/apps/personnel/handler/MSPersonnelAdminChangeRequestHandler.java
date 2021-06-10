package com.awsd.admin.apps.personnel.handler;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.awsd.admin.*;
import com.awsd.personnel.*;
import com.awsd.school.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;


public class MSPersonnelAdminChangeRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    Personnel tmp = null;
    PersonnelCategory cat = null;
    School s = null;
    int pid, sid,catid;
   
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))  {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW"))) {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    } else {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("pid") == null)  {
      throw new AdminException("Personnel ID Required.");
    } else {
      pid = Integer.parseInt(request.getParameter("pid"));
    }

    tmp = PersonnelDB.getPersonnel(pid);

    if(request.getParameter("update") == null)  {        
      request.setAttribute("Personnel", tmp);
      request.setAttribute("ALL_SCHOOLS", new Schools());
      path = "personnel_admin_change.jsp";
    } else {
    	
    	 if(request.getParameter("category") == null) {
    		 
           throw new AdminException("Category ID Required.");
         
    	 } else  {
        	 
           catid = Integer.parseInt(request.getParameter("category"));
        
         }
       
    	  cat = PersonnelCategoryDB.getPersonnelCategory(catid);       
          

         if(!cat.getPersonnelCategoryName().equalsIgnoreCase("TEACHER")) {
           tmp.setViewOnNextLogon(null);
         } else {
           tmp.setViewOnNextLogon("PROFILE");
         }
         if(request.getParameter("school.schoolID") == null)  {
           throw new AdminException("School ID Required.");
         } else {
           sid = Integer.parseInt(request.getParameter("school.schoolID"));
         }
         
         s = SchoolDB.getSchool(sid);          
         
    	String firstname = request.getParameter("firstname");
    	String lastname = request.getParameter("lastname");
    	String username = request.getParameter("username");
    	String email = request.getParameter("email");
    	
    	tmp.setPersonnelCategory(cat);
    	tmp.setSchool(s);    
    	tmp.setFirstName(firstname.toUpperCase());
    	tmp.setLastName(lastname.toUpperCase());
    	tmp.setUserName(username);
    	tmp.setEmailAddress(email);
    	
    	PersonnelDB.updatePersonnel(tmp);
    	request.setAttribute("msgOK", "SUCCESS: Profile updated!");
      path = "personnel_admin_view.jsp";
    }
    
    return path;
  }
}