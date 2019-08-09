package com.esdnl.webupdatesystem.banners.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.banners.bean.BannersBean;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;

import javazoom.upload.UploadFile;

public class AddNewBannerRequestHandler extends RequestHandlerImpl {

	public AddNewBannerRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("banner_rotation", "Banner Rotation is required."),
				new RequiredFormElement("banner_link", "Banner Link is required."),
				new RequiredFormElement("banner_status", "Banner Status is required.")
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
		boolean fileok = false;
		String filelocation = "";
		String newfilename = "";
		UploadFile file = null;
		try {
			if (form.get("op") == null) {

				path = "add_new_banner.jsp";

			}
			else {
				//check file
				if (form.uploadFileExists("banner_file")) {
					file = (UploadFile) form.getUploadFiles().get("banner_file");
					//save the file
					filelocation = "/../../nlesdweb/WebContent/includes/files/banners/img/";
					newfilename = save_file("banner_file", filelocation);
					fileok = true;

				}
				else {
					request.setAttribute("msg", "Please Select Banner File For Upload");
				}
				if (validate_form() && fileok) {
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					BannersBean bb = new BannersBean();
					bb.setBannerFile(newfilename);
					bb.setBannerRotation(form.getInt("banner_rotation"));
					bb.setBannerLink(form.get("banner_link"));
					bb.setBannerStatus(Integer.parseInt(form.get("banner_status").toString()));

					if (form.exists("banner_show_public")) {
						bb.setBannerShowPublic(1);
					}
					else {
						bb.setBannerShowPublic(0);
					}
					if (form.exists("banner_show_staff")) {
						bb.setBannerShowStaff(1);
					}
					else {
						bb.setBannerShowStaff(0);
					}
					if (form.exists("banner_show_business")) {
						bb.setBannerShowBusiness(1);
					}
					else {
						bb.setBannerShowBusiness(0);
					}

					bb.setBannerCode(form.get("banner_code"));
					bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					int id = BannersManager.addBanner(bb);
					path = "add_new_banner.jsp";
					request.setAttribute("msg", "Banner has been added");
				}
				else {

					if (!validate_form()) {
						request.setAttribute("msg", validator.getErrorString());
					}
					path = "add_new_banner.jsp";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_new_banner.jsp";
		}
		return path;
	}
}
