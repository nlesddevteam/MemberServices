<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr"); 
  Collection<School> schools = null;
  schools = SchoolDB.getSchools();	
%>
<c:set var='schools' value="<%= schools %>" />
<c:set var="zonePerm" value='all' />


<c:set var="avalonSchools" value="0" />
<c:set var="centralSchools" value="0" />
<c:set var="westernSchools" value="0" />
<c:set var="labradorSchools" value="0" />
<c:set var="provincialSchools" value="0" />

<c:set var="avalonDocs" value="0" />
<c:set var="centralDocs" value="0" />
<c:set var="westernDocs" value="0" />
<c:set var="labradorDocs" value="0" />
<c:set var="provincialDocs" value="0" />



<esd:SecurityCheck permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-BUSROUTES" />
<html>

	<head>
		<title>Bus Route Documentation</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
  
	<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		td {vertical-align:middle;}
	</style>
	
	<script>		
		$('document').ready(function(){
			mTable = $(".routeTable").dataTable({
				"order" : [[1,"asc"]],
				paging: false,
				responsive: true,
				 "bInfo":false,  
				dom: 'Bfrtip',
		        buttons: [			        	
		        	//'colvis',
		        	{
		        	
	                extend: 'print',
	                title: '<div align="center"><img src="/MemberServices/Administration/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
	                messageTop: '<div align="center" style="font-size:18pt;">Bus Route List</div>',
	                messageBottom: '<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 	of this message and any attachments is strictly prohibited.</div>',
	                	 exportOptions: {	                			                		 
	                         columns: [ 0,1,2,3,4],	                       
	                     }
	            },
	            { 
	       		 extend: 'excel',	
	       		 exportOptions: {        		       		 

	                    columns: [ 0,1,2,3,4],
	                 },
	       },
	       { 
	     		 extend: 'csv',	
	     		 exportOptions: {        		            		 

	                    columns: [ 0,1,2,3,4],
	               },
	     },
	         
		        ],			
								
				 "columnDefs": [
					 {
			                "targets": [5,6],			               
			                "searchable": false,
			                "orderable": false
			            }
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
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			$("#loadingSpinner").css("display","none");
		});

		</script>
	
	
	
	</head>

  <body>
  <div class="siteHeaderGreen">School Profile Bus Route Posting</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Busing Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 

					  Below is a complete list of all district schools sorted alphabetically and catagorized by region (depending on your permissions). Not all regions are available to all users.<p>
                 <p>
 
					  <div align="center">
					  <a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services">Back to StaffRoom</a>
					  </div>

						
  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES">  

	<br/><br/>
<table class="routeTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">			
					<th>DEPT ID</th>		
					<th>SCHOOL NAME</th>
					<th>FAMILY</th>	
					<th>TOWN/CITY</th>					
					<th>REGION</th>		
					<th>ROUTE DOC</th>											
					<th>OPTIONS</th>					
				</tr>
				</thead>
				<tbody>       
							<c:forEach items='${ schools }' var='school'>								
								<c:choose>                    				
   									<c:when test="${!fn:containsIgnoreCase(school.schoolName, 'former')}">   															
   									<c:choose>
   									<c:when test="${school.zone eq 'avalon'}">
   									<c:set var="regionColor" value="officeAvalon"/>   	   														
   									</c:when>
   									<c:when test="${school.zone eq 'central'}">
   									<c:set var="regionColor" value="officeCentral"/>    																		
   									</c:when>
   									<c:when test="${school.zone eq 'western'}">
   									<c:set var="regionColor" value="officeWestern"/>   	   																	
   									</c:when>
   									<c:when test="${school.zone eq 'labrador'}">
   									<c:set var="regionColor" value="officeLabrador"/>   	   																
   									</c:when>
   									<c:when test="${school.zone eq 'provincial'}">
   									<c:set var="regionColor" value="officeProvincial"/>   	   																
   									</c:when>
   									<c:otherwise>
   									<c:set var="regionColor" value="officeProvincial"/>   
   									</c:otherwise>
   									</c:choose>   									   									
   									<tr>
               						<td>${ school.schoolDeptID }</td>										
	     							<td>${ school.schoolName }</td>
	     							<c:catch var='schoolFamilyException'>
										<c:set var="theSchool" value="${ school.schoolFamily.schoolFamilyName}"/>
										</c:catch>
										<c:if test = "${schoolFamilyException != null}">
										<c:set var="theSchool" value="ERROR"/>
										</c:if>
										<c:choose>
										<c:when test="${ theSchool eq 'FOS 01' }">
										<td class="familyAlign family1" style="vertical-align:middle;">FOS 01</td>										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 02' }">
										<td class="familyAlign family2" style="vertical-align:middle;">FOS 02</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 03' }">
										<td class="familyAlign family3" style="vertical-align:middle;">FOS 03</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 04' }">
										<td class="familyAlign family4" style="vertical-align:middle;">FOS 04</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 05' }">
										<td class="familyAlign family5" style="vertical-align:middle;">FOS 05</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 06' }">
										<td class="familyAlign family6" style="vertical-align:middle;">FOS 06</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 07' }">
										<td class="familyAlign family7" style="vertical-align:middle;">FOS 07</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 08' }">
										<td class="familyAlign family8" style="vertical-align:middle;">FOS 08</td>
									
										</c:when>
										<c:when test="${ theSchool eq 'FOS 09' }">
										<td class="familyAlign family9" style="vertical-align:middle;">FOS 09</td>
										
										</c:when>
										<c:when test="${ theSchool eq 'FOS 10' }">
										<td class="familyAlign family10" style="vertical-align:middle;">FOS 10</td>
									
										</c:when>
										<c:when test="${ theSchool eq 'FOS 11 (DSS)' }">
										<td class="familyAlign family11" style="vertical-align:middle;">FOS 11 (DSS)</td>
									
										</c:when>
										<c:when test="${ theSchool eq 'FOS 12 (DSS)' }">
										<td class="familyAlign family12" style="vertical-align:middle;">FOS 12 (DSS)</td>
									
										</c:when>
										<c:when test="${ theSchool eq 'FOS 13 (DSS)' }">
										<td class="familyAlign family13" style="vertical-align:middle;">FOS 13 (DSS)</td>
									
										</c:when>
										<c:otherwise>
										<td class="familyAlign familyDefault" style="vertical-align:middle;">N/A</td>
										
										</c:otherwise>
										
										</c:choose>
	     							<td>${ not empty school.townCity?school.townCity:"N/A" }</td>	
	     							<td class="${regionColor}" style="text-transform:uppercase;text-align:center;">${school.getZone().getZoneName() }</td>	
	     							
	     								     							
	     							<td> 
											<c:choose>
												<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='/schools/doc/${ school.details.busRoutesFilename }' class="btn btn-xs btn-warning" target="_blank"><i class="far fa-eye"></i> VIEW DOC</a>	
												<c:set var="avalonDocs" value="${avalonDocs + 1}" />
												</c:when>
												<c:otherwise>
													<span style="color:red;font-weight:bold;">N/A</span>
												</c:otherwise>
						  					</c:choose>											
									</td>
									<td>		
									<a href="/MemberServices/StaffRoom/BusRoutes/school_directory_bus_routes_edit.jsp?id=${school.schoolID}" class="btn btn-xs btn-success"><i class="fas fa-edit"></i> ADD/EDIT</a>
									</td>		
										</tr>	
									</c:when>
									</c:choose>
								
								</c:forEach> 
   </tbody>
   </table>
  
     
 </esd:SecurityAccessRequired>    								
    
   				
		   		<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-AVALON">   
		   			<script>$(".central,.western,.labrador,.provincial").hide();
		   			$(".avalon").removeClass("region1");		   			
		   			</script>
		      	</esd:SecurityAccessRequired>
   				<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-CENTRAL">   
		   			<script>$(".avalon,.western,.labrador,.provincial").hide();
		   			$(".central").removeClass("region2");
		   			</script>
		      	</esd:SecurityAccessRequired>
   				<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-WESTERN">   
		   			<script>$(".central,.avalon,.labrador,.provincial").hide();
		   			$(".western").removeClass("region3");
		   			</script>
		     	</esd:SecurityAccessRequired>
   				<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-LABRADOR">   
		   			<script>$(".central,.western,.avalon,.provincial").hide();
		   			$(".labrador").removeClass("region4");
		   			</script>
		      </esd:SecurityAccessRequired>
              <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-PROVINCIAL">   
		   			<script>$(".labrador,.central,.western,.avalon,.provincial").show();
		   			$(".avalon").addClass("region1");
		   			$(".central").addClass("region2");
		   			$(".western").addClass("region3");
		   			$(".labrador").addClass("region4");
		   			</script>
		      </esd:SecurityAccessRequired> 
</div>

<script>
$(document).ready(function(){
	
	
	
	
	
	
	
//Write the variables to divs	
	$('.regionsAllowed').html('${region1Allowed} ${region2Allowed} ${region3Allowed} ${region4Allowed} ${region5Allowed}');
	$('#fadeMessage').delay('3000').fadeOut();

});

  			$(".avalonCount").html("${avalonSchools}");   			
  			$(".centralCount").html("${centralSchools}"); 
  			$(".westernCount").html("${westernSchools}"); 
  			$(".labradorCount").html("${labradorSchools}"); 
  			$(".provincialCount").html("${provincialSchools}"); 
  			$(".avalonDocCount").html("${avalonDocs}");   			
  			$(".centralDocCount").html("${centralDocs}"); 
  			$(".westernDocCount").html("${westernDocs}"); 
  			$(".provincialDocCount").html("${provincialDocs}"); 
    		
  			var avalonCalc = ${(avalonDocs/avalonSchools)*100};
  			var avalonPercent = avalonCalc.toFixed(2);
  			$(".avalonPercent").html(avalonPercent +"%");
  			
  			var centralCalc = ${(centralDocs/centralSchools)*100};
  			var centralPercent = centralCalc.toFixed(2);
  			$(".centralPercent").html(centralPercent +"%");
  			
  			var westernCalc = ${(westernDocs/westernSchools)*100};
  			var westernPercent = westernCalc.toFixed(2);
  			$(".westernPercent").html(westernPercent +"%");
  			
  			var labradorCalc = ${(labradorDocs/labradorSchools)*100};
  			var labradorPercent = labradorCalc.toFixed(2);
  			$(".labradorPercent").html(labradorPercent +"%");
  			
  			var provincialCalc = ${(provincialDocs/provincialSchools)*100};
  			var provincialPercent =provincialCalc.toFixed(2);
  			$(".provincialPercent").html(provincialPercent +"%");
  			
    </script>
</body>
</html>	
			

			