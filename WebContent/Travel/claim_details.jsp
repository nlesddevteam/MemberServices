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
  
  SimpleDateFormat sdf_year = null;
  SimpleDateFormat sdf_month = null;
  SimpleDateFormat sdf_day = null;
  
  SimpleDateFormat sdf_title = null;
  SimpleDateFormat cal_sdf = null;
  SimpleDateFormat histF = null;
  DecimalFormat val_df = null;
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
  histF = new SimpleDateFormat("yyyy-MM-dd @ HH:mm:ss");
  cal_sdf = new SimpleDateFormat("MM/dd/yyyy");
  sdf_title = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
  
  sdf_year = new SimpleDateFormat("yyyy");
  sdf_month = new SimpleDateFormat("MM");
  sdf_day = new SimpleDateFormat("dd");
  val_df = new DecimalFormat("###0.00");
  curr_df = new DecimalFormat("$#,##0.00");
  kms_df = new DecimalFormat("#,##0");
  kms_rate_df = new DecimalFormat("$#,##0.000");
  color_off = "#FFFFFF";
  color_on = "#FEF153";  
  TravelClaimNote note = null;  
  HistoryItem history = null;
  Iterator n_items = null;  
  n_items = claim.getNotes().iterator();
  
  budget = (TravelBudget) request.getAttribute("BUDGET");
  String claimDescription = "N/A";
  double total_claimed = ((Double)request.getAttribute("TOTAL_CLAIMED")).doubleValue();
  String acct_code = null;
  SDSInfo sds = null;
  sds = claim.getPersonnel().getSDSInfo();
  
  if((claim.getSDSGLAccountCode()!=null)&&(!claim.getSDSGLAccountCode().trim().equals("10000000000000000")))
  {
    acct_code = claim.getSDSGLAccountCode();
  }
  //else if((sds != null) && (sds.getAccountCode()!= null) && (!sds.getAccountCode().trim().equals("10000000000000000")))
  //{
    //acct_code = sds.getAccountCode();
  //}
  else
  {
    acct_code = null;
  }
  
  Personnel p = null;
  Personnel sup = null;
  Iterator iter = null;
  Supervisors supervisorslist = new Supervisors();
  iter = supervisorslist.iterator();
  boolean isAdmin = false;
  int id = -1;
  int cur_status; 
  claim = (TravelClaim) request.getAttribute("TRAVELCLAIM");
  String RealName = "";	
  String FullName = "";  
  String claimtitle="";             
  if(claim instanceof PDTravelClaim){
	  claimtitle="PD - " + ((PDTravelClaim)claim).getPD().getTitle();
  }else{
	  claimtitle=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
  }

  id = claim.getClaimID();
  cur_status = claim.getCurrentStatus().getID();
  
  	Integer claimid=(Integer)request.getAttribute("claimid");
	Integer claimmonth=(Integer)request.getAttribute("fiscalmonth");
	Integer claimyear=(Integer)request.getAttribute("fiscalyear");
	Integer lastdaymonth=(Integer)request.getAttribute("lastdaymonth");
	isAdmin = usr.getUserRoles().containsKey("ADMINISTRATOR");	 	 
	FullName = claim.getPersonnel().getFullNameReverse().replace("'", "&#8217;").toUpperCase();	  
	RealName = claim.getPersonnel().getFullNameReverse().replace("'", "&#8217;"); 
	n_items = claim.getNotes().iterator();
	Iterator h_items = null;  
	h_items = claim.getHistory().iterator();  	
	
	
	DecimalFormat dollar_f = null;	
	dollar_f = new DecimalFormat("$#,##0");
	
	
%>
			
<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");     
    			$('[data-toggle="popover"]').popover();
    			
    			
    		});	
			
				function showit(target){
					document.getElementById(target).style.display = 'block';
					}
			
				function hideit(target){
					document.getElementById(target).style.display = 'none';
					}
				
				function valid(f) {
					f.value = f.value.replace(/[^\w\s,\/.$+=-]/gi,'');
					f.value = f.value.replace('\n',' ');
					} 
</script>

 <script>

 $('document').ready(function(){
		 
	 $("#claimNotesTable").DataTable({
		  "order": [[ 0, "asc" ]],
		  "lengthChange": false,
		  "paging":   false,
		  "responsive": true
	 });	
	 
	 $("#claimHistoryTable").DataTable({
		  "order": [[ 0, "asc" ]],
		  "lengthChange": false,
		  "paging":   false,
		  "responsive": true
	 });	 
	 
dtable=$("#claimItemsTable").DataTable({
				  "order": [[ 0, "desc" ]],				
				  "lengthChange": false,
				  "paging":   false,
				  "responsive": true,
			        "columnDefs": [
			            { responsivePriority: 1, targets: 0 },
			            { responsivePriority: 10001, targets: 4 },
			            { orderable: false, targets: 7}
			        ],
				  dom: 'Bfrtip',
			        buttons: [			        	
			        	//'colvis',
			        	'copy', 
			        	'csv', 
			        	'excel'      	
			        ],
				  "lengthMenu": [[10, 20, 50, 100, -1], [10, 20, 50, 100, "All"]],				  
				  "footerCallback": function ( row, data, start, end, display ) {
			            var api = this.api(), data;			 
			            // Remove the formatting to get integer data for summation
			            var intVal = function ( i ) {
			                return typeof i === 'string' ?
			                    i.replace(/[\$,]/g, '')*1 :
			                    typeof i === 'number' ?
			                        i : 0;
			            };
			 
			            // Total over all pages			             
			            totalKM = api.column(4).data().reduce( function (a, b) { return Math.round(intVal(a) + intVal(b)); }, 0 );
			            totalMeals = api.column(5).data().reduce( function (a, b) { return (intVal(a) + intVal(b)).toFixed(2); }, 0 );
			            totalLodging = api.column(6).data().reduce( function (a, b) { return (intVal(a) + intVal(b)).toFixed(2); }, 0 );			           
			           	<%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)){%>
			           		totalOther = String(totalOther1.toFixed(2));
			        	<%}else{%>
                     		totalOther = api.column(7).data().reduce( function (a, b) { return (intVal(a) + intVal(b)).toFixed(2); }, 0 );
                     	<%}%>			            
                    //Update fields.
                     $( "#updateDTable" ).click(function(e) {	    
                    	  e.preventDefault();
                         var totalOther1="";        
                        previousTotal = $("#totalDUE").text();                        
                        previousTotal = ((+previousTotal) - (+totalOther)).toFixed(2);                         
                    	 $('#claimItemsTable').find('input[type=text]').each(function() {                    		 
                    		 totalOther1= (+totalOther1) +(+(this.value));                    		
                    	    });                    	 
                    	 totalOther = String(totalOther1.toFixed(2));
                    	 $( api.column( 7 ).footer() ).html(totalOther);                    	                  	 
                    	 newTotal =(+previousTotal)+(+totalOther);              	 
                    	 $("#totalDUE").text(newTotal);
                     }); 
			          
			                   
			            
			            // Total over this page NOT NEEDED YET ONE PAGE FOR ITEMS
			            //pageTotalKM = api.column( 4, { page: 'current'} ).data().reduce( function (a, b) {return (intVal(a) + intVal(b)).toFixed(2) ;}, 0 );
			 			
			            
			            // Update footer
			            $( api.column( 4 ).footer() ).html(totalKM);
			            $( api.column( 5 ).footer() ).html(totalMeals);
			            $( api.column( 6 ).footer() ).html(totalLodging);
			            $( api.column( 7 ).footer() ).html(totalOther);
				  
				  
				  }
				  
				  
			  });	

