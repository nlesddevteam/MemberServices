<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>        
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");	
%>
<c:set var="school" value='<%= SchoolDB.getSchoolFullDetails(Integer.parseInt(request.getParameter("id"))) %>' />
<esd:SecurityCheck permissions='WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL,WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN' />
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
  
<script type="text/javascript">
	    $(function(){
	    	
	    	 //Fix twitter paste if use doesnt have height set  	
	    	$("#twitterembed").blur(function () {
					
   				if ($(this).val().indexOf("data-height") == -1 ) {  			          
   			       
   			    	 var checkForHeight = $('#twitterembed').val();
   			    	 var fixTwitterHeight = checkForHeight.replace("a class=","a data-height=400 class=")   			    	
   			    	$('#twitterembed').val(fixTwitterHeight);
   			  }
   					
   					
   				
   				});
	    	
	    	
	    	
	    	
	    	$('#zoneId').change(function(){
	    		getZoneRegions($(this).val());
	    		var schoolid = $('#schoolId').val();
	    		getZoneSchools($(this).val(),schoolid);
	    		
	    	});
	    	
	    	$('#regionId').change(function(){
	    		//getZoneRegions($(this).val());
	    		var schoolid = $('#schoolId').val();
	    		var zoneid = $('#zoneId').val();
	    		getZoneRegionSchools(zoneid,$(this).val(),schoolid);
	    		a
	    	});
	    	
	    	if(${school.zone ne null}){
	    		getZoneRegions(${school.zone.zoneId});
	    		
	    		if(${school.region ne null}){
	    			$('#regionId').val(${school.region.id});
	    		}
	    	}
	    	
	    
	    	
	    });
	    
	    function getZoneRegions(zoneid){
	    	
	    	$.ajax(
     			{
     				type: "POST",  
     				url: "/MemberServices/StaffRoom/SchoolDirectory/getZoneRegions.html",
     				data: {
     					id : zoneid,
     					ajax : true 
     				}, 
     				success: function(data){
     					if($(data).find('GET-ZONE-REGIONS-RESPONSE').length > 0) {
     						$('#regionId').children().remove();
     						$(data).find('REGION').each(function(){
     							$('#regionId').append($('<option>').attr({'value':$(this).attr('regionid')}).text($(this).attr('regionname')));
     						})
     					}
     				}, 
     				dataType: "xml",
     				async: false
     			}
     		);
	    }
	    function getZoneRegionSchools(zoneid,regionid,sid){
			var type="region";
	    	$.ajax(
     			{
     				type: "POST",  
     				url: "/MemberServices/StaffRoom/SchoolDirectory/getZoneRegionsSchools.html",
     				data: {
     					zid : zoneid, rid: regionid, ddtype: type, schoolid:sid 
     				}, 
     				success: function(data){
     					if($(data).find('GET-ZONE-SCHOOLS-RESPONSE').length > 0) {
     						
     						$('.pickList_sourceList li').remove();
     						$('.pickList_targetList li').remove();
     						$(data).find('SCHOOL').each(function(){
     							var id=$(this).find("ID").text();
     							var school=$(this).find("NAME").text();
     							$("#schoolstreamsenglish").pickList("insert",
     						    	    {
     						    	        value:    id,
     						    	        label:    school,
     						    	        selected: false
     						    	    });
     						    $("#schoolstreamsfrench").pickList("insert",
     						    	    {
     						    	        value:    id,
     						    	        label:    school,
     						    	        selected: false
     						    	    });
     						})
     					}
     				}, 
     				dataType: "xml",
     				async: false
     			}
     		);
	    }
	    function getZoneSchools(zoneid,sid){
			var vrid=0;
			var type="zone";
	    	$.ajax(
     			{
     				type: "POST",  
     				url: "/MemberServices/StaffRoom/SchoolDirectory/getZoneRegionsSchools.html",
     				data: {
     					zid : zoneid, rid: vrid, ddtype: type,schoolid: sid 
     				}, 
     				success: function(data){
     					if($(data).find('GET-ZONE-SCHOOLS-RESPONSE').length > 0) {
     						$('.pickList_sourceList li').remove();
     						$('.pickList_targetList li').remove();
     						$(data).find('SCHOOL').each(function(){
     							var id=$(this).find("ID").text();
     							var school=$(this).find("NAME").text();
     						    $("#schoolstreamsenglish").pickList("insert",
     						    	    {
     						    	        value:    id,
     						    	        label:    school,
     						    	        selected: false
     						    	    });
     						    $("#schoolstreamsfrench").pickList("insert",
     						    	    {
     						    	        value:    id,
     						    	        label:    school,
     						    	        selected: false
     						    	    });
     						})
     					}
     				}, 
     				dataType: "xml",
     				async: false
     			}
     		);
	    }
	    
    </script>	
    <script>
    
    $('document').ready(function(){	
    	
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	

		
		
	  $('#telephone,#fax').mask('000-0000');
	  $('#extension').mask('000');
	  $('#postalcode').mask('S0S 0S0');
	});
    </script>
    
    
    
    
    
    
    	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>

	</head>

  <body>
  <div class="siteHeaderGreen"><span style="color:Red;">${ school.schoolName }</span> Website School Directory Profile Information</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
  



