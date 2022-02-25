<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW" />

<html>
  
  <head>
   
    <TITLE>Administration</title>
    
    <script>
$('document').ready(function(){		

		
		mTable = $(".registrationTable").dataTable({
			"order" : [[0,"asc"]],		
			"bPaginate":false,			
			responsive: true,		
			"orderable": false,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',	        	
            { 
       		 extend: 'excel',	
       		 exportOptions: {           	         		 

       			columns: [ 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45]
                 },
       },
       { 
     		 extend: 'csv',	
     		 exportOptions: {         		           		 

     			columns: [ 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45]
               },
     },
         
	        ],	 "columnDefs": [
				
		            {
		                "targets": [0],	
		                "visible":false,
		                "searchable": false,
		                "orderable": false
		            },
		            
		            
		        ]			
			 
		});		
		
				
		$("tr").not(':first').hover(
		  function () {
		    $(this).css("background","yellow");
		  }, 
		  function () {
		    $(this).css("background","");
		  }
		);				
		 $('.telMask').mask('(000) 000-0000');
		 $('.pcMask').mask('S0S 0S0');
		 
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
		
});
    
    
    	
    </script>
    <style> 
    .table td {vertical-align:middle;}
   	td, th {white-space:nowrap;}
    .pcMask {text-transform:uppercase;}
    @media print {		
	.pageBreak{page-break-after: always;}			
	
  body {
  	font-size:10pt !important;   
    color: #000;
    background-color: #fff;    
     border: 0;
    padding: 0;
    margin: 0;
        
  }
  

  
}
    
    </style>
 
  </head>

  <body>
 
		<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
	  	${krp.schoolYear} Kinderstart/Kindergarten Registrants<br/>  
  				
  		</div>
  	<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;"><img src="../../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading Registrant Data, please wait...</div>		
						<br/>
		<div class='divResponseMsg alert alert-danger' align='center' style="display:none;"></div>
		
		<div style="display:none;" class="loadPage">  
		
			<div align='center' class="no-print">		
			
			<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
		</div>
		<br/>
		<div align="center" class="no-print" style="margin-bottom:10px;">	  				
						<a href="#" class="resetSort btn btn-sm btn-primary"  title="Resorts the table to default sorting.">
	  					<i class="fas fa-sync"></i> RESET SORT</a> 
	  					<a href="#" class="sortSchoolName btn btn-sm btn-warning" title="Sort Table by School and Student Name.">
	  					<i class="fas fa-sync"></i> SORT BY SCHOOL & STUDENT</a> 
	  	</div>
					<div style="clear:both;"></div>
		
		
		Below is the entire registration details of all the students registered for this registration period sorted by registration time. 
		The table is very wide by default. This is for export.	<b>You CANNOT print this datatable.</b>
		Use the options below left to export this data to a CSV or Excel spreadsheet for import into <b>PowerSchool</b>.
		
			<br/><br/>
			<table class="table registrationTable table-sm table-bordered responsive" style="font-size:11px;background-color:White;">
			<thead class="thead-dark" style="text-align:center;">
					<tr>			
					<th>SORT</th>									
					<th>REGISTRATION DATE</th>		
					<th>SCHOOL YEAR</th>	
					<th>DEPT ID</th>				
					<th>SCHOOL NAME</th>					
					<th>STREAM</th>
					<th>STUDENT ID</th>								
					<th>STUDENT LAST NAME</th>
					<th>STUDENT FIRST NAME</th>					
					<th>STUDENT GENDER</th>
					<th>STUDENT DOB</th>
					<th>STUDENT MCP</th>
					<th>STUDENT MCP EXP.</th>					
					<th>PHYSICAL ADDRESS 1</th>
					<th>PHYSICAL ADDRESS 2</th>
					<th>TOWN/CITY</th>
					<th>PROV</th>
					<th>POSTAL CODE</th>					
					<th>MAILING ADDRESS 1</th>
					<th>MAILING ADDRESS 2</th>
					<th>TOWN/CITY</th>
					<th>PROV</th>
					<th>POSTAL CODE</th>
					<th>PAR/G LASTNAME</th>
					<th>PAR/G FIRSTNAME</th>
					<th>PAR/G RELATIONS</th>
					<th>PAR/G HOME PHONE</th>
					<th>PAR/G WORK PHONE</th>
					<th>PAR/G CELL PHONE</th>
					<th>PAR/G EMAIL</th>				
					<th>SEC P/G LASTNAME</th>
					<th>SEC P/G FIRSTNAME</th>
					<th>SEC P/G RELATIONS</th>
					<th>SEC P/G HOME PHONE</th>
					<th>SEC P/G WORK PHONE</th>
					<th>SEC P/G CELL PHONE</th>
					<th>SEC P/G EMAIL</th>					
					<th>EMERGENCY LASTNAME</th>
					<th>EMERGENCY FIRSTNAME</th>
					<th>EMERGENCY TEL</th>					
					<th>CUSTODY ISSUES?</th>
					<th>HEALTH CONCERNS?</th>
					<th>REQUIRE ACCESSIBLE?</th>
					<th>EFI SIBLING?</th>						
					<th>APPROVED</th>
					<th>STATUS</th>					
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(registrants) gt 0 }">
						<c:forEach items="${registrants}" var="r">
							<tr class='period-data-row'>					
							<td width="*"><fmt:formatDate value="${r.registrationDate}" pattern="yyyyMMddHHmmss" /></td>						
							<td><fmt:formatDate value="${r.registrationDate}" pattern="MM/dd/yyyy @ hh:mm:ss" /></td>
							<td>${r.registration.schoolYear}</td>
							<td>${r.school.schoolDeptID}</td>							
							<td>${r.school.schoolName}</td>  							
							<td>${r.schoolStream.text}</td>
							<td></td>									
							<td>${r.studentLastName}</td>
							<td>${r.studentFirstName}</td>
							<td>${r.studentGender.text ne null?r.studentGender.text:""}</td>
							<td><fmt:formatDate pattern="MM/dd/yyyy" value="${r.dateOfBirth}" /></td>
							<td>${r.mcpNumber}</td>
							<td>${r.mcpExpiry}</td>
							<td>${r.physicalStreetAddress1}</td>								
							<td>${r.physicalStreetAddress2}</td>
							<td>${r.physicalCityTown}</td>
							<td>NL</td>
							<td class="pcMask">${r.physicalPostalCode}</td>
							<td>${r.mailingStreetAddress1}</td>	
							<td>${r.mailingStreetAddress2}</td>
							<td>${r.mailingCityTown}</td>
							<td>NL</td>
							<td class="pcMask">${r.mailingPostalCode}</td>							
							<c:set var="nameParts" value="${fn:split(r.primaryContactName, ' ')}" />
							<td>${nameParts[1]}</td>
							<td>${nameParts[0]}</td>
							<td>${r.primaryContactRelationship.text ne null?r.primaryContactRelationship.text:"" }</td>	
							<td class="telMask">${r.primaryContactHomePhone}</td>
							<td class="telMask">${r.primaryContactWorkPhone}</td>
							<td class="telMask">${r.primaryContactCellPhone}</td>
							<td>${r.primaryContactEmail}</td>
							<c:set var="namePartsS" value="${fn:split(r.secondaryContactName, ' ')}" />
							<td>${namePartsS[1]}</td>
							<td>${namePartsS[0]}</td>
							<td>${r.secondaryContactRelationship.text ne null?r.secondaryContactRelationship.text:"" }</td>	
							<td class="telMask">${r.secondaryContactHomePhone}</td>
							<td class="telMask">${r.secondaryContactWorkPhone}</td>
							<td class="telMask">${r.secondaryContactCellPhone}</td>
							<td>${r.secondaryContactEmail}</td>
							<c:set var="namePartsE" value="${fn:split(r.emergencyContactName, ' ')}" />
							<td>${namePartsE[1]}</td>
							<td>${namePartsE[0]}</td>
							<td class="telMask">${r.emergencyContactTelephone}</td>							
							<td>${r.custodyIssues ? "Yes" : "No"}</td>
							<td>${r.healthConcerns ? "Yes" : "No"}</td>
							<td>${r.accessibleFacility ? "Yes" : "No"}</td>
							<td>${r.efiSibling ? "Yes" : "No"}</td>							
							<td>${r.physicalAddressApproved ? "Yes" : "No"}</td>
							<td>${r.status.accepted ? "Accepted" : kr.status.waitlisted ? "Waitlisted" : "Processing"}</td>				
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td>N/A</td>
							<td>N/A</td>	
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>									
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>								
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>	
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>	
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>									
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>								
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>	
							<td>N/A</td>
							<td>N/A</td>
							<td>N/A</td>	
							<td>N/A</td>
							<td>N/A</td>														
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>
	
		<br />
		<div align='center' class="no-print">
			<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/kindergarten/admin/district/index.html' />">Back to Registration Period List</a>
			
		</div>
		<div class='divResponseMsg alert alert-danger' style='display:none;' align='center'></div>
		<br />
			</div>
			
			<script>
	//RESORT THE TABLE
    		
    		$('.resetSort').click( function() {
    			mTable.fnSort([0,"asc"]);    		
    			
    			$(".loadingTable").css("display","block").removeClass("alert-danger").addClass("alert-success").text("Table Data has been sorted by the default settings.").delay(5000).fadeOut();
		    });
    		$('.sortSchoolName').click( function() {
    			mTable.fnSort([[3,"asc"],[6,"asc"]]);    		
    			
    			$(".loadingTable").css("display","block").removeClass("alert-danger").addClass("alert-success").text("Table Data has been sorted by SCHOOL and STUDENT NAME.").delay(5000).fadeOut();
		    });    		
    		
			</script>	
			
			
	</body>
	
</html>