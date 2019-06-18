<%@ page language="java"
        session="true"
        import="java.util.*,com.awsd.security.*,com.awsd.school.*,com.awsd.personnel.*,java.text.*"%>
<%@ page import='org.apache.commons.lang.StringUtils' %>
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
	
	<%  
  int page_num = 0;

  if(request.getParameter("page") != null)
  {
    page_num = Integer.parseInt(request.getParameter("page"));
  }
  else
  {
    page_num = 0;
  }
    
  if((session.getAttribute("SCHOOL-ARRAYLIST") == null)||(page_num == 0))
  {
    session.setAttribute("SCHOOL-ARRAYLIST", SchoolDB.getSchoolsAlphabetized());
  }
%>


<c:set var='schools' value='<%= ((ArrayList<Vector<School>>)session.getAttribute("SCHOOL-ARRAYLIST")).get(page_num) %>' />
<c:set var='pagenum' value='<%= page_num %>' />

<c:set var="now" value="<%=new java.util.Date()%>" /> 								
<fmt:formatDate value="${now}" pattern="D" var="todayDate" />	


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
	
	<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#closing_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

    		);

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
				
				<div class="pageTitleHeader siteHeaders">District School Directory</div>
                      <div class="pageBody">
	
		
	<div align="center">
                  Below is a complete list of all district schools sorted alphabetically.<p>
                  Results are color-coded by region:<p>
                  <span class="region1solid" style="color:White;">&nbsp;Eastern&nbsp;</span>&nbsp;
                  <span class="region2solid" style="color:White;">&nbsp;Central&nbsp;</span>&nbsp;
                  <span class="region3solid" style="color:White;">&nbsp;Western&nbsp;</span>&nbsp;
                  <span class="region4solid" style="color:White;">&nbsp;Labrador&nbsp;</span>
                  
                  <p>
                       
                      
                  
                  <c:if test="${ msg ne null }">  
                  <div style="text-align:center;color:Red;font-size:12px;">                
                    ${ msg }  </div>
                    <p>                
                  </c:if>
	
                      	<div style='font-size:14px;'>
                        <%for (char i='A'; i <= 'Z'; i++){
                            if((i-'A') != page_num){%>
                              <a href="school_directory.jsp?page=<%=i - 'A'%>"><%=i%></a>
                        <%  }else{%>
                            <%=i%>
                        <%}}%>
                        </div>
                        
                        
                        
                        
                     
                     
          </div>        
                       
                        <div style='float:right;'>
                        	<a href='addSchool.html' title="Add new School"><img src="img/add-off.png" class="img-swap menuImage" border=0></a>
                        </div>
                                  
                  <p><br/>
                  <table id='directoryList' align="center" width="100%" cellpadding="0" cellspacing="0" border="0" style="border:none;font-size:11px;">
                    <tr style="color:White;background-color:#007F01;font-weight:bold;">
                      <td>School Name (ID)</td>                      
                      <td>Region/Zone</td>
                      <td>Town/City</td>
                      <td>Grades</td>
                      <td>Items</td>                   
                      
                      <td>Function</td>
                    </tr>
                    <c:forEach items='${ schools }' var='school'>
                    	<tr class="datalist">
                    	
                        <td>
                         <c:if test="${ school.zone.zoneName eq 'eastern' }">	
																<c:set var="regionColor" value="region1solid"/>																
															</c:if>	
									    					<c:if test="${ school.zone.zoneName eq 'central' }">	
																<c:set var="regionColor" value="region2solid"/>
																
															</c:if>	
															<c:if test="${ school.zone.zoneName eq 'western' }">	
																<c:set var="regionColor" value="region3solid"/>
																
															</c:if>	
															<c:if test="${ school.zone.zoneName eq 'labrador' }">	
																<c:set var="regionColor" value="region4solid"/>																
															</c:if>	
                     
	                      
	                      <span class="${regionColor}">&nbsp;&nbsp;</span>
                        <a href='viewSchool.html?id=${school.schoolID}'>${ school.schoolName }</a> (${ school.schoolDeptID })<br/>                 
                        <span class="${regionColor}">&nbsp;&nbsp;</span>
                        <c:choose>
                        <c:when test="${ not empty school.detailsOther.dateAdded }">
                       <span style="color:Grey;font-size:10px;">Updated: <fmt:formatDate value="${school.detailsOther.dateAdded}" dateStyle="long" /> </span>                       
                        </c:when>
                        <c:otherwise>                         
                                               
                         <span style="color:Red;font-size:10px;">** Need Update ** </span> 
                        </c:otherwise>
                        </c:choose>
                        </td>
                        
	                      <td>
	                      
	                     
	                                            
	                      
	                      <span style="text-transform:capitalize;"> ${ school.zone.zoneName }<br/>(${ school.region.name })</span></td>
	                      <td>	                      
	                      	${ school.townCity }
	                      </td>
	                      
	                      <td>${ school.lowestGrade.name } to<br/>${ school.highestGrade.name }</td>
	                      <td>
	                      
	                     
	                      <c:choose>
								<c:when test="${ not empty school.website }">
													<a href='${ school.website }' target="_blank"><img src="img/website-yes.png" title="Website Available"  border="0" height="20" /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/website-no.png" alt="" title="No Website Listed." border="0" height="20" />
												</c:otherwise>
						  </c:choose>
						  
						  <c:choose>
								<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }' target="_blank"><img src="img/busroute-yes.png" alt=""  title="Bus Route Documentation" border="0" height="20"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/busroute-no.png" alt="" title="No Bus Routes available at this time." border="0" height="20" />
												</c:otherwise>
						  </c:choose>
						  
						  <c:choose>
								<c:when test="${ not empty school.details.schoolReportFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.schoolReportFilename }' target="_blank"><img src="img/report-yes.png" title="" alt=""  border="0" height="20" /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/report-no.png" alt="" title="No School Report available at this time." border="0" height="20" />
												</c:otherwise>
						  </c:choose>
						  
						  <c:choose>
								<c:when test="${ not empty school.details.catchmentAreaFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.catchmentAreaFilename }' target="_blank"><img src="img/airq-yes.png" title="" alt=""  border="0" height="20" /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/airq-no.png" alt="" title="No Air Quality Report available at this time." border="0" height="20" />
												</c:otherwise>
						  </c:choose>
						  <c:choose>
								<c:when test="${ not empty school.details.catchmentMapUrl }">
													<a href='${ school.details.catchmentMapUrl }' target="_blank"><img src="img/catchment-yes.png" alt=""  title="School Catchment Map available" border="0" height="20" /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/catchment-no.png" alt="" title="No Catchment Map available at this time." border="0" height="20" />
												</c:otherwise>
						  </c:choose>
						  
						  <c:choose>
								<c:when test="${ not empty school.details.googleMapUrl }">
													<a href='${ school.details.googleMapUrl }' target="_blank"><img src="img/googlemap-yes.png" alt=""  title="Google Map" height="20" border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/googlemap-no.png" alt="" title="No Google map available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>
						  
						  
						  
						  
						 
						  
						   <c:choose>
								<c:when test="${ not empty school.detailsOther.schoolEmail }">
													<a href='mailto:${ school.detailsOther.schoolEmail }'><img src="img/email-yes.png" title="School has email address" alt=""  height="20" border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/email-no.png" alt="" title="No school email address available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>
						  
						   <c:choose>
								<c:when test="${ not empty school.details.twitterUrl }">
													<a href='${ school.details.twitterUrl }' target="_blank"><img src="img/twitter-yes.png" alt=""  title="School has a twitter account" height="20" border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/twitter-no.png" alt="" title="No Twitter account available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>
						  
						   <c:choose>
								<c:when test="${ not empty school.details.facebookUrl }">
													<a href='${ school.details.facebookUrl }' target="_blank"><img src="img/fb-yes.png" alt="" title="School has a Facebook account." height="20" border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/fb-no.png" alt="" title="No Facebook account available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>
						  
						  <c:choose>
								<c:when test="${ not empty school.details.youtubeUrl }">
													<a href='${ school.details.youtubeUrl }' target="_blank"><img src="img/yt-yes.png" alt="" title="School has a YouTube account." height="20" border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/yt-no.png" alt="" title="No YouTube account available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>
						  
						  
						  
						  
						  
						   <c:choose>
								<c:when test="${ not empty school.details.schoolCrestFilename }">
													<a href="http://www.nlesd.ca/schools/img/${ school.details.schoolCrestFilename }"><img src="img/crest-yes.png" height="20" alt="" title="School Crest/Logo" border="0"  /></a>
													
													
								</c:when>
												<c:otherwise>
													<img src="img/crest-no.png" alt="" title="No School Crest or Logo available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>  
						  
						  
						  
						  
						  
						  
						  
						  <c:choose>
								<c:when test="${ not empty school.details.schoolPhotoFilename }">
													<a href="http://www.nlesd.ca/schools/img/${ school.details.schoolPhotoFilename }"><img src="img/photo-yes.png" alt="" height="20" class="schoolProfileImg" title="Photo Available." border="0"  /></a>	
								</c:when>
												<c:otherwise>
													<img src="img/photo-no.png" alt="" title="No Photo Available at this time." height="20" border="0" />
												</c:otherwise>
						  </c:choose>	
						  
						 
						  		
	                      
	                      </td>
                        <td valign="middle" align="right">
                         	<a href="editSchool.html?id=${school.schoolID}"><img src="img/edit.png" border="0" height="20"  title="Edit Profile"/></a>
                          <img src="img/del.png" height="20" border="0" onclick="if(confirm('Are you sure you want to delete ${school.schoolName}?'))document.location.href='deleteSchool.html?sid=${school.schoolID}';">
                      	</td>
                      </tr>
                    </c:forEach>
                  </table>
                <p>
                     <div align="center"><a href="/MemberServices/WebUpdateSystem/index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                    
                    
            
    
    
    </div>
    
    
		
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