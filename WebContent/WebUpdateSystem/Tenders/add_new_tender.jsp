<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>   

<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = (User) session.getAttribute("usr");
%>
<esd:SecurityCheck />
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    
     		
     		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
     		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
   			<link href="../includes/css/ms.css" rel="stylesheet">
   			<link rel="stylesheet" href="../fancybox/jquery.fancybox.css?v=2.1.5"/>
   			<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
   			<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
   			
   				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   				<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
				<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>		
    			<script src="../fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
    			<script src="../fancybox/jquery.fancybox.js?v=2.1.5"></script>
		 		<script src="../fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
				<script src="../fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
				<script src="../fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
				<script src="js/changepopup.js"></script>	
		
	
	<script>
    $(document).ready(function(){
    	
  	
    	
   	     	
    	
    	
    	
    		    $( "#closing_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		    
    		    $( "#awarded_date" ).datepicker({
        		      changeMonth: true,//this option for allowing user to select month
        		      changeYear: true, //this option for allowing user to select from year range
        		      dateFormat: "dd/mm/yy"
        		    });
    		  }

    		);

	</script>

	
	</head>

  <body>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
								<div class="bodyText">			
				
				 
				
				
				<div class="pageTitleHeader siteHeaders">Add Tender</div>
                      <div class="pageBody">
		Please enter the Tender information below. 

			<%if(request.getAttribute("msg") != null){%>
                      
                        <p>                       
                       <div class="alert alert-warning alert-dismissible" style="text-align:center;"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>*** <%=(String)request.getAttribute("msg")%> ***</div>
                        <p>  
                        <div align="center" style="margin-bottom:10px;">  
                        
                        <esd:SecurityAccessRequired permissions="TENDER-ADMIN">
					 	<a class="btn btn-sm btn-success" style="color:white;margin-top:5px;" role="button" href="addNewTender.html">Add another Tender</a> &nbsp; 
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">  
                      	<a class="btn btn-sm btn-primary" style="color:white;margin-top:5px;" role="button" href="viewTenders.html">Current Tenders</a> &nbsp;
                      	<a class="btn btn-sm btn-danger" style="color:white;margin-top:5px;" role="button" href="../../navigate.jsp">Exit to MS</a>
                    </esd:SecurityAccessRequired>
                    <esd:SecurityAccessRequired roles="ADMINISTRATOR">
                       &nbsp; <a class="btn btn-sm btn-warning" style="color:white;margin-top:5px;" role="button" href="../index.jsp">Back to Main Menu</a>
                     </esd:SecurityAccessRequired>
                        
                        
                               
                      
                      
                      </div>
                    <%} else {%>


    <form id="pol_cat_frm" name="TenderForm" action="addNewTender.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">         
     
     <p>     
      
      	<div class="form-group">
      		
				  <label for="tender_number">Number:</label>
				  <input type="text" class="form-control"  id="tender_number"  name="tender_number" placeholder="eg: 18-000" maxlength="15">
			
		</div>
      
<p>
     
     	<div class="form-group">
      		
				  <label for="region">Select Region:</label>
				  <select class="form-control" id="region" name="region">
				  
				  			<c:forEach var="item" items="${regions}" >
				            
          						<c:choose>
											<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">											
												<option value="${item.zoneId}">avalon</option>
											</c:when>
											<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
												<option value="${item.zoneId}">provincial</option>
											</c:when>
											<c:otherwise>
												<option value="${item.zoneId}">${item.zoneName}</option>
											</c:otherwise>
								</c:choose>
				            
				            </c:forEach>
				  </select>
 			
		</div>
    
    	<div class="form-group">
  				<label for="tender_title">Title:</label>
  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" placeholder="Enter Tender Title"></input>  			
		</div>
    
        <div class="form-group">
  				<label for="tender_title">Closing Date:</label>
  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" placeholder="Select Closing Date"></input>
		</div>
                    
   
       <div class="form-group">
  				<label for="tender_doc">Document:</label>
     			<input type="file" id="tender_doc" name="tender_doc"  class="form-control-file">
   		</div>
   
     
     
     <div class="form-group">
      		
				  <label for="opening_location">Opening Office Location:</label>     
			     <select id="opening_location" name="opening_location" class="form-control">
			            <c:forEach var="item" items="${regions}" >
			            
         					<c:choose>
										<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">						
											<option value="${item.zoneId}">avalon</option>
										</c:when>
										<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
											<option value="${item.zoneId}">provincial</option>
										</c:when>
										<c:otherwise>
											<option value="${item.zoneId}">${item.zoneName}</option>
										</c:otherwise>
							</c:choose>	            
			            				
			            </c:forEach>
			     </select>
   </div>  
       
     <div class="form-group">
      		
				  <label for="tender_status">Status:</label> 
						    <select id="tender_status" name="tender_status" class="form-control">
								<c:forEach var="item" items="${statuslist}">
									<c:if test="${item.value eq 'OPEN'}">
										<option value="${item.key}">${item.value}</option>
									</c:if>
								</c:forEach>
						    </select>
						    
	</div>					    
		
		
				    
						    
   <div id="extraDetails" style="border:1px solid red;padding:5px;background:#ffffe6;margin-bottom:10px;">
   <b>TENDER AWARDED DETAILS</b><br/>
   Please fill out the below to update the tender details to complete the tender listing if this tender you are adding is not currently listed and is already awarded. 
    These details can be edited later. If multiple awarded, please enter each company and the amount awarded to that company. (i.e. Company A for $###.##; Company B for $####.##, etc.) with 
							   total value of tender entered in the field below.
    <br/><br/>
    
  
		
		<div class="form-group">
  				<label for="awarded_to">Awarded Details:</label>
  				<br/>Number of chars remaining: <span id="sessionNum_counter">3800</span><br/>
  				<textarea  autocomplete="false" id="awarded_to" name="awarded_to" class="form-control" style="height:100px;" maxlength="3500"></textarea>
  				
		</div>
		<b>Amount:</b> <br/>
		<div class="input-group">  	
					
  				<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
  				 <input type="text" class="form-control" id="contract_value" name="contract_value"></input>
		</div>
		<br/>
       <div class="form-group">
  				<label for="awarded_date">Awarded Date:</label>
  				 <input type="text" class="form-control" id="awarded_date" name="awarded_date" autocomplete="off"></input>
		</div>
    
                       
                      
    </div>                
     <div align="center">             
    <button class="btn btn-sm btn-success" style="color:white;margin-top:5px;" id="butSave">Add This Tender</button> &nbsp; 
    <a class="btn btn-sm btn-danger" style="color:white;margin-top:5px;" role="button" HREF='viewTenders.html'>Back to Tender List</a> &nbsp;   
      </div>      
    </form>
  	
    
    
    <%}%>
    </div>
    
  
				
								
					</div>
	 </div>
		<br/>
	
		
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2018 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
<script>
$(document).ready(function(){
	
	$("#awarded_to").val("TBA");
	$("#contract_value").val("0");
	$("#awarded_date").val("12/12/2020");
	
	var maxChars = $("#awarded_to");
	var max_length = maxChars.attr('maxlength');
	if (max_length > 0) {
	    maxChars.bind('keyup', function(e){
	        length = new Number(maxChars.val().length);
	        counter = max_length-length;
	        $("#sessionNum_counter").text(counter);
	    });
	}
	
	
	
});



	

</script>
    
    
    
    
  </body>

</html>