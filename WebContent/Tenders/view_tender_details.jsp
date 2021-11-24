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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
	
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
    		    	
    		    	 beforeShow: function() {
    		    	        setTimeout(function(){
    		    	            $('.ui-datepicker').css('z-index', 99999999999999);
    		    	        }, 0);
    		    	    },    		    	
        		      changeMonth: true,//this option for allowing user to select month
        		      changeYear: true, //this option for allowing user to select from year range
        		      dateFormat: "dd/mm/yy"
        		    });
    		  }

    		);

	</script>
	<style>
	
	.hide {
    position: absolute !important;
    top: -9999px !important;
    left: -9999px !important;
    visibility: hidden !important;
}
	
	</style>
	
	</head>

  <body><br/>
  								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
								<fmt:formatDate value="${now}" pattern="kk" var="todayHour" />
								<fmt:formatDate value="${now}" pattern="mm" var="todayMinute" />
								<fmt:formatDate value="${now}" pattern="dd/MM/yyyy" var="theClosingDate" />	
								
								<fmt:parseNumber var="todayDay" type="number" value="${todayDay}" />
								<fmt:parseNumber var="todayYear" type="number" value="${todayYear}" />
								<fmt:parseNumber var="todayHour" type="number" value="${todayHour}" />
								<fmt:parseNumber var="todayMinute" type="number" value="${todayMinute}" />
								
  
  <div class="row pageBottomSpace">
<div class="col siteBodyTextBlack">

