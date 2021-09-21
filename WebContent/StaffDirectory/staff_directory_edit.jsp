<%@ page language="java" session="true"%>
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


<%!
  User usr = null;
%>
<%
  User usr = (User) session.getAttribute("usr");
%>

<c:set var='divisions' value='<%= StaffDirectoryContactBean.STAFF_DIRECTORY_DIVISION.values()  %>' />

<html>
  <head>
    <title>Staff Directory</title>
    		<meta name="viewport" content="width=device-width, initial-scale=1.0">
    		<meta charset="utf-8">
    		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
 <script>			
	$('document').ready(function(){	
	
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");	

	  $('#telephone,#cellphone,#fax').mask('000-0000');
	  $('#extension').mask('000');
	  
	});
	
		</script>

  </head>

	<body>
	<div class="siteHeaderGreen">Office Staff Contact Edit/Add</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Staff Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
	




   <form action='updateStaffDirectoryContact.html' method='POST' class="was-validated">
                  	<input type='hidden' name='contactId' id='contactId' value='${contact.contactId }' />


<div class="card">
  <div class="card-header siteSubHeaderBlue">1. NAME &amp; CONTACT INFORMATION</div>
<div class="card-body">

<div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-6 col-12">
	          <b>Full Name:</b>
	          <input type='text' name='fullname' id='fullname' class="form-control" autocomplete="false" value="${ contact.fullName }"/ required>
	 		</div>
	 		<div class="col-lg-6 col-12">	
	 		 <b>Email:</b>
    				<input required type='text' id='email' name='email' class="form-control" autocomplete="false" value='${ contact.email }' />
	 		
	 	
			</div>
</div>
<div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-3 col-12">
	 			<b>Telephone #: </b>
	                     <input type='text' id='telephone' name='telephone' class="form-control" autocomplete="false" required value='${ contact.telephone }' />
	           </div>          
	          <div class="col-lg-3 col-12">  
	            <b>Extension:</b>
	                     <input type='text' id='extension' name='extension' autocomplete="false" class="form-control" value='${ contact.extension }' />
	           </div>
	          <div class="col-lg-3 col-12"> 
	           <b>Cellphone #:</b>
    				<input type='text' id='cellphone' name='cellphone' class="form-control" autocomplete="false" value='${ contact.cellphone }' />
    				</div>
	          <div class="col-lg-3 col-12">
	          <b>Fax #:</b>
    				<input type='text' id='fax' name='fax' class="form-control" autocomplete="false" value='${ contact.fax }' />
</div>
</div>   				
    				
<div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-3 col-12">	         
	         	<input type="checkbox" value="" id="posVacant"> Position Currently Vacant?
	</div>
</div> 
	</div>
	</div>       
<br/><br/>
  <div class="card">
						  	<div class="card-header siteSubHeaderBlue">2. DIVISION &amp; REGION</div>
						  	<div class="card-body">
	                  
	                  <div class="row container-fluid" style="padding-top:5px;">
      		<div class="col-lg-6 col-12">
	                  <b>Division:</b>
	                      	<select id='divisionId' name='divisionId' class="form-control" required>	                      		
	                      		<c:forEach items="${ divisions }" var='division'>
	                      		<c:choose>
	                      		<c:when test="${ division.name eq 'Finance and Business Administration' }">
	                      		<option value='${ division.id }' ${ contact.division.id eq division.id ? "SELECTED" : "" }>
	                      			Corporate Services
	                      			</option>
	                      		</c:when>
	                      		<c:otherwise>
	                      			<option value='${ division.id }' ${ contact.division.id eq division.id ? "SELECTED" : "" }>
	                      			${ division.name }
	                      			</option>
	                      		</c:otherwise>	
	                      		</c:choose>	
	                      		</c:forEach>
	                      	</select>
	                  </div>
	                  <div class="col-lg-6 col-12">
	                  <b>Region:</b>
    					<select id="zoneId" name="zoneId" class="form-control" required>
    					<option value="">---- Please Select (Required) ----</option>
	                    <option value='1' ${ contact.zone eq "avalon" ? "SELECTED" : "" }>Avalon</option>
	                    <option value='2' ${ contact.zone eq "central" ? "SELECTED" : "" }>Central</option>
	                    <option value='3' ${ contact.zone eq "western" ? "SELECTED" : "" }>Western</option>
	                    <option value='4' ${ contact.zone eq "labrador" ? "SELECTED" : "" }>Labrador</option>
	                    <option value='5' ${ contact.zone eq "provincial" ? "SELECTED" : "" }>Provincial</option>
    					</select>
