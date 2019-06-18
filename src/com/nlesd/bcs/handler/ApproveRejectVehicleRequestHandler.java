package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ApproveRejectVehicleRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    Integer cid = Integer.parseInt(request.getParameter("cid"));
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
	    BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
  	   	vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(cid);
  	   	request.setAttribute("vehicle", vbean);
  	   	//now we get the dropdown values for viewing
  	   	if(vbean.getvMake() > 0){
  	   		request.setAttribute("vmake", DropdownManager.getDropdownItemText(vbean.getvMake()));
  	   	}
  	   	if(vbean.getvModel() > 0){
	   		request.setAttribute("vmodel", DropdownManager.getDropdownItemText(vbean.getvModel()));
	   	}
  	   	if(vbean.getvType() > 0){
	   		request.setAttribute("vtype", DropdownManager.getDropdownItemText(vbean.getvType()));
	   	}
  	   	if(vbean.getvSize() > 0){
	   		request.setAttribute("vsize", DropdownManager.getDropdownItemText(vbean.getvSize()));
	   	}
  	   	//now we get the documents
  	   	request.setAttribute("documents",BussingContractorVehicleDocumentManager.getBussingContractorVehicleDocumentsById(vbean.getId()));
  	   	//now we set the rel path
  	   	request.setAttribute("spath",request.getContextPath());
		path = "approve_vehicle.jsp";
		return path;
	  }
}
