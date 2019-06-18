package com.awsd.efile.handler;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

import com.awsd.servlet.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.efile.*;
import com.awsd.school.*;
import javazoom.upload.*;

public class ImportDocumentRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    UploadBean upBean = null;
    UploadFile file = null;
    File f = null;
    MultipartFormDataRequest mrequest = null;
    int doctype=-1, docsub=-1, docgrade=-1, doccourse=-1, fid;
    String keyword1, keyword2, keyword3, keyword4;
    Grade g = null;

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
    try
    {
      upBean = new UploadBean();
      upBean.setFolderstore(ControllerServlet.EFILE_TMP_UPLOAD_DIR);

      if (MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);

        if(mrequest.getParameter("documentType") != null)
        {
          doctype = Integer.parseInt(mrequest.getParameter("documentType"));
        }
        else
        {
          throw new EFileException("Document Type required.");
        }

        if(mrequest.getParameter("documentSubject") != null)
        {
          docsub = Integer.parseInt(mrequest.getParameter("documentSubject"));
        }
        else
        {
          throw new EFileException("Document Subject required.");
        }

        if(mrequest.getParameter("gradeLevel") != null)
        {
          docgrade = Integer.parseInt(mrequest.getParameter("gradeLevel"));
        }
        else
        {
          throw new EFileException("Grade level required.");
        }

        if(mrequest.getParameter("documentCourse") != null)
        {
          doccourse = Integer.parseInt(mrequest.getParameter("documentCourse"));
        }
        else
        {
          g = GradeDB.getGrade(docgrade);
          if(g.getGradeName().equalsIgnoreCase("LEVEL I") 
            || g.getGradeName().equalsIgnoreCase("LEVEL II")
            || g.getGradeName().equalsIgnoreCase("LEVEL III"))
          {
            throw new EFileException(g.getGradeName() + " requires a course ID.");
          }
          else
          {
            doccourse = -1;
          }
        }

        keyword1 = mrequest.getParameter("keyword1");
        keyword2 = mrequest.getParameter("keyword2");
        keyword3 = mrequest.getParameter("keyword3");
        keyword4 = mrequest.getParameter("keyword4");
        if((keyword1==null)&&(keyword2==null)&&(keyword3==null)&&(keyword4==null))
        {
          throw new EFileException("Atleast one keyword is required.");
        }
        
        Hashtable files = mrequest.getFiles();
        if ( (files != null) || (!files.isEmpty()) )
        {
          file = (UploadFile) files.get("uploadfile");
          if (doccourse == -1)
          {
            fid = DocumentDB.addDocument(new Document(doctype, docsub, docgrade, usr.getPersonnel().getPersonnelID(), new String[]{keyword1, keyword2, keyword3, keyword4}));
          }
          else
          {
            fid = DocumentDB.addDocument(new Document(doctype, docsub, docgrade, doccourse, usr.getPersonnel().getPersonnelID(), new String[]{keyword1, keyword2, keyword3, keyword4}));
          }
          upBean.store(mrequest, "uploadfile");
          f = new File(ControllerServlet.EFILE_TMP_UPLOAD_DIR + "/" + file.getFileName());
          
          if(file.getFileName().lastIndexOf(".") > -1)
          {
            f.renameTo(new File(ControllerServlet.EFILE_CONVERT_INPUT_DIR + "/" + fid + file.getFileName().substring(file.getFileName().lastIndexOf("."))));
          }
          else
          {
            f.renameTo(new File(ControllerServlet.EFILE_CONVERT_INPUT_DIR + "/" + fid ));
          }
        }
        else
        {
          throw new EFileException("No File Uploaded");
        }
        
        request.setAttribute("msg", "Resource imported successfully.");
      }
    }
    catch(UploadException e)
    {
      System.err.println(e);
      throw new EFileException(e.toString());
    }

    
    path = "importDocuments.jsp";
    
    return path;
  }
}