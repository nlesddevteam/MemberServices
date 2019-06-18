<%@ page language="java"
         session="true"
         import="com.awsd.security.*,
         		com.awsd.common.*,
                 com.nlesd.bcs.bean.*,
                 java.util.*,
                 java.io.*,
                 java.text.*,com.esdnl.util.*;"
         isThreadSafe="false"%>


         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  BussingContractorBean profile = (BussingContractorBean) session.getAttribute("CONTRACTOR");
%>

<c:set var="countsystemMessages" value="0" />
<c:set var="countemployeeMessages" value="0" />
<c:set var="countvehicleMessages" value="0" />
<html>

	<head>
		<title>NLESD - Busing Contractor System</title>					

		  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/jquery.maskedinput.min.js"></script>
    		<script src="includes/js/bcs.js"></script>
			<script type="text/javascript" src="includes/js/jquery.timepicker.js"></script>
  			<link rel="stylesheet" type="text/css" href="includes/css/jquery.timepicker.css" />
  			
  			
  			
		<script>
			
			jQuery(function(){
				  $(".img-swap").hover(
				          function(){this.src = this.src.replace("-off","-on");},
				          function(){this.src = this.src.replace("-on","-off");});
				 });
			
			
			</script>
			<script>
			$(document).ready(function(){    
        		//clear spinner on load
    			$('#loadingSpinner').css("display","none");
    			$("#display_error_message_top").css("display","none");
    		    $("#display_error_message_bottom").css("display","none");
    		    
        	});
			</script>
			<script>
   			$(document).ready(function () {
    		$('.menuBCSC').click(function () {
    		$("#loadingSpinner").css("display","inline").delay(2000).fadeOut();
    		});  
   			});
		</script> 
		
		<script>
   			$(document).ready(function () {
    		$('.menuBCS').click(function () {
    		$("#loadingSpinner").css("display","inline");
    		});  
   			});
		</script>
			
	</head>

  <body>
  <div class="mainContainer">
  	   	<div class="section group">	   		
	   		<div class="col full_block topper" align="center">
	   			<script src="includes/js/date.js"></script>	   		
			</div>			
			<div class="full_block center">
				<img src="includes/img/header-operator.png" alt="Student Transportation Management System for Operators and Contractors" width="100%" border="0"><br/>	
				<div align="left"><jsp:include page="includes/contractor_menu.jsp" /></div>				
			</div>
			<div class="col full_block content">
						
			
					<div class="bodyText">
					<span style="color:Grey;">Logged in as: <span style="text-transform:capitalize;"><%=profile.getFirstName()%> <%=profile.getLastName() %></span></span>
					
					<div style="float:right;padding-right:5px;color:Silver;text-align:right;">					
					<button type="button" style="margin:3px;" class="btn btn-xs btn-danger" onclick="window.location='contractorLogin.html';">SIGN OUT</button><br/> 					
					</div><p>
					
					<div class="alert alert-danger" id="display_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    				<div class="alert alert-success" id="display_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<div class="alert alert-warning" id="display_warning_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					<span class="BCSCompanyHeader"><%=profile.getCompany() %></span>
										
						<div id="printJob">
							<div id="pageContentBody" style="width:100%;">
							Welcome to your Student Transportation Management System. To start, review any messages below and/or navigate options to manage your employees, vehicles, and contracts via the menu above.						 
							  <br/>
							   
							   	<div class="BCSHeaderText">System Messages or Warnings <span id="sysCount"></span></div>
   									<div class="alert alert-success" id="sysmes_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    								<div class="alert alert-danger" id="sysmes_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 	
   									
   									 <table id="BCS-table-A" width="100%" class="BCSTable">
							  		<thead>
							  				<tr class="listHeader">
							  				<th width="50%" class="listdata" style="padding:2px;">Document</th>
							  				<th width="30%" class="listdata" style="padding:2px;">Type</th>
							  				<th width="10%" class="listdata" style="padding:2px;">Uploaded</th>
							  				<th width="10%" class="listdata" style="padding:2px;">Options</th>
							  				</tr>
							  		</thead>
							  		<tbody>									
						      			<c:if test="${fn:length(dwarnings) > 0}">
							      			<c:forEach items="${dwarnings}" var="rule">
							      			<c:set var="countsystemMessages" value="${countsystemMessages + 1}" />
							 					<tr style="border-bottom:1px solid silver;">
							      					<td class="field_content">	${rule.documentTitle}</td>
													<td class="field_content">${rule.typeString}</td>
													<td class="field_content">${rule.dateUploadedFormatted}</td>
													<td class="field_content">
													
													<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('../${rule.viewPath}');">View</button>		      					   					
			      					
													
													<a href='../${rule.viewPath}' class="menuBCS" target='_blank'>
													<img src="includes/img/viewsm-off.png" class="img-swap" title="View Document" border=0 style="padding-top:3px;padding-bottom:3px;"></a> &nbsp; 
													</td>
							      				</tr>
						        			</c:forEach>
	        							</c:if>
	        							
							  		</tbody>
							  		</table>
							  		
				<c:choose>
			      	<c:when test="${countsystemMessages >0 }">
			      		<script>
			      		$('#sysmes_error_message').html("There are <b>${countsystemMessages}</b> <span style='text-transform:lowercase;'>system messages</span> found.").css("display","block").delay(4000).fadeOut();
			      		$('#sysCount').html('(${countsystemMessages})');
			      		</script>
			      	</c:when>
			      	<c:otherwise>
			      		<script>$('#BCS-table-A').css("display","none");$('#sysmes_success_message').html("There are no <span style='text-transform:lowercase;'>system messages or warnings</span> at this time.").css("display","block");</script>
			      	</c:otherwise>
			    </c:choose>
						<br />							  								
							   <div class="BCSHeaderText">Employee Messages or Warnings <span id="empCount"></span></div>
							   		<div class="alert alert-danger" id="emp_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    								<div class="alert alert-success" id="emp_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
							   
   									 <table id="BCS-table-B" width="100%" class="BCSTable">
							  		<thead>
							  			<tr class="listHeader">
							  				<th width="15%" class="listdata" style="padding:2px;">Last Name</th>
							  				<th width="10%" class="listdata" style="padding:2px;">First Name</th>
							  				<th width="15%" class="listdata" style="padding:2px;">Position</th>
							  				<th width="40%" class="listdata" style="padding:2px;">Warning</th>
							  				<th width="5%" class="listdata" style="padding:2px;">Options</th>
							  			</tr>
							  		</thead>
							  		<tbody>
									
						      			<c:if test="${fn:length(ewarnings) > 0}">
							      			<c:forEach items="${ewarnings}" var="rule">
							      			<c:set var="countemployeeMessages" value="${countemployeeMessages + 1}" />
							 					<tr style="border-bottom:1px solid silver;">
							      					<td class="field_content">${rule.lastName}</td>
							      					<td class="field_content">${rule.firstName}</td>
							      					<td class="field_content">${rule.employeePositionText}</td>
							      					<td class="field_content">${rule.warningNotes}</td>
							      					<td align="right" class="field_content">
							      						<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('addNewEmployee.html?vid=${rule.id}');">View</button>
		      					    				<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialogemp('${rule.lastName},${rule.firstName}','${rule.id}');">Del</button>
			      					</td>
							      				</tr>
						        			</c:forEach>
	        							</c:if>
	        							
							  		</tbody>
							  		</table>
							  		
							  		<c:choose>
								      	<c:when test="${countemployeeMessages >0 }">
								      		<script>
								      		$('#emp_error_message').html("There are <b>${countemployeeMessages}</b> <span style='text-transform:lowercase;'>employee warning messages</span> found.").css("display","block").delay(4000).fadeOut();
								      		$('#empCount').html('(${countemployeeMessages})');
								      		</script>
								      	</c:when>
								      	<c:otherwise>
								      		<script>$('#BCS-table-B').css("display","none");$('#emp_success_message').html("There are no <span style='text-transform:lowercase;'>employee warning messages or warnings</span> at this time.").css("display","block");</script>
								      	</c:otherwise>
								    </c:choose>
							  		
							  		
							  		
							  		<br />							  		
							  		<div class="BCSHeaderText">Vehicle Messages or Warnings <span id="vehCount"></span></div>
							  		<div class="alert alert-danger" id="veh_error_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    								<div class="alert alert-success" id="veh_success_message" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
   									 <table id="BCS-table-C" width="100%" class="BCSTable">
							  		<thead>
							  			<tr class="listHeader">
							  				<th width="15%" class="listdata" style="padding:2px;">Serial Number</th>
							  				<th width="10%" class="listdata" style="padding:2px;">Plate Number</th>
							  				<th width="15%" class="listdata" style="padding:2px;">Year</th>
							  				<th width="40%" class="listdata" style="padding:2px;">Warning</th>
							  				<th width="5%" class="listdata" style="padding:2px;">Options</th>
							  			</tr>
							  		</thead>
							  		<tbody>
									<c:choose>
						      			<c:when test="${fn:length(vwarnings) > 0}">
							      			<c:forEach items="${vwarnings}" var="rule">
							      			<c:set var="countvehicleMessages" value="${countvehicleMessages + 1}" />
							 					<tr style="border-bottom:1px solid silver;">
							 					<td class="field_content">${rule.vSerialNumber}</td>
							      					<td class="field_content">${rule.vPlateNumber}</td>
							      					<td class="field_content">${rule.vYear}</td>
							      					<td class="field_content">${rule.warningNotes}</td>
							      					<td align="right" class="field_content">
							      						<button type="button" class="btn btn-xs btn-primary menuBCS" onclick="closeMenu();loadMainDivPage('addNewVehicle.html?vid=${rule.id}');">View</button>
		      					    				<button type="button" class="btn btn-xs btn-danger" onclick="opendeletedialog('${rule.vPlateNumber}','${rule.id}');">Del</button>
			      					
							      					</td>
							      				</tr>
						        			</c:forEach>
	        							</c:when>
	        							<c:otherwise>
	        								<tr><td colspan='5' style="color:Red;">No vehicle messages or warnings</td></tr>
	        							</c:otherwise>
	        						</c:choose>
	        						
	        						
	        						
							  		</tbody>
							  		</table>	
							  		
							  		<c:choose>
								      	<c:when test="${countemployeeMessages >0 }">
								      		<script>
								      		$('#veh_error_message').html("There are <b>${countvehicleMessages}</b> <span style='text-transform:lowercase;'>vehicle warning messages</span> found.").css("display","block").delay(4000).fadeOut();
								      		$('#vehCount').html('(${countvehicleMessages})');
								      		</script>
								      	</c:when>
								      	<c:otherwise>
								      		<script>$('#BCS-table-C').css("display","none");$('#veh_success_message').html("There are no <span style='text-transform:lowercase;'>vehicle warning messages or warnings</span> at this time.").css("display","block");</script>
								      	</c:otherwise>
								    </c:choose>
							  		
							  		
							  								  			
							</div>
							
							<div class="alert alert-danger" id="display_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>         
    						<div class="alert alert-success" id="display_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
							<div class="alert alert-warning" id="display_warning_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
							
							<br/>&nbsp;<br/>
							
							<div class="alert alert-warning no-print">	
							<b>NOTICE:</b> If you require assistance with the information and/or documentation required and/or entered into this busing system, please contact the Student Transportation division at <b>(709) 757-4664</b> or email <a href="mailto:transportation@nlesd.ca?subject=BCS System Support">transportation@nlesd.ca</a>.
							If you are having technical issues with functionality/display of the system itself, please email <a href="mailto:mssupport@nlesd.ca?subject=BCS System Technical Support">mssuport@nlesd.ca</a>.
							</div>
							
							<div class="alert alert-info no-print">						
						 		<div class="acrobatMessage"><a href="http://get.adobe.com/reader/"><img src="includes/img/adobereader.png" width="150" border="0"></a></div>							
	  							Documents on this site require Adobe Acrobat&reg; Reader. Adobe Acrobat Reader software is used for viewing Adobe&reg; PDF documents. 
								Click on the Get ADOBE Reader icon to download the latest Acrobat&reg; Reader for free.							
									
  					
  							</div>		
							
							
							
						</div>
					</div>
					<div class="section group">
						<div class="col full_block copyright">NLBusingApp BETA &copy; 2017 Newfoundland and Labrador English School District</div>
					</div>
			</div>
		</div>
	</div>
	
	
	 <div id="myModal" class="modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="maintitle"></h4>
                </div>
                <div class="modal-body">
                    <p class="text-warning" id="title1"></p>
                    <p class="text-warning" id="title2"></p>
 		    		<p class="text-warning" id="title3"></p>
		</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-xs btn-default" data-dismiss="modal" id="buttonleft"></button>
                    <button type="button" class="btn btn-xs btn-primary" data-dismiss="modal" id="buttonright"></button>
                </div>
            </div>
        </div>
    </div>
	
	
	
	
	
	
	<script>
			$(document).ready(function(){            	
    			
    		    $("#BCS-table-A tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table-A tr:odd").css("background-color", "#f2f2f2");
    		    $("#BCS-table-B tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table-B tr:odd").css("background-color", "#f2f2f2");
    		    $("#BCS-table-C tr:even").not(':first').css("background-color", "#FFFFFF");
    		    $("#BCS-table-C tr:odd").css("background-color", "#f2f2f2");
    		    
        	});
			</script>
	
	
<!-- ENABLE PRINT FORMATTING -->
	<script src="includes/js/jQuery.print.js"></script>	
  </body>

</html>	