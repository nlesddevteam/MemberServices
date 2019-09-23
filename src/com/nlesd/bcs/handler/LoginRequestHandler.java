package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
public class LoginRequestHandler extends PublicAccessRequestHandlerImpl {
	public LoginRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		
		super.handleRequest(request, response);
		String path = "login.jsp";
		
		return path;
	}
}