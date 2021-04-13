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
		<title>Tender Administration</title>					

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
								
								
								<fmt:parseNumber var="todayDay" type="number" value="${todayDay}" />
								<fmt:parseNumber var="todayYear" type="number" value="${todayYear}" />
								<fmt:parseNumber var="todayHour" type="number" value="${todayHour}" />
								<fmt:parseNumber var="todayMinute" type="number" value="${todayMinute}" />
								
  
  <div class="row pageBottomSpace">
<div class="col siteBodyTextBlack">

<div class="siteHeaderGreen">View Tender Details</div>
  
  
                      
                      
 <form id="pol_cat_frm" action="updateTenderDetails.html" method="post" ENCTYPE="multipart/form-data">
      	<input type="hidden" id="op" name="op" value="CONFIRM">
		<input type="hidden" value="${tender.id}" id="id" name="id">
                     
         <div class="row container-fluid">
      		<div class="col-lg-2 col-12">TENDER NUMBER:
					<input type="text" class="form-control"  id="tender_number"  name="tender_number" value="${tender.tenderNumber}">
			</div>
			
			<div class="col-lg-2 col-12">STATUS:
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
			</div>
			<div class="col-lg-2 col-12">FOR REGION:
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
 			<div class="col-lg-2 col-12">CLOSING ON:
			  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" value="${tender.closingDateFormatted}"></input>
			</div>
 			
 			<div class="col-lg-2 col-12"> OPENING AT:  
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
 			</div>
 			<br/><br/>
			 <div class="row container-fluid">
      		<div class="col-lg-12 col-12">TENDER TITLE:
			  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" value="${tender.tenderTitle}"></input>  			
			</div>		
			</div>
			<br/><br/>
			<div class="row container-fluid">
			<div class="col-lg-12 col-12">TENDER DOCUMENT:  ( <a href="${tender.fileURL}">${tender.tenderDoc}</a> )
			<div class="custom-file">
    				<input type="file" class="custom-file-input form-control" id="tender_doc" name="tender_doc">
    				<label class="custom-file-label" for="customFile">Choose file</label>
  			</div>
			 </div>
			 </div>  		
			   		
			   							<fmt:formatDate value="${tender.closingDate}" pattern="DDD" var="dayClosed" />
                                  		<fmt:formatDate value="${tender.closingDate}" pattern="yyyy" var="yearClosed" />
                                  		<fmt:formatDate value="${tender.dateAdded}" pattern="DDD" var="dayAdded" />
                                  		
                                  		<fmt:parseNumber var="dayClosed" type="number" value="${dayClosed}" />
                                  		<fmt:parseNumber var="yearClosed" type="number" value="${yearClosed}" />
                                  		<fmt:parseNumber var="dayAdded" type="number" value="${dayAdded}" />
                                  		<!-- LOGIC TEST: (${todayDay} > ${dayClosed } or ( ${todayDay} = ${dayClosed} and ((${todayHour} = 14 and ${todayMinute } > 29) or ${todayHour} > 14))) and (${tender.tenderStatus.description} = OPEN or ${tender.tenderStatus.description} = AMMENDED)-->                                  		
                                  		<c:choose>
						    			<c:when test="${ (todayDay gt dayClosed  or   (todayDay eq dayClosed  and ((todayHour eq 14  and todayMinute gt 29) or todayHour gt 14))) and (tender.tenderStatus.description eq 'OPEN' or tender.tenderStatus.description eq 'AMMENDED')}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This tender is now closed. 
													Please update status above to CLOSED or AWARDED. If AWARDED, please update details below.
													</div>
													<script>$('document').ready(function(){
														$("#extraDetails").css("position","static").css("visibility","visible");
														$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													});</script>
				                       	</c:when>
				                       	<c:otherwise>
				                       	<script>$('document').ready(function(){$("#extraDetails").css("position","absolute").css("visibility","hidden");});</script>
				                       	<div class="alert alert-success" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This Tender is still OPEN. Further details on Awarding can be entered once the tender has closed.</div>
				                       	</c:otherwise>  
				                       	</c:choose>
				                       <br/><br/>      
					    
					    <div align="center">             
						    <button class="btn btn-sm btn-success" style="color:white;" id="butSave"><i class="fas fa-redo"></i> Save Changes to this Tender</button> &nbsp; 
						    <a class="fancybox btn btn-sm btn-primary" style="color:White;" href="#inline1" title="Add Addendum" data-toggle="modal" data-target="#addAddendumModal"><i class="fas fa-folder-plus"></i> Add Addendum</a> &nbsp;
						    <a class="btn btn-sm btn-danger" style="color:white;" role="button" HREF='viewTenders.html'><i class="fas fa-step-backward"></i> Back to List</a>
					 </div> 
					    
							<br/> 
                   
		                   <div id="extraDetails" style="padding:5px;background:#ffffe6;margin-bottom:10px;margin-top:10px;">
							   <b>TENDER AWARDED DETAILS</b><br/>
							   Please fill out the below to update the tender details to complete the tender listing if this tender is now CLOSED and AWARDED. 
							   These details can be edited later on any posted tender. If multiple awarded, please enter each company and the amount awarded to that company. (i.e. Company A for $###.##; Company B for $####.##, etc.) with 
							   total value of tender entered in the field below.
							    <br/><br/>
							    
							  
							<div class="row container-fluid">
							<div class="col-lg-12 col-12">		
									<b>AWARDED DETAILS:</b>
							  				<span style="text-align:right;">Number of chars remaining: <span id="sessionNum_counter">3800</span></span>
							  				<textarea  autocomplete="false" id="awarded_to" name="awarded_to" maxlength="3800" style="height:100px;" class="form-control">
							  				<c:out value="${empty tender.awardedTo ? 'TBA' : tender.awardedTo}" />
							  				</textarea>
							</div> 
							</div> 				
								<div class="row container-fluid">
									<div class="col-lg-6 col-12"><b>TOTAL AMOUNT:</b>				
											<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
							  				 <input type="text" class="form-control" id="contract_value" name="contract_value" value="<c:out value="${empty tender.contractValueFormatted ? '1.00' : tender.contractValueFormatted}" />"></input>
									</div>
									<div class="col-lg-6 col-12"><b>AWARDED DATE:</b>
							  				 <input type="text" class="form-control" id="awarded_date" name="awarded_date" autocomplete="off" value="<c:out value="${empty tender.awardedDateFormatted ? '2000-01-01' : tender.awardedDateFormatted}" />"></input>
									
		                       		</div>
		                       </div>
		                      
		    				</div> 
           
                   <p>
                   
            <div style="display:none;text-align:center;" class="alert alert-info" id="saveMessage">NOTICE: Make sure you <b>Save your changes</b> above to update the tender.</div>
                   
                   <div class="siteHeaderGreen">Addendum(s)</div>
                   
                   
					
					 <table class="table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;" id="addendumTable">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">							
							<th width="45%" style="border-right:1px solid white;">TTITLE</th>						    												
							<th width="30%" style="border-right:1px solid white;">DOCUMENT</th>	
							<th width="15%" style="border-right:1px solid white;">ADDENDUM DATE</th>		
							<th width="10%" style="border-right:1px solid white;">OPTIONS</th>		
					</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${tender.otherTendersFiles}" varStatus="counter">
							<c:set var="addendumCount" value="${addendumCount + 1}" />
							<script>$('#tender_status').val('6');
							$("#saveMessage").css("display","block");
							</script>
							<tr>
							<td width="45%">${p.tfTitle}</td>
							<td width="30%"><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank">Addendum Document (PDF)</a></td>							
							<td width="15%">${p.addendumDateFormatted}</td>
							<td width="10%"><a class="btn btn-danger btn-xs" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherTendersFile.html?id=${p.id}&fid=${p.tfDoc}&tid=${p.tenderId}'>DEL</a></td>
							 </tr>
						</c:forEach>		
						<c:if test="${addendumCoun <= 0}">
							<tr>
							<td width="45%">No Addendum(s) currently listed</td>
							<td width="30%"></td>							
							<td width="15%"></td>
							<td width="10%"></td>
		                    </tr>
						</c:if>									
			</tbody>
			</table>
				

<!-- The Modal -->
<div class="modal" id="addAddendumModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Add Addendum</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">
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
  													
								
							
							File (PDF):						
								<input type="file"  id="other_tenders_file" name="other_tenders_file"  class="form-control">
							
						Date:				
								<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="todayAddendum" />
								
								<input type="text" class="form-control" id="addendum_date"  name="addendum_date" autocomplete="off" value="${todayAddendum}">
							
							<br/>	
      </div>

      <!-- Modal footer -->
      <div class="modal-footer">
      <a class="btn btn-sm btn-success" href="#" role="button" onclick="sendtendersinfo();" style="color:White;" data-dismiss="modal">Add Addendum</a> &nbsp;
        <button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Close</button>
      </div>

    </div>
  </div>
</div>




				 
			
			
    </form>
    
</div></div>



<script>
$(document).ready(function(){
	
	var maxChars = $("#awarded_to");
	var max_length = maxChars.attr('maxlength');
	if (max_length > 0) {
	    maxChars.bind('keyup', function(e){
	        length = new Number(maxChars.val().length);
	        counter = max_length-length;
	        $("#sessionNum_counter").text(counter);
	        
	        if(length >10) {
				$('#tender_status').val('5');		
			} else {
				$('#tender_status').val('2');		
			}
	        
	        
	    });
	}
	

});

$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});


</script>   
   
    
  </body>

</html>
			