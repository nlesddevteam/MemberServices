<!-- NLESD.CA DECORATOR FILE (C) 2022  -->	
<!-- AUTHOR: Geoff Taylor geofftaylor@nlesd.ca -->
<!-- HTML 5 BOOTSTRAP 4 JQUERY 3.5.1 JAVA 8.0 -->
<!-- For use of NLESD.CA ONLY -->

<%@ page language="java" contentType="text/html" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!-- PREVENT CACHE OF LOCAL JS AND CSS FROM AGING TOO LONG -->		
		<c:set var="cacheBuster" value="<%=new java.util.Date()%>" />				 								
		<fmt:formatDate value="${cacheBuster}" pattern="MMddyyyyHms" var="todayVer" />

		
<html>

  <head>  	

<!-- META TAGS  -->			
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				
    	<meta name="generator" content="Eclipse IDE for Enterprise Java and Web Developers Version: 2022-06 (4.24.0)">
	    <meta name="dcterms.created" content="Mon, 1 Sept 2022 16:59:07 GMT">    	     				
		<meta name="description" content="The Newfoundland and Labrador English School District represents all English speaking students and schools in Newfoundland and Labrador. The District includes approximately 65,300 students, 252 schools and six alternate sites, and over 8,000 employees." />	
		<meta name="keywords" content="NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		<meta name="google-site-verification" content="4Miuw9m-R9EmQ7GZUE1KP0gpOp91TlWr2KU9FRPqlO4" />
		<meta name="google-translate-customization" content="fc4e6bf392424f3-26adbaed3174ebd4-g7130b514bfc120b1-c">
		
		<title><decorator:title default="NLESD StaffRoom (MemberServices)" /></title>
	
<!-- CSS STYLESHEET FILES CDN -->
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.0/animate.min.css"/>				
		<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.css"/>
 		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto"/>		
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.css">		
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/css/tempusdominus-bootstrap-4.css" />
		
 <!--  CSS LOCAL FILES - ANY MODIFICATIONS DO HERE-->  		
  		<link rel="stylesheet" href="/MemberServices/StaffRoom/includes/css/staffroom.css?ver=${todayVer}"/>				
		<link rel="icon" type="image/x-icon" href="/MemberServices/StaffRoom/includes/img/favicon.ico"> 
		 
<!-- JAVASCRIPT FILES CDN-->			
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
		<script src="https://cdn.datatables.net/v/dt/jszip-2.5.0/dt-1.12.1/af-2.4.0/b-2.2.3/b-colvis-2.2.3/b-html5-2.2.3/b-print-2.2.3/cr-1.5.6/date-1.1.2/fc-4.1.0/fh-3.2.4/kt-2.7.0/r-2.3.0/rg-1.2.0/rr-1.2.8/sc-2.0.7/sb-1.3.4/sp-2.0.2/sl-1.4.0/sr-1.1.1/datatables.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.3/moment.min.js"></script>
			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.39.0/js/tempusdominus-bootstrap-4.js"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/jQuery.print@1.5.1/jQuery.print.min.js"></script> 
		<script src="https://kit.fontawesome.com/053757fa2e.js" crossorigin="anonymous"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js" crossorigin="anonymous"></script>
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-confirmation/1.0.7/bootstrap-confirmation.min.js" crossorigin="anonymous"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js" crossorigin="anonymous"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.21/api/order.neutral().js"></script>		
		<script src="https://cdn.jsdelivr.net/npm/chart.js@2/dist/Chart.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
		<script src="https://cdn.jsdelivr.net/gh/bbbootstrap/libraries@main/choices.min.js"></script>
		
