<%@ page language="java" contentType="text/html" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html> 
<head>
<title>Staff Resources</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style>
.card {min-height:250px;}
.card-text {text-align:left;}
.card-title {text-transform:uppercase;font-size:14px;font-weight:bold;color:white;}
</style>
</head>

<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Staff Resources</div>
Most of these links will open in a new tab or browser window. Documents will require Adobe Acrobat reader or compatible device PDF document reader.<br/><br/>
<div class="card-columns">
  <div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">TEACHER/EDUCATIONAL</h4>
      <p class="card-text">
      	<ul>
			<li><a href='https://www.gov.nl.ca/education/k12/teaching/certification/' target="_blank">Teacher Certification <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		    <li><a href="https://hi.easternhealth.ca/healthy-living/sexual-health/puberty/" target="_blank">Teacher Resource: Grade 4-6 Puberty <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
			<li><a href="/MemberServices/staffroom/includes/doc/TeacherResourcegr7-9sexualhealthandwellbeing.pdf" target="_blank">Teacher Resource: Grade 7-9 Sexual Health &amp; Wellness <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
			<li><a href='doc/edleave_app.pdf' target="_blank">Educational Leave Form <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
			<li><a href="doc/Eligible_Educators_School_Supply_Credit_Info.pdf" target="_blank">School Supply Credit for Educators <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li> 
			<li><a href="teacherseniorityreports.jsp" >Teacher Seniority Reports</a></li>
			
		</ul> 
      
      </p>
    </div>
  </div>
<div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">EMPLOYMENT</h4>
      <p class="card-text">
      <ul>
			<li><a href='/employment/adminpositions.jsp' target="_blank">Admin Positions</a></li>				                        
           	<li><a href='/employment/teachingpositions.jsp' target="_blank">Teaching/TLA Positions</a></li>
           	<li><a href='/employment/subpositions.jsp' target="_blank">Substitute/TLA Positions</a></li>
            <li><a href='/employment/ssminternalpositions.jsp' target="_blank">Internal Jobs</a></li>
            <li><a href='/employment/ssmexternalpositions.jsp' target="_blank">External Jobs</a></li>
            <li><a href='https://www.hiring.gov.nl.ca/Jobs.aspx/Public' target="_blank">NL Public Positions <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>    
            <li><a href='/employment/index.jsp' target="_blank">MyHR Profile Login</a>
	 </ul> 
      
      </p>
    </div>
  </div>
<div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">OTHER</h4>
      <p class="card-text">
      <ul>
		<li><a href='http://www.exec.gov.nl.ca/exec/hrs/pensions/index.html' target="_blank">Public Sector Pensions <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		<li><a href='http://www.ed.gov.nl.ca/edu' target="_blank">Dept of Education <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>         
		<li><a href='https://www.nlta.nl.ca/eap' target="_blank">NLTA Employee Assistance Program <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		<li><a href='http://www.psc.gov.nl.ca/psc/eap/index.html' target="_blank">Government Employee Assistance Program <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li> 
		<li><a href='http://www.exec.gov.nl.ca/exec/hrs/working_with_us/collective_agreements/index.html' target="_blank">Collective Agreements <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		<li><a href='https://www.nlta.nl.ca/home' target="_blank">NLTA Website <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		<li><a href='http://www.nape.nf.ca' target="_blank">NAPE Website <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
		<li><a href='http://nl.cupe.ca' target="_blank">CUPE Website <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>	
	</ul> 
      
      </p>
    </div>
  </div>
</div>
<div style="clear:both;"></div>
 <jsp:include page="includes/acrobat.jsp" />								         
 
<p><div align="center">
<a href="../navigate.jsp" class="btn btn-sm btn-danger">Back to StaffRoom</a>
</div>

</div> 
</div>
</div>

</body>
</html>