<form action='updateSchool.html' method='POST' ENCTYPE="multipart/form-data" class="was-validated">
                  	<input type='hidden' name='schoolId' id='schoolId' value='${school.schoolID }' />	               
	               <input type='hidden' name='provinceState' value='NL' /> 
	               	You are updating the  <b>${ school.schoolName }</b> public profile documentation. 
	               	Not all items that display on your school profile page are editable here. 
	               	
	               	<p><b>You CANNOT change the name of the school, or the school administrators as that is assigned at the district level.</b>
	               	You may have to contact <a href="mailto:geofftaylor@nlesd.ca">geofftaylor@nlesd.ca</a> for any other updates you may require. 
	               	
	               	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:5px;padding:5px;text-align:center;">
	               	<b>NOTICE:</b> ALWAYS make sure this profile page is updated for your school, as this is the first link from our school directory people may access re your school.
	            	</div>
	            
	            When you have your changes made, press <b>Save Changes</b> at the bottom of this page.
	    
		<br/><br/>
		
		
					
<!-- ADDRESS -->	
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;text-transform:uppercase;">1. ${ school.schoolName } ADDRESS</div>
						  	<div class="card-body" style="background-color:white;">	 
						  	<b>School Name:</b> ${ school.schoolName }  <br/><br/>           
				<div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-6 col-12">
				<b>Street Address:</b>
			   <input type='text' id='address1' class="form-control" name='address1'  autocomplete="false" value="${ school.address1 }" required /> 
	         </div>
	         <div class="col-lg-6 col-12"> 	             
				<b>Postal Address:</b>
			    <input type='text' id='address2' class="form-control" name='address2'  autocomplete="false" value="${ school.address2 }" />	             
			</div>
			</div>
			<div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-6 col-12">
				<b>Town/City:</b>
			   <input type='text' id='citytown' class="form-control" name='citytown'   autocomplete="false" value="${ school.townCity }" required/>	             
			</div>
			<div class="col-lg-6 col-12">
				<b>Postal Code:</b>
			   <input type='text' id='postalcode' class="form-control" name='postalcode'   autocomplete="false" value="${ school.postalZipCode }" required />	             
			</div>
			</div>
</div>
</div>
		
<br/><br/>		

<!-- SCHOOL MISSION DESCRIPTION -->	               
				 	<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">2. MISSION / DESCRIPTION</div>
						  	<div class="card-body" style="background-color:#FFFFFF;">
						  	Please add some text describing your school environment and/or mission/values.<br/>
   							<textarea id='description'  autocomplete="false" name='description' class="form-control" required>${ school.detailsOther.description }</textarea>
							</div>
					</div>
	                                  	
	           
<!-- BUILDING PHOTO -->	
	       <br/><br/>
			       <div class="card bg-primary">
						  <div class="card-header" style="font-weight:bold;color:White;font-size:16px;">3. BUILDING PHOTO / SCHOOL CREST/LOGO</div>
						  <div class="card-body" style="background-color:#FFFFFF;">
						  <div class="row container-fluid" style="padding-top:5px;">
      						<div class="col-lg-6 col-12">
						  Please provide a outside photo of your school. Current photo (if any) is shown below.<br/>
						  								<c:choose>
										              		<c:when test="${ school.details.schoolPhotoFilename ne null }">	                      		
							                      				<img src="/schools/img/${ school.details.schoolPhotoFilename }" style="max-height:100px;float:right;padding:10px;">
							                      				<br/>To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</c:when>
							                				<c:otherwise>
							                				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">
							                				No school photo currently on file for your school. Please upload a outside photo of the school building/entrance.
							                				<br/>To add a photo, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</div>
							                				</c:otherwise>
							                			</c:choose>	
							                			<br/><br/>
							                			<div style="clear:both;"></div>
							                    		<input type='file' class="form-control" name='schoolPhotoFile' />
														 <c:if test="${ school.details.schoolPhotoFilename ne null }">	
														 Current File: <a href="/schools/img/${ school.details.schoolPhotoFilename }" target="_blank">${ school.details.schoolPhotoFilename }</a>
														 </c:if>
														
														
						</div>						
      					<div class="col-lg-6 col-12">
						Please provide your school crest or logo. Current file (if any) is shown below.<br/>	              
	           
	           											<c:choose>
										              		<c:when test="${ school.details.schoolCrestFilename ne null }">										              				                      		
							                      				<img src="/schools/img/${ school.details.schoolCrestFilename }" style="max-height:100px;float:right;padding:10px;">
							                      				<br/>To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                      				
							                				</c:when>
							                				<c:otherwise>
							                				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">
							                				No school crest/logo currently on file for your school. Please upload your school crest or logo if available.
							                				<br/>To add a crest, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</div>
							                				</c:otherwise>
							                			</c:choose>
							                			<br/><br/>
							                			<div style="clear:both;"></div>
	           											 <input type='file' class="form-control" name='schoolCrestFile' />
	           											 <c:if test="${ school.details.schoolCrestFilename ne null }">	
	           											 Current File: <a href="/schools/img/${ school.details.schoolCrestFilename }" target="_blank">${ school.details.schoolCrestFilename }</a>
														</c:if>
						</div></div>
						
						
						</div>
					</div>
	       

