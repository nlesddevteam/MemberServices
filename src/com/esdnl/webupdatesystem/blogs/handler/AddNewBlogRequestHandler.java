package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.UploadFile;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.blogs.bean.BlogsBean;
import com.esdnl.webupdatesystem.blogs.constants.BlogStatus;
import com.esdnl.webupdatesystem.blogs.dao.BlogsManager;

public class AddNewBlogRequestHandler extends RequestHandlerImpl {
	public AddNewBlogRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("blog_title", "Blog Title is required."),
				new RequiredFormElement("blog_date", "Blog Date is required."),
				new RequiredFormElement("blog_content", "Blog Content is required.")
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
		boolean fileok=false;
    	String filelocation="";
    	String contentfilename = "";
    	String photofilename="";
    	
		UploadFile file = null;
		try {
			if(form.get("op") == null)
			{
				Map<Integer,String> statuslist = new HashMap<Integer,String>();
				for(BlogStatus t : BlogStatus.ALL)
				{
					statuslist.put(t.getValue(),t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);
				path = "add_new_blog.jsp";
				
			}
			else{
				//check file
				if (form.uploadFileExists("blog_document"))
				{
					//save the file
	                filelocation="/../ROOT/includes/files/blog/doc/";
	                contentfilename = save_file("blog_document", filelocation);
	                	
	                
				}
				if (form.uploadFileExists("blog_photo"))
				{
					filelocation="/../ROOT/includes/files/blog/img/";
                	photofilename = save_file("blog_photo", filelocation);
	               
	                
				}
				//check mandatory fields
				if (validate_form()) {
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
					int id = BlogsManager.addBlog(bb);
					Map<Integer,String> statuslist = new HashMap<Integer,String>();
					
					for(BlogStatus t : BlogStatus.ALL)
					{
						statuslist.put(t.getValue(),t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);
					path = "add_new_blog.jsp";
					request.setAttribute("msg", "Blog has been added");
				}else{
					Map<Integer,String> statuslist = new HashMap<Integer,String>();
					
					for(BlogStatus t : BlogStatus.ALL)
					{
						statuslist.put(t.getValue(),t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);
					
					if(! validate_form())
					{
						request.setAttribute("msg", validator.getErrorString());
					}
					path = "add_new_blog.jsp";
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_new_blog.jsp";
		}
		return path;
	}
}