<div class="siteHeaderGreen">View Tender Details</div>
  
  
                      
                      
 <form id="pol_cat_frm" action="updateTenderDetails.html" method="post" ENCTYPE="multipart/form-data" class="was-validated">
      	<input type="hidden" id="op" name="op" value="CONFIRM">
		<input type="hidden" value="${tender.id}" id="id" name="id">
                     
         <div class="row container-fluid">
         <div class="col-lg-3 col-12">STATUS:
									    <select id="tender_status" name="tender_status" class="form-control the_tender_status" required>
											<c:forEach var="item" items="${statuslist}">
											<c:if test="${item.key ne '4' and item.key ne '7' and item.key ne '8' }">
												 <c:choose>
					    							<c:when test="${item.key eq tender.tenderStatus.value}">					       									
					       									<option value="${item.key}" selected="selected">${item.value}</option>
					    							</c:when>
					    							<c:otherwise>
					        							<option value="${item.key}">${item.value }</option>
					    							</c:otherwise>
    											</c:choose>
											</c:if>	
											</c:forEach>
									    </select>
			</div>          
      		<div class="col-lg-9 col-12">TITLE:
			  				<input type="text" class="form-control" id="tender_title" name="tender_title" maxlength="130" value="${tender.tenderTitle}" required></input>  			
			</div>		
			</div>
			<br/><br/> 
         <div class="row container-fluid">
						         <div class="col-lg-3 col-12">TENDER NUMBER (i.e. 21-0123):
											<input type="text" class="form-control"  id="tender_number"  name="tender_number" value="${tender.tenderNumber}" required>
											<div class="invalid-feedback">Please fill out this field.</div>
									</div>
									<div class="col-lg-3 col-12">FOR REGION:
												  <select class="form-control" id="region" name="region" required>						  
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
						 			<div class="col-lg-3 col-12">CLOSING ON: 
									  				 <input type="text" class="form-control" id="closing_date" name="closing_date" autocomplete="off" value="${tender.closingDateFormatted}" required></input>
									</div>						 			
						 			<div class="col-lg-3 col-12"> OPENING AT:  
													     <select id="opening_location" name="opening_location" class="form-control" required>
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
			<div class="col-lg-12 col-12">TENDER DOCUMENT:  ( <a href="/includes/files/tenders/doc/${tender.docUploadName}">${tender.tenderDoc}</a> )
			<div class="custom-file">
    				<input type="file" class="custom-file-input form-control" id="tender_doc" name="tender_doc">
    				<label class="custom-file-label" for="customFile">If you need to update the document, select a new file to replace.</label>
    				
    				<script>
									// Add the following code if you want the name of the file appear on select									
									$(".custom-file-input").on("change", function() {
									  var fileName = $(this).val().split("\\").pop();
									  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
									});
									</script>
    				
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
						    			<c:when test="${ ((todayDay gt dayClosed  or   (todayDay eq dayClosed  and ((todayHour eq 14  and todayMinute gt 29) or todayHour gt 14))) and (tender.tenderStatus.description eq 'OPEN' or tender.tenderStatus.description eq 'AMMENDED'))}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> 
													Status has been automatically change to CLOSED for you. Press SAVE below to save these changes.<br/>
													 If the tender has been AWARDED, change status to AWARDED above and enter details below:
													</div>
													<script>$('document').ready(function(){
														//$("#extraDetails").css("position","static").css("visibility","visible");
														$("#extraDetails").removeClass("hide");
														
														$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													});</script>
				                       	</c:when>
				                       	<c:when test="${ tender.tenderStatus.description eq 'CLOSED'}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This tender is now closed. 
													If the tender has been AWARDED, change status to AWARDED above. You will then be able to edit Awarded details.
													</div>
													<script>$('document').ready(function(){
														//$("#extraDetails").css("position","static").css("visibility","visible");
														//$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													});</script>
				                       	</c:when>
				                      <c:when test="${ tender.tenderStatus.description eq 'AWARDED'}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This tender is now closed and awarded. 
													 Update AWARDED details below if needed:
													</div>
													<script>$('document').ready(function(){
														//$("#extraDetails").css("position","static").css("visibility","visible");
														$("#extraDetails").removeClass("hide");
														
														//$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													});</script>
				                       	</c:when>
				                       	
				                       	 <c:when test="${ tender.tenderStatus.description eq 'CANCELLED'}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This tender has been cancelled. 	</div>
													
				                       	</c:when>
				                       	
				                       	
				                       	<c:when test="${ tender.tenderStatus.description eq 'EXCEPTIONS'}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This listing is an EXCEPTION. 
													Only details relating to open tender exceptions will be available for input. EXCEPTIONS cannot have their status changed.
													</div>
													<script>
														//$("#exceptionDetails").css("position","static").css("visibility","visible");
														
														$("#exceptionDetails").removeClass("hide");
														
														//$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													</script>
				                       	</c:when>
				                       		<c:when test="${ tender.tenderStatus.description eq 'NOT AWARDED'}">													
													<div class="alert alert-danger" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This tender has not been awarded.
													</div>
													<script>
														//$("#exceptionDetails").css("position","static").css("visibility","visible");
														
														//$("#exceptionDetails").removeClass("hide");
														
														//$('#tender_status').val('2');		
														//$("#tender_status option:contains(CLOSED)").attr('selected', 'selected');
													
													</script>
				                       	</c:when>
				                       	
				                       	<c:otherwise>
				                       	<script>$('document').ready(function(){$("#extraDetails").addClass("hide");});</script>
				                       	<div class="alert alert-success" style="text-align:center;margin-top:10px;"><b>NOTICE:</b> This Tender is still OPEN. Further details on Awarding can be entered once the tender has closed.</div>
				                       	</c:otherwise>  
				                       	</c:choose>
				                       <br/><br/>      
					    
	
					    <div align="center">             
						    <button class="btn btn-sm btn-success" style="color:white;" id="butSave"><i class="fas fa-redo"></i> Save Changes to this Tender</button> &nbsp; 
						    <a class="btn btn-sm btn-danger" style="color:white;" role="button" HREF='viewTenders.html'><i class="fas fa-step-backward"></i> Back to List</a>
					 </div> 
					    <br/><br/>

 <!-- EXCEPTIONS DETAILS -->  		
   		<div id="exceptionsDetails" class="hide" style="border:1px solid red;padding:5px;background:#FFF5EE;padding-bottom:50px;margin-bottom:10px;">
   		  <b>EXCEPTION  DETAILS</b><br/>
				Please fill out the below to update the details to complete the exception to open tender.						 
		 <br/><br/>
<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Contract Description:</b>  
    			<textarea  required autocomplete="false" id="edescription" name="edescription" maxlength="3500"  style="height:100px;" class="form-control">
    			<c:out value="${tender.teBean ne null ? tender.teBean.eDescription ne null ?  tender.teBean.eDescription:'': '' }" />
    			</textarea>
  				<div class="invalid-feedback">Please fill out this field.</div>			
			</div>
		</div>
<br/><br/>

    	<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Vendor Name:</b>  
  				<input type="text" class="form-control" id="vendor_name" name="vendor_name" maxlength="1000" placeholder="Enter Vendor Name"   value="${tender.teBean ne null ? tender.teBean.vendorName ne null ?  tender.teBean.vendorName:'': '' }" required></input>  			
  				<div class="invalid-feedback">Please fill out this field.</div>
			</div>
		</div>		    	
		<br/><br/>
    	<div class="row container-fluid">
      		<div class="col-lg-8 col-12">
    			<b>Vendor Address:</b> (Town/City) 
  				<input required type="text" class="form-control" id="eaddress" name="eaddress" maxlength="1000" placeholder="Enter Address"
  				value="${tender.teBean ne null ? tender.teBean.eAddress ne null ?  tender.teBean.eAddress:'': '' }"></input>  			
			</div>
	
      		<div class="col-lg-4 col-12">
    			<b>Vendor Province/State :</b>     			
    			<select class="form-control" id="elocation" name="elocation" required>
	<option value="">Select One</option>	
	
	
	<option value="${ tender.teBean.eLocation ne null ? tender.teBean.eLocation:''}"  selected>${ tender.teBean.eLocation ne null ? tender.teBean.eLocation:''}</option>
	
	
	<optgroup label="Canadian Provinces">
		<option value="AB">Alberta</option>
		<option value="BC">British Columbia</option>
		<option value="MB">Manitoba</option>
		<option value="NB">New Brunswick</option>
		<option value="NL">Newfoundland and Labrador</option>
		<option value="NT">Northwest Territories</option>
		<option value="NS">Nova Scotia</option>
		<option value="NU">Nunavut</option>
		<option value="ON">Ontario</option>
		<option value="PE">Prince Edward Island</option>
		<option value="QC">Quebec</option>
		<option value="SK">Saskatchewan</option>
		<option value="YT">Yukon Territory</option>
	</optgroup>
	<optgroup label="U.S. States/Territories">
		<option value="AK">Alaska</option>
		<option value="AL">Alabama</option>
		<option value="AR">Arkansas</option>
		<option value="AZ">Arizona</option>
		<option value="CA">California</option>
		<option value="CO">Colorado</option>
		<option value="CT">Connecticut</option>
		<option value="DC">District of Columbia</option>
		<option value="DE">Delaware</option>
		<option value="FL">Florida</option>
		<option value="GA">Georgia</option>
		<option value="HI">Hawaii</option>
		<option value="IA">Iowa</option>
		<option value="ID">Idaho</option>
		<option value="IL">Illinois</option>
		<option value="IN">Indiana</option>
		<option value="KS">Kansas</option>
		<option value="KY">Kentucky</option>
		<option value="LA">Louisiana</option>
		<option value="MA">Massachusetts</option>
		<option value="MD">Maryland</option>
		<option value="ME">Maine</option>
		<option value="MI">Michigan</option>
		<option value="MN">Minnesota</option>
		<option value="MO">Missouri</option>
		<option value="MS">Mississippi</option>
		<option value="MT">Montana</option>
		<option value="NC">North Carolina</option>
		<option value="ND">North Dakota</option>
		<option value="NE">Nebraska</option>
		<option value="NH">New Hampshire</option>
		<option value="NJ">New Jersey</option>
		<option value="NM">New Mexico</option>
		<option value="NV">Nevada</option>
		<option value="NY">New York</option>
		<option value="OH">Ohio</option>
		<option value="OK">Oklahoma</option>
		<option value="OR">Oregon</option>
		<option value="PA">Pennsylvania</option>
		<option value="PR">Puerto Rico</option>
		<option value="RI">Rhode Island</option>
		<option value="SC">South Carolina</option>
		<option value="SD">South Dakota</option>
		<option value="TN">Tennessee</option>
		<option value="TX">Texas</option>
		<option value="UT">Utah</option>
		<option value="VA">Virginia</option>
		<option value="VT">Vermont</option>
		<option value="WA">Washington</option>
		<option value="WI">Wisconsin</option>
		<option value="WV">West Virginia</option>
		<option value="WY">Wyoming</option>
	</optgroup>
	
</select>
    		</div>
		</div>
		<br/><br/>
		
<div class="row container-fluid">
      		<div class="col-lg-3 col-12">
    			<b>Price:</b>  
  				<input type="text" class="form-control" id="eprice" name="eprice" maxlength="1000" placeholder="Enter Price" value="${tender.teBean ne null ? tender.teBean.ePrice ne null ?  tender.teBean.ePrice:'': '' }"></input>  
  				<div class="invalid-feedback">Please fill out this field.</div>					
			</div>
	  		<div class="col-lg-5 col-12">
    			<b>PO Number:</b>  
  				<input type="text" class="form-control" id="po_number" name="po_number" maxlength="500" placeholder="Enter PO Number"	 value="${tender.teBean ne null ? tender.teBean.poNumber ne null ?  tender.teBean.poNumber:'': '' }"></input>  		
  				<div class="invalid-feedback">Please fill out this field.</div>			
			</div>			
			<div class="col-lg-4 col-12">
    			<b>Renewal:</b>  
  				<select id="erenewal" name="erenewal" class="form-control">
					<c:forEach var="item" items="${renewallist}">
						<option value="${item.value}" ${tender.teBean ne null?tender.teBean.tenderRenewal.value eq item.value?'SELECTED':'':''}>${item.description}</option>
					</c:forEach>
				</select> 
				 			
			</div>
			</div>
			
	<br/><br/>		
			<div class="row container-fluid">
			<div class="col-lg-12 col-12">
			<div id="divother" style="display:none;">
			If Other, please explain briefly:<br/>
			<input type="text" class="form-control" id="erenewalother" name="erenewalother" maxlength="1000" placeholder="Enter Other" value="${tender.teBean ne null?tender.teBean.renewalother:''}"></input></div> 
			</div>
			
</div>

   		<br/><br/>
    	<div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Terms:</b>  
    			<textarea  autocomplete="false" id="eterms" name="eterms" maxlength="3500"  style="height:100px;" class="form-control">
    			<c:out value="${tender.teBean ne null ? tender.teBean.eTerms ne null ?  tender.teBean.eTerms:'': '' }" />
    			</textarea>
    			<div class="invalid-feedback">Please fill out this field.</div>		
    		</div>
		</div>
		
     <br/><br/>
     <div class="row container-fluid">
      		<div class="col-lg-12 col-12">
    			<b>Exception Clause:</b>  
    			<textarea  autocomplete="false"  id="eclause" name="eclause" maxlength="3500"  style="height:100px;" class="form-control">
    			<c:out value="${tender.teBean ne null ? tender.teBean.eClause ne null ?  tender.teBean.eClause:'': '' }" />
    			</textarea>
    			<div class="invalid-feedback">Please fill out this field.</div>		
			</div>
		</div>
		<br/><br/>  		


</div>
<!-- END EXCEPTIONS BLOCK -->
	
	
<br/><br/>

<!-- ADDENDUMS BLOCK  -->
					<div class="alert alert-info hide" id="addendumBlock"> 					
						
					
					  <a class="fancybox btn btn-xs btn-danger" style="color:White;float:right;margin-bottom:5px;" href="#inline1" title="Add Addendum" data-toggle="modal" data-target="#addAddendumModal"><i class="fas fa-folder-plus"></i> ADD ADDENDUM</a> &nbsp;
						   
					 <b>ADDENDUM(s) </b><br/>  
					 
					 <table class="table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;" id="addendumTable">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">							
							<th width="45%" style="border-right:1px solid white;">TITLE</th>						    												
							<th width="30%" style="border-right:1px solid white;">DOCUMENT</th>	
							<th width="15%" style="border-right:1px solid white;">ADDENDUM DATE</th>		
							<th width="10%" style="border-right:1px solid white;">OPTIONS</th>		
					</tr>
					</thead>
					<tbody>
						<c:forEach var="p" items="${tender.otherTendersFiles}" varStatus="counter">
							<c:set var="addendumCount" value="${addendumCount + 1}" />							
							<tr>
							<td width="45%">${p.tfTitle}</td>
							<td width="30%"><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank">Addendum Document (PDF)</a></td>							
							<td width="15%">${p.addendumDateFormatted}</td>
							<td width="10%"><a class="btn btn-danger btn-xs" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherTendersFile.html?id=${p.id}&fid=${p.tfDoc}&tid=${p.tenderId}'>DEL</a></td>
							 </tr>
							
						</c:forEach>		
						<c:if test="${addendumCount le 0}">
							<tr>
							<td width="45%">No Addendum(s) currently listed</td>
							<td width="30%"></td>							
							<td width="15%"></td>
							<td width="10%"></td>
		                    </tr>
						</c:if>									
						
						 
							 
			</tbody>
			</table>					 
			</div>		    
<!-- END ADDENDUMS BLOCK -->					    
	
		<br/><br/>
<!-- AWARDED DATE  BLOCK -->			
		<div id="theAwardDate" class="hide" style="z-index:-999;border:1px solid red;padding:5px;background:#F0FFF0;margin-bottom:10px;">		
		<b><span id="awardType"></span> AWARDED DATE</b>
	<div class="form-group">
  				<label for="awarded_date">Select Date: (Defaults to current date for an exception. Click date field to change.)</label>
  				<input type="text" class="form-control" id="awarded_date" name="awarded_date" autocomplete="off" value="<c:out value="${empty tender.awardedDateFormatted ? '01/01/2021' : tender.awardedDateFormatted}" />"></input>
  				 
		</div>
	</div>
	
 <!-- AWARDED DETAILS BLOCK -->		
          <div id="extraDetails" class="hide" style="z-index:-999;border:1px solid red;padding:5px;background:#ffffe6;margin-bottom:10px;">   
		                   <div  style="margin-bottom:10px;">
							   <b>TENDER AWARDED DETAILS</b><br/>
							   Please fill out the below to update the tender details to complete the tender listing if this tender is now CLOSED and AWARDED. 
							   These details can be edited later on any posted tender. If multiple awarded, please enter each company and the amount awarded to that company. (i.e. Company A for $###.##; Company B for $####.##, etc.) with 
							   total value of tender entered in the field below.
							    <br/><br/>
							    
							  
							<div class="row container-fluid">
							<div class="col-lg-12 col-12">		
									<b>AWARDED DETAILS:</b>
							  		<textarea  autocomplete="false" id="awarded_to" name="awarded_to" class="form-control" style="height:100px;" maxlength="3500" required>
							  				<c:out value="${empty tender.awardedTo ? 'TBA' : tender.awardedTo}" />
							  				</textarea>
							  				<script>							  				
							  				$('#awarded_to').val($.trim($('#awarded_to').val()));							  				
							  				</script>
							</div> 
							</div> 				
								<div class="row container-fluid">
									<div class="col-lg-6 col-12"><b>TOTAL AMOUNT:</b>				
											<span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
							  				 <input type="text" class="form-control" id="contract_value" name="contract_value" value="<c:out value="${empty tender.contractValueFormatted ? '0.00' : tender.contractValueFormatted}" />"></input>
									</div>
									
		                       </div>
		                      
		    				</div> <br/>&nbsp;<br/>
           </div>
        <p>
                <br/>&nbsp;<br/>   
            <div style="display:none;text-align:center;" class="alert alert-info" id="saveMessage">NOTICE: Make sure you <b>Save your changes</b> above to update the tender.</div>
                   
                
				

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
  													
								
							
							<b>File (PDF):</b>						
								<input type="file"  id="other_tenders_file" name="other_tenders_file"  class="form-control">
							
						<b>Date:</b>				
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
	
	
	//DEFAULTS	
		 $('#po_number').mask('00 0000');	 
		 $('#tender_number').mask('00-000S');	 
		 $("#awardType").text("TENDER");		 
		 
		var pageWordCountConf = {
	    	    showParagraphs: true,
	    	    showWordCount: true,
	    	    showCharCount: true,
	    	    countSpacesAsChars: true,
	    	    countHTML: true,
	    	    maxWordCount: -1,
	    	    maxCharCount: 3500,
	    	    }
		
		  	CKEDITOR.replace( 'edescription',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
		    CKEDITOR.replace( 'eterms',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
		    CKEDITOR.replace( 'eclause',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
		    CKEDITOR.replace( 'awarded_to',{wordcount: pageWordCountConf,toolbar : 'Basic'} );
		
		    
			   
		   //HIDE THIS
		   	$("#addendumBlock").addClass("hide");
		   	$("#theAwardDate").addClass("hide");
		  	$("#extraDetails").addClass("hide");
		  	$("#exceptionsDetails").addClass("hide");		
		  	
		   //DISABLE THIS	
		   $('#tender_status').removeAttr('required').prop("readonly",true);	
		   $('#tender_title').removeAttr('required').prop("readonly",true);	
		   $('#tender_number').removeAttr('required').prop("readonly",true);	
		   $('#region').removeAttr('required').prop("readonly",true);	
		   $('#closing_date').removeAttr('required').prop("readonly",true);	
		   $('#opening_location').removeAttr('required').prop("readonly",true);		   
		   $('#tender_doc').removeAttr('required').prop("readonly",true);	
		   
		   $('#edescription').removeAttr('required').prop("readonly",true);	
		   $('#vendor_name').removeAttr('required').prop("readonly",true);	
		   $('#eaddress').removeAttr('required').prop("readonly",true);	
		   $('#elocation').removeAttr('required').prop("readonly",true);	
		   $('#eprice').removeAttr('required').prop("readonly",true);	
		   $('#po_number').removeAttr('required').prop("readonly",true);	
		   $('#erenewal').removeAttr('required').prop("readonly",true);	
		   $('#erenewalother').removeAttr('required').prop("readonly",true);	
		   $('#eterms').removeAttr('required').prop("readonly",true);	
		   $('#eclause').removeAttr('required').prop("readonly",true);			   
		   $('#awarded_date').removeAttr('required').prop("readonly",true);		   
		   $('#awarded_to').removeAttr('required').prop("readonly",true);	
		   $('#contract_value').removeAttr('required').prop("readonly",true);		   
		    
		   	//CKEDITOR.instances['edescription'].setReadOnly(true);
			  //CKEDITOR.instances['eterms'].setReadOnly(true);
			  //CKEDITOR.instances['eclause'].setReadOnly(true);
			
				//$("#contract_value").val("0.00");
				//$("#awarded_date").val("${theClosingDate}");		  
			//	CKEDITOR.instances['awarded_to'].setData("TBA");
		// $('#closing_date').val("${theClosingDate}");
					  //$('#tender_number').val("00-0000");	
					  
//  OPEN
	
				
				  if($("#erenewal").val() == '5'){ 		
					  $('#divother').css("display","block");			  
					  
				  } else {
					  $('#divother').css("display","none");		
				  };

					$('#erenewal').change(function(){
						
						  if($(this).val() == '5'){ 		
							  $('#divother').css("display","block");			  
							  
						  } else {
							  $('#divother').css("display","none");		
						  };
					});				  
				  
				  

   if( $('#tender_status').val() =='1') {
	
	   // SHOW THIS	   
	   // $("#addendumBlock").removeClass("hide");
	   	//$("#theAwardDate").removeClass("hide");
	  	//$("#extraDetails").removeClass("hide");
	  	//$("#exceptionsDetails").removeClass("hide");		   
	   
	   //ENABLE THIS
	   $('#tender_status').removeAttr('readonly').prop("required",true);	
	   $('#tender_title').removeAttr('readonly').prop("required",true);
	   $('#tender_number').removeAttr('readonly').prop("required",true);
	   $('#region').removeAttr('readonly').prop("required",true);
	   $('#closing_date').removeAttr('readonly').prop("required",true);
	   $('#opening_location').removeAttr('readonly').prop("required",true);	   
	   $('#tender_doc').removeAttr('readonly').prop("required",false);	   
	   
	   // $('#edescription').removeAttr('readonly').prop("required",true); 
	   // $('#vendor_name').removeAttr('readonly').prop("required",true);
	  // $('#eaddress').removeAttr('readonly').prop("required",true);
	   // $('#elocation').removeAttr('readonly').prop("required",true);
	  // $('#eprice').removeAttr('readonly').prop("required",true);
	  // $('#po_number').removeAttr('readonly').prop("required",true);
	   // $('#erenewal').removeAttr('readonly').prop("required",true);
	   // $('#erenewalother').removeAttr('readonly').prop("required",true);
	   // $('#eterms').removeAttr('readonly').prop("required",true);
	   // $('#eclause').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_date').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_to').removeAttr('readonly').prop("required",true);
	   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
	   
	   // HIDE THIS
	    $("#addendumBlock").addClass("hide");
	   	$("#theAwardDate").addClass("hide");
	  	$("#extraDetails").addClass("hide");
	  	$("#exceptionsDetails").addClass("hide");	
	  	$("#tender_status option[value='9']").remove();
	  	
	  	
	   // DISABLE THIS	
	   // $('#tender_status').removeAttr('required').prop("readonly",true);	
	   // $('#tender_title').removeAttr('required').prop("readonly",true);	
	   // $('#tender_number').removeAttr('required').prop("readonly",true);	
	   // $('#region').removeAttr('required').prop("readonly",true);	
	  // $('#closing_date').removeAttr('required').prop("readonly",true);	
	   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
	   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
	   
	   $('#edescription').removeAttr('required').prop("readonly",true);	
	   $('#vendor_name').removeAttr('required').prop("readonly",true);	
	   $('#eaddress').removeAttr('required').prop("readonly",true);	
	   $('#elocation').removeAttr('required').prop("readonly",true);	
	   $('#eprice').removeAttr('required').prop("readonly",true);	
	   $('#po_number').removeAttr('required').prop("readonly",true);	
	   $('#erenewal').removeAttr('required').prop("readonly",true);	
	   $('#erenewalother').removeAttr('required').prop("readonly",true);	
	   $('#eterms').removeAttr('required').prop("readonly",true);	
	   $('#eclause').removeAttr('required').prop("readonly",true);	
	   
	   $('#awarded_date').removeAttr('required').prop("readonly",true);
	   
	   $('#awarded_to').removeAttr('required').prop("readonly",true);	
	   $('#contract_value').removeAttr('required').prop("readonly",true);		
	   
} else if( $('#tender_status').val() =='2') {
		// CLOSED	
		   // SHOW THIS	   
		 
	   	$("#addendumBlock").removeClass("hide");
	   	// $("#theAwardDate").removeClass("hide");
	  	// $("#extraDetails").removeClass("hide");
	  	// $("#exceptionsDetails").removeClass("hide");		   
	   
	   //ENABLE THIS
	   $('#tender_status').removeAttr('readonly').prop("required",true);	
	   $('#tender_title').removeAttr('readonly').prop("required",true);
	   $('#tender_number').removeAttr('readonly').prop("required",true);
	   $('#region').removeAttr('readonly').prop("required",true);
	   $('#closing_date').removeAttr('readonly').prop("required",true);
	   $('#opening_location').removeAttr('readonly').prop("required",true);	   
	   $('#tender_doc').removeAttr('readonly').prop("required",false);	   
	   
	   // $('#edescription').removeAttr('readonly').prop("required",true);
	   // $('#vendor_name').removeAttr('readonly').prop("required",true);
	   // $('#eaddress').removeAttr('readonly').prop("required",true);
	   // $('#elocation').removeAttr('readonly').prop("required",true);
	   // $('#eprice').removeAttr('readonly').prop("required",true);
	   // $('#po_number').removeAttr('readonly').prop("required",true);
	   // $('#erenewal').removeAttr('readonly').prop("required",true);
	   // $('#erenewalother').removeAttr('readonly').prop("required",true);
	   // $('#eterms').removeAttr('readonly').prop("required",true);
	   // $('#eclause').removeAttr('readonly').prop("required",true);
	   
	   //$('#awarded_date').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_to').removeAttr('readonly').prop("required",true);
	   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
	   
	   //HIDE THIS
	   //	$("#addendumBlock").addClass("hide");
	   	$("#theAwardDate").addClass("hide");
	  	$("#extraDetails").addClass("hide");
	  	$("#exceptionsDetails").addClass("hide");		
	  	$("#tender_status option[value='9']").remove();
	   //DISABLE THIS	
	   // $('#tender_status').removeAttr('required').prop("readonly",true);	
	   // $('#tender_title').removeAttr('required').prop("readonly",true);	
	   // $('#tender_number').removeAttr('required').prop("readonly",true);	
	   // $('#region').removeAttr('required').prop("readonly",true);	
	   // $('#closing_date').removeAttr('required').prop("readonly",true);	
	   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
	   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
	   
	   $('#edescription').removeAttr('required').prop("readonly",true);	
	   $('#vendor_name').removeAttr('required').prop("readonly",true);	
	   $('#eaddress').removeAttr('required').prop("readonly",true);	
	   $('#elocation').removeAttr('required').prop("readonly",true);	
	   $('#eprice').removeAttr('required').prop("readonly",true);	
	   $('#po_number').removeAttr('required').prop("readonly",true);	
	   $('#erenewal').removeAttr('required').prop("readonly",true);	
	   $('#erenewalother').removeAttr('required').prop("readonly",true);	
	   $('#eterms').removeAttr('required').prop("readonly",true);	
	   $('#eclause').removeAttr('required').prop("readonly",true);	
	   
	   $('#awarded_date').removeAttr('required').prop("readonly",true);
	   
	  $('#awarded_to').removeAttr('required').prop("readonly",true);	
	   $('#contract_value').removeAttr('required').prop("readonly",true);		
		
} else if( $('#tender_status').val() =='3') {
		// CANCELLED		
		   // SHOW THIS	   
	   	$("#addendumBlock").removeClass("hide");
	   	// $("#theAwardDate").removeClass("hide");
	  	// $("#extraDetails").removeClass("hide");
	  	// $("#exceptionsDetails").removeClass("hide");		   
	   
	   //ENABLE THIS
	   $('#tender_status').removeAttr('readonly').prop("required",true);	
	   $('#tender_title').removeAttr('readonly').prop("required",true);
	   $('#tender_number').removeAttr('readonly').prop("required",true);
	   $('#region').removeAttr('readonly').prop("required",true);
	   $('#closing_date').removeAttr('readonly').prop("required",true);
	   $('#opening_location').removeAttr('readonly').prop("required",true);	   
	   $('#tender_doc').removeAttr('readonly').prop("required",false);	   
	   
	   // $('#edescription').removeAttr('readonly').prop("required",true);
	   // $('#vendor_name').removeAttr('readonly').prop("required",true);
	   // $('#eaddress').removeAttr('readonly').prop("required",true);
	   // $('#elocation').removeAttr('readonly').prop("required",true);
	   // $('#eprice').removeAttr('readonly').prop("required",true);
	   // $('#po_number').removeAttr('readonly').prop("required",true);
	   // $('#erenewal').removeAttr('readonly').prop("required",true);
	   // $('#erenewalother').removeAttr('readonly').prop("required",true);
	   // $('#eterms').removeAttr('readonly').prop("required",true);
	   // $('#eclause').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_date').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_to').removeAttr('readonly').prop("required",true);
	   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
	   
	   //HIDE THIS
	   	// $("#addendumBlock").addClass("hide");
	   	$("#theAwardDate").addClass("hide");
	  	$("#extraDetails").addClass("hide");
	  	$("#exceptionsDetails").addClass("hide");		
	  	$("#tender_status option[value='9']").remove();
	  	
	   //DISABLE THIS	
	   // $('#tender_status').removeAttr('required').prop("readonly",true);	
	   // $('#tender_title').removeAttr('required').prop("readonly",true);	
	   // $('#tender_number').removeAttr('required').prop("readonly",true);	
	   // $('#region').removeAttr('required').prop("readonly",true);	
	   // $('#closing_date').removeAttr('required').prop("readonly",true);	
	   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
	   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
	   
	   $('#edescription').removeAttr('required').prop("readonly",true);	
	   $('#vendor_name').removeAttr('required').prop("readonly",true);	
	   $('#eaddress').removeAttr('required').prop("readonly",true);	
	   $('#elocation').removeAttr('required').prop("readonly",true);	
	   $('#eprice').removeAttr('required').prop("readonly",true);	
	   $('#po_number').removeAttr('required').prop("readonly",true);	
	   $('#erenewal').removeAttr('required').prop("readonly",true);	
	   $('#erenewalother').removeAttr('required').prop("readonly",true);	
	   $('#eterms').removeAttr('required').prop("readonly",true);	
	   $('#eclause').removeAttr('required').prop("readonly",true);	
	   
	   $('#awarded_date').removeAttr('required').prop("readonly",true);
	   
	   $('#awarded_to').removeAttr('required').prop("readonly",true);	
	   $('#contract_value').removeAttr('required').prop("readonly",true);		
		
} else if( $('#tender_status').val() =='5') {
		// AWARDED		
		 $("#awardType").text("TENDER");
				
		   // SHOW THIS	   
		   	$("#addendumBlock").removeClass("hide");
		   	$("#theAwardDate").removeClass("hide");
		  	$("#extraDetails").removeClass("hide");
		  	// $("#exceptionsDetails").removeClass("hide");		   
		   
		   //ENABLE THIS
		   $('#tender_status').removeAttr('readonly').prop("required",true);	
		   $('#tender_title').removeAttr('readonly').prop("required",true);
		   $('#tender_number').removeAttr('readonly').prop("required",true);
		   $('#region').removeAttr('readonly').prop("required",true);
		   $('#closing_date').removeAttr('readonly').prop("required",true);
		   $('#opening_location').removeAttr('readonly').prop("required",true);	   
		   $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
		   
		   // $('#edescription').removeAttr('readonly').prop("required",true);
		   // $('#vendor_name').removeAttr('readonly').prop("required",true);
		   // $('#eaddress').removeAttr('readonly').prop("required",true);
		   // $('#elocation').removeAttr('readonly').prop("required",true);
		   // $('#eprice').removeAttr('readonly').prop("required",true);
		   // $('#po_number').removeAttr('readonly').prop("required",true);
		   // $('#erenewal').removeAttr('readonly').prop("required",true);
		   // $('#erenewalother').removeAttr('readonly').prop("required",true);
		   // $('#eterms').removeAttr('readonly').prop("required",true);
		   // $('#eclause').removeAttr('readonly').prop("required",true);
		   
		   $('#awarded_date').removeAttr('readonly').prop("required",true);
		   
		   $('#awarded_to').removeAttr('readonly').prop("required",true);
		   $('#contract_value').removeAttr('readonly').prop("required",true);	  
		   
		   // HIDE THIS
		   	// $("#addendumBlock").addClass("hide");
		   	// $("#theAwardDate").addClass("hide");
		  	// $("#extraDetails").addClass("hide");
		  	$("#exceptionsDetails").addClass("hide");		
		  	$("#tender_status option[value='9']").remove();
		  	
		   // DISABLE THIS	
		   // $('#tender_status').removeAttr('required').prop("readonly",true);	
		   // $('#tender_title').removeAttr('required').prop("readonly",true);	
		   // $('#tender_number').removeAttr('required').prop("readonly",true);	
		   // $('#region').removeAttr('required').prop("readonly",true);	
		   // $('#closing_date').removeAttr('required').prop("readonly",true);	
		   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
		   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
		   
		   $('#edescription').removeAttr('required').prop("readonly",true);	
		   $('#vendor_name').removeAttr('required').prop("readonly",true);	
		   $('#eaddress').removeAttr('required').prop("readonly",true);	
		   $('#elocation').removeAttr('required').prop("readonly",true);	
		   $('#eprice').removeAttr('required').prop("readonly",true);	
		   $('#po_number').removeAttr('required').prop("readonly",true);	
		   $('#erenewal').removeAttr('required').prop("readonly",true);	
		   $('#erenewalother').removeAttr('required').prop("readonly",true);	
		   $('#eterms').removeAttr('required').prop("readonly",true);	
		   $('#eclause').removeAttr('required').prop("readonly",true);	
		   
		   // $('#awarded_date').removeAttr('required').prop("readonly",true);
		   
		   // $('#awarded_to').removeAttr('required').prop("readonly",true);	
		   // $('#contract_value').removeAttr('required').prop("readonly",true);		
		   
} else if( $('#tender_status').val() =='10') {
	// NOT AWARDED		
	 $("#awardType").text("TENDER");
			
	   // SHOW THIS	   
	   	$("#addendumBlock").removeClass("hide");
	   	//$("#theAwardDate").removeClass("hide");
	  	//$("#extraDetails").removeClass("hide");
	  	// $("#exceptionsDetails").removeClass("hide");		   
	   
	   //ENABLE THIS
	   $('#tender_status').removeAttr('readonly').prop("required",true);	
	   $('#tender_title').removeAttr('readonly').prop("required",true);
	   $('#tender_number').removeAttr('readonly').prop("required",true);
	   $('#region').removeAttr('readonly').prop("required",true);
	   $('#closing_date').removeAttr('readonly').prop("required",true);
	   $('#opening_location').removeAttr('readonly').prop("required",true);	   
	   $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
	   
	   // $('#edescription').removeAttr('readonly').prop("required",true);
	   // $('#vendor_name').removeAttr('readonly').prop("required",true);
	   // $('#eaddress').removeAttr('readonly').prop("required",true);
	   // $('#elocation').removeAttr('readonly').prop("required",true);
	   // $('#eprice').removeAttr('readonly').prop("required",true);
	   // $('#po_number').removeAttr('readonly').prop("required",true);
	   // $('#erenewal').removeAttr('readonly').prop("required",true);
	   // $('#erenewalother').removeAttr('readonly').prop("required",true);
	   // $('#eterms').removeAttr('readonly').prop("required",true);
	   // $('#eclause').removeAttr('readonly').prop("required",true);
	   
	   //$('#awarded_date').removeAttr('readonly').prop("required",true);
	   
	   //$('#awarded_to').removeAttr('readonly').prop("required",true);
	  // $('#contract_value').removeAttr('readonly').prop("required",true);	  
	   
	   // HIDE THIS
	   	// $("#addendumBlock").addClass("hide");
	    $("#theAwardDate").addClass("hide");
	  	$("#extraDetails").addClass("hide");
	  	$("#exceptionsDetails").addClass("hide");		
	  	$("#tender_status option[value='9']").remove();
	  	
	   // DISABLE THIS	
	   // $('#tender_status').removeAttr('required').prop("readonly",true);	
	   // $('#tender_title').removeAttr('required').prop("readonly",true);	
	   // $('#tender_number').removeAttr('required').prop("readonly",true);	
	   // $('#region').removeAttr('required').prop("readonly",true);	
	   // $('#closing_date').removeAttr('required').prop("readonly",true);	
	   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
	   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
	   
	   $('#edescription').removeAttr('required').prop("readonly",true);	
	   $('#vendor_name').removeAttr('required').prop("readonly",true);	
	   $('#eaddress').removeAttr('required').prop("readonly",true);	
	   $('#elocation').removeAttr('required').prop("readonly",true);	
	   $('#eprice').removeAttr('required').prop("readonly",true);	
	   $('#po_number').removeAttr('required').prop("readonly",true);	
	   $('#erenewal').removeAttr('required').prop("readonly",true);	
	   $('#erenewalother').removeAttr('required').prop("readonly",true);	
	   $('#eterms').removeAttr('required').prop("readonly",true);	
	   $('#eclause').removeAttr('required').prop("readonly",true);	
	   
	   $('#awarded_date').removeAttr('required').prop("readonly",true);
	   
	  $('#awarded_to').removeAttr('required').prop("readonly",true);	
	    $('#contract_value').removeAttr('required').prop("readonly",true);		
	   
		   
		
} else if( $('#tender_status').val() =='6') {
		// AMMENDED	
		   // SHOW THIS	   
	   	$("#addendumBlock").removeClass("hide");
	   	// $("#theAwardDate").removeClass("hide");
	  	// $("#extraDetails").removeClass("hide");
	  	// $("#exceptionsDetails").removeClass("hide");		   
	   
	   // ENABLE THIS
	   $('#tender_status').removeAttr('readonly').prop("required",true);	
	   $('#tender_title').removeAttr('readonly').prop("required",true);
	   $('#tender_number').removeAttr('readonly').prop("required",true);
	   $('#region').removeAttr('readonly').prop("required",true);
	   $('#closing_date').removeAttr('readonly').prop("required",true);
	   $('#opening_location').removeAttr('readonly').prop("required",true);	   
	   $('#tender_doc').removeAttr('readonly').prop("required",false);	    
	   
	   // $('#edescription').removeAttr('readonly').prop("required",true);
	   // $('#vendor_name').removeAttr('readonly').prop("required",true);
	   // $('#eaddress').removeAttr('readonly').prop("required",true);
	   // $('#elocation').removeAttr('readonly').prop("required",true);
	   // $('#eprice').removeAttr('readonly').prop("required",true);
	   // $('#po_number').removeAttr('readonly').prop("required",true);
	   // $('#erenewal').removeAttr('readonly').prop("required",true);
	   // $('#erenewalother').removeAttr('readonly').prop("required",true);
	   // $('#eterms').removeAttr('readonly').prop("required",true);
	   // $('#eclause').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_date').removeAttr('readonly').prop("required",true);
	   
	   // $('#awarded_to').removeAttr('readonly').prop("required",true);
	   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
	   
	   // HIDE THIS
	   	// $("#addendumBlock").addClass("hide");
	   	$("#theAwardDate").addClass("hide");
	  	$("#extraDetails").addClass("hide");
	  	$("#exceptionsDetails").addClass("hide");		
	  	$("#tender_status option[value='9']").remove();	
	   // DISABLE THIS	
	   // $('#tender_status').removeAttr('required').prop("readonly",true);	
	   // $('#tender_title').removeAttr('required').prop("readonly",true);	
	   // $('#tender_number').removeAttr('required').prop("readonly",true);	
	   // $('#region').removeAttr('required').prop("readonly",true);	
	   // $('#closing_date').removeAttr('required').prop("readonly",true);	
	   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
	   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
	   
	   $('#edescription').removeAttr('required').prop("readonly",true);	
	   $('#vendor_name').removeAttr('required').prop("readonly",true);	
	   $('#eaddress').removeAttr('required').prop("readonly",true);	
	   $('#elocation').removeAttr('required').prop("readonly",true);	
	   $('#eprice').removeAttr('required').prop("readonly",true);	
	   $('#po_number').removeAttr('required').prop("readonly",true);	
	   $('#erenewal').removeAttr('required').prop("readonly",true);	
	   $('#erenewalother').removeAttr('required').prop("readonly",true);	
	   $('#eterms').removeAttr('required').prop("readonly",true);	
	   $('#eclause').removeAttr('required').prop("readonly",true);	
	   
	   $('#awarded_date').removeAttr('required').prop("readonly",true);
	   
	   $('#awarded_to').removeAttr('required').prop("readonly",true);	
	   $('#contract_value').removeAttr('required').prop("readonly",true);		
		   
		   
		
} else	if( $('#tender_status').val() =='9') {
		// EXCEPTIONS
		 $("#awardType").text("EXCEPTION");
		
		 $("#tender_status option[value='1']").remove();
		 $("#tender_status option[value='2']").remove();
		 $("#tender_status option[value='3']").remove();
		 $("#tender_status option[value='5']").remove();
		 $("#tender_status option[value='6']").remove();
		 
		   // SHOW THIS	   
		   	// $("#addendumBlock").removeClass("hide");
		   	$("#theAwardDate").removeClass("hide");
		  	// $("#extraDetails").removeClass("hide");
		  	$("#exceptionsDetails").removeClass("hide");		   
		   
		   // ENABLE THIS
		   // $('#tender_status').removeAttr('readonly').prop("required",true);	
		   $('#tender_title').removeAttr('readonly').prop("required",true);
		   // $('#tender_number').removeAttr('readonly').prop("required",true);
		   $('#region').removeAttr('readonly').prop("required",true);
		   // $('#closing_date').removeAttr('readonly').prop("required",true);
		   // $('#opening_location').removeAttr('readonly').prop("required",true);	   
		    $('#tender_doc').removeAttr('readonly').prop("required",false);	    
		   
		   $('#edescription').removeAttr('readonly').prop("required",true);
		   $('#vendor_name').removeAttr('readonly').prop("required",true);
		   $('#eaddress').removeAttr('readonly').prop("required",true);
		   $('#elocation').removeAttr('readonly').prop("required",true);
		   $('#eprice').removeAttr('readonly').prop("required",true);
		   $('#po_number').removeAttr('readonly').prop("required",true);
		   $('#erenewal').removeAttr('readonly').prop("required",true);
		   $('#erenewalother').removeAttr('readonly').prop("required",false);
		   $('#eterms').removeAttr('readonly').prop("required",true);
		   $('#eclause').removeAttr('readonly').prop("required",true);
		   
		   $('#awarded_date').removeAttr('readonly').prop("required",true);
		   
		   // $('#awarded_to').removeAttr('readonly').prop("required",true);
		   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
		   			//CKEDITOR.instances['edescription'].setReadOnly(false);
				  //CKEDITOR.instances['eterms'].setReadOnly(false);
				  //CKEDITOR.instances['eclause'].setReadOnly(false);
		   
		   
		   
		   // HIDE THIS
		   	$("#addendumBlock").addClass("hide");
		   	// $("#theAwardDate").addClass("hide");
		  	$("#extraDetails").addClass("hide");
		  	// $("#exceptionsDetails").addClass("hide");		
		  	
		   // DISABLE THIS	
		   $('#tender_status').removeAttr('required').prop("readonly",true);	
		   // $('#tender_title').removeAttr('required').prop("readonly",true);	
		   $('#tender_number').removeAttr('required').prop("readonly",true);	
		   // $('#region').removeAttr('required').prop("readonly",true);	
		   $('#closing_date').removeAttr('required').prop("readonly",true);	
		   $('#opening_location').removeAttr('required').prop("readonly",true);		   
		   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
		   
		   // $('#edescription').removeAttr('required').prop("readonly",true);	
		   // $('#vendor_name').removeAttr('required').prop("readonly",true);	
		   // $('#eaddress').removeAttr('required').prop("readonly",true);	
		   // $('#elocation').removeAttr('required').prop("readonly",true);	
		   // $('#eprice').removeAttr('required').prop("readonly",true);	
		   // $('#po_number').removeAttr('required').prop("readonly",true);	
		   // $('#erenewal').removeAttr('required').prop("readonly",true);	
		   // $('#erenewalother').removeAttr('required').prop("readonly",true);	
		   // $('#eterms').removeAttr('required').prop("readonly",true);	
		   // $('#eclause').removeAttr('required').prop("readonly",true);	
		   
		   // $('#awarded_date').removeAttr('required').prop("readonly",true);
		   
		   $('#awarded_to').removeAttr('required').prop("readonly",true);	
		   $('#contract_value').removeAttr('required').prop("readonly",true);		
		  
		
} else	{
// DEFAULTS

};
			

				
				
	//If change status:
		
		$('#tender_status').change(function(){
					
			  
				  if($(this).val() =='1') {						
					   // SHOW THIS	   
					   // $("#addendumBlock").removeClass("hide");
					   	//$("#theAwardDate").removeClass("hide");
					  	//$("#extraDetails").removeClass("hide");
					  	//$("#exceptionsDetails").removeClass("hide");		   
					   
					   //ENABLE THIS
					   $('#tender_status').removeAttr('readonly').prop("required",true);	
					   $('#tender_title').removeAttr('readonly').prop("required",true);
					   $('#tender_number').removeAttr('readonly').prop("required",true);
					   $('#region').removeAttr('readonly').prop("required",true);
					   $('#closing_date').removeAttr('readonly').prop("required",true);
					   $('#opening_location').removeAttr('readonly').prop("required",true);	   
					   $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
					   
					   // $('#edescription').removeAttr('readonly').prop("required",true); 
					   // $('#vendor_name').removeAttr('readonly').prop("required",true);
					  // $('#eaddress').removeAttr('readonly').prop("required",true);
					   // $('#elocation').removeAttr('readonly').prop("required",true);
					  // $('#eprice').removeAttr('readonly').prop("required",true);
					  // $('#po_number').removeAttr('readonly').prop("required",true);
					   // $('#erenewal').removeAttr('readonly').prop("required",true);
					   // $('#erenewalother').removeAttr('readonly').prop("required",true);
					   // $('#eterms').removeAttr('readonly').prop("required",true);
					   // $('#eclause').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_date').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_to').removeAttr('readonly').prop("required",true);
					   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
					   
					   // HIDE THIS
					    $("#addendumBlock").addClass("hide");
					   	$("#theAwardDate").addClass("hide");
					  	$("#extraDetails").addClass("hide");
					  	$("#exceptionsDetails").addClass("hide");		
					  	
					   // DISABLE THIS	
					   // $('#tender_status').removeAttr('required').prop("readonly",true);	
					   // $('#tender_title').removeAttr('required').prop("readonly",true);	
					   // $('#tender_number').removeAttr('required').prop("readonly",true);	
					   // $('#region').removeAttr('required').prop("readonly",true);	
					  // $('#closing_date').removeAttr('required').prop("readonly",true);	
					   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
					   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
					   
					   $('#edescription').removeAttr('required').prop("readonly",true);	
					   $('#vendor_name').removeAttr('required').prop("readonly",true);	
					   $('#eaddress').removeAttr('required').prop("readonly",true);	
					   $('#elocation').removeAttr('required').prop("readonly",true);	
					   $('#eprice').removeAttr('required').prop("readonly",true);	
					   $('#po_number').removeAttr('required').prop("readonly",true);	
					   $('#erenewal').removeAttr('required').prop("readonly",true);	
					   $('#erenewalother').removeAttr('required').prop("readonly",true);	
					   $('#eterms').removeAttr('required').prop("readonly",true);	
					   $('#eclause').removeAttr('required').prop("readonly",true);	
					   
					   $('#awarded_date').removeAttr('required').prop("readonly",true);
					   
					   $('#awarded_to').removeAttr('required').prop("readonly",true);	
					   $('#contract_value').removeAttr('required').prop("readonly",true);		
					   
				} else if($(this).val() =='2') {
						// CLOSED	
						   // SHOW THIS	   						
					   	$("#addendumBlock").removeClass("hide");
					   	// $("#theAwardDate").removeClass("hide");
					  	// $("#extraDetails").removeClass("hide");
					  	// $("#exceptionsDetails").removeClass("hide");		   
					   
					   //ENABLE THIS
					   $('#tender_status').removeAttr('readonly').prop("required",true);	
					   $('#tender_title').removeAttr('readonly').prop("required",true);
					   $('#tender_number').removeAttr('readonly').prop("required",true);
					   $('#region').removeAttr('readonly').prop("required",true);
					   $('#closing_date').removeAttr('readonly').prop("required",true);
					   $('#opening_location').removeAttr('readonly').prop("required",true);	   
					   $('#tender_doc').removeAttr('readonly').prop("required",false);	    
					   
					   // $('#edescription').removeAttr('readonly').prop("required",true);
					   // $('#vendor_name').removeAttr('readonly').prop("required",true);
					   // $('#eaddress').removeAttr('readonly').prop("required",true);
					   // $('#elocation').removeAttr('readonly').prop("required",true);
					   // $('#eprice').removeAttr('readonly').prop("required",true);
					   // $('#po_number').removeAttr('readonly').prop("required",true);
					   // $('#erenewal').removeAttr('readonly').prop("required",true);
					   // $('#erenewalother').removeAttr('readonly').prop("required",true);
					   // $('#eterms').removeAttr('readonly').prop("required",true);
					   // $('#eclause').removeAttr('readonly').prop("required",true);
					   
					   //$('#awarded_date').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_to').removeAttr('readonly').prop("required",true);
					   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
					   
					   //HIDE THIS
					   //	$("#addendumBlock").addClass("hide");
					   	$("#theAwardDate").addClass("hide");
					  	$("#extraDetails").addClass("hide");
					  	$("#exceptionsDetails").addClass("hide");		
					  	
					   //DISABLE THIS	
					   // $('#tender_status').removeAttr('required').prop("readonly",true);	
					   // $('#tender_title').removeAttr('required').prop("readonly",true);	
					   // $('#tender_number').removeAttr('required').prop("readonly",true);	
					   // $('#region').removeAttr('required').prop("readonly",true);	
					   // $('#closing_date').removeAttr('required').prop("readonly",true);	
					   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
					   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
					   
					   $('#edescription').removeAttr('required').prop("readonly",true);	
					   $('#vendor_name').removeAttr('required').prop("readonly",true);	
					   $('#eaddress').removeAttr('required').prop("readonly",true);	
					   $('#elocation').removeAttr('required').prop("readonly",true);	
					   $('#eprice').removeAttr('required').prop("readonly",true);	
					   $('#po_number').removeAttr('required').prop("readonly",true);	
					   $('#erenewal').removeAttr('required').prop("readonly",true);	
					   $('#erenewalother').removeAttr('required').prop("readonly",true);	
					   $('#eterms').removeAttr('required').prop("readonly",true);	
					   $('#eclause').removeAttr('required').prop("readonly",true);	
					   
					   $('#awarded_date').removeAttr('required').prop("readonly",true);
					   
					  $('#awarded_to').removeAttr('required').prop("readonly",true);	
					   $('#contract_value').removeAttr('required').prop("readonly",true);		
						
				} else if($(this).val() =='3') {
						// CANCELLED		
						   // SHOW THIS	   
					   	$("#addendumBlock").removeClass("hide");
					   	// $("#theAwardDate").removeClass("hide");
					  	// $("#extraDetails").removeClass("hide");
					  	// $("#exceptionsDetails").removeClass("hide");		   
					   
					   //ENABLE THIS
					   $('#tender_status').removeAttr('readonly').prop("required",true);	
					   $('#tender_title').removeAttr('readonly').prop("required",true);
					   $('#tender_number').removeAttr('readonly').prop("required",true);
					   $('#region').removeAttr('readonly').prop("required",true);
					   $('#closing_date').removeAttr('readonly').prop("required",true);
					   $('#opening_location').removeAttr('readonly').prop("required",true);	   
					   $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
					   
					   // $('#edescription').removeAttr('readonly').prop("required",true);
					   // $('#vendor_name').removeAttr('readonly').prop("required",true);
					   // $('#eaddress').removeAttr('readonly').prop("required",true);
					   // $('#elocation').removeAttr('readonly').prop("required",true);
					   // $('#eprice').removeAttr('readonly').prop("required",true);
					   // $('#po_number').removeAttr('readonly').prop("required",true);
					   // $('#erenewal').removeAttr('readonly').prop("required",true);
					   // $('#erenewalother').removeAttr('readonly').prop("required",true);
					   // $('#eterms').removeAttr('readonly').prop("required",true);
					   // $('#eclause').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_date').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_to').removeAttr('readonly').prop("required",true);
					   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
					   
					   //HIDE THIS
					   	// $("#addendumBlock").addClass("hide");
					   	$("#theAwardDate").addClass("hide");
					  	$("#extraDetails").addClass("hide");
					  	$("#exceptionsDetails").addClass("hide");		
					  	
					   //DISABLE THIS	
					   // $('#tender_status').removeAttr('required').prop("readonly",true);	
					   // $('#tender_title').removeAttr('required').prop("readonly",true);	
					   // $('#tender_number').removeAttr('required').prop("readonly",true);	
					   // $('#region').removeAttr('required').prop("readonly",true);	
					   // $('#closing_date').removeAttr('required').prop("readonly",true);	
					   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
					   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
					   
					   $('#edescription').removeAttr('required').prop("readonly",true);	
					   $('#vendor_name').removeAttr('required').prop("readonly",true);	
					   $('#eaddress').removeAttr('required').prop("readonly",true);	
					   $('#elocation').removeAttr('required').prop("readonly",true);	
					   $('#eprice').removeAttr('required').prop("readonly",true);	
					   $('#po_number').removeAttr('required').prop("readonly",true);	
					   $('#erenewal').removeAttr('required').prop("readonly",true);	
					   $('#erenewalother').removeAttr('required').prop("readonly",true);	
					   $('#eterms').removeAttr('required').prop("readonly",true);	
					   $('#eclause').removeAttr('required').prop("readonly",true);	
					   
					   $('#awarded_date').removeAttr('required').prop("readonly",true);
					   
					   $('#awarded_to').removeAttr('required').prop("readonly",true);	
					   $('#contract_value').removeAttr('required').prop("readonly",true);		
						
				} else if($(this).val() =='5') {
						// AWARDED		
						 $("#awardType").text("TENDER");
								
						   // SHOW THIS	   
						   	$("#addendumBlock").removeClass("hide");
						   	$("#theAwardDate").removeClass("hide");
						  	$("#extraDetails").removeClass("hide");
						  	// $("#exceptionsDetails").removeClass("hide");		   
						   
						   //ENABLE THIS
						   $('#tender_status').removeAttr('readonly').prop("required",true);	
						   $('#tender_title').removeAttr('readonly').prop("required",true);
						   $('#tender_number').removeAttr('readonly').prop("required",true);
						   $('#region').removeAttr('readonly').prop("required",true);
						   $('#closing_date').removeAttr('readonly').prop("required",true);
						   $('#opening_location').removeAttr('readonly').prop("required",true);	   
						   $('#tender_doc').removeAttr('readonly').prop("required",false);	    
						   
						   // $('#edescription').removeAttr('readonly').prop("required",true);
						   // $('#vendor_name').removeAttr('readonly').prop("required",true);
						   // $('#eaddress').removeAttr('readonly').prop("required",true);
						   // $('#elocation').removeAttr('readonly').prop("required",true);
						   // $('#eprice').removeAttr('readonly').prop("required",true);
						   // $('#po_number').removeAttr('readonly').prop("required",true);
						   // $('#erenewal').removeAttr('readonly').prop("required",true);
						   // $('#erenewalother').removeAttr('readonly').prop("required",true);
						   // $('#eterms').removeAttr('readonly').prop("required",true);
						   // $('#eclause').removeAttr('readonly').prop("required",true);
						   
						   $('#awarded_date').removeAttr('readonly').prop("required",true);
						   CKEDITOR.instances['awarded_to'].setReadOnly(false);
						   $('#awarded_to').removeAttr('readonly').prop("required",true);
						   $('#contract_value').removeAttr('readonly').prop("required",true);	  
						   
						   // HIDE THIS
						   	// $("#addendumBlock").addClass("hide");
						   	// $("#theAwardDate").addClass("hide");
						  	// $("#extraDetails").addClass("hide");
						  	$("#exceptionsDetails").addClass("hide");		
						  	
						   // DISABLE THIS	
						   // $('#tender_status').removeAttr('required').prop("readonly",true);	
						   // $('#tender_title').removeAttr('required').prop("readonly",true);	
						   // $('#tender_number').removeAttr('required').prop("readonly",true);	
						   // $('#region').removeAttr('required').prop("readonly",true);	
						   // $('#closing_date').removeAttr('required').prop("readonly",true);	
						   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
						   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
						   
						   $('#edescription').removeAttr('required').prop("readonly",true);	
						   $('#vendor_name').removeAttr('required').prop("readonly",true);	
						   $('#eaddress').removeAttr('required').prop("readonly",true);	
						   $('#elocation').removeAttr('required').prop("readonly",true);	
						   $('#eprice').removeAttr('required').prop("readonly",true);	
						   $('#po_number').removeAttr('required').prop("readonly",true);	
						   $('#erenewal').removeAttr('required').prop("readonly",true);	
						   $('#erenewalother').removeAttr('required').prop("readonly",true);	
						   $('#eterms').removeAttr('required').prop("readonly",true);	
						   $('#eclause').removeAttr('required').prop("readonly",true);	
						   
						   // $('#awarded_date').removeAttr('required').prop("readonly",true);
						   
						   // $('#awarded_to').removeAttr('required').prop("readonly",true);	
						   // $('#contract_value').removeAttr('required').prop("readonly",true);		
						   
				} else if($(this).val() =='10') {
					// AWARDED		
					 $("#awardType").text("TENDER");
							
					   // SHOW THIS	   
					   	$("#addendumBlock").removeClass("hide");
					   //	$("#theAwardDate").removeClass("hide");
					  	//$("#extraDetails").removeClass("hide");
					  	// $("#exceptionsDetails").removeClass("hide");		   
					   
					   //ENABLE THIS
					   $('#tender_status').removeAttr('readonly').prop("required",true);	
					   $('#tender_title').removeAttr('readonly').prop("required",true);
					   $('#tender_number').removeAttr('readonly').prop("required",true);
					   $('#region').removeAttr('readonly').prop("required",true);
					   $('#closing_date').removeAttr('readonly').prop("required",true);
					   $('#opening_location').removeAttr('readonly').prop("required",true);	   
					   $('#tender_doc').removeAttr('readonly').prop("required",false);	    
					   
					   // $('#edescription').removeAttr('readonly').prop("required",true);
					   // $('#vendor_name').removeAttr('readonly').prop("required",true);
					   // $('#eaddress').removeAttr('readonly').prop("required",true);
					   // $('#elocation').removeAttr('readonly').prop("required",true);
					   // $('#eprice').removeAttr('readonly').prop("required",true);
					   // $('#po_number').removeAttr('readonly').prop("required",true);
					   // $('#erenewal').removeAttr('readonly').prop("required",true);
					   // $('#erenewalother').removeAttr('readonly').prop("required",true);
					   // $('#eterms').removeAttr('readonly').prop("required",true);
					   // $('#eclause').removeAttr('readonly').prop("required",true);
					   
					   //$('#awarded_date').removeAttr('readonly').prop("required",true);
					   //CKEDITOR.instances['awarded_to'].setReadOnly(false);
					   //$('#awarded_to').removeAttr('readonly').prop("required",true);
					  // $('#contract_value').removeAttr('readonly').prop("required",true);	  
					   
					   // HIDE THIS
					   	// $("#addendumBlock").addClass("hide");
					    $("#theAwardDate").addClass("hide");
					    $("#extraDetails").addClass("hide");
					  	$("#exceptionsDetails").addClass("hide");		
					  	
					   // DISABLE THIS	
					   // $('#tender_status').removeAttr('required').prop("readonly",true);	
					   // $('#tender_title').removeAttr('required').prop("readonly",true);	
					   // $('#tender_number').removeAttr('required').prop("readonly",true);	
					   // $('#region').removeAttr('required').prop("readonly",true);	
					   // $('#closing_date').removeAttr('required').prop("readonly",true);	
					   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
					   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
					   
					   $('#edescription').removeAttr('required').prop("readonly",true);	
					   $('#vendor_name').removeAttr('required').prop("readonly",true);	
					   $('#eaddress').removeAttr('required').prop("readonly",true);	
					   $('#elocation').removeAttr('required').prop("readonly",true);	
					   $('#eprice').removeAttr('required').prop("readonly",true);	
					   $('#po_number').removeAttr('required').prop("readonly",true);	
					   $('#erenewal').removeAttr('required').prop("readonly",true);	
					   $('#erenewalother').removeAttr('required').prop("readonly",true);	
					   $('#eterms').removeAttr('required').prop("readonly",true);	
					   $('#eclause').removeAttr('required').prop("readonly",true);	
					   
					  $('#awarded_date').removeAttr('required').prop("readonly",true);					   
					    $('#awarded_to').removeAttr('required').prop("readonly",true);	
					  $('#contract_value').removeAttr('required').prop("readonly",true);		
					   
				
						
				} else if($(this).val() =='6') {
						// AMMENDED	
						   // SHOW THIS	   
					   	$("#addendumBlock").removeClass("hide");
					   	// $("#theAwardDate").removeClass("hide");
					  	// $("#extraDetails").removeClass("hide");
					  	// $("#exceptionsDetails").removeClass("hide");		   
					   
					   // ENABLE THIS
					   $('#tender_status').removeAttr('readonly').prop("required",true);	
					   $('#tender_title').removeAttr('readonly').prop("required",true);
					   $('#tender_number').removeAttr('readonly').prop("required",true);
					   $('#region').removeAttr('readonly').prop("required",true);
					   $('#closing_date').removeAttr('readonly').prop("required",true);
					   $('#opening_location').removeAttr('readonly').prop("required",true);	   
					   $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
					   
					   // $('#edescription').removeAttr('readonly').prop("required",true);
					   // $('#vendor_name').removeAttr('readonly').prop("required",true);
					   // $('#eaddress').removeAttr('readonly').prop("required",true);
					   // $('#elocation').removeAttr('readonly').prop("required",true);
					   // $('#eprice').removeAttr('readonly').prop("required",true);
					   // $('#po_number').removeAttr('readonly').prop("required",true);
					   // $('#erenewal').removeAttr('readonly').prop("required",true);
					   // $('#erenewalother').removeAttr('readonly').prop("required",true);
					   // $('#eterms').removeAttr('readonly').prop("required",true);
					   // $('#eclause').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_date').removeAttr('readonly').prop("required",true);
					   
					   // $('#awarded_to').removeAttr('readonly').prop("required",true);
					   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
					   
					   // HIDE THIS
					   	// $("#addendumBlock").addClass("hide");
					   	$("#theAwardDate").addClass("hide");
					  	$("#extraDetails").addClass("hide");
					  	$("#exceptionsDetails").addClass("hide");		
					  	
					   // DISABLE THIS	
					   // $('#tender_status').removeAttr('required').prop("readonly",true);	
					   // $('#tender_title').removeAttr('required').prop("readonly",true);	
					   // $('#tender_number').removeAttr('required').prop("readonly",true);	
					   // $('#region').removeAttr('required').prop("readonly",true);	
					   // $('#closing_date').removeAttr('required').prop("readonly",true);	
					   // $('#opening_location').removeAttr('required').prop("readonly",true);		   
					   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
					   
					   $('#edescription').removeAttr('required').prop("readonly",true);	
					   $('#vendor_name').removeAttr('required').prop("readonly",true);	
					   $('#eaddress').removeAttr('required').prop("readonly",true);	
					   $('#elocation').removeAttr('required').prop("readonly",true);	
					   $('#eprice').removeAttr('required').prop("readonly",true);	
					   $('#po_number').removeAttr('required').prop("readonly",true);	
					   $('#erenewal').removeAttr('required').prop("readonly",true);	
					   $('#erenewalother').removeAttr('required').prop("readonly",true);	
					   $('#eterms').removeAttr('required').prop("readonly",true);	
					   $('#eclause').removeAttr('required').prop("readonly",true);	
					   
					   $('#awarded_date').removeAttr('required').prop("readonly",true);
					   
					   $('#awarded_to').removeAttr('required').prop("readonly",true);	
					   $('#contract_value').removeAttr('required').prop("readonly",true);		
						   
						   
						
				} else	if($(this).val() =='9') {
						// EXCEPTIONS
						 $("#awardType").text("EXCEPTIONS");
							
						   // SHOW THIS	   
						   	// $("#addendumBlock").removeClass("hide");
						   	$("#theAwardDate").removeClass("hide");
						  	// $("#extraDetails").removeClass("hide");
						  	$("#exceptionsDetails").removeClass("hide");		   
						   
						   // ENABLE THIS
						   // $('#tender_status').removeAttr('readonly').prop("required",true);	
						   $('#tender_title').removeAttr('readonly').prop("required",true);
						   // $('#tender_number').removeAttr('readonly').prop("required",true);
						   $('#region').removeAttr('readonly').prop("required",true);
						   // $('#closing_date').removeAttr('readonly').prop("required",true);
						   // $('#opening_location').removeAttr('readonly').prop("required",true);	   
						    $('#tender_doc').removeAttr('readonly').prop("required",false);	 	   
						   
						   $('#edescription').removeAttr('readonly').prop("required",true);
						   $('#vendor_name').removeAttr('readonly').prop("required",true);
						   $('#eaddress').removeAttr('readonly').prop("required",true);
						   $('#elocation').removeAttr('readonly').prop("required",true);
						   $('#eprice').removeAttr('readonly').prop("required",true);
						   $('#po_number').removeAttr('readonly').prop("required",true);
						   $('#erenewal').removeAttr('readonly').prop("required",true);
						   $('#erenewalother').removeAttr('readonly').prop("required",true);
						   $('#eterms').removeAttr('readonly').prop("required",true);
						   $('#eclause').removeAttr('readonly').prop("required",true);
						   
						   $('#awarded_date').removeAttr('readonly').prop("required",true);
						   
						   // $('#awarded_to').removeAttr('readonly').prop("required",true);
						   // $('#contract_value').removeAttr('readonly').prop("required",true);	  
						   			//CKEDITOR.instances['edescription'].setReadOnly(false);
								  //CKEDITOR.instances['eterms'].setReadOnly(false);
								  //CKEDITOR.instances['eclause'].setReadOnly(false);
						   
						   
						   
						   // HIDE THIS
						   	$("#addendumBlock").addClass("hide");
						   	// $("#theAwardDate").addClass("hide");
						  	$("#extraDetails").addClass("hide");
						  	// $("#exceptionsDetails").addClass("hide");		
						  	
						   // DISABLE THIS	
						   $('#tender_status').removeAttr('required').prop("readonly",true);	
						   // $('#tender_title').removeAttr('required').prop("readonly",true);	
						   $('#tender_number').removeAttr('required').prop("readonly",true);	
						   // $('#region').removeAttr('required').prop("readonly",true);	
						   $('#closing_date').removeAttr('required').prop("readonly",true);	
						   $('#opening_location').removeAttr('required').prop("readonly",true);		   
						   // $('#tender_doc').removeAttr('required').prop("readonly",true);	
						   
						   // $('#edescription').removeAttr('required').prop("readonly",true);	
						   // $('#vendor_name').removeAttr('required').prop("readonly",true);	
						   // $('#eaddress').removeAttr('required').prop("readonly",true);	
						   // $('#elocation').removeAttr('required').prop("readonly",true);	
						   // $('#eprice').removeAttr('required').prop("readonly",true);	
						   // $('#po_number').removeAttr('required').prop("readonly",true);	
						   // $('#erenewal').removeAttr('required').prop("readonly",true);	
						   // $('#erenewalother').removeAttr('required').prop("readonly",true);	
						   // $('#eterms').removeAttr('required').prop("readonly",true);	
						   // $('#eclause').removeAttr('required').prop("readonly",true);	
						   
						   // $('#awarded_date').removeAttr('required').prop("readonly",true);
						   
						   $('#awarded_to').removeAttr('required').prop("readonly",true);	
						   $('#contract_value').removeAttr('required').prop("readonly",true);		
						  
						
				} else	{
				// DEFAULTS

					  
					
					
								};
			});
	
	
	
	
	
	
	
	
	
	
	
	
	// $('#tender_status').change(function(){
	//	  if($(this).val() == '5'){ 
	//		  $("#extraDetails").css("position","static").css("visibility","visible");
	//	  } else {
	//		  $("#extraDetails").css("visibility","hidden");
	//	  }
	//	});
	
	
//var maxChars = $("#awarded_to");
//	var max_length = maxChars.attr('maxlength');
	//if (max_length > 0) {
	//    maxChars.bind('keyup', function(e){
//	        length = new Number(maxChars.val().length);
	//        counter = max_length-length;
	//        $("#sessionNum_counter").text(counter);
	        
	//        if(length >10) {
	//			$('#tender_status').val('5');		
	//		} else {
	//			$('#tender_status').val('2');		
	//		}
	        
	        
	   // });
	//}
	

});

$(".custom-file-input").on("change", function() {
  var fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});


</script>   
   
    
  </body>

</html>
			