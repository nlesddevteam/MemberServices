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
				"pageLength": 25,
				"lengthMenu": [[25, 50, 75, 100, -1], [25, 50, 75, 100, "All"]],
				"lengthChange": true,
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

If no location is set, it will display UNKNOWN or OTHER/SCHOOL in the Location field. You can also select OTHER/SCHOOL if the person is located outside the main office, but you should enter the proper location in the Position/Title section(3) when adding/editing. Please update these entries by editing the persons main office location. If the user is at a school, set the location as the closest office responsible for that school. 
You will also notice any vacancies as red. Please update these when possible, or remove if the position will no longer be filled. All results are color-coded by division and office, and sorted by Division, Location, and Sort Number.<p>
<p>For the head of a division, assign sort order number 1 to have their title highlighted and displayed at the start of the listings. Always try to order the sort by ranking with priority sort 1 to the division head. <p>
 Please make sure you keep your office staff directory updated at all times and remove anyone that has left unless the position will be filled soon, you can then set the position as vacant on the edit page.
<p>

<div class="alert alert-warning">NOTE: After making a change, it may take up to 5 minutes before the change displays on below list, and up to 20 minutes before it displays on live website. Please give it time before attempting to change again.
</div>

<div align="center"><b>NOTICE:</b> If you see a <span style="color:White;background-color:Red;"> &nbsp; *** NEED UPDATE *** &nbsp; </span> &nbsp; or an <span style="color:White;background-color:Silver;"> &nbsp; UNKNOWN  &nbsp;</span> under Division Category, then they require updating to the new 2023 Divisions.</div><br/>

<div align="center"><a href='addStaffDirectoryContact.html' class="btn btn-sm btn-success" style="color:White;" title="Add new Contact">Add New Contact</a>&nbsp; <a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to Staff Room</a></div>

<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-STAFFING"> 

      
           
    <table class="staffingTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:white;font-size:12px;">											
					<th width="15%">NAME</th>											
					<th width="35%">POSITION</th>
					<th width="10%">TELEPHONE</th>
					<th width="15%">DIVISION</th>
					<th width="10%">OFFICE</th>			
					<th width="10%">OPTIONS</th>
					<th width="*">REG# 6</th>
					<th width="*">DIV# 7</th>
					<th width="*">SORT#</th>		
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
	     			<c:when test="${contact.division.id eq '1' or contact.division.id eq '4' or contact.division.id eq '5' }">
	     			<td width="15%" class="divisionERROR divisions">*** NEED UPDATE ***</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '2' }">
	     			<td width="15%" class="divisionPROGRAMS divisions">PROGRAMS</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '3'}">
	     			<td width="15%" class="divisionHR divisions">HUMAN RESOURCES</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '6'}">
	     			<td width="15%" class="divisionCORPORATESERVICES divisions">CORPORATE SERVICES</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '7'}">
	     			<td width="15%" class="divisionSTUDENTSERVICES divisions">STUDENT SERVICES</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '8'}">
	     			<td width="15%" class="divisionSCHOOLSYSTEMS divisions">SCHOOL SYSTEMS</td>
	     			</c:when>
	     			<c:when test="${contact.division.id eq '9'}">
	     			<td width="15%" class="divisionEXECUTIVE divisions">EXECUTIVE</td>
	     			</c:when>
	     			<c:otherwise>
	     			<td width="15%" class="divisionERROR divisions">UNKNOWN</td>
	     			</c:otherwise>
	     			</c:choose>	     			
	     			<c:choose>
					<c:when test="${ (contact.zone.zoneName eq 'eastern') or (contact.zone.zoneName eq 'avalon') or (contact.zone.zoneName eq 'AVALON') }">
							<td class="officeAvalon" style="vertical-align:middle;text-align:center;">AVALON</td>																								
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'central')}">
							<td class="officeCentral" style="vertical-align:middle;text-align:center;">CENTRAL</td>																				
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'western')}">
							<td class="officeWestern" style="vertical-align:middle;text-align:center;">WESTERN</td>																								
						</c:when>
						<c:when test="${ (contact.zone.zoneName eq 'labrador')}">
							<td class="officeLabrador" style="vertical-align:middle;text-align:center;">LABRADOR</td>																																												
						</c:when>	
						<c:when test="${ (contact.zone.zoneName eq 'provincial')}">
							<td class="officeProvincial" style="vertical-align:middle;text-align:center;">GOV/DEPT</td>
						</c:when>	
						<c:otherwise>
							<td class="officeError" style="vertical-align:middle;text-align:center;">UNKNOWN</td>
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
					<td width="*" class="divisionNum">
					<c:choose>
													<c:when test="${ (contact.division.id eq '9')}">1</c:when>
													<c:when test="${ (contact.division.id eq '2')}">2</c:when>
													<c:when test="${ (contact.division.id eq '7')}">3</c:when>
													<c:when test="${ (contact.division.id eq '8')}">4</c:when>
													<c:when test="${ (contact.division.id eq '3')}">5</c:when>
													<c:when test="${ (contact.division.id eq '6')}">6</c:when>
													<c:otherwise>7</c:otherwise>
													</c:choose>
					</td>
					
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