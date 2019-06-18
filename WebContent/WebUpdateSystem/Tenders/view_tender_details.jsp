<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*,com.esdnl.webupdatesystem.tenders.constants.*"%>   

<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<c:set var="currentlyOpen" value="0" /> 
<c:set var="closedThisYear" value="0" /> 
<c:set var="awardedThisYear" value="0" /> 
<c:set var="cancelledThisYear" value="0" />
<c:set var="archivedClosed" value="0" /> 
<c:set var="archivedAwarded" value="0" />
<c:set var="archivedCancelled" value="0" />
<c:set var="now" value="0" />
<c:set var="todayHour" value="0" />
<c:set var="todayMinute" value="0" />
<c:set var="todayYear" value="0" />
<c:set var="todayDay" value="0" />
<c:set var="dayClosed" value="0" />
<c:set var="yearClosed" value="0" />
<c:set var="dayAdded" value="0" />
<c:set var="dayCheck" value="0" />
<c:set var="addendumCount" value="0" />
<esd:SecurityCheck />
<%
  User usr = (User) session.getAttribute("usr");
%>
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
    $(document).ready(
    		  
    
    		
    		
    		
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    			  
    			  $('.fancybox').fancybox();  
    			  
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
    		    $( "#addendum_date" ).datepicker({
        		      changeMonth: true,//this option for allowing user to select month
        		      changeYear: true, //this option for allowing user to select from year range
        		      dateFormat: "dd/mm/yy"
        		    });
    		  }

    		);

	</script>

	
	</head>

  <body><br/>
  								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
								<fmt:formatDate value="${now}" pattern="kk" var="todayHour" />
								<fmt:formatDate value="${now}" pattern="mm" var="todayMinute" />
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
				
				
				
				
				
				
				<div class="pageTitleHeader siteHeaders">View Tender Details</div>
                      <div class="pageBody">
                      
                      <%if(request.getAttribute("msg") != null){%>                    
                       
                     <p>                       
                       <div class="alert alert-warning alert-dismissible" style="text-align:center;"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>*** <%=(String)request.getAttribute("msg")%> ***</div>
                        <p>  
                        <div align="center" style="margin-bottom:10px;">                     
                        
                        <esd:SecurityAccessRequired permissions="TENDER-ADMIN">
					 	<a class="btn btn-sm btn-success" style="color:white;margin-top:5px;" role="button" href="addNewTender.html">Add a Tender</a> &nbsp; 
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
                      
 <form id="pol_cat_frm" action="updateTenderDetails.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
		<input type="hidden" value="${tender.id}" id="id" name="id">
                     
                     
                     <div class="form-group">
      		
						  <label for="tender_number">Number:</label>
						  <input type="text" class="form-control"  id="tender_number"  name="tender_number" value="${tender.tenderNumber}">
			
					 </div>
                     
                     <div class="form-group">
      		
						  <label for="region">Region:</label>
						  <select class="form-control" id="region" name="region">
						  
						  			<c:forEach var="item" items="${regions}" >
						            
						            
						            <c:choose>
    							<c:when test="${item.zoneId eq tender.tenderZone.zoneId}">
						            
		          						<c:choose>
													<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">											
														<option value="${item.zoneId}"  selected="selected">avalon</option>
													</c:when>
													<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
														<option value="${item.zoneId}"  selected="selected">provincial</option>
													</c:when>
													<c:otherwise>
														<option value="${item.zoneId}"  selected="selected">${item.zoneName}</option>
													</c:otherwise>
										</c:choose>
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
			  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" value="${tender.tenderTitle}"></input>  			
					</div>
    
			        <div class="form-group">
			  				<label for="closing_date">Closing Date:</label>
			  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" value="${tender.closingDateFormatted}"></input>
					</div>
                    
   
			       <div class="form-group">
			  				<label for="tender_doc">Document:  ( <a href="${tender.fileURL}">${tender.tenderDoc}</a> )</label>
			     			<input type="file" id="tender_doc" name="tender_doc"  class="form-control-file">
			   		</div>
                     
                   <div class="form-group">
      		
							  <label for="opening_location">Opening Office Location:</label>     
							     <select id="opening_location" name="opening_location" class="form-control">
							            <c:forEach var="item" items="${regions}" >
							            
							            <c:choose>
							            <c:when test="${item.zoneId eq tender.tenderOpeningLocation.zoneId}">
       									
				         					<c:choose>
														<c:when test="${ (item.zoneName eq 'eastern') or (item.zoneName eq 'avalon')}">						
															<option value="${item.zoneId}" selected="selected">avalon</option>
														</c:when>
														<c:when test="${fn:containsIgnoreCase(item.zoneName, 'NLESD')}">
															<option value="${item.zoneId}" selected="selected">provincial</option>
														</c:when>
														<c:otherwise>
															<option value="${item.zoneId}" selected="selected">${item.zoneName}</option>
														</c:otherwise>
											</c:choose>
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
												 <c:choose>
					    							<c:when test="${item.key eq tender.tenderStatus.value}">					       									
					       									<option value="${item.key}" selected="selected">${item.value}</option>
					    							</c:when>
					    							<c:otherwise>
					        							<option value="${item.key}">${item.value}</option>
					    							</c:otherwise>
    											</c:choose>
												
											</c:forEach>
									    </select>
						    <br/>
						         				<fmt:formatDate value="${tender.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${tender.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${tender.dateAdded}" pattern="DDD" var="dayAdded" />
						    				<c:if test="${((todayDay gt dayClosed) or ((todayDay eq dayClosed) and ((todayHour eq 14) and (todayMinute gt 29)) or (todayHour gt 14))) and ((tender.tenderStatus.description eq 'OPEN') or (tender.tenderStatus.description eq 'AMMENDED'))}">													
														<span style="color:Red;">NOTICE: </span>This tender is now closed. Please update status above to CLOSED or AWARDED. If AWARDED, please update details below.
				                             </c:if>  
				                             
						    
							</div>	
                   
		                   <div id="extraDetails" style="border:1px solid red;padding:5px;background:#ffffe6;margin-bottom:10px;">
							   <b>TENDER AWARDED DETAILS</b><br/>
							   Please fill out the below to update the tender details to complete the tender listing if this tender is now AWARDED. 
							   These details can be edited later on any posted tender. If multiple awarded, please enter each company and the amount awarded to that company. (i.e. Company A for $###.##; Company B for $####.##, etc.) with 
							   total value of tender entered in the field below.
							    <br/><br/>
							    
							  
									
									<div class="form-group">
							  				<label for="awarded_to">Awarded Details:</label>
							  				<br/>Number of chars remaining: <span id="sessionNum_counter">3800</span><br/>
							  				<textarea  autocomplete="false" id="awarded_to" name="awarded_to" maxlength="3800" style="height:100px;" class="form-control"><c:out value="${empty tender.awardedTo ? 'TBA' : tender.awardedTo}" /></textarea>
							  				
									</div>
									<b>Total Amount:</b> <br/>
									<div class="input-group">  	
												
							  				<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
							  				 <input type="text" class="form-control" id="contract_value" name="contract_value" value="<c:out value="${empty tender.contractValueFormatted ? '1.00' : tender.contractValueFormatted}" />"></input>
									</div>
									<br/>
							       <div class="form-group">
							  				<label for="awarded_date">Awarded Date:</label>
							  				 <input type="text" class="form-control" id="awarded_date" name="awarded_date" autocomplete="off" value="<c:out value="${empty tender.awardedDateFormatted ? '2000-01-01' : tender.awardedDateFormatted}" />"></input>
									</div>	    
		                       
		                      
		    				</div> 
                   
                    
                   <p><div align="center">             
						    <button class="btn btn-sm btn-success" style="color:white;" id="butSave">Update This Tender</button> &nbsp; 
						    <a class="fancybox btn btn-sm btn-primary" style="color:White;" href="#inline1" title="Add Addendum" onclick="OpenPopUp('${tender.id}');">Add Addendum</a> &nbsp;
						    <a class="btn btn-sm btn-danger" style="color:white;" role="button" HREF='viewTenders.html'>Back to List</a>
				 </div> 
                   
                                     
                    
                   <p><div class="pageSectionHeader siteSubHeaders">Addendum(s)</div>
					
					<div class="pageBody">	
                   
                 <p>
					
					<div style="width:30%;float:left;background-color:Green;color:white;">Title</div>
					<div style="width:30%;float:left;background-color:Green;color:white;">Document</div>
					<div style="width:20%;float:left;background-color:Green;color:white;">Addendum Date</div>
					<div style="width:20%;float:left;background-color:Green;color:white;">Options</div>
					<div style="clear:both;"></div>
						<c:forEach var="p" items="${tender.otherTendersFiles}" varStatus="counter">
							<c:set var="addendumCount" value="${addendumCount + 1}" />
							<div style="width:30%;float:left;">${p.tfTitle}</div>
							<div style="width:30%;float:left;"><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank">Addendum Document (PDF)</a></div>							
							<div style="width:20%;float:left;">${p.addendumDateFormatted}</div>
							<div style="width:20%;float:left;"><a onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherTendersFile.html?id=${p.id}&fid=${p.tfDoc}&tid=${p.tenderId}'>Delete file</a></div>
		                    	
						<div style="clear:both;"></div>
						</c:forEach>
					
				</div>
                  			 
			
				
				 
			<div id="inline1" style="width:600px;display: none;">
				<span style="font-size:16px;color:Red;font-weight:bold;">Add Addendum</span>
					<div style="border:1px solid red;background-color:white;padding:5px;color:Black;">
						
							<div class="form-group">
  								<label for="other_tenders_title">Title:</label>		
  								
  								<select id="other_tenders_title" name="other_tenders_title" class="form-control">
  								<c:if test="${addendumCount lt 1 }"><option value="Addendum 1">Addendum 1</option></c:if>
  								<c:if test="${addendumCount lt 2 }"><option value="Addendum 2">Addendum 2</option></c:if>  								
  								<c:if test="${addendumCount lt 3 }"><option value="Addendum 3">Addendum 3</option></c:if> 								
  								<c:if test="${addendumCount lt 4 }"><option value="Addendum 4">Addendum 4</option></c:if>
  								<c:if test="${addendumCount lt 5 }"><option value="Addendum 5">Addendum 5</option></c:if>  								
  								<c:if test="${addendumCount lt 6 }"><option value="Addendum 6">Addendum 6</option></c:if> 	
  								<c:if test="${addendumCount lt 7 }"><option value="Addendum 7">Addendum 7</option></c:if>
  								<c:if test="${addendumCount lt 8 }"><option value="Addendum 8">Addendum 8</option></c:if>  								
  								<c:if test="${addendumCount lt 9 }"><option value="Addendum 9">Addendum 9</option></c:if> 
  								<c:if test="${addendumCount lt 10 }"><option value="Addendum 10">Addendum 10</option></c:if>  									
  								</select>
  													
								
							</div>
							<div class="form-group">
  								<label for="other_tenders_title">File (PDF):</label>							
								<input type="file"  id="other_tenders_file" name="other_tenders_file"  class="form-control">
							</div>
							<div class="form-group">
  								<label for="other_tenders_title">Date:</label>							
								<input type="text" class="form-control" id="addendum_date"  name="addendum_date" autocomplete="off">
							</div>
							<br/>	
							
								
							<a class="btn btn-sm btn-success" href="#" role="button" onclick="sendtendersinfo();" style="color:White;">Add this Addendum</a> &nbsp; <a  style="color:White;" class="btn btn-sm btn-danger" href="#" role="button" onclick="closewindow();">Cancel</a>
								
						</div>	
			</div>       
			
    </form>
    
    
    
    
    
    
    
    <%}%>
    
</div>
    
    
		
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2018 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
<script>
$(document).ready(function(){
	
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
			