<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         com.esdnl.payadvice.bean.*,com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="PAY-ADVICE-ADMIN" />
<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Teacher Pay Advice Manager</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
			
		<script>
		
		$(document).ready(function() {
			setOptions(); // on load
		    $('#searchby').change(setOptions); // on change
		    function setOptions() {       
		        switch ($("#searchby").val()) {
		            case "SELECT" :
		                $("#divtext").hide();
		                $("#divselect").hide();
		                break;
		            case "SIN":
		                $("#divselect").hide();
		                $("#divtext").show();
		                break;
		            case "NAME":
		                $("#divtext").show();
		                $("#divselect").hide();
		                break;
		            case "SCHOOL":
		                $("#divselect").show();
		                $("#divtext").hide();
		                break;
		            }
		    } 
		});
		

			function search()
			{
				cleartable();
				$(".payrollSearchTable").DataTable().clear().destroy();
				ajaxRequestInfo();
								
			}
			function cleartable()
			{
				$('#showlists td').parent().remove();
			}
			function ajaxRequestInfo()
			{
				var searchby = $.trim($('#searchby').val());
				var searchtext = $.trim($('#txtsearch').val());
				var searchschool = $.trim($('#school').val());
				
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "searchNLESDPayAdvice.html",
			 			
			 				data: {
			 					sby: searchby,
			 					stx: searchtext,
			 					ssc: searchschool
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					var newrow="";
			 					$(xml).find('EMPLOYEE').each(function(){
			 							
			 							if($(this).find("MESSAGE").text() == "LISTFOUND")
			 								{
			 								
			 								
												newrow +="<tr>";
			                                    newrow += "<td>" + $(this).find("EMPNAME").text() + "</td>";
			                                    newrow += "<td>" + $(this).find("SCHOOL").text() + "</td>";
			                                    newrow += "<td>" + $(this).find("SIN").text() + "</td>";
			                                    newrow += "<td>" + $(this).find("ID").text() +"</td>";
												newrow += "<td align='center'>";										
												newrow += "<a title='View Employee' class='btn btn-xs btn-primary' "
												newrow += "href='viewNLESDPayAdviceTeacherListAdmin.html?empnumber=" ;
												newrow +=  $(this).find("ID").text() + "'>VIEW</a>";																						
												newrow += "<a href='#' title='Reset Password' class='btn btn-xs btn-danger' ";
												newrow += " onclick='updatePassword(\"" + $(this).find("ID").text() + "\");'>RESET PWD</a>";																						
												newrow += "<a href='#' title='Resend Password' class='btn btn-xs btn-warning' ";
												newrow += " onclick='resendPassword(\"" + $(this).find("ID").text() + "\");'>RESEND PWD</a></td></tr>";
												
												//$('table#showlists tr:last').after(newrow);
												i=i+1;
												isvalid=true;
												
			 								}else{			 									
			 									 $(".msgERR").css("display","block").append($(this).find("MESSAGE").text()); 
			 								}
			 							
									});
			 					
			 					$(".payrollSearchTable tbody").append(newrow);
			 					
			 					$(".payrollSearchTable").DataTable({ 					
									  "order": [[ 0, "asc" ]],
									   "responsive": true,
									  dom: 'Blfrtip',
								        buttons: [			        	
								        	//'colvis',
								        	//'copy', 
								        	//'csv', 
								        	'excel', 
								        	{
								                extend: 'pdfHtml5',
								                footer:true,
								                //orientation: 'landscape',
								                messageTop: 'PayAdvice System',
								                messageBottom: null,
								                exportOptions: {
								                    columns: [ 0, 1, 2, 3 ]
								                }
								            },
								        	{
								                extend: 'print',
								                //orientation: 'landscape',
								                footer:true,
								                messageTop: 'PayAdvice System',
								                messageBottom: null,
								                exportOptions: {
								                    columns: [ 0, 1, 2, 3]
								                }
								            }
								        ],
								        
								        "columnDefs": [
											 {
									             "targets": [4],			               
									                "sortable": false,
									                "visible": true,
									            },
									        ],
									  "lengthMenu": [[50, 100, 250, -1], [50, 100, 250, "All"]]							
								}); 
			 					
								},
			 				  error: function(xhr, textStatus, error){
			 					 $(".msgERR").css("display","block").append(xhr.statusText); 
			 					 $(".msgERR").css("display","block").append(textStatus); 
			 					 $(".msgERR").css("display","block").append(error); 
			 				  },
			 				dataType: "text",
			 				async: false
			 			}
			 		);
				return isvalid;
				
				}
			function updatePassword(testing)
			{
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "resetEmployeePassword.html",
			 			
			 				data: {
			 					pid: testing
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('EMPLOYEE').each(function(){
			 							
			 							if($(this).find("MESSAGE").text() == "SUCCESS")
			 								{
			 								$(".msgOK").css("display","block").append("SUCCESS: Password has been reset and email sent."); 			 									
												isvalid=true;
				                   				
			 								}else{
			 									$(".msgERR").css("display","block").append($(this).find("MESSAGE").text()); 
			 								}
									});
								},
			 				  error: function(xhr, textStatus, error){
			 					 $(".msgERR").css("display","block").append(xhr.statusText); 
			 					 $(".msgERR").css("display","block").append(textStatus); 
			 					 $(".msgERR").css("display","block").append(error); 
			 				  },
			 				dataType: "text",
			 				async: false
			 			}
			 		);
				return isvalid;

			}
			function resendPassword(testing)
			{
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "resendEmployeePasswordAdmin.html",
			 			
			 				data: {
			 					pid: testing
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('EMPLOYEE').each(function(){
			 							
			 							if($(this).find("MESSAGE").text() == "SUCCESS")
			 								{	 								
			 									
			 									$(".msgOK").css("display","block").append("SUCCESS: Password has been resent."); 
												isvalid=true;
				                   				
			 								}else{			 									
			 									$(".msgERR").css("display","block").append($(this).find("MESSAGE").text()); 
			 								}
									});
								},
			 				  error: function(xhr, textStatus, error){
			 					 $(".msgERR").css("display","block").append(xhr.statusText); 
			 					 $(".msgERR").css("display","block").append(textStatus); 
			 					 $(".msgERR").css("display","block").append(error); 			 				     
			 				  },
			 				dataType: "text",
			 				async: false
			 			}
			 		);
				return isvalid;

			}			
