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
  TravelClaimRateSummary rate_summary = null;
  TravelClaimSummary summary = null;
  Iterator claims = null;
  Iterator rate_summaries = null;
  Iterator items = null;
  SimpleDateFormat sdf = null;
  SimpleDateFormat cal_sdf = null;
  SimpleDateFormat sdf_title = null;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;
  DecimalFormat kms_rate_df = null;

  usr = (User) session.getAttribute("usr");
  
  claims = ((Vector) request.getAttribute("TRAVELCLAIMS")).iterator();
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  cal_sdf = new SimpleDateFormat("dd/MM/yyyy");
  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
  kms_rate_df = new DecimalFormat("$#,##0.000");
  sdf_title = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
 
%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>	
		<script src="/MemberServices/Travel/includes/js/travel.js"></script>	 
		<link rel="stylesheet" href="/MemberServices/Travel/includes/css/travel.css">	
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">      
  	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <style>
    	@media print {
      	.pagebreak {page-break-after: always;}
      }
      .claimItemTitle {font-weight:bold;font-size:12px;}
       .claimItemData {;font-size:11px;}
      .claimItemTotal {font-weight:bold;font-size:12px;}
      
    </style>
     <script>        
     $('#loadingSpinner').css("display","none");
     
  		$( document ).ready(function() {  			
  			 	startPrint();  		
		}); 		
  		
  		function startPrint() {
  		 
  		  $(".msginfo").css("display","block").delay(3000).fadeOut();
  		    setTimeout(function () {  		    	
  		    	loadPrint(); 		    	
  		    }, 4000);
  		}
  		
  		
  		
  		function loadPrint() {
  		    window.print();
  		  $(".msgok").css("display","block").delay(5000).fadeOut();
  		    setTimeout(function () {  		    	
  		    	document.location.href = "index.jsp";   	  		    	
  		    }, 1000);
  		}
  		
  		</script> 