//dtable.cell({row:2, column:7}).data('0.00');
//dtable.draw();
//dtable.draw();

 });
 
 

 
 
</script>




	 <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/singlefile.png" style="max-width:100px;" border=0/>    
	<div class="siteHeaderBlack"><span  style="text-transform:capitalize;"><%=claim.getPersonnel().getFullNameReverse()%></span>'s Claim for 
				<%if(claim instanceof PDTravelClaim){%>              
		              PD <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
		        <%}else if(claim instanceof TravelClaim){%>
		              <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
		        <%}%>
	</div>		       
	
	<div class="no-print">
						<% if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION)) { %>
									 This is your claim entry form page. You will see three tabs below. 
									 The <b>DETAILS</b> tab for your claim entry and status, the <b>HISTORY</b> tab for a history of work completed on this claim, and a <b>NOTES</b> tab which will show any messages left by Travel Admins or supervisor for you 
									 regarding your claim such as more information or receipts required. <i>(The claim in such cases will be set as Payment Pending until required information is provided and validated.)</i>. 
									 <br/><br/>	 	
									Simply fill out the form below and click <b>ADD ITEM</b> to add an item to your opened claim <i>(You can add multiple items to a claim)</i>. 	 
									To remove or edit a claim item you already entered, use the <b>EDIT</b> or <b>DELETE</b> Tools at the far right of each listed claim item. <i>(When you click on edit, please wait as the data is loaded back into the form)</i>. To print a copy of the claim properly formatted, simply click on <b>PRINT</b>. If the listed supervisor is not correct, use the <b>CHANGE SUPERVISOR</b> option to select. <i>(If your supervisor is not listed for selection, please contact your supervisor directly as they may need to be added as a supervisor).</i>
									<br/><br/>Once you have completed entry of all items and are ready to submit for processing, click on <b>SUBMIT CLAIM FOR PROCESSING</b> link below.
						<%} %>
						
						<%if(claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>	
									<div class="alert alert-danger" style="max-width:85%;">
									<b>NOTICE:</b> Claim has been <b>rejected</b> by your supervisor. 
									Please check your claim for errors <i>(amounts, wrong supervisor, or invalid claimed item)</i> or check <b>NOTES</b> tab below for possible reason. 
									Correct any issue(s) and re-submit this claim, or delete.
									</div>
						<%} %>	
						
						<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PAYMENT_PENDING)){%>	
									<div class="alert alert-info" style="max-width:85%;">
									<b>NOTICE:</b> Claim has been set to <b>Pending Information</b>. 
									Travel Admins have set your claim as Pending. Please check NOTICES or <b>NOTES</b> tab below left for reason.
									</div>
						 <%} %>
						
						<!-- DISPLAY NOTES (If any) IF REJECTED OR PENDING, Once processed hide. -->	
						
						<%if(n_items.hasNext() && (claim.getCurrentStatus().equals(TravelClaimStatus.PAYMENT_PENDING) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED))){%>
						                  <div class="alert alert-danger" style="max-width:85%;"><b>NOTICE(S) RE THIS CLAIM:</b><br/>
						                  <ul>  
					                      <%while(n_items.hasNext()){
					                        note = (TravelClaimNote) n_items.next();%>
					                        <li><%=note.getNote()%><br/>
					                        <i>(Request Posted: <%=note.getNoteDate().toString()%> by <span style="text-transform:Capitalize;"><%=note.getPersonnel().getFullNameReverse()%>)</span>.</i>                        
					                      <%}%>                      
					                      </ul></div>
					    <%}%>
	</div>
	<div class="no-print" style="padding-top:10px;padding-bottom:15px;text-align:center;">
	<a href="#" class="noJump btn btn-danger btn-xs" title="Back" onclick="loadingData();loadMainDivPage('back');return false;"><i class="fas fa-step-backward"></i> Back</a>
						<%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID())||(cur_status == TravelClaimStatus.REJECTED.getID())){%>
					            <%if(!claim.getItems().isEmpty()){%>
					            		<a href="#" class="noJump btn btn-xs btn-primary" title="Submit this claim for processing." onclick="openModalDialog('<%=id%>','submitclaim','<%=claimtitle%>');"><i class="fas fa-sign-in-alt"></i> Submit Claim for Processing</a>
					             <%}%>
					              		<a href="#" class="noJump btn btn-xs btn-warning" onclick="openModalDialog('<%=claim.getClaimID()%>','changesupervisor','none');"><i class="fas fa-user-check"></i> Change Your Supervisor</a>
					             		<a href="#" class="noJump btn btn-danger btn-xs"  title="Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> Delete Claim</a>
					                   
					         <%}else if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")
                                                        && (claim.getSupervisor() != null && (claim.getSupervisor().getPersonnelID() == usr.getPersonnel().getPersonnelID()))
                                                        &&((cur_status == TravelClaimStatus.SUBMITTED.getID())
                                                        || (cur_status == TravelClaimStatus.REVIEWED.getID()))){%>
					           			<a href="#" class="noJump btn btn-xs btn-success" title="Approve this claim." onclick="openModalDialog('<%=id%>','supervisorapprove','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-clipboard-check"></i> Approve this Claim</a>
					          			<a href="#" class="noJump btn btn-xs btn-danger" title="Decline this claim." onclick="openModalDialog('<%=id%>','supervisordecline','<%=claimtitle%>,<%=RealName%>');"><i class="far fa-times-circle"></i> Decline this Claim</a>
					            
					            <%}else if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")
					            						&&((cur_status == TravelClaimStatus.APPROVED.getID()) 
					            						|| (cur_status == TravelClaimStatus.PAYMENT_PENDING.getID()))){%>
					            		<a href="#" class="noJump btn btn-xs btn-primary" title="Pay this claim." onclick="openModalDialog('<%=id%>','paytravelclaim','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-cogs"></i> Process this Claim</a>
					            		<a href="#" class="noJump btn btn-xs btn-info" title="Payment pending." onclick="openModalDialog('<%=id%>','paypendingtravelclaim','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-file-invoice-dollar"></i> Set Payment Pending</a>				
					          
					          <%}else{%>
					           <!-- Do nothing for now -->
					          <%}%>
					           <esd:SecurityAccessRequired roles="TRAVEL-CLAIM-DELETE">
					           <!-- Allow AP to delete invalid claims or twice submitted claims. Cannot delete Approved or paid claims. -->
					           	<%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID()) 
					           			|| (cur_status == TravelClaimStatus.REVIEWED.getID()) 
					           			|| (cur_status == TravelClaimStatus.SUBMITTED.getID()) 
					           			|| (cur_status == TravelClaimStatus.PAYMENT_PENDING.getID()) 
					           			|| (cur_status == TravelClaimStatus.REJECTED.getID())){%>
					          		 	 <a href="#" class="noJump btn btn-danger btn-xs" title="Accounts Payable - Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> AP Delete Claim</a>
					          		 	 <%} %>
					          	</esd:SecurityAccessRequired>	
					          		 	
					          		<%if(isAdmin && claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()){%>
					          		<a href="#" class="noJump btn btn-danger btn-xs" title="Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> Admin Delete Claim</a>
					          		 <%}%>   
					          		
			<br/>   		
  </div>
  
  <div style="clear:both;"></div>  
      
  <!-- Nav tabs -->
