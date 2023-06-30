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
    
      <style>
  	input {border: 1px solid silver;}
		.btn-group {float:left;}	
  
  </style>	
    
   <script>
	$(document).ready(function() {
		
		
		$(".payrollPeriodsTable").DataTable({ 					
			  "order": [[ 0, "desc" ]],			  
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
		                    columns: [ 0, 1, 2, 3, 4]
		                }
		            },
		        	{
		                extend: 'print',
		                //orientation: 'landscape',
		                footer:true,
		                messageTop: 'PayAdvice System',
		                messageBottom: null,
		                exportOptions: {
		                    columns: [ 0, 1, 2, 3, 4]
		                }
		            }
		        ],		        
		        
			  "lengthMenu": [[50, 100, 250, -1], [50, 100, 250, "All"]]							
		}); 
		
		});
  	
  	</script>	
	
	</head>

	<body>
	<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">View Pay Periods</div>
	
									
									
<table align="center" class="payrollPeriodsTable table table-small" style="font-size:12px;width:100%;">
									<thead class="thead-dark">
									<tr>
										<th>START</th>
										<th>END</th>
										<th>IMPORT STATUS</th>
										<th>STUB &amp; EMAIL STATUS</th>
										<th>PERIOD STATUS</th>
										<th>OPTIONS</th>
									</tr>
									</thead>
									<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(periods) gt 0}'>
                                  		<c:forEach items='${periods}' var='g'>
                                  			<tr>                                  			
                                  			  <td>${g.bgDate}</td>
		                                      <td>${g.endDate}</td>
		                                      <td>${g.importStatus}</td>
		                                      <td>${g.stubCreationStatus}</td>
		                                      <td>${g.closedStatus}</td>
		                                      <td>
		                                      <a title="View Details" class="btn btn-xs btn-primary" href='viewNLESDPayAdvicePayPeriodDetails.html?id=${g.payGroupId}'>VIEW</a>
		                                      </td>
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr>
											<td>N/A</td>
											<td>N/A</td>
											<td>N/A</td>
											<td>N/A</td>
											<td>N/A</td>											
											</tr>
											<script>
											$(".msgERR").append("<b>NOTE:</b> No pay periods found/listed.").css("display","block");
											</script>
										</c:otherwise>
									</c:choose>
								</tbody>	
								</table>
					

   </div></div></div> 
  </body>

</html>												
					
			