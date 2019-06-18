package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.security.User;
import com.esdnl.payadvice.bean.NLESDPayAdviceTeacherListBean;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeSecurityManager;
import com.esdnl.payadvice.dao.NLESDPayAdviceTeacherListManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdviceTeacherListRequestHandler extends RequestHandlerImpl {
	public ViewNLESDPayAdviceTeacherListRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-NORMAL"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				usr = (User) session.getAttribute("usr");
				String sin = "";
				System.out.println(usr.getPersonnel().getEmailAddress());
				sin = NLESDPayAdviceEmployeeSecurityManager.getEmployeeSIN(usr.getPersonnel().getEmailAddress());
		
				if(sin != null){
					String empnumber="0";
					if(sin.length() > 9){
						//replace - with nothing
						sin.replace("-","").replace(" ", "");
						List<NLESDPayAdviceTeacherListBean> list = new ArrayList<NLESDPayAdviceTeacherListBean>(NLESDPayAdviceTeacherListManager.getNLESDPayAdviceTeacherListBean(sin).values());
						Collections.sort(list);
						if(list != null){
							empnumber=list.get(0).getEmpNumber();
						}
						request.setAttribute("paygroups",list );
						request.setAttribute("empnum",empnumber );
						path = "view_teacher_list.jsp";
					}
					else if(sin.length() < 9){
						request.setAttribute("msg", "Invalid employee number, please contact Administrtor");
						path = "view_teacher_list.jsp";
					}
					else{
						List<NLESDPayAdviceTeacherListBean> list = new ArrayList<NLESDPayAdviceTeacherListBean>(NLESDPayAdviceTeacherListManager.getNLESDPayAdviceTeacherListBean(sin).values());
						if(!list.isEmpty()){
							Collections.sort(list);
							empnumber=list.get(0).getEmpNumber();
						}
						request.setAttribute("empnum",empnumber );
						request.setAttribute("paygroups", list);
						path = "view_teacher_list.jsp";
					}
				}else{
					request.setAttribute("msg", "Invalid employee number, please contact Administrtor");
					path = "view_teacher_list.jsp";
				}
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "view_teacher_list.jsp";
		}
		return path;
	}
}