<ul class="nav nav-tabs no-print" >
  <li class="nav-item">
    <a class="nav-link active" id="detailTAB" data-toggle="tab" href="#details" style="background-color:#FAFAD2;font-weight:bold;"><i class="fas fa-info-circle"></i> Details</a>
  </li>
  <li class="nav-item">
    <a class="nav-link"  id="historyTAB" data-toggle="tab" href="#history" style="background-color:#FFF0F5;font-weight:bold;"><i class="fas fa-history"></i> History</a>
  </li>
  <li class="nav-item">
    <a class="nav-link"  id="noteTAB" data-toggle="tab" href="#notes" style="background-color:#F5FFFA;font-weight:bold;"><i class="far fa-clipboard"></i> Notes</a>
  </li>
</ul>



<!-- Tab panes -->
<div class="tab-content" id="theTABS" style="padding:0px;border:1px solid silver;border-top:0px;">

 <!-- CLAIM DETAILS ENTRY ----------------------------------------------------------------------------------------->
  <div class="tab-pane active" id="details" style="background-color:#FAFAD2;padding:5px;">

  <form name="add_claim_item_form" id="add_claim_item_form" class="was-validated" method="post" action="<%=(request.getAttribute("EDIT")!=null)?"editTravelClaimItem.html":"addTravelClaimItem.html"%>">

      <input type="hidden" name="id"  id="id" value=<%=claim.getClaimID()%>>
      <input type="hidden" name="cm"  id="cm" value=<%=claimmonth%>> 
      <input type="hidden" name="cy"  id="cy" value=<%=claimyear%>> 
      <input type="hidden" name="ldm"  id="ldm" value=<%=lastdaymonth%>> 
      
      <%if(request.getAttribute("EDIT")!=null){%>
        <input type="hidden" name="iid" value=<%=failed_item.getItemID()%>>
      <%}%>
      <input type="hidden" name="op" value="CONFIRM"> 

         <div  style="float:left;padding-top:10px;text-transform:Capitalize;font-size:18px;width:50%;"><b>Claimant:</b> <%=FullName%></div>         
         
         <div style="float:right;font-size:26px;color:Silver;width:50%;text-align:right;">
					               <%if(claim instanceof PDTravelClaim){%>              
					              			<%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
					              <%}else if(claim instanceof TravelClaim){%>
					              			<%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
					              <%}%>
		    </div>
		   <div style="clear:both;"></div>       
		   	
                      <!-- Load image for claim file. -->
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
		              <b>Supervisor: </b><span style="text-transform:capitalize;"><a href='mailto:<%=(claim.getSupervisor()!=null?claim.getSupervisor().getEmailAddress():"")%>'><%=(claim.getSupervisor()!=null?claim.getSupervisor().getFullNameReverse():"N/A")%></a></span>
		              <%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID())||(cur_status == TravelClaimStatus.REJECTED.getID())){%>
		              &nbsp; ( <a href="#" class="noJump" onclick="openModalDialog('<%=claim.getClaimID()%>','changesupervisor','none');">Change</a> )
					  <%} %>
		              <br/><b>Your KM Rate:</b> <span id="kmRates" style="color:Green;">Add item to display your rate.</span><br/>
					
					<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
						<br/>
							 <span style="color:Red;" class="no-print">
							 <b>NOTE:</b>  If your supervisor listed is above is incorrect, please use the change supervisor link above.</span>
		       <br/>
					<%}%>              
              <br/>              
            
        
        <%if(claim instanceof PDTravelClaim){%>        
				        <b>PD Title:</b> <%=((PDTravelClaim)claim).getPD().getTitle().replace("\"", "")%>	<br/>			             
				        <b>PD Date:</b> <%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>   <br/>          
       
        <% } else if(claim instanceof TravelClaim) { %>         				
         				<b>Claim Date:</b> <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %><br/>          
        <%}%>
                  
       <% if(claim instanceof PDTravelClaim){ %>							                  
						 <b>PD Description:</b><br/>
						 <%=((PDTravelClaim)claim).getPD().getDescription().replace("\"", "")%>							                      
        <%}%>
                    							
           <br/>         							
                    							
             <b>Claim Status:</b><br/>
             
						<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />                                
									                                <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">
									                                			<div class="alert alert-warning" style="text-align:center;margin-top:5px;padding:2px;"><b>PRE-SUBMISSION:</b> Claim is in Pre-Submission mode and is ready to complete. Please fill in your claim details and submit when ready for processing.</div>
									                                			<script>$('#statPreSub').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                			<div class="alert alert-info" style="text-align:center;margin-top:5px;padding:2px;"><b>SUBMITTED:</b> Claim has been submitted to supervisor.</div>
									                                			<script>$('#statSubmitted').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                			<div class="alert alert-info" style="text-align:center;margin-top:5px;padding:2px;"><b>REVIEWED:</b> Claim has been reviewed by  supervisor.</div>
									                                			<script>$('#statReviewed').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                			<div class="alert alert-success" style="text-align:center;margin-top:5px;padding:2px;"><b>APPROVED:</b> Claim has been approved by  supervisor.</div>
									                                			<script>$('#statApproved').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                			<div class="alert alert-danger" style="text-align:center;margin-top:5px;padding:2px;"><b>REJECTED:</b> Claim has been rejected by supervisor. <br/>Please check your claim for errors (amounts, wrong supervisor, or invalid claimed item) or check Notes tab above for possible reason. Correct any issue(s) and re-submit this claim, or delete.</div>
									                                			<script>$('#statRejected').css('display', 'inline-block');</script>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                			<div class="alert alert-warning" style="text-align:center;margin-top:5px;padding:2px;"><b>PENDING MORE INFORMATION:</b> Claim is pending further action. <br/>Please check NOTES tab above and/or any emails from Travel Claims staff re your claim.</div>
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
				                        												<div class="alert alert-success" style="text-align:center;margin-top:5px;padding:2px;"><b>PAID</b> Claim has been processed and marked as paid. 
				                        												Please allow anywhere from 2-10 business days for deposit to show in your account. 
				                        												If you have NOT been paid, please contact support below.</div>									                                		
									                                					<script>$('#statPaid').css('display', 'inline-block');</script>                        												
                        												</c:when>                        												
                        												<c:when test="${((claimPaidDateStamp ne '0') and (claimExportDateStamp ne '0')) and (todayDateStamp le claimCheckDateStamp)}">                    												
                        															 <div class="alert alert-info" style="text-align:center;margin-top:5px;padding:2px;"><b>PROCESSED:</b> Claim has been processed and is pending payment. 
			                        												   Please allow time for processing of your payment and final deposit anywhere from <b>2-10 business days</b>. 
			                        												   Claim may show as PROCESSED for up to 30 days after any payment has been made.  
			                        												   If there is an issue with final payment, you will be notified before any deposit is made.</div>									                                		
									                                				<script>$('#statProcessed').css('display', 'inline-block');</script>                        												
                        												</c:when>              												
                        												<c:when test="${claimPaidDateStamp ne '0' and claimExportDateStamp eq '0'}">
                        															<div class="alert alert-info" style="text-align:center;margin-top:5px;padding:2px;"><b>PROCESSING:</b> Claim is being processed. 
                        															Please allow 2-10 business days for your claim to be processed. If there is an issue with your claim, you will be notified before it is submitted for payment.</div>									                                		
									                                				<script>$('#statProcessing').css('display', 'inline-block');</script>
                        												</c:when>
                        												<c:otherwise>                        												
                        															<div class="alert alert-danger" style="text-align:center;margin-top:5px;padding:2px;"><b>ERROR:</b> There seems to have been a problem. Please contact supervisor or accounts payable.</div>
                        															<script>$('#statError').css('display', 'inline-block');</script>                        												
                        												</c:otherwise>
                        												</c:choose>                        												
									                                	</c:when>
									                                	<c:otherwise>
									                                	 				<div class="alert alert-danger" style="text-align:center;margin-top:5px;padding:2px;"><b>ERROR:</b> There seems to have been a problem. Please contact supervisor or accounts payable.</div>
									                                	</c:otherwise>                             
									                                </c:choose>
							                              
                                                                         
                            
                            <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PAID) && usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){%>
                            <div style="clear:both;"></div>   
                            
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
                            
                           
                            <%}%>
               
