package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingsBean;
import com.esdnl.webupdatesystem.newspostings.constants.NewsCategory;
import com.esdnl.webupdatesystem.newspostings.constants.NewsStatus;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsManager;

import javazoom.upload.UploadFile;

public class AddNewNewsPostingsRequestHandler extends RequestHandlerImpl {

	public AddNewNewsPostingsRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("news_category", "News Category is required."),
				new RequiredFormElement("news_location", "News Location is required."),
				new RequiredFormElement("news_title", "News Title is required."),
				new RequiredFormElement("news_description", "News Description is required."),
				new RequiredFormElement("news_status", "News Status is required."),
				new RequiredFormElement("news_date", "News Date is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		String filelocation = "";
		String docfilename = "";
		String photofilename = "";

		UploadFile file = null;
		try {
			if (form.get("op") == null) {
				Map<Integer, String> categorylist = new HashMap<Integer, String>();
				for (NewsCategory t : NewsCategory.ALL) {
					categorylist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("categorylist", categorylist);
				Map<Integer, String> statuslist = new HashMap<Integer, String>();
				for (NewsStatus t : NewsStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);

				LocationBean[] listregions = LocationManager.getLocationBeans();
				request.setAttribute("locationlist", listregions);
				path = "add_new_news_postings.jsp";

			}
			else {
				//check file
				if (form.uploadFileExists("news_photo")) {
					file = (UploadFile) form.getUploadFiles().get("news_photo");
					//save the file
					filelocation = "/../../nlesdweb/WebContent/includes/files/news/img/";
					photofilename = save_file("news_photo", filelocation);

				}
				//check mandatory fields
				if (validate_form()) {
					//save doc file if it exists
					if (form.uploadFileExists("news_documentation")) {
						file = (UploadFile) form.getUploadFiles().get("news_photo");
						//save the file
						filelocation = "/../../nlesdweb/WebContent/includes/files/news/doc/";
						docfilename = save_file("news_documentation", filelocation);
					}
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					NewsPostingsBean np = new NewsPostingsBean();
					np.setNewsCategory(NewsCategory.get(form.getInt("news_category")));
					if (form.getInt("news_location") == -1) {
						np.setNewsLocation(null);
					}
					else {
						if (form.getInt("news_location") > 0 && form.getInt("news_location") < 100) {
							//need to adjust for the leading zeros for numbers under 100
							StringBuilder sb = new StringBuilder();
							sb.append("00");
							sb.append(form.getInt("news_location"));
							np.setNewsLocation(LocationManager.getLocationBeanByString(sb.toString()));
							//np.setNewsLocation(LocationManager.getLocationBean(form.getInt("news_location")));
						}
						else {
							np.setNewsLocation(LocationManager.getLocationBean(form.getInt("news_location")));
						}

					}
					np.setNewsTitle(form.get("news_title").toString());
					np.setNewsDescription(form.get("news_description").toString());
					np.setNewsPhoto(photofilename);
					np.setNewsDocumentation(docfilename);
					np.setNewsExternalLink(form.get("news_external_link").toString());
					np.setNewsExternalLinkTitle(form.get("news_external_link_title").toString());
					np.setNewsStatus(NewsStatus.get(form.getInt("news_status")));
					np.setNewsDate(sdf.parse(form.get("news_date").toString()));
					np.setAddedBy(usr.getPersonnel().getFullNameReverse());
					np.setNewsPhotoCaption(form.get("news_photo_caption"));
					int id = NewsPostingsManager.addNewsPostings(np);
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (NewsCategory t : NewsCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
					Map<Integer, String> statuslist = new HashMap<Integer, String>();
					for (NewsStatus t : NewsStatus.ALL) {
						statuslist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);

					LocationBean[] listregions = LocationManager.getLocationBeans();
					request.setAttribute("locationlist", listregions);

					path = "add_new_news_postings.jsp";
					request.setAttribute("msg", "News Postings has been added");
				}
				else {
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (NewsCategory t : NewsCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
					Map<Integer, String> statuslist = new HashMap<Integer, String>();
					for (NewsStatus t : NewsStatus.ALL) {
						statuslist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);

					LocationBean[] listregions = LocationManager.getLocationBeans();
					request.setAttribute("locationlist", listregions);

					if (!validate_form()) {
						request.setAttribute("msg", validator.getErrorString());
					}
					path = "add_new_news_postings.jsp";

				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);

			path = "add_new_news_postings.jsp";

		}
		return path;
	}
}