</div></div>
						
						</div>
	       </div>
	       
<br/><br/>	       
	       
	        <div class="card">
						  	<div class="card-header siteSubHeaderBlue">3. POSITION TITLE</div>
						  	<div class="card-body">
	                  			Using the fields A, B, C, D E and/or F below, build the Position Title for this staff member. The Title will form in the Position/Title field just below. (You cannot type into this field).
	                  			Depending on the title, fields B, C, D, E, F are <b>not</b> required.
						<br/>
						<br/>



	                  Position/Title Builder:
	                    <input type='text' id='position' name='position' class="form-control" readonly value='${ contact.position ne "" ? contact.position : "N/A" }' />

	               
	                    <br/><div class="alert alert-danger" id="warningMessage1" style="display:none;margin-top:10px;font-size:12px;margin-bottom:10px;padding:5px;"></div>

	                  <br/>
	                  The above will show current position title (if any). As you select the fields below, the position builder will display the selections above.
	                  When editing you may see an error saying current position is not valid.
	                  If this is the case, please rebuild the position using the builder.
	                  Only use the fields below to add/edit a position title. <b>Leave alone if no changes</b>. Current title for ${ contact.fullName } is <b>${ contact.position }</b>.

<br/><br/>

		
   Only use the following selectors (B, C, D, E and/or F) if the position you are adding/editing has special titles, description and/or locations. Most positions are fine using the Position and the Division and Region above. Some others may require more of a description.
   (i.e. if you added a Programs itinerat, you may need to add the sector that position relates to, and where the office location is if outside the regional offices.)
	<br/><br/>	               
	                 <div class="row container-fluid" style="padding-top:5px;">
      				<div class="col-lg-6 col-12">  
	                   <div id="posPrimarySelect">
    				<b>A. Position:</b>
	                     <select id='posPrimary' name='posPrimary' class="form-control" required>
	                    <option value="N/A">---- Please Select (Required) ----</option>
	                     <option value="Accountant">Accountant</option>
	                    <option value="Accountant I">Accountant I</option>
	                    <option value="Accountant II">Accountant II</option>	                    
	                   	<option value="Accounts Payable Clerk">Accounts Payable Clerk</option>
	                   	<option value="Accounts Payable Clerk I">Accounts Payable Clerk I</option>
	                   	<option value="Accounts Payable Clerk II">Accounts Payable Clerk II</option>
						<option value="Accounts Receivable Clerk">Accounts Receivable Clerk</option>
						<option value="Accounts Supervisor">Accounts Supervisor</option>
						<option value="Active Schools Coordinator">Active Schools Coordinator</option>
						<option value="Administrative Assistant">Administrative Assistant</option>
						<option value="ASL Immersion Program">ASL Immersion Program</option>
						<option value="Alternate Transportation Clerk">Alternate Transportation Clerk</option>
						<option value="Assistant Director of Education">Assistant Director of Education</option>
						<option value="Associate Director of Education">Associate Director of Education</option>
						<option value="Audiologist">Audiologist</option>
						<option value="Auditor">Auditor</option>
						<option value="Auditory Verbal Therapist">Auditory Verbal Therapist</option>
						<option value="Budget Analyst">Budget Analyst</option>
						<option value="Bus Foreperson">Bus Foreperson</option>
						<option value="Buyer I">Buyer I</option>
						<option value="Buyer II">Buyer II</option>
						<option value="CEO/Director of Education">CEO/Director of Education</option>
						<option value="CFO/Assistant Director of Education">CFO/Assistant Director of Education</option>
						<option value="Classroom Teacher">Classroom Teacher</option>
						<option value="Clerk I">Clerk I</option>
						<option value="Clerk II">Clerk II</option>
						<option value="Clerk III">Clerk III</option>
						<option value="Clerk IV">Clerk IV</option>			
						<option value="Comprehensive School Health Behavioral Specialist">Comprehensive School Health Behavioral Specialist</option>					
						<option value="Comptroller/Director of Financial Services">Comptroller/Director of Financial Services</option>
						<option value="Computer Programmer Analyst">Computer Programmer Analyst</option>
						<option value="Computer Support Specialist">Computer Support Specialist</option>
						<option value="Confidential Secretary">Confidential Secretary</option>
						<option value="Corporate/Executive Assistant">Corporate/Executive Assistant</option>
						<option value="Curriculum Clerk">Curriculum Clerk</option>
						<option value="Data Analyst">Data Analyst</option>
						<option value="Director of Communications">Director of Communications</option>
						<option value="Director of Educational Programs">Director of Educational Programs</option>
						<option value="Director of Facilities & Custodial Management">Director of Facilities & Custodial Management</option>
						<option value="Director of Human Resources">Director of Human Resources</option>
						<option value="Director of Information Technology">Director of Information Technology</option>
						<option value="Director of Learning">Director of Learning</option>
						<option value="Director of Procurement and Business Services">Director of Procurement and Business Services</option>
						<option value="Director of School Financial Support">Director of School Financial Support</option>
						<option value="Director of Programs">Director of Programs</option>
						<option value="Director of Schools">Director of Schools</option>
						<option value="Director of Strategic Planning, Policy and Communications">Director of Strategic Planning, Policy and Communications</option>
						<option value="Director of Student Services">Director of Student Services</option>
						<option value="Director of Student Support Services">Director of Student Support Services</option>
						<option value="Director of Student Transportation">Director of Student Transportation</option>
						<option value="Director of Student Transportation and School Financial Support">Director of Student Transportation and School Financial Support</option>
						<option value="E-Learning Facilitator">E-Learning Facilitator</option>
						<option value="Educational Psychologist">Educational Psychologist</option>
						<option value="Electrician">Electrician</option>
						<option value="Equipment Operator">Equipment Operator</option>
						<option value="Equipment Operator/Utility Worker I">Equipment Operator/Utility Worker I</option>
						<option value="ESL Teacher">ESL Teacher</option>
						<option value="Fleet Maintenance Coordinator">Fleet Maintenance Coordinator</option>
						<option value="Foreman">Foreman</option>
						<option value="FSL Learning Design Facilitator">FSL Learning Design Facilitator</option>
						<option value="Functional Analyst">Functional Analyst</option>
						<option value="GIS Specialist">GIS Specialist</option>
						<option value="Guidance Counsellor">Guidance Counsellor</option>
						<option value="Guidance Counsellor/IRT">Guidance Counsellor/IRT</option>
						<option value="Hospital Teacher">Hospital Teacher</option>
						<option value="Human Resources Assistant">Human Resources Assistant</option>
						<option value="Internal Auditor">Internal Auditor</option>
						<option value="Inuktitut Curriculum Worker">Inuktitut Curriculum Worker</option>
						<option value="Itinerant">Itinerant</option>
						<option value="Itinerant Services">Itinerant Services</option>
						<option value="Itinerant Teacher">Itinerant Teacher</option>
						<option value="Instructional Resource Teacher">Instructional Resource Teacher</option>
						<option value="LAN Administrator">LAN Administrator</option>
						<option value="Lead Driver">Lead Driver</option>
						<option value="Maintenance">Maintenance</option>
						<option value="Maintenance Clerk">Maintenance Clerk</option>
						<option value="Maintenance Foreman">Maintenance Foreman</option>
						<option value="Maintenance Supervisor">Maintenance Supervisor</option>
						<option value="Manager of Accounting and Financial Operations">Manager of Accounting and Financial Operations</option>
						<option value="Manager of ATIPP">Manager of ATIPP</option>
						<option value="Manager of Accounts Payable">Manager of Accounts Payable</option>
						<option value="Manager of Budgeting">Manager of Budgeting</option>
						<option value="Manager of Communications">Manager of Communications</option>						
						<option value="Manager of Contracted Student Transportation">Manager of Contracted Student Transportation</option>
						<option value="Manager of Facilities">Manager of Facilities</option>
						<option value="Manager of Facilities & Maintenance">Manager of Facilities & Maintenance</option>
						<option value="Manager of Facilities Operations">Manager of Facilities Operations</option>
						<option value="Manager of Finance & Administration">Manager of Finance & Administration</option>
						<option value="Manager of Human Resources">Manager of Human Resources</option>
						<option value="Manager of Information Systems">Manager of Information Systems</option>
						<option value="Manager of Information Technology">Manager of Information Technology</option>
						<option value="Manager of Occupational Health and Safety">Manager of Occupational Health and Safety</option>
						<option value="Manager of Payroll">Manager of Payroll</option>
						<option value="Manager of Policy">Manager of Policy</option>
						<option value="Manager of Plants & Facilities">Manager of Plants & Facilities</option>
						<option value="Manager of Procurement">Manager of Procurement</option>
						<option value="Manager of Student Transportation">Manager of Student Transportation</option>
						<option value="Network Architect">Network Architect</option>
						<option value="Network Administrator">Network Administrator</option>
						<option value="NG Clerk">NG Clerk</option>
						<option value="Numeracy Support Teacher">Numeracy Support Teacher</option>
						<option value="Operations Manager">Operations Manager</option>
						<option value="Operations Manager (Energy Management)">Operations Manager (Energy Management)</option>
						<option value="Orientation and Mobility Specialist">Orientation and Mobility Specialist</option>
						<option value="Pathfinder Teacher">Pathfinder Teacher</option>
						<option value="Payroll Clerk">Payroll Clerk</option>
						<option value="Payroll Specialist">Payroll Specialist</option>
						<option value="Personnel Clerk">Personnel Clerk</option>
						<option value="Plant Office Clerk">Plant Office Clerk</option>
						<option value="Procurement Clerk">Procurement Clerk</option>
						<option value="Program Itinerant">Program Itinerant</option>
						<option value="Program Specialist">Program Specialist</option>
						<option value="Programmer Analyst I">Programmer Analyst I</option>
						<option value="Programmer Analyst II">Programmer Analyst II</option>
						<option value="Project Coordinator">Project Coordinator</option>
						<option value="Provincial Lead, Employee Safety and Wellness">Provincial Lead, Employee Safety and Wellness</option>
						<option value="Receptionist">Receptionist</option>
						<option value="Regional Office Manager">Regional Office Manager</option>
						<option value="Requisition Clerk">Requisition Clerk</option>
						<option value="Safety and Compliance Officer">Safety and Compliance Officer</option>
						<option value="School Health Promotion Liaison Consultant">School Health Promotion Liaison Consultant</option>
						<option value="School Principal">School Principal</option>
						<option value="Secretary">Secretary</option>
						<option value="Secretary/Receptionist">Secretary/Receptionist</option>
						<option value="Senior Accountant">Senior Accountant</option>
						<option value="Senior Administrative Officer">Senior Administrative Officer</option>
						<option value="Senior Education Officer">Senior Education Officer</option>
						<option value="Senior Manager of Student Transportation">Senior Manager of Student Transportation</option>
						<option value="Senior Programmer Analyst">Senior Programmer Analyst</option>
						<option value="Senior Systems Analyst">Senior Systems Analyst</option>
						<option value="Solicitor">Solicitor</option>
						<option value="Speech Language Pathologist">Speech Language Pathologist</option>
						<option value="Store Keeper">Store Keeper</option>
						<option value="Student Services">Student Services</option>
						<option value="Student Transportation Enforcement Officer">Student Transportation Enforcement Officer</option>
						<option value="Supervisor of Board Owned Bus Services">Supervisor of Board Owned Bus Services</option>
						<option value="Supervisor of District-Operated Busing">Supervisor of District-Operated Busing</option>
						<option value="Training Specialist">Training Specialist</option>
						<option value="Transportation Clerk">Transportation Clerk</option>
						<option value="Utility Worker I">Utility Worker I</option>
						<option value="Utility Worker II">Utility Worker II</option>
					</select>
					</div>
					</div>					
      		<div class="col-lg-6 col-12">		
					
	<div class="alert alert-danger" id="warningMessage2" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>



					<div id="posSectorSelect">
    				<b>B. Position Sector:</b>
	                    <select id="posSector" name="posSector" class="form-control">
	                        <option value="">---- Please Select (If Necessary) ----</option>
							<option value="">Not Applicable</option>
							<option value="ASL Immersion Program">ASL Immersion Program</option>
	                    	<option value="Auditory Verbal Therapist">Auditory Verbal Therapist</option>
							<option value="Autism">Autism</option>
							<option value="Autism Consultant">Autism Consultant</option>
							<option value="Behavioral/Mental Health">Behavioral/Mental Health</option>
							<option value="Behavioral/Mental Health/Guidance Counsellor">Behavioral/Mental Health/Guidance Counsellor</option>
							<option value="Blind and Visually Impairment">Blind and Visually Impairment</option>
							<option value="Casual">Casual</option>
							<option value="Chief Financial Officer">Chief Financial Officer</option>
							<option value="Coding">Coding</option>
							<option value="Comprehensive School Health">Comprehensive School Health</option>
							<option value="Corporate Services">Corporate Services</option>
							<option value="Deaf and Hard of Hearing">Deaf and Hard of Hearing</option>
							<option value="Educational Assessment Specialist">Educational Assessment Specialist</option>
							<option value="English Language Arts">English Language Arts</option>
							<option value="English Language Arts/Social Studies">English Language Arts/Social Studies</option>
							<option value="Finance">Finance</option>
							<option value="Finance and Business Administration">Finance and Business Administration</option>
							<option value="Finance and Business Administration / Student Transportation">Finance and Business Administration / Student Transportation</option>
							<option value="Fine Arts">Fine Arts</option>
							<option value="French Programs">French Programs</option>
							<option value="Guidance">Guidance</option>
							<option value="Guidance Counsellor">Guidance Counsellor</option>
							<option value="Guidance Counsellor/IRT">Guidance Counsellor/IRT</option>
							<option value="Hearing Impaired">Hearing Impaired</option>
							<option value="Hearing/Visually Impaired">Hearing/Visually Impaired</option>
							<option value="Human Resources">Human Resources</option>
							<option value="Inclusive Education">Inclusive Education</option>
							<option value="Indigenous Education">Indigenous Education</option>					
							<option value="Information Systems">Information Systems</option>
							<option value="Instructional Materials Centre">Instructional Materials Centre</option>
							<option value="K-6 Programming">K-6 Programming</option>
							<option value="Literacy Teacher">Literacy Teacher</option>
							<option value="Literacy/Numeracy">Literacy/Numeracy</option>
							<option value="Literacy/Numeracy Teacher">Literacy/Numeracy Teacher</option>
							<option value="Mathematics">Mathematics</option>
							<option value="Multiculturalism">Multiculturalism</option>							
							<option value="Numeracy Support Teacher">Numeracy Support Teacher</option>
							<option value="Numeracy Teacher">Numeracy Teacher</option>
							<option value="OH&S">OH&S</option>
							<option value="Operations">Operations</option>
							<option value="Operations/HR">Operations/HR</option>
							<option value="Orientation and Mobility Specialist">Orientation and Mobility Specialist</option>
							<option value="Pension and Benefits">Pension and Benefits</option>
							<option value="Physical Education, Health, Social Studies">Physical Education, Health, Social Studies</option>
							<option value="PowerSchool">PowerSchool</option>
							<option value="Professional Learning">Professional Learning</option>
							<option value="Programs">Programs</option>
							<option value="Programs and Operations">Programs and Operations</option>
							<option value="Programs and Human Resources">Programs and Human Resources</option>
							<option value="Programs/Reception">Programs/Reception</option>
							<option value="Programs/Multiculturalism">Programs/Multiculturalism</option>
							<option value="Programs/Student Support Services">Programs/Student Support Services</option>							
							<option value="Reading">Reading</option>
							<option value="Safe and Inclusive Schools">Safe and Inclusive Schools</option>
							<option value="School Support">School Support</option>
							<option value="Science">Science</option>
							<option value="Science and Career Education">Science and Career Education</option>
							<option value="Science and Technology">Science and Technology</option>
							<option value="Student Assistants">Student Assistants</option>
							<option value="Student Assistants/Teachers">Student Assistants/Teachers</option>
							<option value="Student Assistants/Teachers (Schools A-E)">Student Assistants/Teachers (Schools A-E)</option>
							<option value="Student Assistants/Teachers (Schools F-Le)">Student Assistants/Teachers (Schools F-Le)</option>
							<option value="Student Assistants/Teachers (Schools Lf-Sp)">Student Assistants/Teachers (Schools Lf-Sp)</option>
							<option value="Student Assistants/Teachers (Schools Sq-Z)">Student Assistants/Teachers (Schools Sq-Z)</option>
							<option value="Student Services">Student Services</option>
							<option value="Student Support Services">Student Support Services</option>
							<option value="Student Transportation">Student Transportation</option>							
							<option value="Student Transportation Enforcement Officer">Student Transportation Enforcement Officer</option>
							<option value="Support Staff">Support Staff</option>	
							<option value="Support Staff and Student Assistants">Support Staff and Student Assistants</option>
							<option value="TCAS Support">TCAS Support</option>
							<option value="Teacher">Teacher</option>
							<option value="Teachers">Teachers</option>							
							<option value="Teachers (Schools A-E)">Teachers (Schools A-E)</option>
							<option value="Teachers (Schools F-Le)">Teachers (Schools F-Le)</option>
							<option value="Teachers (Schools Lf-Sp)">Teachers (Schools Lf-Sp)</option>
							<option value="Teachers (Schools Sq-Z)">Teachers (Schools Sq-Z)</option>				
							<option value="Teaching Staff">Teaching Staff</option>
							<option value="Technology Education">Technology Education</option>
							<option value="Technology/PowerSchool">Technology/PowerSchool</option>
							<option value="Transportation">Transportation</option>
							<option value="Visually Impaired">Visually Impaired</option>
							<option value="Writing">Writing</option>
	                    </select>
	             </div>
	             </div>
	             </div>
	             
	              <div class="row container-fluid" style="padding-top:5px;">
      				<div class="col-lg-6 col-12">
	             <div class="alert alert-danger" id="warningMessage3" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
	             <div  id="posregionSelect" >
    				<b>C. Position Region</b>
	                     <select id="posRegion" name="posRegion" class="form-control">
	                         <option value="">---- Please Select (Optional) ----</option>
	                    	 <option value="">Not Applicable</option>
	                     	 <option value="Avalon">Avalon</option>
							 <option value="Avalon East">Avalon East</option>
							 <option value="Avalon West">Avalon West</option>
							 <option value="Burin">Burin</option>
							 <option value="Burin/CUPE 1560">Burin/CUPE 1560</option>
							 <option value="Burin/Avalon West">Burin/Avalon West</option>
							 <option value="Central">Central</option>
							 <option value="Labrador">Labrador</option>
							 <option value="Labrador/Burin">Labrador/Burin</option>
							 <option value="Labrador/Central">Labrador/Central</option>
							 <option value="Provincial">Provincial</option>
							 <option value="Vista">Vista</option>
							 <option value="Vista/Avalon West">Vista/Avalon West</option>
							 <option value="Burin/Vista/Avalon West">Burin/Vista/Avalon West</option>
							 <option value="Western/Central">Western/Central</option>
							 <option value="Avalon/Labrador">Avalon/Labrador</option>
							 <option value="Western/Central/Labrador">Western/Central/Labrador</option>
							 <option value="Vista/Burin">Vista/Burin</option>							 
							 <option value="Western">Western</option>
							 
	                     </select>
	            </div>
	            </div>	         
      				<div class="col-lg-6 col-12">
	            
	            <div class="alert alert-danger" id="warningMessage4" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
	            <div  id="posGradeSelect">
    			<b>D. Position Grade Level</b>
	                     <select id="posGrade" name="posGrade" class="form-control">
	                         <option value="">---- Please Select (Optional) ----</option>
	                     	 <option value="">Not Applicable</option>
							 <option value="K-6">K-6</option>
							 <option value="7-12">7-12</option>
							 <option value="K-12">K-12</option>
							 <option value="Intermediate">Intermediate</option>
							 
	                     </select>
	            </div>
	            </div>
	            </div>
	             <div class="row container-fluid" style="padding-top:5px;">
      				<div class="col-lg-6 col-12">
	            <div class="alert alert-danger" id="warningMessage5" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
	            <div  id="posLocationSelect">
    			<b>E. Position Location</b>
	                     <select id="posLocation" name="posLocation" class="form-control">
	                         <option value="">---- Please Select (Optional) ----</option>
	                    	 <option value="">Not Applicable</option> 
	                    	 <option value="Curriculum Centre (Nain)">Curriculum Centre (Nain)</option>
	                     	 <option value="DCC">District Conference Centre</option>
							 <option value="District School">District School</option>
							 <option value="East Point Elementary">East Point Elementary</option>
							 <option value="Gonzaga High School">Gonzaga High School</option>
							 <option value="Holy Heart">Holy Heart</option>
							 <option value="Horizon Academy">Horizon Academy</option>
							 <option value="Hospital School">Hospital School</option>
							 <option value="IMC">IMC</option>
							 <option value="Indian River Academy">Indian River Academy</option>
							 <option value="Makkovik">Makkovik</option>
							 <option value="Nain">Nain</option>
							  <option value="Prince of Wales Collegiate ">Prince of Wales Collegiate </option>							 
							 <option value="SD Cook">SD Cook</option>
							 <option value="Stephenville Elementary">Stephenville Elementary</option>
							 <option value="St. James Elementary">St. James Elementary</option>
							<option value="Avalon Office/HQ">Avalon Office/HQ</option>
							 <option value="Central Office">Central Office</option>
							  <option value="Labrador Office">Labrador Office</option>
							 <option value="Western Office">Western Office</option>
							 <option value="Noton Building">Noton Building</option>							 
							 <option value="Located in School">Located in School</option>

	                     </select>
	            </div>
	            </div>
	            	<div class="col-lg-6 col-12">
	            
	            <div class="alert alert-danger" id="warningMessage6" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>
	            <div  id="posOtherSelect">
    			<b>F. Other</b>
	                     <select id="posOther" name="posOther" class="form-control">
	                         <option value="">---- Please Select (Optional) ----</option>
	                    	 <option value="">Not Applicable</option>
	                    	 <option value="Acting">Acting</option>
	                    	 <option value="Casual">Casual</option>
	                    	  <option value="Casuals">Casuals</option>
	                    	 <option value="CUPE 1560">CUPE 1560</option>
	                    	 <option value="CUPE 1560 (10 Month) Staff">CUPE 1560 (10 Month) Staff</option>
	                    	 <option value="CUPE 1560 (12 Month) Staff">CUPE 1560 (12 Month) Staff</option>
	                    	 <option value="CUPE 1560 (10 and 12 Month) Staff">CUPE 1560 (10 and 12 Month) Staff</option>
	                     	 <option value="Interim">Interim</option>
	                     	 <option value="On Leave">On Leave</option>
							 <option value="Provisional">Provisional</option>
               <option value="Provisional Registration">Provisional Registration</option> 
							 <option value="Temporary">Temporary</option>
							 <option value="">Permanent</option>

	                     </select>
	            </div>