<%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)) {%>

<% if((request.getAttribute("EDIT") != null) ) {%>
			<script>$('.collapse').collapse();</script>
			

			
<%}else { %>
			<div align="center" class="no-print" style="padding-bottom:10px;">
					<a href="#" class="noJump btn btn-sm btn-success" data-toggle="collapse" data-target="#addClaimItemArea" id="addItemLink"><i class="far fa-plus-square"></i> Add a Item to this Claim</a>
			
			
 <%if(claim instanceof PDTravelClaim){%>
 <script>
       //Set date
	$('.datepicker').datetimepicker({
	    defaultDate: moment({
	    	 year: <%=sdf_year.format(((PDTravelClaim)claim).getPD().getStartDate()) %>,
		    	month:<%=sdf_month.format(((PDTravelClaim)claim).getPD().getStartDate()) %>-1,
		    	day:<%=sdf_day.format(((PDTravelClaim)claim).getPD().getStartDate()) %>	    	
	    }),
	    format: 'L'
	  });	
	</script>	
       
<% } else if(claim instanceof TravelClaim) { %>         				
         			
        <script>
       //Set date 
		$('.datepicker').datetimepicker({
	    defaultDate: moment({
	    	year: <%=Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())%>,
	    	month:<%=claim.getFiscalMonth()%>,
	    	day:01
	    }),
	    format: 'L'
	  });
	</script>					
         				
<%}%>
					
			</div>
<%} %>

<div class="alert alert-success collapse" id="addClaimItemArea">

<div class="siteHeaderGreen"><%=(request.getAttribute("EDIT") != null)?"Edit":"Add"%> Claim Item</div>
 Please enter valid data for all items you add to this claim.  Some entries have been pre-filled for you. To change, simply click on the field and edit as necessary.       
