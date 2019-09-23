package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminViewSystemDocumentRequestHandler extends RequestHandlerImpl
{
	public AdminViewSystemDocumentRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid")
			});
	}	
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			TreeMap<Integer,String> items;
			int docid = form.getInt("vid");
			//get search by values
			items = DropdownManager.getDropdownValuesTM(12);
			request.setAttribute("dtypes", items);
			request.setAttribute("document", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentById(docid));
			request.setAttribute("dpath","../BCS/documents/system/");

			path = "admin_view_system_doc.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}

		return path;
	}
}
