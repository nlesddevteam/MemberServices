<!-- PDReg DECORATOR FILE (C) 2019  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.4.1 -->

<%@ page  language="java" 
          session="true" 
          import="java.util.*, 
                  java.text.*,
                  java.io.*,
                  com.awsd.pdreg.*,
                  com.awsd.security.*,
                  com.awsd.common.Utils,
                  org.apache.commons.lang.*"
           errorPage="error.jsp" 
          isThreadSafe="false"%>


<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="awsd" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="CALENDAR-VIEW" />



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
		<meta name="description" content="Professional Development Calendar" />	
		<meta name="keywords" content="MyPD,NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		
		<title><decorator:title default="MyPD Professional Development Calendar" /></title>
	
	<!-- CSS STYLESHEET FILES -->	
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.dataTables.min.css">
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">       
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">		
  		<link rel="shortcut icon" href="/MemberServices/PDReg/includes/img/favicon.ico">	
		<link href="/MemberServices/PDReg/includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link href="/MemberServices/PDReg/includes/css/hover_drop_2.css" rel="stylesheet" media="all" type="text/css" />				
		<link rel="stylesheet" href="/MemberServices/PDReg/includes/css/pdreg.css">	


	
	<!-- JAVASCRIPT FILES -->
		
		<script src="/MemberServices/PDReg/includes/ckeditor/ckeditor.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.js"></script>		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>		
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>	
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  	
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>		
  		<script src="/MemberServices/PDReg/includes/js/bootstrap-confirmation.min.js"></script> 			
		<script src="/MemberServices/PDReg/includes/js/jquery-ui.js"></script>
		<script src="/MemberServices/PDReg/includes/js/jquery.cookie.js"></script>		
		<script src="/MemberServices/PDReg/includes/js/bootstrap-multiselect.js"></script>
		<script src="/MemberServices/PDReg/includes/js/iefix.js"></script>
	    <script src="/MemberServices/PDReg/includes/js/jquery.validate.js"></script>	    
	    <script src="/MemberServices/PDReg/includes/js/pdreg.js"></script> 
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>				
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
		
		
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

			
		
		
		
  <style>
 .ui-button {background-color:none;}
 	
 	.cke_skin_v2 input.cke_dialog_ui_input_text, .cke_skin_v2 input.cke_dialog_ui_input_password {
 	position: relative;
    z-index: 9999; 
    }
    .goog-te-banner-frame.skiptranslate {display: none !important;}
    body { top: 0px !important; }
  </style>
	</head>

	<body>
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/PDReg/includes/img/loading.gif" title="Loading Events...Please Wait." class="spinnerSize"  border=0><br/>Loading events, please wait...</div></div>
		
	<div id="printJob">			
					
								<decorator:body />	
	</div>
    
    
    
    
    
<!-- FOOTER AREA -->		
	
	<div class="mainFooter no-print"> 	
		
		 <div class="gTranslate">
		  <div id="google_translate_element" ></div>
    <script type="text/javascript">
function googleTranslateElementInit() {
  new google.translate.TranslateElement({pageLanguage: 'en',includedLanguages: 'en,fr,es', layout: google.translate.TranslateElement.InlineLayout.HORIZONTAL, autoDisplay: false}, 'google_translate_element');
}
</script>
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
		  </div>
		
		  <div class="copyright">		
		 PL Calendar Application 3.0 &copy; 2022 NLESD &middot; All Rights Reserved.
		 </div>
		
	</div>	




<!-- ENABLE PRINT FORMATTING -->		
		<script src="/MemberServices/PDReg/includes/js/jQuery.print.js"></script> 
		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
		
		</script>
	
</body>

</html>

<!-- END DECORATOR -->

