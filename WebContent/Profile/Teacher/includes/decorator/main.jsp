<!-- TEACHER APP DECORATOR FILE (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->

<%@ page language="java"
        session="true"
         import="com.awsd.security.*, 
                 java.util.*,com.awsd.school.*,com.awsd.personnel.*"%>


<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
        <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<esd:SecurityCheck permissions='PERSONNEL-PROFILE-TEACHER-VIEW,PERSONNEL-PROFILE-SECRETARY-VIEW' />


<html>
  
  <head>
  	
	<!-- META TAGS  -->
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">			
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				
    	<meta name="generator" content="Eclipse Java EE IDE for Web Developers. Version: Oxygen.3 Release (4.7.3)">
	    <meta name="dcterms.created" content="Monday, April 15, 2019">    	     				
		<meta name="description" content="Professional Learning Plan System" />	
		<meta name="keywords" content="MyPLP,NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		
		<title><decorator:title default="Teaching/Secretary Profile Update" /></title>
	
	<!-- CSS STYLESHEET FILES -->	
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.dataTables.min.css">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">		
		<link rel="stylesheet" href="/MemberServices/Profile/Teacher/includes/css/tprofile.css">			
		<link rel="shortcut icon" href="/MemberServices/Profile/Teacher/includes/img/favicon.ico">	
		<link href="/MemberServices/Profile/Teacher/includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link href="/MemberServices/Profile/Teacher/includes/css/hover_drop_2.css" rel="stylesheet" media="all" type="text/css" />				
		


	
	<!-- JAVASCRIPT FILES -->
		
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>	
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  		
  		<script src="/MemberServices/Profile/Teacher/includes/js/bootstrap-confirmation.min.js"></script> 			
		<script src="/MemberServices/Profile/Teacher/includes/js/jquery-ui.js"></script>
		<script src="/MemberServices/Profile/Teacher/includes/js/jquery.cookie.js"></script>		
		<script src="/MemberServices/Profile/Teacher/includes/js/bootstrap-multiselect.js"></script>
		<script src="/MemberServices/Profile/Teacher/includes/js/iefix.js"></script>
	    <script src="/MemberServices/Profile/Teacher/includes/js/jquery.validate.js"></script>
	    <script src="/MemberServices/Profile/Teacher/includes/js/tprofile.js"></script> 
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>				
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		
		  ga('create', 'UA-74660544-1', 'auto');
		  ga('send', 'pageview');

		</script>
		<decorator:head />	

			

	</head>

	<body>
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/Profile/Teacher/includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>
		
	<!-- TOP PANEL -->
		<div class="mainContainer">	
		
		
		
			<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<img class="topLogoImg" style="max-width:600px;width:100%;" src="/MemberServices/Profile/Teacher/includes/img/plogo.png" title="Newfoundland and Labrador English School District">
				  </div>				  
				</div>		
			</div>


<!-- PAGE BODY -->


			
				<div class="container-fluid">				
					
					
					<div id="printJob">			
					
								<decorator:body />	
					</div>

				</div>



<div class="alert alert-info no-print" style="text-align:center;font-size:11px;">
Any questions or concerns relating to your teaching position/school should be directed to Human Resources, your regional Director of Schools, or your local school administrator.<br/> 
If you are experiencing technical difficulties with this system, email <a href="mailto:geofftaylor@nlesd.ca?subject=Teacher MiniProfile System">geofftaylor@nlesd.ca</a>.
</div>

<!-- FOOTER AREA -->		
	
	<div class="mainFooter no-print"> 
	 
	
		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">		
		  Teacher/Support Staff MS Profile App 1.0 &copy; 2019 Newfoundland and Labrador English School District &middot; All Rights Reserved 
		 </div>
		  
		</div> 
	 	
		
	</div>	


</div>

<!-- ENABLE PRINT FORMATTING -->		
		<script src="/MemberServices/Profile/Teacher/includes/js/jQuery.print.js"></script> 
		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
		
		</script>
	
</body>

</html>

<!-- END DECORATOR -->