<br/><br/>
 
 <div class="row">
    <div  class="col-xs-12 col-sm-12 col-md-4" style="padding-bottom:5px;">           
   			<label for="item-date" class="mr-sm-2"><b>Claim Date:</b></label>
   			<input  required class="form-control datetimepicker-input datepicker mb-2 mr-sm-2" type="text" placeholder="Date of Claim" data-toggle="datetimepicker" data-target="#item_date" name="item_date" id="item_date" onfocus="this.select();">
   		 	<div class="valid-feedback" style="display:none;">A valid date is entered.</div>
    		<div class="invalid-feedback">ERROR: Please fill out this field.</div>
   		</div>  	
   		<div  class="col-xs-6 col-sm-6 col-md-4" style="padding-bottom:5px;">       	
   			<label for="item_departure_time"><b>Departure Time:</b></label>
    		<input required class="form-control datetimepicker-input departureTimePicker mb-2 mr-sm-2" type="text" placeholder="Departure Date" data-toggle="datetimepicker" data-target="#item_departure_time" name="item_departure_time" id="item_departure_time" onfocus="this.select();">
   			 <div class="form-check"> <label class="form-check-label"><input type="checkbox" class="form-check-input" id="timeDepartureON" value="" /> Is this Overnight? </label></div>
   			<div class="invalid-feedback">TIME ERROR: Please fill out this field as a valid time (i.e. 12:00 AM), unless Overnight, check below.</div>    		
   		</div>
    	<div  class="col-xs-6 col-sm-6 col-md-4" style="padding-bottom:5px;">         
    		<label for="item_return_time"><b>Return Time:</b></label>    				
    		<input required class="form-control datetimepicker-input returnTimePicker mb-2 mr-sm-2" type="text" placeholder="Return Date" data-toggle="datetimepicker" data-target="#item_return_time"  name="item_return_time" id="item_return_time" onfocus="this.select();">
   			 <div class="form-check"> <label class="form-check-label"><input type="checkbox" class="form-check-input" id="timeReturnON" value="" /> Is this Overnight? </label></div>
   			<div class="invalid-feedback">DATE ERROR: Please fill out this field as a valid time (i.e. 12:00 AM), unless Overnight, check below.</div>    		
   		</div>			
   		<div  class="col-xs-6 col-sm-6 col-md-3" style="padding-bottom:5px;">   
   			<label for="item_kms" class="mr-sm-2"><b>KMs Traveled:</b></label>
    		<input required class="form-control mb-2 mr-sm-2 integerOnly" type="text" name="item_kms"  id="item_kms" autocomplete="no" placeholder="# Kilometers" value="<%=(failed_item != null)?""+failed_item.getItemKMS():"0"%>" onkeypress="return isNumber(event);" onfocus="this.select();" maxlength="4" onpaste="return false;">
 			<div class="invalid-feedback">ERROR: Please fill out this field.</div>
 			<div class="decOnlyMsg" style="display:none;"></div>
 		</div> 
    	<div  class="col-xs-6 col-sm-6 col-md-3" style="padding-bottom:5px;">  
 			<label for="item_meals" class="mr-sm-2"><b>Meals ($):</b></label>
  			<input class="form-control" type="text" name="item_meals"  id="item_meals"  placeholder="Meals $" value="<%=(failed_item != null)?failed_item.getItemMeals():"0.00"%>" onkeypress="return isNumberDec(event)" onfocus="removeCurrency(this);this.select();" onblur="validateDollar(this);">
		</div>
		<div  class="col-xs-6 col-sm-6 col-md-3" style="padding-bottom:5px;">    
 			<label for="item_lodging" class="mr-sm-2"><b>Lodging ($):</b></label>
			<input class="form-control" type="text" name="item_lodging" id="item_lodging" placeholder="Lodging $" value="<%=(failed_item != null)?failed_item.getItemLodging():"0.00"%>" onfocus="removeCurrency(this);this.select();" onblur="validateDollar(this);">
 		</div>
 		<div  class="col-xs-6 col-sm-6 col-md-3" style="padding-bottom:5px;">    
 			<label for="item_other" class="mr-sm-2"><b>Other ($):</b></label>
 			<input class="form-control"  type="text" name="item_other"  id="item_other" placeholder="Other $" value="<%=(failed_item != null)?failed_item.getItemOther():"0.00"%>" onfocus="removeCurrency(this);this.select();" onblur="validateDollar(this);">
       </div>               
 
 
 
 	<div  class="col-xs-12 col-sm-12 col-md-12">
 		<label for="item_kms" class="mr-sm-2"><b>Description (Max 500 Characters.):</b></label>            
 	                   <textarea class="form-control" name="item_desc" id="item_desc"  onfocus="this.select();" onkeyup="valid(this)" onblur="valid(this)"><%=(failed_item != null)?failed_item.getItemDescription():""%></textarea> 					
 		
 		<script>
 		$('document').ready(function(){  	
 			//CKEditor Configuration
 			var pageWordCountConf = {
 				    showParagraphs: true,
 				    showWordCount: true,
 				    showCharCount: true,
 				    countSpacesAsChars: true,
 				    countHTML: true,
 				    maxWordCount: -1,
 				    maxCharCount: 500,
 			} 			
 		CKEDITOR.replace('item_desc',{wordcount: pageWordCountConf,height:150});
 		});
 		
 		
 		</script>
 		
		 <span style="font-size:10px;">Description should include all necessary information to review the claim 
		 (eg. departure and return points, and items included in other category). 
		 You are limited to 500 characters. Any ' or &quot; characters and other invalid characters will be automatically removed from any text you enter on submission. 
		 This is not an error. ($+-=,./ are accepted characters.)<br/>
		 </span>
	
	</div>
	
	<div  class="col-xs-12 col-sm-12 col-md-12" style="display:none;" id="itemAttachmentBlock">
	<br/>
 	<div class="alert alert-danger" style="text-align:center;">
 	<b><span class='blink-me' style='float:left;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span> NOTICE: Receipt(s) Maybe Required and/or Updated <span class='blink-me' style='float:right;font-size:20px;'><i class='fas fa-exclamation-triangle'></i></span></b><br/>
 	You have specified and/or changed a claimed value for Lodging and/or a value for Other more than $5.00 in this item. 
 	<b>Please attach receipt(s) or a delay in your claim processing may occur</b>.
 	If you have already submitted receipt(s) (listed below) please update based on any value change you made,  if required.
 	If the Other is for a ferry travel expense, no receipt is required. Please upload any document(s) in PDF format using the Add Receipt(s) option below/right.
  	</div> 	
	</div>
	
	
	<div  class="col-xs-12 col-sm-12 col-md-12">
	
	
		<hr>
	<input type="hidden" id="hidfiledelete" name="hidfiledelete">
	<div style="float:right;"><a href="#" class="noJump btn btn-xs btn-primary" id="butaddb" onclick="addattach();"><i class="fas fa-paperclip"></i> Add Receipt(s)</a></div> 
		<b>Current Receipts):</b><br/>
		Below are a list of any receipt(s) for this claim item.<br/>
		<b>NOTE:</b> If no value entered for lodging and other is less than or equal to $5, (or it is a ferry expense), no receipts are required.
			<%if(failed_item == null || failed_item.getAttachments() == null || failed_item.getAttachments().isEmpty()){ %>
			<ul>
			<li><span style="color:green;">No receipt(s) on file for this item.</span>
			</ul>
			<script>			
			valueLodging = $("#item_lodging").val().replace(/[$,]+/g,"");
			valueOther = $("#item_other").val().replace(/[$,]+/g,"");				
			 if(valueLodging > 0 || valueOther >5) {				
				$("#itemAttachmentBlock").css("display","block");				
			} 		
			</script>
			
		<%}else{ %>
		<table class="table table-bordered table-sm" width="100%" style="font-size:11px;background-color:White;">
		<thead class="thead-light"><tr><th width="75%">Description</th><th width="10%">Date Added</th><th width="15%" class="no-print">Options</th></tr></thead>
		<tbody>	
			<% for (Map.Entry<Integer, TravelClaimFileBean> entry : failed_item.getAttachments().entrySet()){%>	
			<tr valign="middle">			
					<td class="align-middle"><%=(entry.getValue().getFileNotes()!=null)?entry.getValue().getFileNotes():"Receipt" %></td>
					<td class="align-middle"><%=entry.getValue().getDateUploadedFormatted()%></td>
					<td class="no-print align-middle">
							<a class="btn btn-xs btn-primary" href="Attachments/<%=entry.getValue().getFilePath() %>" target="_blank" title="View Receipt"><i class="far fa-eye"></i> VIEW</a>							
							<button type="button" class="btn btn-xs btn-danger" onclick="deletefile(this,'<%=entry.getValue().getId()%>');" title="Delete Receipt"><i class="far fa-trash-alt"></i> DEL</button>							
					</td>		
					</tr>
			<%} %>
			</tbody>
		</table>
		<%} %>
		
		<div class="addFileTableBlock" style="display:none;">
	<table class="table table-sm" id="addtable" width="100%" style="font-size:11px;background-color:White;width:100%;">
		<thead class="thead-light">
		<tr>
		<th width="40%">FILE</th>
		<th width="40%">DESCRIPTION (Max 30 chars)</th>
		<th width="20%">OPTION</th>
		</tr>
		</thead>
		<tbody>		
		</tbody>
	</table>	
	<b>NOTE:</b> Receipt(s) you are adding will only save when you hit <b><%=(request.getAttribute("EDIT")!=null)?"Save Edited":"Add"%> Item</b> below. You can add as many receipts as you require by pressing <b>Add Another</b> for each new one.
	</div>
	
 	</div> 					
 		                 
               </div>       
                        <br/>
                        <div align="center" class="no-print">
                          <%if(request.getAttribute("EDIT") != null){%>
                           			<a href="#" class="noJump btn-sm btn-success btn" title="<%=(request.getAttribute("EDIT")!=null)?"Submit Edited":"Add"%> Claim Item." onclick="loadingData();findTheInvalids();addnewtravelclaimitem('<%=claim.getClaimID()%>','UPDATE','<%=failed_item.getItemID()%>');"><%=(request.getAttribute("EDIT")!=null)?"Save Edited":"Add"%> Claim Item</a>
                          		 	<a href="#" class="noJump btn btn-danger btn-sm"  title="Cancel edit claim item." onclick="loadingData();unloadEditItem('<%=claim.getClaimID()%>');"><i class="fas fa-times"></i> Cancel</a>                           
                          <%} else {%>                          
                          			<a href="#" class="noJump btn btn-sm btn-success" onclick="loadingData();findTheInvalids();addnewtravelclaimitem('<%=claim.getClaimID()%>','ADD','0');"><%=(request.getAttribute("EDIT")!=null)?"Save Edited":"Add"%> Item</a>      
                          			<a href="#" class="noJump btn btn-danger btn-sm"  title="Cancel Add  item." onclick="loadingData();unloadEditItem('<%=claim.getClaimID()%>');"><i class="fas fa-times"></i> Cancel</a>                              
                          <%}%>     
                       </div>
       
      
            </div>           
      <%}%>    
      
    
    <div class="alert alert-primary" id="claimItemsAlertArea">
         
  <div class="siteHeaderBlue">Current Claim Items</div>
                     <%if(!items.hasNext()){%>                    
                    		<div class="alert alert-danger" style="text-align:center;padding:2px;"><b>NOTE:</b> This claim currently has no items. To add an item, use the link above.</div>                     
                    <%}else{ %>
                   		 	<span class="msgArea1">Below are your current claim items for this claim. You will also see a variety of options to search, export, and edit/delete items(if claim has not been submitted/approved).</span>                    
                     <%if(claim.getCurrentStatus().equals(TravelClaimStatus.REVIEWED) || claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED)){ %>
                    		<div class="alert alert-danger" style="text-align:center;margin-top:5px;padding:2px;">
                    		<b>NOTE:</b> This claim has already been submitted. 
                    		You will not be able to edit or delete any entries below unless your supervisor or an administrator rejects the claim. <br/>
                    		If you submitted this claim in error, please contact your supervisor to have it rejected back.</div>
                    <%} %>
                         
        <table id="claimItemsTable" class="table table-condensed table-striped table-bordered claimToPrint" style="font-size:11px;background-color:White;" width="100%">
  		<thead>
  			<tr style="text-transform:uppercase;font-weight:bold;">  			
             		  <td width="10%">Date</td>
                      <td width="20%">Description</td>
                      <td width="15%">Depart - Return</td> 
                      <td width="10%" >Rate($)</td>            
                      <td width="5%" >KMs</td>                      
                      <td width="10%">Meals</td>
                      <td width="10%">Lodging</td>                      
                      <td width="10%">Other</td>
                      <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){ %>
                      <td width="*" class="no-print">Tools</td>
                      <%}%>
            </tr>
           </thead>
          <tbody>
          
                 
                              
                  <%
                        summary = claim.getSummaryTotals();
                        rate_summaries = claim.getRateSummaryTotals().iterator();
                        
                        DecimalFormat df = new DecimalFormat("0.00");
                        
                      	int  itemCounter = 0;%>
                      	 <script>
                         var totalOther1="";
                        </script>
                        <%
                        while(items.hasNext()){
                         			 item = (TravelClaimItem) items.next();%>                          
                                                  
                           <script>
                          $("#kmRates").html("<%=kms_rate_df.format(item.getPerKilometerRate())%>");
                          </script>                          
                          
                          <tr>
                          <% itemCounter++; %>
                            <td><%=sdf.format(item.getItemDate())%></td>
                            <td><%=item.getItemDescription()%>
								<%if(item == null || item.getAttachments() == null || item.getAttachments().isEmpty()){ %>								
								<%if(item.getItemLodging() > 0 || item.getItemOther() >5) {%>
									<span style="font-size:9px;color:red;">&middot; No receipt(s) attached.</span>
								<%}%>
								<%}else{ %>
									<%for (Map.Entry<Integer, TravelClaimFileBean> entry : item.getAttachments().entrySet()) {%>			
									&middot; <a href="Attachments/<%=entry.getValue().getFilePath() %>" target="_blank"><%=(entry.getValue().getFileNotes()!=null)?entry.getValue().getFileNotes():"Receipt" %></a><br />
									<%}%>
								<%}%>
                            </td>
                            <td><%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getDepartureTime()!=null)?item.getDepartureTime():"")))%> - <%=(((item.getDepartureTime() == null) && (item.getReturnTime() == null))?"OVERNIGHT":(((item.getReturnTime()!=null)? item.getReturnTime():"OVERNIGHT")))%></td>
                           <td><%=df.format(item.getPerKilometerRate()) %></td>
                            <td><%=item.getItemKMS()%></td>                            
                            <td><%=df.format(item.getItemMeals())%></td>
                            <td><%=df.format(item.getItemLodging())%></td>
                            <td>
                            <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)){%>
                                      <input class="itemOTHER" type="text" id="item_<%=item.getItemID()%>" name="item_<%=item.getItemID()%>"  value="<%=item.getItemOther()%>">
                                		 <script>                               
		                               				 totalOther1= (+totalOther1) +(+$("#item_<%=item.getItemID()%>").val());
		                                </script>
                              <%}else{%>
                                      <%=df.format(item.getItemOther())%>
                              <%}%>
                            </td>
                            
                             <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
                              <td class="no-print"> 
                                    <%//claimDescription =item.getItemDescription().replaceAll("\\<[^>]*>", "");
                                    claimDescription =item.getItemDescription().replaceAll("\\<.*?\\>","").trim();                                          
                                    %>
                                     	<a href="#" class="noJump btn btn-xs btn-warning" title="Edit claim item." onclick="loadingData();loadEditItem('<%=claim.getClaimID()%>','<%=item.getItemID()%>');"><i class="fas fa-edit"></i></a>                                        
                                    	<a href="#" class="noJump btn btn-xs btn-danger" title="Delete claim item." onclick="openModalDialog('<%=item.getItemID()%>','deletetravelclaimitem','<%=claimtitle%>,<%=sdf.format(item.getItemDate())%>,<%=claimDescription%>');"><i class="fas fa-trash-alt"></i></a>                                     	 
                                 </td>  
                                  <%}%>                         
                          </tr>                         
                          
                      <%}%>  
                                       
                   
                  </tbody>
                  <tfoot>
			            <tr style="font-weight:bold;">
			                <td style="text-align:right">TOTAL:</th>
			                <td></td>
			               <td></td>
			               <td></td>
			               <td></td>
			               <td></td>
			               <td></td>
			               <td></td>
			               <%if(claim.getCurrentStatus().equals(TravelClaimStatus.PRE_SUBMISSION) || claim.getCurrentStatus().equals(TravelClaimStatus.REJECTED)){%>
			               <td class="no-print"></td>
			               <%}%>
			            </tr>
        		</tfoot>
                  
                  </table>
                                                                  
                              <%while(rate_summaries.hasNext()){
                                rate_summary = (TravelClaimRateSummary) rate_summaries.next();
                               if(rate_summary.getKMSSummary() > 0){%>        
                               <br/>                        
                               		<b>YOUR KM RATE:</b> <span style="color:green;"><%=kms_rate_df.format(rate_summary.getPerKilometerRate())%></span>
									<br/><%=kms_df.format(rate_summary.getKMSSummary()) + "kms x " + kms_rate_df.format(rate_summary.getPerKilometerRate()) + " = "%>
                               		<b><%=curr_df.format(rate_summary.getKMSTotal())%></b>
                                <%}%>
                              <%}%>
                              
                          
                              
                        	<div style="font-weight:bold;font-size:16px;float:right;padding-right:10px;">TOTAL DUE: <span id="totalDUE"><%=curr_df.format(summary.getSummaryTotal())%></span></div>
                        
                      <div style="clear:both;"></div>
                        
                        <div align="center" class="no-print">
                        	
                        
                        <%if((usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)) || 		                        		
                        		(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW") && (claim.getCurrentStatus().equals(TravelClaimStatus.SUBMITTED) || claim.getCurrentStatus().equals(TravelClaimStatus.REVIEWED))  )
                        		){%>
                         <a href="#" class="noJump btn btn-xs btn-info"  title="Add note." onclick="openModalDialog('<%=claim.getClaimID()%>','travelclaimnote','<%=claimtitle%>,<%=FullName%>');"><i class="far fa-clipboard"></i> Add Claim Note</a>
                              <!-- <img style="padding-right:5px;padding-bottom:2px;" src="includes/img/save-off.png"  title="Save changes to claim." onclick="openModalDialog('<%=claim.getClaimID()%>','savetravelclaim','<%=claimtitle%>,<%=FullName%>');">-->
                          <a href='#' class="noJump btn btn-xs btn-primary" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print this Claim</a>
                            
                        <%}else{%>                         
                              <a href='#' class="noJump btn btn-xs btn-primary" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print this Claim</a>
                        <%}%>
                        <%if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW") && claim.getCurrentStatus().equals(TravelClaimStatus.APPROVED)){%>
			           		<a href="#" id="updateDTable" class="noJump btn btn-xs btn-danger" style="color:White;"><i class="far fa-sync-alt" style="color:white;"></i> Re-Calculate Other</a>
                        <%}%>
                        </div>
                        
                    <%}%>
                   
    </div>
    
        		             
                      <!-- 	    <br/>        <%=claim.getFiscalYear()%> Total Amount Claimed To Date: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(total_claimed)%></span>    <br/>        
                      
                      	     <%if(budget != null){ %>
                      			<br/><%=claim.getFiscalYear()%> Amount Claimed Against Budget:<span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmountClaimed())%></span>
                      			<br/><%=claim.getFiscalYear()%> Pre-submission Amount: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmountPreclaimed())%></span>
                      			<br/><%=claim.getFiscalYear()%> Approved Budget: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmount())%></span>
                      			<br/><%=claim.getFiscalYear()%> Remaining Available Funds: <span style="color:#FF0000;font-weight:bold;"><%=curr_df.format(budget.getAmount() - budget.getAmountClaimed() - budget.getAmountPreclaimed())%></span>
                      		<%}%>	
                    -->
                 
                    
    </form>
   </div>
 <!-- END CLAIM DETAILS ENTRY -->
 
  
  <!-- CLAIM HISTORY----------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->
  <div class="tab-pane" id="history" style="background-color:#FFF0F5;padding:5px;">
  
 
    <div  style="float:left;padding-top:10px;text-transform:Capitalize;font-size:18px;width:50%;"><b>Claim History for:</b> <%=FullName%></div>         
         
         <div style="float:right;font-size:26px;color:Silver;width:50%;text-align:right;">
					               <%if(claim instanceof PDTravelClaim){%>              
					              			<%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
					              <%}else if(claim instanceof TravelClaim){%>
					              			<%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
					              <%}%>
		    </div>
		    
		   <div style="clear:both;"></div>    
		   
  			<%if(!h_items.hasNext()){%>
                  <div class="alert alert-secondary">This claim currently has no history.</div>
                    <%}else{%>           
           	<table id="claimHistoryTable" class="table table-condensed table-striped table-bordered" style="font-size:11px;background-color:White;" width="100%">
           <thead>                  
                   <tr style="text-transform:uppercase;font-weight:bold;"> 
                      <th width="15%">Date</th>
                      <th width="35%">Action</th>
                      <th width="35%">Performed By</th>
                      <th width="15%">Email Address</th>
                    </tr>
            </thead>
            		<tbody>         	
            		
                    <%while(h_items.hasNext()){                    	
                     history = (HistoryItem) h_items.next();%>
                      <tr>
                        <td><%=history.getDatePerformed().toString()%></td>
                        <td><%=history.getActionPerformed()%></td>
                        <td><%=history.getPerformedBy().getFullName()%></td>
                        <td><a href="mailto:<%=history.getPerformedBy().getEmailAddress()%>?subject=Travel Claim"><%=history.getPerformedBy().getEmailAddress()%></a></td>
                      
                      </tr>
                    <%}%>
                   </tbody>  
              </table>       
                    <br/>
                    <div align="center">
                    <a href='#' class="btn btn-xs btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img border=0 width=300 src=includes/img/nlesd-colorlogo.png></div><br/><br/>',append:'<div align=center style=font-size:9px;margin-top:20px;>95 Elizabeth Avenue, St. John&acute;s, NL &middot; A1B 1R6 &middot; Tel: (709) 758-2372 &middot; Fax: (709) 758-2706</div>'});">
                     <i class="fas fa-print"></i> Print History</a> 
                     </div>
                     <br/>
  <%} %>
  
  
  </div>
  <!-- END CLAIM HISTORY -->