<br/><br/>
<!-- MISC DATA -->	              
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">4. MISC DETAILS</div>
						  	<div class="card-body" style="background-color:#FFFFFF;"> 
						  	<div class="row container-fluid" style="padding-top:5px;">
							<div class="col-lg-2 col-12">
								<b>Current Enrollment:</b>
								   <input required type='text' id='schoolenrollment'  autocomplete="false" name='schoolenrollment' class="form-control" value='${ school.detailsOther.schoolEnrollment }' />
			 				(PowerSchool overrides this)
			 				</div>
			 				<div class="col-lg-2 col-12">			
								<b>Start Time:</b>
								   <input required  type='text' id='starttime'  autocomplete="false" class="form-control datetimepicker-input starttime" data-toggle="datetimepicker" data-target="#starttime" name='starttime'  value='${ school.details.schoolStartTime }' />
							</div>
							<div class="col-lg-2 col-12">
								  <b>Dismissal Time:</b>
								   <input required type='text' id='endtime'  autocomplete="false" data-toggle="datetimepicker" data-target="#endtime" class="form-control datetimepicker-input endtime" name='endtime' value='${ school.details.schoolEndTime }' />
							</div>							
							<div class="col-lg-6 col-12">	  
								 <b><i class="fas fa-language"></i> Is French Immersion offered in your school?</b>							  
								    <label><input type='radio' name='frenchImmersion' value='true' ${ school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } /> Yes</label>
						            <label><input type='radio' name='frenchImmersion' value='false' ${ not school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } /> No</label>
							 <br/><br/>
								  <b><i class="fas fa-wheelchair"></i> Is your school Accessible?</b>							   							  
								    <label><input type='radio' name='accessible' value='true' ${ school.details.accessible ? "CHECKED=CHECKED" :"" } /> Yes </label>	
						            <label><input type='radio' name='accessible' value='false' ${ not school.details.accessible ? "CHECKED=CHECKED" :"" } /> No </label>
							</div></div>	   
							</div>
</div> 
	          
<br/><br/>	          

