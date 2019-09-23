package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminSearchContractsRequestHandler extends RequestHandlerImpl
{
	public AdminSearchContractsRequestHandler() {
			this.requiredPermissions = new String[] {
					"BCS-SYSTEM-ACCESS"
			};
	}
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
		super.handleRequest(request, response);
	    TreeMap<Integer,String> items;
	    items = DropdownManager.getDropdownValuesTM(15);
	    //remove option to search by region if regional admin
	    if(usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN") || usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL") || usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR")){
	    	items.remove(77);
	    }
	    request.setAttribute("sby", items);
	    items = DropdownManager.getDropdownValuesTM(13);
	    request.setAttribute("regions", items);
	    items = DropdownManager.getDropdownValuesTM(14);
	    request.setAttribute("types", items);

	    path = "admin_search_contracts.jsp";
	    return path;
	  }
}