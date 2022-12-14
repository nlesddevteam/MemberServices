<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
                 com.awsd.travel.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,
                 java.sql.*"
        isThreadSafe="false"%>
        
        <%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>   
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/travel.tld" prefix="tra" %>
		<%@ taglib uri="/WEB-INF/personnel.tld" prefix="per" %>


<esd:SecurityCheck permissions="TRAVEL-CLAIM-ADMIN" />

<%
  DistrictPersonnel members = null;
  Role tcRole = null; 
  Personnel per = null;  
  Iterator iter = null; 
  tcRole= RoleDB.getRole("TRAVELCLAIM APPROVED RATE");
  members = new DistrictPersonnel();
 ArrayList<TravelClaimKMRate> rates = TravelClaimKMRateDB. getTravelClaimKMRates(); 
	DecimalFormat df = null;
	DecimalFormat dollar_f = null;
 df = new DecimalFormat("#,##0");
 dollar_f = new DecimalFormat("$#,##0");
 
%>
		<script>
			
			
			$(document).ready(function(){    
						
				
				
    			$('#loadingSpinner').css("display","none");           		      		
    			 $.cookie('backurl', 'travel_rates.jsp', {expires: 1 });
			   	 
		   		 $("#claims-table").DataTable({
		   			"responsive": true,
		   		  "order": [[ 0, "asc" ]],
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
		   	                messageTop: 'Travel/PD Claims ',
		   	                messageBottom: null,
		   	                exportOptions: {
		   	                    columns: [ 0, 1, 2 ,3,4,5,6]
		   	                }
		   	            },
		   	        	{
		   	                extend: 'print',
		   	                //orientation: 'landscape',
		   	                footer:true,
		   	                messageTop: 'Travel/PD Claims',
		   	                messageBottom: null,
		   	                exportOptions: {
		   	                    columns: [ 0, 1, 2,3,4,5,6]
		   	                }
		   	            }
		   	        ],		  
		   		  "lengthMenu": [[100, 250, 500, -1], [100, 250, 500, "All"]]
		   	  
		   	  
		   	  });	
		   	 
		   	
		   		  
		   		  
		    });
		       </script>
		
	<style>