<!-- CLAIM NOTES- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->

  <div class="tab-pane" id="notes" style="background-color:#F5FFFA;padding:5px;">
  
    
  <div  style="float:left;padding-top:10px;text-transform:Capitalize;font-size:18px;width:50%;"><b>Notes for Claimant:</b> <%=FullName%></div>         
         
         <div style="float:right;font-size:26px;color:Silver;width:50%;text-align:right;">
					               <%if(claim instanceof PDTravelClaim){%>              
					              			<%=sdf_title.format(((PDTravelClaim)claim).getPD().getStartDate())%>
					              <%}else if(claim instanceof TravelClaim){%>
					              			<%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %>
					              <%}%>
		    </div>
		   <div style="clear:both;"></div>   
  
    
  
		     <%if(!n_items.hasNext()){%>
                  <div class="alert alert-secondary">This claim currently has no notes.</div>
                    <%}else{%>           
		              
		   	<table id="claimNotesTable" class="table table-condensed table-striped table-bordered" style="font-size:11px;background-color:White;" width="100%">
  			<thead>
  			<tr style="text-transform:uppercase;font-weight:bold;"> 
                      <th width="20%">Date</th>
                      <th width="30%">Submitted By</th>
                      <th width="50%">Note</th>
                    </tr>
           	</thead>
           	<tbody>                  
                      <%while(n_items.hasNext()){
                        note = (TravelClaimNote) n_items.next();%>
		                        <tr>
		                          <td style="padding-left:15px;" width="20%" valign="top" class="field_content"><%=note.getNoteDate().toString()%></td>
		                          <td width="30%" valign="top" class="field_content"><%=note.getPersonnel().getFullNameReverse()%></td>
		                          <td width="50%" valign="top" class="field_content"><%=note.getNote()%></td>
		                        </tr>
                      <%}%>
              </tbody>  
              </table>       
                    <br/>
                     <div align="center">
                    <a href='#' class="btn btn-xs btn-primary no-print" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=300 src=includes/img/nlesd-colorlogo.png></div><br/><br/>'});"><i class="fas fa-print"></i> Print Claim Notes</a> 
                	</div>
                	<br/>
				<%}%>
  
  
  
  </div> 
   <!-- END CLAIM NOTES -->       