<!-- CONTACTS -->			
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">5. OTHER CONTACT INFORMATION</div>
						  	<div class="card-body" style="background-color:White;">
		School Administrators and Trustee can only be assigned at the district level. 
		Please contact <a href="mailto:geofftaylor@nlesd.ca">geofftaylor@nlesd.ca</a> for administrator updates. 
			<br/>
		<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-6 col-12">				  	 				
			<b>Secretary(s):</b>
				<input required type='text'  id='secretary'  autocomplete="false" name='secretary' class="form-control" value="${ school.details.secretaries }" />	             
		</div>
		<div class="col-lg-6 col-12">	
			<b>Guidance:</b>
			   <input type='text' id='schoolguidancesupport'  autocomplete="false" name='schoolguidancesupport' class="form-control" value="${ school.detailsOther.schoolGuidanceSupport }" />	             
		</div>
		</div>
		<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-6 col-12">	
			<b>Telephone:</b>
			    <input type='text' id='telephone' name='telephone' class="form-control"  autocomplete="false" value='${ school.telephone }' required/>	             
		</div>
		<div class="col-lg-6 col-12">	
			<b>Fax:</b>
			   	<input type='text' id='fax' name='fax' class="form-control"  autocomplete="false" value='${ school.fax }' />             
		</div>
		</div>
		<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-4 col-12">	
			<b>School Email:</b>
			   <input type='text' id='schoolemail' name='schoolemail'  autocomplete="false" class="form-control" value="${ school.detailsOther.schoolEmail }" />             
		</div>
		<div class="col-lg-8 col-12">	
			<b>School Web Site:</b>	
			    <input required type='text' id='website' name='website'  autocomplete="false" class="form-control" placeholder="http://www.yoursite.com" value="${ school.website }" />             
		</div>
		</div>
		
		<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-12 col-12">		
			<b>Twitter Address URL:</b>
			   <input type='text' id='twitter' class="form-control"  autocomplete="false" name='twitter' value='${ school.details.twitterUrl }' />	             
		</div>
		</div>	
		<br/><br/>
			
			<div class="card" style="background-color:#F0F8FF;">
						  <div class="card-header" style="font-weight:bold;color:Black;font-size:12px;">Twitter Embed Code: </div>
						  <div class="card-body">
						  <img src="../includes/img/twitter.png" border=0 style="max-height:40px;padding-right:5px;" align="left"/> 
						  <div style="float:right;"><a class="btn btn-sm btn-info" href="https://publish.twitter.com/#" target="_blank">Get Twitter EMBED CODE</a></div>
			   			<div style="clear:both;"></div>
			   			<c:choose>
						  				<c:when test="${ not empty school.detailsOther.twitterEmbed }">				  				
							  					${ school.detailsOther.twitterEmbed }
								  		</c:when>
						  				<c:otherwise>				  										  				
								  				<div class="alert alert-danger" style="text-align:center;margin-top:5px;margin-bottom:10px;padding:5px;">
								  				No Twitter Embed Code. Enter your Twitter web address on the Get EMBED Code site and generate the timeline code and paste below.
								  				</div>
								  		</c:otherwise>	
						</c:choose>
			   		 <textarea id='twitterembed' autocomplete="false" style="width:100%;max-height:300px;height:200px;padding-bottom:5px;padding-top:10px;" name='twitterembed'>${ school.detailsOther.twitterEmbed }</textarea>
			   				
			   			
			   			</div> 	             
			</div>
			
			<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-6 col-12">	
			<b>Facebook Address URL:</b>
		<input type='text' id='facebook' class="form-control"  autocomplete="false" name='facebook' value='${ school.details.facebookUrl }' />	             
		</div>
		<div class="col-lg-6 col-12">			
			<b>Youtube Channel Address URL:</b>
			   <input type='text' id='youtube' class="form-control"  autocomplete="false" name='youtube' value='${ school.details.youtubeUrl }' />	             
		</div>
		</div>
		<div class="row container-fluid" style="padding-top:5px;">
		<div class="col-lg-12 col-12">
		<b>Google Map Address URL:</b>		
			   <input type='text' id='googleMapUrl' class="form-control"  autocomplete="false" name='googleMapUrl' value='${ school.details.googleMapUrl }' />	             
		</div>
		</div>	
		<br/><br/>
			<div class="card" style="background-color:#F0F8FF;">
						  	<div class="card-header" style="font-weight:bold;color:Black;font-size:12px;">Google Map Embed Code:</div>
						  	<div class="card-body" style="background-color:white;">
						  	<img src="../includes/img/google.png" border=0 style="max-height:40px;padding-right:5px;" align="left">
						  	This is to embed a google map location on your school profile page. Embed code is available on <b>
						  	<a href="https://maps.google.com/" target="_blank">Google Maps</a></b> 
						  	where you get your Google Maps link. Paste the GoogleMaps embed code that you generate in the space below.
			   				
			   				<c:choose>
						  				<c:when test="${ not empty school.detailsOther.googleMapEmbed }">						  									  				
								  				${ school.detailsOther.googleMapEmbed }								  					
						  				</c:when>		
						  				<c:otherwise>	
						  				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">					  				
								  				 Google Map Location not yet provided. If you have no google map, please use Google maps to locate your school and get the Google Map embed code for your school location and paste in the box at right.
								  		</div>		 								  					
						  				</c:otherwise>	
						  				
						  	</c:choose>
						  <br/>
			   				<textarea id='googlemapembed'  autocomplete="false" style="width:100%;height:200px;padding-bottom:5px;padding-top:10px;" name='googlemapembed'>${ school.detailsOther.googleMapEmbed }</textarea>
			   				</div>
			 	</div> 	             
			
			</div>												               
</div>	               	            


	                
        <br/><br/>


<!-- School Development Report Document -->	               
				 	<div class="card bg-success">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">6. SCHOOL DEVELOPMENT REPORT</div>
						  	<div class="card-body" style="background-color:#FFFFFF;"> 
						  	This document should be updated every year with the most current plan. 					
				               <c:choose>
				              		<c:when test="${ school.details.schoolReportFilename ne null }">
					              		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">          
					                      	SUCCESS: You already have a School Report on file.<br/> 
					                      	To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in PDF format. 
					                      
					              		</div>
				              		</c:when>
				              		<c:otherwise>
						              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
						              			<b>No School Development Report documentation currently on file for ${ school.schoolName }.</b><br/>
						              				<br/>To add report file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in PDF format.
					                   	</div>
				              		</c:otherwise>
				              	</c:choose>	
				              		<input type='file' class="form-control" name='schoolReportFile' /> 
				              		<c:if test="${ school.details.schoolReportFilename ne null }">
				              		Current File: <a href="https://www.nlesd.ca/schools/doc/${ school.details.schoolReportFilename }" target="_blank">${ school.details.schoolReportFilename }</a>
				              		</c:if>
							</div>
	               </div>   
	                           
<br/><br/>	       

