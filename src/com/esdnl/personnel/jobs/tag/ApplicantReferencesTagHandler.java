package com.esdnl.personnel.jobs.tag;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.concurrent.TimeUnit;  

public class ApplicantReferencesTagHandler extends TagSupport
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    ApplicantProfileBean profile = null;
    ApplicantSupervisorBean[] beans = null;
    
    try
    {    
      out = pageContext.getOut();
      
      profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", pageContext.SESSION_SCOPE);
      
      if(profile != null)
        beans = ApplicantSupervisorManager.getApplicantSupervisorBeans(profile.getSIN());
 
      out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>References on File</div>");
    
      if((beans != null) && (beans.length > 0))
      {
    		out.println("<span style='font-size:11px;'>Below are the various stages and status of your references on file. "
    				+ "Once a reference is requested you must wait up to 72 hours for a response from the recipient. If you do not receive a response after 72 hours, you may DELETE or RESEND the request."
    				+ " Once a reference is COMPLETED by the recipient it will show as completed below. You cannot REMOVE  a completed reference until after 30 days. "
    				+ "After 30 days you may REMOVE the reference and it will be removed from your quota of three references allowing you to add a new one, if required. "
    				+ "Removing a reference does not remove it from your file. It will move and display in the Other Completed References list area until they officially expire after a year from the completion date. "
    				+ "As for any references that show as EXPIRED in your list, you may DELETE or RESEND a new request to update that reference.</span><br/>");
    	  	out.println("<input type='hidden' id='hidcount' name='hidcount' value='" + beans.length + "'>");
    	    out.println("<table class='table table-condensed' style='font-size:11px;'>");
			out.println("<thead><tr>");
			out.println("<th width='20%'>NAME (TITLE)</th>");		
			out.println("<th width='25%'>ADDRESS</th>");
			out.println("<th width='15%'>TELEPHONE</th>");
			out.println("<th width='15%'>EMAIL</th>");		
			out.println("<th width='10%'>STATUS</th>");	
			out.println("<th width='15%'>OPTIONS</th>");
			out.println("</tr></thead>");				
			out.println("<tbody>");

        for(int i=0; i < beans.length; i ++)
        {        	
        	out.println("<tr>");
			out.println("<td width='20%'>" + beans[i].getName() + " (" + beans[i].getTitle() + ")</td>");			
			out.println("<td width='25%'>" + beans[i].getAddress() + "</td>");
			out.println("<td width='15%'>" + beans[i].getTelephone() + "</td>");  			
			out.println("<td width='15%'>" + ((beans[i].getApplicantRefRequestBean()!= null)?beans[i].getApplicantRefRequestBean().getEmailAddress():"N/A" ) +"</td>");			
			
			Date date = new Date();		//Get today			
			int refResendTimeLeft=0;	
			int refDiff,refDiffResend,refDateRequested,refCurrentDate,refTimeLeft;; //declare ints 			
			int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
			
			int refExpiredTimeM = 720; //# hours in a month exact. References expire after a year, can del after a month
			
			int refExpiredTimeToResend = 72; //#cant resend a ref request until 72 hours after initial send.		
			
			 int refExpiredTimeToDel = 720; //#cant delete a ref request until 720 hours (30 days) after completion.
			
			if  (beans[i].getApplicantRefRequestBean()!=null && beans[i].getApplicantRefRequestBean().getDateStatus()!=null) {								
			 
				refDateRequested = (int) TimeUnit.MILLISECONDS.toHours(beans[i].getApplicantRefRequestBean().getDateStatus().getTime());
			 	refCurrentDate = (int) TimeUnit.MILLISECONDS.toHours(date.getTime());			 
			 	refDiff = (refCurrentDate - refDateRequested)-12;	//do the math hours
			 	refDiffResend = refDateRequested + refExpiredTimeToResend+12; //Add the date time to current date			 
			 	refResendTimeLeft =refDiffResend - refDateRequested; //get hours			 	
			 	refTimeLeft =  refExpiredTimeToResend-refDiff;
			 	//Dont use LONGS use the TimeUnit!!!!
			 	if(refTimeLeft < 0) {
			 		refTimeLeft = 0;
			 	}
			} else {
				refDateRequested = 0; //if null 0 all variables
			 	refCurrentDate = 0;									
			 	refDiff = 0;		
			 	refDiffResend = 0;
			 	refResendTimeLeft = 0;
			 	refTimeLeft =  0;			 	
			}		
						
			if(refDiff >refExpiredTimeY) { 
				out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> EXPIRED</td>");	
				} else {					
													    
					if(beans[i].getApplicantRefRequestBean() == null || beans[i].getApplicantRefRequestBean().getRequestStatus() == null){
						out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>");						
					} else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")){	
					out.println("<td width='10%' class='info' style='text-align:center;'><i class='far fa-clock'></i>  PENDING</td>");	
				} else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")){
					out.println("<td width='10%' class='success' style='text-align:center;'><i class='fas fa-check'></i> COMPLETED</td>");		
				} else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")){
					out.println("<td width='10%' class=danger' style='text-align:center;'><i class='fas fa-times'></i> DECLINED</td>");	
				} else {
					out.println("<td width='10%' class='danger' style='text-align:center;'><i class='fas fa-times'></i> NOT SENT</td>");	
				}
					}				
						
			//Add if request is sent, cannot resend or delete until 24 hours after		
			
			out.print("<td style='text-align:right;'>");			
					
			
			if(!(beans[i].getApplicantRefRequestBean() == null)){
			
				if((beans[i].getApplicantRefRequestBean() == null || beans[i].getApplicantRefRequestBean().getRequestStatus() == null)){        			
  
///// SEND REQUEST //////
					
        			if(!(beans[i].getApplicantRefRequestBean().getEmailAddress() == null)){
        				
        				if(profile.getProfileType().equals("T")){
        					if(beans[i].getApplicantRefRequestBean().getEmailAddress().toLowerCase().contains("nlesd")){
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND</a>");
            				}else{
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"sendexternalemail('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND</a>");
            				}       					
        					out.println("<a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");  
        					
        				}else{
        					out.println("<a href='#' class='btn btn-xs btn-primary' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
            						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND</a>");
        					out.println("<a class='btn-xs btn btn-danger' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
        				}			
        			}else{
        				//No email, cant send.
        				
        				//out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"opensendemail('" + beans[i].getId() + "');\">SEND REQ</a>");
        			
        				if(profile.getProfileType().equals("S")){
        		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
        		    	}else{        		
        		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
        		    	}
        				
        				
        			}        			
        			
        			
        			
        			out.println("</td></tr><tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request has <b>NOT</b> been sent. Please SEND REQUEST or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
        			
        			
///////END SEND REQUEST        			
        		
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")){

        			if(refCurrentDate > refDiffResend) {          
//Allow DELETE after 72 hours	
	        				if(profile.getProfileType().equals("S")){
	        		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
	        		    	}else{        		
	        		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
	        		    	}		
//Allow RESEND after 72 hours	
        				out.println("<a class='btn btn-xs btn-info' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
        						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() + "');\">RESEND</a></td></tr>");		
        			out.println("<tr><td colspan=6><div class='alert alert-warning' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request was SENT to " + beans[i].getName() + " on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +" with <b>NO RESPONSE</b> after 72 hours. Please try RESEND or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        				
        			} 	else {  
// NO DEL or Resend until time of 72 hours is lapsed.
        				out.println("<span style='color:Red;'>Pending Response</span></td></tr>");		
        			out.println("<tr><td colspan=6><div class='alert alert-info' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request was SENT to " + beans[i].getName() + " on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +".  <b>Wait "+ refTimeLeft  +" hours for a response</b> before RESEND or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        		}
        			
        			        			
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")){

//If COMPLETED and EXPIRED after a year allow DEL and RESEND        			
	if(refDiff >refExpiredTimeY) {   
		
		if(profile.getProfileType().equals("S")){
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
    	}else{        		
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
    	}					
		out.println("<a class='btn btn-xs btn-info' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
				+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">RESEND</a></td></tr>");
        				out.println("<tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Completed Reference on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +" from " + beans[i].getName() + " has EXPIRED. Please DEL or  RESEND reference. <span class='glyphicon glyphicon-chevron-up'></span></div>");						
  		
		
	} else if(refDiff >refExpiredTimeM) {   
	
		if(profile.getProfileType().equals("S")){
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>REMOVE</a>");
    	}else{        		
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>REMOVE</a>");        		
    	}					
		
		out.println("<a class='btn btn-xs btn-success' href='viewNLESDReferenceApp.html?id=" 
				+ beans[i].getApplicantRefRequestBean().getFkReference() + "&reftype=" + beans[i].getApplicantRefRequestBean().getReferenceType() + "' target='_blank' >VIEW</a></td></tr>"); 

        				out.println("<tr><td colspan=6><div class='alert alert-info' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Completed Reference on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +" from " + beans[i].getName() + " can be REMOVED if you need to add a new and/or updated reference.<span class='glyphicon glyphicon-chevron-up'></span></div>");						
	}
		
	else {       			
 //IF COMPLETED and NOT EXPIRED, allow VIEW. Cannot DEL enabled/completed reference
            			out.println("<a class='btn btn-xs btn-success' href='viewNLESDReferenceApp.html?id=" 
            					+ beans[i].getApplicantRefRequestBean().getFkReference() + "&reftype=" + beans[i].getApplicantRefRequestBean().getReferenceType() + "' target='_blank' >VIEW</a></td></tr>"); 
            			out.println("<tr><td colspan=6><div class='alert alert-success' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Reference Request to " + beans[i].getName() + " was COMPLETED on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +". You may REMOVE/DEL this reference in "+ (refExpiredTimeM-refDiff)/24 +" days. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
        		}
	
//If REQUEST DECLINED, RESEND or DEL	
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")){
        			
        			if(profile.getProfileType().equals("S")){
        	    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
        	    	}else{        		
        	    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
        	    	}				
        			
        			out.println("<a class='btn btn-xs btn-danger' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
    						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">RESEND</a></td></tr>");  
        			out.println("<tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: This Reference Request to " + beans[i].getName() + " was DECLINED. Please DEL or RETRY. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
        		}else{
        			out.println("");
        		}	
				
				
			} else {
			
			//out.println("<a class='btn btn-xs btn-primary' href='#' data-toggle=\"modal\" data-target=\"#refRequest\" onclick=\"opensendemail('" + beans[i].getId() + "');\">SEND REQ</a>");
				if(profile.getProfileType().equals("S")){
		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
		    	}else{        		
		    		out.println("<a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
		    	}
				
			out.println("</td></tr><tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request has <b>NO EMAIL ADDRESS</b>. Please DEL and ADD NEW.<span class='glyphicon glyphicon-chevron-up'></span> </div>");
			
			
			}
			
        	
        	
        	out.println("</td></tr>");
          	
        	
        	
}
        out.println("</tbody></table>");
       
       //Other block Was Here
      }
      else
      {
    	out.println("<br/><span style='font-size:11px;'>No references currently on file.</span>");
        out.println("<input type='hidden' id='hidcount' name='hidcount' value='0'>");
      }
      
  	Date date = new Date();		//Get today
  	int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
	
      out.println("<hr><div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>Other Completed References On File</div>");        
   
      
		NLESDReferenceListBean[] chks = NLESDReferenceListManager.getOtherReferenceBeansByApplicant(profile.getSIN());
		if((chks != null) && (chks.length > 0)){ 
			
			int refDateRequested,refCurrentDate,refExpiresInHours,refExpiresInDays = 0;			
			out.println("<span style='font-size:11px;'>These are other completed references you have on file. These will expire after a year. The time left in days is shown below.</span><br/>");
			out.println("<table class='table table-striped table-condensed' style='width:100%;font-size:11px;'>");
			out.println("<thead><tr>");
			out.println("<th width='20%'>NAME</th>");
			out.println("<th width='30%'>TITLE</th>");
			out.println("<th width='10%'>TYPE</th>");	
			out.println("<th width='15%'>PROVIDED</th>");
			out.println("<th width='15%'>EXPIRES IN</th>");
			out.println("<th width='10%'>OPTIONS</th>");
			out.println("</tr></thead>");				
			out.println("<tbody>");	
			
		for(NLESDReferenceListBean bean: chks){
			
			refDateRequested = (int) TimeUnit.MILLISECONDS.toHours(bean.getReferenceDate().getTime());   
		 	refCurrentDate = (int) TimeUnit.MILLISECONDS.toHours(date.getTime());	
			refExpiresInHours = refExpiredTimeY - (refCurrentDate - refDateRequested);
			refExpiresInDays = refExpiresInHours/24;
			out.println("<tr>");	
			out.println("<td width='20%'>" + bean.getProvidedBy() + "</td>");
			out.println("<td width='30%'>" + bean.getProviderPosition() + "</td>");
			out.println("<td width='10%'>" + bean.getReferenceType() + "</td>");  
			out.println("<td width='15%'>" +bean.getProvidedDateFormatted() + "</td>"); 
			
		if (refExpiresInDays >60) {	
			out.println("<td width='15%' style='color:Green;'>" +refExpiresInDays + " days</td>");  
		} else if (refExpiresInDays <= 60 && refExpiresInDays >=30) {
			out.println("<td width='15%' style='color:Orange;'>" +refExpiresInDays + " days</td>");
		} else {
			out.println("<td width='15%' style='color:Red;'>" +refExpiresInDays + " days</td>");
		}			
			out.println("<td width='10%'><a class='btn btn-xs btn-primary' href='viewNLESDReferenceApp.html?id=" + bean.getId() + "&reftype=" + 
		(bean.getReferenceType().equals("SUPPORT") ? "SS" : bean.getReferenceType().equals("EXTERNAL/TLA") ? "SS": bean.getReferenceType().substring(0,1)) + "'>VIEW</a>"); 
			out.println("</tr>");				
		}
		out.println("</tbody></table>");	
		
	} else {
		out.println("<br/><span style='font-size:11px;'>No other completed references currently on file.</span>");
		
	}
      
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}