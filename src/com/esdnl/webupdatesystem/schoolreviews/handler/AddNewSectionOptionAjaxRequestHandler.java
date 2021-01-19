package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionOptionBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionOptionManager;

public class AddNewSectionOptionAjaxRequestHandler extends RequestHandlerImpl{
	public AddNewSectionOptionAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("opttitle"),
				new RequiredFormElement("optsecid"),
				new RequiredFormElement("optsectype")
			});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		String message="";
			if (validate_form()) {
				try {
					//get fields
					SchoolReviewSectionOptionBean srb = new SchoolReviewSectionOptionBean();
					srb.setSectionOptionTitle(form.get("opttitle"));
					srb.setSectionOptionEmbed(form.get("optembed"));
					srb.setSectionOptionLink(form.get("optlink"));
					srb.setSectionOptionSectionId(form.getInt("optsecid"));
					srb.setSectionOptionAddedBy(usr.getLotusUserFullName());
					srb.setSectionOptionType(form.get("optsectype"));
					if(form.exists("optionid")) {
						srb.setSectionOptionId(form.getInt("optionid"));
						SchoolReviewSectionOptionManager.updateSchoolReviewSectionOption(srb);
					}else {
						SchoolReviewSectionOptionManager.addSchoolReviewSectionOption(srb);
					}
					
				}
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if(message == "") {
				//get the options and send them back
				ArrayList<SchoolReviewSectionOptionBean> list = SchoolReviewSectionOptionManager.getSchoolReviewSectionOptions(form.getInt("optsecid"), form.get("optsectype"));
				sb.append("<SOOPTIONS>");
				for(SchoolReviewSectionOptionBean sopt : list) {
					sb.append(sopt.toXml());
				}
				sb.append("</SOOPTIONS>");
			}else {
				sb.append("<SOOPTIONS>");
				sb.append("<SOOPTION>" + message + "</SOOPTION>");
				sb.append("</SOOPTION>");
			}
			
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}