</div> 
 
 
 	<div class="no-print" style="text-align:center;padding-top:10px;padding-bottom:15px;">
 	<a href="#" class="noJump btn btn-danger btn-xs" title="Back" onclick="loadingData();loadMainDivPage('back');return false;"><i class="fas fa-step-backward"></i> Back</a>
						<%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID())||(cur_status == TravelClaimStatus.REJECTED.getID())){%>
					            <%if(!claim.getItems().isEmpty()){%>
					            		<a href="#" class="noJump btn btn-xs btn-primary" title="Submit this claim for processing." onclick="openModalDialog('<%=id%>','submitclaim','<%=claimtitle%>');"><i class="fas fa-sign-in-alt"></i> Submit Claim for Processing</a>
					             <%}%>
					              		<a href="#" class="noJump btn btn-xs btn-warning" onclick="openModalDialog('<%=claim.getClaimID()%>','changesupervisor','none');"><i class="fas fa-user-check"></i> Change Your Supervisor</a>
					             		<a href="#" class="noJump btn btn-danger btn-xs"  title="Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> Delete Claim</a>
					                   
					         <%}else if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")
                                                            && (claim.getSupervisor() != null && (claim.getSupervisor().getPersonnelID() == usr.getPersonnel().getPersonnelID()))
                                                        	&&((cur_status == TravelClaimStatus.SUBMITTED.getID())
                                                            || (cur_status == TravelClaimStatus.REVIEWED.getID()))){%>
					           			<a href="#" class="noJump btn btn-xs btn-success" title="Approve this claim." onclick="openModalDialog('<%=id%>','supervisorapprove','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-clipboard-check"></i> Approve this Claim</a>
					          			<a href="#" class="noJump btn btn-xs btn-danger" title="Decline this claim." onclick="openModalDialog('<%=id%>','supervisordecline','<%=claimtitle%>,<%=RealName%>');"><i class="far fa-times-circle"></i> Decline this Claim</a>
					            
					            <%}else if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")
					            						&&((cur_status == TravelClaimStatus.APPROVED.getID()) 
					            						|| (cur_status == TravelClaimStatus.PAYMENT_PENDING.getID()))){%>
					            		<a href="#" class="noJump btn btn-xs btn-primary" title="Pay this claim." onclick="openModalDialog('<%=id%>','paytravelclaim','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-cogs"></i> Process this Claim</a>
					            		<a href="#" class="noJump btn btn-xs btn-info" title="Payment pending." onclick="openModalDialog('<%=id%>','paypendingtravelclaim','<%=claimtitle%>,<%=RealName%>');"><i class="fas fa-file-invoice-dollar"></i> Set Payment Pending</a>				
					          
					          <%}else{%>
					           <!-- Do nothing for now -->
					          <%}%>
					           <esd:SecurityAccessRequired roles="TRAVEL-CLAIM-DELETE">
					           <!-- Allow AP to delete invalid claims or twice submitted claims. Cannot delete Approved or paid claims. -->
					           	<%if((cur_status == TravelClaimStatus.PRE_SUBMISSION.getID()) 
					           			|| (cur_status == TravelClaimStatus.REVIEWED.getID()) 
					           			|| (cur_status == TravelClaimStatus.SUBMITTED.getID()) 
					           			|| (cur_status == TravelClaimStatus.PAYMENT_PENDING.getID()) 
					           			|| (cur_status == TravelClaimStatus.REJECTED.getID())){%>
					          		 	 <a href="#" class="noJump btn btn-danger btn-xs" title="Accounts Payable - Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> AP Delete Claim</a>
					          		 	 <%} %>
					          	</esd:SecurityAccessRequired>	
					          		 	
					          		<%if(isAdmin && claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()){%>
					          		<a href="#" class="noJump btn btn-danger btn-xs" title="Delete this claim." onclick="openModalDialog('<%=id%>','deleteclaim','<%=claimtitle%>');"><i class="far fa-trash-alt"></i> Admin Delete Claim</a>
					          		 <%}%>   
					          		
					          		
  </div>
 
 
 
 