<!-- SCHOOL OPENING INFORMATION -->	              
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">7. SCHOOL OPENING INFORMATION (Sept)</div>
						  	<div class="card-body" style="background-color:#FFFFFF;">  
						  	Please enter any important information relevant to your school opening in September.      
						  	<br/><br/>
						  	<b>General Information:</b>   
	          					<textarea id='schoolOpening'   autocomplete="false" class="form-control" name='schoolOpening'>${ school.details.schoolOpening }</textarea>
	

								<!-- Check if school is a Elementary/Primary -->
								<c:if test="${ school.lowestGrade.name eq 'Kindergarten'}">
								<br/> <br/>                   
											<!-- KINDERSTART INFORMATION -->	              
			
									  	 	<b>Kinderstart Times/Dates and Information:</b>   
				          					<textarea id='kinderstartTimes'  autocomplete="false"  class="form-control" name='kinderstartTimes'>${ school.details.kinderstartTimes }</textarea>
				               			  	<br/><br/>
				               			  	<b>Kindergarten Times and Information:</b>  
				          					<textarea id='kindergartenTimes'  autocomplete="false"  class="form-control" name='kindergartenTimes'>${ school.details.kindergartenTimes }</textarea>
				               			
								</c:if>		               			
				               	</div>
</div>	                    

 
<br/><br/>

<!-- STREAM FEEDER INFORMATION -->	              
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">8. FEEDER/STREAM INFORMATION</div>
						  	<div class="card-body" style="background-color:#FFFFFF;">  
						  	Enter details on schools feeding into and/or feeding from your school, if applicable.     <br/>    
	          					<textarea id='streamnotes' autocomplete="false" class="form-control" name='streamnotes'>${ school.schoolStreams.streamNotes }</textarea>
	               			</div>
</div>	

<br/><br/>
	                     
<!-- OTHER INFORMATION -->	              
<div class="card bg-primary">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">9. OTHER SCHOOL INFORMATION/NOTICES</div>
						  	<div class="card-body" style="background-color:#FFFFFF;">           
	          					<textarea id='otherInfo' autocomplete="false" class="form-control" name='otherInfo'>${ school.details.otherInfo }</textarea>
	               			</div>
</div>	                   
                    	                      	
<br/><br/>
	                    
<!-- IMPORTANT NOTICE -->	              
<div class="card bg-danger">
						  	<div class="card-header" style="font-weight:bold;color:White;font-size:16px;">10. IMPORTANT NOICE</div>
						  	<div class="card-body" style="background-color:#ffe6ea;"> 	                     
	                      This section will display an important notice atop your profile page in red as the first thing a visitor sees on the page. 
	                      <b>Only use this if there is an important issue or notice you need displayed for your school</b>. (To remove a notice, just clear the text.)
	                      <br/><br/>	                      	                      	
	                      <textarea id='importantnotice'  autocomplete="false"  class="form-control" name='importantnotice'>${ school.detailsOther.importantNotice }</textarea>
	                                     
	               </div>
</div>
	               
<br/><br/>	               
			
<!-- NOT IN USE and HIDDEN from school admins, but accessable to System Administrator -->	
<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">	
	
