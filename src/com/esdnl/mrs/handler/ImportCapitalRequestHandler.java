package com.esdnl.mrs.handler;

import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.text.*;
import com.awsd.school.*;


public class ImportCapitalRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String line = null;
    String[] fields = null;
    MaintenanceRequest req = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    ControllerServlet servlet = null;
    try
    {
      servlet = new ControllerServlet();
      
      BufferedReader br = new BufferedReader(
        new FileReader("C:/Documents and Settings/Chris.ESDHQ/Desktop/ReportCapitalExport.txt"));
        
      while((line=br.readLine()) != null)
      {
        fields = line.split(",");
        
        req = new MaintenanceRequest(new RequestType(fields[2]), new StatusCode("UNASSIGNED"), 
          fields[3].trim(), sdf.parse(fields[4]),(((fields[6]!=null)&& !fields[7].trim().equals(""))?fields[7]:"N/A"),
          4442, SchoolDB.getSchoolFromDeptId(Integer.parseInt(fields[1])).getSchoolID(), 1);
        
        req.setRequestCategory(new RequestCategory("CAPITAL"));
        req.setCatiptalType(new CapitalType("DEPARTMENT"));
        req.setCapitalPriority(1);
        req.setEstimatedCost(Double.parseDouble(((fields[7]!=null)&&!fields[7].trim().equals(""))?fields[7]:"0.0"));
        
        //System.err.println(req);
        MaintenanceRequestDB.addMaintenanceRequestCapital(req);
        
      }  
    }
    catch(Exception e)
    {
      e.printStackTrace(System.err);
    }
    
    return "";
  }
}