</div></div>


	    </div>
	    </div>

<br/><br/>

	    <div class="card">
						  	<div class="card-header siteSubHeaderBlue">4. SORT ORDER</div>
						  	<div class="card-body">
	            This number is the order on the list in the Division selected for this staff member.
	            (i.e. 1 will obviously be #1 on the list).
	            Use this to sort the positions as you see fit keeping in mind to try to keep all similar positions together.
	            You can use the same number more than once which will keep all those names on the same sort number together. 
	            For the head of a division in your region, assign sort order number 1 to have their title highlighted and displayed. Always sort the staff based on priority order where possible.
	            <br/><br/>
	            <div class="row container-fluid" style="padding-top:5px;">
      				<div class="col-lg-3 col-12">
	            <b>Sort#</b>
    				<select id='sortorder' name='sortorder' class="form-control" required>
	                    	<option value="${ contact.sortorder }">${ contact.sortorder }</option>
	                    	<option value="">-----</option>
	                    	</select>
	        </div></div>


	      </div>
	      </div>
<br/><br/>
	      	<div align="center">
	     	<button type="submit" class="btn btn-success btn-sm">Save Changes</button> &nbsp; <a href="staff_directory.jsp" class="btn btn-sm btn-danger" style="color:white;">Cancel</a> &nbsp; <a href="staff_directory.jsp" class="btn btn-sm btn-info" style="color:white;">Back to Staff List</a>
	       	</div>


          </form>

   </div>


  <script>

if ($("#fullname").val() !='') {
	$("#vacantEnable").css("display","block");
	var currentName = $("#fullname").val();
	var currentEmail = $("#email").val();
}

$(function(){
$("#posVacant").change(function() {

    if (this.checked) {
    	$("#fullname").val("*** VACANT ***");
    	$("#email").val("");
    } else {
    	$("#fullname").val(currentName);
    	$("#email").val(currentEmail);

    }
});
});

  $(function(){
	    for (i=1;i<=250;i++){
	        $("#sortorder").append($('<option></option>').val(i).html(i))
	    }
//count the number of variables.


	 //Take the current position, split it using the - selector for valid format. If not valid format, show error and make user update/build new position title FOR CONSISTANCY.
	var inputText = $("#position").val().split(" - ");
	var desiredOption = inputText[0];
    if (desiredOption == '') {
       // $("#posPrimary").focus();
        return false;
    }





    if (!$('#posPrimary option[value="' +desiredOption+ '"]').prop("selected", true).length) {
    	$("#warningMessage1").html("<b>" + inputText[0] + "</b> is not a valid Position title. Please update by selecting a valid title from the valid Position list below and use the fields (B, C, D, and/or E) below to add to the title to make it similar to current if desired.").css("display","block");
    	$("#posPrimary").focus();
    	$("#posPrimarySelect").addClass("has-error");
    } else {
    $("#posPrimary").select();
    $("#posPrimarySelect").addClass("has-success");
    }
    $( "#posPrimary" ).change(function() {
  	  $( "#warningMessage1" ).fadeOut( "slow", function() {
  		$("#posPrimarySelect").removeClass("has-error");
  	    //May add something.
  	  });
  	});

     //check for second variable in array.
    var desiredOption = inputText[1];

    if (desiredOption == '') {
        return false;
    } else {

    var i;
    for (i = 1; i < inputText.length; i++) {

   	//Search for matching records and select.
    if ($('#posSector option[value="' +inputText[i]+ '"]').prop("selected", true).length) {
    	$("#posSector").select();
    	$("#posSectorSelect").addClass( "has-success" );
    	}
  	if ($('#posRegion option[value="' +inputText[i]+ '"]').prop("selected", true).length) {
  		$("#posRegion").select();
  		$("#posRegionSelect").addClass( "has-success" );
   		}
  	if ($('#posGrade option[value="' +inputText[i]+ '"]').prop("selected", true).length) {
  		$("#posGrade").select();
  		$("#posGradeSelect").addClass( "has-success" );
  		}
	if ($('#posLocation option[value="' +inputText[i]+ '"]').prop("selected", true).length) {
		$("#posLocation").select();
		$("#posLocationSelect").addClass( "has-success" );
	 	}
	if ($('#posOther option[value="' +inputText[i]+ '"]').prop("selected", true).length) {
		$("#posOther").select();
		$("#posOtherSelect").addClass( "has-success" );
	 	}

    }}


    //Write value into field
   $("#posLocation,#posPrimary,#posSector,#posRegion,#posGrade,#posOther").change(function(){

	    	if ($("#posPrimary").val() !="") {
	    		var posPrimary = $("#posPrimary").val();
	    	} else {var posPrimary="N/A"};

	    	if ($("#posSector").val() !="") {
	    		var posSector = " - " + $("#posSector").val();
	    	} else {var posSector=""};

	    	if ($("#posRegion").val() !="") {
	    		var posRegion = " - " + $("#posRegion").val();
	    	} else {var posRegion=""};

	    	if ($("#posGrade").val() !="") {
	    		var posGrade = " - " + $("#posGrade").val();
	    	} else {var posGrade=""};

	    	if ($("#posLocation").val() !="") {
	    		var posLocation = " - " + $("#posLocation").val();
	    	} else {var posLocation=""};

	    	if ($("#posOther").val() !="") {
	    		var posOther = " - " + $("#posOther").val();
	    	} else {var posOther=""};

	    	$("#position").val($("#posPrimary").val() + posSector + posRegion + posGrade + posLocation + posOther);

	    });



	});


  </script>

  </body>

</html>