<div class="card bg-danger">	
 <div class="card-header"  style="font-weight:bold;color:White;font-size:16px;">11. SYSTEM ADMINISTRATOR ONLY ITEMS</div>
						  	<div class="card-body" style="background-color:#fffae6;"> 
								<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-8 col-12">
								<b>School Name:</b>
								   <input required type='text' id='schoolName' name='schoolName' autocomplete="false" class="form-control" value="${ school.schoolName }" />
								</div>
								<div class="col-lg-4 col-12">
								<b>Department ID:</b>
								    <input required type='text' id='deptId' name='deptId' class="form-control" value='${ school.schoolDeptID }' />
								</div>
								</div>
								<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-4 col-12">
								 <b>Lowest Grade Level:</b>
								   <sch:GradesDDL id='lgrade'  cls="form-control" value='${ school.lowestGrade }' />
								</div>
								<div class="col-lg-4 col-12">
								<b>Highest Grade Level:</b>
								   <sch:GradesDDL id='hgrade' cls="form-control" value='${ school.highestGrade }' />
								</div>
								<div class="col-lg-4 col-12">
			 					<b>Does this school have Surveillance Cameras?</b><br/>							   							  
								    <label><input type='radio' name='surcameras' value='true' ${ school.detailsOther.surveillanceCamera? "CHECKED=CHECKED" :"" } /> Yes </label>	
						            <label><input type='radio' name='surcameras' value='false' ${ not school.detailsOther.surveillanceCamera ? "CHECKED=CHECKED" :"" } /> No </label>
								</div>
								</div>
			 					<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-4 col-12">
								<b>School Region:</b>
								    <sch:SchoolZonesDDL id="zoneId" dummy="true" cls="form-control" value='${ school.zone }'  />
								</div>
								<div class="col-lg-4 col-12">
								<b>School Regional Zone:</b>
								    <sch:RegionsDDL id="regionId" cls="form-control" value='${ school.region.id }' />
								</div>
								<div class="col-lg-4 col-12" style="display:none;">
								<b>Trustee/Electorial Zone:</b>				
				    					<select id='electorialZone' name='electorialZone' class="form-control">
				    					<option value="0">---- Please Select (Required) ----</option>	
				    					<option value='1' ${ school.details.electorialZone eq "1" ? "SELECTED" : "" }>Raymond Bennett (1)</option> 
					                    <option value='2' ${ school.details.electorialZone eq "2" ? "SELECTED" : "" }>Goronwy Price (2)</option> 
					                    <option value='3' ${ school.details.electorialZone eq "3" ? "SELECTED" : "" }>Guy Elliott (3)</option> 
					                    <option value='4' ${ school.details.electorialZone eq "4" ? "SELECTED" : "" }>Scott Burden (4)</option> 
					                    <option value='5' ${ school.details.electorialZone eq "5" ? "SELECTED" : "" }>Pamela Gill (5)</option> 
					                    <option value='6' ${ school.details.electorialZone eq "6" ? "SELECTED" : "" }>Wayne Lee (6)</option> 
					                    <option value='7' ${ school.details.electorialZone eq "7" ? "SELECTED" : "" }>Thomas Kendell (7)</option> 
					                    <option value='8' ${ school.details.electorialZone eq "8" ? "SELECTED" : "" }>John George (8)</option> 
					                    <option value='9' ${ school.details.electorialZone eq "9" ? "SELECTED" : "" }>Winston Carter (9)</option> 
					                    <option value='10' ${ school.details.electorialZone eq "10" ? "SELECTED" : "" }>Eric Ayers (10)</option>
					                    <option value='11' ${ school.details.electorialZone eq "11" ? "SELECTED" : "" }>Jean Butt (11)</option> 
					                    <option value='12' ${ school.details.electorialZone eq "12" ? "SELECTED" : "" }>Vacant (12)</option> 
					                    <option value='13' ${ school.details.electorialZone eq "13" ? "SELECTED" : "" }>Kevin Ryan (13)</option> 
					                    <option value='14' ${ school.details.electorialZone eq "14" ? "SELECTED" : "" }>Vacant (14)</option> 
					                    <option value='15' ${ school.details.electorialZone eq "15" ? "SELECTED" : "" }>Vacant  (15)</option> 
					                    <option value='16' ${ school.details.electorialZone eq "16" ? "SELECTED" : "" }>Peter Whittle (16)</option> 
					                    <option value='17' ${ school.details.electorialZone eq "17" ? "SELECTED" : "" }>Steve Tessier (17)</option> 
					                    <option value='0' ${ school.details.electorialZone eq "0" ? "SELECTED" : "" }>Not Assigned</option>
				    					</select>    				
								</div>
								</div>
								
								 
							   	<!-- NOT CURRENTLY IN USE -->					      
							    <div style="display:none;"> 
					                  
					                     
					                    <input type='text' id='instagramlink' class="form-control" name='instagramlink' value='${ school.detailsOther.instagramLink }' />
							     		<input type='text' id='twitterfeedwidgetid' name='twitterfeedwidgetid' value='${ school.detailsOther.twitterFeedWidgetId }' />
	                    		 		<input type='text' id='twitterfeedscreenname' name='twitterfeedscreenname' value='${ school.detailsOther.twitterFeedScreenName }'  /> 
					                     
					                    <select id="schoolstreamsenglish" name="schoolstreamsenglish" multiple="multiple">
												<c:forEach var="item" items="${schoollistenglish}">
													<option value="${item.schoolId}" ${item.selected} >${item.schoolName}</option>
				    							</c:forEach>
										</select>
										<select id="schoolstreamsfrench" name="schoolstreamsfrench" multiple="multiple">
												<c:forEach var="item" items="${schoollistfrench}">
				    								<option value="${item.schoolId}" ${item.selected}>${item.schoolName}</option>
												</c:forEach>
											</select>
					                 
					            </div> 
							    
						<hr>
							     
						<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-6 col-12">	     
						<b>School Bus Route Documentation:</b><br/>					
								<c:choose>
					              	<c:when test="${ school.details.busRoutesFilename ne null }">
					              	<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>SUCCESS:</b> School already has Bus Documentation updated. 
				                   	</div>
					              	</c:when>
					              	<c:otherwise>
					              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>NOTICE:</b> No bus route documentation currently on file for ${ school.schoolName }.
				                   	</div>
					              	</c:otherwise>
					              	</c:choose>	
					              	Busing staff in each region typically update this via the Bus Route Updating App in Member Services.
					              	</div>              	
					              	<div class="col-lg-6 col-12">	              	
					              	Select the PDF file to upload by clicking on <i>Choose File</i> below if necessary.					              	               
				                   	<input type='file' class="form-control" name='busRoutesFile' />
				                   	<c:if test="${ school.details.busRoutesFilename ne null }">
				                   	<b>Current File:</b> <a href="https://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }" target="_blank">${ school.details.busRoutesFilename }</a>
				                   </c:if>
				                   	</div>
				                   	</div>
				                   	
								<hr>
								
			 					<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-6 col-12">	  
			 						<b>Air Quality Report</b>
			 					<c:choose>
					              	<c:when test="${ school.details.catchmentAreaFilename ne null }">
					              	<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>SUCCESS:</b> School has Air Quality report Posted.
				                   	</div>
					              	</c:when>
					              	<c:otherwise>
					              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>NOTICE: </b>No Air Quality Report currently on file for ${ school.schoolName }.
				                   	</div>
					              	</c:otherwise>
					              	</c:choose>
					              	</div>	              	
					              	<div class="col-lg-6 col-12">	 	              	
					              	Select the PDF file to upload by clicking on <i>Choose File</i> below if necessary.					              	              
				                   	<input type='file' class="form-control" name='catchmentAreaFile' />
									<c:if test="${ school.details.catchmentAreaFilename ne null }">
									<b>Current File:  <a href="https://www.nlesd.ca/schools/doc/${ school.details.catchmentAreaFilename }" target="_blank">${ school.details.catchmentAreaFilename} </a></b>
									</c:if>
									</div>
									</div>
									
								<hr>
								
								<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-12 col-12">	
								<b>Catchment Area Details</b><br/>
									This is to link to the catchment area map and/or embed the catchment area map, if available, for busing. Currently NOT part of the busing route app.<br/>
			 						<input type='text' id='catchmentMapUrl'  name='catchmentMapUrl' class="form-control" value='${ school.details.catchmentMapUrl }' />	
								</div>
							</div>
							
							
						<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-12 col-12">	
									<b>Catchment Map Embed Code:</b><br/>
						
						  	This is to embed the catchment map location on the school profile page if available. 
			   				
			   				<div style="float:left;width:50%;min-width:300px;padding-right:5px;">
			   				<c:choose>
						  				<c:when test="${ not empty school.detailsOther.schoolCatchmentEmbed }">						  									  				
								  				${ school.detailsOther.schoolCatchmentEmbed }								  					
						  				</c:when>		
						  				<c:otherwise>	
						  				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">					  				
								  				 Catchment Map not yet provided. Provide the embed code for your school catchment area location and paste in the box at right.
								  		</div>		 								  					
						  				</c:otherwise>	
						  				
						  	</c:choose>
						  	</div>			   				
			   				<div style="float:left;width:50%;min-width:300px;">
			   				<textarea autocomplete="false" id='schoolcatchmentembed' style="width:90%;height:300px;padding-bottom:5px;padding-top:10px;" name='schoolcatchmentembed'>${ school.detailsOther.schoolCatchmentEmbed }</textarea></div>
			   				</div>	             
						</div>	
							
						
												
						
							
			 

