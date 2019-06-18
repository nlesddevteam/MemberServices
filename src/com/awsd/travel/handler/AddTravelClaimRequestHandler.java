package com.awsd.travel.handler;

import com.awsd.common.*;
import com.awsd.personnel.*;
import com.awsd.personnel.pd.*;
import com.awsd.personnel.profile.*;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.travel.*;
import com.esdnl.sds.*;
import com.awsd.school.*;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddTravelClaimRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    String op = "";
    String year = "";
    int month = -1, claim_id = -1;
    boolean check = false;
    Personnel supervisor = null;

    TreeMap claims = null;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    
    op = request.getParameter("op");
    if(ProfileDB.getProfile(usr.getPersonnel()) == null)
      path = "profile.jsp";
    else if(op != null)
    {
      if(op.equalsIgnoreCase("YEAR-SELECT"))
      {
        year = request.getParameter("claim_year");
        if((year != null) && (!year.equals("SELECT YEAR")))
        {
          claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

          request.setAttribute("YEAR-CLAIMS", claims);
          request.setAttribute("YEAR-SELECT", year);

          try
          {
            request.setAttribute("SUPERVISOR", PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id"))));
          }
          catch(NumberFormatException e)
          {
            request.setAttribute("SUPERVISOR", null);
          }

          path = "add_claim.jsp";
        }
        else
          path = "add_claim.jsp";
      }
      else if(op.equalsIgnoreCase("CONFIRM"))
      {
        
        String claim_type = request.getParameter("claim_type");
        
        if((claim_type != null)&&(claim_type.equals("0"))) //this is a regular monthly claim.
        {
          try
          {
              supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
          }
          catch(NumberFormatException e)
          {
            supervisor = null;
          }
  
          if(supervisor == null)
          {
            request.setAttribute("msg", "Please select supervisor.");
  
            claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
            request.setAttribute("YEAR-CLAIMS", claims);
            request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
            
            path = "add_claim.jsp";
          }
          else
          {
            year = request.getParameter("claim_year");
            if((year == null) || (year.equalsIgnoreCase("SELECT YEAR")))
            {
              request.setAttribute("msg", "Please select fiscal year.");
  
              claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
              request.setAttribute("YEAR-CLAIMS", claims);
              request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
              request.setAttribute("SUPERVISOR", supervisor);
              
              path = "add_claim.jsp";
            }
            else
            {
              try
              {
                month = Integer.parseInt(request.getParameter("claim_month"));
              }
              catch(NumberFormatException e)
              {
                month = -1;
              }
  
              if(month == -1)
              {
                request.setAttribute("msg", "Please select fiscal month.");
  
                claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);
  
                request.setAttribute("YEAR-CLAIMS", claims);
                request.setAttribute("YEAR-SELECT", year);
                request.setAttribute("SUPERVISOR", supervisor);                
                
                path = "add_claim.jsp";
              }
              else
              {
                //all clear
  
                claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, year, month);
  
                if(claim_id > 0)
                {
                  request.setAttribute("msg", "Claim added successfully.");
                  request.setAttribute("NEW_CLAIM_ID", new Integer(claim_id));
                  
                  path = "add_claim.jsp";
                }
                else
                {
                  request.setAttribute("msg", "Claim could not be added.");
                  
                  claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);
                  
                  request.setAttribute("YEAR-CLAIMS", claims);
                  request.setAttribute("YEAR-SELECT", year);
                  request.setAttribute("SUPERVISOR", supervisor);
                  
                  path = "add_claim.jsp";
                }
              }
            }
          }
          
          request.setAttribute("CLAIM-TYPE", request.getParameter("claim_type"));
        }
        else if((claim_type != null)&&(claim_type.equals("1"))) //pd travel claim
        {
          String title =  request.getParameter("title");
          String desc = request.getParameter("desc");
          String sd = request.getParameter("start_date");
          String fd = request.getParameter("finish_date");
          
          if((title == null) || (title.trim().equals("")))
          {
            request.setAttribute("msg", "Please enter pd title.");
  
            claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
            request.setAttribute("YEAR-CLAIMS", claims);
            request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
            
            path = "add_claim.jsp";
          }
          else if((desc == null) || (desc.trim().equals("")))
          {
            request.setAttribute("msg", "Please enter pd description.");
  
            claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
            request.setAttribute("YEAR-CLAIMS", claims);
            request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
            
            path = "add_claim.jsp";
          }
          else if((sd == null) || (sd.trim().equals("")))
          {
            request.setAttribute("msg", "Please enter pd start date.");
  
            claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
            request.setAttribute("YEAR-CLAIMS", claims);
            request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
            
            path = "add_claim.jsp";
          }
          else if((fd == null) || (fd.trim().equals("")))
          {
            request.setAttribute("msg", "Please enter pd finish date.");
  
            claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
            request.setAttribute("YEAR-CLAIMS", claims);
            request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
            
            path = "add_claim.jsp";
          }
          else
          { 
            PersonnelPD pd = null;
            SDSSchoolInfo sds = null;
            School s = null;
            
            try
            {
              supervisor = usr.getPersonnel().getSupervisor();
              if(supervisor == null)
              {
                try
                {
                  supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("pd_supervisor_id")));
                }
                catch(NumberFormatException e)
                {
                  supervisor = null;
                }
              }
              
              if(supervisor == null)
              {
                request.setAttribute("msg", "No supervisor on record.");
  
                claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
                request.setAttribute("YEAR-CLAIMS", claims);
                request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
              
                path = "add_claim.jsp"; 
              }
              else
              {
                //all clear
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                
                pd = new PersonnelPD(title, desc, sdf.parse(sd), sdf.parse(fd));
                
                Calendar cal = Calendar.getInstance();
                cal.setTime(pd.getStartDate());
                year = Utils.getSchoolYear(cal);
                month = cal.get(Calendar.MONTH);
                
                try
                {
                  pd = PersonnelPDDB.addPD(usr.getPersonnel(), pd);
                }
                catch(PDException e)
                {
                  pd.setID(-1);
                }
                
                if(pd.getID() > 0)
                {                
                  claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, year, month, pd);
    
                  if(claim_id > 0)
                  {
                    request.setAttribute("msg", "Claim added successfully.");
                    request.setAttribute("NEW_CLAIM_ID", new Integer(claim_id));
                    
                    s = usr.getPersonnel().getSchool();
                    if(s != null)
                    {
                      sds = SDSSchoolInfoDB.getSDSSchoolInfo(s);
                      if(sds != null)
                      {
                        if((sds.getPDAcctCode() != null) && !sds.getPDAcctCode().trim().equals(""))
                          TravelClaimDB.setClaimGLAccount(TravelClaimDB.getClaim(claim_id), sds.getPDAcctCode());
                      }
                    }
                    path = "add_claim.jsp";
                  }
                  else
                  {
                    PersonnelPDDB.deletePD(pd.getID());
                    
                    request.setAttribute("msg", "Claim could not be added.");
                    
                    claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);
                    
                    request.setAttribute("YEAR-CLAIMS", claims);
                    request.setAttribute("YEAR-SELECT", year);
                    request.setAttribute("SUPERVISOR", supervisor);
                    
                    path = "add_claim.jsp";
                  }
                }
                else
                {
                  request.setAttribute("msg", "PD could not be added.");
                    
                  claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);
                    
                  request.setAttribute("YEAR-CLAIMS", claims);
                  request.setAttribute("YEAR-SELECT", year);
                  request.setAttribute("SUPERVISOR", supervisor);
                    
                  path = "add_claim.jsp";
                }
              }
            }
            catch(ParseException e)
            {
              request.setAttribute("msg", "Error receiving pd information.");
  
              claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());
  
              request.setAttribute("YEAR-CLAIMS", claims);
              request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
              
              path = "add_claim.jsp";
            }
          }
          
          request.setAttribute("CLAIM-TYPE", request.getParameter("claim_type"));
        }
        else
        {
          request.setAttribute("msg", "Invalid claim type.");

          claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

          request.setAttribute("YEAR-CLAIMS", claims);
          request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
        
          path = "add_claim.jsp"; 
        }
      }
      else
      {
        request.setAttribute("msg", "Invalid option.");

        claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

        request.setAttribute("YEAR-CLAIMS", claims);
        request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
        
        path = "add_claim.jsp";
      }
    }
    else
    {
      claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

      request.setAttribute("YEAR-CLAIMS", claims);
      request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
      
      path = "add_claim.jsp";
    }

    request.setAttribute("SUPERVISORS", TravelClaimSupervisorRuleDB.getTravelClaimSupervisors(usr.getPersonnel()));
    
    return path;
  }
}