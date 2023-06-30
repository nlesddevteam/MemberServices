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
    <script>
  $(document).ready(function() {			
			
			$(".payrollTeacherTable").DataTable({ 					
				  "order": [[ 0, "asc" ]],			  
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
			                    columns: [ 0, 1, 2, 3]
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
			        
				  "lengthMenu": [[50, 100, 250, -1], [50, 100, 250, "All"]]							
			}); 
			
			});
		
		</script>
	
	
	  <style>
  	input {border: 1px solid silver;}
		.btn-group {float:left;}	
  
  </style>	
	</head>

	<body>
	<div class="row pageBottomSpace">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
	<div class="siteBodyTextBlack">
	<jsp:include page="menu.jsp" />
	<div class="siteHeaderGreen">View Employee Information</div>



					<table class="payrollTeacherTable table table-sm" style="width:100%;font-size:12px;">
							<thead class="thead-dark">
									<tr>
										<th>NAME</th>
										<th>PAY BEGIN DATE</th>
										<th>PAY END DATE</th>
										<th>CHECK DATE</th>
										<th>DETAILS</th>
										<th>HISTORY</th>
										<th>EMAIL</th>
									</tr>
							</thead>
							<tbody>
										<c:choose>
	                                  	<c:when test='${fn:length(employees) gt 0}'>
                                  		<c:forEach items='${employees}' var='g'>
                                  			<tr>
                                  				<td>${g.empName}</td>
		                                      	<td>${g.payBgDt}</td>
		                                     	<td>${g.payEndDt}</td>
		                                      	<td>${g.checkDt}</td>
		                                      	<td><a class="btn btn-xs btn-info" title="View Details" href="viewNLESDPayAdviceTestStub.html?id=${g.id}&emp=${g.empNumber}" target="_blank">VIEW</a></td>
		                                      
		                                      <c:choose>
		                                      <c:when test='${g.hisCount gt 0}'>
		                                      	<td><a class="btn btn-xs btn-info" title="View History" href="viewNLESDPayAdviceWorkHistory.html?id=${g.id}&emp=${g.empNumber}" target="_blank">VIEW</a></td>
		                                       </c:when>
												<c:otherwise>
												<td>N/A</td>
		                                       </c:otherwise>
												</c:choose>
												<td>												
												<a class="btn btn-xs btn-danger" href="#" title="Email Employee Pay Advice Information" onclick="OpenPopUp(${g.id},'${g.empNumber}');">EMAIL</a>
												</td>
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											</tr>
											
											<script>
											$(".msgERR").append("<b>ERROR:</b> No information found.").css("display","block");
											</script>
											
										</c:otherwise>
									</c:choose>
									</tbody>		
								</table>
					
			<br/><br/>
	
	
<!-- The Modal -->
<div class="modal" id="emailPayInfo">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Email Employee Pay Advice Information</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
      
      <b>Employee:</b> <span id="empname"></span><br/>
      <b>Email:</b> <span id="empemail"></span>
      <b>Pay Period:</b> <span id="payperiod"></span>
      
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
      
      <input type="hidden" id='hidPID'>
						<input type="hidden" id='hidEID'>
						<input type="hidden" id='hidEMAIL'>
      					<input class="btn btn-sm btn-primary" type="button" value="Email" onclick="sendinfo();"/>
						<input class="btn btn-sm btn-danger" type="button" value="Cancel" onclick="closewindow();"/>
     
      
      </div>

    </div>
  </div>
</div>	
	
			

	
</div>
</div>
</div>


    
  </body>

</html>					