</div>	                  
</div>	                  
	                  
	 </esd:SecurityAccessRequired> 
	 
	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL,WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY">	 
	 <!-- CUREENTLY NOT IN USE FOR NON-ADMINS - KEEP HERE TO AUTOFILL DUE TO PERMISSIONS -->			 
	                <table style="display:none;"> 
	                   	<tr><td>
	                      <input type='text' id='schoolName' name='schoolName' autocomplete="false" class="form-control" value="${ school.schoolName }" />
								
								   <sch:GradesDDL id='lgrade' style="height:30px;width:100%;" value='${ school.lowestGrade }' />
								
								   <sch:GradesDDL id='hgrade' style="height:30px;width:100%;" value='${ school.highestGrade }' />
								
								    <input type='text' id='deptId' name='deptId' class="form-control" value='${ school.schoolDeptID }' />
								
								    <sch:SchoolZonesDDL id="zoneId" dummy="true" style="height:30px;width:100%;" value='${ school.zone }'  />								
								    <sch:RegionsDDL id="regionId" style="height:30px;width:100%;" value='${ school.region.id }' />								
								   	<label><input type='radio' name='surcameras' value='true' ${ school.detailsOther.surveillanceCamera? "CHECKED=CHECKED" :"" } /> Yes </label>	
						            <label><input type='radio' name='surcameras' value='false' ${ not school.detailsOther.surveillanceCamera ? "CHECKED=CHECKED" :"" } /> No </label>
								  
								
								<sch:SchoolZonesDDL id="zoneId" dummy="true" value='${ school.zone }'  />
	                    <sch:RegionsDDL id="regionId" value='${ school.region.id }' />
	                    <input type='text' id='electorialZone' name='electorialZone' style="width:100%;max-width:100px;" value="${ school.details.electorialZone }" />
								
							
								
				                   	<input type='file' name='busRoutesFile' />
								
				                   	<input type='file' name='catchmentAreaFile' />
				                   	
				         <input type='text' id='catchmentMapUrl'  name='catchmentMapUrl' class="form-control" value='${ school.details.catchmentMapUrl }' />	
								
			   			<textarea autocomplete="false" id='schoolcatchmentembed' style="width:90%;height:300px;padding-bottom:5px;padding-top:10px;" name='schoolcatchmentembed'>${ school.detailsOther.schoolCatchmentEmbed }</textarea></div>
			   				                      	
	                    <input type='text' id='twitterfeedwidgetid' name='twitterfeedwidgetid' value='${ school.detailsOther.twitterFeedWidgetId }' />
	                    <input type='text' id='twitterfeedscreenname' name='twitterfeedscreenname' value='${ school.detailsOther.twitterFeedScreenName }'  />
	                     <input type='text' id='instagramlink' class="form-control" name='instagramlink' value='${ school.detailsOther.instagramLink }' />
	                    <select id="schoolstreamsenglish" name="schoolstreamsenglish" multiple="multiple">
								<c:forEach var="item" items="${schoollistenglish}">
									<option value="${item.schoolId}" ${item.selected} >${item.schoolName}</option>
    							</c:forEach>
						</select>
						<select id="schoolstreamsfrench" name="schoolstreamsfrench" multiple="multiple">
								<c:forEach var="item" items="${schoollistfrench}">
    								<option value="${item.schoolId}" ${item.selected}>${item.schoolName}</option>
								</c:forEach>
							</select>
	                      </td>
	                    </tr>	                    	                    	                    
	                   
	                  </table>
	

								 
		</esd:SecurityAccessRequired>	 
          

	                 
	       <br/><br/>
	               
	               <div align="center">	
	               <button type="submit" class="btn btn-success btn-sm" onclick="loadingData();">Save Changes</button> &nbsp;<a href="school_profile.jsp" onclick="loadingData();" class="btn btn-sm btn-danger" style="color:white;">Cancel</a>
					</div>            
                  </form>
    
