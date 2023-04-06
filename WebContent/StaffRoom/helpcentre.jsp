<%@ page language="java" contentType="text/html" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html> 
<head>
<title>StaffRoom Help Centre</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<style>
.card-header {font-weight:bold;font-size:16px;}
.cardImg {width:100%;max-width:300px;padding:3px;}
.containerPanel {float:left;width:25%;}
.containerPanelHeader {font-weight:bold;}
.card-body {text-align:center;}
.card {min-width:330px;margin-top:20px;max-width:410px;}
.card-text {text-align:left;}
.card-title {text-transform:uppercase;font-size:14px;font-weight:bold;color:white;}

</style>

</head>

<body>
<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<img src="includes/img/helplarge.png" style="float:right;max-height:150px;"/>
<div class="siteHeaderGreen">HELP CENTRE INFORMATION</div>
 
  							
	<p>Below are listed most of our main online tools and application services and associated support contacts. 
	Some issues can be resolved at the school level, or simply by trying another computer, device or web browser. 
	While we try to make all our systems compatible with all devices, there will be some technical issues at times. 
	Please feel free to contact the support staff in your region for assistance.
	 (You can also visit our <a href="/contact/districtstaffdirectory.jsp" target="_blank">Staff Directory</a> for a complete list of District contacts.)
	<p>Please select a topic from the list below:





