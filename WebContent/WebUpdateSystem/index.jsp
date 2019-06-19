<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />
<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="includes/js/jquery-1.7.2.min.js"></script>
			<script src="includes/js/jquery-1.9.1.js"></script>
			<script src="includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="includes/js/common.js"></script>
			<script src="includes/js/nlesd.js"></script>
		
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
		<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#news_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

	);

</script>
<script src="includes/ckeditor/ckeditor.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	</head>

  <body><br/>
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">				
				<p>Below are a selection of website editing tools available to you. Not all options are available to all members.
				<p>		
												
				<div align="center">
				<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-ANNOUNCEMENTS">
				<div class="menuIconImage">      
	          		       	      
	          		<a href="Tenders/viewTenders.html"><img src="includes/img/viewtenders-off.png" class="img-swap menuImage" title="View Tenders"></a>
	          	</div>		
	          	</esd:SecurityAccessRequired>
	          	 <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BOARDMINUTES">          	
				<div class="menuIconImage">      
	          		        	      
	          		<a href="MeetingMinutes/viewMeetingMinutes.html"><img src="includes/img/viewminutes-off.png" class="img-swap menuImage" title="View Meeting Minutes"></a>
	          	</div>	
	          	</esd:SecurityAccessRequired>
	          	 <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BOARDHIGHLIGHTS">
				<div class="menuIconImage">      
	          		       	      
	          		<a href="MeetingHighlights/viewMeetingHighlights.html"><img src="includes/img/viewhighlights-off.png" class="img-swap menuImage" title="View Meeting Highlights"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-ANNOUNCEMENTS">
	          	<div class="menuIconImage">     
	          		        	      
	          		<a href="Banners/viewBanners.html"><img src="includes/img/viewbanners-off.png" class="img-swap menuImage" title="View Banners"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-DIRECTORSWEB">
	          	<div class="menuIconImage">      
	          		        	      
	          		<a href="Blogs/viewBlogs.html"><img src="includes/img/viewblogs-off.png" class="img-swap menuImage" title="View Director's Blogs"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	 <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-DISTRICTPOLICIES">
	          	<div class="menuIconImage">      
	          		        	      
	          		<a href="Policies/viewPolicies.html"><img src="includes/img/viewpolicies-off.png" class="img-swap menuImage" title="View Policies"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-ANNOUNCEMENTS">
	          	<div class="menuIconImage">      
	          		        	      
	          		<a href="NewsPostings/viewNewsPostings.html"><img src="includes/img/viewnews-off.png" class="img-swap menuImage" title="View News Items"></a>
	          	</div>
	          	</esd:SecurityAccessRequired> 
	          	 
	          	          	
	          	<esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES">
	          	<div class="menuIconImage">      
	          		        	      
	          		<a href="BusRoutes/school_directory_bus_routes.jsp"><img src="includes/img/viewbusroutedoc-off.png" class="img-swap menuImage" title="View Bus Route Documents"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	
	          	
	          	<esd:SecurityAccessRequired roles="ADMINISTRATOR">
	          	<div class="menuIconImage">    
	          		        	 
	          		<a href="/MemberServices/WebUpdateSystem/StaffDirectory/staff_directory.jsp"><img src="includes/img/viewstaff-off.png" class="img-swap menuImage" title="View Office Staff"></a>
	          	</div>
	          	          	
	          	
	          	</esd:SecurityAccessRequired>
	          	
	          	<esd:SecurityAccessRequired roles="ADMINISTRATOR">
	          	<div class="menuIconImage">      
	          		        	      
	          		<a href="/MemberServices/MemberAdmin/Apps/Schools/school_directory.jsp"><img src="includes/img/view-schools-off.png" class="img-swap menuImage" title="View Schools"></a>
	          	</div>
	          	          	
	          	
	          	</esd:SecurityAccessRequired>
	          	
	          	
	          	
	          	
	         <!-- <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-DISTRICTPOLICIES">
	          	<div class="menuIconImage">      
	          		<a href="Programs/addNewProgram.html"><img src="includes/img/addprogram-off.png" class="img-swap menuImage" title="Add Course Descriptor"></a>        	      
	          		<a href="Programs/viewPrograms.html"><img src="includes/img/viewprograms-off.png" class="img-swap menuImage" title="View Course Descriptors"></a>
	          	</div>
	          	</esd:SecurityAccessRequired>
	          	-->
	          	
	          	
	          	</div>
	          	<p>
	          	
	          	If you add special documents (see Other Files Section when editing a posting) to a posted item in any category above, please use keywords in their title (where applicable) like <i>form</i> or <i>presentation</i>, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
				
		
		<br/><br/>
		<div align="center">
					<A HREF='../navigate.jsp'><img src="includes/img/back-off.png" border="0" class="img-swap"></a>
					</div>
	
		<br/><br/>
		
			</div>
			</div>

<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../navigate.jsp" title="Back to MemberServices Main Menu"><img src="includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    
  </body>

</html>	
		