</div>
<script>

$( "#googlemapembed,#twitterembed,#schoolcatchmentembed" ).keypress(function(evt) {
	
	var clipboardKeys = {
		winInsert : 45,
		winDelete : 46,
		SelectAll : 97,
		macCopy : 99,
		macPaste : 118,
		macCut : 120,
		redo : 121,	
		undo : 122
	}
	
	var charCode = evt.which;

	if (
		evt.ctrlKey && charCode == clipboardKeys.redo ||	
		evt.ctrlKey && charCode == clipboardKeys.undo ||		
		evt.ctrlKey && charCode == clipboardKeys.macCut ||		
		evt.ctrlKey && charCode == clipboardKeys.macPaste ||		
		evt.ctrlKey && charCode == clipboardKeys.macCopy ||		
		evt.shiftKey && evt.keyCode == clipboardKeys.winInsert ||	
		evt.shiftKey && evt.keyCode == clipboardKeys.winDelete ||	
		evt.ctrlKey && evt.keyCode == clipboardKeys.winInsert  ||	
		evt.ctrlKey && charCode == clipboardKeys.SelectAll		
		){ return 0; }
	
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	key = String.fromCharCode(key);
	var regex = /[]|\./;
	if(!regex.test(key)) {
		theEvent.returnValue = false;
		theEvent.preventDefault();
	}
});

</script>

 <script>
    
    $('document').ready(function(){	
    	
	
	$('.starttime').datetimepicker({		 
	 		format: 'LT',
			date: moment('${ school.details.schoolStartTime ne null?school.details.schoolStartTime:'' }','LT')
 			});
	$('.endtime').datetimepicker({		 
 		format: 'LT',
		date: moment('${ school.details.schoolEndTime ne null?school.details.schoolEndTime:'' }','LT')
			});	
		
	var pageWordCountConf = {
    	    showParagraphs: true,
    	    showWordCount: true,
    	    showCharCount: true,
    	    countSpacesAsChars: true,
    	    countHTML: true,
    	    maxWordCount: -1,
    	    maxCharCount: 3000,
    	}

    CKEDITOR.replace( 'description',{wordcount: pageWordCountConf,toolbar : 'Basic'} );    
    CKEDITOR.replace( 'schoolOpening',{toolbar : 'Basic'}  );
    CKEDITOR.replace( 'importantnotice',{toolbar : 'Basic'}  );
    CKEDITOR.replace( 'otherInfo',{toolbar : 'Basic'}  );    
    currentLowestGrade = "${school.lowestGrade.name}";   
    if(currentLowestGrade === "Kindergarten") {
    	 CKEDITOR.replace( 'kindergartenTimes',{toolbar : 'Basic'}  );
    	 CKEDITOR.replace( 'kinderstartTimes',{toolbar : 'Basic'}  );
    };
   
    
  
    CKEDITOR.replace( 'streamnotes',{toolbar : 'Basic'}  );    
	
	
	
	
	
	
	});
    </script>


  </body>

</html>	
			

			