<div align="center">
<div class="card-deck"> 
  <div class="card text-center">
  <div class="card-header">IT Assistance Portal</div>
    <div class="card-body">
     <img src="includes/img/itportal.png" class="cardImg"/><br/><br/>
        A IT Assistance Web portal containing a variety of resources, request forms and guides has been created to better assist you in your requests for IT Assistance for your school and staff. 
  		
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://itassist.nlesd.ca">IT Assist Portal</a> </div>
  </div>
  <div class="card text-center">
  <div class="card-header">IT Service Desk</div>
    <div class="card-body">
    <img src="includes/img/helpdesk.png" class="cardImg"><br/><br/>
    For all technical support services (such as networking issues, system software issues, and school based systems troubleshooting and repair) in each region, administrators are required to enter a help request ticket into the Help Desk system where the ticket will be addressed by a desinated support technician in your region for the related issue.
  							
  	<p> If you have an issue using the help desk, please contact your regional office technical support staff.
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://servicedesk.nlesd.ca/">IT Service Desk</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">Finance Help Desk</div>
    <div class="card-body">
    <img src="includes/img/finance.jpg" class="cardImg"><br/><br/>
							This Helpdesk is intended to help streamline responses to finance related inquiries from Schools and Regional Office staff. 
							Common topics include, but are not limited to:
					eFunds, Fundraising, School Generated Funds, Via SDS, Cayenta, Instructional Allocation, Purchasing Procedures, Fund 3 Accounts    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://docs.google.com/forms/d/e/1FAIpQLSds_59KsxMEzt4fiIqgcyMXDVfhhZ18eyDonU-ff-CCqq16cQ/viewform?usp=sf_link">Finance Helpdesk</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">Travel Claim Support</div>
    <div class="card-body">
     <img src="includes/img/travel.png" class="cardImg"><br/><br/>
      If you are have any questions regarding a travel claim entry or payments, please visit the Travel Help Desk. 
      For issues regarding travel km and meal rates and approved rate applications and approvals, please contact Susan Hussey (Email: <a href="mailto:susanhussey@nlesd.ca?subject=Travel Rates">susanhussey@nlesd.ca</a> &middot; Tel: (709) 758-2382).
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://docs.google.com/forms/d/e/1FAIpQLSfWm5FdaMXKCAAdSZZM_gTQGgkU7zkiUkZPupMWM9Pi-96NOw/viewform">Travel Help Desk</a> </div>
  </div>
  <div class="card text-center">
  <div class="card-header">MyHR Profile/Employment</div>
    <div class="card-body">
    <img src="includes/img/myhr.png" class="cardImg"> <br/><br/>
        MyHRP is an employment portal where you can register a complete online profile in our system to apply for educational or support positions 
        throughout the district.          
       
  		<p>If you have any questions or problems with your online application and the approval process, please visit the MyHRP HelpDesk.
  		For regional HR contacts, please visit the <a href="/employment/index.jsp" target="_blank">MyHr Portal</a> or email below:	<br/>
  		<a href="mailto:hrlabrador@nlesd.ca?subject=Labrador Jobs" title="hrlabrador@nlesd.ca">LABRADOR</a> &middot; <a href="mailto:hrwest@nlesd.ca?subject=Western Jobs" title="hrwest@wnlesd.ca">WESTERN</a> &middot;
  		<a href="mailto:hrcentral@nlesd.ca?subject=Central Jobs" title="hrcentral@nlesd.ca">CENTRAL</a> &middot; <a href="mailto:hravalon@nlesd.ca?subject=Avalon Jobs" title="hravalon@nlesd.ca">AVALON</a>						
  				
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" target="_blank" href="https://sites.google.com/nlesd.ca/myhrphelp/home">Help Guide</a> <a class="btn btn-primary btn-sm" target="_blank" href="https://forms.gle/zudb87zxbJW9QTVg8">Help Form</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">Google Apps (G-Suite)</div>
    <div class="card-body">
    <img src="includes/img/google.png" class="cardImg"><br/><br/>
    Technical support for issues related to Google Apps will continue to come from our District support channels
     (<a target="_blank" href="https://servicedesk.nlesd.ca/">Service Desk</a> and <a target="_blank" href="https://itassist.nlesd.ca">IT Assist Portal</a>) as they do for all of our applications. 
    <p>Please visit our <a href="googleapps.jsp">G-Suite page</a> for details.    
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" href="https://servicedesk.nlesd.ca/" target="_blank">Service Desk</a> <a class="btn btn-primary btn-sm" href="https://itassist.nlesd.ca" target="_blank">IT Assist</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">PowerSchool Support</div>
    <div class="card-body">
    <img src="includes/img/powerschoollogo.jpg" class="cardImg"><br/><br/>
        PowerSchool is a tool whereby Parents can login to see their students progress in school. 
        If parents are having issues logging into their PowerSchool account, they are advised to contact their local school, or one of the District PowerSchool contacts below. 
        Administrators having issues may contact:<br/>
        <b>Corey Downey</b> (Labrador/Avalon)<br/>Tel: (709) 256-2547 Ext:298 &middot; Email: <a href="mailto:coreydowney@nlesd.ca?subject=PowerSchool Support Request">coreydowney@nlesd.ca</a><br/>
        <b>Mike Burt</b> (Western/Central)<br/>Tel: (709) 637-6769 &middot; Email: <a href="mailto:mikeburt@nlesd.ca?subject=PowerSchool Support Request">mikeburt@nlesd.ca</a>
    </div>
  <div class="card-footer"><a class="btn btn-warning btn-sm" href="mailto:coreydowney@nlesd.ca?subject=PowerSchool Support Request"><i class="fa-regular fa-envelope"></i> Labrador/Avalon</a> 
  <a class="btn btn-warning btn-sm" href="mailto:mikeburt@nlesd.ca?subject=PowerSchool Support Request"><i class="fa-regular fa-envelope"></i> Western/Central</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">SmartFind Express Support</div>
    <div class="card-body">
    <img src="includes/img/smartfind.jpg" class="cardImg"> <br/><br/>
       SmartFind Express is a program we are running in schools throughout the District as an automated substitute teacher / 
       student assistant call in system for school administrators. 
							<p>It will automatically call in the next available registered substitute teacher or student assistant for their school. 
							If you have any issues with SmartFind Express, please email <a href="mailto:smartfindsupport@nlesd.ca?subject=SmartFind Support Request">smartfindsupport@nlesd.ca</a>.
    </div>
  <div class="card-footer"><a class="btn btn-warning btn-sm" href="mailto:smartfindsupport@nlesd.ca?subject=SmartFind Support Request"><i class="fa-regular fa-envelope"></i> SmartFind Support</a></div>
  </div>
  
  <div class="card text-center">
  <div class="card-header">Microsoft Office and Applications</div>
    <div class="card-body">
    <img src="includes/img/office365.jpg" class="cardImg"> <br/><br/>
       For your Microsoft Office Applications, Microsoft 365 Online, Skype, etc, please contact:<br/>
      <b>Gordon Moller</b><br/>Tel: (709) 256-2547 &middot; Email: <a href="mailto:gordonmoller@nlesd.ca?subject=Microsoft Based Support Request">gordonmoller@nlesd.ca</a>
  	 </div>
  <div class="card-footer"><a class="btn btn-warning btn-sm" href="mailto:gordonmoller@nlesd.ca?subject=Microsoft Based Support Request"><i class="fa-regular fa-envelope"></i> Office 365 Support</a></div>
  </div>
  
  <div class="card text-center">
  <div class="card-header">E-Serve TimeSheet Login / Profile</div>
    <div class="card-body">
     <img src="includes/img/eserverw.jpg" class="cardImg"><br/><br/>
        Eserve is a method by which timesheets are electronically submitted to payroll. 
        This process is currently being implemented throughout NLESD and will apply to all 10 &amp; 12 month support staff employees.   		
  		Timesheets are available for completion 3 days prior to the pay period end date.
  				
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" href="https://forms.gle/rUbTMWgW77Q9NJYy6" target="_blank">Payroll Helpdesk</a> <a class="btn btn-primary btn-sm" target="_blank" href="https://sdsweb.nlesd.ca/sds/eserve/reminder.xsp">Password Recovery</a></div>
  </div>
  
  <div class="card text-center">
  <div class="card-header">Payroll Help Desk</div>
    <div class="card-body">
     <img src="includes/img/payhelpdesk.png" class="cardImg"><br/><br/>
       NLESD Payroll Inquiry Form (Department Paid Staff Only). 