<!-- CLAIM MODAL -->    
       <div id="travelModal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                 <h4 class="modal-title" id="maintitle"></h4>
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>                   
                </div>
                <div class="modal-body">
                                
                    <span id="title1"></span>
                    <span id="title2"></span>
                    
                    <div id="selectbox" style="display:none;">
                    	<select id="supervisor_id" class="form-control">
                    		<option value="-1">SELECT SUPERVISOR</option>
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
                    <span id="title3"></span>
                    <span id="title4"></span>
                    
                    <div id="sdsvendorbox" style="display:none;">
                    SDS VENDOR NUMBER: <input type="text"  class="form-control" name="sds_ven_num" id="sds_ven_num" value='<%=sds != null ? sds.getVendorNumber() : "" %>'/>                     	
                    </div>
                    
                    <div id="glaccountbox" style="display:none;">
                    	<b><span id="optionaltitle">Mandatory Information</span></b>
                    	<b>GL ACCOUNT CODE:</b> <input type="text"  class="form-control" name="glaccount" id="glaccount"  value='<%=acct_code != null ? acct_code : "" %>'/>						 
                    </div>
                    
                    <div id="teacherpaybox" style="display:none;">
                    	<input type="checkbox" id="sds_tchr_par" name="sds_tchr_par"> <span valign="middle">Process through teacher payroll?</span>
                    </div>
                    
                    <div id="declinenotes" style="display:none;">
                    	
                    	<b>Note: (Optional)</b><br/>
                    	<textarea id="note" class="form-control" rows="5" name="note"></textarea>
						 
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-sm btn-success" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>  	

        	<script>
        	
        	
        	
        	
		$('document').ready(function(){  			
			
			
			$('input#item_meals').blur(function(){
			    var num = parseFloat($(this).val());
			    var cleanNum = num.toFixed(2);
			    $(this).val(cleanNum);
			    if(num == "" || num == null) {
			    	$("#item_meals").val("0.00");			    	
			    }
			    if(num/cleanNum < 1){			    	
			    	$(".details_error_message").html("<b>INVALID CURRENCY AMOUNT:</b> Please enter correct format of $0.00.").css("display","block").delay(6000).fadeOut();
			        }
			    });
			$('input#item_lodging').blur(function(){
			    var num = parseFloat($(this).val());
			    var cleanNum = num.toFixed(2);
			    $(this).val(cleanNum);
			    if(num == "" || num == null) {
			    	$("#item_lodging").val("0.00");		    	
			    }
			    if(num/cleanNum < 1){			    	
			    	$(".details_error_message").html("<b>INVALID CURRENCY AMOUNT:</b> Please enter correct format of $0.00.").css("display","block").delay(6000).fadeOut();
			        }
			    });
			$('input#item_other').blur(function(){
			    var num = parseFloat($(this).val());
			    var cleanNum = num.toFixed(2);
			    $(this).val(cleanNum);
			    if(num == "" || num == null) {
			    	$("#item_other").val("0.00");			    	
			    }
			    if(num/cleanNum < 1){			    	
			    	$(".details_error_message").html("<b>INVALID CURRENCY AMOUNT:</b> Please enter correct format of $0.00.").css("display","block").delay(6000).fadeOut();
			        }
			    });
			
    			$( "#item_meals" ).blur();
    			$( "#item_lodging" ).blur();
    			$( "#item_other" ).blur();
    			window.parent.$('#claim_details').css('height', $('#add_claim_item_form').height()+100);
				$("#glaccount").mask("9-9999-9-99-9999-99-999", {placeholder: "_-____-_-__-____-__-___"});
				
		});
		</script>

<!-- ENABLE DATE/TIME PICKERS -->	
<script> 
$('document').ready(function(){  		

      $('.datepicker').datetimepicker({
    	 	   format: 'L',
    		  date: moment('<%=(failed_item != null)?cal_sdf.format(new Date(failed_item.getItemDate().getTime())):cal_sdf.format(Calendar.getInstance().getTime())%>','L')
 				});
      
      
	 $('.departureTimePicker').datetimepicker({		 
		 		format: 'LT',
	 			date: moment('<%=(failed_item != null)?failed_item.getDepartureTime():"8:30 AM"%>','LT')
		 		
	 			});
	
	    $('.returnTimePicker').datetimepicker({
	    	format: 'LT',	
	    	date: moment('<%=(failed_item != null)?failed_item.getReturnTime():"4:30 PM"%>','LT')	    		
	    });
	    

//CKEDITOR.replace('item_desc',{wordcount: pageWordCountConf,height:150});

$('#item_lodging,#item_other').change(function(){		
	//Get rid $
	valueLodging = $("#item_lodging").val().replace(/[$,]+/g,"");
	valueOther = $("#item_other").val().replace(/[$,]+/g,"");
		
	 if(valueLodging > 0 || valueOther >5) {				
		$("#itemAttachmentBlock").css("display","block");				
	} 
	 if( valueLodging ==0 && valueOther <= 5 ) {			
		$("#itemAttachmentBlock").css("display","none");
	}
});

$('#timeDepartureON').change(function(){
	if (this.checked) {
        $('#item_departure_time').val("Overnight");
        $( "#item_departure_time" ).prop( "disabled", true );
    } else {
    	$('#item_departure_time').val("Overnight");
    	 $( "#item_departure_time" ).prop( "disabled",false );
    }
});

$('#timeReturnON').change(function(){
	if (this.checked) {
        $('#item_return_time').val("Overnight");
        $( "#item_return_time" ).prop( "disabled", true );
    } else {
    	 $('#item_return_time').val("4:30 PM");
    	$( "#item_return_time" ).prop( "disabled", false );
    }
});

$( "#addItemLink" ).click(function() {
	$("#addItemLink").css("display","none");
});
    
$( "#detailTAB" ).click(function() {
	$("#theTABS").css("background-color","#FAFAD2");
	});
$( "#historyTAB" ).click(function() {
	$("#theTABS").css("background-color","#FFF0F5");
});
$( "#noteTAB" ).click(function() {
	$("#theTABS").css("background-color","#F5FFFA");
});

});

$("a.noJump").click(function(e) {
    e.preventDefault();  
});

</script>



  <!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>	
	

