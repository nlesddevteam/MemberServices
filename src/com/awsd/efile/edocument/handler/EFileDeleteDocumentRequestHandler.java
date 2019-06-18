package com.awsd.efile.edocument.handler;

import com.awsd.efile.*;
import com.awsd.efile.edocument.Document;
import com.awsd.efile.edocument.DocumentDB;
import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class EFileDeleteDocumentRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Document doc = null;
    boolean done = false;
    final int id;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("EFILE-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    if(request.getParameter("id") == null)
    {
      throw new EFileException("Docuemnt ID required for delete operation.");
    }

    doc = DocumentDB.getDocument(Integer.parseInt(request.getParameter("id")));

    if(!(usr.getUserPermissions().containsKey("EFILE-DELETE-DOCUMENT") || (doc.getPersonnel().getPersonnelID() == usr.getPersonnel().getPersonnelID())))
    {
      throw new SecurityException("Illegal Delete attemp of EFILE RESOURCE [" + usr.getLotusUserFullName() + "]");
    }
    
    id = doc.getDocumentID();
    //DocumentKeywordsDB.deleteDocumentKeywords(doc);

    done = DocumentDB.deleteDocument(doc);

    if(done)
      {
        //delete physical files.
        File doc_dir = new File(ControllerServlet.EFILE_CONVERT_OUTPUT_DIR);
        if(doc_dir.exists())
        {
          File files[] = doc_dir.listFiles(new EFileFilenameFilter(doc.getDocumentID()));

          boolean deleted;
          for(int i=0; i < files.length; i++)
          {
            deleted = files[i].delete();
            if(!deleted)
            {
              throw new DocumentException("deleteDocument: Could not delete physical file [" + files[i].getAbsolutePath()+"]");
            }
          }
        }
        else
        {
          throw new DocumentException("deleteDocument: " + doc_dir.getAbsolutePath() + " does not exist.");
        }
      }
      else
      {
        throw new DocumentException("deleteDocument: could not delete document with id=" + id);
      }

    path = "searchRepository.jsp";
      
    return path;
  }
}