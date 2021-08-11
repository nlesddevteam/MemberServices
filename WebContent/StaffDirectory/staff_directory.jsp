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
<c:set var="avalonCnt" value="0" />
<c:set var="centralCnt" value="0" />
<c:set var="westernCnt" value="0" />
<c:set var="labradorCnt" value="0" />
<c:set var="provincialCnt" value="0" />

<head>
		<title>Staff Directory</title>			

  			<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    		<meta charset="utf-8">    		
	<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		aTable = $(".staffingTable").dataTable({
			"order" : [[4,"asc"],[0,"asc"]],			
			  "paging":   false,
			  "searching": true,			 
				responsive: true,
				"lengthChange": false,
				"columnDefs": [
					 {
			                "targets": [0,5],			               
			                "searchable": false,
			                "visible": true
			            }
			        ]
		});
		
				
		
	
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
	});

	
	
	
		
		</script>
 </head>

	<body>
  <div class="siteHeaderGreen">Staff Directory Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Staff Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

 
					
Below is a list of district office staff you have access to update. The system is divided into 5 regions as below. Not all users will have access to update all regions. 
Please keep in mind if you create/edit a position as PROVINCIAL only the person with access to edit Provincial staff can make further changes to that staff member.<p>
Results are color-coded by region:
<br/><br/>
<b>For the head of a division in your region, assign sort order number 1 to have their title highlighted and displayed.</b> Always sort the staff based on priority order where possible.
      Use the search at right in each dropdown to help narrow to the person your are looking for. Listings are sorted by Division and Sort Number. Click on the column header to sort by that column.	  
      
<br/>
<div align="center"><a href='addStaffDirectoryContact.html' class="btn btn-sm btn-success" style="color:White;" title="Add new Contact">Add New Contact</a>&nbsp; <a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to Staff Room</a></div>
<br/><br/>

<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING"> 


                 
   <div id="accordion">


