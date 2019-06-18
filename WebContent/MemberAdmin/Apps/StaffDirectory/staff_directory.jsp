<%@ page language="java" session="true" %>
<%@ page import='com.awsd.security.*,java.util.*'%>
<%@ page import='com.nlesd.webmaint.bean.*'%>
<%@ page import='com.nlesd.webmaint.service.*'%>
<%@ page import='org.apache.commons.lang.StringUtils' %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>


<esd:SecurityCheck permissions="MEMBERADMIN-VIEW" />

<%
	Collection<StaffDirectoryContactBean> contacts = StaffDirectoryService.getStaffDirectoryContactBeans();
%>

<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>

<c:set var='contacts' value="<%= contacts %>" />

<head>
		<title>NLESD - Web Update Posting System</title>
					

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
				
				<div class="pageTitleHeader siteHeaders">District Office Staff Directory</div>
                      <div class="pageBody">
	
		
	
                       
                      
                  
                  <c:if test="${ msg ne null }">  
                  <div style="text-align:center;color:Red;font-size:14px;">                
                    ${ msg }  </div>
                    <p>                
                  </c:if>
                  
                  <BR />
                  
                  <div align="center">
                  Below is a complete list of all district office staff. To narrow your search, use the form below to categorize by region and division.<p>
                  Results are color-coded by region:<p>
                  <span class="region1solid" style="color:White;">&nbsp;Eastern&nbsp;</span>&nbsp;
                  <span class="region2solid" style="color:White;">&nbsp;Central&nbsp;</span>&nbsp;
                  <span class="region3solid" style="color:White;">&nbsp;Western&nbsp;</span>&nbsp;
                  <span class="region4solid" style="color:White;">&nbsp;Labrador&nbsp;</span>
                  
                  <p>
                  <div align="center"><a href="/MemberServices/WebUpdateSystem/index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                    <p>
							                  <form method="GET" action="staff_directory.jsp">
							 Region:
							  <select name="region">
							  <option value="">All</option>
							    <option value="eastern" class="region1">Eastern</option>
							    <option value="central" class="region2">Central</option>
							    <option value="western" class="region3">Western</option>
							    <option value="labrador" class="region4">Labrador</option>
							  </select> Division: 
							  <select name="division">
							  <option value="">All</option>
							  <option value="Director's Office">Director's Office</option>
							    <option value="Programs">Programs</option>
							    <option value="Human Resources">Human Resources</option>
							    <option value="Finance">Finance and Administration</option>
							    <option value="Operations">Operations</option>
							  </select>
							  <input type="submit" value="Filter">
							</form>
							
							<jsp:include page="stafffindbar.jsp" />
							
        </div>          
                  
                  
                
                
					
                 <p>
                  
                 <div style='float:right;'>
                        	<a href='addStaffDirectoryContact.html' title="Add new Contact"><img src="img/add-off.png" class="img-swap menuImage" border=0></a>
                        </div>
                 
                   <br/>      
                   
                   
                   
                    <span style="color:#1F4279;font-weight:bold;font-size:16px;text-transform:uppercase;">${param.region} DISTRICT OFFICE STAFF <c:if test="${not empty param.division }">(${param.division})</c:if>&nbsp;</span><br/>
                  <table id='directoryList' align="center" width="100%" cellpadding="0" cellspacing="0" border="0" style="border:none;font-size:11px;">
                 
                     <tr style="color:White;background-color:#007F01;font-weight:bold;">
                       <td width="15%">&nbsp;NAME</td>
                      <td>POSITION/TITLE</td> 
                       <td>TELEPHONE</td>                     
                      <c:if test="${ empty param.division or param.division eq '' }">	<td width="20%">DIVISION</td></c:if>
                       <td width="5%">SORT</td>
                       <td width="10%">EDIT</td>
                    </tr>
                    <c:forEach items='${ contacts }' var='contact'>                 
                    
                    <c:if test="${fn:containsIgnoreCase(contact.zone.zoneName, param.region)}">  				
                    <c:if test="${fn:containsIgnoreCase(contact.division.name, param.division)}">  
                                      
                                                  
                   
                    
                    	<tr class='datalist'>
                        <td>
                        <c:if test="${ contact.zone.zoneName eq 'eastern' }">	
																<c:set var="regionColor" value="region1solid"/>																
															</c:if>	
									    					<c:if test="${ contact.zone.zoneName eq 'central' }">	
																<c:set var="regionColor" value="region2solid"/>
																
															</c:if>	
															<c:if test="${ contact.zone.zoneName eq 'western' }">	
																<c:set var="regionColor" value="region3solid"/>
																
															</c:if>	
															<c:if test="${ contact.zone.zoneName eq 'labrador' }">	
																<c:set var="regionColor" value="region4solid"/>																
															</c:if>	
                        <span class="${regionColor}">&nbsp;&nbsp;</span>
                        								<c:choose>
														<c:when test="${ not empty contact.email }">
														<a href="mailto:${contact.email}">${ contact.fullName }</a>
														</c:when>
														<c:otherwise>
														${ contact.fullName }
														</c:otherwise>
														</c:choose>
														
														
                        </td>
												<td>${ contact.position }</td>
												
												<td>${ contact.telephone }</td>
												
												
												
												<c:if test="${ empty param.division or param.division eq '' }">	
												<td class='column division'>
												
												
												
												
												
													<c:choose>
														<c:when test="${ contact.division.id gt 0  }">
															${ contact.division.name }
														</c:when>
														<c:otherwise>&nbsp;</c:otherwise>
													</c:choose>
												</td>											
												</c:if>																					
												
						<td align="center">${ contact.sortorder }</td>
                        <td valign="middle" align="right">
                         	<img src="img/edit.png"  title="Edit"                                    
                                      onclick="document.location.href='editStaffDirectoryContact.html?id=${contact.contactId}';" />
                          <img src="img/del.png"  title="Delete"                                    
                                      onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';">
                      	</td>
                      </tr>
                      
                      
                      
                      
                      
                      
                      
                      </c:if>
                      </c:if>
                      
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