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
    <TITLE>ICF Registration Period Schools</title>
   	<script>
	$('document').ready(function(){
		$('input[type=text], select').css({'border' : 'solid 1px #7B9EBD', 'background-color' : '#FFFFCC'});
		
			refreshTable();
		
				
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
     <a class="btn btn-success btn-sm float-right" style="color:White;margin-top:10px;" title="Add New School" onclick="showAddEditSchoolDialog('A',${period.icfRegPerId},'','','','')">Add New School</a> 
 </esd:SecurityAccessRequired>
	<div class="siteHeaderGreen">REGISTRATION PERIOD SCHOOLS</div><br/>
	Below are the schools included in the registration period <fmt:formatDate type="both" pattern="yyyy/MM/dd" value="${period.icfRegStartDate}"/> to <fmt:formatDate type="both" pattern="yyyy/MM/dd" value="${period.icfRegEndDate}"/>.
    To add a new school, use the link at right. depending on your level of access, options to edit/remove schools are shown in the table below.<br/> 
     <div class="alert alert-danger" id="errormsg" style="display:none;"></div>
      <div class="alert alert-success" id="successmsg" style="display:none;"></div>
     <br/>
		  		
  			<div class="row" style="font-size:14px;">
    			<div class="col-lg-4 col-sm-4 col-12">
      				<b>SCHOOL YEAR:</b> ${period.icfRegPerSchoolYear}
    			</div>
    			<div class="col-lg-4 col-sm-4 col-12">
      				<b>DATES:</b> <fmt:formatDate type="both" pattern="yyyy/MM/dd" value="${period.icfRegStartDate}"/> to <fmt:formatDate type="both" pattern="yyyy/MM/dd" value="${period.icfRegEndDate}"/>
    			</div>
    		<div class="col-lg-4 col-sm-4 col-12">
      			<b>STATUS:</b> 
      			<c:choose>
      			<c:when test="${period.isPast() eq 'OPENED'}">
      			<span style="color:White;background-color:Green;"> &nbsp; OPENED &nbsp; </span>
      			</c:when>
      			<c:when test="${period.isPast() eq 'NOT STARTED'}">
      			<span style="color:Black;background-color:#FFD700;"> &nbsp; NOT STARTED &nbsp; </span>
      			</c:when>
      			<c:otherwise>
      			<span style="color:White;background-color:Red;"> &nbsp; ${period.isPast()} &nbsp; </span>
      			</c:otherwise>
      			</c:choose>
      			
    		</div>
  			</div>
		
  <br />
		<table id="registrationPeriodSchoolsTable" class="registrationPeriodSchoolsTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
				<thead class="thead-dark">
				<tr>
				<th width="30%">SCHOOL NAME</th>
				<th width="20%">REGISTRATION CAP</th>
				<th width="20%">REGISTRATION COUNT</th>
				<th width="30%">OPTIONS</th>
				
				</tr>
				
				</thead>
				<tbody>
				<c:choose>
					<c:when test="${fn:length(schools) gt 0 }">
					<c:set var="periodCnt" value="0"/>
						<c:forEach items="${schools}" var="p">												
							<tr class='period-data-row'>							
								<td width="30%" class="dateData">${p.icfSchSchool}</td>	
								<td width="20%" class="dateData">${p.icfSchCap}</td>
								<td width="20%" align='center'>${p.icfSchCount}</td>
								<td width="30%">
								<a onclick="loadingData();" class='btn btn-xs btn-primary' href="/MemberServices/schools/registration/icfreg/admin/viewPeriodRegistrantsBySchool.html?irp=${p.icfRegPerId}&sid=${p.icfSchSchoolId}">REGISTRANTS</a>
								<a onclick='showAddEditSchoolDialog("E","${p.icfRegPerId}","${fn:replace(p.icfSchSchool, '\'', '*')}","${p.icfSchCap}","${p.icfSchId}","${p.icfSchSchoolId}")' class='btn btn-xs btn-warning'>EDIT</a>
								 <esd:SecurityAccessRequired permissions="ICF-REGISTRATION-DELETE-PERIOD">
								<a onclick='showDeleteSchoolDialog("${p.icfRegPerId}","${fn:replace(p.icfSchSchool, '\'', '*')}","${p.icfSchCount}","${p.icfSchSchoolId}")' class='btn btn-xs btn-danger'>DELETE</a>
								</esd:SecurityAccessRequired>
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
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>

		<br />
		<a onclick="loadingData();" class='btn btn-sm btn-danger' href="<c:url value='/schools/registration/icfreg/admin/index.html' />">Back to Registration Period List</a>



<div class="modal fade" id="addPeriodSchoolModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="apsTitle"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row">
    		<div class="col-md-4">
      			School Name:
    		</div>
    		<div id="divselschool" class="col-md-8">
      			<select id="selschools" class="form-control">
        		<option value="NONE">Please Select</option>
        		<c:forEach var="entry" items="${ischools}">
  					<option value="${entry.value}">${entry.key}</option>
				</c:forEach>	
        	</select>
    		</div>
    		<div id="divshowschool" class="col-md">
      			<span id="spanshowschool"></span>
    		</div>
  		</div>
  		<div class="row">
    		<div class="col-md-4">
      			Registration Cap:
    		</div>
    		<div class="col-md-8">
      			<input class="form-control" type="text" id='txtcap' maxlength="3"><input type="hidden" id="hidsid"><input type="hidden" id="hidtype">
      		</div>
  		</div>
  		<div class="row" style="text-align: center;">
  			<div class="col-lg">
  			<br/>
    		 <div id="msgerradd" style="display:none;"></div>
    		 </div>
  		</div>
  	</div>
      
      </div>
       
      
      <div class="modal-footer">
      	<button type="button" class="btn btn-sm btn-success" id="btnsave">Save</button>
      	<button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="deletePeriodSchoolModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="aps1Title"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="container">
  		<div class="row" >
    		<div class="col-md" id="schoolname">
    		
      		</div>
      		<br/>
  		</div>
  		<div class="row" >
    		<div id="divmessage" class="col-md">
      			<span id="spanmessage"></span>
    		</div>
  		</div>
  	</div>
      
      </div>
       
      
      <div class="modal-footer">
      <button type="button" class="btn btn-sm btn-danger" id="btndelete">Delete</button>
      	<button type="button" class="btn btn-sm btn-primary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>	
	</body>
	
</html>