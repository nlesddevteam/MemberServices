package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminViewSystemDocumentsRequestHandler extends RequestHandlerImpl
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }

	    TreeMap<Integer,String> items;
	    //get search by values
		items = DropdownManager.getDropdownValuesTM(12);
		request.setAttribute("dtypes", items);
		request.setAttribute("memos", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByType(64));
		request.setAttribute("policies", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByType(65));
		request.setAttribute("procedures", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByType(66));
		request.setAttribute("forms", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByType(67));
	    		
	    path = "admin_view_system_documents.jsp";
	    return path;
	  }
}
