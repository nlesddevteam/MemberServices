<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,                
                 org.apache.commons.lang.StringUtils.*,     
                 java.util.*,
                 java.io.*,
                 java.text.*"
        isThreadSafe="false"%>
        
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />

<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
DecimalFormat df = null;
DecimalFormat dollar_f =  null;
Iterator p_iter = null;
SimpleDateFormat sdf_title = null;
SimpleDateFormat sdf_date = null;
DecimalFormat curr_df = null;
DecimalFormat chart_df = null;
DecimalFormat percent_fm = null;


  int c_cnt = 0;
  
  usr = (User) session.getAttribute("usr");
  
  claims = usr.getPersonnel().getTravelClaims();

  df = new DecimalFormat("#,##0.00");
  dollar_f = new DecimalFormat("$#,##0.00");
  percent_fm = new DecimalFormat("##0.00");
  curr_df = new DecimalFormat("$#,##0.00");
  sdf_title = new SimpleDateFormat("EEE, MMM dd, yyyy");
  sdf_date= new SimpleDateFormat("yyyy-MM-dd");

  Vector<TravelClaim> list = null;
	Iterator i = null;

	if(request.getAttribute("claimslist") != null){
		list = (Vector<TravelClaim>)request.getAttribute("claimslist");
		i = list.iterator();
	}

%>

