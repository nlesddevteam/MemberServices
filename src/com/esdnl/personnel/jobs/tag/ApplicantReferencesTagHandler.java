package com.esdnl.personnel.jobs.tag;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;

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
			out.println("<th width='20%'>TITLE</th>");
			out.println("<th width='20%'>ADDRESS</th>");
			out.println("<th width='10%'>TELEPHONE</th>");
			out.println("<th width='20%'>EMAIL</th>");
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
			out.println("<td>");
			out.println((beans[i].getApplicantRefRequestBean()!= null)?beans[i].getApplicantRefRequestBean().getEmailAddress():"N/A");
			out.println("</td>");		
			
        	if(profile.getProfileType().equals("S")){
        		out.println("<td><a class='btn-xs btn btn-danger' href='applicantRegistrationSS.html?step=7&del=" + beans[i].getId() +"'>DEL</a>");
        	}else{
        		out.println("<td><a class='btn-xs btn btn-danger' href='applicantRegistration.html?step=8&del=" + beans[i].getId() +"'>DEL</a>");
        	}
			
			
			if(!(beans[i].getApplicantRefRequestBean() == null)){
			
				if((beans[i].getApplicantRefRequestBean().getRequestStatus() == null)){        			
        			
        			if(!(beans[i].getApplicantRefRequestBean().getEmailAddress() == null)){
        				
        				if(profile.getProfileType().equals("T")){
        					if(beans[i].getApplicantRefRequestBean().getEmailAddress().toLowerCase().contains("nlesd")){
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
            				}else{
            					out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"sendexternalemail('" + beans[i].getApplicantRefRequestBean().getId() 
                						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
            				}
        				}else{
        					out.println("<a href='#' class='btn btn-xs btn-primary' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
            						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">SEND REQ</a>");
        				}
        				
        				
        			}else{
        				out.println("<a class='btn btn-xs btn-primary' href='#' onclick=\"opensendemail('" + beans[i].getId() + "');\">SEND REQ</a>");
        			}
        		
        		
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST SENT")){
        			out.println("<a class='btn btn-xs btn-info' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
    						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() + "');\">RESEND</a></td></tr>");
        			out.println("<tr><td colspan=6><div class='alert alert-info' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: The above Reference Request was SENT to " + beans[i].getName() + " on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +". <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			        			
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REFERENCE COMPLETED")){
        			//check to see if it is expired, six months
        			Calendar c = Calendar.getInstance();
        			c.add(Calendar.MONTH, -18);
        			Date testdate = c.getTime();
        			if(beans[i].getApplicantRefRequestBean().getDateStatus().compareTo(testdate) < 0){
        				out.println("<a class='btn btn-xs btn-danger' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
        						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">RESEND</a></td></tr>");
        				out.println("<tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Reference Request to " + beans[i].getName() + " has EXPIRED. Please resend or delete. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        				
        			}else{
            			out.println("<a class='btn btn-xs btn-success' href='viewNLESDReferenceApp.html?id=" 
            					+ beans[i].getApplicantRefRequestBean().getFkReference() + "&reftype=" + beans[i].getApplicantRefRequestBean().getReferenceType() + "' target='_blank' >VIEW</a></td></tr>"); 
            			out.println("<tr><td colspan=6><div class='alert alert-success' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: Your Reference Request to " + beans[i].getName() + " was COMPLETED on " + beans[i].getApplicantRefRequestBean().getDateStatusFormatted() +". <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			}
      		
        		}else if(beans[i].getApplicantRefRequestBean().getRequestStatus().toUpperCase().equals("REQUEST DECLINED")){
        			out.println("<a class='btn btn-xs btn-danger' href='#' onclick=\"opensend('" + beans[i].getApplicantRefRequestBean().getId() 
    						+ "','" + beans[i].getApplicantRefRequestBean().getEmailAddress() +"');\">RESEND</a></td></tr>");  
        			out.println("<tr><td colspan=6><div class='alert alert-danger' style='text-align:center;padding:0px;'> <span class='glyphicon glyphicon-chevron-up'></span> NOTE: This Reference Request to " + beans[i].getName() + " was DECLINED. <span class='glyphicon glyphicon-chevron-up'></span> </div>");
        			
        		}else{
        			out.println("");
        		}	
				
				
			} else {
			
			out.println("<a class='btn btn-xs btn-primary' href='#' data-toggle=\"modal\" data-target=\"#refRequest\" onclick=\"opensendemail('" + beans[i].getId() + "');\">SEND REQ</a>");
				
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