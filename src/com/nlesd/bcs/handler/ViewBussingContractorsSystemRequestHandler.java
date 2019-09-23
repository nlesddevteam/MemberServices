package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewBussingContractorsSystemRequestHandler extends RequestHandlerImpl {
	public ViewBussingContractorsSystemRequestHandler()
	{
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		path = "index.jsp";
		
		return path;
	}
		
}
