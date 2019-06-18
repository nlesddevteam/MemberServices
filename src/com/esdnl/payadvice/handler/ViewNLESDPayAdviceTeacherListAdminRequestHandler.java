package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdviceTeacherListBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceTeacherListManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdviceTeacherListAdminRequestHandler extends RequestHandlerImpl { 
	public ViewNLESDPayAdviceTeacherListAdminRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				String empnumber = form.get("empnumber");
				if(empnumber != null)
				{
					List<NLESDPayAdviceTeacherListBean> list = NLESDPayAdviceTeacherListManager.getNLESDPayAdviceTeacherListAdminBean(empnumber);
					if(!list.isEmpty()){
						Collections.sort(list);
					}
					request.setAttribute("employees", list);
					path = "view_teacher_list_admin.jsp";
			
				}else{
					request.setAttribute("msg", "Invalid employee number, please contact Administrtor");
					path = "view_teacher_list_admin.jsp";
				}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_teacher_list_admin.jsp";
		}
		return path;
	}
}
