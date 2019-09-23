package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminViewSystemDocumentsRequestHandler extends RequestHandlerImpl
{
	public AdminViewSystemDocumentsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}		
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
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
