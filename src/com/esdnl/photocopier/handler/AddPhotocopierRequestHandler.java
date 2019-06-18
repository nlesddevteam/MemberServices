package com.esdnl.photocopier.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import com.esdnl.photocopier.bean.*;
import com.esdnl.photocopier.dao.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddPhotocopierRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PHOTOCOPIER-VIEW")
        || usr.getUserPermissions().containsKey("PHOTOCOPIER-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    try
    {
      String brand = request.getParameter("brand");
      String model = request.getParameter("model_num");
      String year_acquired = request.getParameter("year_acquired");
      String acquired_from = request.getParameter("acquired_from");
      String purchased = request.getParameter("purchased");
      String lease_term = request.getParameter("lease_term");
      String service_contract = request.getParameter("service_contract");
      String contract_start_date = request.getParameter("contract_start_date");
      String contract_term = request.getParameter("contract_term");
      String serviced_by = request.getParameter("serviced_by");
      String average_service_time = request.getParameter("average_service_time");
      String num_copies = request.getParameter("num_copies");
      String effective_date = request.getParameter("effective_date");
      String other_comments = request.getParameter("other_comments");
      
      if(StringUtils.isEmpty(brand))
      {
        request.setAttribute("msg", "BRAND is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(model))
      {
        request.setAttribute("msg", "MODEL# is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(year_acquired))
      {
        request.setAttribute("msg", "YEAR ACQUIRED is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(purchased))
      {
        request.setAttribute("msg", "PURCHASED is a required field.");
        path = "add_copier.jsp";
      }
      else if(purchased.equals("0") && StringUtils.isEmpty(lease_term))
      {
        request.setAttribute("msg", "LEASE TERM is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(service_contract))
      {
        request.setAttribute("msg", "HAS SERVICE CONTRACT is a required field.");
        path = "add_copier.jsp";
      }
      else if(service_contract.equals("1") && StringUtils.isEmpty(contract_start_date))
      {
        request.setAttribute("msg", "CONTRACT START DATE is a required field.");
        path = "add_copier.jsp";
      }
      else if(service_contract.equals("1") && StringUtils.isEmpty(contract_term))
      {
        request.setAttribute("msg", "CONTRACT TERM is a required field.");
        path = "add_copier.jsp";
      }
      else if(service_contract.equals("1") && StringUtils.isEmpty(serviced_by))
      {
        request.setAttribute("msg", "SERVICED BY is a required field.");
        path = "add_copier.jsp";
      }
      else if(service_contract.equals("1") && StringUtils.isEmpty(average_service_time))
      {
        request.setAttribute("msg", "AVERAGE SERVICE TIME is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(num_copies))
      {
        request.setAttribute("msg", "# COPIES is a required field.");
        path = "add_copier.jsp";
      }
      else if(StringUtils.isEmpty(effective_date))
      {
        request.setAttribute("msg", "EFFECTIVE_DATE is a required field.");
        path = "add_copier.jsp";
      }
      else
      {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        PhotocopierBean copier = null;
        if(request.getParameter("id") == null)
        {
          copier = new PhotocopierBean();
          if(!usr.getUserPermissions().containsKey("PHOTOCOPIER-ADMIN-VIEW"))
            copier.setSchoolId(usr.getPersonnel().getSchool().getSchoolID());
        }
        else
          copier = PhotocopierManager.getPhotocopierBean(Integer.parseInt(request.getParameter("id")));
          
        copier.setBrandId(Integer.parseInt(brand));
        copier.setModelNumber(model);
        copier.setYearAcquired(Integer.parseInt(year_acquired));
        copier.setAcquiredFrom(Integer.parseInt(acquired_from));
        copier.setPurchased(purchased.equals("1"));
        if(!copier.isPurchased())
          copier.setLeaseTerm(Integer.parseInt(lease_term));
        copier.setServiceContract(service_contract.equals("1"));
        if(copier.hasServiceContract())
        {
          copier.setContractDate(sdf.parse(contract_start_date));
          copier.setContractTerm(Integer.parseInt(contract_term));
          copier.setServicedBy(Integer.parseInt(serviced_by));
          copier.setAverageServiceTime(Double.parseDouble(average_service_time));
        }
        copier.setNumberCopies(Integer.parseInt(num_copies));
        copier.setEffectiveDate(sdf.parse(effective_date));
        if(!StringUtils.isEmpty(other_comments))
          copier.setOtherComments(other_comments);
        
        if(request.getParameter("id") == null)
        {
          PhotocopierManager.addPhotocopierBean(copier);
        
          request.setAttribute("msg", "Photocopier added successfully.");  
        
          path = "add_copier.jsp";
        }
        else
          PhotocopierManager.updatePhotocopierBean(copier);
        
          request.setAttribute("msg", "Photocopier updated successfully.");  
        
          path = "view_copiers.jsp";
      }
    }
    catch(ParseException e)
    {
      request.setAttribute("msg", "Invalid data format.");
      path = "admin_post_job.jsp";
    }
    catch(PhotocopierException e)
    {
      request.setAttribute("msg", "Could not add photocopier.");
      path = "add_copier.jsp";
    }
    

    return path;
  }
}