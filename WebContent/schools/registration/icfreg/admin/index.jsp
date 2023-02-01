<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
                 
<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-VIEW" />


<html>
  
  <head>   
  
     <meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>ICF Registration</title>
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
                         columns: [ 0,1,2,3,4 ],
                     }
            },
            {
                extend: 'csv',
                exportOptions: {
                    columns: [ 0,1,2,3,4 ],
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
  
<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-VIEW">					 
						<a class="btn btn-success btn-sm float-right" style="color:White;margin-top:10px;" title="Add New Period" data-toggle="modal" data-target="#addPeriodModal">Add New Period</a> 
</esd:SecurityAccessRequired>
  
  
  
     <div class="siteHeaderGreen">ICF REGISTRATION PERIODS</div>
     				
     <br/><b>Below is the list of Intensive Core French (ICF) registration periods currently in the system.</b><br/><br/>
     
     <ul>     
     	<li>You can view the registration periods, or EXPORT complete data to CSV/Excel using the option below right 
     	<li>You can also use the Print, Excel, or CSV on any table (below left atop any table) to export the current view. 
    	<li>Use the Search on any table (below right atop any table) to quickly find any data as you type.
    	<li>To add a NEW registration period, use the option above right.
	</ul>
	<br/>
     <div class="alert alert-danger" id="errormsg" style="display:none;"></div>
      <div class="alert alert-success" id="successmsg" style="display:none;"></div>
     <br/>
		<table id="registrationPeriodsTable" class="registrationPeriodsTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr>							
					<th width="10%">YEAR</th>
					<th width="20%">START (y/m/d)</th>
					<th width="20%">END (y/m/d)</th>
					<th width="20%">STATUS</th>
					<th width="10%">TOTAL</th>
					<th width="20%">OPTIONS</th>
				</tr>
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(periods) gt 0 }">
					<c:set var="periodCnt" value="0"/>
						<c:forEach items="${periods}" var="p">												
							<tr class='period-data-row'>							
								<td width="10%" class="dateData">${p.icfRegPerSchoolYear}</td>	
								<td width="15%"><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.icfRegStartDate}" /></td>
								<td width="15%"><fmt:formatDate type="both" pattern="yyyy/MM/dd @ h:mm a" value="${p.icfRegEndDate}" /></td>
								<td width="20%" align='center'>${p.isPast()}</td>
								<td width="10%" align='center' class="totalData">${p.icfRegCount}</td>
								<td width="30%" align='center'><a onclick="loadingData();" title="View Registrants" class='btn btn-xs btn-primary' href="/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrants.html?irp=${p.icfRegPerId}">REGISTRANTS</a>
								<a onclick="loadingData();" class='btn btn-xs btn-info' title="View Schools" href="/MemberServices/schools/registration/icfreg/admin/viewPeriodSchools.html?irp=${p.icfRegPerId}">SCHOOLS</a>
								<a onclick="loadingData();" class='btn btn-xs btn-warning' title="Export" href="/MemberServices/schools/registration/icfreg/admin/exportRegistrants.html?irp=${p.icfRegPerId}">EXPORT</a>
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

		<br />
<div class="modal fade" id="addPeriodModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Add New Registration Period</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row">
    		<div class="col-md-3 col-sm-12">
      			School Year
    		</div>
    		<div class="col-md-9 col-sm-12">
      			<select id="selschyear" class="form-control">
        		<option value="NONE">Please Select</option>
        		<c:forEach items="${schyrs}" var="p">
        			<option value="${p}">${p}</option>
        		</c:forEach>	
        	</select>
    		</div>
  		</div>
  		<div class="row" style="padding-top:5px;">
    		<div class="col-md-3 col-sm-12">
      			Start Date
    		</div>
    		<div class="col-md-9 col-sm-12">
      			<input type="datetime-local" id='dtstartdate' class="form-control">
      		</div>
  		</div>
  		<div class="row" style="padding-top:5px;">
    		<div class="col-md-3 col-sm-12">
      			End Date
    		</div>
    		<div class="col-md-9 col-sm-12">
      			<input type="datetime-local" id='dtenddate' class="form-control">
      		</div>
  		</div>
  		
  		<div class="row" style="text-align: center;padding-top:5px;">
  			<div class="col-lg-12">
  			<br/>
    		 <div id="msgerradd" style="display:none;"></div>
    		 </div>
  		</div>
  		         
       
		</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-success btn-sm" onclick="confirmRegistrationPeriodFields()">Save</button>
      	<button type="button" class="btn btn-danger btn-sm" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>
	
	</body>
	
</html>