package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewFileManager;

public class UpdateFileAjaxRequestHandler extends RequestHandlerImpl{
	public UpdateFileAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("filetitle"),
				new RequiredFormElement("filedate"),
				new RequiredFormElement("filetype"),
				new RequiredFormElement("fileid"),
				new RequiredFormElement("sectionid")
			});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER","SCHOOL-REVIEW-ADMIN"
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
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//SchoolReviewFileBean fb = new SchoolReviewFileBean();	
					SchoolReviewFileBean fb = SchoolReviewFileManager.getSchoolReviewFileById(form.getInt("fileid"));
					fb.setId(form.getInt("fileid"));
					fb.setFileTitle(form.get("filetitle"));
					fb.setFileDate(sdf.parse(form.get("filedate").toString()));
					//now we check to see if a new file has been upload
					if(form.uploadFileExists("newfile")) {
						String filelocation = SchoolReviewFileBean.rootbasepath + "includes/files/schoolreview/sections/files/";
						String filename = save_file("newfile", filelocation);
						fb.setFilePath(filename);
					}//else we leave it the value it is now									
					SchoolReviewFileManager.updateSchoolReviewFile(fb);
					message="SUCCESS";
					
	            }
				catch (Exception e) {
					message=e.getMessage();
				}
			}else {
				message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
			}

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<SRFILES>");
			if(message == "SUCCESS") {
				//now we get a list of files sorted by date and return them
				ArrayList<SchoolReviewFileBean> list =SchoolReviewFileManager.getSchoolReviewFiles(form.getInt("sectionid"), "S");
				for(SchoolReviewFileBean bb : list) {
					sb.append(bb.toXml());
				}
			}else {
				sb.append("<SRFILE><MESSAGE>" + message + "<SRFILE><MESSAGE>");
			}
			sb.append("</SRFILES>");
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