<!-- PROVINCIAL STAFF -->
<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-PROVINCIAL"> 


  <div class="card">
    <div class="card-header region5">
      <a class="card-link cardP" data-toggle="collapse" href="#collapseProvincial"><span class="iconP"><i class='fas fa-folder'></i></span> PROVINCIAL STAFF</a>  (<span class="provincialCount">0</span>) 
  </div> 
    
     <div id="collapseProvincial" class="collapse" data-parent="#accordion">
      <div class="card-body">  
      
      <script>
 				$('.cardP').on("click", function(e){       
				 if( $("#collapseProvincial").hasClass("show")) {
	                	$(".iconP").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $(".iconP").html("<i class='fas fa-folder-open'></i>");
	                }
				 e.preventDefault();                	 
	                	});                 
	</script>
      
      
           
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="region5solid">
					<tr style="color:white;font-size:12px;">
					<th width="5%">SORT#</th>							
					<th width="15%">NAME</th>
					<th width="10%">TELEPHONE</th>							
					<th width="35%">POSITION</th>	
					<th width="15%">DIVISION</th>							
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                          
				<c:forEach items='${ contacts }' var='contact'>			
				
				
				<c:if test="${ (contact.zone.zoneName eq 'provincial')}">
				<c:set var="provincialCnt" value="${provincialCnt+1 }"/>
				<c:choose>
				<c:when test="${ contact.sortorder eq '1' }">
  					<tr class="region5" style="font-weight:bold;text-transform:uppercase;">
  				</c:when>
  				<c:otherwise>
  					<tr>
  				</c:otherwise>
  				</c:choose>	
  					<td width="5%">${ contact.sortorder }</td>  					
  					<td width="15%">	
  					<c:choose>
					<c:when test="${ not empty contact.email }">
						<a href="mailto:${contact.email}">${ contact.fullName }</a>
					</c:when>
					<c:otherwise>
						${ contact.fullName }
					</c:otherwise>
					</c:choose>
					</td>
					<td width="10%">${ contact.telephone }</td>    										
	     			<td width="35%">${ contact.position }</td>
	     			<td width="15%">
	     			<c:choose>
	     			<c:when test="${contact.division.name eq 'Finance and Business Administration'}">
	     			Corporate Services
	     			</c:when>
	     			<c:otherwise>
	     			${contact.division.name}
	     			</c:otherwise>
	     			</c:choose>
	     			</td>
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					</tr>
					</c:if>
	     		</c:forEach>
  		</tbody>
  		</table>
  		</div>
  		</div>
  		</div>
 </esd:SecurityAccessRequired> 
  
 <!--  AVALON STAFF -->
 <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-AVALON"> 
 
 <div class="card">
    <div class="card-header region1">
      <a class="card-link cardA" data-toggle="collapse" href="#collapseAvalon"><span class="iconA"><i class='fas fa-folder'></i></span> AVALON STAFF</a>  (<span class="avalonCount">0</span>)
  </div> 
    <script>
 				$('.cardA').on("click", function(e){       
				 if( $("#collapseAvalon").hasClass("show")) {
	                	$(".iconP").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $(".iconP").html("<i class='fas fa-folder-open'></i>");
	                }
				 e.preventDefault();                	 
	                	});                 
	</script>
     <div id="collapseAvalon" class="collapse" data-parent="#accordion">
      <div class="card-body">       
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="region1solid">
					<tr style="color:white;font-size:12px;">
					<th width="5%">SORT#</th>							
					<th width="15%">NAME</th>
					<th width="10%">TELEPHONE</th>							
					<th width="45%">POSITION</th>	
					<th width="15%">DIVISION</th>							
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                          
				<c:forEach items='${ contacts }' var='contact'>			
				<c:if test="${ (contact.zone.zoneName eq 'avalon' or contact.zone.zoneName eq 'eastern')}">
				<c:set var="avalonCnt" value="${avalonCnt+1 }"/>
				<c:choose>
				<c:when test="${ contact.sortorder eq '1' }">
  					<tr class="region1" style="font-weight:bold;text-transform:uppercase;">
  				</c:when>
  				<c:otherwise>
  					<tr>
  				</c:otherwise>
  				</c:choose>	
  					<td width="5%">${ contact.sortorder }</td>  					
  					<td width="15%">	
  					<c:choose>
					<c:when test="${ not empty contact.email }">
						<a href="mailto:${contact.email}">${ contact.fullName }</a>
					</c:when>
					<c:otherwise>
						${ contact.fullName }
					</c:otherwise>
					</c:choose>
					</td>
					<td width="10%">${ contact.telephone }</td>    										
	     			<td width="45%">${ contact.position }</td>
	     			<td width="15%">
	     			<c:choose>
	     			<c:when test="${contact.division.name eq 'Finance and Business Administration'}">
	     			Corporate Services
	     			</c:when>
	     			<c:otherwise>
	     			${contact.division.name}
	     			</c:otherwise>
	     			</c:choose>
	     			</td>
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					</tr>
					</c:if>
	     		</c:forEach>
  		</tbody>
  		</table>
  		</div>
  		</div>
  		</div>
 
 </esd:SecurityAccessRequired>
 
  		
 <!-- CENTRAL STAFF --> 		
 <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-CENTRAL"> 
 
  		
 <div class="card">
    <div class="card-header region2">
      <a class="card-link cardC" data-toggle="collapse" href="#collapseCentral"><span class="iconC"><i class='fas fa-folder'></i></span> CENTRAL STAFF</a>  (<span class="centralCount">0</span>)
  </div> 
    <script>
 				$('.cardC').on("click", function(e){       
				 if( $("#collapseProvincial").hasClass("show")) {
	                	$(".iconC").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $(".iconC").html("<i class='fas fa-folder-open'></i>");
	                }
				 e.preventDefault();                	 
	                	});                 
	</script>
     <div id="collapseCentral" class="collapse" data-parent="#accordion">
      <div class="card-body">       
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="region2solid">
					<tr style="color:white;font-size:12px;">
					<th width="5%">SORT#</th>							
					<th width="15%">NAME</th>
					<th width="10%">TELEPHONE</th>							
					<th width="45%">POSITION</th>	
					<th width="15%">DIVISION</th>							
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                          
				<c:forEach items='${ contacts }' var='contact'>			
				
				
				<c:if test="${ (contact.zone.zoneName eq 'central')}">
				<c:set var="centralCnt" value="${centralCnt+1 }"/>
				<c:choose>
				<c:when test="${ contact.sortorder eq '1' }">
  					<tr class="region2" style="font-weight:bold;text-transform:uppercase;">
  				</c:when>
  				<c:otherwise>
  					<tr>
  				</c:otherwise>
  				</c:choose>	
  					<td width="5%">${ contact.sortorder }</td>  					
  					<td width="15%">	
  					<c:choose>
					<c:when test="${ not empty contact.email }">
						<a href="mailto:${contact.email}">${ contact.fullName }</a>
					</c:when>
					<c:otherwise>
						${ contact.fullName }
					</c:otherwise>
					</c:choose>
					</td>
					<td width="10%">${ contact.telephone }</td>    										
	     			<td width="45%">${ contact.position }</td>
	     			<td width="15%">
	     			<c:choose>
	     			<c:when test="${contact.division.name eq 'Finance and Business Administration'}">
	     			Corporate Services
	     			</c:when>
	     			<c:otherwise>
	     			${contact.division.name}
	     			</c:otherwise>
	     			</c:choose>
	     			</td>
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					</tr>
					</c:if>
	     		</c:forEach>
  		</tbody>
  		</table>
  		</div>
  		</div>
  		</div>
 </esd:SecurityAccessRequired>
 
 
  <!-- WESTERN STAFF --> 		
 <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-WESTERN"> 
 
  		
 <div class="card">
    <div class="card-header region3">
      <a class="card-link cardW" data-toggle="collapse" href="#collapseWestern"><span class="iconW"><i class='fas fa-folder'></i></span> WESTERN STAFF</a>  (<span class="westernCount">0</span>) 
  </div> 
    <script>
 				$('.cardW').on("click", function(e){       
				 if( $("#collapseProvincial").hasClass("show")) {
	                	$(".iconW").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $(".iconW").html("<i class='fas fa-folder-open'></i>");
	                }
				 e.preventDefault();                	 
	                	});                 
	</script>
     <div id="collapseWestern" class="collapse" data-parent="#accordion">
      <div class="card-body">       
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="region3solid">
					<tr style="color:white;font-size:12px;">
					<th width="5%">SORT#</th>							
					<th width="15%">NAME</th>
					<th width="10%">TELEPHONE</th>							
					<th width="45%">POSITION</th>	
					<th width="15%">DIVISION</th>							
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                          
				<c:forEach items='${ contacts }' var='contact'>			
				
				
				<c:if test="${ (contact.zone.zoneName eq 'western')}">
				<c:set var="westernCnt" value="${westernCnt+1 }"/>
				<c:choose>
				<c:when test="${ contact.sortorder eq '1' }">
  					<tr class="region3" style="font-weight:bold;text-transform:uppercase;">
  				</c:when>
  				<c:otherwise>
  					<tr>
  				</c:otherwise>
  				</c:choose>	
  					<td width="5%">${ contact.sortorder }</td>  					
  					<td width="15%">	
  					<c:choose>
					<c:when test="${ not empty contact.email }">
						<a href="mailto:${contact.email}">${ contact.fullName }</a>
					</c:when>
					<c:otherwise>
						${ contact.fullName }
					</c:otherwise>
					</c:choose>
					</td>
					<td width="10%">${ contact.telephone }</td>    										
	     			<td width="45%">${ contact.position }</td>
	     			<td width="15%">
	     			<c:choose>
	     			<c:when test="${contact.division.name eq 'Finance and Business Administration'}">
	     			Corporate Services
	     			</c:when>
	     			<c:otherwise>
	     			${contact.division.name}
	     			</c:otherwise>
	     			</c:choose>
	     			</td>
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					</tr>
					</c:if>
	     		</c:forEach>
  		</tbody>
  		</table>
  		</div>
  		</div>
  		</div>
 </esd:SecurityAccessRequired>
 
 
   <!-- LABRADOR STAFF --> 		
