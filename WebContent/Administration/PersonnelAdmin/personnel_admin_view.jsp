<%@ page language="java"
        session="true"
        import="java.util.*,
        com.awsd.security.*,
        com.awsd.school.*,
        com.awsd.personnel.*,
        com.awsd.personnel.profile.*,
         com.awsd.common.*,
                com.esdnl.sds.*,
                com.esdnl.util.*, 
                java.time.format.*,                
                java.time.*,                            
                java.text.*"%> 
   
    
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>


<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
 	ArrayList<Personnel> emps  = PersonnelDB.getDistrictPersonnelArray(); 	
 	int totalCnt=0; 
 	DateFormat formatter; 
 	Date date; 
 		
%>

<html>
  <head>
    <title>MemberServices Administration</title>
   
   <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".membershipTable").dataTable({
				"order" : [[1,"asc"]],
				  "lengthMenu": [[25,50,100, 250, 500, 1000, -1], [25,50,100, 250, 500, 1000, "All"]],	
				responsive: true,
				dom: 'Blfrtip',
		        buttons: [			        	
		        	//'colvis',
		        	{
		        	
	                extend: 'print',
	                title: '<div align="center"><img src="/MemberServices/Administration/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
	                messageTop: '<div align="center" style="font-size:18pt;">MemberServices Personnel List</div>',
	                messageBottom: '<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
	                	 exportOptions: {
	                			                		 
	                         columns: [ 0,1,2,3,4,5],
	                       
	                     }
	            },
	            { 
	       		 extend: 'excel',	
	       		 exportOptions: {
	           		        		 

	                    columns: [ 0,1,2,3,4,5],
	                 },
	       },
	       { 
	     		 extend: 'csv',	
	     		 exportOptions: {
	         		            		 

	                    columns: [ 0,1,2,3,4,5],
	               },
	     },
	         
		        ],	
				
								
				 "columnDefs": [
					 {
			                "targets": [6],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
			});			
			
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
   

   
  </head>

<body>

<div class="siteHeaderGreen">Personnel Administration Summary</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Members Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage">  

This page displays all current NLESD personnel who have valid accounts in MemberServices. Older accounts prior to 2014 are not displayed.
You can update a persons MS Profile for school, permissions category, and name/email change. You can also login as the user to check for administrative purposes. 
There is also an option to delete a user if the account is empty and not in use.

<br/><br/>
 <div align="center"><a onclick="loadingData();" class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a></div>
<br/><br/>
<b>Total Members:</b> <span id="membersTotal"></span>

 
<br/><br/>
<table class="membershipTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th width="5%">ID</th>
					<th width="15%">NAME</th>
					<th width="15%">EMAIL (LOGIN)</th>
					<th width="15%">LOCATION</th>
					<th width="15%">CATEGORY</th>	
					<th width="15%">LAST LOGIN</th>					
					<th width="15%">OPTIONS</th>					
				</tr>
				</thead>
				<tbody>
		<% for(Personnel tmp : emps) {	
			totalCnt++;	
         formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         date = (Date)formatter.parse(tmp.getLastLogin()!=null?tmp.getLastLogin():"2000-01-01 12:00:00");         
         %>
         <c:set var="test1" value="<%=date %>"/>
        			 <tr>                        
         			<td width="5%"><%=tmp.getPersonnelID()%></td>            
					<td width="15%"><%=tmp.getFullName()%></td>
					<td width="15%"><%=tmp.getUserName()%></td>
					<td width="15%"><%=tmp.getSchoolName() == null ? "N/A": tmp.getSchoolName()%></td>
					<td width="15%"><%=tmp.getPersonnelCategory().getPersonnelCategoryName()%></td>	
					<td width="15%"><fmt:formatDate value="${test1}" pattern="yyyy/MM/dd @ h:mm a" /></td>							
					<td width="15%">
						  	<a class="btn btn-xs btn-primary" href="personnelAdminChange.html?pid=<%=tmp.getPersonnelID()%>" title="Change Profile"><i class="fas fa-user-alt"></i> PROFILE</a>
                            <a class="btn btn-xs btn-warning" onclick="top.document.location.href='../../loginAs.html?pid=<%=tmp.getPersonnelID()%>';" href='#'><i class="fas fa-sign-in-alt"></i> LOGIN</a>
                         	<a class="btn btn-xs btn-danger" onclick="return confirm('NOTICE: Are you sure you wish to remove this user? All data will be lost!')" href="personnelDelete.html?pid=<%=tmp.getPersonnelID()%>" title="Delete User"><i class="far fa-trash-alt"></i> DEL</a>
                         
					</td>
				 </tr>
         <% }%>
				</tbody>
				</table>
				
				<br/><br/>
				   <div align="center"><a onclick="loadingData();" class="btn btn-sm btn-danger" href="../index.jsp">Back to Administration</a></div>
				
</div> 
  
 
  
        
        <script>
        $("#membersTotal").html(<%=totalCnt%>);          
        </script>
        
  </body>
</html>