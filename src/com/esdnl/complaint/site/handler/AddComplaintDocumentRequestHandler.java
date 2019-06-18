package com.esdnl.complaint.site.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.school.*;
import com.awsd.personnel.*;

import com.esdnl.util.*;
import com.esdnl.complaint.model.bean.*;
import com.esdnl.complaint.model.constant.*;
import com.esdnl.complaint.database.*;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class AddComplaintDocumentRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    ComplaintBean complaint = null;
    UploadBean bean = null;
    MultipartFormDataRequest mrequest = null;
    UploadFile file = null;
    Hashtable files = null;
    File f = null;
    File dir = null;
    String extension = null;
    
    try
    {
      session = request.getSession(false);
      if((session != null) && (session.getAttribute("usr") != null))
      {
        usr = (User) session.getAttribute("usr");
        if(!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
          || usr.getUserPermissions().containsKey("COMPLAINT-ADMIN")
          || usr.getUserPermissions().containsKey("COMPLAINT-VIEW")))
        {
          throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
        }
      }
      
        System.out.println(MultipartFormDataRequest.isMultipartFormData(request));
        if (MultipartFormDataRequest.isMultipartFormData(request))
        {
          mrequest = new MultipartFormDataRequest(request);
          
          String id = mrequest.getParameter("id");
          String doc_title = mrequest.getParameter("doc_title");
          String doc_file = mrequest.getParameter("doc_file");
          String confirmed = mrequest.getParameter("CONFIRMED");
          
          if(StringUtils.isEmpty(id))
            request.setAttribute("msg", "ERROR: Complaint ID is require to view a complaint record.");
          else
          {
            files = mrequest.getFiles();
          
            complaint = ComplaintManager.getComplaintBean(Integer.parseInt(id));
            if(!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
              || usr.getUserPermissions().containsKey("COMPLAINT-ADMIN"))
              && usr.getUserPermissions().containsKey("COMPLAINT-VIEW") && 
              !ComplaintManager.isComplaintBeanAssignedTo(complaint, usr.getPersonnel()))
              request.setAttribute("msg", "ERROR: You do not have the permissions necessary to view this complaint record.");
            else
            {
              if(!StringUtils.isEmpty(confirmed))
              {
                if(StringUtils.isEmpty(doc_title))
                  request.setAttribute("msg", "ERROR: Please enter a document title.");
                else if((files == null) || files.isEmpty())
                  request.setAttribute("msg", "ERROR: Please select a document to add.");
                else
                {
                  ComplaintDocumentBean doc = new ComplaintDocumentBean();
                  
                  doc.setTitle(doc_title);
                  doc.setComplaint(complaint);
                  doc.setUploadedBy(usr.getPersonnel());
                  
                  file = (UploadFile) files.get("doc_file");
                  doc.setFilename(Calendar.getInstance().getTimeInMillis() + file.getFileName().substring(file.getFileName().lastIndexOf(".")));  
                    
                  ComplaintDocumentManager.addComplaintDocumentBean(doc);
                  
                  if(!StringUtils.isEmpty(doc.getFilename()))
                  {
                    dir = new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/");
                    if(!dir.exists())
                      dir.mkdirs();
                      
                    bean = new UploadBean();
                    bean.setFolderstore(session.getServletContext().getRealPath("/") + "/complaint/documentation/");
                    bean.store(mrequest, "doc_file");
                    f = new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/" + file.getFileName());
                    f.renameTo(new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/"
                              + doc.getFilename()));
                            
                  }
                  request.setAttribute("RELOAD_PARENT", new Boolean(true));
                }
              }
              else
                request.setAttribute("COMPLAINT_BEAN", complaint);
            }
          }
        }
        else
        {
          if(StringUtils.isEmpty(request.getParameter("id")))
            request.setAttribute("msg", "ERROR: Complaint ID is require to view a complaint record.");
          else
            request.setAttribute("COMPLAINT_BEAN", ComplaintManager.getComplaintBean(Integer.parseInt(request.getParameter("id")), usr.getPersonnel()));
        }
      path = "admin_add_complaint_document.jsp";
      
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "admin_add_complaint_document.jsp";
    }
    
    return path;
  }
}