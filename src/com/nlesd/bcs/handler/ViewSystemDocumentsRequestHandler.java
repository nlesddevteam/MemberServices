package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewSystemDocumentsRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public ViewSystemDocumentsRequestHandler() {
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		TreeMap<Integer,String> items;
		//get search by values
		items = DropdownManager.getDropdownValuesTM(12);
		request.setAttribute("dtypes", items);
		request.setAttribute("memos", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByTypeCont(64));
		request.setAttribute("policies", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByTypeCont(65));
		request.setAttribute("procedures", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByTypeCont(66));
		request.setAttribute("forms", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsByTypeCont(67));

		path = "view_system_documents.jsp";

		return path;
	}
}
