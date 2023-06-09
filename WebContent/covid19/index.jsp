<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*"%>

<esd:SecurityCheck />
<%
  User usr = null;
  usr = (User) session.getAttribute("usr");  
  if(usr == null)
  {%>
  	 <jsp:forward page="/MemberServices/login.html">
      <jsp:param name="msg" value="This is a Secure Resource!<br>Please Login."/>
    </jsp:forward>
  <%} %> 


<html>
  <head>
    <title>Member Services - COVID-19</title>    
 
  </head>
  <body>
  
<!-- TEACHER NAME UPDATE -->

<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="font-size:12px;">   
      <div class="pageHeader">District Monitoring Ongoing Coronavirus Situation</div>
      
The Newfoundland and Labrador English School District is in regular contact with the Department of Health and Community Service and relevant public health agencies as the situation surrounding COVID-19 (Novel Coronavirus) evolves. As with all matters of public health, the District will continue to take guidance from the Provincial Government and public health authorities in supporting our school communities.
<br/><br/>
Information Shared with Schools and District Staff can be found below:
<br/><br/>
   <ul>     
  		<li><a href="includes/doc/c19-sa-08192020.pdf" target="_blank">August 19 - Memo to Student Assistants (Change to Recall Date - September 2, 2020)</a>
   		<li><a href="includes/doc/c19-tla-08192020.pdf" target="_blank">August 19 - Memo to Teaching and Learning Assistants (Change to Start Date - September 2, 2020)</a>
  		<li><a href="includes/doc/c19-admin-07202020.pdf" target="_blank">July 20 - Memo to Principals/Teachers (District Update)</a>   
		<li><a href="includes/doc/c19-admin-05292020.pdf" target="_blank">May 29 - Memo to Principals (District Update)</a>
		<li><a href="includes/doc/c19-teach-05262020.pdf" target="_blank">May 26 - Memo to All Teachers (Changes to Recruitment Process)</a>   
   		<li><a href="includes/doc/c19-teach-05252020.pdf" target="_blank">May 25 - Memo to School Staff (June 1st Building Reopening for School Teaching Staff)</a>
     	<li><a href="includes/doc/c19-teach-05122020.pdf" target="_blank">May 12 - Memo to Teachers (Recruitment Process for 2020-21)</a>
        <li><a href="includes/doc/c19-lah-05082020.pdf" target="_blank">May 8 - Memo Learning at Home Resource Being Distributed Via Canada Post </a>
  		<li><a href="includes/doc/c19-admin-05072020.pdf" target="_blank">May 7 - Memo to Administrators (Personal Materials Retrieval )</a>
  		<li><a href="includes/doc/c19-admin-04022020.pdf" target="_blank">April 2 - Memo to Administrators/Teachers (Next Steps; General and High School)</a>
  		<li><a href="includes/doc/c19-gc-03312020.pdf" target="_blank">March 31 - Memo to Guidance Counsellors (Volunteer Guidance Initiatives)</a>
   		<li><a href="includes/doc/c19-sservices-03302020.pdf" target="_blank">March 30 - Memo to Administrators, Guidance Counsellors, Educational Psychologists (Supporting Students &amp; Assessments)</a>
     	<li><a href="includes/doc/c19-guidelines-03302020.pdf" target="_blank">March 30 - Guidelines and Support for Administrators, Teachers, and Programs Staff: Google Meet</a>
     	<li><a href="includes/doc/c19-stravel-03232020.pdf" target="_blank">March 25 - Memo to Select Schools (Remaining Student Travel Scheduled for 2019-2020 School Year Cancelled)</a>
     	<li><a href="includes/doc/c19-admin-03232020.pdf" target="_blank">March 23 - Memo to Administrators/Teachers (Learning at Home - Online Resource)</a>
     	<li><a href="includes/doc/c19-admin-03222020.pdf" target="_blank">March 22 - Memo to Administrators (Access to School Facilities)</a>
     	<li><a href="includes/doc/c19-teaadmin-03192020.pdf" target="_blank">March 19 - Memo to All Teachers/Administrators (Contact with Students/Next Steps)</a>     	
     	<li><a href="includes/doc/c19-unions-03192020.pdf" target="_blank">March 19 - Memo to NAPE and CUPE Members (Compensation)</a>
     	<li><a href="includes/doc/c19-admins-03182020.pdf" target="_blank">March 18 - Memo to Administrators (Collection of Student Belongings)</a>     	
     	<li><a href="includes/doc/c19-admins-03172020.pdf" target="_blank">March 17 - Memo to Administrators (Retrieval of Student Items - Change of Plans)</a>
     	<li><a href="includes/doc/c19-parents-03172020.pdf" target="_blank">March 17 - Memo to Families (District Cancels Plan for Students/Parents to Return to Schools)</a>
     	<li><a href="includes/doc/c19-parents-03162020.pdf" target="_blank">March 16 - Memo to Families (Suspension of In-Class Instruction/Scheduled School Materials Pick-Up)</a>
		<li><a href="includes/doc/c19-emp-03162020.pdf" target="_blank">March 16 - Memo to All Employees (Protocols; Return to Work Following Out of Country Travel)</a>
     	<li><a href="includes/doc/c19-all-03132020.pdf" target="_blank">March 13 - Memo to All Staff (Professional Development/Community Use of Schools)</a>
		<li><a href="includes/doc/c19-prec-03132020.pdf" target="_blank">March 13 - Memo to Administrators (Extra-curricular Activities Cancellations)</a>
		<li><a href="includes/doc/c19-aemp-03132020.pdf" target="_blank">March 13 - Memo to All Employees (Update: COVID-19 protocols)</a>
		<li><a href="includes/doc/c19-mfamst-03102020.pdf" target="_blank">March 10 - Memo to Chaperones/ Families of Students Travelling</a>
</ul>
   
    
   
   </div>
  				</div>
 </div>   
 
<div align="center" style="padding-top:5px;padding-bottom:10px;"><a class="btn btn-sm btn-primary" href="/families/covid19.jsp" target="_top">District COVID-19 Information Page</a> <a href="/MemberServices/navigate.jsp" class="btn btn-danger btn-sm">Exit to Member Services</a></div>
   
  </body>
</html>
