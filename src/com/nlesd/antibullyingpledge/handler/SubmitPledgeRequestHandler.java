package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeBean;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;
public class SubmitPledgeRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String path = null;
		AntiBullyingPledgeBean bpb = new AntiBullyingPledgeBean();
		try {
			
			bpb.setFirstName(form.get("firstname"));
			bpb.setLastName(form.get("lastname"));
			bpb.setEmail(form.get("email"));
			bpb.setConfirmEmail(form.get("confirmemail"));
			String selectschool = form.get("schoolselect");
			String selectgrade = form.get("gradeselect");
			boolean valid=true;
			//check to see if the question
			if (StringUtils.isEmpty(bpb.getFirstName())) {
					
				valid=false;
				request.setAttribute("msg","Please enter your First Name!");
				//request.setAttribute("msg", "Please enter your First Name!");
			}else if (StringUtils.isEmpty(bpb.getLastName())) {
				request.setAttribute("msg","Please enter your Last Name!");
				valid=false;
				//request.setAttribute("msg", "Please enter your Last Name!");
			}else if (StringUtils.isEmpty(bpb.getEmail())) {
				request.setAttribute("msg","Please enter your email!");
				valid=false;
				//request.setAttribute("msg", "Please select your Grade!");
			}else if (StringUtils.isEmpty(bpb.getConfirmEmail())) {
				request.setAttribute("msg","Please confirm your email!");
				valid=false;
				//request.setAttribute("msg", "Please select your Grade!");
			}else{
				if (StringUtils.isEmpty(selectgrade)) {
					request.setAttribute("msg","Please select your Grade!");
					valid=false;
						//request.setAttribute("msg", "Please select your Grade!");
				}else{
						if(selectgrade.equals("-1"))
						{
							request.setAttribute("msg","Please select your Grade!");
							valid=false;
						}
				}
					if (StringUtils.isEmpty(selectschool)) {
						request.setAttribute("msg","Please select your School!");
						valid=false;
					//request.setAttribute("msg", "Please select your School!");
					}else{
							if(selectschool.equals("-1"))
							{
								request.setAttribute("msg","Please select your School!");
								valid=false;
							}
					}
					if (!StringUtils.isEmpty(bpb.getEmail()) && !StringUtils.isEmpty(bpb.getConfirmEmail()) )
					{
						//check to see if they are the same
						if(!bpb.getEmail().equals(bpb.getConfirmEmail()))
						{
							request.setAttribute("msg","Email and Confirm Email do not match!");
							valid=false;
						}else
						{
						//check format
						if(!EmailBean.isValidEmailAddress(bpb.getEmail()))
						{
							request.setAttribute("msg","Invalid Email Address!");
							valid=false;
						}
						
					}
					//check to see if they already submitted
					if(!AntiBullyingPledgeManager.checkPledgeEmail(bpb.getEmail()))
					{
						request.setAttribute("msg","Email Address Has Already Submitted A Pledge!");
						valid=false;
					}
				}
				}
				if (valid)
				{
						Date  d = new Date();
						bpb.setFkSchool(Integer.parseInt(selectschool));
						bpb.setGradeLevel(Integer.parseInt(selectgrade));
						bpb.setDateSubmittedUser(d);
						//create unique string
						//take first two initial firstname
						StringBuffer sb = new StringBuffer();
						if(bpb.getFirstName().length() > 2)
						{
						sb.append(bpb.getFirstName().substring(0,2));
						}else{
							sb.append(bpb.getFirstName().substring(0));
						}
						//now the date\
						SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
						sb.append(sdf.format(d));
						//now first two intial last name
						if(bpb.getLastName().length() > 2)
						{
						sb.append(bpb.getLastName().substring(0,2));
						}else{
							sb.append(bpb.getLastName().substring(0));
						}
						//now the time
						sdf.applyPattern("HHmmss");
						sb.append(sdf.format(d));
						sb.append(bpb.getEmail().substring(2,5));
						bpb.setCancellation_Code(sb.toString().toUpperCase());
						bpb.setSchoolImage(bpb.getFkSchool()+ ".jpg");
						AntiBullyingPledgeBean abpb = AntiBullyingPledgeManager.addBullyingPledgeBean(bpb);
						abpb.setPk(-1);
						request.setAttribute("BULL", abpb);
						//send confirmation email
						AntiBullyingPledgeManager.sendConfirmationEmail(abpb);
						//transfer to confirmation screen
						path = "addantibullyingpledge.jsp?ptype=submitted";
				}else
				{
					//request.setAttribute("msg", errormessage);
					request.setAttribute("BULL", bpb);
					path = "addantibullyingpledge.jsp";
				}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not add applicant profileother information.");
			path = "addantibullyingpledge.jsp";
		}
		System.out.println(path);
		return path;
	}
}