input { border:1px solid silver;}
optgroup{ font-size:10px; }
</style>  		
		
        <img class="pageHeaderGraphic" src="/MemberServices/Travel/includes/img/dollar.gif" style="max-width:120px;" border=0/> 
	<div class="siteHeaderBlue">Travel Claim Approved Rate Assignment</div>
	<br/>Below is the table of Regular Rate Members (Left) and Approved Rate Members (Right). You can add/remove by selecting a name and hitting Add or Remove. Below that is a table of currently approved rate members for a quick display.<br/><br/>

		<b>Base Rate:</b> <%=rates.get(0).getBaseRate() %><br/>		 
		<b>Approved Rate:</b> <%=rates.get(0).getApprovedRate() %>
		<br/><br/>
	Above  Government Rates are effective <b><%=rates.get(0).getEffectiveStartDateFormatted() %></b> thru to <b><%=rates.get(0).getEffectiveEndDateFormatted() %></b>
	
	
	
	
	<br/><br/>
	<div class="alert alert-danger" id="details_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
     <div class="alert alert-success" id="details_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
	
	<form name="membership" action="approvedRateMembership.html" method="post" class="form-inline">
          <input type="hidden" name="uid" value="<%=tcRole.getRoleUID()%>">
          <input type="hidden" name="op" value="NONE">
          <div class="siteSubHeaderBlue">Employee Rate Assignment</div>
                          
            <table class="table table-condensed" style="font-size:11px;background-color:White;" width="100%">			
           <thead>
           <tr>
           <th width="40%">BASE RATE (<%=rates.get(0).getBaseRate() %>) EMPLOYEES (<span id="rrCount"></span>)</th>
           <th width="20%"></th>
           <th width="40%">APPROVED RATE (<%=rates.get(0).getApprovedRate() %>) EMPLOYEES (<span id="aprCount"></span>)</th>
           </tr>
           </thead>
           <tbody>
           <tr>
           <td>
           				<select style="font-size:10px;" size="10" name="available" id="available" class="form-control form-control-sm">           				
                          <%
                          int rrcnt = 0;
                            iter = members.iterator(); 
                            while(iter.hasNext())
                            {
                              per = (Personnel) iter.next();
                              if(!tcRole.getRoleMembership().containsKey(new Integer(per.getPersonnelID())))
                              {
                           //Do NOT display esdnl addresses and invalid accounts. ONLY NLESD.
                          if(per.getEmailAddress().contains("nlesd.ca")) {
                          rrcnt++;
                          %>   
                        <option value="<%=per.getPersonnelID()%>"><%=per.getFullName()%> [<%=per.getEmailAddress()%>]</option>
                       <% }}} %>                        
                          </select>
           </td>
           <td style="text-align:center;vertical-align:middle;">  
         	<a href="#" id="addMember" onClick="addnewmembers();" class="disabled btn btn-sm btn-success"> ADD <i class="fas fa-arrow-circle-right"></i></a><br/><br/>
            <a href="#" id="removeMember" onClick="removenewmembers();" class="disabled btn btn-sm btn-danger"><i class="fas fa-arrow-circle-left"></i> REMOVE </a>
           </td>
           <td>
            <select style="font-size:10px;" size="10" name="assigned" id="assigned" class="form-control form-control-sm">
         
                          <%
                          int aprcnt = 1;
                            iter = tcRole.getRoleMembership().entrySet().iterator();
                            while(iter.hasNext())
                            {
                              per = (Personnel) (((Map.Entry)iter.next()).getValue());
                              //Do NOT display esdnl addresses and invalid accounts. ONLY NLESD.
                              if(per.getEmailAddress().contains("nlesd.ca")) {
                              aprcnt++;
                          %>  
                         
                          <option value="<%=per.getPersonnelID()%>"><%=per.getFullName()%> [<%=per.getEmailAddress()%>]</option>
                         
                          <% }} %> 
                       
            </select>
           
           
           </td>
            </tr>
           </tbody>
           
           
           </table>
                        
                 
          </form>
	
	<div class="siteSubHeaderGreen">Currently Approved Rate Employees</div>
	
	When you add new members, the table below may need to be refreshed to display YTD values. 
	<div style="float:right;"><a href="#" class="btn btn-xs btn-info" onclick="loadingData();refreshApprovedDataTable();return false;"><i class="fas fa-sync-alt"></i> RELOAD</a></div>
	<br/><br/>
      <table id="claims-table" class="table table-condensed compact table-striped table-bordered claimsTable" style="font-size:11px;background-color:White;" width="100%">	
				<thead>
				<tr style="text-transform:uppercase;font-weight:bold;">  	
      		<td width="15%">EMPLOYEE</td>
      		<td width="10%">ID</td>
      		<td width="20%">EMAIL</td>
      		<td width="15%">SUPER</td>
      		<td width="20%">POSITION</td>      		
      		<td width="10%">YTD KM</td>
      		<td width="10%">YTD $</td>      		
     		</tr>
     		</thead>
     		<tbody>
     		<% 
               iter = tcRole.getRoleMembershipList().iterator();
               while(iter.hasNext()) 
                              {                             
                                per = (Personnel) iter.next();                             
                            %>  
                           <tr>    
                                  <td width="15%"><%=per.getFullName()%></td>                                  
                                  <td width="10%"><%=per.getPersonnelID()%></td>                                  
                                  <td width="20%"><a href="mailto:<%=per.getEmailAddress() %>?subject=Travel Claim Message"><%=per.getEmailAddress() %></a></td> 
                                  <td width="15%" style="text-transform:Capitalize;"><%=per.getSupervisor()!=null?per.getSupervisor().getFullNameReverse():"N/A"%></td>
                                  <td width="20%"><%=per.getPersonnelCategory().getPersonnelCategoryName() != null ? per.getPersonnelCategory().getPersonnelCategoryName() : "N/A" %></td>
                                   <td width="10%">
                                   <%
                                  if(per.getYearToDateKilometerUsage()<9000) { %>
                                	  <%=df.format(per.getYearToDateKilometerUsage())%>
                                 <%
                                 } else {
                                	  %>
                                	  <span style="color:Red;">
                                	  <%=df.format(per.getYearToDateKilometerUsage())%>
                                	  </span>
                                	  <%  }
                                  %>
                                   </td>
                                  <td width="10%"><%=dollar_f.format(per.getCurrentYearClaimTotal())%></td>           
                                                              
                            </tr>     
                            <%}%>
     		 </tbody>
      </table>
  
  	
  
  
  

<%if(request.getAttribute("msgERR")!=null){%>
	                      <script>
	                      $(".details_error_message").html("<%=(String)request.getAttribute("msgERR")%>").css("display","block").delay(3000).fadeOut();
	                      </script>
  <%}%>		
<%if(request.getAttribute("msgOK")!=null){%>
	                      <script>
	                      $(".details_success_message").html("<%=(String)request.getAttribute("msgOK")%>").css("display","block").delay(3000).fadeOut();
	                      </script>
  <%}%>	

 
                     <script>
                     $(document).ready(function(){   
                     $("#rrCount").html(<%=rrcnt%>);
                     $("#aprCount").html(<%=aprcnt%>);                    
         				var my_options = $("#assigned option");

                 		my_options.sort(function(a,b) {
                 		    if (a.text > b.text) return 1;
                 		    else if (a.text < b.text) return -1;
                 		    else return 0;
                 		});
                 		$("#assigned").empty().append(my_options);                 
       
                 	                		
                 		
                     });                    
                         
                     
                     $('#available').change(function () {         
                     
                    	 	if($('#available').val() !=null) { 
                    	 		$("#removeMember").addClass("disabled");
              					$("#addMember").removeClass("disabled");
              				} else {
              					$("#addMember").addClass("disabled");
              				}
                     });
                     
                     $('#assigned').change(function () {         
                         	if($('#assigned').val() !=null) { 
                        		$("#addMember").addClass("disabled");               			
                  				$("#removeMember").removeClass("disabled");
                  			} else {
                  				$("#removeMember").addClass("disabled");
                  			}
                      });
                     
                    
                     
                     </script>  
                            
  

