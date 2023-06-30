<!-- DECORATOR FILE (C) 2020  -->	
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


<html>
  
  <head>
  	
	<!-- META TAGS  -->
		<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">			
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				
    	<meta name="generator" content="Eclipse Java EE IDE for Web Developers. Version: Oxygen.3 Release (4.7.3)">
	    <meta name="dcterms.created" content="March 26, 2020">    	     				
		<meta name="description" content="School Allocations Data" />	
		<meta name="keywords" content="NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		
		<title><decorator:title default="School Allocations Data" /></title>
	
	<!-- CSS STYLESHEET FILES -->	   
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css">
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">      
  	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.css">		
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
  		<link rel="stylesheet" href="/MemberServices/PSCounts/includes/css/style.css">			
		<link rel="shortcut icon" href="/MemberServices/PSCounts/includes/img/favicon.ico">	
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
		
	<!-- CDN JAVASCRIPT> -->	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>		
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>		
		<script src="https://kit.fontawesome.com/053757fa2e.js" crossorigin="anonymous"></script>		
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>			
	  	<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>		
		<script src="https://cdn.datatables.net/responsive/1.0.7/js/dataTables.responsive.min.js"></script>		
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>	
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.colVis.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/colreorder/1.5.2/js/dataTables.colReorder.min.js"></script>
		<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
		<script src="https://cdn.datatables.net/rowreorder/1.2.6/js/dataTables.rowReorder.min.js"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>				
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>				
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>	 	
	 	 <script src="https://cdn.datatables.net/plug-ins/1.11.3/sorting/natural.js"></script>
	 <!-- LOCAL JAVASCRIPT FILES -->		
		<script src="/MemberServices/PSCounts/includes/js/scripts.js"></script>	 	
	 	
<!-- GOOGLE ANALYTICS -->
		<!-- Google tag (gtag.js) -->
		<script async src="https://www.googletagmanager.com/gtag/js?id=G-S44S0R2BYK"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		  gtag('config', 'G-S44S0R2BYK');
		</script>


		<decorator:head />	

			

	</head>

	<body>
	<jsp:include page="/StaffRoom/includes/topmenu.jsp" />
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/PSCounts/includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>
		
	<!-- TOP PANEL -->
		<div class="mainContainer">	
		
		
		
			<div class="container-fluid">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<img class="topLogoImg" src="/MemberServices/PSCounts/includes/img/header.png" title="Newfoundland and Labrador English School District">
				  </div>				  
				</div>		
			</div>


<!-- PAGE BODY -->


			
				<div class="container-fluid">				
					
					
					<div id="printJob">			
					
								<decorator:body />	
					</div>

				</div>




<!-- FOOTER AREA -->		
	
	<div class="mainFooter no-print"> 
	 
	
		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">		
		 &copy; 2021 Newfoundland and Labrador English School District &middot; All Rights Reserved 
		 </div>
		  
		</div> 
	 	
		
	</div>	


</div>

<!-- ENABLE PRINT FORMATTING -->		
		<script src="/MemberServices/PSCounts/includes/js/jQuery.print.js"></script> 
		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
		
		</script>
	
</body>

</html>

<!-- END DECORATOR -->