<!-- JAVASCRIPT FILES LOCAL- ANY MODIFICATIONS DO HERE-->		
 		<script src="/MemberServices/StaffRoom/includes/js/jssor.slider-28.1.0.min.js"></script>
		<script src="/MemberServices/StaffRoom/includes/js/staffroom.js?ver=${todayVer}"></script>	
		<script src="/MemberServices/StaffRoom/includes/ckeditor/ckeditor.js"></script>		 	
    
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
<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:99999;">
	<div id="spinner" style="text-align:center;padding:5px;">
	<img src="/MemberServices/StaffRoom/includes/img/nllogo-load.png" border="0" style="padding:5px;"/><br/>
	<div class="spinner-grow text-primary"></div>
		<div class="spinner-grow text-success"></div>
		<div class="spinner-grow text-info"></div>
		<div class="spinner-grow text-warning"></div>
		<div class="spinner-grow text-danger"></div>
		<br/>Loading. Please wait!

	</div>
	</div>
	
<!-- NAVIGATION MENU -->
<div class="headerFull">
				   			<img src="/MemberServices/StaffRoom/includes/img/header.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerLarge">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-large.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerMedium">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-medium.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
				   	<div class="headerSmall">
				   			<img src="/MemberServices/StaffRoom/includes/img/header-small.png" style="width:100%;min-width:300px;border-top:5px solid red;" title="Newfoundland and Labrador English School District">
				   	</div>
<!-- PAGE BODY -->	
			<div class="mainContainer">
				<div class="container-fluid">	
				<c:if test="${ msgOK ne null }">  
                  				<div class="alert alert-success msgOKd" style="display:none;">${ msgOK } </div>  
                  				<script>$(".msgOKd").css("display","block").delay(3000).fadeOut();</script> 
                  			</c:if>                  			
							<c:if test="${ msgERR ne null }">  
                  				<div class="alert alert-danger msgERRd" style="display:none;">${ msgERR } </div>   
                  				<script>$(".msgERRd").css("display","block").delay(3000).fadeOut();</script> 
                  			</c:if>
                  			<c:if test="${ msg ne null }">  
                  				<div class="alert alert-info msgd" style="display:none;">${ msg } </div>   
                  				<script>$(".msgd").css("display","block").delay(3000).fadeOut();</script> 
                  			</c:if>  
				
								
						<div id="printJob">						
						<decorator:body />						
						</div>		
						
						
										
														
				</div>
			</div>				


<br/>&nbsp;<br/>

<!-- FOOTER FLOATING TAGLINE -->	
	<div class="mainFooter">	
	<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">   	 		
		  <div class="copyright">&copy; 2023 NLESD &middot; All Rights Reserved &middot; <a onclick="loadingData();" href="/privacypolicy.jsp" style="color:white;">Privacy Policy</a> &middot; 
		  <a onclick="loadingData();" href="/termsofuse.jsp" style="color:white;">Terms</a></div>		
		  </div> 
	</div> 	
	</div>

<!-- WHEN PAGE SCROLLS TO TOP, CHANGE MENU HEADER-->	
		<script>
				var distance = 120;
				$window = $(window);
				var x = window.innerWidth;
				$window.scroll(function() {
				//If distance is at top and width is greator than 768 display the Brand logo, and indent the search!!
				if (($window.scrollTop() >= distance) && (x >= 768)) {					
					 $(".navbar-toggler").css("margin-right","90px");
					 $(".navbar-brand").css("display","block")	;
				} else if (($window.scrollTop() < distance) && (x >= 768)) {					
					 $(".navbar-brand").css("display","none");		
					 $(".searchBoxNavBar").css("padding-right","0px");
				} else if (x<768){
					 $(".navbar-brand").css("display","block");					
					 $(".navbar-toggler").css("margin-right","90px");
				} else {
					$(".navbar-brand").css("display","none");					
				}				
				if (($window.scrollTop() >= distance) && (x <= 1600)) {
					 $(".searchBoxNavBar").css("padding-right","80px");
				}				
				});
		</script>
			

<!-- ENABLE LOADING SPINNER -->
<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
</script>

</body>
</html>

<!-- END DECORATOR -->

