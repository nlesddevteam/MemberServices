<%@ page language="java" session="true" import="com.awsd.security.*" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="ICF-REGISTRATION-SCHOOL-VIEW,ICF-REGISTRATION-ADMIN-VIEW" />

<%
	User usr = (User) session.getAttribute("usr");
%>
<html>
  
  <head>   
  
     <meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>School ICF Registration</title>
   	<script>
	$('document').ready(function(){
		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
		
		mTable = $(".registrationPeriodsTable").dataTable({
			"order" : [[0,"desc"]],		
			"bPaginate": false,
			responsive: true,
			dom: 'Bfrtip',
	        buttons: [			        	
	        	//'colvis',
	        	{
                extend: 'print',
                title: '<div align="center"><img src="/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png" style="max-width:600px;"/></div>',
                messageTop: '<div align="center" style="font-size:18pt;">${sy} Intensive Core French (ICF) Registration Periods</div>',           
                	 exportOptions: {
                         columns: [ 0,1,2,3,4],
                     }
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0,1,2,3,4],
                }
            },
            {
                extend: 'excel',
                exportOptions: {
                    columns: [ 0,1,2,3,4],
                }
            },    	
	        ],				
			 "columnDefs": [
				 {
		                "targets": [5],			               
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
  
  
     <div class="siteHeaderGreen">VIEW REGISTRATION PERIODS</div><br/>
     Below is the list of registration periods currently in the system that included your school <b>
     <%=(usr.getPersonnel().getSchool() != null ? usr.getPersonnel().getSchool().getSchoolName() : "NO SCHOOL")%></b><br/><br/>
     
	<ul>
     <li>You can view the registration periods, or EXPORT complete data to CSV/Excel using the option below right 
     <li>You can also use the Print, Excel, or CSV on any table (below left atop any table) to export the current view. 
    <li>Use the Search on any table (below right atop any table) to quickly find any data as you type.
	</ul>
	
     <br/>
		<table id="registrationPeriodsTable" class="registrationPeriodsTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr>							
					<th>YEAR</th>					
					<th>START (y/m/d)</th>
					<th>END (y/m/d)</th>
					<th>STATUS</th>
					<th>TOTAL</th>
					<th>OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(periods) gt 0 }">
					<c:set var="periodCnt" value="0"/>
						<c:forEach items="${periods}" var="p">												
							<tr class='period-data-row'>							
								<td class="dateData">${p.icfRegPerSchoolYear}</td>	
								
								<td><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.icfRegStartDate}" /></td>
								<td><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.icfRegEndDate}" /></td>
								<td align='center'>${p.isPast()}</td>
								<td align='center' class="totalData">${p.icfRegCount}</td>
								<td>
								<a onclick="loadingData();" class='btn btn-xs btn-primary' title="View Registrants" href="/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrantsBySchool.html?irp=${p.icfRegPerId}&sid=${sid}">REGISTRANTS</a>
								<a onclick="loadingData();" class='btn btn-xs btn-warning' title="Export Data" href="/MemberServices/schools/registration/icfreg/admin/exportRegistrants.html?irp=${p.icfRegPerId}&sid=${sid}">EXPORT</a>
								</td>
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
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>


	</body>
	
</html>