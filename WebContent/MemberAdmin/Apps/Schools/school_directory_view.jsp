<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,
                java.text.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>



<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>


<html>
  <head>
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
				$('tr.datalist:odd').css('background-color', '#E5F2FF');
				$('#directoryListSchool tr:odd td').css({'background-color' : '#E0E0E0'})
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
                  
                  
                  <form action='updateSchoolDirector.html' method='POST'>
                  	<input type='hidden' name='schoolId' value='${school.schoolID }' />
                  	<span style="text-align:center;color:#007F01;font-weight:bold;font-size:16px;">Viewing ${ school.schoolName }</span><p>
	                 
	                  	
	                   	<b>School Name</b><br/>
	                      ${ school.schoolName }
	                      
	                      
	                    <p><b>Description / Mission Statement</b><br/>
	                    
	                    <c:choose>
	                    <c:when test="${ not empty school.detailsOther.description }">
	                      ${ school.detailsOther.description }
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No mission statement/description provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                    
	                    
	                    
	                      
	                    
	                    <p><b>School Photo</b><br/>
	                    <c:choose>
	                    <c:when test="${ not empty school.details.schoolPhotoFilename }">
	                     <img src="/schools/img/${ school.details.schoolPhotoFilename }" style="max-width:400px;">
	                    </c:when>
	                    <c:otherwise>
	                    <span style="color:Red;">No photo provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                   
	                   
	                    <p><b>School Crest</b><br/>
	                     <c:choose>
	                    <c:when test="${ not empty school.details.schoolCrestFilename }">
	                      <img src="/schools/img/${ school.details.schoolCrestFilename }" style="max-width:300px;">
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No school logo/crest provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                    
	                    <p><b>Dept. Id</b>: ${ school.schoolDeptID }                 
	                    <p><b>Region:</b>  <span style="text-transform:capitalize;">${ school.zone.zoneName }</span> &nbsp;&nbsp; <b>Zone:</b> <span style="text-transform:capitalize;">${ school.region.name }</span>
	                    <p><b>Electorial Zone:</b>  <span style="text-transform:capitalize;">${ school.details.electorialZone gt 0 ? school.details.electorialZone : "<SPAN style='color:red;'>NOT SET</SPAN>" }</span></p>
	                     
	                   
	                   <p><b>Address</b><br/>
	                  ${ school.address1 } &middot;
	                       	<c:if test="${ not empty school.address2 }">
	                       		${ school.address2 }  &middot;
	                       	</c:if>
	                       	${ school.townCity }, ${ school.provinceState }  &middot; ${ school.postalZipCode } 
	                    
	                    
	                    
	                   <p><b>Telephone:</b> ${ school.telephone } &nbsp;&nbsp; <b>Fax:</b> ${ school.fax }
	                   <p><b>Website:</b> 
	                   
	                   <c:choose>
	                    <c:when test="${ not empty school.website }">
	                      <a href="${ school.website }">${ school.website }</a>
	                    </c:when>
	                    <c:otherwise>
	                    <span style="color:Red;">No website address provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                   
	                   
	                   
	                   
	                   
	                   
	                   
	                   
	                   
	                   
	                      <p><b>School Enrollment:</b>                      
	                      <c:choose>
	                    <c:when test="${ not empty school.detailsOther.schoolEnrollment }">
	                      ${ school.detailsOther.schoolEnrollment }
	                    </c:when>
	                    <c:otherwise>
	                    <span style="color:Red;">No data provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                      
	                      
	                      
	                      
	                      
	                      
	                      
	                      <p><b>School Email:</b> 
	                      
	                      <c:choose>
	                    <c:when test="${ not empty school.detailsOther.schoolEmail }">
	                      <a href="mailto:${ school.detailsOther.schoolEmail }">${ school.detailsOther.schoolEmail }</a>
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No school email provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                      
	                      
	                      
	                      
	                      <p><b>School Guidance/Support:</b> 
	                      
	                       <c:choose>
	                    <c:when test="${ not empty school.detailsOther.schoolGuidanceSupport }">
	                      ${ school.detailsOther.schoolGuidanceSupport }
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No contact provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                      
	                      
	                      
	                      
	                      <p><b>Grades:</b>  ${ school.lowestGrade.name } - ${ school.highestGrade.name }
	                      
	                      <p><b>Secretary(s):</b><br/> 
	                      
	                       <c:choose>
	                    <c:when test="${ not empty school.details.secretaries }">
	                      ${ school.details.secretaries }
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No name(s) provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                      
	                      
	                      
	                      <p><b>Accessible:</b> ${ school.details.accessible ? "YES" :"NO" }
	                      
	                      
	                      <p><b>French Immersion:</b> ${ school.details.frenchImmersion ? "YES" :"NO" }
	                      
	                      
	                      <p><b>School Opening:</b><br/>
	                      
	                       <c:choose>
	                    <c:when test="${ not empty school.details.schoolOpening }">
	                      ${ school.details.schoolOpening }
	                    </c:when>
	                    <c:otherwise>
	                     <span style="color:Red;">No details provided.</span>
	                    </c:otherwise>
	                    </c:choose>
	                      
	                      
	                      
	                      <p><b>School Start/End Time:</b> ${ school.details.schoolStartTime } - ${ school.details.schoolEndTime }
	                      
	                      <p><b>Kindergarten Times:</b>
	                      ${ school.details.kindergartenTimes }
	                      
	                      <p><b>KinderStart Times:</b>
	                      ${ school.details.kinderstartTimes }
	                    
	                    
	                    <p><b>Bus Routes:</b>
	                      	${ school.details.busRoutesFilename }
	                      
	                    <!-- using catchmentAreaFilename for Air Quality Reports  -->
	                      
	                    <p><b>Air Quality Report:</b>
	                      	${ school.details.catchmentAreaFilename }
	                     
	                     <p><b>School Report:</b>
	                      	${ school.details.schoolReportFilename }
	                      
	                      
	                      <p><b>Youtube URL:</b>
	                      ${ school.details.youtubeUrl }
	                      
	                      <p><b>Facebook URL:</b>
	                      ${ school.details.facebookUrl }
	                      
	                      <p><b>Twitter URL:</b>
	                      ${ school.details.twitterUrl }
	                      
	                      <p><b>Twitter Embed:</b>
	                      ${ school.detailsOther.twitterEmbed}
	                      
	                      <p><b>Google Map URL:</b>
	                      ${ school.details.googleMapUrl }
	                      
	                      <p><b>Google Map Embed:</b>
	                      ${ school.detailsOther.googleMapEmbed }
	                      
	                      <p><b>Catchment Map URL:</b>
	                      ${ school.details.catchmentMapUrl }
	                      
	                      <p><b>School Catchment Embed:</b>
	                      ${ school.detailsOther.schoolCatchmentEmbed }
	                      
	                      <p><b>Instagram Link:</b>
	                      ${ school.detailsOther.instagramLink }
	                      
	                      <p><b>Twitter Feed Widget Id:</b>
	                      ${ school.detailsOther.twitterFeedWidgetId }
	                      
	                      <p><b>Twitter Feed Screen Name</b>:</b>
	                      ${ school.detailsOther.twitterFeedScreenName }
	                      
	                      <p><b>Other Info</b>
	                      
	                      ${ school.details.otherInfo }
	                      
	                      <p><b>Important Notice</b>
	                      
	                     ${ school.detailsOther.importantNotice }
	                     
	                     <p><b>School Streams Notes</b>
	                     ${ school.schoolStreams.streamNotes }
	                     
	                     <p><b>English Schools Stream</b>
	                     
	                      	<select id="englishstreams" disabled="true" size="10">
								<c:forEach var="item" items="${school.schoolStreams.schoolStreamsEnglish}">
    								<option value="${item.schoolId}">${item.schoolName}</option>
								</c:forEach>
        					</select>
        					
	                    	<p><b>French Schools Stream</b>
	                      	<select id="frenchstreams" disabled="true" size="10">
								<c:forEach var="item" items="${school.schoolStreams.schoolStreamsFrench}">
    								<option value="${item.schoolId}">${item.schoolName}</option>
								</c:forEach>
        					</select>
        					
	                    	
	                    	<p><a href='editSchool.html?id=${school.schoolID}' style='padding: 8px;'>
	                    			<img style='vertical-align:middle; border:none;'
	                    					 src="../../images/modify_off.gif"
                               	 onmouseover="src='../../images/modify_on.gif';"
                               	 onmouseout="src='../../images/modify_off.gif';" /> Edit School/Details
                          </a>
	                    	
                  </form>
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