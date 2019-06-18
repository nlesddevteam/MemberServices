package com.esdnl.webupdatesystem.banners.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.UploadFile;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.banners.bean.BannersBean;
import com.esdnl.webupdatesystem.banners.bean.BannersException;
import com.esdnl.webupdatesystem.banners.dao.BannersManager;

public class UpdateBannerRequestHandler extends RequestHandlerImpl {
	public UpdateBannerRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("banner_rotation", "Banner Rotation is required."),
				new RequiredFormElement("banner_link", "Banner Link is required."),
				new RequiredFormElement("banner_status", "Banner Status is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		boolean deletedocfile=false;
    	String filelocation="";
    	String newfilename = "";
		UploadFile file = null;
		BannersBean bbo =null;
		try {
				//get copy of original to use for file update and deletion
				bbo = BannersManager.getBannerById(form.getInt("id"));
				//check file
				if (form.uploadFileExists("banner_file"))
				{
					file = (UploadFile) form.getUploadFiles().get("banner_file");
					//save the file
	                filelocation="/../ROOT/includes/files/banners/img/";
	                newfilename = save_file("banner_file", filelocation);
	                deletedocfile=true;
	                
				}else
				{
					//no new file uploaded so we will retrieve the exisiting info
					newfilename=bbo.getBannerFile();
				}
				if (validate_form()) {
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					BannersBean bb = new BannersBean();
					bb.setBannerFile(newfilename);
					bb.setBannerRotation(form.getInt("banner_rotation"));
					bb.setBannerLink(form.get("banner_link"));
					bb.setBannerStatus(Integer.parseInt(form.get("banner_status").toString()));
					
					if(form.exists("banner_show_public"))
					{
						bb.setBannerShowPublic(1);
					}else{
						bb.setBannerShowPublic(0);
					}
					if(form.exists("banner_show_staff"))
					{
						bb.setBannerShowStaff(1);
					}else{
						bb.setBannerShowStaff(0);
					}
					if(form.exists("banner_show_business"))
					{
						bb.setBannerShowBusiness(1);
					}else{
						bb.setBannerShowBusiness(0);
					}

					bb.setBannerCode(form.get("banner_code"));
					bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					bb.setId(form.getInt("id"));
					BannersManager.updateBanner(bb);
					//now delete the old file if needed
					if(deletedocfile)
					{
						delete_file(filelocation, bbo.getBannerFile());
					}
					path = "view_banner_details.jsp";
					request.setAttribute("banner", BannersManager.getBannerById(bbo.getId()));
					request.setAttribute("msg", "Banner has been updated");
					
				}else{
	
	
					request.setAttribute("msg", validator.getErrorString());
					path = "view_banner_details.jsp";
					request.setAttribute("banner", BannersManager.getBannerById(bbo.getId()));
				}
			
		}
		catch (Exception e) {
			try {
				request.setAttribute("banner", BannersManager.getBannerById(bbo.getId()));
			} catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (BannersException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			path = "view_banner_details.jsp";
			
		}
		return path;

	}
}