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


<esd:SecurityCheck permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-BUSROUTES" />

<c:set var="school" value='<%= SchoolDB.getSchoolFullDetails(Integer.parseInt(request.getParameter("id"))) %>' />
<html>

	<head>
		<title>Bus Route Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
   

<script type="text/javascript">
	    $(function(){
	    	
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
     				url: "/MemberServices/SchoolDirectory/getZoneRegions.html",
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
     				url: "/MemberServices/SchoolDirectory/getZoneRegionsSchools.html",
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
     				url: "/MemberServices/SchoolDirectory/getZoneRegionsSchools.html",
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

				
	});
    </script>

	
	</head>

  <body>
  <div class="siteHeaderGreen"><span style="color:Red;">${ school.schoolName }</span> Profile Bus Route Posting/Update</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 



<form action='/MemberServices/SchoolDirectory/updateSchool.html' method='POST' ENCTYPE="multipart/form-data">
                  	<input type='hidden' name='schoolId' id='schoolId' value='${school.schoolID }' />	               
	               
	               	You are updating the  <b>${ school.schoolName }</b> bus route documentation. Please make sure the document you are posting/replacing is a PDF. 
	                Current documentation, if any, will be listed below. 
	              	
<br/><br/>
	            
<div class="card">
  <div class="card-header" style="text-transform:uppercase;">				     
						<b>1. Bus Route Documentation:</b></div>
						<div class="card-body">	
						  	<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-6 col-12">		
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
				       </div>            	
 </div>     
	                                    
