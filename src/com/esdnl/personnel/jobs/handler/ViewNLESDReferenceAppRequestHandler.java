package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.NLESDReferenceAdminBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSManageBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSSupportBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceTeacherBean;
import com.esdnl.personnel.jobs.dao.NLESDReferenceAdminManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceExternalManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceGuideManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceSSManageManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceSSSupportManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceTeacherManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewNLESDReferenceAppRequestHandler extends PublicAccessRequestHandlerImpl  {
	public ViewNLESDReferenceAppRequestHandler() {
		
		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				
				
				String referenceType="";
				referenceType=form.get("reftype").toString();
				//now we load the correct page
				if(referenceType.equals("A"))
				{
					path = "view_nlesd_admin_reference.jsp";
					NLESDReferenceAdminBean ref = NLESDReferenceAdminManager.getNLESDReferenceAdminBean(form.getInt("id"));
		
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}else if(referenceType.equals("G"))
				{
					path = "view_nlesd_guide_reference.jsp";
					NLESDReferenceGuideBean ref = NLESDReferenceGuideManager.getNLESDReferenceGuideBean(form.getInt("id"));
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}else if(referenceType.equals("T") ){
					path = "view_nlesd_teacher_reference.jsp";
					NLESDReferenceTeacherBean ref = NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(form.getInt("id"));
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}else if(referenceType.equals("E") ){
					path = "view_nlesd_external_reference.jsp";
					NLESDReferenceExternalBean ref = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(form.getInt("id"));
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}else if(referenceType.equals("SS") ){
					path = "view_nlesd_support_reference.jsp";
					NLESDReferenceSSSupportBean ref = NLESDReferenceSSSupportManager.getNLESDReferenceSSSupportBean(form.getInt("id"));
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}else if(referenceType.equals("M") ){
					path = "view_nlesd_manage_reference.jsp";
					NLESDReferenceSSManageBean ref = NLESDReferenceSSManageManager.getNLESDReferenceSSManageBean(form.getInt("id"));
					request.setAttribute("REFERENCE_BEAN", ref);
					request.setAttribute("PROFILE", ref.getProfile());
				}
				

			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("FORM", form);
				request.setAttribute("msg", "Could not view reference.");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
		}
		return path;
	}
}
