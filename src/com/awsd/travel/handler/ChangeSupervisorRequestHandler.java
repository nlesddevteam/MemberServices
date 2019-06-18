package com.awsd.travel.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ChangeSupervisorRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    String path;
    HttpSession session = null;
    User usr = null;
    String op = "";
    boolean check = false;
    Personnel supervisor = null;
    TravelClaim claim = null;
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null)){
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))){
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }else{
      throw new SecurityException("User login required.");
    }
    claim = TravelClaimDB.getClaim(Integer.parseInt(request.getParameter("claim_id")));
    op = request.getParameter("op");
    if(op != null){
      if(op.equalsIgnoreCase("CONFIRM")){
          try{
              supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
          }catch(NumberFormatException e){
            supervisor = null;
          }
          if(supervisor == null){
            request.setAttribute("msg", "Please select supervisor.");
          }else if(supervisor.getPersonnelID() == claim.getSupervisor().getPersonnelID()){
            request.setAttribute("msg", "Selected supervisor already set.");
          }else{
        	  //all clear
        	  	check = TravelClaimDB.setSupervisor(claim, supervisor);
        	  	if(check){
	              request.setAttribute("SUCCESS", new Boolean(true));
	            }else{
	              request.setAttribute("msg", "Supervisor could not be changed.");
	            }
          }
      }else{
        request.setAttribute("msg", "Invalid option.");
      }
    }
    request.setAttribute("TRAVELCLAIM", claim);
    request.setAttribute("SUPERVISORS", new Supervisors());
    path = "change_supervisor.jsp";
    return path;
  }
}