<br/><br/>	          
	               
 <div class="card">
  <div class="card-header" style="text-transform:uppercase;"><b>2. Catchment Area Details</b></div>	
  <div class="card-body">		
     If you know the URL of the catcment map location for this school, please paste it here. You will find this information in the GiS maping system, if available.
	             
  
  	   
	             		<div class="row container-fluid" style="padding-top:5px;">
									<div class="col-lg-12 col-12">	
								
									This is to link to the catchment area map and/or embed the catchment area map, if available, for busing. Currently NOT part of the busing route app.<br/>
			 						<input type='text' id='catchmentMapUrl'  name='catchmentMapUrl' class="form-control" value='${ school.details.catchmentMapUrl }' />	
								</div>
							</div>
	</div>
	</div>	
	
	<br/><br/>	
						
		 <div class="card">
  <div class="card-header" style="text-transform:uppercase;"><b>3. Catchment Map Embed Code:</b></div>	
  <div class="card-body">							
						<div class="row container-fluid" style="padding-top:5px;">
			   				<div class="col-lg-6 col-12">	
			   				This is to embed the catchment map location on the school profile page if available. <br/>
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
			   				<div class="col-lg-6 col-12">	
			   				<textarea autocomplete="false" id='schoolcatchmentembed' style="width:90%;height:300px;padding-bottom:5px;padding-top:10px;" name='schoolcatchmentembed'>${ school.detailsOther.schoolCatchmentEmbed }</textarea>
			   				</div>
			   				</div>	             
						</div>	
	               </div>
	            
	               
	   	<br/><br/>            
	                             
	               
	               <div align="center">	
	               <button type="submit" class="btn btn-success btn-sm">Save Changes</button> &nbsp;
	               <a href="school_directory_bus_routes.jsp" class="btn btn-sm btn-danger" >Cancel</a> &nbsp;
				   </div>
                 
					 
	                <table style="display:none;"> 
	                   	<tr><td>
	                   	<input type='text' id='schoolName' name='schoolName' style="width:100%;max-width:500px;" value="${ school.schoolName }" />
	                   	<input type='file' name='schoolReportFile' />
	                    <input type='file'  name='catchmentAreaFile' />	                   	                   
	                    <textarea id='description' name='description' style="width:100%;height:100px;">${ school.detailsOther.description }</textarea>
	                    <sch:GradesDDL id='lgrade' value='${ school.lowestGrade }' />  <sch:GradesDDL id='hgrade' value='${ school.highestGrade }' />
	                    <c:if test="${ school.details.schoolPhotoFilename ne null }">	                      		
	                      		<img src="http://www.nlesd.ca//schools/img/${ school.details.schoolPhotoFilename }" style="max-width:200px;">
	                    </c:if>
	                    <input type='file' style="width:100%;max-width:500px;" name='schoolPhotoFile' />
	                    <c:if test="${ school.details.schoolCrestFilename ne null }">
	                    <img src="http://www.nlesd.ca/schools/img/${ school.details.schoolCrestFilename }" style="max-width:200px;">
	                    </c:if>
	                    <input type='file' style="width:100%;max-width:500px;" name='schoolCrestFile' />
	                    <input type='text' id='deptId' name='deptId' style="width:100%;max-width:100px;" value='${ school.schoolDeptID }' />
	                    <input type='text' id='schoolenrollment' name='schoolenrollment' style="width:100%;max-width:100px;" value='${ school.detailsOther.schoolEnrollment }' />
	                    <sch:SchoolZonesDDL id="zoneId" dummy="true" value='${ school.zone }'  />
	                    <sch:RegionsDDL id="regionId" value='${ school.region.id }' />
	                    <input type='text' id='electorialZone' name='electorialZone' style="width:100%;max-width:100px;" value="${ school.details.electorialZone }" />
	                    <input type='text' id='starttime' style="width:100%;max-width:100px;" name='starttime' value='${ school.details.schoolStartTime }' />
	                    <input type='text' id='endtime' style="width:100%;max-width:100px;" name='endtime' value='${ school.details.schoolEndTime }' />
	                    <input type='text' id='address1' name='address1' style="width:100%;max-width:500px;" value="${ school.address1 }" /> 
	                    <input type='text' id='address2' name='address2' style="width:100%;max-width:500px;" value="${ school.address2 }" />
	                    <input type='text' id='citytown' name='citytown' style="width:100%;max-width:500px;" value="${ school.townCity }" />
	                    <input type='hidden' name='provinceState' value='NL' />
	                   <input type='text' id='postalcode' name='postalcode' style="width:100%;max-width:100px;" value='${ school.postalZipCode }' />
	                    <input type='text' id='telephone' name='telephone' style="width:100%;max-width:100px;" value='${ school.telephone }' />
	                   <input type='text' id='fax' name='fax' style="width:100%;max-width:100px;" value='${ school.fax }' />
	                    <input type='text' id='schoolemail' name='schoolemail' style="width:100%;max-width:500px;" value='${ school.detailsOther.schoolEmail }' />
	                    <input type='text' id='website' name='website' style="width:100%;max-width:500px;" value='${ school.website }' />
	                    
	                      <input type='text'  id='secretary' name='secretary' style="width:100%;max-width:500px;" value='${ school.details.secretaries }' />	                                            	
	                      <input type='text' id='schoolguidancesupport' name='schoolguidancesupport' style="width:100%;max-width:500px;" value='${ school.detailsOther.schoolGuidanceSupport }' />
	                    <input type='radio' name='accessible' value='true' ${ school.details.accessible ? "CHECKED=CHECKED" :"" } />
	                      	<input type='radio' name='accessible' value='false' ${ not school.details.accessible ? "CHECKED=CHECKED" :"" } />
	                     <input type='radio' name='frenchImmersion' value='true' ${ school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } />
	                      	<input type='radio' name='frenchImmersion' value='false' ${ not school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } />
	                      <input type='radio' name='surcameras' value='true' ${ school.detailsOther.surveillanceCamera? "CHECKED=CHECKED" :"" } />
						 <input type='radio' name='surcameras' value='false' ${ not school.detailsOther.surveillanceCamera ? "CHECKED=CHECKED" :"" } />	                      
	                      
	                      <textarea id='schoolOpening'  style="width:100%;height:100px;" name='schoolOpening'>${ school.details.schoolOpening }</textarea>	                      	
	                      <textarea id='kindergartenTimes'  style="width:100%;max-width:500px;height:100px;" name='kindergartenTimes'>${ school.details.kindergartenTimes }</textarea>	                      	
	                      <textarea id='kinderstartTimes'  style="width:100%;max-width:500px;height:100px;" name='kinderstartTimes'>${ school.details.kinderstartTimes }</textarea>	                      	
	                     <input type='text' id='youtube' style="width:100%;max-width:500px;" name='youtube' value='${ school.details.youtubeUrl }' />
	                   <input type='text' id='facebook' style="width:100%;max-width:500px;" name='facebook' value='${ school.details.facebookUrl }' />
	                    <input type='text' id='twitter' style="width:100%;max-width:500px;" name='twitter' value='${ school.details.twitterUrl }' />
	                    <textarea id='twitterembed' style="width:100%;max-width:500px;height:100px;" name='twitterembed'>${ school.detailsOther.twitterEmbed }</textarea>
	                    <input type='text' id='googleMapUrl' style="width:100%;max-width:500px;" name='googleMapUrl' value='${ school.details.googleMapUrl }' />
	                    <textarea id='googlemapembed'  style="width:100%;max-width:500px;height:100px;" name='googlemapembed'>${ school.detailsOther.googleMapEmbed }</textarea>	                      	
	                     	
	                    <input type='text' id='instagramlink' style="width:100%;max-width:500px;" name='instagramlink' value='${ school.detailsOther.instagramLink }' />
	                    <input type='text' id='twitterfeedwidgetid' name='twitterfeedwidgetid' value='${ school.detailsOther.twitterFeedWidgetId }' />
	                    <input type='text' id='twitterfeedscreenname' name='twitterfeedscreenname' value='${ school.detailsOther.twitterFeedScreenName }'  />
	                    <textarea id='otherInfo'  style="width:100%;max-width:500px;height:100px;" name='otherInfo'>${ school.details.otherInfo }</textarea>	                      	
	                      <textarea id='importantnotice'  style="width:100%;max-width:500px;height:100px;" name='importantnotice'>${ school.detailsOther.importantNotice }</textarea>	                      	
	                      <textarea id='streamnotes'  style="width:100%;max-width:500px;height:100px;" name='streamnotes'>${ school.schoolStreams.streamNotes }</textarea>                      
	                    
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
                  </form>

</div>
    
    <script>
//This allows pasting and delete and not much else.
$( "#schoolcatchmentembed" ).keypress(function(evt) {
	
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
    
    
  </body>

</html>	
			

			