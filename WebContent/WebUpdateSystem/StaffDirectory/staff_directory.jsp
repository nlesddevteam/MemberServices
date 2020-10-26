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
<c:set var="avalona" value="0" />
<c:set var="avalonb" value="0" />
<c:set var="avalonc" value="0" />
<c:set var="avalond" value="0" />
<c:set var="avalone" value="0" />
<c:set var="centrala" value="0" />
<c:set var="centralb" value="0" />
<c:set var="centralc" value="0" />
<c:set var="centrald" value="0" />
<c:set var="centrale" value="0" />
<c:set var="westerna" value="0" />
<c:set var="westernb" value="0" />
<c:set var="westernc" value="0" />
<c:set var="westernd" value="0" />
<c:set var="westerne" value="0" />
<c:set var="labradora" value="0" />
<c:set var="labradorb" value="0" />
<c:set var="labradorc" value="0" />
<c:set var="labradord" value="0" />
<c:set var="labradore" value="0" />
<c:set var="provinciala" value="0" />
<c:set var="provincialb" value="0" />
<c:set var="provincialc" value="0" />
<c:set var="provinciald" value="0" />
<c:set var="provinciale" value="0" />

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
			<link rel="stylesheet" href="css/staff_directory.css" />
	
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
				<div class="pageTitleHeader siteHeaders">District Office Staff Directory</div>
                <div class="pageBody">
					 <c:if test="${ msg ne null }">  
                  		<div style="text-align:center;color:Red;font-size:14px;">${ msg }</div>
                     </c:if>                  
                  		<br/>                  
	                  	<div align="center">
		                  Below is a list of district office staff you have access to update. The system is divided into 5 regions as below. Not all users will have access to update all regions. 
		                  To narrow your search, use the form below to categorize by region and division. Please keep in mind if you create/edit a position as PROVINCIAL only the person with access to edit Provincial staff can make further changes to that staff member.<p>
		                  Results are color-coded by region:<p>
		                  <span class="region1solid" style="color:White;">&nbsp;Avalon&nbsp;</span>&nbsp;
		                  <span class="region2solid" style="color:White;">&nbsp;Central&nbsp;</span>&nbsp;
		                  <span class="region3solid" style="color:White;">&nbsp;Western&nbsp;</span>&nbsp;                
		                  <span class="region4solid" style="color:White;">&nbsp;Labrador&nbsp;</span>&nbsp;  
		                  <span class="region5solid" style="color:White;">&nbsp;Provincial&nbsp;</span>
		                  <p>
		                  <div align="center"><a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services">Back to Member Services</a></div>
		                  <p>						
						 	<jsp:include page="stafffindbar.jsp" />
						</div>          
                  <p>
                  <div style='float:right;'>                        	
                        	<a href='addStaffDirectoryContact.html' class="btn btn-sm btn-success" style="color:White;" title="Add new Contact">+ Add New Contact</a>
                  </div>
                 
                   <br/>      
                   
  <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING"> 
  	
  
  
    <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-AVALON"> 
     
     

     
     
                
    
  				<div class="row">
  				<span style="font-size:16px;font-weight:bold;" class="region1text">AVALON REGION</span><br/>
  				
  				
  <div class="panel-group" id="accordion">
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse1a">Director's Office Division</a> (<span class="avalonaCount"></span>)</h4>
    </div>
    <div id="collapse1a" class="panel-collapse collapse">
      <div class="panel-body">
  				
  				
  				
  						
	 			<div class="stafflist">	
	 			
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>											
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					
  					
  					
  					
  						<c:if test="${ (contact.zone.zoneName eq 'eastern' or contact.zone.zoneName eq 'avalon') and (contact.division.name eq \"Director's Office\")}">
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
	     				<c:set var="avalona" value="${avalona + 1}" />					
	     				</c:if>	
	     				
	     									
  					</c:forEach>
  			</div>
  	</div>
    </div>
  </div>		
  			
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse1b">Programs Division</a> (<span class="avalonbCount"></span>)</h4>
    </div>
    <div id="collapse1b" class="panel-collapse collapse">
      <div class="panel-body">	
  	
  	
  			
  			<div class="stafflist">			
  					
  					
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>										
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'eastern' or contact.zone.zoneName eq 'avalon') and (contact.division.name eq 'Programs')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										
												</div>
	     									</div> 
	     				<c:set var="avalonb" value="${avalonb + 1}" />	
	     				</c:if>							
  					</c:forEach>
  			</div></div></div></div>
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1c">Human Resources Division</a> (<span class="avaloncCount"></span>)
      </h4>
    </div>
    <div id="collapse1c" class="panel-collapse collapse">
      <div class="panel-body">	
  		
  			<div class="stafflist">			
  				
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'eastern' or contact.zone.zoneName eq 'avalon') and (contact.division.name eq 'Human Resources')}">
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
	     									<c:set var="avalonc" value="${avalonc + 1}" />	
	     				</c:if>							
  					</c:forEach>
  					
  					</div></div></div></div>
  					
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1d">Corporate Services Division</a> (<span class="avalondCount"></span>)
      </h4>
    </div>
    <div id="collapse1d" class="panel-collapse collapse">
      <div class="panel-body">			
  					  					
  			
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'eastern' or fn:containsIgnoreCase(contact.zone.zoneName, 'avalon')) and (fn:containsIgnoreCase(contact.division.name,'Finance') or fn:containsIgnoreCase(contact.division.name,'Corporate') or fn:containsIgnoreCase(contact.division.name,'Operations'))}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div>
	     									<c:set var="avalond" value="${avalond + 1}" />	 
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  <div class="panel panel-default" style="display:none;">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1e">Operations Division</a> (<span class="avaloneCount"></span>)
      </h4>
    </div>
    <div id="collapse1e" class="panel-collapse collapse">
      <div class="panel-body">
  					
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region1solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region1solid">POSITION</div>									        
											<div class="column header StaffTelephone region1solid">TELEPHONE</div>
											<div class="column header StaffSort region1solid">SORT #</div> 
											<div class="column header StaffOptions region1solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'eastern' or contact.zone.zoneName eq 'avalon') and (contact.division.name eq 'Operations')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="avalone" value="${avalone + 1}" />	
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  					
  					
  					
  </div>	</div>				
  </esd:SecurityAccessRequired>
  
  

  
  
  
   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-CENTRAL"> 
           
    
  				<div class="row">
  				<span style="font-size:16px;font-weight:bold;" class="region2text">CENTRAL REGION</span><br/>
  		
  <div class="panel-group" id="accordion">		
  	<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2b">Programs Division</a> (<span class="centralbCount"></span>)</h4>
    </div>
    <div id="collapse2b" class="panel-collapse collapse">
      <div class="panel-body">	
  		
  		
  			<div class="stafflist">			
  					
  					
  					<div class="row">
									   		<div class="column header StaffName region2solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region2solid">POSITION</div>									        
											<div class="column header StaffTelephone region2solid">TELEPHONE</div>										
											<div class="column header StaffSort region2solid">SORT #</div> 
											<div class="column header StaffOptions region2solid">OPTIONS</div>   
					</div>
  					
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'central') and (contact.division.name eq 'Programs')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										
												</div>
	     									</div>
	     									<c:set var="centralb" value="${centralb + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  			</div>
  			</div></div></div>
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2c">Human Resources Division</a> (<span class="centralcCount"></span>)</h4>
    </div>
    <div id="collapse2c" class="panel-collapse collapse">
      <div class="panel-body">
  			  			
  		
  			<div class="stafflist">			
  				
  					<div class="row">
									   		<div class="column header StaffName region2solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region2solid">POSITION</div>									        
											<div class="column header StaffTelephone region2solid">TELEPHONE</div>
											<div class="column header StaffSort region2solid">SORT #</div> 
											<div class="column header StaffOptions region2solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'central') and (contact.division.name eq 'Human Resources')}">
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
	     									<c:set var="centralc" value="${centralc + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  					</div></div></div></div>
  					
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2d">Corporate Services Division</a> (<span class="centraldCount"></span>)</h4>
    </div>
    <div id="collapse2d" class="panel-collapse collapse">
      <div class="panel-body">
  			
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region2solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region2solid">POSITION</div>									        
											<div class="column header StaffTelephone region2solid">TELEPHONE</div>
											<div class="column header StaffSort region2solid">SORT #</div> 
											<div class="column header StaffOptions region2solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'central') and (fn:containsIgnoreCase(contact.division.name,'Finance') or fn:containsIgnoreCase(contact.division.name,'Corporate') or fn:containsIgnoreCase(contact.division.name,'Operations'))}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="centrald" value="${centrald + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  <div class="panel panel-default" style="display:none;">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse2e">Operations Division</a> (<span class="centraleCount"></span>)</h4>
    </div>
    <div id="collapse2e" class="panel-collapse collapse">
      <div class="panel-body">
  
  					
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region2solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region2solid">POSITION</div>									        
											<div class="column header StaffTelephone region2solid">TELEPHONE</div>
											<div class="column header StaffSort region2solid">SORT #</div> 
											<div class="column header StaffOptions region2solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'central') and (contact.division.name eq 'Operations')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="centrale" value="${centrale + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  
  </div>	</div>				
  </esd:SecurityAccessRequired>
  
  

  
  
  
   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-WESTERN"> 
             
    
  				<div class="row">
  				<span style="font-size:16px;font-weight:bold;" class="region3text">WESTERN REGION</span><br/>
  	
  	
  	<div class="panel-group" id="accordion">		
  	<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse3b">Programs Division</a> (<span class="westernbCount"></span>)</h4>
    </div>
    <div id="collapse3b" class="panel-collapse collapse">
      <div class="panel-body">	
  	
  	
  	
  			<div class="stafflist">			
  					
  					
  					<div class="row">
									   		<div class="column header StaffName region3solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region3solid">POSITION</div>									        
											<div class="column header StaffTelephone region3solid">TELEPHONE</div>										
											<div class="column header StaffSort region3solid">SORT #</div> 
											<div class="column header StaffOptions region3solid">OPTIONS</div>   
					</div>
  					
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'western') and (contact.division.name eq 'Programs')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										
												</div>
	     									</div> 
	     									<c:set var="westernb" value="${westernb + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  			</div>
  			</div></div></div>
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse3c">Human Resources Division</a> (<span class="westerncCount"></span>)</h4>
    </div>
    <div id="collapse3c" class="panel-collapse collapse">
      <div class="panel-body">	
  			
  			
  			<div class="stafflist">			
  				
  					<div class="row">
									   		<div class="column header StaffName region3solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region3solid">POSITION</div>									        
											<div class="column header StaffTelephone region3solid">TELEPHONE</div>
											<div class="column header StaffSort region3solid">SORT #</div> 
											<div class="column header StaffOptions region3solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'western') and (contact.division.name eq 'Human Resources')}">
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
	     									<c:set var="westernc" value="${westernc + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  					</div></div></div></div>
  					
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse3d">Corporate Services Division</a> (<span class="westerndCount"></span>)</h4>
    </div>
    <div id="collapse3d" class="panel-collapse collapse">
      <div class="panel-body">		
  					
  			
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region3solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region3solid">POSITION</div>									        
											<div class="column header StaffTelephone region3solid">TELEPHONE</div>
											<div class="column header StaffSort region3solid">SORT #</div> 
											<div class="column header StaffOptions region3solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'western') and (fn:containsIgnoreCase(contact.division.name,'Finance') or fn:containsIgnoreCase(contact.division.name,'Corporate') or fn:containsIgnoreCase(contact.division.name,'Operations'))}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="westernd" value="${westernd + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  
  <div class="panel panel-default" style="display:none;">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse3e">Operations Division</a> (<span class="westerneCount"></span>)</h4>
    </div>
    <div id="collapse3e" class="panel-collapse collapse">
      <div class="panel-body"> 
  
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region3solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region3solid">POSITION</div>									        
											<div class="column header StaffTelephone region3solid">TELEPHONE</div>
											<div class="column header StaffSort region3solid">SORT #</div> 
											<div class="column header StaffOptions region3solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'western') and (contact.division.name eq 'Operations')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="westerne" value="${westerne + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  
  </div>	</div>				
  </esd:SecurityAccessRequired>
  
    
   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-LABRADOR"> 
               
    
  				<div class="row">
  				<span style="font-size:16px;font-weight:bold;" class="region4text">LABRADOR REGION</span><br/>
  	
  	
  	<div class="panel-group" id="accordion">		
  	<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse4b">Programs Division</a> (<span class="labradorbCount"></span>)</h4>
    </div>
    <div id="collapse4b" class="panel-collapse collapse">
      <div class="panel-body">	
  	
  				
  			<div style="clear:both;padding-bottom:10px;"></div>
  			<span style="font-size:14px;font-weight:bold;color:Black;">Programs Division</span>
  			<div class="stafflist">			
  					
  					
  					<div class="row">
									   		<div class="column header StaffName region4solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region4solid">POSITION</div>									        
											<div class="column header StaffTelephone region4solid">TELEPHONE</div>										
											<div class="column header StaffSort region4solid">SORT #</div> 
											<div class="column header StaffOptions region4solid">OPTIONS</div>   
					</div>
  					
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'labrador') and (contact.division.name eq 'Programs')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										
												</div>
	     									</div> 
	     									<c:set var="labradorb" value="${labradorb + 1}" /> 
	     				</c:if>							
  					</c:forEach>
  			</div></div></div></div>
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse4c">Human Resources Division</a> (<span class="labradorcCount"></span>)</h4>
    </div>
    <div id="collapse4c" class="panel-collapse collapse">
      <div class="panel-body">	
  		
  			<div class="stafflist">			
  				
  					<div class="row">
									   		<div class="column header StaffName region4solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region4solid">POSITION</div>									        
											<div class="column header StaffTelephone region4solid">TELEPHONE</div>
											<div class="column header StaffSort region4solid">SORT #</div> 
											<div class="column header StaffOptions region4solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'labrador') and (contact.division.name eq 'Human Resources')}">
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
	     									<c:set var="labradorc" value="${labradorc + 1}" />
	     				</c:if>							
  					</c:forEach>
  					
  					</div></div></div></div>
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse4d">Corporate Services Division</a> (<span class="labradordCount"></span>)</h4>
    </div>
    <div id="collapse4d" class="panel-collapse collapse">
      <div class="panel-body">
  			
  		
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region4solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region4solid">POSITION</div>									        
											<div class="column header StaffTelephone region4solid">TELEPHONE</div>
											<div class="column header StaffSort region4solid">SORT #</div> 
											<div class="column header StaffOptions region4solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'labrador') and (fn:containsIgnoreCase(contact.division.name,'Finance') or fn:containsIgnoreCase(contact.division.name,'Corporate') or fn:containsIgnoreCase(contact.division.name,'Operations'))}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="labradord" value="${labradord + 1}" />
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  <div class="panel panel-default" style="display:none;">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse4e">Operations Division</a> (<span class="labradoreCount"></span>)</h4>
    </div>
    <div id="collapse4e" class="panel-collapse collapse">
      <div class="panel-body">
  
  				
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region4solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region4solid">POSITION</div>									        
											<div class="column header StaffTelephone region4solid">TELEPHONE</div>
											<div class="column header StaffSort region4solid">SORT #</div> 
											<div class="column header StaffOptions region4solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'labrador') and (contact.division.name eq 'Operations')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="labradore" value="${labradore + 1}" />
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  
  </div>	</div>				
  </esd:SecurityAccessRequired>
  
  

  
  
  
   <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-PROVINCIAL"> 
    
    	
    
              
    
  				<div class="row">
  				<span style="font-size:16px;font-weight:bold;" class="region5text">PROVINCIAL</span><br/>
  		
  	<div class="panel-group" id="accordion">		
  	<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse5a">Director's Office Division</a> (<span class="provincialaCount"></span>)</h4>
    </div>
    <div id="collapse5a" class="panel-collapse collapse">
      <div class="panel-body">	  		
  				
  				<div class="stafflist">	
	 			
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>											
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					
  					<c:forEach items='${ contacts }' var='contact'>
  						<c:if test="${ (contact.zone.zoneName eq 'provincial') and (contact.division.name eq \"Director's Office\")}">
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
	     									<c:set var="provinciala" value="${provinciala + 1}" />
	     				</c:if>							
  					</c:forEach>
  			</div></div></div></div>
  			
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse5b">Programs Division</a> (<span class="provincialbCount"></span>)</h4>
    </div>
    <div id="collapse5b" class="panel-collapse collapse">
      <div class="panel-body">	  		
  			
  			
  			<div class="stafflist">			
  					
  					
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>										
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					
  					
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'provincial') and (contact.division.name eq 'Programs')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										
												</div>
	     									</div> 
	     									<c:set var="provincialb" value="${provincialb + 1}" />
	     				</c:if>							
  					</c:forEach>
  			</div></div></div></div>
  			
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse5c">Human Resources Division</a> (<span class="provincialcCount"></span>)</h4>
    </div>
    <div id="collapse5c" class="panel-collapse collapse">
      <div class="panel-body">	  			
  			
  			<div class="stafflist">			
  				
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'provincial') and (contact.division.name eq 'Human Resources')}">
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
	     									<c:set var="provincialc" value="${provincialc + 1}" />
	     				</c:if>							
  					</c:forEach>
  					
  					</div></div></div></div>
  					
  			<div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse5d">Corporate Services Division</a> (<span class="provincialdCount"></span>)</h4>
    </div>
    <div id="collapse5d" class="panel-collapse collapse">
      <div class="panel-body">			
  					
  			
  			<div class="stafflist">	  					
  					
  					  					
  					
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'provincial') and (fn:containsIgnoreCase(contact.division.name,'Finance') or fn:containsIgnoreCase(contact.division.name,'Corporate') or fn:containsIgnoreCase(contact.division.name,'Operations'))}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="provinciald" value="${provinciald + 1}" />
	     				</c:if>							
  					</c:forEach>
  					
  </div></div></div></div>
  
  
  <div class="panel panel-default" style="display:none;">
    <div class="panel-heading">
      <h4 class="panel-title"><a data-toggle="collapse" data-parent="#accordion" href="#collapse5e">Operations Division</a> (<span class="provincialeCount"></span>)</h4>
    </div>
    <div id="collapse5e" class="panel-collapse collapse">
      <div class="panel-body">
  			<div class="stafflist">	  					
  					<div class="row">
									   		<div class="column header StaffName region5solid"><a name='avalon'></a>NAME</div>	
									        <div class="column header StaffPosition region5solid">POSITION</div>									        
											<div class="column header StaffTelephone region5solid">TELEPHONE</div>
											<div class="column header StaffSort region5solid">SORT #</div> 
											<div class="column header StaffOptions region5solid">OPTIONS</div>   
					</div>
  					<c:forEach items='${ contacts }' var='contact'>
  					<c:if test="${ (contact.zone.zoneName eq 'provincial') and (contact.division.name eq 'Operations')}">
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
	     										<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" style="color:White;" class="btn btn-xs btn-danger">DEL</a>
	     										</div>
	     									</div> 
	     									<c:set var="provinciale" value="${provinciale + 1}" />
	     				</c:if>							
  					</c:forEach>  					
  			</div>
  		</div>
  	</div>
  </div>
    
  </div></div>				
  </esd:SecurityAccessRequired>
  
  </esd:SecurityAccessRequired>
  
  <p>
                     <div align="center">
                     <a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services">Back to Member Services</a>
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
  			$(".avalonaCount").html("${avalona}");   			
  			$(".centralaCount").html("${centrala}"); 
  			$(".westernaCount").html("${westerna}"); 
  			$(".labradoraCount").html("${labradora}"); 
  			$(".provincialaCount").html("${provinciala}"); 
  			$(".avalonbCount").html("${avalonb}");   			
  			$(".centralbCount").html("${centralb}"); 
  			$(".westernbCount").html("${westernb}"); 
  			$(".labradorbCount").html("${labradorb}"); 
  			$(".provincialbCount").html("${provincialb}"); 
  			$(".avaloncCount").html("${avalonc}");   			
  			$(".centralcCount").html("${centralc}"); 
  			$(".westerncCount").html("${westernc}"); 
  			$(".labradorcCount").html("${labradorc}"); 
  			$(".provincialcCount").html("${provincialc}"); 
  			$(".avalondCount").html("${avalond}");   			
  			$(".centraldCount").html("${centrald}"); 
  			$(".westerndCount").html("${westernd}"); 
  			$(".labradordCount").html("${labradord}"); 
  			$(".provincialdCount").html("${provinciald}"); 
  			$(".avaloneCount").html("${avalone}");   			
  			$(".centraleCount").html("${centrale}"); 
  			$(".westerneCount").html("${westerne}"); 
  			$(".labradoreCount").html("${labradore}"); 
  			$(".provincialeCount").html("${provinciale}"); 
    		
  		
    </script>
    
  </body>

</html>