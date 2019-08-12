<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*, 
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.sds.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>



<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />


<%
  User usr = null;
  TravelClaim claim = null;
  TravelClaimItem item = null;
  TravelClaimItem failed_item = null;
  TravelClaimRateSummary rate_summary = null;
  TravelClaimSummary summary = null;
  Iterator rate_summaries = null;
  Iterator items = null;
  SimpleDateFormat sdf = null;
  SimpleDateFormat sdf_title = null;
  SimpleDateFormat cal_sdf = null;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;
  DecimalFormat kms_rate_df = null;
  String color_on;
  String color_off;
  TravelBudget budget = null; 
     
  usr = (User) session.getAttribute("usr");
  
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  failed_item = (TravelClaimItem) request.getAttribute("FAILED_ITEM");
  items = claim.getItems().iterator();
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  cal_sdf = new SimpleDateFormat("dd/MM/yyyy");
  sdf_title = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
  kms_rate_df = new DecimalFormat("$#,##0.000");

  color_off = "#FFFFFF";
  color_on = "#FEF153";
  
  TravelClaimNote note = null;  
  Iterator n_items = null;  
  n_items = claim.getNotes().iterator();
 
  
  budget = (TravelBudget) request.getAttribute("BUDGET");
  
  double total_claimed = ((Double)request.getAttribute("TOTAL_CLAIMED")).doubleValue();
  String acct_code = null;
  SDSInfo sds = null;
  sds = claim.getPersonnel().getSDSInfo();
  
  if((claim.getSDSGLAccountCode()!=null)&&(!claim.getSDSGLAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = claim.getSDSGLAccountCode();
  }
  else if((sds != null) && (sds.getAccountCode()!= null) && (!sds.getAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = sds.getAccountCode();
  }
  else
  {
    acct_code = null;
  }
  
  Personnel p = null;
  Personnel sup = null;
  Iterator iter = null;
  Supervisors supervisorslist = new Supervisors();
  iter = supervisorslist.iterator();
  String claimtitle="";             
  if(claim instanceof PDTravelClaim){
	  claimtitle="PD - " + ((PDTravelClaim)claim).getPD().getTitle();
  }else{
	  claimtitle=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
  }
  
  

  	Integer claimid=(Integer)request.getAttribute("claimid");
	Integer claimmonth=(Integer)request.getAttribute("fiscalmonth");
	Integer claimyear=(Integer)request.getAttribute("fiscalyear");
	Integer lastdaymonth=(Integer)request.getAttribute("lastdaymonth");
 
	
	 String FullName = "";
	 
	 FullName = claim.getPersonnel().getFullNameReverse();
	
  
%>

<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
<script src="includes/js/travel.js"></script>
<script type="text/javascript" src="includes/js/jquery.timepicker.js"></script>	
			
			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none"); 
    			$('#item_departure_time').timepicker();
    			$('#item_return_time').timepicker();      
    			
			});
			
			function showit(target){
				document.getElementById(target).style.display = 'block';
				
				}
				function hideit(target){
				document.getElementById(target).style.display = 'none';
				
				}
			
			
			</script>
	
	<script type="text/JavaScript">
