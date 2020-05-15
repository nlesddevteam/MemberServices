package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceAdminBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSManageBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceSSSupportBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceTeacherBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceAdminManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceExternalManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceGuideManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceSSManageManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceSSSupportManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceTeacherManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
public class EditNLESDReferenceRequestHandler extends RequestHandlerImpl {
	public EditNLESDReferenceRequestHandler() {
		requiredPermissions = new String[] {
					"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
			};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id"),
				new RequiredFormElement("rtype")
			});
		}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
				throws ServletException,
					IOException {
		super.handleRequest(request, response);
		//path = "add_nlesd_guide_reference.jsp";
		if (validate_form()) {
				try {
					String rtype= form.get("rtype");
					String pby ="";
					String uid="";
					if(rtype.equals("TEA")) {
						NLESDReferenceTeacherBean ref = NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_teacher_reference.jsp";
					}else if(rtype.equals("GUI")) {
						NLESDReferenceGuideBean ref = NLESDReferenceGuideManager.getNLESDReferenceGuideBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_guide_reference.jsp";
					}else if(rtype.equals("ADM")) {
						NLESDReferenceAdminBean ref = NLESDReferenceAdminManager.getNLESDReferenceAdminBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_admin_reference.jsp";
					}else if(rtype.equals("EXT")) {
						NLESDReferenceExternalBean ref = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_external_reference.jsp";
					}else if(rtype.equals("SUP")) {
						NLESDReferenceSSSupportBean ref = NLESDReferenceSSSupportManager.getNLESDReferenceSSSupportBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_support_reference.jsp";
					}else if(rtype.equals("MAN")) {
						NLESDReferenceSSManageBean ref = NLESDReferenceSSManageManager.getNLESDReferenceSSManageBean(form.getInt("id"));
						pby=ref.getProvidedBy();
						uid=ref.getProfile().getUID();
						request.setAttribute("REFERENCE_BEAN", ref);
						request.setAttribute("PROFILE", ref.getProfile());
						path = "add_nlesd_manage_reference.jsp";
					}
					
					// principal can only view if they have submited the reference or if the
					// applicant is shortlisted for a position
					// at their school.
					if (usr.checkRole("PRINCIPAL") || usr.checkRole("VICE PRINCIPAL")) {
						Personnel p = usr.checkRole("PRINCIPAL") ? usr.getPersonnel()
								: usr.getPersonnel().getSchool().getSchoolPrincipal();
						boolean authorized = false;
						if (pby.equalsIgnoreCase(p.getFullName())
								|| pby.equalsIgnoreCase(usr.getPersonnel().getFullName()))
							authorized = true;
						else {
							for (JobOpportunityBean opp : JobOpportunityManager.getJobOpportunityBeans(p.getSchool().getSchoolID())) {
								if (ApplicantProfileManager.getApplicantShortlistMap(opp).containsKey(uid)) {
									authorized = true;
									break;
								}
							}
						}
						if (!authorized) {
							try {
								new AlertBean(new com.awsd.security.SecurityException("Applicant Reference Illegal Access Attempted By "
										+ usr.getPersonnel().getFullNameReverse()));
							}
							catch (EmailException e) {}

							throw new com.awsd.security.SecurityException("Illegal Access Attempted By "
									+ usr.getPersonnel().getFullNameReverse());
						}
					}
					request.setAttribute("hidesearch",true);
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

