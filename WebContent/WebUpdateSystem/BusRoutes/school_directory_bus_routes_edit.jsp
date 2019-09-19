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
		<title>NLESD - Bus Route Posting System</title>
					

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
			<script src="../includes/js/nlesd.js"></script>
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
</style>

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
	
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="/MemberServices/WebUpdateSystem/BusRoutes/img/header.png" alt="" width="100%" border="0"><br/>			
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">School Profile Bus Route Posting/Update</div>
                      <div class="pageBody">
                     
                      <p>
                      
				<c:if test="${ msg ne null }">                  
                  <div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">${ msg } </div>   
                </c:if>  
									
									<p>
				<div class="row">	


<form action='/MemberServices/MemberAdmin/Apps/Schools/updateSchool.html' method='POST' ENCTYPE="multipart/form-data">
                  	<input type='hidden' name='schoolId' id='schoolId' value='${school.schoolID }' />	               
	               
	               	You are updating the  <b>${ school.schoolName }</b> bus route documentation. 
	               	<br/><br/>Please make sure the document you are posting/replacing is a PDF. 
	              	<br/><br/>Current documentation, if any, will be listed below. 
	              	
	              	<c:choose>
	              	<c:when test="${ school.details.busRoutesFilename ne null }">
	              	<div class="alert alert-success" style="margin-top:10px;margin-bottom:10px;padding:5px;">
                   		<b>Current documentation:</b>  <a href="https://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }" target="_blank">${ school.schoolName } Route(s) ( System FileName: ${school.details.busRoutesFilename} )</a>
                   	</div>
	              	</c:when>
	              	<c:otherwise>
	              	<div class="alert alert-danger" style="margin-top:10px;margin-bottom:10px;padding:5px;">
                   		<b>No bus route documentation currently on file for ${ school.schoolName }.</b>
                   	</div>
	              	</c:otherwise>
	              	</c:choose>	              	
	              		              	
	              	Select the PDF file to upload by clicking on <i>Choose File</i> below, and select <i>Update School</i> to make the change.
	              	
	                <br/><br/>                
                   	<input type='file' name='busRoutesFile' />
	               
	                                    
	               
	               <hr>
	               
	               If you know the URL of the catcment map location for this school, please paste it here. You will find this information in the GiS maping system, if available.
	               <div class="input-group" style="padding-bottom:5px;padding-top:10px;">
								    <span class="input-group-addon">Catchment Map URL:</span>
								   <input type='text' id='catchmentMapUrl'  name='catchmentMapUrl' class="form-control" value='${ school.details.catchmentMapUrl }' />	
								</div>
							
							<br>							
							
						<div class="panel panel-default">
						  	<div class="panel-heading">Catchment Map Embed Code:</div>
						  	<div class="panel-body"  style="background-color:#ffffff;">
						  	This is to embed the catchment map location on the school profile page if available. Depending on the GiS map application, there maybe an embed map code export option. Please copy here if known.
			   				<div style="clear:both;padding-bottom:5px;"></div>
			   				<div style="float:left;width:45%;min-width:300px;padding-right:5px;">
			   				<c:choose>
						  				<c:when test="${ not empty school.detailsOther.schoolCatchmentEmbed }">						  									  				
								  				${ school.detailsOther.schoolCatchmentEmbed }								  					
						  				</c:when>		
						  				<c:otherwise>	
						  				<div class="alert alert-danger" style="margin-top:5px;margin-bottom:10px;padding:5px;">					  				
								  				 Catchment Map not yet provided. Provide the embed code for ${ school.schoolName } catchment area location and paste in the box at right.
								  		</div>		 								  					
						  				</c:otherwise>	
						  				
						  	</c:choose>
						  	</div>			   				
			   				<div style="float:left;width:50%;min-width:300px;">
			   				<textarea autocomplete="false" id='schoolcatchmentembed' style="width:90%;height:300px;padding-bottom:5px;padding-top:10px;" name='schoolcatchmentembed'>${ school.detailsOther.schoolCatchmentEmbed }</textarea></div>
			   				</div>	             
						</div>	
	               
	               
	               
	                             
	               
	               <div align="center">	
	               <button type="submit" class="btn btn-success btn-sm">Save Changes</button> &nbsp;
	               <a href="school_directory_bus_routes.jsp" class="btn btn-sm btn-danger" style="color:white;">Cancel</a> &nbsp;
				<a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-warning" style="color:White;" title="Back to Member Services">Back to Member Services</a></div>
                 
					 
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
			

			