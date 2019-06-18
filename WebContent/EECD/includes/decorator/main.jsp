<!-- EECD DECORATOR  FILE (C) 2019  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->

<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
        <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<html>
  
  <head>
  	
	<!-- META TAGS  -->
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">			
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				
    	<meta name="generator" content="Eclipse Java EE IDE for Web Developers. Version: Oxygen.3 Release (4.7.3)">
	    <meta name="dcterms.created" content="Tuesday, 3 July 2018 12:59:00 GMT">    	     				
		<meta name="description" content="EECD Working Groups/Committees System" />	
		<meta name="keywords" content="EECD,NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		
		<title><decorator:title default="EECD Working Groups/Committees System" /></title>
	
	<!-- CSS STYLESHEET FILES -->	
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">		
		<link rel="stylesheet" href="/MemberServices/EECD/includes/css/eecd.css">
		<link rel="stylesheet" href="includes/css/buttons.dataTables.min.css" >		
			
		<link href="/MemberServices/Personnel/includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link href="/MemberServices/Personnel/includes/css/hover_drop_2.css" rel="stylesheet" media="all" type="text/css" />
		
	<!-- JAVASCRIPT FILES -->		
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>	
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	 
  		<script src="/MemberServices/EECD/includes/js/bootstrap-confirmation.min.js"></script>  			
		<script src="/MemberServices/EECD/includes/js/jquery-ui.js"></script>		
		<script src="/MemberServices/EECD/includes/js/bootstrap-multiselect.js"></script>	
	    <script src="/MemberServices/EECD/includes/js/jquery.validate.js"></script>
	    <script src="/MemberServices/EECD/includes/js/eecd.js"></script>
	    <script src="/MemberServices/EECD/includes/js/eecd_view.js"></script>	   
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="/MemberServices/EECD/includes/js/dataTables.buttons.min.js"></script>
	  	<script src="/MemberServices/EECD/includes/js/buttons.flash.min.js"></script>
	  	<script src="/MemberServices/EECD/includes/js/buttons.html5.min.js"></script>
	  	<script src="/MemberServices/EECD/includes/js/buttons.print.min.js"></script>
	  	<script src="/MemberServices/EECD/includes/js/jszip.min.js"></script>
	  	<script src="/MemberServices/EECD/includes/js/pdfmake.min.js"></script>	
	  	<script src="/MemberServices/EECD/includes/js/vfs_fonts.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
		<decorator:head />	

		
  <style>
 .ui-button {background-color:none;}
  
  </style>
	</head>

	<body>
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>
		
	<!-- TOP PANEL -->
		<div class="mainContainer">		
		

		
			<div class="container-fluid" style="max-height:300px;min-height:120px;height:auto;">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<img class="topLogoImg" src="/MemberServices/EECD/includes/img/header.png" title="Newfoundland and Labrador English School District">
				  </div>				  
				</div>		
			</div>




<!-- PAGE BODY -->



			
				<div class="container-fluid">
					
					<div class="alert alert-success" id="divsuccess" style="display:none;">
    						<span id="spansuccess"></span>
  					</div>
  					
  					<div class="alert alert-danger" id="diverror" style="display:none;">
    						<span id="spanerror"></span>
  					</div>
					
					<div id="printJob">	
								<decorator:body />	
					</div>

				</div>



<div class="alert alert-info no-print" style="text-align:center;font-size:11px;">
If you are experiencing technical difficulties with this system, email <a href="mailto:mssupport@nlesd.ca?subject=EECD System">mssupport@nlesd.ca</a>.
</div>

<!-- FOOTER AREA -->		
	
	<div class="mainFooter no-print"> 
	 
	
		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">		
		  EECD Working Groups/Committees System App 1.0 &middot; &copy; 2019 Newfoundland and Labrador English School District &middot; All Rights Reserved 
		 </div>
		  
		</div> 
	 	
		
	</div>	


</div>

<!-- ENABLE PRINT FORMATTING -->
		<script src="/MemberServices/EECD/includes/js/jQuery.print.js"></script> 
		
		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
		
		</script>
	
</body>

</html>

<!-- END DECORATOR -->

