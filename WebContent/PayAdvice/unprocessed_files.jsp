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
	
			function checkfiles()
			{
				var checkboxes = [];
				var strids="";
				$('input[type=checkbox]').each(function () {
				    if (this.checked) {
				    	if(strids.length == 0)
				    	{
				    		strids = strids + $(this).attr("id");
				    		
				    	}else{
				    		strids = strids + "," + $(this).attr("id");
				    	}
				        
				    }
				});
				$("#strfileids").val(strids);
				
			}
			
			$(document).ready(function() {
				
			
			$(".payrollFilesTable").DataTable({ 					
				  "order": [[ 1, "asc" ]],				   
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
			                    columns: [ 0, 1, 2, 3, 4, 5 ]
			                }
			            },
			        	{
			                extend: 'print',
			                //orientation: 'landscape',
			                footer:true,
			                messageTop: 'PayAdvice System',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2, 3, 4, 5]
			                }
			            }
			        ],
			        
			        "columnDefs": [
						 {
				             "targets": [0,6],			               
				                "sortable": false,
				                "visible": true,
				            },
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
<div class="siteHeaderGreen">Unprocessed Payroll Files</div>


	
	
	
		<form onsubmit="checkfiles();" action="processNLESDPayrollDocument.html">	
		<input type='hidden' id='strfileids' name='strfileids'>
		
									
    				
                				
                				<table class="payrollFilesTable table table-sm table-border table-striped" style="font-size:11px;">	
                				<thead class="thead-dark">							
									<tr>
										<th>CHK</th>
										<th>FILE NAME</th>
										<th>UPLOADED</th>
										<th>UPLOADED BY</th>
										<th>NOTES</th>
										<th>TYPE</th>
										<th>OPTIONS</th>
									</tr>
								</thead>	
								<tbody>
								<c:choose>
	                                  	<c:when test='${fn:length(documents) gt 0}'>
                                  		<c:forEach items='${documents}' var='g'>
                                  			<tr>
                                  			<c:choose>
                                  			<c:when test="${g.documentType == 'Payroll Data'}">
                                  				<td><input type='checkbox' id='${g.documentId}'></td>
                                  			</c:when>
                                  			<c:otherwise>
                                  				<td></td>
        									</c:otherwise>
        									</c:choose>
		                                      <td>${g.originalFileName}</td>
		                                      <td>${g.createdDateFormatted}</td>
		                                      <td><span style=" text-transform: capitalize;">${g.uploadedBy}</span></td>
		                                      <td>${g.notes}</td>
		                                      <td>${g.documentType}</td>
		                                      
		                                    <c:choose>
                                  			<c:when test="${g.documentType == 'Payroll Data'}">
                                  			<td>
		                                      <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE these documents?');loadingData();" href='deleteNLESDPayrollDocument.html?fn=${g.filename}&fid=${g.documentId}'>DEL</a>
		                                      </td>
		                                    </c:when>
                                  			<c:otherwise>
                                  				<td></td>
        									</c:otherwise>
        									</c:choose>
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
											<td>N/A</td>
											<td>N/A</td>
											</tr>
											<script>
											$(".msgERR").append("<b>NOTE:</b> No unprocessed documents found.").css("display","block");
											</script>
										</c:otherwise>
									</c:choose>
								</tbody>
								</table>
								<p>
								<div align="center" class="no-print"><input type='Submit' value='Process Files' class="btn btn-sm btn-danger"></div>
		
		</form>
				
	
  </div>
  </div>
  </div>   
  
  </body>

</html>				