</script>
		
	 <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	
	
	</head>

	<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">Search Employees</div>
		
									
Search the PayAdvice system by Employee name, school, or SIN.									
									
<hr>					
									
<div class="row">
<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"> 
<b>SEARCH BY:</b><br/>				 
										<select id="searchby" class="form-control" required>
										<option value="SELECT">Please Select Search By</option>
										<option value="NAME">Employee Name</option>
										<option value="SCHOOL">School</option>
										<option value="SIN">SIN</option>
										</select>
					
</div>	
<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">								
		<div id="divtext" style="display:none">
		<b>FOR:</b><br/> 
		<input type="text" id="txtsearch" class="form-control" placeholder="Enter Name, partial Name, or SIN"><br/>
		</div>									
		<div id="divselect"  style="display:none">
		<b>SELECT SCHOOL:</b><br/>
		<select id="school" class="form-control">
		<option value="SELECT">*** Select School ***</option>
		<c:forEach var="test" items="${list}" >
		<option value="<c:out value='${test}'/>"><c:out value="${test}"/></option>
		</c:forEach>
		</select>
		</div>
</div>	
<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
&nbsp;<br/>									
<input type="button" value="SEARCH" class="btn btn-sm btn-primary" onclick="search()">
</div>
</div>
<hr>
<div class="row">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">						
					<br>
					<table align="center" id="showlists" class="payrollSearchTable table table-sm responsive table-striped" style="font-size:12px;width:100%;">
					<thead class="thead-dark">
					<tr>
					<th>EMPLOYEE</th>
					<th>SCHOOL</th>
					<th>SIN</th>
					<th>PAYROLL ID</th>
					<th>OPTIONS</th>
					</tr>
					</thead>
					<tbody>
					
					</tbody>
					</table>
</div>					
</div> 
</div>
</div>		
</div>
    
  </body>

</html>						