<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING-LABRADOR">   		
 <div class="card">
    <div class="card-header region4">
      <a class="card-link cardL" data-toggle="collapse" href="#collapseLabrador"><span class="iconL"><i class='fas fa-folder'></i></span> LABRADOR STAFF</a> (<span class="labradorCount">0</span>) 
  </div> 
    <script>
 				$('.cardL').on("click", function(e){       
				 if( $("#collapseProvincial").hasClass("show")) {
	                	$(".iconL").html("<i class='fas fa-folder'></i>");
	                } else {                	
	                	 $(".iconL").html("<i class='fas fa-folder-open'></i>");
	                }
				 e.preventDefault();                	 
	                	});                 
	</script>
     <div id="collapseLabrador" class="collapse" data-parent="#accordion">
      <div class="card-body">       
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="region4solid">
					<tr style="color:white;font-size:12px;">
					<th width="5%">SORT#</th>							
					<th width="15%">NAME</th>
					<th width="10%">TELEPHONE</th>							
					<th width="45%">POSITION</th>	
					<th width="15%">DIVISION</th>							
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                          
				<c:forEach items='${ contacts }' var='contact'>			
				
				
				<c:if test="${ (contact.zone.zoneName eq 'labrador')}">
				<c:set var="labradorCnt" value="${labradorCnt+1 }"/>
				<c:choose>
				<c:when test="${ contact.sortorder eq '1' }">
  					<tr class="region4" style="font-weight:bold;text-transform:uppercase;">
  				</c:when>
  				<c:otherwise>
  					<tr>
  				</c:otherwise>
  				</c:choose>	
  					<td width="5%">${ contact.sortorder }</td>  					
  					<td width="15%">	
  					<c:choose>
					<c:when test="${ not empty contact.email }">
						<a href="mailto:${contact.email}">${ contact.fullName }</a>
					</c:when>
					<c:otherwise>
						${ contact.fullName }
					</c:otherwise>
					</c:choose>
					</td>
					<td width="10%">${ contact.telephone }</td>    										
	     			<td width="45%">${ contact.position }</td>
	     			<td width="15%">
	     			<c:choose>
	     			<c:when test="${contact.division.name eq 'Finance and Business Administration'}">
	     			Corporate Services
	     			</c:when>
	     			<c:otherwise>
	     			${contact.division.name}
	     			</c:otherwise>
	     			</c:choose>
	     			</td>
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					</tr>
					</c:if>
	     		</c:forEach>
  		</tbody>
  		</table>
  		</div>
  		</div>
  		</div>
 </esd:SecurityAccessRequired>
 
  
  
</div>

</esd:SecurityAccessRequired>
 </div>
 
       
    
     <script>
  			$(".avalonCount").html("${avalonCnt}");   			
  			$(".centralCount").html("${centralCnt}"); 
  			$(".westernCount").html("${westernCnt}"); 
  			$(".labradorCount").html("${labradorCnt}"); 
  			$(".provincialCount").html("${provincialCnt}"); 
  		    		
  		
    </script>
    
  </body>

</html>