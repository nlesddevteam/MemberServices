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

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("CONTRACTOR") != null))
			validated = true;

		if (!validated)
			request.getRequestDispatcher("/MemberServices/BCS/login.jsp").forward(request, reponse);
		else {
			bcbean = (BussingContractorBean) session.getAttribute("CONTRACTOR");
		}
		return null;
	}
}
