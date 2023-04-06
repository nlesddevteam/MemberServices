<%@ page language="java" contentType="text/html" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html> 
<head>
<title>Payroll Resources</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style>
.card {min-height:180px;}
.card-text {text-align:left;}
.card-title {text-transform:uppercase;font-size:14px;font-weight:bold;color:white;}

</style>
</head>

<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Payroll Resources</div>

Below are some Payroll resources for teaching and support staff.

<br/><br/>

<div class="card-columns">
  <div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">SUPPORT STAFF</h4>
      <p class="card-text">
      <ul>
		<li><a href='/MemberServices/StaffRoom/doc/Payroll_Direct_Deposit_Application.pdf' target="_blank">Direct Deposit Application <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
		<li><a href='/MemberServices/StaffRoom/doc/Casual_Support_Staff_Timesheet.pdf' target="_blank">Casual Staff Timesheet <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>              
		<li><a href='/MemberServices/StaffRoom/doc/Support_Staff_Over_Time_Timesheet.pdf' target="_blank">OT Timesheet <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
	</ul> 
      
      </p>
    </div>
  </div>
<div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">TEACHERS/SA</h4>
      <p class="card-text">
      <ul>
 	<li><a href='doc/PayrollDirectDepositForm.pdf' target="_blank">Direct Deposit Form <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
 	<li><a href='doc/Third_Party_Billings.pdf' target="_blank">Third Party Billing <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>              
	</ul> 
      
      </p>
    </div>
  </div>
  <div class="card">
    <div class="card-body">
     <h4 class="card-title text-center bg-primary">OTHER DOCS</h4>
      <p class="card-text">
      <ul>
  	<li><a href="doc/Address_Change_Form.pdf" target="_blank">Address Change Form <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
	<li><a href='doc/2022_Federal_TD1.pdf' target="_blank">Federal TD1 (2022) <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li> 
	<li><a href='doc/2022_Provincial_TD1.pdf' target="_blank">Provincial TD1 (2022) <i style="color:Red;" class="fa-solid fa-file-pdf"></i></a></li>
	<li><a href='/contact/districtstaffdirectory.jsp?search=Payroll' target="_blank">Payroll Staff</a></li>
	<li><a href='https://docs.google.com/forms/d/e/1FAIpQLScF8w3I0vvsK6dqWtHBlwiCUOPV6A4LZOn--VSxan51F7N3lw/viewform' target="_blank">Payroll HelpDesk <i style="color:Green;" class="fas fa-external-link-alt"></i></a></li>
						
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