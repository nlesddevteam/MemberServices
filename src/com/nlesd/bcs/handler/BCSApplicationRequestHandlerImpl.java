package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.BypassLoginRequestHandlerImpl;
import com.esdnl.servlet.Form;
import com.nlesd.bcs.bean.BussingContractorBean;
public class BCSApplicationRequestHandlerImpl extends BypassLoginRequestHandlerImpl {

	protected BussingContractorBean bcbean;

	public BCSApplicationRequestHandlerImpl() {

		super();
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		boolean validated = false;
		form = new Form(request);
		path=null;
		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("CONTRACTOR") != null))
			validated = true;

		if (!validated) {
			session.setAttribute("CONTRACTOR", null);
			//session.setAttribute("scheck", true);
			//used with ajax requests
			reponse.setStatus(401);
			//request.getRequestDispatcher("contractorLogin.html").forward(request, reponse);
			sessionExpired=true;
			
		}else {
			bcbean = (BussingContractorBean) session.getAttribute("CONTRACTOR");
		}
		return path;
	}
	public boolean sessionExpired=false;//used to return ajax calls to the login screen
	
	
}
