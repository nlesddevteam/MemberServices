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
<esd:SecurityCheck permissions="PAY-ADVICE-NORMAL" />
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
		function openHistorical()
		{
			var pp = $("#payperiod").val();
			var en = $("#empnumber").val();
			var choice = $("#viewtype").val();
			if(pp > 0)
			{
			if(choice == "vd")
			{
				var url="viewNLESDPayAdviceTestStub.html?id=" + pp + "&emp=" + en;
			}else{
				var url="viewNLESDPayAdviceWorkHistory.html?id=" + pp + "&emp=" + en;
			}
			window.open(url,"_blank");
			}else{
				$(".msgERR").css("display","block").append("Please Select Pay Period");				
			}
		}
		function resendPassword(testing)
		{
			var isvalid=false;
			$.ajax(
		 			{
		 				type: "POST",  
		 				url: "resendEmployeePasswordTeacher.html",
		 			
		 				data: {
		 					pid: testing
		 				}, 
		 				success: function(xml){
		 					var i=1;
		 					$(xml).find('EMPLOYEE').each(function(){
		 							
		 							if($(this).find("MESSAGE").text() == "SUCCESS")
		 								{
		 								$(".msgOK").css("display","block").append("Password has been resent");		 									
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
		
		
		$(document).ready(function() {
			
			
			$(".payrollTeacherTable").DataTable({ 					
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
			                    columns: [ 0, 1, 2]
			                }
			            },
			        	{
			                extend: 'print',
			                //orientation: 'landscape',
			                footer:true,
			                messageTop: 'PayAdvice System',
			                messageBottom: null,
			                exportOptions: {
			                    columns: [ 0, 1, 2]
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
<p><div align="center" style="padding:10px;" class="no-print">


		<a class="btn btn-sm btn-info" href="#passChangeModal" style="margin-top:5px;" title="Change My Password" onclick="OpenPopUpPassword('${empnum}');">CHANGE PASSWORD</a>
		<a href='#' class="btn btn-sm btn-warning" style="margin-top:5px;" title="Resend My Password" onclick="resendPassword('${empnum}');">RESEND PASSWORD</a>	
		<a href="#" class="btn btn-dark btn-sm" style="margin-top:5px;" role="button"  title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img src=/includes/img/nlesd-colorlogo.png></div><br/><br/>'});">PRINT PAGE</a>
        <a class="btn btn-sm btn-danger" style="margin-top:5px;" href='/MemberServices/memberServices.html' title="Exit to StaffRoom" onclick="loadingData();">EXIT TO STAFFROOM</a>
		</div>
		
<div class="siteHeaderGreen">View My Pay Advices</div>
	

	
	
<table class="payrollTeacherTable table table-sm" style="width:100%;font-size:12px;" >
									<thead class="thead-dark">
									<tr>
										<th class="ppbegint">Pay Begin Date</th>
										<th class="ppendt">Pay End Date</th>
										<th class="ppcheckt">Check Date</th>
										<th class="ppdetail">Details<input type="hidden" id="empnumber" value="${empnum}"></th>
										<th class="pphistory">History</th>
									</tr>
									</thead>
									<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(paygroups) gt 0}'>
                                  		<c:forEach items='${paygroups}' var='g' end='5'>
                                  			<tr class='datalist'>
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
											$(".msgERR").append("<b>ERROR:</b> No pay periods found. Please contact PayRoll.").css("display","block");
											</script>
										</c:otherwise>
									</c:choose>
									</tbody>
									 
									</table>
									<p>
									
		<hr>
		
		<div class="siteSubHeaderGreen">View Other Pay Period(s)</div>					
			<div class="row">
<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"> 	
		<select id="payperiod" class="form-control">
			<option value='-1'>Please Select</option>
			<c:choose>
				<c:when test='${fn:length(paygroups) gt 0}'>
                	<c:forEach items='${paygroups}' var='g'>
                		<option value="${g.id}">${g.payBgDt}-${g.payEndDt}</option>
					</c:forEach>
                </c:when>
            </c:choose>
			</select>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"> 		
			<select id="viewtype" class="form-control">
			<option value='-1'>Please Select</option>
			<option value="vd">Details</option>
			<option value="vh">History</option>
			</select>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4"> 		
			<input class="btn btn-sm btn-primary" type="button" value="VIEW" onClick="return openHistorical();" />
			
</div>
</div>				
	<br/><br/>
		<div class="alert alert-warning"><b>NOTE:</b> If you have issues with your pay information listed here, please contact the <a href="https://forms.gle/nm8xb5iLthGxg4UF8" target="_blank">PayRoll HelpDesk</a>.</div>		




<!-- The Modal -->
<div class="modal" id="passChangeModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Change My Password</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
      <form class="was-validated">
        Current Password:
        <input type="text" required id="current_password"  name="current_password" class="form-control"><br/>
        New Password:
		<input type="text" required id="new_password"  name="new_password" class="form-control" ><br/>
        Confirm New Password:
		<input type="text" required id="confirm_password"  name="confirm_password" class="form-control" >
        </form>
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
      <input class="btn btn-sm btn-primary" type="button" value="Change Password" onclick="updatepassword();"/>
	  <input class="btn btn-sm btn-danger" type="button" value="Cancel" data-dismiss="modal"/>
	  <input type="hidden" id="hidPID" name="hidPID">
	  <input type="hidden" id="hidPASSWORD" name="hidPASSWORD">
      
      </div>

    </div>
  </div>
</div>

</div>
</div></div>
    
  </body>

</html>				