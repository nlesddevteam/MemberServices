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
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
				
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
		

	
	<style>
	
	.schoollistDocLinkContainer {float:left;width:160px;text-align:center;}
.schoollistHeader {color: #007F01;font-weight: bold;}
.schoollist .row .email a { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:visited { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:hover {	color: #FF0000;	 text-decoration: underline;}
.schoollist .row .email a:active { color: #FF0000; text-decoration: none;}
.schoollist {display:table;  width:100%;font-size:11px;}
.schoollist .table {display:table;}
.schoollist .rowCaption {caption-side: top; display: table-caption;width:100%;text-align:left; font-weight:bold;color:#007F01;margin-bottom:3px;}
.schoollist .row { display:table-row; width:100%;}
.schoollist .column { display: table-cell;border-bottom:1px solid grey;padding:2px;}
.schoollist .row .header { background-color: #000000; text-align:left; color:white; font-weight:bold; }
.schoollist .row .SchoolName {width:35%;vertical-align: text-top;}
.schoollist .row .SchoolRegion { width:25%;border-left:1px solid grey;vertical-align: text-top;}
.schoollist .row .SchoolLocation { width:15%;border-left:1px solid grey;vertical-align: text-top;}
.schoollist .row .SchoolBusDoc { width:10%;border-left:1px solid grey;vertical-align: text-top;text-align:center;}
.schoollist .row .SchoolOptions { width:10%;border-left:1px solid grey;vertical-align: text-top;text-align:center;}

div.schoollist > div:nth-of-type(odd) {background: #FDF5E6;}

.itemHeader {font-size:16px;color:#1F4279;font-weight:bold;}	
.regionDefault {background-color: #1F4279;}
.region1 {background-color:rgba(191, 0, 0, 0.1);}
.region1a {border-left:10px solid rgba(191, 0, 0, 0.3);}
.region1solid {background-color:rgba(191, 0, 0, 1);}
.region1half {background-color:rgba(191, 0, 0, 0.5);}
.region2 {background-color:rgba(0, 191, 0, 0.1);}
.region2a {border-left:10px solid rgba(0, 191, 0, 0.3);}
.region2solid {background-color:rgba(0, 191, 0, 1);}
.region2half {background-color:rgba(0, 191, 0, 0.5);}
.region3 {background-color:rgba(255, 132, 0, 0.1);}
.region3solid {background-color:rgba(255, 132, 0, 1);}
.region3half {background-color:rgba(255, 132, 0, 0.5);}
.region3a {border-left:10px solid rgba(255, 132, 0, 0.3);}
.region4 {background-color:rgba(127, 130, 255, 0.1);}
.region4a {border-left:10px solid rgba(127, 130, 255, 0.3);}
.region4solid {background-color:rgba(127, 130, 255,1);}
.region4half {background-color:rgba(127, 130, 255, 0.5);}	

.ui-timepicker-wrapper {
	overflow-y: auto;
	max-height: 150px;
	width: 6.5em;
	background: #fff;
	border: 1px solid #ddd;
	-webkit-box-shadow:0 5px 10px rgba(0,0,0,0.2);
	-moz-box-shadow:0 5px 10px rgba(0,0,0,0.2);
	box-shadow:0 5px 10px rgba(0,0,0,0.2);
	outline: none;
	z-index: 10001;
	margin: 0;
}

.ui-timepicker-wrapper.ui-timepicker-with-duration {
	width: 13em;
}

.ui-timepicker-wrapper.ui-timepicker-with-duration.ui-timepicker-step-30,
.ui-timepicker-wrapper.ui-timepicker-with-duration.ui-timepicker-step-60 {
	width: 11em;
}

.ui-timepicker-list {
	margin: 0;
	padding: 0;
	list-style: none;
}

.ui-timepicker-duration {
	margin-left: 5px; color: #888;
}

.ui-timepicker-list:hover .ui-timepicker-duration {
	color: #888;
}

.ui-timepicker-list li {
	padding: 3px 0 3px 5px;
	cursor: pointer;
	white-space: nowrap;
	color: #000;
	list-style: none;
	margin: 0;
}

.ui-timepicker-list:hover .ui-timepicker-selected {
	background: #fff; color: #000;
}

li.ui-timepicker-selected,
.ui-timepicker-list li:hover,
.ui-timepicker-list .ui-timepicker-selected:hover {
	background: #1980EC; color: #fff;
}

li.ui-timepicker-selected .ui-timepicker-duration,
.ui-timepicker-list li:hover .ui-timepicker-duration {
	color: #ccc;
}

.ui-timepicker-list li.ui-timepicker-disabled,
.ui-timepicker-list li.ui-timepicker-disabled:hover,
.ui-timepicker-list li.ui-timepicker-selected.ui-timepicker-disabled {
	color: #888;
	cursor: default;
}

.ui-timepicker-list li.ui-timepicker-disabled:hover,
.ui-timepicker-list li.ui-timepicker-selected.ui-timepicker-disabled {
	background: #f2f2f2;
}



	</style>
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
	    	
	    	$('#directoryListSchool tr:odd td').css({'background-color' : '#E5F2FF'})
	    	
	    });
	    
	    function getZoneRegions(zoneid){
	    	
	    	$.ajax(
     			{
     				type: "POST",  
     				url: "/MemberServices/MemberAdmin/Apps/Schools/getZoneRegions.html",
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
     				url: "/MemberServices/MemberAdmin/Apps/Schools/getZoneRegionsSchools.html",
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
     				url: "/MemberServices/MemberAdmin/Apps/Schools/getZoneRegionsSchools.html",
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
<script src="../includes/ckeditor/ckeditor.js"></script>

<script type="text/javascript" src="../includes/js/jquery.timepicker.js"></script>	
			
			<script>
			$(document).ready(function(){    
        		//clear spinner on load    			
    			$('#starttime').timepicker();
    			$('#endtime').timepicker();      
    			
    			
   				
   				
    			
    			
    			
    			
			});
			</script>
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="/MemberServices/WebUpdateSystem/SchoolProfiles/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">${ school.schoolName } Website School Directory Profile Information</div>
                      <div class="pageBody">
                     
                      <p>
                      
									<c:if test="${ msg ne null }">  
                  
                  <div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">${ msg } </div>   
                                 
                  </c:if>  
									
									<p>
				<div class="row">	




<form action='updateSchool.html' method='POST' ENCTYPE="multipart/form-data">
                  	<input type='hidden' name='schoolId' id='schoolId' value='${school.schoolID }' />	               
	               <input type='hidden' name='provinceState' value='NL' /> 
	               	You are updating the  <b>${ school.schoolName }</b> public profile documentation. 
	               	Not all items that display on your school profile page are editable here. <b>You CANNOT change the name of the school, or the school administrators as that is assigned at the district level.</b>
	               	You may have to contact <a href="mailto:mssupport@nlesd.ca">mssupport@nlesd.ca</a> for any other updates you may require. 
	               	
	               	<div class="alert alert-warning" style="margin-top:10px;margin-bottom:5px;padding:5px;text-align:center;">
	               	<b>NOTICE:</b> ALWAYS make sure this profile page is updated for your school, as this is the first link from our school directory people may access re your school.
	            	</div>
	            
	            When you have your changes made, press <b>Save Changes</b> at the bottom of this page.
	                       
	            
	  <br/><br/>
	  
	  <div align="center">	
	               <a href="school_profile.jsp" class="btn btn-sm btn-danger" style="color:white;">Back to School List</a>
					</div>	            <br/>
<!-- ADDRESS -->	
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">1. SCHOOL ADDRESS</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;">	 
						  	<b>School Name:</b> ${ school.schoolName }  <br/><br/>           
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Street Address:</span>
			   <input type='text' id='address1' class="form-control" name='address1'  autocomplete="false" value="${ school.address1 }" /> 
	          	             
			</div>	               
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Postal Address:</span>
			    <input type='text' id='address2' class="form-control" name='address2'  autocomplete="false" value="${ school.address2 }" />	             
			</div>	
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Town/City:</span>
			   <input type='text' id='citytown' class="form-control" name='citytown'   autocomplete="false" value="${ school.townCity }" />	             
			</div>	
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Postal Code:</span>
			   <input type='text' id='postalcode' class="form-control" name='postalcode'   autocomplete="false" value="${ school.postalZipCode }" />	             
			</div>
			</div>
</div>			
<!-- CONTACTS -->			
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">2. OTHER CONTACT INFORMATION</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;"> 				
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Telephone:</span>
			    <input type='text' id='telephone' name='telephone' class="form-control"  autocomplete="false" value='${ school.telephone }' />	             
			</div>	
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Fax:</span>
			   	<input type='text' id='fax' name='fax' class="form-control"  autocomplete="false" value='${ school.fax }' />             
			</div>	
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">School Email:</span>	
			   <input type='text' id='schoolemail' name='schoolemail'  autocomplete="false" class="form-control" value="${ school.detailsOther.schoolEmail }" />             
			</div>
			
				
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">School Website:</span>	
			    <input type='text' id='website' name='website'  autocomplete="false" class="form-control" placeholder="http://www.yoursite.com" value="${ school.website }" />             
			</div>
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Twitter Address URL:</span>
			   <input type='text' id='twitter' class="form-control"  autocomplete="false" name='twitter' value='${ school.details.twitterUrl }' />	             
			</div>
			<div class="panel panel-default">
						  <div class="panel-heading">Twitter Embed Code:</div>
						  <div class="panel-body"  style="background-color:#e6faff;"><img src="img/twitter.png" border=0 style="max-height:40px;padding-right:5px;" align="left">
						  This is to embed your Twitter feed on your school profile page. Use <a href="https://publish.twitter.com/#" target="_blank">this link to get the embed code</a>. (Enter your Twitter web address on that site from above and generate the timeline code and paste below.)
			   			<div style="clear:both;"></div>
			   			
			   			<div style="float:left;width:45%;min-width:300px;max-height:400px;padding-right:5px;">
			   			<c:choose>
						  				<c:when test="${ not empty school.detailsOther.twitterEmbed }">				  				
							  					
								  				${ school.detailsOther.twitterEmbed }
								  		</c:when>
						  				<c:otherwise>				  										  				
								  				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">
								  				School has no active Twitter Account feed published. If you have a twitter account, please goto the <b><a href="https://publish.twitter.com/#" target="_blank">Twitter Publish Site</a></b> and generate your timeline code and paste in the area at right. If you do not have a twitter account, we suggest you think about getting one for your school.
								  				</div>
								  		</c:otherwise>	
						</c:choose>
			   			</div>
			   			<div style="float:left;width:50%;min-width:300px;min-height:300px;">
			   			
			   				<textarea id='twitterembed' autocomplete="false" style="width:100%;max-height:400px;height:300px;padding-bottom:5px;padding-top:10px;" name='twitterembed'>${ school.detailsOther.twitterEmbed }</textarea>
			   				
			   			</div>
			   			
			   			</div> 	             
			</div>
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Facebook Address URL:</span>
			   <input type='text' id='facebook' class="form-control"  autocomplete="false" name='facebook' value='${ school.details.facebookUrl }' />	             
			</div>
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Youtube Channel Address URL:</span>
			   <input type='text' id='youtube' class="form-control"  autocomplete="false" name='youtube' value='${ school.details.youtubeUrl }' />	             
			</div>
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Google Map Address URL:</span>
			   <input type='text' id='googleMapUrl' class="form-control"  autocomplete="false" name='googleMapUrl' value='${ school.details.googleMapUrl }' />	             
			</div>
			<div class="panel panel-default" >
						  	<div class="panel-heading">Google Map Embed Code:</div>
						  	<div class="panel-body"  style="background-color:#ecffe6;"><img src="img/google.png" border=0 style="max-height:40px;padding-right:5px;" align="left">
						  	This is to embed a google map location on your school profile page. Embed code is available on <b><a href="https://maps.google.com/" target="_blank">Google Maps</a></b> where you get your Google Maps link. Paste the GoogleMaps embed code that you generate in the space below.
			   				<div style="clear:both;padding-bottom:5px;"></div>
			   				<div style="float:left;width:45%;min-width:300px;padding-right:5px;">
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
						  	</div>			   				
			   				<div style="float:left;width:50%;min-width:300px;">
			   				<textarea autocomplete="false" id='googlemapembed'  autocomplete="false" style="width:90%;height:300px;padding-bottom:5px;padding-top:10px;" name='googlemapembed'>${ school.detailsOther.googleMapEmbed }</textarea></div>
			   				</div> 	             
			</div>
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Secretary(s):</span>
				<input type='text'  id='secretary'  autocomplete="false" name='secretary' class="form-control" value="${ school.details.secretaries }" />	             
			</div>	
			<div class="input-group" style="padding-bottom:5px;">
			   <span class="input-group-addon">Guidance:</span>
			   <input type='text' id='schoolguidancesupport'  autocomplete="false" name='schoolguidancesupport' class="form-control" value="${ school.detailsOther.schoolGuidanceSupport }" />	             
			</div>
			
			
			
			
			<br/>School Administrators and Trustee can only be assigned at the district level. Please contact <a href="mailto:mssupport@nlesd.ca">mssupport@nlesd.ca</a> for administrator updates. 
			
			</div>												               
</div>	               	            

<!-- SCHOOL MISSION DESCRIPTION -->	               
				 	<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">3. MISSION / DESCRIPTION</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;">
						  	Please add some text describing your school environment and/or mission/values.<br/>
   							<textarea id='description'  autocomplete="false" name='description' class="form-control">${ school.detailsOther.description }</textarea>
							</div>
					</div>
	                                  	
	              <!-- Count empty/blank fields, and highlight saying fields not complete and to update. Also change Header for Tender site, Bus Route Site, and Profile Update sites-->
	       
<!-- BUILDING PHOTO -->	
	       
			       <div class="panel panel-info">
						  <div class="panel-heading" style="font-weight:bold;">4. BUILDING PHOTO</div>
						  <div class="panel-body" style="background-color:#F8F8FF;">
						  We ask that you always provide an updated school building photo when possible. Current photo (if any) is shown below.<br/>
						  								<c:choose>
										              		<c:when test="${ school.details.schoolPhotoFilename ne null }">	                      		
							                      				<img src="https://www.nlesd.ca/schools/img/${ school.details.schoolPhotoFilename }" style="max-height:100px;">
							                      				<br/>To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</c:when>
							                				<c:otherwise>
							                				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">
							                				No school photo currently on file for your school. Please upload a outside photo of the school building/entrance.
							                				<br/>To add a photo, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</div>
							                				</c:otherwise>
							                			</c:choose>	
							                    			<p><input type='file' style="width:100%;max-width:500px;" name='schoolPhotoFile' />
						</div>
					</div>
	       
<!-- BUILDING PHOTO -->	
	       
			       <div class="panel panel-info">
						  <div class="panel-heading" style="font-weight:bold;">5. SCHOOL CREST/LOGO</div>
						  <div class="panel-body" style="background-color:#F8F8FF;">
						 	Please provide your school crest or logo. Current file (if any) is shown below.<br/>	              
	           
	           											<c:choose>
										              		<c:when test="${ school.details.schoolCrestFilename ne null }">
										              				                      		
							                      				<img src="https://www.nlesd.ca/schools/img/${ school.details.schoolCrestFilename }" style="max-height:100px;">
							                      				<br/>To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                      				
							                				</c:when>
							                				<c:otherwise>
							                				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">
							                				No school crest/logo currently on file for your school. Please upload your school crest or logo if available.
							                				<br/>To add a crest, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in JPG format. 
							                				</div>
							                				</c:otherwise>
							                			</c:choose>
	           											 <p><input type='file' style="width:100%;max-width:500px;" name='schoolCrestFile' />
	           
	             		</div>
	                </div>
	                
        


<!-- School Development Report Document -->	               
				 	<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">6. SCHOOL DEVELOPMENT REPORT</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;"> 
						  	This document should be updated every year with the most current plan. 					
				               <c:choose>
				              		<c:when test="${ school.details.schoolReportFilename ne null }">
					              		<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">            
					                      	
					                      		<b>Current File: <a href="http://www.nlesd.ca/schools/doc/${ school.details.schoolReportFilename }" target="_blank">School Development Report (Click to open)</a></b>
					                      	<br/>To update file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in PDF format. 
					                      
					              		</div>
				              		</c:when>
				              		<c:otherwise>
						              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
						              			<b>No School Development Report documentation currently on file for ${ school.schoolName }.</b><br/>
						              				<br/>To add report file, <i>Choose File</i> below to select and upload. File <b>MUST BE</b> in PDF format.
					                   	</div>
				              		</c:otherwise>
				              	</c:choose>	
				              		<input type='file' style="width:100%;max-width:500px;" name='schoolReportFile' /> 
							</div>
	               </div>               
	       
<!-- MISC DATA -->	              
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">7. MISC DETAILS</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;"> 
								<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Current Enrollment:</span>
								   <input type='text' id='schoolenrollment'  autocomplete="false" name='schoolenrollment' class="form-control" value='${ school.detailsOther.schoolEnrollment }' />
								</div>								
								 <div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Start Time:</span>
								   <input type='text' id='starttime'  autocomplete="false" class="form-control time ui-timepicker-input" name='starttime' value='${ school.details.schoolStartTime }' />
								  </div>
								  
								  <div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Dismissal Time:</span>
								   <input type='text' id='endtime'  autocomplete="false" class="form-control time ui-timepicker-input" name='endtime' value='${ school.details.schoolEndTime }' />
								  </div>  
								  
								   <br/>Is French Immersion offered in your school?<br/>	               
						          <div class="radio" style="padding-bottom:5px;padding-top:5px;">								  
								    <label><input type='radio' name='frenchImmersion' value='true' ${ school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } /> Yes</label>
						            <label><input type='radio' name='frenchImmersion' value='false' ${ not school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } /> No</label>
								  </div> 
								  
								   <br/>Is your school Accessible?<br/>
								  <div class="radio" style="padding-bottom:5px;padding-top:5px;">								   							  
								    <label><input type='radio' name='accessible' value='true' ${ school.details.accessible ? "CHECKED=CHECKED" :"" } /> Yes </label>	
						            <label><input type='radio' name='accessible' value='false' ${ not school.details.accessible ? "CHECKED=CHECKED" :"" } /> No </label>
								  </div>			  
							</div>
</div> 
	          
	          
<!-- SCHOOL OPENING INFORMATION -->	              
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">8. SCHOOL OPENING INFORMATION (Sept)</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;">           
	          					<textarea id='schoolOpening'   autocomplete="false" class="form-control" name='schoolOpening'>${ school.details.schoolOpening }</textarea>
	               			</div>
</div>

<!-- Check if school is a Elementary/Primary -->
<c:if test="${ school.lowestGrade.name eq 'Kindergarten'}">
	                    
			<!-- KINDERSTART INFORMATION -->	              
			<div class="panel panel-info">
									  	<div class="panel-heading">8.1. KINDERSTART INFORMATION (IF APPLICABLE)</div>
									  	<div class="panel-body" style="background-color:#F8F8FF;">           
				          					<textarea id='kinderstartTimes'  autocomplete="false"  class="form-control" name='kinderstartTimes'>${ school.details.kinderstartTimes }</textarea>
				               			</div>
			</div>	                    
				                    	 
			<!-- KINDERGARTEN INFORMATION -->	              
			<div class="panel panel-info">
									  	<div class="panel-heading">8.2. KINDERGARTEN INFORMATION (IF APPLICABLE)</div>
									  	<div class="panel-body" style="background-color:#F8F8FF;">           
				          					<textarea id='kindergartenTimes'  autocomplete="false"  class="form-control" name='kindergartenTimes'>${ school.details.kindergartenTimes }</textarea>
				               			</div>
			</div>	                     
	
	
	                      
</c:if>	 


<!-- STREAM FEEDER INFORMATION -->	              
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">9. FEEDER/STREAM INFORMATION</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;">  
						  	Enter details on schools feeding into and/or feeding from your school, if applicable.     <br/>    
	          					<textarea id='streamnotes' autocomplete="false" class="form-control" name='streamnotes'>${ school.schoolStreams.streamNotes }</textarea>
	               			</div>
</div>	

	                     
<!-- OTHER INFORMATION -->	              
<div class="panel panel-info">
						  	<div class="panel-heading" style="font-weight:bold;">10. OTHER SCHOOL INFORMATION/NOTICES</div>
						  	<div class="panel-body" style="background-color:#F8F8FF;">           
	          					<textarea id='otherInfo' autocomplete="false" class="form-control" name='otherInfo'>${ school.details.otherInfo }</textarea>
	               			</div>
</div>	                   
	                    

                    	                      	
	                    
<!-- IMPORTANT NOTICE -->	              
<div class="panel panel-danger">
						  	<div class="panel-heading" style="font-weight:bold;">11. IMPORTANT NOICE</div>
						  	<div class="panel-body" style="background-color:#ffe6ea;"> 	                     
	                      This section will display an important notice atop your profile page in red as the first thing a visitor sees on the page. 
	                      <b>Only use this if there is an important issue or notice you need displayed for your school</b>. (To remove a notice, just clear the text.)
	                      <br/><br/>
	                      	                      	
	                      <textarea id='importantnotice'  autocomplete="false"  class="form-control" name='importantnotice'>${ school.detailsOther.importantNotice }</textarea>
	                                     
	               </div>
	               </div>
	               
	               
			
<!-- NOT IN USE and HIDDEN from school admins, but accessable to System Administrator -->	
<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">	
	
<div class="panel panel-warning">	
 <div class="panel-heading" style="font-weight:bold;">12. SYSTEM ADMINISTRATOR ONLY ITEMS</div>
						  	<div class="panel-body" style="background-color:#fffae6;"> 
								
								<br/>Does this school have Surveillance Cameras?<br/>
								  <div class="radio" style="padding-bottom:5px;padding-top:5px;">								   							  
								    <label><input type='radio' name='surcameras' value='true' ${ school.detailsOther.surveillanceCamera? "CHECKED=CHECKED" :"" } /> Yes </label>	
						            <label><input type='radio' name='surcameras' value='false' ${ not school.detailsOther.surveillanceCamera ? "CHECKED=CHECKED" :"" } /> No </label>
								  </div>	
								
								<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">School Name:</span>
								   <input type='text' id='schoolName' name='schoolName' autocomplete="false" class="form-control" value="${ school.schoolName }" />
								</div>	
								<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Lowest Grade Level:</span>
								   <sch:GradesDDL id='lgrade' style="height:30px;width:100%;" value='${ school.lowestGrade }' />
								</div>	
			 					<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Highest Grade Level:</span>
								   <sch:GradesDDL id='hgrade' style="height:30px;width:100%;" value='${ school.highestGrade }' />
								</div>	
			 			
			 					<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Department ID:</span>
								    <input type='text' id='deptId' name='deptId' class="form-control" value='${ school.schoolDeptID }' />
								</div>	
											 					
			 			       
								<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">School Region:</span>
								    <sch:SchoolZonesDDL id="zoneId" dummy="true" style="height:30px;width:100%;" value='${ school.zone }'  />
								</div>	
								 <div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">School Regional Zone:</span>
								    <sch:RegionsDDL id="regionId" style="height:30px;width:100%;" value='${ school.region.id }' />
								</div>	
								
								<div class="input-group">
				    				<span class="input-group-addon">Trustee/Electorial Zone:</span>     				
				    					<select id='electorialZone' name='electorialZone' class="form-control">
				    					<option value="0">---- Please Select (Required) ----</option>	
				    					<option value='1' ${ school.details.electorialZone eq "1" ? "SELECTED" : "" }>Raymond Bennett (1)</option> 
					                    <option value='2' ${ school.details.electorialZone eq "2" ? "SELECTED" : "" }>Goronwy Price (2)</option> 
					                    <option value='3' ${ school.details.electorialZone eq "3" ? "SELECTED" : "" }>Lester Simmons (3)</option> 
					                    <option value='4' ${ school.details.electorialZone eq "4" ? "SELECTED" : "" }>Scott Burden (4)</option> 
					                    <option value='5' ${ school.details.electorialZone eq "5" ? "SELECTED" : "" }>Pamela Gill (5)</option> 
					                    <option value='6' ${ school.details.electorialZone eq "6" ? "SELECTED" : "" }>Wayne Lee (6)</option> 
					                    <option value='7' ${ school.details.electorialZone eq "7" ? "SELECTED" : "" }>Thomas Kendell (7)</option> 
					                    <option value='8' ${ school.details.electorialZone eq "8" ? "SELECTED" : "" }>John George (8)</option> 
					                    <option value='9' ${ school.details.electorialZone eq "9" ? "SELECTED" : "" }>Winston Carter (9)</option> 
					                    <option value='10' ${ school.details.electorialZone eq "10" ? "SELECTED" : "" }>Eric Ayers (10)</option>
					                    <option value='11' ${ school.details.electorialZone eq "11" ? "SELECTED" : "" }>Vacant (11)</option> 
					                    <option value='12' ${ school.details.electorialZone eq "12" ? "SELECTED" : "" }>Hayward Blake (12)</option> 
					                    <option value='13' ${ school.details.electorialZone eq "13" ? "SELECTED" : "" }>Kevin Ryan (13)</option> 
					                    <option value='14' ${ school.details.electorialZone eq "14" ? "SELECTED" : "" }>Jennifer Aspell (14)</option> 
					                    <option value='15' ${ school.details.electorialZone eq "15" ? "SELECTED" : "" }>Keith Culleton (15)</option> 
					                    <option value='16' ${ school.details.electorialZone eq "16" ? "SELECTED" : "" }>Peter Whittle (16)</option> 
					                    <option value='17' ${ school.details.electorialZone eq "17" ? "SELECTED" : "" }>John Smith (17)</option> 
					                    <option value='0' ${ school.details.electorialZone eq "0" ? "SELECTED" : "" }>Not Assigned</option>
				    					</select>    				
	                      </div>
								
							
								
								
								
								
								  	
								
								
								
													
								
								
								
								 
							   	<!-- NOT CURRENTLY IN USE -->					      
							    <table style="display:none;"> 
					                   	<tr><td>
					                     
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
					                      </td>
					                    </tr>	                    	                    	                    
					                   
					            </table> 
							    
							     
							     
							<hr>	
								<b>School Bus Route Documentation:</b><br/>
								Busing staff in each region typically update this via the Bus Route Updating App in Member Services.<br/>
									<c:choose>
					              	<c:when test="${ school.details.busRoutesFilename ne null }">
					              	<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>Current documentation:  <a href="https://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }" target="_blank">${ school.schoolName } Route(s)</a></b>
				                   	</div>
					              	</c:when>
					              	<c:otherwise>
					              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>No bus route documentation currently on file for ${ school.schoolName }.</b>
				                   	</div>
					              	</c:otherwise>
					              	</c:choose>	              	
					              		              	
					              	Select the PDF file to upload by clicking on <i>Choose File</i> below if necessary.
					              	
					                <br/><br/>                
				                   	<input type='file' name='busRoutesFile' />
								<hr>
			 
			 			<b>Air Quality Report</b>
			 					<c:choose>
					              	<c:when test="${ school.details.catchmentAreaFilename ne null }">
					              	<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>Current documentation:  <a href="https://www.nlesd.ca/schools/doc/${ school.details.catchmentAreaFilename }" target="_blank">${ school.schoolName } Air Quality Report</a></b>
				                   	</div>
					              	</c:when>
					              	<c:otherwise>
					              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
				                   		<b>No Air Quality Report currently on file for ${ school.schoolName }.</b>
				                   	</div>
					              	</c:otherwise>
					              	</c:choose>	              	
					              		              	
					              	Select the PDF file to upload by clicking on <i>Choose File</i> below if necessary.
					              	
					                <br/><br/>                
				                   	<input type='file' name='catchmentAreaFile' />
								
								<hr>
								<b>Catchment Area Details</b><br/>
								This is to link to the catchment area map and/or embed the catchment area map, if available, for busing. Currently NOT part of the busing route app.<br/>
			 			<div class="input-group" style="padding-bottom:5px;">
								    <span class="input-group-addon">Catchment Map URL:</span>
								   <input type='text' id='catchmentMapUrl'  name='catchmentMapUrl' class="form-control" value='${ school.details.catchmentMapUrl }' />	
								</div>
							
							<br>
							
							
						<div class="panel panel-default">
						  	<div class="panel-heading">Catchment Map Embed Code:</div>
						  	<div class="panel-body"  style="background-color:#ffffff;">
						  	This is to embed the catchment map location on the school profile page if available. 
			   				<div style="clear:both;padding-bottom:5px;"></div>
			   				<div style="float:left;width:45%;min-width:300px;padding-right:5px;">
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
	               <button type="submit" class="btn btn-success btn-sm">Save Changes</button> &nbsp;<a href="school_profile.jsp" class="btn btn-sm btn-danger" style="color:white;">Cancel</a>
					</div>            
                  </form>
          
          
          
    <script>
    CKEDITOR.replace( 'description',{toolbar : 'Basic'} );
    CKEDITOR.replace( 'schoolOpening',{toolbar : 'Basic'}  );
    CKEDITOR.replace( 'importantnotice',{toolbar : 'Basic'}  );
    CKEDITOR.replace( 'otherInfo',{toolbar : 'Basic'}  );
  
    
    currentLowestGrade = "${school.lowestGrade.name}";
    
    if(currentLowestGrade == "kindergarten") {
    	 CKEDITOR.replace( 'kindergartenTimes',{toolbar : 'Basic'}  );
    	 CKEDITOR.replace( 'kinderstartTimes',{toolbar : 'Basic'}  );
    }
   
    
  
    CKEDITOR.replace( 'streamnotes',{toolbar : 'Basic'}  );    
    
    </script>     

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


</div>			
					

								
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
  </body>

</html>	
			

			