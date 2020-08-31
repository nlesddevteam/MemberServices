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
    	  	out.println("<input type='hidden' id='hidcount' name='hidcount' value='" + beans.length + "'>");
    	    out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
			out.println("<thead><tr>");
			out.println("<th width='15%'>NAME</th>");
			out.println("<th width='15%'>TITLE</th>");
			out.println("<th width='25%'>ADDRESS</th>");
			out.println("<th width='15%'>TELEPHONE</th>");
			out.println("<th width='15%'>EMAIL</th>");			
			out.println("<th width='15%'>OPTIONS</th>");
			out.println("</tr></thead>");				
			out.println("<tbody>");

        for(int i=0; i < beans.length; i ++)
        {        	
        	out.println("<tr>");
			out.println("<td>" + beans[i].getName() + "</td>");
			out.println("<td>" + beans[i].getTitle() + "</td>");
			out.println("<td>" + beans[i].getAddress() + "</td>");
			out.println("<td>" + beans[i].getTelephone() + "</td>");  			
			out.println("<td>" + ((beans[i].getApplicantRefRequestBean()!= null)?beans[i].getApplicantRefRequestBean().getEmailAddress():"N/A" ) +"</td>");	
			
			//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
			//String sentDate = formatter.format(beans[i].getApplicantRefRequestBean().getDateRequested()) ;
					
			
			
			Date date = new Date();		//Get today			
			int refResendTimeLeft=0;	
			int refDiff,refDiffResend,refDateRequested,refCurrentDate,refTimeLeft;; //declare ints 			
			int refExpiredTimeY = 8760; //# hours in a year exact. References expire after a year.
			int refExpiredTimeToResend = 72; //#cant resend a ref request until 72 hours after initial send.		
			
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
			//Add if request is sent, cannot resend or delete until 24 hours after			
			
			out.print("<td style='text-align:right;'>");			
			//Variable output test
			//out.print("Current Date(hrs): "+refCurrentDate);
			//out.print("<br/>Req Date(hrs): "+refDateRequested);
			//out.print("<br/>Diff (hrs): "+refDiff);
			//out.print("<br/>DiffR (hrs): "+refDiffResend);
			//out.print("<br/>Left (hrs): "+refResendTimeLeft);
			//out.print("<br/>");		
			
			
			if(!(beans[i].getApplicantRefRequestBean() == null)){
			
				if((beans[i].getApplicantRefRequestBean().getRequestStatus() == null)){        			
  
///// SEND REQUEST //////
					
        			if(!(beans[i].getApplicantRefRequestBean().getEmailAddress() == null)){
        				
        				if(profile.getProfileType().equals("T")){
        					if(beans[i].getApplicantRefRequestBean().getEmailAddress().toLowerCase().contains("nlesd")){
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
            				}else{
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"sendexternalemail('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
            				}       					
        					out.println("<a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");  
        					
        				}else{
        					out.println("<a href='#' class='btn btn-xs btn-primary' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
            						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
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
        			
        			
        			
        			out.println("</td></tr><tr><td colspan=8><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request has <b>NOT</b> been sent. Please SEND REQUEST or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
        			
        			
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
        			out.println("<tr><td colspan=8><div class='alert alert-warning' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request was SENT to " + beans[i].getName() + " on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +" with <b>NO RESPONSE</b> after 72 hours. Please try RESEND or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        				
        			} 	else {  
// NO DEL or Resend until time of 72 hours is lapsed.
        				out.println("<span style='color:Red;'>Pending Response</span></td></tr>");		
        			out.println("<tr><td colspan=8><div class='alert alert-info' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request was SENT to " + beans[i].getName() + " on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +".  <b>Wait "+ refTimeLeft  +" hours for a response</b> before RESEND or DEL. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        		}
        			
        			        			
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")){

//If COMPLETED and EXPIRED allow DEL and RESEND        			
	if(refDiff >refExpiredTimeY) {   
		
		if(profile.getProfileType().equals("S")){
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
    	}else{        		
    		out.println("<a class='btn-xs btn btn-danger delRef' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");        		
    	}					
		out.println("<a class='btn btn-xs btn-info' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
				+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">RESEND</a></td></tr>");
        				out.println("<tr><td colspan=8><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Completed Reference on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +" from " + beans[i].getName() + " has EXPIRED. Please DEL or  RESEND reference. <span class='glyphicon glyphicon-chevron-up'></span></div>");						
  		
		
	} 	else {       			
 //IF COMPLETED and NOT EXPIRED, allow VIEW. Cannot DEL enabled/completed reference
            			out.println("<a class='btn btn-xs btn-success' href='viewNLESDReferenceApp.html?id=" 
            					+ beans[i].getApplicantRefRequestBean().getFkReference() + "&reftype=" + beans[i].getApplicantRefRequestBean().getReferenceType() + "' target='_blank' >VIEW</a></td></tr>"); 
            			out.println("<tr><td colspan=8><div class='alert alert-success' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Reference Request to " + beans[i].getName() + " was COMPLETED on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +". <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
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
        			out.println("<tr><td colspan=8><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: This Reference Request to " + beans[i].getName() + " was DECLINED. Please DEL or RETRY. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
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
				
			out.println("</td></tr><tr><td colspan=8><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request has <b>NO EMAIL ADDRESS</b>. Please DEL and ADD NEW.<span class='glyphicon glyphicon-chevron-up'></span> </div>");
			
			
			}
			
        	
        	
        	out.println("</td></tr>");
          	
        	
        	
}
        out.println("</tbody></table>");
       
        out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>Other Completed References On File</div>");        
        
    		NLESDReferenceListBean[] chks = NLESDReferenceListManager.getOtherReferenceBeansByApplicant(profile.getSIN());
    		if(chks.length > 0){
    		
    			out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
				out.println("<thead><tr>");
				out.println("<th width='30%'>NAME</th>");
				out.println("<th width='35%'>TITLE</th>");
				out.println("<th width='25%'>TYPE</th>");				
				out.println("<th width='10%'>OPTIONS</th>");
				out.println("</tr></thead>");				
				out.println("<tbody>");	
    			
    		for(NLESDReferenceListBean bean: chks){
    			out.println("<tr>");	
    			out.println("<td>" + bean.getProvidedBy() + "</td>");
    			out.println("<td>" + bean.getProviderPosition() + "</td>");
    			out.println("<td>" + bean.getReferenceType() + "</td>");
    			out.println("<td><a class='btn btn-xs btn-primary' href='viewNLESDReferenceApp.html?id=" + bean.getId() + "&reftype=" + bean.getReferenceType().substring(0,1) + "'>VIEW</a>"); 
    			out.println("</tr>");	
    		}
    		out.println("</tbody></table>");	
    	} else {
    		out.println("None currently on file.");
    		
    	}
      }
      else
      {
        out.println("None currently on file.");
        out.println("<input type='hidden' id='hidcount' name='hidcount' value='0'>");
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