function valid(f) {
f.value = f.value.replace(/[^\w\s,\/.$+=-]/gi,'');
f.value = f.value.replace('\n',' ');
} 
</script>
	
	
	<div id="printJob"> 
	<div class="no-print">
	<div class="claimHeaderText"><div class="claimHeaderText"><span style="text-transform:capitalize;"><%=claim.getPersonnel().getFullNameReverse()%></span>'s Claim for <%if(claim instanceof PDTravelClaim){%>              
		              PD <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		              <%}else if(claim instanceof TravelClaim){%>
		              <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		              <%}%></div></div>
	<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION)){%>
	 This is your claim entry form page. You will see three tabs below. 
	 The <b>DETAILS</b> tab for your claim entry and status, the <b>HISTORY</b> tab for a history of work completed on this claim, and a <b>NOTES</b> tab which will show any messages left by Travel Admins or supervisor for you 
	 regarding your claim such as more information or receipts required. <i>(The claim in such cases will be set as Payment Pending until required information is provided and validated.)</i>. 
	 <br/><br/>
	 	
	Simply fill out the form below and click <b>ADD ITEM</b> to add an item to your opened claim <i>(You can add multiple items to a claim)</i>. 
	 
	To remove or edit a claim item you already entered, use the <b>EDIT</b> or <b>DELETE</b> Tools at the far right of each listed claim item. <i>(When you click on edit, please wait as the data is loaded back into the form)</i>. To print a copy of the claim properly formatted, simply click on <b>PRINT</b>. If the listed supervisor is not correct, use the <b>CHANGE SUPERVISOR</b> option to select. <i>(If your supervisor is not listed for selection, please contact your supervisor directly as they may need to be added as a supervisor).</i>
	  <br/><br/>Once you have completed entry of all items and are ready to submit for processing, click on <b>SUBMIT</b> at right.
	<%} %>
	
	<%if(claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
	
	<div class="alert alert-danger">Claim has been <b>rejected</b> by supervisor. Please check your claim for errors <i>(amounts, wrong supervisor, or invalid claimed item)</i> or check <b>NOTES</b> tab below for possible reason. Correct any issue(s) and re-submit this claim, or delete.</div>
	<%} %>
	
	<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PAYMENT_PENDING)){%>
	
	<div class="alert alert-info">Claim has been set to <b>Pending Information</b>. Travel Admins have set your claim as Pending. Please check NOTICES or <b>NOTES</b> tab below left for reason.</div>
	
	 				
                   
    <%} %>
	<!-- DISPLAY NOTES (If any) IF REJECTED OR PENDING, Once processed hide. -->	
	<%if(n_items.hasNext() && (claim.getCurrentStatus().equals(TravelClaimStatus.PAYMENT_PENDING) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED))){%>
	                  <div class="alert alert-danger"><b>NOTICE(S) RE THIS CLAIM:</b><br/>
	                  <ul>  
                      <%while(n_items.hasNext()){
                        note = (TravelClaimNote) n_items.next();%>
                        <li><%=note.getNote()%><br/>
                        <i>(Request Posted: <%=note.getNoteDate().toString()%> by <span style="text-transform:Capitalize;"><%=note.getPersonnel().getFullNameReverse()%>)</span>.</i>                        
                      <%}%>                      
                      </ul></div>
    <%}%>
	
	
	
	
	
	</div>	
    <form name="add_claim_item_form" id="add_claim_item_form" method="post" action="<%=(request.getAttribute("EDIT")!=null)?"editTravelClaimItem.html":"addTravelClaimItem.html"%>">

      <input type="hidden" name="id"  id="id" value=<%=claim.getClaimID()%>>
      <input type="hidden" name="cm"  id="cm" value=<%=claimmonth%>> 
      <input type="hidden" name="cy"  id="cy" value=<%=claimyear%>> 
      <input type="hidden" name="ldm"  id="ldm" value=<%=lastdaymonth%>> 
      
      <%if(request.getAttribute("EDIT")!=null){%>
        <input type="hidden" name="iid" value=<%=failed_item.getItemID()%>>
      <%}%>
      <input type="hidden" name="op" value="CONFIRM">
      <table width="100%">
        
        <tr class="no-print">
          <td width="100%" height="30">
            <jsp:include page="tab_bar.jsp" flush="true">
              <jsp:param name="tab" value="details" />
              <jsp:param name="id" value="<%=claim.getClaimID()%>" />
              <jsp:param name="status" value="<%=claim.getCurrentStatus().getID()%>" />
              <jsp:param name="hasItems" value="<%=items.hasNext()%>" />
            </jsp:include>
          </td>
        </tr>
        
       
        
        
        
        <tr>
          <td width="100%" height="300" class="claimTabContent" valign="top">
            			<table id="tab_content" width="100%" cellpadding="0" cellspacing="0">
              				<tr>
                				<td width="100%" valign="top" valign="top">
                				
                        
          <table width="100%">
          <tr>
            <td>
            
         
          
            
              
              		<div class="claimHeaderText">Claimant: <span style="text-transform:capitalize;"><%=FullName%> </span>
             			
		              <div style="float:right;">
		               <%if(claim instanceof PDTravelClaim){%>              
		              <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		              <%}else if(claim instanceof TravelClaim){%>
		              <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		              <%}%>
		              </div>
		              
              		
              		</div>    
              			<div id="statPreSub" style="display:none;float:right;"><img src="includes/img/presub_stamp.png" class="statusLogo"></div>
                    	<div id="statPaid" style="display:none;float:right;"><img src="includes/img/paid-stamp.png" class="statusLogo"></div>
                    	<div id="statError" style="display:none;float:right;"><img src="includes/img/error-stamp.png" class="statusLogo"></div>
                    	<div id="statProcessed" style="display:none;float:right;"><img src="includes/img/processed.png" class="statusLogo"></div>
                    	<div id="statProcessing" style="display:none;float:right;"><img src="includes/img/processing.png" class="statusLogo"></div>
                    	<div id="statRejected" style="display:none;float:right;"><img src="includes/img/rejected_stamp.png" class="statusLogo"></div>
                     	<div id="statApproved" style="display:none;float:right;"><img src="includes/img/approved_stamp.png" class="statusLogo"></div>
              			<div id="statSubmitted" style="display:none;float:right;"><img src="includes/img/submitted_stamp.png" class="statusLogo"></div>
                    	<div id="statReviewed" style="display:none;float:right;"><img src="includes/img/reviewed_stamp.png" class="statusLogo"></div>
                     	<div id="statPending" style="display:none;float:right;"><img src="includes/img/pending-info.png" class="statusLogo"></div>
                      <b>Address:</b>  <%=claim.getPersonnel().getProfile().getStreetAddress() != null ? claim.getPersonnel().getProfile().getStreetAddress(): "N/A" %>, <%=claim.getPersonnel().getProfile().getCommunity() %>, <%=claim.getPersonnel().getProfile().getProvince() %> &middot; <%=claim.getPersonnel().getProfile().getPostalCode() %><br/>
		              <b>Tel:</b> <%=(claim.getPersonnel().getProfile().getPhoneNumber() != null ? claim.getPersonnel().getProfile().getPhoneNumber() : "N/A") %> &nbsp;&middot;&nbsp; <b>Cell:</b> <%=claim.getPersonnel().getProfile().getCellPhoneNumber() != null ? claim.getPersonnel().getProfile().getCellPhoneNumber(): "N/A" %>&nbsp;&middot;&nbsp;
		              <b>Fax:</b> <%=claim.getPersonnel().getProfile().getFaxNumber() != null ? claim.getPersonnel().getProfile().getFaxNumber() : "N/A" %><br/>
		              <b>Email:</b> <a href="mailto:<%=claim.getPersonnel().getEmailAddress()%>"><%=claim.getPersonnel().getEmailAddress()%></a><br/>
		            	
		            
		              <br>
		                  
		              
		              
		              <b>Position:</b> <%=claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null ? claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() : "N/A" %><br/> 
		              <b>School:</b> <%=(claim.getPersonnel().getSchool() != null ? claim.getPersonnel().getSchool().getSchoolName(): "NO SCHOOL")%><br/>                                         
		              <b>Supervisor: </b><span style="text-transform:capitalize;"><a href="mailto:<%=claim.getSupervisor().getEmailAddress()%>"><%=claim.getSupervisor().getFullNameReverse()%></a></span><br/>
		             
		              <b>Your KM Rate:</b> <span id="kmRates" style="color:Green;">Add item to display your rate.</span><br/>
						
						<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
						
							<br/><img src="includes/img/changesup-off.png" class="img-swap no-print" align="right" title="Change Supervisor" style="margin-top:5px;" onclick="openModalDialog('<%=claim.getClaimID()%>','changesupervisor','none');">
							 <span style="color:Black;" class="no-print"><b>NOTE:</b> Check your listed supervisor above. You may have defaulted to a previous supervisor depending on your school or job position. If your supervisor listed is incorrect, please use the change supervisor link at right.</span>
		       <br/>
						<%}%>              
              <br/>              
            
          
          	
          	</td>
          </tr>
        
        <%if(claim instanceof PDTravelClaim){%>
          <tr>
            <td>
             <div class="claimHeaderText">PD Title: <%=((PDTravelClaim)claim).getPD().getTitle().replace("\"", "")%></div>
             
             <b>PD Date:</b> <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
             
            </td>
          </tr>
        <%}else if(claim instanceof TravelClaim){%>
          <tr>
            <td>
            <b>Claim Date:</b> <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></td>
          </tr>
          </table>
        <%}%>
                  
                  
                  
                 						 <table align="center" style="width:100%;">
                    							<% if(claim instanceof PDTravelClaim){ %>
							                      <tr>
							                        <td colspan="7">
							                        <div class="claimStatusBlock">
							                         <div class="claimHeaderText">PD Description</div>
							                        <%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%>
							                        </div>							                        
							                       
							                          
							                        </td>
							                      </tr>
                    								<%}%>
                    							  <tr>
								                     <td colspan='7'>
								                     
								                     
								               		                     
								                     
								                     
								                     
								                     
								                     
								                     								                       							              
								                   
								                     
								                     <div class="claimStatusSuperBlock">
						                            	
						                            </div>
								                     
								                     
									                      <div class="claimStatusBlock">
									                      <div class="claimHeaderText">Claim Status</div>
									                                <c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
									                                <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">
									                                		<div class="alert alert-warning" style="margin-top:5px;padding:2px;"><b>PRE-SUBMISSION:</b> Claim is in Pre-Submission mode and is ready to complete. Please fill in your claim details and submit when ready for processing.</div>
									                                	<script>$('#statPreSub').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;"><b>SUBMITTED:</b> Claim has been submitted to supervisor.</div>
									                                	<script>$('#statSubmitted').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;"><b>REVIEWED:</b> Claim has been reviewed by  supervisor.</div>
									                                	<script>$('#statReviewed').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;"><b>APPROVED:</b> Claim has been approved by  supervisor.</div>
									                                	<script>$('#statApproved').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<div class="alert alert-danger" style="margin-top:5px;padding:2px;"><b>REJECTED:</b> Claim has been rejected by supervisor. Please check your claim for errors (amounts, wrong supervisor, or invalid claimed item) or check Notes tab above for possible reason. Correct any issue(s) and re-submit this claim, or delete.</div>
									                                		<script>$('#statRejected').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<div class="alert alert-warning" style="margin-top:5px;padding:2px;"><b>PENDING MORE INFORMATION:</b> Claim is pending further action. Please check NOTES tab above and/or any emails from Travel Claims staff re your claim.</div>
									                                	    <script>$('#statPending').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">						                                	
									                                	
									                                	
									                                	 		<% Date now = new java.util.Date();	%>
									                                		<c:set var="todayDate" value="<%=new java.util.Date() %>" />									                                										                                	
									                                		<c:set var="todayDateStamp" value="<%=now.getTime()%>" />
									                                		
									                                		<c:set var="claimExportDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate() :"0" %>'/>																            															               	
																            <c:set var="claimExportDateStamp" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0" %>'/>
																            <c:set var="claimPaidDate" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate() :"0" %>'/>	
																            <c:set var="claimPaidDateStamp" value='<%=(claim.getPaidDate() != null) ? claim.getPaidDate().getTime() : "0" %>'/>
																         
																          <!-- After 30 days, set as paid for cosmetic reasons.-->
																            <c:set var="claimCheckDate" value='<%=(claim.getExportDate() != null) ? claim.getExportDate().getTime() : "0"%>'/>															               							                
																            <c:set var="claimCheckDateStamp" value="${(60*60*24*30*1000) + claimCheckDate}" /> 
																                 
																               									                                  				
								                                  			
                        				
                        												<c:choose>
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp gt claimCheckDateStamp)}">
                        												
                        												<div class="alert alert-success" style="margin-top:5px;padding:2px;"><b>PAID</b> Claim has been processed and marked as paid. 
                        												Please allow anywhere from 2-10 business days for deposit to show in your account. 
                        												If you have NOT been paid, please contact support below.</div>									                                		
									                                		<script>$('#statPaid').css('display', 'inline-block');</script>
                        												
                        												</c:when>
                        												
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">
                        												
                        												
                        												   <div class="alert alert-info" style="margin-top:5px;padding:2px;"><b>PROCESSED:</b> Claim has been processed and is pending payment. 
                        												   Please allow time for processing of your payment and final deposit anywhere from <b>2-10 business days</b>. 
                        												   Claim may show as PROCESSED for up to 30 days after any payment has been made.  
                        												   If there is an issue with final payment, you will be notified before any deposit is made.</div>									                                		
									                                		<script>$('#statProcessed').css('display', 'inline-block');</script>
                        												
                        												</c:when>
                        												
                        												
                        												<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        												<div class="alert alert-info" style="margin-top:5px;padding:2px;"><b>PROCESSING:</b> Claim is being processed. 
                        												Please allow 2-10 business days for your claim to be processed. If there is an issue with your claim, you will be notified before it is submitted for payment.</div>									                                		
									                                		<script>$('#statProcessing').css('display', 'inline-block');</script>
                        												
                        												</c:when>
                        												                                                                                     
                        												
                        												
                        												<c:otherwise>
                        												
                        												<div class="alert alert-danger" style="margin-top:5px;padding:2px;"><b>ERROR:</b> There seems to have been a problem. Please contact supervisor or accounts payable.</div>
                        												<script>$('#statError').css('display', 'inline-block');</script>
                        												
                        												</c:otherwise>
                        												</c:choose>
                        				
                        				
                        				
									                                		
									                                	</c:when>
									                                	<c:otherwise>
									                                	 	<div class="alert alert-danger" style="margin-top:5px;padding:2px;"><b>ERROR:</b> There seems to have been a problem. Please contact supervisor or accounts payable.</div>
									                                	</c:otherwise>                             
									                                </c:choose>
									                            </div>    
                            
                             <div class="alert alert-danger" id="details_error_message" style="display:none;font-size:12px;margin-top:10px;margin-bottom:10px;padding:5px;text-align:center;"></div>         
         					<div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;font-size:12px;text-align:center;"></div>   
							<div class="alert alert-info" id="details_info_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;font-size:12px;text-align:center;"></div>   
								                     
                            
                            <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PAID)
                            && usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){%>
                            <div style="clear:both;"></div>   
                            <div class="claimStatusInfoBlock">
                            <b>Teacher Payroll:</b> <%=claim.isPaidThroughTeacherPayroll()?"YES":"NO"%><br/>
                            <b>GL Account:</b>
                                    	<%=(claim.getSDSGLAccountCode()!=null)?
                                      claim.getSDSGLAccountCode().substring(0, 1) + "-"
                                      + claim.getSDSGLAccountCode().substring(1, 5) + "-"
                                      + claim.getSDSGLAccountCode().substring(5, 6) + "-"
                                      + claim.getSDSGLAccountCode().substring(6, 8) + "-"
                                      + claim.getSDSGLAccountCode().substring(8, 12) + "-"
                                      + claim.getSDSGLAccountCode().substring(12, 14) + "-"
                                      + claim.getSDSGLAccountCode().substring(14):""%>
                            
                            </div>
                            <%}%>
                            
                           </td>
                           </tr>                              
                           
                    <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION)
                        || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
                      <tr style="padding-top:10px;" class="no-print">
                        <td colspan='7' class='title'><%=(request.getAttribute("EDIT") != null)?"Edit":"Add"%> Claim Item</td>
                      </tr>
                       <tr  class="no-print">
                        <td colspan='7' style="padding-top:10px;padding-bottom:10px;font-size:10px;">Please enter valid data for all items you add to this claim. If you leave the Return or Departure times blank, system will recognize as Overnight automatically.</td>
                      </tr>
                      <tr class="no-print">
                        <td width="15%" valign='bottom'>Date<br/><input class="requiredinput_date" type="text" name="item_date" id="item_date" style="width:98%;" value="<%=(failed_item != null)?cal_sdf.format(new Date(failed_item.getItemDate().getTime())):""%>"></td>
                        <td width="15%" valign='bottom'>Departure<br/><input class="requiredInputBox time ui-timepicker-input" type="text" style="width:98%;" name="item_departure_time" id="item_departure_time" value="<%=(failed_item != null)?failed_item.getDepartureTime():""%>" onfocus="this.select();"></td>
                        <td width="15%" valign='bottom'>Return<br/><input class="requiredInputBox time ui-timepicker-input" type="text" style="width:98%;" name="item_return_time" id="item_return_time"   value="<%=(failed_item != null)?failed_item.getReturnTime():""%>" onfocus="this.select();"></td>
                        <td width="15%" valign='bottom'>KMs<br/><input class="requiredInputBox" type="text" name="item_kms"  id="item_kms" style="width:98%;" value="<%=(failed_item != null)?""+failed_item.getItemKMS():""%>" onfocus="this.select();" onblur="return validateInteger(this);"></td>
                        <td width="15%" valign='bottom'>Meals<br/><input class="requiredInputBox" type="text" name="item_meals"  id="item_meals" style="width:98%;" value="<%=(failed_item != null)?curr_df.format(failed_item.getItemMeals()):""%>" onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);"></td>
                        <td width="15%" valign='bottom'>Lodging<br/><input class="requiredInputBox" type="text" name="item_lodging" id="item_lodging" style="width:98%;" value="<%=(failed_item != null)?curr_df.format(failed_item.getItemLodging()):""%>" onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);"></td>
                        <td width="10%" valign='bottom'>Other<br/><input class="requiredInputBox" type="text" name="item_other"  id="item_other" style="width:98%;" value="<%=(failed_item != null)?curr_df.format(failed_item.getItemOther()):""%>" onfocus="removeCurrency(this); this.select();" onblur="validateDollar(this);"></td>
                      </tr>          
                      <tr><td colpsan=7>&nbsp;</td></tr>           
                      <tr class="no-print">
                      	<td colspan='7'>
                      		Description (Max 500 Characters. Remaining: <span id="remainder">500</span>)<br/>
	                      				<textarea class="requiredInputBox" name="item_desc" id="item_desc" style="width:100%;height:60px;" onfocus="this.select();" onkeyup="valid(this)" onblur="valid(this)"><%=(failed_item != null)?failed_item.getItemDescription():""%></textarea><br/>
	                      				<span style="font-size:10px;">Description should include all nesessary information to review the claim (eg. departure and return points, and items included in other category). You are limited to 500 characters. 
	                      				Any ' or &quot; characters and other invalid characters will be automatically removed from any text you enter on submission. This is not an error. ($+-=,./ are accepted characters.)<br/>
	                      				<i style="color:Red;">*Red outlined fields are required.</i><br/></span>
	                    </td>
                      </tr>
                      <tr class="no-print">
                        <td colspan='5' align="left" style="padding-top:5px;">
                        <div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:5px;margin-bottom:10px;padding:5px;"></div>
                        <%if(request.getAttribute("msg") != null){%>
                            <%=request.getAttribute("msg")%>
                          <%}else{%>
                            &nbsp;
                          <%}%>
                        </td>
                        <td colspan='2' class="no-print" align="right" style="padding-top:5px;padding-right:10px;">
                          <%if(request.getAttribute("EDIT") != null){%>
                           <img src="includes/img/save-off.png" class="img-swap" title="<%=(request.getAttribute("EDIT")!=null)?"Submit edited":"Add"%> claim item." onclick="findTheInvalids();addnewtravelclaimitem('<%=claim.getClaimID()%>','UPDATE','<%=failed_item.getItemID()%>');">
                           <img style="padding-right:5px;" src="includes/img/cancel-off.png" class="img-swap" title="Cancel edit claim item." onclick="unloadEditItem('<%=claim.getClaimID()%>');"><br/>
                             
                          <%} else {%>
                          <img src="includes/img/additem-off.png" class="img-swap" title="<%=(request.getAttribute("EDIT")!=null)?"Submit edited":"Add"%> claim item." onclick="findTheInvalids();addnewtravelclaimitem('<%=claim.getClaimID()%>','ADD','0');"><br>
                          <%}%>     
                        </td>
                      </tr>

                      <tr class="no-print">
                        <td colspan='7'>&nbsp;<br/><img src="includes/img/bar.png" width="100%" height=1><br/>&nbsp;</td>
                      </tr>
                    <%}else{%>
                      <tr class="no-print">
                        <td colspan='7'>&nbsp;</td>
                      </tr>
                    <%}%>
                    <tr>
                    	<td colspan='7' style='padding:0px;'>
           <table width="100%">
                    <tr>
                      <td colspan="8" class="title">Current Claim Items</td>
                    </tr>
                    <tr>
                      <td width="15%" class="itemsHeader">Date</td>
                      <td width="15%" class="itemsHeader">Depart</td>     
                      <td width="15%" class="itemsHeader">Return</td>                
                      <td width="10%" class="itemsHeader">KMs</td>
                      <td width="10%" class="itemsHeader">Meals</td>
                      <td width="15%" class="itemsHeader">Lodging</td>
                      <td width="10%" class="itemsHeader">Other</td>
                      <td width="*" class="itemsHeader no-print">Tools</td>
                    </tr>
                    <tr><td colspan="8" style="padding-bottom:2px;border-top: solid 1px #c4c4c4;color:Grey;height:5px;text-transform:none;"></td></tr>
                    <tr><td colspan=8><div id="loadMes" style="display:none;color:white;background-color:Green;padding:3px;text-align:center;font-weight:bold;"> &nbsp; LOADING ITEM DATA FOR EDITING... &nbsp; </div></td></tr>            
                    <%if(!items.hasNext()){%>
                      <tr><td colspan="8" style="padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Red;height:5px;text-transform:none;">This claim has no items.</td></tr>
                    <%}else{
                        summary = claim.getSummaryTotals();
                        rate_summaries = claim.getRateSummaryTotals().iterator();
                        while(items.hasNext()){
                          item = (TravelClaimItem) items.next();%>
                          
                                                  
                           <script>
                          $("#kmRates").html("<%=kms_rate_df.format(item.getPerKilometerRate())%>");
                          </script>                          
                          
                          <tr id="item_row_<%=item.getItemID()%>">
                            <td width="15%" class="field_content"><%=sdf.format(item.getItemDate())%></td>
                            <td width="15%" class="field_content"><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getDepartureTime()!=null)?item.getDepartureTime():"")))%></td>
                            <td width="15%" class="field_content"><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getReturnTime()!=null)? item.getReturnTime():"-OVERNIGHT")))%></td>
                            <td width="10%" class="field_content"><%=item.getItemKMS()%> ($<%=item.getPerKilometerRate() %>)</td>
                            <td width="10%" class="field_content"><%=curr_df.format(item.getItemMeals())%></td>
                            <td width="15%" class="field_content"><%=curr_df.format(item.getItemLodging())%></td>
                            <td width="10%" class="field_content">
                             
                                    <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")
                                      && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)){%>
                                      <input class="requiredinput" type="text" name="item_<%=item.getItemID()%>"  style="width:54px;" value="<%=curr_df.format(item.getItemOther())%>">
                                    <%}else{%>
                                      <%=curr_df.format(item.getItemOther())%>
                                    <%}%>
                              </td>
                              <td width="*" class="field_content no-print">   
                                    <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION)
                                      || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
                                       
                                              <img src="includes/img/editsm-off.png" title="Edit claim item." class="img-swap" onclick="showit('loadMes');loadEditItem('<%=claim.getClaimID()%>','<%=item.getItemID()%>');">&nbsp;
                                           
                                              <img src="includes/img/deletesm-off.png" title="Delete claim item." class="img-swap" onclick="openModalDialog('<%=item.getItemID()%>','deletetravelclaimitem','<%=claimtitle%>,<%=sdf.format(item.getItemDate())%>,<%=item.getItemDescription()%>');">
                                            
                                    <%}else{%>
                                      <img src="../images/spacer.gif" width="1" height="1"><br>
                                    <%}%>
                                  
                            </td>
                           
                          </tr>
                          <tr id="item_row_<%=item.getItemID()%>_desc"><td colspan='8' class="field_content" style='padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Grey;text-transform:none;'><%=item.getItemDescription()%></td></tr>
                      <%}%>  
                              
                        <tr>
                          <td class="total" colspan="3" align="right" valign="middle">Totals:&nbsp;</td>
                          <td class="total" width="15%" valign="middle"><%=kms_df.format(summary.getKMSSummary())%> kms</td>
                          <td class="total" width="10%" valign="middle"><%=curr_df.format(summary.getMealSummary())%></td>
                          <td class="total" width="15%" valign="middle"><%=curr_df.format(summary.getLodgingSummary())%></td>
                          <td class="total" width="10%" valign="middle"><%=curr_df.format(summary.getOtherSummary())%></td>
                          <td class="total no-print" width="*" valign="middle"></td>
                        </tr>  
                        <tr><td colspan=8>&nbsp;</td></tr>                       
                        <tr>
                          <td class="total_label" colspan="2" align="right" valign="middle"></td>
                                                    
                              <%while(rate_summaries.hasNext()){
                                rate_summary = (TravelClaimRateSummary) rate_summaries.next();
                               if(rate_summary.getKMSSummary() > 0){%>
                                
                                <td valign="middle" colspan=4><b>YOUR KM RATE:</b> <span style="color:green;"><%=kms_rate_df.format(rate_summary.getPerKilometerRate())%></span>
								<br/><%=kms_df.format(rate_summary.getKMSSummary()) + "kms x " + kms_rate_df.format(rate_summary.getPerKilometerRate()) + " = "%></td>
                                <td><b><%=curr_df.format(rate_summary.getKMSTotal())%></b></td> 
                                <%}%>
                              <%}%>
                            <td class="no-print"></td>
                          
                        </tr> 
                        <tr><td colspan=8>&nbsp;</td></tr>                                                  
                        <tr>
                          <td class="total_label" colspan="6" align="right" valign="middle">Total Due:</td>                         
                          <td class="summary_total" valign="middle"><%=curr_df.format(summary.getSummaryTotal())%></td>
                          <td class="no-print"></td>
                        </tr>
                       <tr><td colspan=8>&nbsp;</td></tr> 
                        <tr>
                          <td colspan="8" height="10">&nbsp;</td>
                        </tr>
                        
                        <%if(
                        		(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED))
                        		
                        		|| 		
                        		
                        		(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW") && (claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED) || claim.getCurrentStatus().equals(TravelClaimStatus.REVIEWED))  )
                        		
                        		)
                        		
                        		
                        		
                        		
                        		{%>
                          <tr class="no-print">
                           
                            <td colspan="8" align="right" valign="bottom">
                              <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/addnote-off.png" class="img-swap" title="Add note." onclick="openModalDialog('<%=claim.getClaimID()%>','travelclaimnote','<%=claimtitle%>,<%=FullName%>');">
                              <!-- <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/save-off.png" class="img-swap" title="Save changes to claim." onclick="openModalDialog('<%=claim.getClaimID()%>','savetravelclaim','<%=claimtitle%>,<%=FullName%>');">-->
                               <a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><img style="padding-right:10px;padding-bottom:2px;" src="includes/img/print-off.png" class="img-swap" title="Print claim."></a><br>
                            </td>
                          </tr>
                        <%}else{%>
                          <tr class="no-print">
                            <td colspan="8" align="right" valign="bottom">
                              <a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});">
                              <img style="padding-right:10px;padding-bottom:2px;" src="includes/img/print-off.png" class="img-swap" title="Print claim."></a><br>
                            </td>
                          </tr>
                        <%}%>
                    <%}%>
                   
        </table>
                    </td>
                    </tr>
                    
                  </table>
                  
                  <br/><br/>
                  <table width="100%">
                  <tr>
                      <td>                      
                      	<%=claim.getFiscalYear()%> Total Amount Claimed To Date: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(total_claimed)%></span>
                      	     <%if(budget != null){ %>
                      			<br/><%=claim.getFiscalYear()%> Amount Claimed Against Budget:<span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmountClaimed())%></span>
                      			<br/><%=claim.getFiscalYear()%> Pre-submission Amount: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmountPreclaimed())%></span>
                      			<br/><%=claim.getFiscalYear()%> Approved Budget: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmount())%></span>
                      			<br/><%=claim.getFiscalYear()%> Remaining Available Funds: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmount() - budget.getAmountClaimed() - budget.getAmountPreclaimed())%></span>
                      		<%}%>	
                     </td>
                    </tr>                  
                  </table>
                  
                  
                 
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form>
   </div>
    
       <div id="myModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle"></h4>
                </div>
                <div class="modal-body">
                    <p id="title1"></p>
                    <p class="text-warning" id="title2"></p>
                    <div id="selectbox" style="display:none;">
                    	<select id="supervisor_id">
                    		<option value="SELECT YEAR">SELECT SUPERVISOR</option>
                          		<%while(iter.hasNext()){
                              		p = (Personnel) iter.next();
                              		if((p.getPersonnelID() != usr.getPersonnel().getPersonnelID()) 
                                		|| usr.getUserRoles().containsKey("DIRECTOR") || usr.getUserRoles().containsKey("ADMINISTRATOR")){%>
                              				<option style="text-transform:capitalize;" value="<%=p.getPersonnelID()%>" <%=((sup != null)&&(sup.getPersonnelID() == p.getPersonnelID()))?"SELECTED":""%>><%=p.getFullName().toLowerCase()%></option>
                          		<%  }
                            	}
                          		%>
                    	
                    	</select>
                    </div>
                    <p class="text-warning" id="title3"></p>
                    <p class="text-warning" id="title4"></p>
                    <div id="sdsvendorbox" style="display:none;">
                    	
                    	<table>
                    	<tr>
                    	<td><b>SDS VENDOR NUMBER:</b>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input type="text" name="sds_ven_num" id="sds_ven_num" style="font-size:11px;width:250px;" value='<%=sds != null ? sds.getVendorNumber() : "" %>'/></td>
                    	</tr>
                    	</table>
						 
                    </div>
                    <div id="glaccountbox" style="display:none;">
                    	<table>
                    	<tr>
                    	<td colspan='2' align="center"><b><span id="optionaltitle">Mandatory Information</span></b><br /><br /></td></tr>
                    	<tr>
                    	<td><b>GL ACCOUNT CODE:</b>&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input type="text" name="glaccount" id="glaccount" style="font-size:11px;width:250px;" value='<%=acct_code != null ? acct_code : "" %>'/></td>
                    	</tr>
                    	</table>
						 
                    </div>
                    <div id="teacherpaybox" style="display:none;">
                    	<table>
                    	<tr>
                            <td width="*" valign="middle" align="right" style="padding-right:5px;"><input type="checkbox" id="sds_tchr_par" name="sds_tchr_par"></td>
                            <td width="70%" valign="middle" align="left"><span valign="middle">Process through teacher payroll?</span></td>
                        </tr>
                    	</table>
						 
                    </div>
                    <div id="declinenotes" style="display:none;">
                    	<table width="95%" align="center">
                    	<tr>
                    	<td colspan='2' align="center"><b>Note: (Optional)</b><br /><br /></td></tr>
                    	<tr>
                    	<td colspan='2' align='center'><textarea id="note" name="note" style="width:90%;height:100px;"></textarea></td>
                    	</tr>
                    	</table>
						 
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>  	

        	<script>
		$('document').ready(function(){
    			$( ".requiredinput_date" ).datepicker({
      		      	changeMonth: false,//this option for allowing user to select month
      		      	changeYear: false, //this option for allowing user to select from year range
      		      	dateFormat: "dd/mm/yy"
      		      
      		 	});
    			$( "#item_meals" ).blur();
    			$( "#item_lodging" ).blur();
    			$( "#item_other" ).blur();
    			window.parent.$('#claim_details').css('height', $('#add_claim_item_form').height()+100);
				$("#glaccount").mask("9-9999-9-99-9999-99-999", {placeholder: "_-____-_-__-____-__-___"});
				
		});
		</script>


<script>
$('#item_desc').keypress(function(e) {
    var tval = $('#item_desc').val(),
        tlength = tval.length,
        set = 500,
        remain = parseInt(set - tlength);
    $('#remainder').text(remain);
    if (remain <= 0 && e.which !== 0 && e.charCode !== 0) {
        $('#item_desc').val((tval).substring(0, tlength - 1))
    }
})

</script>

  <!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>	