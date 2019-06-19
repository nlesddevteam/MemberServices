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

<html>
	<head>
		<title>Travel/Expense Claim SDS Export Report - <%=sdf.format(Calendar.getInstance().getTime())%></title>
			<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/travel.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script> 	
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/travel.js"></script>
			<script src="includes/js/jquery.maskedinput.min.js"></script>
    <STYLE TYPE="text/css">
    	@media print {
      	.pagebreak {page-break-after: always;}
      }
    </STYLE>
     <script>
        
  		$( document ).ready(function() {
  			$('#loadingSpinner').css("display","none");
  			window.print();
		});
  		</script> 
	</head>
	<body>
	<div id="printJob"> 
	
	<div class="claimHeaderText">Travel/Expense Claim SDS Export Report</div>
	<div align="right" class="no-print"><a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><img style="padding-right:10px;padding-bottom:2px;" src="includes/img/print-off.png" class="img-swap" title="Print Pages."></a></div>
	
	<%=sdf.format(Calendar.getInstance().getTime())%>
	<p>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	
    <form name="add_claim_item_form" method="post" >
     
       
       
        <%while(claims.hasNext()){
          claim = (TravelClaim)claims.next();
          items = claim.getItems().iterator();%>
            <table width="100%">
          <tr>
            <td>
            
            <div class="claimHeaderText">Claimant: <span style="text-transform:capitalize;"><%=claim.getPersonnel().getFullNameReverse()%></span> 
            
            <div style="float:right;"><%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></div>
            
            
            </div>
            
            
             		  <b>Address:</b>  <%=claim.getPersonnel().getProfile().getStreetAddress() != null ? claim.getPersonnel().getProfile().getStreetAddress() : "N/A" %>, <%=claim.getPersonnel().getProfile().getCommunity() %>, <%=claim.getPersonnel().getProfile().getProvince() %> &middot; <%=claim.getPersonnel().getProfile().getPostalCode() %><br/>
		              <b>Tel:</b> <%=(claim.getPersonnel().getProfile().getPhoneNumber() != null ? claim.getPersonnel().getProfile().getPhoneNumber() : "N/A") %> &nbsp;&middot;&nbsp; <b>Cell:</b> <%=claim.getPersonnel().getProfile().getCellPhoneNumber() != null ? claim.getPersonnel().getProfile().getCellPhoneNumber(): "N/A" %>&nbsp;&middot;&nbsp;
		              <b>Fax:</b> <%=claim.getPersonnel().getProfile().getFaxNumber() != null ? claim.getPersonnel().getProfile().getFaxNumber() : "N/A" %><br/>
		              <b>Email:</b> <a href="mailto:<%=claim.getPersonnel().getEmailAddress()%>"><%=claim.getPersonnel().getEmailAddress()%></a><br/>
		              <b>Position:</b> <%=claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null ? claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() : "N/A" %><br/> 
		              <b>School:</b> <%=(claim.getPersonnel().getSchool() != null ? claim.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%><br/>                                         
		              <b>Supervisor:</b> <a href="mailto:<%=claim.getSupervisor().getEmailAddress()%>"><span style="text-transform:capitalize;"><%=claim.getSupervisor().getFullNameReverse()%></span></a>
					 
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
          <tr>
          <td>
          <div class="claimStatusBlock">
			<div class="claimHeaderText">PD Description</div>
				<%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%>
			</div>		
          
          </td>
                   
          </tr>
        <%}else if(claim instanceof TravelClaim){%>
          <tr>
            <td>
            <b>Claim Date:</b> <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></td>
          </tr>          
        <%}%>
         
         
         
         
         
         
         
          <tr>
            <td>
            
            		<div class="claimStatusBlock">
						<div class="claimHeaderText">Claim Status</div>
	                                <c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
	                                <c:choose>
	                                	<c:when test="${claimStatus eq 1 }">
	                                		<div class="alert alert-warning" style="margin-top:5px;padding:2px;height:20px;"><b>PRE-SUBMISSION:</b> Claim is in Pre-Submission mode. You have yet to submit this claim for processing.</div>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 2 }">
	                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;height:20px;"><b>SUBMITTED:</b> Claim has been submitted to your supervisor.</div>
	                                	<script>$('#statSubmitted').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 3 }">
	                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;height:20px;"><b>REVIEWED:</b> Claim has been reviewed by your supervisor.</div>
	                                	<script>$('#statReviewed').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 4 }">
	                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;height:20px;"><b>APPROVED:</b> Claim has been approved by your supervisor.</div>
	                                	<script>$('#statApproved').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 5 }">
	                                		<div class="alert alert-danger" style="margin-top:5px;padding:2px;height:20px;"><b>REJECTED:</b> Claim has been rejected by your supervisor.</div>
	                                		<script>$('#statRejected').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 6 }">
	                                		<div class="alert alert-info" style="margin-top:5px;padding:2px;height:20px;"><b>PAYMENT PENDING:</b> Claim has been submitted for payment.</div>
	                                	    <script>$('#statPending').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:when test="${claimStatus eq 7 }">
	                                		<div class="alert alert-success" style="margin-top:5px;padding:2px;height:20px;"><b>PAID:</b> Claim has been paid.</div>									                                		
	                                		<script>$('#statPaid').css('display', 'inline-block');</script>
	                                	</c:when>
	                                	<c:otherwise>
	                                	 	<div class="alert alert-danger" style="margin-top:5px;padding:2px;height:20px;"><b>ERROR:</b> There seems to have been a problem. Please contact your supervisor or accounts payable.</div>
	                                	</c:otherwise>                             
	                                </c:choose>
                     </div>
             
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
                            
             
             
             
             
             </td>
            </tr>
            <tr>
              <td>&nbsp;<br/><img src="includes/img/bar.png" width="100%" height=1><br/>&nbsp;</td>
            </tr>
            
            <tr>
              <td>
                      <table width="100%">
                      <tr>
                        <td colspan="6" class="title">Claim Items</td>
                      </tr>
                      <tr>
                         <td width="15%" class="itemsHeader">Date</td>
                         <td width="25%" class="itemsHeader">Dep/Rtn Time</td>
                         <td width="15%" class="itemsHeader">KMs</td>
                         <td width="15%" class="itemsHeader">Meals</td>
                         <td width="15%" class="itemsHeader">Lodging</td>
                         <td width="*" class="itemsHeader">Other</td>
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
                              <td width="15%" class="field_content"><%=sdf.format(item.getItemDate())%></td>
                              <td width="25%" class="field_content"><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getDepartureTime()!=null)?item.getDepartureTime():"") + ((item.getReturnTime()!=null)?"-" + item.getReturnTime():"-OVERNIGHT")))%></td>
                              <td width="15%" class="field_content"><%=item.getItemKMS()%><span class="small_text"><%=(item.getItemKMS() > 0)?" (" + (kms_rate_df.format(item.getPerKilometerRate())) +")":""%></span></td>
                              <td width="15%" class="field_content"><%=curr_df.format(item.getItemMeals())%></td>
                              <td width="15%" class="field_content"><%=curr_df.format(item.getItemLodging())%></td>
                              <td width="*" class="field_content"><%=curr_df.format(item.getItemOther())%></td>
                            </tr>
                        
                          <tr>
                            <td colspan="6" class="field_content" style='padding-bottom:2px;border-bottom: dashed 1px #c4c4c4;color:Grey;text-transform:none;'><%=item.getItemDescription()%></td>
                          </tr>
                          <%}%>
                          
                          <tr>
                            <td class="total" colspan="2" align="right" valign="middle">Totals:&nbsp;</td>
                            <td class="total" width="15%" valign="middle"><%=kms_df.format(summary.getKMSSummary())%> kms</td>
                            <td class="total" width="10%" valign="middle"><%=curr_df.format(summary.getMealSummary())%></td>
                            <td class="total" width="15%" valign="middle"><%=curr_df.format(summary.getLodgingSummary())%></td>
                            <td class="total" width="15%" valign="middle"><%=curr_df.format(summary.getOtherSummary())%></td>
                          </tr>
                          
                          <tr><td colspan=6>&nbsp;</td></tr>  
                          
                          <tr>
                             <td class="total" colspan="2" align="right" valign="middle">Total distance traveled:</td>
                            	
                            
                                <%while(rate_summaries.hasNext()){
                                  rate_summary = (TravelClaimRateSummary) rate_summaries.next();
                                  if(rate_summary.getKMSSummary() > 0){%>
                                   <td valign="middle" colspan=3>
                                   <%=kms_df.format(rate_summary.getKMSSummary()) + "kms x " + kms_rate_df.format(rate_summary.getPerKilometerRate()) + " = "%></td>
                                     <td><%=curr_df.format(rate_summary.getKMSTotal())%>
                                      </td>
                                    
                                  <%}%>
                                <%}%>
                          </tr>
                            
                          <tr><td colspan=6>&nbsp;</td></tr>   
                            
                           <tr>
                          <td class="total_label" colspan="5" align="right" valign="middle">Total Due:</td>                         
                          <td class="summary_total" valign="middle"><%=curr_df.format(summary.getSummaryTotal())%></td>
                        </tr>
                       <tr><td colspan=6>&nbsp;</td></tr> 
                       
                       
                       
                         
                      <%}%>
                    </table>
                  
                   </td>
                </tr> 
          
           </table>
           <div class="pagebreak">&nbsp;</div>
        <%}%>
     
    </form>
    </div>
    <script src="includes/js/jQuery.print.js"></script>	
	</body>
</html>
