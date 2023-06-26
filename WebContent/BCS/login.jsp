<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>
	<head>
		<title>NLESD - Busing Operator System Login</title>						

		  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
		    <meta charset="utf-8">
		    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">    		
    		<link href="includes/css/jquery-ui.css" rel="stylesheet" type="text/css"> 
    		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">  	
   			<link href="includes/css/bcs.css" rel="stylesheet" type="text/css">
   			<link href="includes/css/bootstrap.min.css.map" rel="stylesheet" type="text/css">
   			<!-- For mini-icons in menu -->
   			<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">			
    		<script src="includes/js/jquery.min.js"></script>		
    		<script src="includes/js/jquery-ui.js"></script>
    		<script src="includes/js/bootstrap.min.js"></script>
    		<script src="includes/js/jquery.maskedinput.min.js"></script>
    		<script src="includes/js/bcs.js"></script>
    		
    		<script>
			$(document).ready(function(){    
				$('.menuBCS').click(function () {
		    		$("#loadingSpinner").css("display","inline");
		    	});
				$("#loadingSpinner").css("display","none");
				
        	});
			</script>
    		
    		
				<script>
				
				jQuery(function(){
					  $(".img-swap").hover(
					          function(){this.src = this.src.replace("-off","-on");},
					          function(){this.src = this.src.replace("-on","-off");});
					 });
				


				
				
				</script>
				
	</head>

  <body>

 <div class="mainContainer">
  	   	<div class="section group">	   		
	   		<div class="col full_block topper" align="center">
	   			   		
			</div>			
			<div class="full_block center">
				<img src="includes/img/header-operator.png" alt="Student Transportation Management System for Operators" width="100%" border="0"><br/>	
				<div align="left" style="background-color:#ffd333;width:100%;height:5px;"></div>				
			</div>
			<div class="col full_block content">
					<div class="bodyText">
					<div id="loadingSpinner" style="display:none;"><div id="spinner"><img src="includes/img/animated-bus.gif" width="200" border=0><br/>Transporting data, please wait...</div></div>
					
					 <%if((String)request.getParameter("msg") != null) {%>
						<div class="alert alert-success" id="body_success_message_top" style="margin-top:10px;margin-bottom:10px;padding:5px;"><%=(String)request.getParameter("msg")%></div> 
					 <%}else{ %>
					 	<div class="alert alert-success" id="body_success_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<%} %>       
    				<div class="alert alert-warning" id="body_approval_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					<div class="alert alert-danger" id="body_error_message_top" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
										
						<div id="printJob">
							<div id="pageContentBody" style="width:100%;">
							<div class="alert alert-success" id="body_success_message_top" style="margin-top:10px;margin-bottom:10px;padding:5px;">
								Site is Currently Down for End of Year Maintenance. The District will advise all Contractors by email when the system is back up and running
							</div>
							<div style="margin-top:10px;float:right;width:25%;min-width:300px;padding-left:10px;padding-right:10px;border:1px dotted #007F01;margin-bottom:5px;background-color:#FFFFE0;display:none;">
    <form class="form-signin" id="form-signin" action="contractorSubmitLogin.html" method='POST'>       
      	<div class="BCSHeaderText">Operator Login</div>
      	<input type="text" class="form-control" name="username" id="username" placeholder="Enter your Email Address" required autofocus />      	
      	<div class="alert alert-danger" id="errormessage" style="display:none;margin-top:5px;margin-bottom:5px;padding:5px;"></div>      	
      	<br/><input type="password" class="form-control" name="password" id="password" placeholder="Enter your Password" required/>      
      	<div class="alert alert-danger" id="errmessage" style="display:none;margin-top:5px;margin-bottom:5px;padding:5px;"></div>
      	<br/><div align="center"><button class="btn btn-xs btn-success btn-block type="submit" style="width:200px;">Click to Login</button></div>
      	<div align="center" style="font-size:10px;padding-top:5px;">Did you forget your password?<br/>    	
      	 <button type="button" style="margin:3px;" class="btn btn-xs btn-danger" onclick="openReset();">Retrieve Your Password</button>      	
      	<br/>
      	
      	<div id="hidreg" style="display:none;">
      	Don't have an account? Click below to register as a NLESD Busing Operator.<br/>
      	<button type="button" style="margin:3px;" class="btn btn-xs btn-primary" onclick="window.location='register.html';">Operator Registration</button><br/>      
      	</div>
      <button type="button" style="margin:3px;" class="btn btn-xs btn-warning" onclick="window.location='http://www.nlesd.ca';">Exit to NLESD</button>
      </div>
      	<div class="alert alert-success" role="alert" id="success_message" style="display:none;"><i class="glyphicon glyphicon-thumbs-up"></i> <span id="successspan"></span></div>                   	      
    	
       	<% if (request.getAttribute("msg") != null) { %>
       	<script>
       		$("#errmessage").html("ERROR: <%=(String) request.getAttribute("msg")%>").css("display","block").delay(3000).fadeOut();
       	</script>	
       	<% } %>
    </form>
  </div>
	<br/>					
					
   Welcome to the NLESD Busing Operator Management System. 
							
							<br/><br/>The Student Transportation Busing Operator Documentation System is designed to make it easier for the Newfoundland and Labrador English School District and Student Transportation Contractors to manage:
							<br/><br/><i>
							<ul>
							<li>the collection and updating of required Contractor documentation;
							<li>the driver and vehicle assignments to specific routes; and
							<li>communication relating to documentation approvals/expiries and group MEMOs/messages to Contractors 
							</ul></i>
							It also provides clarity for both parties regarding documentation that is on file as well as each document's status ("Approved" or "Not Approved" including why it is not approved). This system may also be a useful tool for a Contractor's operation for the tracking of student transportation drivers and student transportation fleet.
						
							<br/><br/>As a contract is awarded to a Contractor, the District will add it into the system including its associated routes. The Contractor is responsible to keep their Contractor information up-to-date, listing of drivers and fleet up-to-date, upload all required documentation under the corresponding driver or vehicle in the system and ensure each route is assigned a driver and a vehicle. The system will send automatic notifications to Contractors for upcoming document expirations as well as District approval of drivers and vehicles.
					
                            <br/><br/>To get started, please login at right.

  
  <!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="modaltitle"></span></h4>
      </div>
      <div class="modal-body" id="divstep1">
        <p><span id="modaltext"></span></p>
        <p><input type="text" id="txtanswer" name="txtanswer" style="width: 60%";></p>
      </div>
      <div class="modal-body" id="divstep2" style="display:none;">
        <p><span id="modaltext">New Password</span></p>
        <p><input type="text" id="txtnpassword" name="txtnpassword" style="width: 60%";></p>
        <p><span id="modaltext">Confirm New Password</span></p>
        <p><input type="text" id="txtcnpassword" name="txtcnpassword" style="width: 60%";></p>
      </div>
      <div class="modal-footer" id="modalbuttons">
      	<button type="button" class="btn btn-default" onclick="confirmSecurity();">Ok</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <input type="hidden" id="email">
        <input type="hidden" id="question">
        <input type="hidden" id="answer">
        <input type="hidden" id="cid">
        <input type="hidden" id="cstep">
      </div>
      <div  class="alert alert-success" role="alert" id="success_message_m" style="display:none;"><i class="glyphicon glyphicon-thumbs-up"></i> Password has been changed.  Click Close button and then login with new password.</div>                   	      
	<div class="alert alert-danger" role="alert" id="error_message_m" style="display:none;"><i class="glyphicon glyphicon-thumbs-down"></i> <span id="errorspanm"></span></div> 
  
    </div>
	</div>
</div>





</div>

					<div class="alert alert-success" id="body_success_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div> 
					<div class="alert alert-warning" id="body_approval_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
					<div class="alert alert-danger" id="body_error_message_bottom" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>


						<br/>&nbsp;<br/> 	
							
							<div style="clear:both;"></div>
						<div class="alert alert-warning" style="display:none;"></div>
						
						<div class="alert alert-info no-print">						
						 		<div class="acrobatMessage"><a href="http://get.adobe.com/reader/"><img src="includes/img/adobereader.png" width="150" border="0"></a></div>							
	  							Documents on this site require Adobe Acrobat&reg; Reader. Adobe Acrobat Reader software is used for viewing Adobe&reg; PDF documents. 
								Click on the Get ADOBE Reader icon to download the latest Acrobat&reg; Reader for free.							
									
  					
  					</div>			
							
						</div>
					</div>
					<div class="section group">
						<div class="col full_block copyright">NLBusingApp 1.0 &copy; 2017 Newfoundland and Labrador English School District</div>
					</div>
			</div>
		</div>
	</div>

<script>
$('#form-signin').submit(function() {
	
	$("#loadingSpinner").css("display","inline");
});

</script>

<script> 
function iniFrame() { 
	var elementExists = document.getElementById("butsignout");
    if ( typeof(elementExists) != 'undefined' && elementExists != null) 
    { 
    	// The page is in an iFrames 
    	window.location = "https://localhost/MemberServices/BCS/contractorLogin.html";
        //alert(window.location.pathname);
    }  
    
} 
  
// Calling iniFrame function 
iniFrame(); 
</script> 


  </body>
</html>