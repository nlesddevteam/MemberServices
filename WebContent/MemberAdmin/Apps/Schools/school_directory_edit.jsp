<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>

<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>


<html>
  <head>
    <title>NLESD - School Directory System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
		<script >
		
		
		$(document).ready(function() {

			$(function() {
			    var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
			    $('html').css({'background': 'url(../includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
			    	'-webkit-background-size':'cover',
			    	'-moz-background-size':'cover',
			    	'-o-background-size':'cover',
			    	'background-size':'cover'});
			   });


		}); 
		</script>
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
				
			});
		</script>
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
    <script type="text/javascript" src="js/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="js/jquery-picklist.js"></script>
		<script type="text/javascript">
			$(function()
			{
				$("#schoolstreamsfrench").pickList();
				$("#schoolstreamsenglish").pickList();

			});
	</script>
	<link type="text/css" href="css/jquery-picklist.css" rel="stylesheet" />
<script src="../includes/ckeditor/ckeditor.js"></script>
  </head>

	<body><br/>

  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">District School Directory Edit/Add</div>
                      <div class="pageBody">
                  
                  <c:if test="${ msg ne null }">  
                  <div style="text-align:center;color:Red;font-size:12px;">                
                    ${ msg }  </div>
                    <p>                
                  </c:if>
                 
                 
                 
                 
                 
                  <form action='updateSchool.html' method='POST' ENCTYPE="multipart/form-data">
                  	<input type='hidden' name='schoolId' id='schoolId' value='${school.schoolID }' />
	                  <table id='directoryListSchool' cellpadding="5" cellspacing="0" style="width:100%;font-size:11px;">
	                  	<span style="text-align:center;color:#007F01;font-weight:bold;font-size:16px;">${ school.schoolName }</span>
	                   	<tr>
	                   		<td width="20%">School Name</td>
	                       <td width="80%"><input type='text' id='schoolName' name='schoolName' style="width:100%;max-width:500px;" value="${ school.schoolName }" /></td>
	                    </tr>
	                     <tr>
	                    	<td>Description</td>
	                      <td><textarea id='description' name='description' style="width:100%;height:100px;">${ school.detailsOther.description }</textarea></td>
	                    </tr>
	                    <tr>
	                     	<td>Dept. Id</td>
	                      <td><input type='text' id='deptId' name='deptId' style="width:100%;max-width:100px;" value='${ school.schoolDeptID }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Zone</td>
	                    	<td><sch:SchoolZonesDDL id="zoneId" dummy="true" value='${ school.zone }'  /></td>
	                    </tr>
	                    <tr id='trRegion'>
	                    	<td>Region</td>
	                    	<td><sch:RegionsDDL id="regionId" value='${ school.region.id }' /></td>
	                    </tr>
	                    <tr id='trRegion'>
	                    	<td>Electorial Zone</td>
	                    	<td><input type='text' id='electorialZone' name='electorialZone' style="width:100%;max-width:100px;" value="${ school.details.electorialZone }" /></td>
	                    </tr>
	                    <tr>
	                     	<td>Street Address</td>
	                      <td>
	                      	<input type='text' id='address1' name='address1' style="width:100%;max-width:500px;" value="${ school.address1 }" /> 
	                       	<br />
	                       	<input type='text' id='address2' name='address2' style="width:100%;max-width:500px;" value="${ school.address2 }" />
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>City/Town</td>
	                      <td><input type='text' id='citytown' name='citytown' style="width:100%;max-width:500px;" value="${ school.townCity }" /></td>
	                    </tr>
	                    <tr>
	                    	<td>Province/State</td>
	                    	<td><input type='hidden' name='provinceState' value='NL' />Newfoundland &amp; Labrador</td>
	                    </tr>
	                    <tr>
	                    	<td>Postal Code</td>
	                      <td><input type='text' id='postalcode' name='postalcode' style="width:100%;max-width:100px;" value='${ school.postalZipCode }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Telephone</td>
	                      <td><input type='text' id='telephone' name='telephone' style="width:100%;max-width:100px;" value='${ school.telephone }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Fax</td>
	                      <td><input type='text' id='fax' name='fax' style="width:100%;max-width:100px;" value='${ school.fax }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Website</td>
	                      <td><input type='text' id='website' name='website' style="width:100%;max-width:500px;" value='${ school.website }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>School Enrollment</td>
	                      <td><input type='text' id='schoolenrollment' name='schoolenrollment' style="width:100%;max-width:100px;" value='${ school.detailsOther.schoolEnrollment }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>School Email</td>
	                      <td><input type='text' id='schoolemail' name='schoolemail' style="width:100%;max-width:500px;" value='${ school.detailsOther.schoolEmail }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>School Guidance/Support</td>
	                      <td><input type='text' id='schoolguidancesupport' name='schoolguidancesupport' style="width:100%;max-width:500px;" value='${ school.detailsOther.schoolGuidanceSupport }' /></td>
	                    </tr>	   
	                    <tr>
	                    	<td>Grades</td>
	                      <td><sch:GradesDDL id='lgrade' value='${ school.lowestGrade }' /> to <sch:GradesDDL id='hgrade' value='${ school.highestGrade }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Secretary(s)</td>
	                      <td>
	                      <input type='text'  id='secretary' name='secretary' style="width:100%;max-width:500px;" value='${ school.details.secretaries }' />	                                            	
	                      </td>
	                    </tr>
	                    
	                    <tr>
	                    	<td>Accessible</td>
	                      <td>
	                      	YES&nbsp;<input type='radio' name='accessible' value='true' ${ school.details.accessible ? "CHECKED=CHECKED" :"" } />
	                      	&nbsp;&nbsp;
	                      	NO&nbsp;<input type='radio' name='accessible' value='false' ${ not school.details.accessible ? "CHECKED=CHECKED" :"" } />
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>French Immersion</td>
	                      <td>
	                      	YES&nbsp;<input type='radio' name='frenchImmersion' value='true' ${ school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } />
	                      	&nbsp;&nbsp;
	                      	NO&nbsp;<input type='radio' name='frenchImmersion' value='false' ${ not school.details.frenchImmersion ? "CHECKED=CHECKED" :"" } />
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>School Opening Information</td>
	                      <td>
	                      	<textarea id='schoolOpening'  style="width:100%;height:100px;" name='schoolOpening'>${ school.details.schoolOpening }</textarea>	                      	
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>School Start Time</td>
	                      <td><input type='text' id='starttime' style="width:100%;max-width:100px;" name='starttime' value='${ school.details.schoolStartTime }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>School End Time</td>
	                      <td><input type='text' id='endtime' style="width:100%;max-width:100px;" name='endtime' value='${ school.details.schoolEndTime }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Kindergarten Information</td>
	                      <td>
	                      	<textarea id='kindergartenTimes'  style="width:100%;max-width:500px;height:100px;" name='kindergartenTimes'>${ school.details.kindergartenTimes }</textarea>	                      	
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>KinderStart Information</td>
	                      <td>
	                      	<textarea id='kinderstartTimes'  style="width:100%;max-width:500px;height:100px;" name='kinderstartTimes'>${ school.details.kinderstartTimes }</textarea>	                      	
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>School Photo</td>
	                      <td>
	                      	<c:if test="${ school.details.schoolPhotoFilename ne null }">	                      		
	                      		<img src="http://www.nlesd.ca//schools/img/${ school.details.schoolPhotoFilename }" style="max-width:200px;"><br/>
	                      	</c:if>
	                      	<input type='file' style="width:100%;max-width:500px;" name='schoolPhotoFile' />
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>School Crest</td>
	                      <td>
	                      	<c:if test="${ school.details.schoolCrestFilename ne null }">
	                      		<img src="http://www.nlesd.ca/schools/img/${ school.details.schoolCrestFilename }" style="max-width:200px;"><br/>
	                      	</c:if>
	                      	<input type='file' style="width:100%;max-width:500px;" name='schoolCrestFile' />
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>Bus Routes</td>
	                      <td>
	                      	<c:if test="${ school.details.busRoutesFilename ne null }">
	                      		Current File: <a href="http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }">${ school.details.busRoutesFilename }</a><br />
	                      	</c:if>
	                      	<input type='file' style="width:100%;max-width:500px;" name='busRoutesFile' />
	                      </td>
	                    </tr>
	                   
	                    <tr>
	                    	<td>School Report</td>
	                      <td>
	                      	<c:if test="${ school.details.schoolReportFilename ne null }">
	                      		Current File: <a href="http://www.nlesd.ca/schools/doc/${ school.details.schoolReportFilename }">${ school.details.schoolReportFilename }</a> <br />
	                      	</c:if>
	                      	<input type='file' style="width:100%;max-width:500px;" name='schoolReportFile' />
	                      </td>
	                    </tr>
	                    
	                     <!-- using  catchmentAreaFilename for Air Quality Reports -->
	                    <tr>
	                    	<td>Air Quality Report</td>
	                      <td>
	                      	<c:if test="${ school.details.catchmentAreaFilename ne null }">
	                      		Current File: <a href="${ school.details.catchmentAreaFilename }">${ school.details.catchmentAreaFilename }</a> <br />
	                      	</c:if>
	                      	<input type='file' style="width:100%;max-width:500px;" name='catchmentAreaFile' />
	                      </td>
	                    </tr>
	                    
	                    <tr>
	                    	<td>Youtube URL</td>
	                      <td><input type='text' id='youtube' style="width:100%;max-width:500px;" name='youtube' value='${ school.details.youtubeUrl }' /></td>
	                    </tr>
	                     <tr>
	                    	<td>Facebook URL</td>
	                      <td><input type='text' id='facebook' style="width:100%;max-width:500px;" name='facebook' value='${ school.details.facebookUrl }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Twitter URL</td>
	                      <td><input type='text' id='twitter' style="width:100%;max-width:500px;" name='twitter' value='${ school.details.twitterUrl }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Twitter Embed</td>
	                      <td><textarea id='twitterembed' style="width:100%;max-width:500px;height:100px;" name='twitterembed'>${ school.detailsOther.twitterEmbed }</textarea></td>
	                    </tr>	
	                   
	                    <tr>
	                    	<td>Google Map URL</td>
	                      <td><input type='text' id='googleMapUrl' style="width:100%;max-width:500px;" name='googleMapUrl' value='${ school.details.googleMapUrl }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Google Map Embed</td>
	                      <td>
	                      	<textarea id='googlemapembed'  style="width:100%;max-width:500px;height:100px;" name='googlemapembed'>${ school.detailsOther.googleMapEmbed }</textarea>	                      	
	                      </td>
	                    </tr>
	                    <tr>
	                    	<td>Catchment Map URL</td>
	                      <td><input type='text' id='catchmentMapUrl' style="width:100%;max-width:500px;" name='catchmentMapUrl' value='${ school.details.catchmentMapUrl }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>School Catchment Embed</td>
	                      <td>
	                      	<textarea id='schoolcatchmentembed'  style="width:100%;max-width:500px;height:100px;" name='schoolcatchmentembed'>${ school.detailsOther.schoolCatchmentEmbed }</textarea>	                      	
	                      </td>
	                    </tr>                   
	                   	                    
	                    
	                   
	                    <tr>
	                    	<td>Instagram Link</td>
	                      <td><input type='text' id='instagramlink' style="width:100%;max-width:500px;" name='instagramlink' value='${ school.detailsOther.instagramLink }' /></td>
	                    </tr>
	                                     	                    	                    	                    
	                    <tr>
	                    	<td>Twitter Feed Widget Id</td>
	                      <td><input type='text' id='twitterfeedwidgetid' name='twitterfeedwidgetid' value='${ school.detailsOther.twitterFeedWidgetId }' /></td>
	                    </tr>
	                    <tr>
	                    	<td>Twitter Feed Screen Name</td>
	                      <td><input type='text' id='twitterfeedscreenname' name='twitterfeedscreenname' value='${ school.detailsOther.twitterFeedScreenName }'  /></td>
	                    </tr>
	                      <tr>
	                    	<td>Other Information</td>
	                      <td>
	                      	<textarea id='otherInfo'  style="width:100%;max-width:500px;height:100px;" name='otherInfo'>${ school.details.otherInfo }</textarea>	                      	
	                      </td>
	                    </tr>                   
	                    <tr style="border:1px solid red;">
	                    	<td>Important Notice</td>
	                      <td>
	                      	<textarea id='importantnotice'  style="width:100%;max-width:500px;height:100px;" name='importantnotice'>${ school.detailsOther.importantNotice }</textarea>	                      	
	                      </td>
	                    </tr>
	                    
	                    <tr>
	                    	<td>School Streams Notes </td>
	                    	<td><textarea id='streamnotes'  style="width:100%;max-width:500px;height:100px;" name='streamnotes'>${ school.schoolStreams.streamNotes }</textarea></td>	                      
	                    </tr>
	                    <tr>
	                    	<td>School Streams English </td>
	                    	
	                      <td>
	                      
							<select id="schoolstreamsenglish" name="schoolstreamsenglish" multiple="multiple">
								<c:forEach var="item" items="${schoollistenglish}">
									<option value="${item.schoolId}" ${item.selected} >${item.schoolName}</option>
    							</c:forEach>
							</select>
						</td>
	                    </tr>
	                    <tr>
	                    	<td>School Streams French </td>
	                    	
	                      <td>
	                      
							<select id="schoolstreamsfrench" name="schoolstreamsfrench" multiple="multiple">
								<c:forEach var="item" items="${schoollistfrench}">
    								<option value="${item.schoolId}" ${item.selected}>${item.schoolName}</option>
								</c:forEach>
							</select>
	                      </td>
	                    </tr>	                    	                    	                    
	                    <tr>
	                    	<td colspan='2'><input type='submit' value='Update School' /></td>
	                    </tr>
	                  </table>
                  </form>
          <script>
    CKEDITOR.replace( 'description' );
    CKEDITOR.replace( 'schoolOpening' );
    CKEDITOR.replace( 'importantnotice' );
    CKEDITOR.replace( 'otherInfo' );
    CKEDITOR.replace( 'kindergartenTimes' );
    CKEDITOR.replace( 'kinderstartTimes' );
    CKEDITOR.replace( 'streamnotes' );
    
    
    </script>     
            </div>
    
     <div align="center"><a href="school_directory.jsp"><img src="../includes/img/back-off.png" class="img-swap menuImage" title="Back to Staff Listing"></a></div>
		
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>

</html>      
               