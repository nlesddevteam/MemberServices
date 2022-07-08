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
  Iterator eclaims = null;
  Iterator rate_summaries = null;
  Iterator items = null;
  SimpleDateFormat sdf = null;
  SimpleDateFormat cal_sdf = null;
  SimpleDateFormat sdf_title = null;
  DecimalFormat curr_df = null;
  DecimalFormat kms_df = null;
  DecimalFormat kms_rate_df = null;
  DecimalFormat df = null;

  usr = (User) session.getAttribute("usr");
  
  claims = ((Vector) request.getAttribute("TRAVELCLAIMS")).iterator();
  eclaims = ((Vector) request.getAttribute("EXCELCLAIMS")).iterator();
  
  double gst = 0.0, total = 0.0;
  
  sdf = new SimpleDateFormat("MM/dd/yyyy");
  cal_sdf = new SimpleDateFormat("dd/MM/yyyy");
  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
  kms_rate_df = new DecimalFormat("$#,##0.000");
  sdf_title = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  df = new DecimalFormat("0.00");
%>
<html>
  <head>
    <title>Travel Claim Export Data</title>    
 
  
 		
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css">		
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">      
  	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
  	    <link rel="stylesheet" href="/MemberServices/Travel/includes/css/travel.css">	
  	    
  	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>		
  	    <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>		
		<script src="https://cdn.datatables.net/responsive/1.0.7/js/dataTables.responsive.min.js"></script>		
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>	
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.colVis.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/colreorder/1.5.2/js/dataTables.colReorder.min.js"></script>
		<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
		<script src="https://cdn.datatables.net/rowreorder/1.2.6/js/dataTables.rowReorder.min.js"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>				
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>		
				
  	    <script src="/MemberServices/Travel/includes/js/travel.js"></script>	
  	    
    <style>
    body {background-color:white;}
    	@media print {
      	.pagebreak {page-break-after: always;}
      }
      .claimItemTitle {font-weight:bold;font-size:12px;}
       .claimItemData {;font-size:11px;}
      .claimItemTotal {font-weight:bold;font-size:12px;}
      
      
      .msginfo,.msginfo1,.msginfo2 {
	position: fixed;
	z-index:999999;
    top: 50%;left: 50%;
    margin-top: -25px; margin-left: -200px; 
    width: 400px;  height: auto;	
	vertical-align:center;	text-align:center;
	box-shadow: 0 0 0 100% rgba(0,0,0,0.6); box-shadow: 0 0 0 100vmax rgba(0,0,0,0.6);		
	}
      
      
    </style>
     <script>        
     $('#loadingSpinner').css("display","none");
     
  		$( document ).ready(function() {  			
  			 	//startPrint();  	
  			
		}); 		
  		
  		function startPrint() {
  		$(".printTable").css("display","block");
  		 $(".excelTableView").css("display","none");
  		  $(".msginfo2").css("display","block").delay(4000).fadeOut();;
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
  		
<script>		
		$('document').ready(function(){
			
			 setTimeout(function () {  		    	
				 $(".msginfo1").css("display","none");	    	
	  		    }, 4000);
			
			
			
			$('.excelTable').DataTable( {
			    dom: 'Bfrtip',
			    "order" : [[0,"asc"]],
			    buttons: [	
			    	'excel',			    	
			        'csv',
			        'print'
			    ]
			} );
			
			
			mTable = $(".dataOutputTable").dataTable({
				"order" : [[0,"asc"]],		
				"bPaginate": false,
				"bFilter": false,
			    "bInfo": false,
				responsive: true	,
				
			});						
		});		
		</script>
</head>
  <body>
 
 <div style="padding:10px;font-size:12px;">
 
<div class="msgok alert alert-success" style="display:none;font-weight:bold;">Closing Print Job.<br/>You will be redirected. Please Wait....</div>
<div class="msginfo1 alert alert-info" style="display:block;font-weight:bold;">Building data file and loading data. This process is automatic so please wait.</div>
<div class="msginfo2 alert alert-info" style="display:none;font-weight:bold;">Loading data for print job. This process is automatic so please wait until PRINT DIALOG opens..</div>

<div class="excelTableView">
<script>$(".msginfo1").css("display","block");</script>
<br/><br/>
<span style="font-size:16px;font-weight:bold;">TRAVEL CLAIM DATA EXPORT</span><br/><br/>

To export/download this claim data in an Excel or CVS format, please select one of the following options below left atop the table. 
After clicking, your browser will start the download process of the xls file to your default downloads directory. You can also print this table. <br/><br/>
If you wish, you can Print these claims individually using the RED link at bottom of this table. (Select Save/Print as PDF to save as file when print dialog opens. Please be patient.)
<br/><br/>
<table class="excelTable table table-striped" width="100%" style="font-size:11px;background-color:White;padding-top:10px;">

<thead>
<tr>
<td width="10%">INVOICE #</td>
<td width="10%">PROCESSED</td>
<td width="10%">VENDOR #</td>
<td width="15%">NAME</td>
<td width="30%">DESCRIPTION</td>
<td width="5%">TOTAL</td>
<td width="5%">GST</td>
<td width="15%">GL CODE</td>
</tr>
</thead> 
<tbody>


<%while(eclaims.hasNext()){
          claim = (TravelClaim)eclaims.next();
        %>
      
      
    <tr>
    <td width="10%">TC<%=claim.getClaimID() %></td>
    <td width="10%"><%=sdf.format(claim.getPaidDate()) %></td>
    <td width="10%"><%=claim.getPersonnel().getSDSInfo().getVendorNumber().replaceAll("-", "")%></td>
    <td width="15%"><%=claim.getPersonnel().getFullNameReverse().toUpperCase() %></td>
    <td width="30%">      
      	<%if(claim instanceof PDTravelClaim){%>
	      	<%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%> PD Claim<br/> 
	      	<%=((PDTravelClaim)claim).getPD().getTitle().replace("\"", "")%><br/>          				        				
			<%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%><br/>
			Travel: (<%=claim.getSummaryTotals().getKMSSummary() %>kms) - <%=curr_df.format(claim.getSummaryTotals().getKMSTotal()) %><br/>
			Lodging: <%=curr_df.format(claim.getSummaryTotals().getLodgingSummary()) %> - 
			Meals: <%=curr_df.format(claim.getSummaryTotals().getMealSummary()) %> -
			Other: <%=curr_df.format(claim.getSummaryTotals().getOtherSummary()) %>
		<%}else if(claim instanceof TravelClaim){%>
	        <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %> Claim<br/>
	        Travel: (<%=claim.getSummaryTotals().getKMSSummary()%>kms) - <%=curr_df.format(claim.getSummaryTotals().getKMSTotal()) %><br/>
			Lodging: <%=curr_df.format(claim.getSummaryTotals().getLodgingSummary()) %> - 
			Meals: <%=curr_df.format(claim.getSummaryTotals().getMealSummary()) %> -
			Other: <%=curr_df.format(claim.getSummaryTotals().getOtherSummary()) %>
        <%}%>                
      
                     
     </td>     
     <td width="5%"><%=claim.getSummaryTotals().getSummaryTotal() %></td> 
     <td width="5%">
      <%
      total = claim.getSummaryTotals().getSummaryTotal();     
      if (total > 0) {    	  
		gst = (total / 1.15) * 0.05;									
      } else {     	  
      	gst = 0.00;
      }
      %>
      <%=df.format(gst) %>
     </td>           
     <td width="15%"><%=(claim.getSDSGLAccountCode()!=null)?
                                      claim.getSDSGLAccountCode().substring(0, 1) + "-"
                                      + claim.getSDSGLAccountCode().substring(1, 5) + "-"
                                      + claim.getSDSGLAccountCode().substring(5, 6) + "-"
                                      + claim.getSDSGLAccountCode().substring(6, 8) + "-"
                                      + claim.getSDSGLAccountCode().substring(8, 12) + "-"
                                      + claim.getSDSGLAccountCode().substring(12, 14) + "-"
                                      + claim.getSDSGLAccountCode().substring(14):""%>
      </td>     
       </tr>                            	
    <%}%>
</tbody>
</table>
<br/>
<div align="center">If you wish to print these claims into one file with one full claim with all details per page, please use the Print below. If just the table above, use the Print atop left. The Excel and CSV links above will initiate a file download to open in Excel.<br/><br/>
<a class="btn btn-xs btn-info no-print" title="Print Claims" href="#" onclick="startPrint();return false;">Print These Claim Details</a> <a class="btn btn-xs btn-danger no-print" title="Back" href="index.html">Back</a></div>
<br/><br/>
</div>





<div class="printTable" style="display:none;">
	
	<br/><br/><br/><br/><br/><br/>
	<div align="center"><img src="includes/img/nlesd-colorlogo.png" style="max-width:400px;" border=0/>
	<br/><br/><br/><br/><br/><br/>
	<div style="font-size:28px;"><b>EXCEL Travel/Expense Claim Data Report</b>
	<br/><br/><br/><br/>
	<b><%=sdf.format(Calendar.getInstance().getTime())%></b>
	<br/><br/>	<br/><br/><br/><br/>	<br/><br/>
	<span style="text-transform: capitalize;font-size:16px;"><b>Exported by:</b> <%=usr.getPersonnel().getFullNameReverse() %></span>
	</div></div>
	<br/><br/>	<br/><br/><br/><br/><br/><br/>
	<span style="font-size:11px;">
	<b>Confidentiality Warning:</b> This message and any attachments are intended for the sole use of the intended recipient(s), and may contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, conversion to hard copy, copying, circulation or other use of this message and any attachments is strictly prohibited. If you received this email in error, please delete the message and attachments immediately and notify the sender by return email. Thank you!
	<br/><br/>
	<b>avis de confidentialit�:</b> Ce courriel, ainsi que tout renseignement ci-inclus, est destin� uniquement au(x) destinaire(s) susmentionn�(s) et peut contenir de l'information confidentielle.  Si vous n'�tes pas le destinaire pr�vu, tout examen, copie, impression, reproduction, distribution ou autre utilisation de ce courriel est strictement interdit. Si vous avez re�u ce message par erreur, veuillez en aviser imm�diatement l'exp�diteur par retour de ce courriel et veuillez supprimer imm�diatement cette communication.  Merci.
	</span>

	<div class="pagebreak">&nbsp;</div>
        <%while(claims.hasNext()){
          claim = (TravelClaim)claims.next();
          items = claim.getItems().iterator();%>
       <br/>   
                    
          
     <table width="100%" style="font-size:14px;width:100%;">
     <tr>
     <td colspan="2">
     <span style="font-weight:bold;text-transform:capitalize;font-size:30px;"><%=claim.getPersonnel().getFullNameReverse()%></span> 
      <div style="float:right;font-weight:bold;font-size:30px;"><%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></div>
      </td>
     </tr>
     <tr>
     <td colspan="2">&nbsp;</td>
     </tr>
          <tr>
            <td width="50%" style="font-size:12px;vertical-align:top;">            
             		  <b>ADDRESS:</b>  <%=claim.getPersonnel().getProfile().getStreetAddress() != null ? claim.getPersonnel().getProfile().getStreetAddress() : "N/A" %>, <%=claim.getPersonnel().getProfile().getCommunity() %>, <%=claim.getPersonnel().getProfile().getProvince() %> &middot; <%=claim.getPersonnel().getProfile().getPostalCode() %><br/>
		              <b>TEL:</b> <%=(claim.getPersonnel().getProfile().getPhoneNumber() != null ? claim.getPersonnel().getProfile().getPhoneNumber() : "N/A") %> &nbsp;&middot;&nbsp; <b>Cell:</b> <%=claim.getPersonnel().getProfile().getCellPhoneNumber() != null ? claim.getPersonnel().getProfile().getCellPhoneNumber(): "N/A" %>&nbsp;&middot;&nbsp;
		              <b>FAX:</b> <%=claim.getPersonnel().getProfile().getFaxNumber() != null ? claim.getPersonnel().getProfile().getFaxNumber() : "N/A" %><br/>
		              <b>EMAIL:</b> <a href="mailto:<%=claim.getPersonnel().getEmailAddress()%>"><%=claim.getPersonnel().getEmailAddress()%></a><br/><br/>
					  <b>POSITION:</b> <%=claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() != null ? claim.getPersonnel().getPersonnelCategory().getPersonnelCategoryName() : "N/A" %><br/> 
		              <b>SCHOOL:</b> <%=(claim.getPersonnel().getSchool() != null ? claim.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%><br/>                                         
		              <b>SUPERVISOR:</b> <a href="mailto:<%=claim.getSupervisor().getEmailAddress()%>"><span style="text-transform:capitalize;"><%=claim.getSupervisor().getFullNameReverse()%></span></a><br/>
			</td>		 
			<td width="50%" style="font-size:12px;vertical-align:top;"> 
           <%if(claim instanceof PDTravelClaim){%>
            			<b>CLAIM TYPE:</b> PD<br/>
            			<b>PD TITLE:</b> <%=((PDTravelClaim)claim).getPD().getTitle().replace("\"", "")%><br/>
          				<b>PD DATE:</b> <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%><br/>
           				<b>PD DESCRIPTION:</b><br/>
						<%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%>
		  
        <%}else if(claim instanceof TravelClaim){%>
       
            			<b>CLAIM TYPE:</b> Regular Monthly<br/>
          				<b>CLAIM DATE:</b> <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
         <%}%>                
           				<br/><b>CLAIM STATUS: </b>
	                                <c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
	                                <c:choose>
	                                	<c:when test="${claimStatus eq 1 }">
	                                		PRE-SUBMISSION: Claim is in Pre-Submission mode. You have yet to submit this claim for processing.	                                	
	                                	</c:when>
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
             
             
             		
                           <br/><br/> <b>TEACHER PAYROLL? </b> <%=claim.isPaidThroughTeacherPayroll()?"YES":"NO"%><br/>
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
              <td colspan="2">
              <br/>
             <div style="font-size:16px;font-weight:bold;">CLAIM ITEMS LIST</div>
              

                      <table  class="dataOutputTable table table-sm responsive" width="100%" style="font-size:11px;background-color:White;">
                      <thead>
                      <tr>
                         <th width="10%" class="claimItemTitle">DATE</th>
                         <th width="35%" class="claimItemTitle">DESCRIPTION</th>
                         <th width="15%"  class="claimItemTitle">DEP/RTN</th>
                         <th width="10%"  class="claimItemTitle">KMs</th>
                         <th width="10%"  class="claimItemTitle">MEALS</th>
                         <th width="10%"  class="claimItemTitle">LODGING</th>
                         <th width="10%"  class="claimItemTitle">OTHER</th>
                      </tr>
                      </thead>
                      <tbody>                     
                      <%if(!items.hasNext()){%>
                        <tr>
                        <td>N/A</td>
                        <td>This claim has no items.</td>
                        <td>N/A</td>
                        <td>N/A</td>
                        <td>N/A</td>
                        <td>N/A</td>
                        <td>N/A</td>
                        </tr>
                         </tbody>
                          </table>   
                      <%}else{
                          summary = claim.getSummaryTotals();
                          rate_summaries = claim.getRateSummaryTotals().iterator();
                          while(items.hasNext()){
                            item = (TravelClaimItem) items.next();%> 
                            <tr>
                              <td width="10%" class="claimItemData"><%=sdf.format(item.getItemDate())%></td>
                              <td width="35%" class="claimItemData"><%=item.getItemDescription()%></td>
                              <td width="15%" class="claimItemData"><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getDepartureTime()!=null)?item.getDepartureTime():"") + ((item.getReturnTime()!=null)?"-" + item.getReturnTime():"-OVERNIGHT")))%></td>
                              <td width="10%" class="claimItemData"><%=item.getItemKMS()%><span class="small_text"><%=(item.getItemKMS() > 0)?" (" + (kms_rate_df.format(item.getPerKilometerRate())) +")":""%></span></td>
                              <td width="10%" class="claimItemData"><%=curr_df.format(item.getItemMeals())%></td>
                              <td width="10%" class="claimItemData"><%=curr_df.format(item.getItemLodging())%></td>
                              <td width="10%" class="claimItemData"><%=curr_df.format(item.getItemOther())%></td>
                            </tr>                        
                          
                          <%}%>
                          </tbody>
                          <tfoot>
                          <tr>
                            <td></td>
                            <td></td>
                            <td class="claimItemTotal" align="right" valign="middle">TOTALS:&nbsp;</td>
                            <td class="claimItemData" valign="middle"><%=kms_df.format(summary.getKMSSummary())%> kms</td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getMealSummary())%></td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getLodgingSummary())%></td>
                            <td class="claimItemData" valign="middle"><%=curr_df.format(summary.getOtherSummary())%></td>
                          </tr>
                          </tfoot>
                          </table>                        
                               
                         <br/>&nbsp;<br/>
                         <div style="font-size:18px;"><b>TOTAL DUE:</b> <%=curr_df.format(summary.getSummaryTotal())%></div>

                      <%}%>
                      
                   
                  
                   </td>
                </tr> 
          
           </table>
           <div class="pagebreak">&nbsp;</div>
        <%}%>

 
  </div>
</div>
</body>
</html>
