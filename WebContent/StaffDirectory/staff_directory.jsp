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
			"order" : [[ 7, "asc" ],[6,"asc"],[8,"asc"]],			
			  "paging":   false,
			  "searching": true,			 
				responsive: true,
				"lengthChange": false,
				"columnDefs": [
					 {
			                "targets": [5,8],			               
			                "searchable": false,
			                "visible": true,
			            },
			            {
			                "targets": [6,7],			               
			                "searchable": false,
			                "visible": false,
			            },
			            {
			                "targets": [0,1,2,3,4,5,8],			               
			                "sortable": false,
			                
			            },
			        ]
		});
		
				
		
	
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
	});

	
	
	
		
		</script>
 </head>

	<body>
  <div class="siteHeaderGreen">Staff Directory Administration (<span class="provincialCount">0</span>)</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Staff Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
					
Below is a complete list of district office staff as entered. This data will display on the public website under the District Staff Directory. The system is divided into 4 office locations: St. John's, Gander, Corner Brook, and Happy Valley - Goose Bay (HV-GB). <p>

If no location is set, it will display NOT-SET or UNKNOWN in the Location field. Please update these entries by editing the persons main office location. If the user is at a school, set the location as the closest office responsible for that school. 
You will also notice any vacancies as red. Please update these when possible, or remove if the position will no longer be filled. All results are color-coded by division and office, and sorted by Division, Location, and Sort Number.<p>
<b>For the head of a division, assign sort order number 1 to have their title highlighted and displayed at the start of the listings. Always try to order the sort by ranking with priority sort 1 to the division head. <p>
 Please make sure you keep your office staff directory updated at all times and remove anyone that has left unless the position will be filled soon, you can then set the position as vacant on the edit page.
<p>
<div align="center"><a href='addStaffDirectoryContact.html' class="btn btn-sm btn-success" style="color:White;" title="Add new Contact">Add New Contact</a>&nbsp; <a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to Staff Room</a></div>

<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING"> 

      
           
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:white;font-size:12px;">											
					<th width="15%">NAME</th>											
					<th width="35%">POSITION</th>
					<th width="10%">TELEPHONE</th>		
					<th width="15%">DIVISION</th>			
					<th width="10%">LOCATION</th>				
					<th width="10%">OPTIONS</th>
					<th width="*">REG# 6</th>
					<th width="*">DIV# 7</th>
					<th width="*">SORT# 8</th>		
				</tr>
				</thead>
				<tbody>    
				
				
					                      
				<c:forEach items='${ contacts }' var='contact'>						
				
	
				<c:set var="provincialCnt" value="${provincialCnt+1 }"/>
				
  				
  				<tr>  		
  									
  					
  					<c:choose>
					<c:when test="${ not empty contact.email and contact.fullName ne 'VACANT' and contact.fullName ne '*** VACANT ***' }">
						<td width="15%"><a href="mailto:${contact.email}">${ contact.fullName }</a></td>
					</c:when>
					<c:when test="${ contact.fullName eq 'VACANT' or contact.fullName eq '*** VACANT ***' }">
						<td width="15%" style="color:white;background-color:Red;">*** VACANT ***</td>
					</c:when>
					<c:otherwise>
						<td width="15%">${ contact.fullName }</td>
					</c:otherwise>
					</c:choose>
									   										
	     			<td width="35%">${ contact.position }</td>
	     			<td width="10%">${ contact.telephone }</td> 
	     			
	     			<c:choose>
	     			<c:when test="${contact.division.id eq '1'}">
	     			<td width="15%" style="text-align:center;background-color:rgba(255, 218, 185,0.5);">DIRECTOR'S OFFICE</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '2'}">
	     			<td width="15%" style="text-align:center;background-color:rgba(178, 34, 34,0.2);">PROGRAMS</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '3'}">
	     			<td width="15%" style="text-align:center;background-color:rgba(47, 79, 79,0.2);">HUMAN RESOURCES</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '4'}">
	     			<td width="15%" style="text-align:center;background-color:rgba(30, 144, 255,0.2);">CORPORATE SERVICES</td>
	     			</c:when>
	     			<c:otherwise>
	     			<td width="15%" style="text-align:center;background-color:Silver;">UNKNOWN</td>
	     			</c:otherwise>
	     			</c:choose>	     			
	     			
	     			
	     			<c:choose>
					<c:when test="${ (contact.zone.zoneName eq 'eastern') or (contact.zone.zoneName eq 'avalon') or (contact.zone.zoneName eq 'AVALON') }">
							<td style="text-align:center;background-color:rgba(191, 0, 0, 0.3);">ST. JOHN'S</td>																								
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'central')}">
							<td style="text-align:center;background-color:rgba(143, 188, 143, 0.3);">GANDER</td>																				
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'western')}">
							<td style="text-align:center;background-color:rgba(255, 132, 0, 0.13);">CORNER BROOK</td>																								
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'labrador')}">
							<td style="text-align:center;background-color:rgba(127, 130, 255, 0.3);">HV-GB</td>																																												
						</c:when>	
						<c:when test="${ (contact.zone.zoneName eq 'provincial')}">
							<td style="text-align:center;background-color:Red;color:White">NOT SET</td>
						</c:when>	
						<c:otherwise>
							<td style="text-align:center;background-color:Red;color:White;">UNKNOWN</td>
						</c:otherwise>
					</c:choose>     			
	     			
	     			
	     			<td width="10%">	
					<a href="editStaffDirectoryContact.html?id=${contact.contactId}" class="btn btn-xs btn-success" style="color:White;">EDIT</a>
					<a href="#" onclick="if(confirm('Are you sure you want to delete ${fn:escapeXml(contact.fullName)}?'))document.location.href='deleteDirectoryStaff.html?id=${contact.contactId}';" class="btn btn-xs btn-danger" style="color:White;">DEL</a>
					</td>
					<td width="*">
					<c:choose>
					<c:when test="${ (contact.zone.zoneName eq 'provincial')}">1</c:when>
					<c:when test="${ (contact.zone.zoneName eq 'eastern') or (contact.zone.zoneName eq 'avalon') or (contact.zone.zoneName eq 'AVALON') }">2</c:when>
					<c:when test="${ (contact.zone.zoneName eq 'central')}">3</c:when>
					<c:when test="${ (contact.zone.zoneName eq 'western')}">4</c:when>
					<c:otherwise>5</c:otherwise>
					</c:choose>		
					</td>
					<td width="*" class="divisionNum">${ contact.division.id }</td>
					
					<td width="5%">${ contact.sortorder }</td>  	
					
					
					</tr>
					
								
					
	     		</c:forEach>
	     		
  		</tbody>
  		</table>
  		
  	

</esd:SecurityAccessRequired>
</div>

 
 
    
     <script>
  			
  			$(".provincialCount").html("${provincialCnt}"); 
  		    		
  		
    </script>
    
  </body>

</html>