<style>
input { border:1px solid silver;}
</style>
 <script>
 $('document').ready(function(){
	  $("#claimsTableList").DataTable({
				  "order": [[ 0, "desc" ]],
				  "responsive": true,
				  "lengthMenu": [[25, 50, 100, -1], [25, 50, 100, "All"]],
				  dom: 'Blfrtip',
			        buttons: [			        	
			        	//'colvis',
			        	'copy', 
			        	'csv', 
			        	'excel', 
			        	{
			                extend: 'pdfHtml5',
			                footer:true,
			                //orientation: 'landscape',
			                messageTop: '<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase() %>&apos;s Travel/PD Claims ',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5 ]
			                }
			            },
			        	{
			                extend: 'print',
			                //orientation: 'landscape',
			                footer:true,
			                messageTop: '<%=usr.getPersonnel().getFirstName().toLowerCase()%> <%=usr.getPersonnel().getLastName().toLowerCase() %>&apos;s Travel/PD Claims',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3,4,5 ]
			                }
			            }
			        ],
				  
			  });
	  $('#loadingSpinner').css("display","none");	  
 });
    </script>

    
  <c:choose>
  <c:when test="${param.sid ==5}">
   <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/decline1.png" style="max-width:100px;" border=0/>    
		   <div class="siteHeaderRed">Travel Claims Previously Rejected</div>  
		   <script> $.cookie('backurl', 'viewPreviousApproved.html?sid=5', {expires: 1 });</script>
		   Below are a list of travel claims that you have previously rejected.
  </c:when>
  <c:otherwise>
  <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/check-green.png" style="max-width:100px;" border=0/>   
		   <div class="siteHeaderGreen">Travel Claims Previously Approved</div>  
		   <script> $.cookie('backurl', 'viewPreviousApproved.html', {expires: 1 });</script>
		   Below are a list of Travel Claims you have previously approved.   
  </c:otherwise>
  </c:choose>
  <br/><br/>
  <table id="claimsTableList" class="table table-condensed table-striped table-bordered" style="font-size:11px;" width="100%">
				<thead>					
					<tr class="listHeader" style="text-transform:uppercase;">  
						<th width="10%">Submitted</th>
						<c:choose>
						<c:when test="${param.sid ==5}">
						<th width="10%">Rejected</th>
						</c:when>
						  <c:otherwise>
						  <th width="10%">Approved</th>
						  </c:otherwise>
						  </c:choose>
						<th width="15%">Employee</th>					
						<th width="10%">Type</th>
						<th width="25%">Title</th>
						<th width="10%">Amount $</th>
						<th width="10%">Status</th>			
						<th width="10%" >Options</th>			
					</tr>
				</thead>
				<tbody>
					<% while (i.hasNext()) {
						claim = (TravelClaim) i.next();
							if(claim instanceof PDTravelClaim){
					%>							
							<tr style="vertical-align:middle;">
							<td><%=sdf_date.format(((PDTravelClaim)claim).getPD().getStartDate())%></td>
							<td><%=(claim.getApprovedDate()!=null)?claim.getApprovedDate():"<span style='color:Silver;'>N/A</span>" %></td>
							<td><%= claim.getPersonnel().getLastName() + ", " + claim.getPersonnel().getFirstName() %></td>
							<td style="background-color:#ff8400;text-align:center;font-size:11px;color:white;font-weight:bold;">&nbsp;PD CLAIM&nbsp;</td>  
							<td><%= ((PDTravelClaim)claim).getPD().getTitle()  %> - <%=((PDTravelClaim)claim).getPD().getDescription() %></td>
							<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
							<td>							
							<c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />        
                    		 <c:choose>
									                                	<c:when test="${claimStatus eq 1 }">									                                	
									                                	<span style="color:DarkOrange;"><i class="far fa-file-alt"></i> PRE-SUBMISSION</span>				                                		
									                                    </c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;"><i class="fas fa-sign-in-alt"></i> SUBMITTED</span>										                               	                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;"><i class="fas fa-book-reader"></i> REVIEWED</span>									                                	                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;"><i class="far fa-check-square"></i>  APPROVED</span>										                                					                                	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;"><i class="far fa-times-circle"></i> REJECTED</span>									                                		
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;"><i class="far fa-question-circle"></i> PENDING INFO</span>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">				
																		<span style="color:DarkGreen;"><i class="fas fa-hand-holding-usd"></i> PAID</span>
                        												</c:when>
																		</c:choose>
							</td>		
							<td><div align="center"><a href="#" class="btm btn-xs btn-primary" onclick="loadingData();loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');">VIEW</a></div></td>					
							</tr>
						<%}else{ %>
						<tr style="vertical-align:middle;">
						 	<%    String monthClaim= String.format("%02d", claim.getFiscalMonth()+1);  %>                    	
                    		<td><%=Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())+"-"+ monthClaim +"-01" %>	</td>
                    		<td><%=(claim.getApprovedDate()!=null)?claim.getApprovedDate():"<span style='color:Silver;'>N/A</span>" %></td>					
							<td><%= claim.getPersonnel().getLastName() + ", " + claim.getPersonnel().getFirstName() %></td>
							<td style="background-color:#1c90ec;text-align:center;font-size:11px;color:white;font-weight:bold;">&nbsp;MONTHLY&nbsp;</td>
							<td>Standard Claim for <%=Utils.getMonthString(claim.getFiscalMonth()) + " " +  Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) %></td>
							<td><%=curr_df.format(claim.getSummaryTotals().getSummaryTotal()) %></td>
							<td><c:set var="claimStatus" value="<%=claim.getCurrentStatus().getID()%>" />        
                    		 										<c:choose>
									                                	<c:when test="${claimStatus eq 1 }">									                                	
									                                	<span style="color:DarkOrange;"><i class="far fa-file-alt"></i> PRE-SUBMISSION</span>									                                		
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 2 }">
									                                		<span style="color:DarkViolet;"><i class="fas fa-sign-in-alt"></i> SUBMITTED</span>	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 3 }">
									                                		<span style="color:CornflowerBlue;"><i class="fas fa-book-reader"></i> REVIEWED</span>	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 4 }">
									                                		<span style="color:Green;"><i class="far fa-check-square"></i>  APPROVED</span>	
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 5 }">
									                                		<span style="color:Red;"><i class="far fa-times-circle"></i> REJECTED</span>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 6 }">
									                                		<span style="color:Blue;"><i class="far fa-question-circle"></i> PENDING INFO</span>
									                                	</c:when>
									                                	<c:when test="${claimStatus eq 7 }">				
																		<span style="color:DarkGreen;"><i class="fas fa-hand-holding-usd"></i> PAID</span>
                        												</c:when>
																	</c:choose>
							</td>		
							<td><div align="center"><a href="#" class="btm btn-xs btn-primary" onclick="loadingData();loadMainDivPage('viewTravelClaimDetails.html?id=<%=claim.getClaimID()%>');">VIEW</a></div></td>					
							</tr>
						<%} %>
					<%} %>
					
				</tbody>
			</table>
	
   