If you fall under one of the following employee types and you have a Payroll inquiry, please complete this form.
Teachers, Teaching & Learning Assistant, Student Assistant, Substitute, Guidance Counsellor, Program Specialist, School Administrator, Senior Management, or TCAS Administrators.
 				
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" href="https://forms.gle/rUbTMWgW77Q9NJYy6" target="_blank">Payroll Helpdesk</a></div>
  </div>
  <div class="card text-center">
  <div class="card-header">StaffRoom (MemberServices)</div>
    <div class="card-body">
     <img src="includes/img/staffroom.png" class="cardImg"><br/><br/>
    The StaffRoom (formerly Member Services) is an employee only resource that consists of various employee applications such as a Profession Development Calendar, 
    Travel Claim System, and HR Applications. The availability of various applications is based on your level of access to the system and what school and/or position you are assigned. 
    The login for all StaffRoom (MemberServices) accounts is the same as your Google email login credentials. 
    If you are already logged into your NLESD email, you should automatically login to the StaffRoom. 
    If Google doesn't find a match to your email address it will prompt you to create an account. 
    If you changed your email address, Google will still prompt for new account. Please DO NOT continue if you already have a StaffRoom (MemberServices) account and instead contact StaffRoom HelpDesk below or email <a href="mailto:geofftaylor@nlesd.ca?subject=StaffRoom Support Request">geofftaylor@nlesd.ca</a>.
 				
    </div>
  <div class="card-footer"><a class="btn btn-primary btn-sm" href="https://forms.gle/rUbTMWgW77Q9NJYy6" target="_blank">StaffRoom Helpdesk</a></div>
  </div>
  
  <!-- 
  <div class="card text-center">
  <div class="card-header">Purchasing Information</div>
    <div class="card-body">
     <img src="includes/img/staffroom.png" class="cardImg"><br/><br/>
    The NLESD carries out its procurement in compliance with the provincial governments <a href="http://assembly.nl.ca/Legislation/sr/statutes/p41-001.htm" target="_blank">Public Procurement Act</a> and 
    its associated <a href="http://www.assembly.nl.ca/legislation/sr/annualregs/2018/nr180013.htm" target="_blank">Public Procurement Regulations</a> and <a href="http://www.gpa.gov.nl.ca/division/policy/index.html" target="_blank">Policy</a>. 
    This includes obtaining quotes for relevant goods and services, and issuing open calls when required.
    Purchasing questions can be directed to one of the following NLESD buyers:<br/>
    Jamie Whitten<br/>
(709) 758-2395 &middot; jamiewhitten@nlesd.ca<br/>
Jackie Ralph<br/>
(709) 757-4701 &middot; jackieralph@nlesd.ca<br/>
Mark Green<br/>
(709) 758-0869 &middot; markgreen@nlesd.ca<br/>

    </div>
  <div class="card-footer"><a class="btn btn-warning btn-sm" href="/business/index.jsp">Tenders/Vendors</a></div>
  </div>
  
  
    <div class="card text-center">
  <div class="card-header">Accounts Payable</div>
    <div class="card-body">
     <img src="includes/img/staffroom.png" class="cardImg"><br/><br/>
    The NLESD carries out its procurement in compliance with the provincial governments <a href="http://assembly.nl.ca/Legislation/sr/statutes/p41-001.htm" target="_blank">Public Procurement Act</a> and 
    its associated <a href="http://www.assembly.nl.ca/legislation/sr/annualregs/2018/nr180013.htm" target="_blank">Public Procurement Regulations</a> and <a href="http://www.gpa.gov.nl.ca/division/policy/index.html" target="_blank">Policy</a>. 
    This includes obtaining quotes for relevant goods and services, and issuing open calls when required.
    Purchasing questions can be directed to one of the following NLESD buyers:<br/>
    Jamie Whitten<br/>
(709) 758-2395 &middot; jamiewhitten@nlesd.ca<br/>
Jackie Ralph<br/>
(709) 757-4701 &middot; jackieralph@nlesd.ca<br/>
Mark Green<br/>
(709) 758-0869 &middot; markgreen@nlesd.ca<br/>

Vendors can submit their invoices for payment via email below:

Call 758-2917 or 758-2316.
    </div>
  <div class="card-footer"><a class="btn btn-warning btn-sm" href="mailto:AccountsPayable@nlesd.ca?subject=Accounts Payable Inquiry"><i class="fa-regular fa-envelope"></i> Accounts Payable</a></div>
  </div>
 

  -->
  
  
  
</div>





</div>


   
  <div style="clear:both;"></div>
<br/><br/>
 <div align="center"><a href="../navigate.jsp" class="btn btn-danger btn-sm">Back to StaffRoom</a></div>
  

 

</div> 
</div>
</div>

</body>
</html>