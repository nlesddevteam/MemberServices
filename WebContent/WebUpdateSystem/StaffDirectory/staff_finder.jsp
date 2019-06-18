<%@ page language="java" session="true" %>
<%@ page import='com.awsd.security.*,java.util.*'%>
<%@ page import='com.nlesd.webmaint.bean.*'%>
<%@ page import='com.nlesd.webmaint.service.*'%>
<%@ page import='org.apache.commons.lang.StringUtils' %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld"  %>
<%@ taglib prefix="sch" uri="/WEB-INF/school_admin.tld"  %>


<esd:SecurityCheck permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING" />

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
<c:set var="containsa" value="0"/>   
<c:set var="containsb" value="0"/>  
<c:set var="containsc" value="0"/>  
<c:set var="containsd" value="0"/>  
<c:set var="containse" value="0"/>  
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
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
		

<style>
	
.stafflistDocLinkContainer {float:left;width:160px;text-align:center;}
.stafflistHeader {color: #007F01;font-weight: bold;}
.stafflist .row .email a { color:#007F01;  text-decoration: none;}
.stafflist .row .email a:visited { color:#007F01;  text-decoration: none;}
.stafflist .row .email a:hover {	color: #FF0000;	 text-decoration: underline;}
.stafflist .row .email a:active { color: #FF0000; text-decoration: none;}
.stafflist {display:table;  width:100%;font-size:11px;}
.stafflist .table {display:table;}
.stafflist .rowCaption {caption-side: top; display: table-caption;width:100%;text-align:left; font-weight:bold;color:#007F01;margin-bottom:3px;}
.stafflist .row { display:table-row; width:100%;}
.stafflist .column { display: table-cell;border-bottom:1px solid grey;padding:2px;}
.stafflist .row .header { text-align:left; color:white; font-weight:bold; }
.stafflist .row .StaffName {width:20%;vertical-align: text-top;}
.stafflist .row .StaffPosition { width:50%;border-left:1px solid grey;vertical-align: text-top;}
.stafflist .row .StaffTelephone { width:12%;border-left:1px solid grey;vertical-align: text-top;}
.stafflist .row .StaffSort { width:8%;border-left:1px solid grey;vertical-align: text-top;text-align:center;}
.stafflist .row .StaffOptions { width:10%;border-left:1px solid grey;vertical-align: text-top;text-align:center;}
div.stafflist > div:nth-of-type(odd) {background: #FDF5E6;}

.itemHeader {font-size:16px;color:#1F4279;font-weight:bold;}	
.regionDefault {background-color: #1F4279;}
.region1text {color:rgba(191, 0, 0, 1);}
.region1 {background-color:rgba(191, 0, 0, 0.1);}
.region1a {border-left:10px solid rgba(191, 0, 0, 0.3);}
.region1solid {background-color:rgba(191, 0, 0, 1);}
.region1half {background-color:rgba(191, 0, 0, 0.5);}
.region2text {color:rgba(0, 191, 0, 1);}
.region2 {background-color:rgba(0, 191, 0, 0.1);}
.region2a {border-left:10px solid rgba(0, 191, 0, 0.3);}
.region2solid {background-color:rgba(0, 191, 0, 1);}
.region2half {background-color:rgba(0, 191, 0, 0.5);}
.region3text {color:rgba(255, 132, 0, 1);}
.region3 {background-color:rgba(255, 132, 0, 0.1);}
.region3solid {background-color:rgba(255, 132, 0, 1);}
.region3half {background-color:rgba(255, 132, 0, 0.5);}
.region3a {border-left:10px solid rgba(255, 132, 0, 0.3);}
.region4text {color:rgba(127, 130, 255, 1);}
.region4 {background-color:rgba(127, 130, 255, 0.1);}
.region4a {border-left:10px solid rgba(127, 130, 255, 0.3);}
.region4solid {background-color:rgba(127, 130, 255,1);}
.region4half {background-color:rgba(127, 130, 255, 0.5);}	
.region5text {color:rgba(128, 0, 128, 1);}
.region5 {background-color:rgba(128, 0, 128, 0.1);}
.region5a {border-left:10px solid rgba(128, 0, 128, 0.3);}
.region5solid {background-color:rgba(128, 0, 128,1);}
.region5half {background-color:rgba(128, 0, 128, 0.5);}	
</style>
  </head>

	<body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="/MemberServices/WebUpdateSystem/StaffDirectory/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">District Office Staff Directory Search</div>
                      <div class="pageBody">
	
					Staff contact information 
				<c:if test="${ not empty param.pos }"><span style='vertical-align:top;'>for <b>${ param.pos }</b></span></c:if> 
				<c:if test="${ not empty param.region }"><span style='vertical-align:top;'>of <b>${ param.region }</b> region</span></c:if> 
				can be found below. <br/>
	
                <c:set var="query" value="${ param.pos }"/>
				<c:set var="abbrev" value="SEO"/>	
				<c:if test="${query eq 'SEO'}">
				<c:set var="query" value="Senior"/>				
				</c:if>
				<c:if test="${query eq 'DCC'}">
				<c:set var="query" value="District Conference"/>				
				</c:if>       
                   
                  
                  <c:if test="${ msg ne null }">  
                   <div class="alert alert-danger" id="memo_error_message" style="margin-top:10px;margin-bottom:10px;padding:5px;">${ msg } </div>     
                  </c:if>
                  
                  <BR />
                  
                  <div align="center">
                 
                  Results are color-coded by region:<p>
                  <span class="region1solid" style="color:White;">&nbsp;Avalon&nbsp;</span>&nbsp;
                  <span class="region2solid" style="color:White;">&nbsp;Central&nbsp;</span>&nbsp;
                  <span class="region3solid" style="color:White;">&nbsp;Western&nbsp;</span>&nbsp;                
                  <span class="region4solid" style="color:White;">&nbsp;Labrador&nbsp;</span>&nbsp;  
                  <span class="region5solid" style="color:White;">&nbsp;Provincial&nbsp;</span>
                  
                  <p>
                  
							      
        		     
                  
                  
                <jsp:include page="stafffindbar.jsp" />
                
				</div>     	
                 <p>
                  
                 <div style='float:right;'>
                        	<a href='addStaffDirectoryContact.html' class="btn btn-sm btn-success" style="color:White;" title="Add new Contact">+ Add New Contact</a>
                        </div>
                 
                   <br/>      
                   
                   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-AVALON">
                  	 <c:set var="region" value="Avalon"/>
                  	 
                  	 
                  	 
                  	 
                  	 <div class="row">      
                 
                  <span class="region1text" style="font-size:16px;">Results for ${region} Region</span>
                  <div class="stafflist">	
	 			   
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>											
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					<c:choose>
  					<c:when test="${fn:containsIgnoreCase(contact.zone.zoneName, region) and (fn:containsIgnoreCase(contact.position, query) or fn:containsIgnoreCase(contact.fullName, query) or fn:containsIgnoreCase(contact.telephone, query)  or fn:containsIgnoreCase(contact.email, query))}">
                     
  					
  					
  						
  											<div class='row contact-row'> 
	     										<div class="column StaffName">
	     												<c:choose>
															<c:when test="${ not empty contact.email }">
															<a href="mailto:${contact.email}">${ contact.fullName }</a>
															</c:when>
															<c:otherwise>
															${ contact.fullName }
															</c:otherwise>
														</c:choose>
	     										</div>
	     										<div class="column StaffPosition">${ contact.position }</div>
	     										<div class="column StaffTelephone">${ contact.telephone }</div>	     										
	     										<div class="column StaffSort">${ contact.sortorder }</div>
	     										<div class="column StaffOptions">
	     										<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
	     										
	     										</div>
	     									</div> 
	     				<c:set var="containsa" value="${containsa + 1}"/>				
	     		</c:when>
	     		</c:choose>		
	     				
	     									
  					</c:forEach>
  			</div>
                  
                  
            <c:if test="${ containsa eq '0' }">No results found.</c:if>
				<c:if test="${ containsa > '0' }">${containsa} result(s) found.</c:if>
				<c:if test="${ containsa > '10' }">Try narrowing your search criteria if you cannot find who you are looking for.</c:if>
				<br/><br/>	      
                  
                  
                                  
                    
            
    
    
   
 </div>                	 
                  	 
                  	 
                  	 
                   </esd:SecurityAccessRequired>
                   
                   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-CENTRAL">
                  	 <c:set var="region" value="Central"/>
                  	 
                  	
                  	 
                  	 
                  	 <div class="row">      
                  
                  <span class="region2text" style="font-size:16px;">Results for ${region} Region</span>
                  <div class="stafflist">	
	 			  
  					<div class="row">
									   		<div class="column header StaffName region2solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region2solid">POSITION</div>									        
											<div class="column header StaffTelephone region2solid">TELEPHONE</div>											
											<div class="column header StaffSort region2solid">SORT #</div> 
											<div class="column header StaffOptions region2solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					<c:choose>
  					<c:when test="${fn:containsIgnoreCase(contact.zone.zoneName, region) and (fn:containsIgnoreCase(contact.position, query) or fn:containsIgnoreCase(contact.fullName, query) or fn:containsIgnoreCase(contact.telephone, query)  or fn:containsIgnoreCase(contact.email, query))}">
                     
  					
  					
  						
  											<div class='row contact-row'> 
	     										<div class="column StaffName">
	     												<c:choose>
															<c:when test="${ not empty contact.email }">
															<a href="mailto:${contact.email}">${ contact.fullName }</a>
															</c:when>
															<c:otherwise>
															${ contact.fullName }
															</c:otherwise>
														</c:choose>
	     										</div>
	     										<div class="column StaffPosition">${ contact.position }</div>
	     										<div class="column StaffTelephone">${ contact.telephone }</div>	     										
	     										<div class="column StaffSort">${ contact.sortorder }</div>
	     										<div class="column StaffOptions">
	     										<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
	     										
	     										</div>
	     									</div> 
	     				<c:set var="containsb" value="${containsb + 1}"/>				
	     		</c:when>
	     		</c:choose>		
	     				
	     									
  					</c:forEach>
  			</div>
                  
                  
            <c:if test="${ containsb eq '0' }">No results found.</c:if>
				<c:if test="${ containsb > '0' }">${containsb} result(s) found.</c:if>
				<c:if test="${ containsb > '10' }">Try narrowing your search criteria if you cannot find who you are looking for.</c:if>
				<br/><br/>	      
                  
                  
                                  
                    
            
    
   
    </div>
                  	 
                   </esd:SecurityAccessRequired>
                  
         <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-WESTERN">
                  	 <c:set var="region" value="Western"/>
                  	 
                  	 
                  	 
                  	 
                  	 <div class="row">      
                  
                  <span class="region3text" style="font-size:16px;">Results for ${region} Region</span>
                  <div class="stafflist">	
	 			
  					<div class="row">
									   		<div class="column header StaffName region3solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region3solid">POSITION</div>									        
											<div class="column header StaffTelephone region3solid">TELEPHONE</div>											
											<div class="column header StaffSort region3solid">SORT #</div> 
											<div class="column header StaffOptions region3solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					<c:choose>
  					<c:when test="${fn:containsIgnoreCase(contact.zone.zoneName, region) and (fn:containsIgnoreCase(contact.position, query) or fn:containsIgnoreCase(contact.fullName, query) or fn:containsIgnoreCase(contact.telephone, query)  or fn:containsIgnoreCase(contact.email, query))}">
                     
  					
  					
  						
  											<div class='row contact-row'> 
	     										<div class="column StaffName">
	     												<c:choose>
															<c:when test="${ not empty contact.email }">
															<a href="mailto:${contact.email}">${ contact.fullName }</a>
															</c:when>
															<c:otherwise>
															${ contact.fullName }
															</c:otherwise>
														</c:choose>
	     										</div>
	     										<div class="column StaffPosition">${ contact.position }</div>
	     										<div class="column StaffTelephone">${ contact.telephone }</div>	     										
	     										<div class="column StaffSort">${ contact.sortorder }</div>
	     										<div class="column StaffOptions">
	     										<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
	     										
	     										</div>
	     									</div> 
	     				<c:set var="containsc" value="${containsc + 1}"/>				
	     		</c:when>
	     		</c:choose>		
	     				
	     									
  					</c:forEach>
  			</div>
                  
                  
            <c:if test="${ containsc eq '0' }">No results found.</c:if>
				<c:if test="${ containsc > '0' }">${containsc} result(s) found.</c:if>
				<c:if test="${ containsc > '10' }">Try narrowing your search criteria if you cannot find who you are looking for.</c:if>
				<br/><br/>	      
                  
                  
                                  
                    
            
    
    
    </div>
                  	 
                   </esd:SecurityAccessRequired>   
    <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-LABRADOR">
                  	 <c:set var="region" value="Labrador"/>
                   	 
                  	
                  	 
                  	 
                  	 <div class="row">      
                  
                  <span class="region4text" style="font-size:16px;">Results for ${region} Region</span>
                  <div class="stafflist">	
	 			
  					<div class="row">
									   		<div class="column header StaffName region4solid">NAME</div>	
									        <div class="column header StaffPosition region4solid">POSITION</div>									        
											<div class="column header StaffTelephone region4solid">TELEPHONE</div>											
											<div class="column header StaffSort region4solid">SORT #</div> 
											<div class="column header StaffOptions region4solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					<c:choose>
  					<c:when test="${fn:containsIgnoreCase(contact.zone.zoneName, region) and (fn:containsIgnoreCase(contact.position, query) or fn:containsIgnoreCase(contact.fullName, query) or fn:containsIgnoreCase(contact.telephone, query)  or fn:containsIgnoreCase(contact.email, query))}">
                     
  					
  					
  						
  											<div class='row contact-row'> 
	     										<div class="column StaffName">
	     												<c:choose>
															<c:when test="${ not empty contact.email }">
															<a href="mailto:${contact.email}">${ contact.fullName }</a>
															</c:when>
															<c:otherwise>
															${ contact.fullName }
															</c:otherwise>
														</c:choose>
	     										</div>
	     										<div class="column StaffPosition">${ contact.position }</div>
	     										<div class="column StaffTelephone">${ contact.telephone }</div>	     										
	     										<div class="column StaffSort">${ contact.sortorder }</div>
	     										<div class="column StaffOptions">
	     										<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
	     										
	     										</div>
	     									</div> 
	     				<c:set var="containsd" value="${containsd + 1}"/>				
	     		</c:when>
	     		</c:choose>		
	     				
	     									
  					</c:forEach>
  			</div>
                  
                  
            <c:if test="${ containsd eq '0' }">No results found.</c:if>
				<c:if test="${ containsd > '0' }">${containsd} result(s) found.</c:if>
				<c:if test="${ containsd > '10' }">Try narrowing your search criteria if you cannot find who you are looking for.</c:if>
				<br/><br/>	      
                  
                  
                                  
                    
            
    
    </div>
  
                  	 
                   </esd:SecurityAccessRequired>
    <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-PROVINCIAL">
                  	 <c:set var="region" value="Provincial"/>
                 	 
                  	 
                  	 
                  	 
                  	 <div class="row">      
                  
                  <span class="region5text" style="font-size:16px;">Results for ${region} Region</span>
                  <div class="stafflist">	
	 			
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>											
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					<c:choose>
  					<c:when test="${fn:containsIgnoreCase(contact.zone.zoneName, region) and (fn:containsIgnoreCase(contact.position, query) or fn:containsIgnoreCase(contact.fullName, query) or fn:containsIgnoreCase(contact.telephone, query)  or fn:containsIgnoreCase(contact.email, query))}">
                     
  					
  					
  						
  											<div class='row contact-row'> 
	     										<div class="column StaffName">
	     												<c:choose>
															<c:when test="${ not empty contact.email }">
															<a href="mailto:${contact.email}">${ contact.fullName }</a>
															</c:when>
															<c:otherwise>
															${ contact.fullName }
															</c:otherwise>
														</c:choose>
	     										</div>
	     										<div class="column StaffPosition">${ contact.position }</div>
	     										<div class="column StaffTelephone">${ contact.telephone }</div>	     										
	     										<div class="column StaffSort">${ contact.sortorder }</div>
	     										<div class="column StaffOptions">
	     										<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
	     										
	     										</div>
	     									</div> 
	     				<c:set var="containse" value="${containse + 1}"/>				
	     		</c:when>
	     		</c:choose>		
	     				
	     									
  					</c:forEach>
  			</div>
                  
                  
            <c:if test="${ containse eq '0' }">No results found.</c:if>
				<c:if test="${ containse > '0' }">${containse} result(s) found.</c:if>
				<c:if test="${ containse > '10' }">Try narrowing your search criteria if you cannot find who you are looking for.</c:if>
				<br/><br/>	      
                  
                  
                                  
                    
            

    
    </div>
                  	 
                   </esd:SecurityAccessRequired>
		
		<br/><br/>
		<div align="center">             	
	     	<a href="staff_directory.jsp" class="btn btn-sm btn-danger" style="color:white;">Back to Staff List</a>               	
	       	</div>
		
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2018 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div></div>
    <br/>
    
   
    
    
    
    
  </body>

</html>