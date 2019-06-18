package com.awsd.travel.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;
import com.awsd.travel.service.TravelBudgetService;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SaveTravelClaimChangesRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    HttpSession session = null;
    User usr = null;
    String path = "";
    TravelClaim claim = null;
    TravelClaimItem item = null;
    Iterator iter = null;
    int id = -1;
    boolean done = false;
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null)){
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))){
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }else{
      throw new SecurityException("User login required.");
    }
    try{
      id = Integer.parseInt(request.getParameter("id"));
    }catch(NumberFormatException e){
      id = -1;
    }
    if(id > 0){
      claim = TravelClaimDB.getClaim(id);
      if(claim != null){
        request.setAttribute("TRAVELCLAIM", claim);
        if(request.getParameter("op") != null){
          if(request.getParameter("op").equalsIgnoreCase("CONFIRM")){
            iter = claim.getItems().iterator();
            while(iter.hasNext()){
              item = (TravelClaimItem) iter.next();
              if(request.getParameter("item_" + item.getItemID()) != null){
                try{
                  item.setItemOther(Double.parseDouble(request.getParameter("item_" + item.getItemID()).replaceAll("[$,]","")));
                  done = TravelClaimItemsDB.updateClaimItem(claim, usr.getPersonnel(), item);
                }catch(Exception e){
                  e.printStackTrace(System.err);
                  done = true;
                }
              }
            }
            
            if(!done)
              request.setAttribute("msg", "Some changes could not be saved...");
            else
              request.setAttribute("msg", "Changes saved successfully...");
            
			request.setAttribute("TOTAL_CLAIMED",
					new Double(TravelClaimDB.getYearToDateTotalClaimed(claim.getPersonnel(), claim.getFiscalYear())));
			request.setAttribute("BUDGET", TravelBudgetService.getTravelBudget(claim.getPersonnel(), claim.getFiscalYear()));  
            path = "claim_details.jsp";
          }else
            path = "save_claim_changes.jsp";
        }else
          path = "save_claim_changes.jsp";
      }
      else
        path = "claim_error.jsp?msg=Claim cound not be found.";
    }
    else
      path = "claim_error.jsp?msg=Claim ID Required For SAVE CHANGES Operation.";
    return path;
  }
}