<div class="msgok alert alert-success" style="display:none;font-weight:bold;">Closing Print Job.<br/>You will be redirected. Please Wait....</div>
<div class="msginfo alert alert-info" style="display:none;">Loading Print Job. Please Wait....</div>
	
	<br/><br/><br/><br/><br/><br/>
	<div align="center"><img src="includes/img/nlesd-colorlogo.png" style="max-width:400px;" border=0/>
	<br/><br/><br/><br/><br/><br/>
	<div style="font-size:28px;"><b>Travel/Expense Claim Data Report</b>
	<br/><br/><br/><br/>
	<b><%=sdf.format(Calendar.getInstance().getTime())%></b>
	<br/><br/>	<br/><br/><br/><br/>	<br/><br/>
	<span style="text-transform: capitalize;font-size:16px;"><b>Exported by:</b> <%=usr.getPersonnel().getFullNameReverse() %></span>
	</div></div>
	<br/><br/>	<br/><br/><br/><br/><br/><br/>
	<span style="font-size:11px;">
	<b>Confidentiality Warning:</b> This message and any attachments are intended for the sole use of the intended recipient(s), and may contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, conversion to hard copy, copying, circulation or other use of this message and any attachments is strictly prohibited. If you received this email in error, please delete the message and attachments immediately and notify the sender by return email. Thank you!
	<br/><br/>
	<b>avis de confidentialité:</b> Ce courriel, ainsi que tout renseignement ci-inclus, est destiné uniquement au(x) destinaire(s) susmentionné(s) et peut contenir de l'information confidentielle.  Si vous n'êtes pas le destinaire prévu, tout examen, copie, impression, reproduction, distribution ou autre utilisation de ce courriel est strictement interdit. Si vous avez reçu ce message par erreur, veuillez en aviser immédiatement l'expéditeur par retour de ce courriel et veuillez supprimer immédiatement cette communication.  Merci.
	</span>

	<div class="pagebreak">&nbsp;</div>
        <%while(claims.hasNext()){
          claim = (TravelClaim)claims.next();
          items = claim.getItems().iterator();%>
       <br/>   
                    
          
     <table width="100%" style="font-size:14px;width:100%;">
     <tr>
     <td>
     <span style="font-weight:bold;text-transform:capitalize;font-size:30px;"><%=claim.getPersonnel().getFullNameReverse()%></span> 
      <div style="float:right;font-weight:bold;font-size:30px;"><%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></div>
      </td>
     </tr>
          <tr>
            <td>            
             		  <b>ADDRESS:</b>  <%=claim.getPersonnel().getProfile().getStreetAddress() != null ? claim.getPersonnel().getProfile().getStreetAddress() : "N/A" %>, <%=claim.getPersonnel().getProfile().getCommunity() %>, <%=claim.getPersonnel().getProfile().getProvince() %> &middot; <%=claim.getPersonnel().getProfile().getPostalCode() %><br/>
		              <b>TEL:</b> <%=(claim.getPersonnel().getProfile().getPhoneNumber() != null ? claim.getPersonnel().getProfile().getPhoneNumber() : "N/A") %> &nbsp;&middot;&nbsp; <b>Cell:</b> <%=claim.getPersonnel().getProfile().getCellPhoneNumber() != null ? claim.getPersonnel().getProfile().getCellPhoneNumber(): "N/A" %>&nbsp;&middot;&nbsp;
		              <b>FAX:</b> <%=claim.getPersonnel().getProfile().getFaxNumber() != null ? claim.getPersonnel().getProfile().getFaxNumber() : "N/A" %><br/>
		              <b>EMAIL:</b> <a href="mailto:<%=claim.getPersonnel().getEmailAddress()%>"><%=claim.getPersonnel().getEmailAddress()%></a><br/>
		              <b>POSITION:</b> <%=claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null ? claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() : "N/A" %><br/> 
		              <b>SCHOOL:</b> <%=(claim.getPersonnel().getSchool() != null ? claim.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%><br/>                                         
		              <b>SUPERVISOR:</b> <a href="mailto:<%=claim.getSupervisor().getEmailAddress()%>"><span style="text-transform:capitalize;"><%=claim.getSupervisor().getFullNameReverse()%></span></a><br/>
					 
					
         
           <%if(claim instanceof PDTravelClaim){%>
            			<b>CLAIM TYPE:</b> PD<br/>
            			<b>PD TITLE:</b> <%=((PDTravelClaim)claim).getPD().getTitle().replace("\"", "")%><br/>
          				<b>PD DATE:</b> <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%><br/>
           				<br/><b>PD DESCRIPTION:</b><br/>
						<%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%>
		  
        <%}else if(claim instanceof TravelClaim){%>
       
            			<b>CLAIM TYPE:</b> Regular Monthly<br/>
          				<b>CLAIM DATE:</b> <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %><br/>
         <%}%>                
           				<b>CLAIM STATUS: </b>
	                                <c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
	                                <c:choose>
	                                	<c:when test="${claimStatus eq 1 }">
	                                		PRE-SUBMISSION: Claim is in Pre-Submission mode. You have yet to submit this claim for processing.	                                	</c:when>
	                                	<c:when test="${claimStatus eq 2 }">
	                                		SUBMITTED: Claim has been submitted to your supervisor.	                                	
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 3 }">
	                                		REVIEWED: Claim has been reviewed by your supervisor.	                                	
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 4 }">
	                                		APPROVED: Claim has been approved by your supervisor.	                                	
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 5 }">
	                                		REJECTED: Claim has been rejected by your supervisor.
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 6 }">
	                                		PAYMENT PENDING: Claim has been submitted for payment.	                         
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 7 }">
	                                		PAID: Claim has been paid.
	                                	</c:when>
	                                	<c:otherwise>
	                                	 	ERROR: There seems to have been a problem. Please contact your supervisor or accounts payable.
	                                	</c:otherwise>                             
	                                </c:choose>
             
             
             		
                           <br/> <b>TEACHER PAYROLL? </b> <%=claim.isPaidThroughTeacherPayroll()?"YES":"NO"%><br/>
                            <b>GL ACCOUNT:</b>
                                    	<%=(claim.getSDSGLAccountCode()!=null)?
                                      claim.getSDSGLAccountCode().substring(0, 1) + "-"
                                      + claim.getSDSGLAccountCode().substring(1, 5) + "-"
                                      + claim.getSDSGLAccountCode().substring(5, 6) + "-"
                                      + claim.getSDSGLAccountCode().substring(6, 8) + "-"
                                      + claim.getSDSGLAccountCode().substring(8, 12) + "-"
                                      + claim.getSDSGLAccountCode().substring(12, 14) + "-"
                                      + claim.getSDSGLAccountCode().substring(14):""%>
                    
        </td>
            </tr>           
            
            <tr>
              <td>
              <br/>
                      <table width="100%" style="font-size:12px;width:100%;">
                      <tr>
                        <td colspan="6" style="font-size:14px;font-weight:bold;">CLAIM ITEMS LIST</td>
                      </tr>
                      <tr>
                         <td width="15%" class="claimItemTitle">DATE</td>
                         <td width="25%"  class="claimItemTitle">DEP/RTN TIME</td>
                         <td width="15%"  class="claimItemTitle">KMs</td>
                         <td width="15%"  class="claimItemTitle">MEALS</td>
                         <td width="15%"  class="claimItemTitle">LODGING</td>
                         <td width="*"  class="claimItemTitle">OTHER</td>
                      </tr>
                       <tr><td colspan="6" style="padding-bottom:2px;border-top: solid 1px #c4c4c4;color:Grey;height:5px;text-transform:none;"></td></tr>
                      <%if(!items.hasNext()){%>
                        <tr><td colspan="6" style="padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Red;height:5px;text-transform:none;">This claim has no items.</td></tr>
                      <%}else{
                          summary = claim.getSummaryTotals();
                          rate_summaries = claim.getRateSummaryTotals().iterator();
                          while(items.hasNext()){
                            item = (TravelClaimItem) items.next();%>                            
                                                     
                            <tr>
                              <td class="claimItemData"><%=sdf.format(item.getItemDate())%></td>
                              <td class="claimItemData"><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getDepartureTime()!=null)?item.getDepartureTime():"") + ((item.getReturnTime()!=null)?"-" + item.getReturnTime():"-OVERNIGHT")))%></td>
                              <td class="claimItemData"><%=item.getItemKMS()%><span class="small_text"><%=(item.getItemKMS() > 0)?" (" + (kms_rate_df.format(item.getPerKilometerRate())) +")":""%></span></td>
                              <td class="claimItemData"><%=curr_df.format(item.getItemMeals())%></td>
                              <td class="claimItemData"><%=curr_df.format(item.getItemLodging())%></td>
                              <td class="claimItemData"><%=curr_df.format(item.getItemOther())%></td>
                            </tr>
                        
                          <tr>
                            <td colspan="6"  style='padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Black;text-transform:none;'><%=item.getItemDescription()%></td>
                          </tr>
                          <%}%>
                          
                          <tr>
                            <td class="claimItemTotal" colspan="2" align="right" valign="middle">Totals:&nbsp;</td>
                            <td class="claimItemData" valign="middle"><%=kms_df.format(summary.getKMSSummary())%> kms</td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getMealSummary())%></td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getLodgingSummary())%></td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getOtherSummary())%></td>
                          </tr>
                          
                          <tr><td colspan=6>&nbsp;</td></tr> 
                          
                          <tr style="display:none;">
                             <td  class="claimItemTotal" colspan="2" align="right" valign="middle">TOTAL DISTANCE TRAVELLED :</td>                            	
                            
                                <%while(rate_summaries.hasNext()){
                                  rate_summary = (TravelClaimRateSummary) rate_summaries.next();
                                  if(rate_summary.getKMSSummary() > 0){%>
                                   <td valign="middle" colspan=3>
                                   <%=kms_df.format(rate_summary.getKMSSummary()) + "kms x " + kms_rate_df.format(rate_summary.getPerKilometerRate()) + " = "%></td>
                                     <td><%=curr_df.format(rate_summary.getKMSTotal())%> </td>
                                    
                                  <%}%>
                                <%}%>
                          </tr>                            
                          <tr><td colspan=6>&nbsp;</td></tr>                               
                           <tr>
                          <td  class="claimItemTotal" colspan="5" align="right" valign="middle">TOTAL DUE:</td>                         
                          <td  class="claimItemData" valign="middle"><%=curr_df.format(summary.getSummaryTotal())%></td>
                        </tr>
                       <tr><td colspan=6>&nbsp;</td></tr> 

                      <%}%>
                    </table>
                  
                   </td>
                </tr> 
          
           </table>
           <div class="pagebreak">&nbsp;</div>
        <%}%>

 
  

