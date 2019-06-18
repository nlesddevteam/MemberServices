package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewPolicyDetailsRequestHandler extends RequestHandlerImpl {
	public ViewPolicyDetailsRequestHandler() {

}
@Override
public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
		throws ServletException,
			IOException {
	super.handleRequest(request, reponse);
    try {
    	request.setAttribute("policy", PolicyManager.getPolicyById(Integer.parseInt(request.getParameter("id").toString())));
    }catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Fund3Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    path = "view_policy_details.jsp";
    return path;
}
}
