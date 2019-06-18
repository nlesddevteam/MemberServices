package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javazoom.upload.UploadFile;
import com.awsd.school.dao.RegionManager;
import com.esdnl.personnel.jobs.bean.OtherJobOpportunityBean;
import com.esdnl.personnel.jobs.constants.PostingType;
import com.esdnl.personnel.jobs.dao.OtherJobOpportunityManager;
import com.esdnl.util.StringUtils;
import com.esdnl.servlet.RequestHandlerImpl;
public class PostJobOtherRequestHandler extends RequestHandlerImpl {
	public PostJobOtherRequestHandler()
	{
		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		//call the superclass's constructor to validate the permissions and initialize variables
		super.handleRequest(request, response);
		String path="admin_post_job_other.jsp";
		UploadFile file = null;
		Hashtable files = null;
		try {
				//String position_title = mrequest.getParameter("ad_title");
				String position_title = form.get("ad_title");
				//String comp_end_date = mrequest.getParameter("ad_comp_end_date");
				String comp_end_date = form.get("ad_comp_end_date");
				//String region = mrequest.getParameter("region");
				String region = form.get("region");
				//String posting_type = mrequest.getParameter("posting_type");
				String posting_type = form.get("posting_type");
				//String original_file = mrequest.getParameter("originalfile");
				String original_file = form.get("originalfile");
				//files = mrequest.getFiles();
				files =form.getUploadFiles();
				//now we validate the fields
				//first we use the validator for the string fields
				if (StringUtils.isEmpty(position_title)) {
					request.setAttribute("msg", "Position title is a required field.");
					return path = "admin_post_job_other.jsp";
				}
				else if (StringUtils.isEmpty(comp_end_date)) {
					request.setAttribute("msg", "Competition end date is a required field.");
					return path = "admin_post_job_other.jsp";
				}//if this is an add then we check for a file, if it is edit (original file is populated then we skip validation
				else if (((files == null) || (files.size() <= 0)) && (original_file != null) ) {
					request.setAttribute("msg", "Select job ad file.");
					return path = "admin_post_job_other.jsp";
				}//if this is an add then we check for a file, if it is edit (original file is populated then we skip validation
				else if ((((file = (UploadFile) files.get("ad_file")) == null) || (file.getFileSize() <= 0)) && (original_file == null)){
					request.setAttribute("msg", "Job ad file is empty. Please select job ad file.");
					return path = "admin_post_job_other.jsp";
				}//if this is an add then we check for a pdf file, if it is edit (original file is populated then we skip validation
				else if (!((file = (UploadFile) files.get("ad_file")) == null) && (original_file == null) ) {
					if (!(file = (UploadFile) files.get("ad_file")).getFileName().toLowerCase().endsWith(".pdf"))
					{
					request.setAttribute("msg", "Job ad file must be a PDF. Please select job ad file.");
					return path = "admin_post_job_other.jsp";
					}
				}
					SimpleDateFormat sdf_ced = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
					String base_path =  "../ROOT/employment/doc/";
					OtherJobOpportunityBean opp = new OtherJobOpportunityBean();
					opp.setTitle(position_title);
					opp.setEndDate(sdf_ced.parse(comp_end_date));
					if (!StringUtils.isEmpty(region))
						opp.setRegion(RegionManager.getRegionBean(Integer.parseInt(region)));
					else
						opp.setRegion(RegionManager.getRegionBean(5));
					if (!StringUtils.isEmpty(posting_type))
						opp.setPostingType(PostingType.get(Integer.parseInt(posting_type)));
					else
						opp.setPostingType(PostingType.EXTERNAL_ONLY);
					if (form.get("edit") == null) {
						//save the file
						opp.setFilename(save_file("ad_file",base_path));
						//add the job to the database
						OtherJobOpportunityManager.addOtherJobOpportunityBean(opp);
						request.setAttribute("JOB_OPP", opp);
						request.setAttribute("msg", "Job \"" + opp.getTitle() + "\" posted successfully.");
					}else {
						//check to see if a new file is uploaded
						opp.setId(Integer.parseInt(form.get("ad_comp_num")));
						//String filename =file.getFileName();
						if(file.getFileName() != null)
						{
							//save the file
							opp.setFilename(save_file("ad_file",base_path));
							//delete the old file
							this.delete_file(base_path, original_file);
						}else{
							//set the filename to the old file name
							opp.setFilename(original_file);
						}
						//call the update function
						OtherJobOpportunityManager.updateOtherJobOpportunityBean(opp);
						request.setAttribute("msg", "Job " + opp.getTitle() + " updated successfully.");
					}
					request.setAttribute("JOB_OPP", opp);
					path = "admin_post_job_other.jsp";
		}
		catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			path = "admin_post_job_other.jsp";
		}
		return path;
	}
}