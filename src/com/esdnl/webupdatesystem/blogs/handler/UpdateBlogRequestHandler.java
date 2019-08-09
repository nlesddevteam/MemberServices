package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.blogs.bean.BlogsBean;
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;

import javazoom.upload.UploadFile;

public class UpdateBlogRequestHandler extends RequestHandlerImpl {

	public UpdateBlogRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("blog_title", "Blog Title is required."),
				new RequiredFormElement("blog_date", "Blog Date is required."),
				new RequiredFormElement("blog_content", "Blog Content is required.")
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
		boolean fileokp = false;
		boolean fileokd = false;
		String contentfilename = "";
		String photofilename = "";
		UploadFile file = null;
		try {
			//get copy of original to use for file update and deletion
			BlogsBean bbb = BlogsManager.getBlogById(form.getInt("id"));
			//check file
			if (form.uploadFileExists("blog_document")) {
				file = (UploadFile) form.getUploadFiles().get("blog_document");
				if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
					request.setAttribute("msg", "Only PDF Tender Files Accepted");
				}
				else {
					//save the file
					filelocation = "/../../nlesdweb/WebContent/includes/files/blog/doc/";
					contentfilename = save_file("blog_document", filelocation);
					fileokd = true;
				}
			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				contentfilename = bbb.getBlogDocument();
				fileokd = true;
			}
			if (form.uploadFileExists("blog_photo")) {
				filelocation = "/../../nlesdweb/WebContent/includes/files/blog/img/";
				photofilename = save_file("blog_photo", filelocation);
				fileokp = true;

			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				photofilename = bbb.getBlogPhoto();
				fileokp = true;

			}
			//check mandatory fields
			if (validate_form() && fileokd && fileokp) {
				//parse the fields
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				BlogsBean bb = new BlogsBean();
				bb.setBlogTitle(form.get("blog_title").toString());
				bb.setBlogDate(sdf.parse(form.get("blog_date").toString()));
				bb.setBlogContent(form.get("blog_content").toString());
				bb.setBlogPhoto(photofilename);
				bb.setBlogDocument(contentfilename);
				bb.setBlogStatus(BlogStatus.get(Integer.parseInt(form.get("blog_status").toString())));
				bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				bb.setBlogPhotoCaption(form.get("blog_photo_caption"));
				bb.setId(form.getInt("id"));
				BlogsManager.updateBlog(bb);
				//now remove the origninal file from server
				if (!(bbb.getBlogPhoto() == null) && !(bb.getBlogPhoto() == null)) {
					if (!bbb.getBlogPhoto().equals(bb.getBlogPhoto())) {
						filelocation = "/../../nlesdweb/WebContent/includes/files/blog/img/";
						delete_file(filelocation, bbb.getBlogPhoto());
					}
				}
				if (!(bbb.getBlogDocument() == null) && !(bb.getBlogDocument() == null)) {
					if (!bbb.getBlogDocument().equals(bb.getBlogDocument())) {
						filelocation = "/../../nlesdweb/WebContent/includes/files/blog/doc/";
						delete_file(filelocation, bbb.getBlogDocument());
					}
				}
				Map<Integer, String> statuslist = new HashMap<Integer, String>();

				for (BlogStatus t : BlogStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}

				request.setAttribute("statuslist", statuslist);
				request.setAttribute("blog", BlogsManager.getBlogById(form.getInt("id")));
				path = "view_blog_details.jsp";
				request.setAttribute("msg", "Blog has been updated");
			}
			else {
				Map<Integer, String> statuslist = new HashMap<Integer, String>();
				for (BlogStatus t : BlogStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);
				if (!validate_form()) {
					request.setAttribute("msg", validator.getErrorString());
				}
				request.setAttribute("blog", BlogsManager.getBlogById(form.getInt("id")));
				path = "view_blog_details.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				request.setAttribute("blog", BlogsManager.getBlogById(form.getInt("id")));
				Map<Integer, String> statuslist = new HashMap<Integer, String>();

				for (BlogStatus t : BlogStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}

				request.setAttribute("statuslist", statuslist);
			}
			catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BlogsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			path = "view_blog_details.jsp";

		}
		return path;
	}
}
