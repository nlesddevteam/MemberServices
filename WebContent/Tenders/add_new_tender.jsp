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
    
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    	<script>
 $('document').ready(function(){  		

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
 <div class="row pageBottomSpace">
<div class="col siteBodyTextBlack">

<div class="siteHeaderGreen">Add New Tender</div>
	   		
		Please enter the Tender information below. Any tender you add here is assumed to be new and open. You can change this status once the tender is posted by editing or deleting.

			<%if(request.getAttribute("msgOK") != null){%>
                      
                        <p>                       
                       <div class="alert alert-success alert-dismissible" style="text-align:center;">
                       <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                       *** <%=(String)request.getAttribute("msgOK")%> ***
                       </div>
                        <p>  
                        <div align="center" style="margin-bottom:10px;">  
                        
                        <esd:SecurityAccessRequired permissions="TENDER-ADMIN">
					 	<a class="btn btn-sm btn-success" style="color:white;margin-top:5px;" role="button" href="addNewTender.html">Add Another Tender</a> &nbsp; 
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">  
                      	<a class="btn btn-sm btn-primary" style="color:white;margin-top:5px;" role="button" href="viewTenders.html">Back to Tender List</a> 
                    </esd:SecurityAccessRequired>            
                   
                      
                      </div>
                    <%} else {%>


    <form id="pol_cat_frm" name="TenderForm" action="addNewTender.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">         
     
     <p>     
      
      	<div class="row container-fluid">
      		<div class="col-lg-3 col-12">
				  <b>TENDER NUMBER:</b>
				  <input type="text" class="form-control"  id="tender_number"  name="tender_number" placeholder="eg: 18-000" maxlength="15">
			</div>
	
			
			<div class="col-lg-3 col-12">      		
				  <b>FOR REGION:</b>  
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
 			<div class="col-lg-3 col-12">
 			<b>CLOSING ON:</b>  
  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" placeholder="Select Closing Date"></input>
 			</div>
 			<div class="col-lg-3 col-12">
 			<b>OPENING AT:</b>  
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
 			
 			
 		</div>
    <br/><br/>
    	<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>TENDER TITLE:</b>  
  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" placeholder="Enter Tender Title"></input>  			
			</div>
		</div>
		    <br/><br/>
      			<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
  			<b>ADD TENDER DOCUMENT: (PDF)</b>  			
  			<div class="custom-file">
    <input type="file" class="custom-file-input form-control" id="tender_doc" name="tender_doc">
    <label class="custom-file-label" for="customFile">Choose file</label>
  </div>
  			
  			
     		
   		</div>
   		</div>
   
     <br/><br/>
	 <div align="center">             
    <button class="btn btn-sm btn-success" style="color:white;margin-top:5px;" id="butSave">Add This Tender</button> &nbsp; 
    <a class="btn btn-sm btn-danger" style="color:white;margin-top:5px;" role="button" HREF='viewTenders.html'>Back to Tender List</a> &nbsp;   
      </div>      			    
	
						    
   <div id="extraDetails" style="border:1px solid red;padding:5px;background:#ffffe6;margin-bottom:10px;visibility:hidden;">
   
   	STATUS:
						    <select id="tender_status" name="tender_status" class="form-control">
								<c:forEach var="item" items="${statuslist}">
									<c:if test="${item.value eq 'OPEN'}">
										<option value="${item.key}">${item.value}</option>
									</c:if>
								</c:forEach>
						    </select>
   
   
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
             
    
    </form>
  	

    <%}%>
   
    
  </div></div>
		

<script>
$(document).ready(function(){	
	$("#awarded_to").text("TBA");
	$("#contract_value").val("0.00");
	$("#awarded_date").val("12/12/2021");	
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
    <script>
$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
    
    
    
  </body>

</html>