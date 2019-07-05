<!-- MyHRP DECORATOR EXTERNAL FILE (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->
<!-- THIS CAOS BY GEOFF - Good Luck! -->

<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.awsd.security.*"%>


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
		<meta name="description" content="MyHRP Applicant Profiling System" />
		<meta name="keywords" content="MYHRP,NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">

		<title><decorator:title default="MyHRP Applicant Profiling System" /></title>

	<!-- CSS STYLESHEET FILES -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="/MemberServices/Personnel/includes/css/msapp.css">
		<link rel="shortcut icon" href="/MemberServices/Personnel/includes/img/favicon.ico">
		<link href="/MemberServices/Personnel/includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link href="/MemberServices/Personnel/includes/css/hover_drop_2.css" rel="stylesheet" media="all" type="text/css" />

	<!-- JAVASCRIPT FILES -->
		<script src="/MemberServices/Personnel/includes/ckeditor/ckeditor.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  		<script src="/MemberServices/Personnel/includes/js/bootstrap-confirmation.min.js"></script>
		<script src="/MemberServices/Personnel/includes/js/jquery-ui.js"></script>
		<script src="/MemberServices/Personnel/includes/js/jquery.cookie.js"></script>
		<script src="/MemberServices/Personnel/includes/js/bootstrap-multiselect.js"></script>
		<script src="/MemberServices/Personnel/includes/js/iefix.js"></script>
	    <script src="/MemberServices/Personnel/includes/js/jquery.validate.js"></script>
	    <script src="/MemberServices/Personnel/includes/js/msapp.js"></script>
	   <script src="/MemberServices/Personnel/includes/js/personnel_ajax_v1.js"></script>
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
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



			<div class="container-fluid no-print" id="noPrintThis" style="max-height:230px;min-height:120px;height:auto;">
				<div class="row">
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<img class="topLogoImg" src="/MemberServices/Personnel/includes/img/myhrplogowide.png" title="Newfoundland and Labrador English School District">
				  </div>
				</div>
			</div>

<!-- START NAVIGATION BAR navbar-fixed-top-->

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="270" style="transform:translateZ(0);max-width:1250px;">
  <div class="container-fluid">
		    <div class="navbar-header">
				      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				      </button>
				       <a href="#" class="navbar-left navbar-brand"><img src="/MemberServices/Personnel/includes/img/myhrpsm.png"></a>
		    </div>

  		<div class="collapse navbar-collapse" id="myNavbar" style="padding-left:0px;">

			<ul class="nav navbar-nav">

<!-- HOME MENU-->
					    	<li class="dropdown" id="menuNormal">
					    	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-file"></span> File <span class="caret"></span></a>
					    	 <ul class="dropdown-menu multi-level">

					          <li><a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span> Print Page</a></li>
                              <li class="divider"></li>
                               <li><a onclick="loadingData()" href="/"><span class="glyphicon glyphicon-step-backward"></span> Back to NLESD</a></li>
					          	</ul>
					        </li>




<!-- HELP MENU -->
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-question-sign"></span> Help<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

										<li><a href="/contact/stafffinderresults.jsp?pos=Human+Resources&region=" target="_blank">HR Contacts</a></li>
										<li><a href="/" onclick="loadingData()">Exit to NLESD</a></li>

					         	</ul>
					        </li>

   			</ul>

 		</div>
   </div>
</nav>

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



<div class="alert alert-info no-print" id="noPrintThis" style="text-align:center;font-size:11px;">
Any questions or concerns relating to a profile should be directed to the
proper contact(s) found under the <a href="viewContactInformation.html">Help</a> menu above or your regional HR staff.<br/>
If you are experiencing technical difficulties with this system, email <a href="mailto:mssupport@nlesd.ca?subject=MyHRP System">mssupport@nlesd.ca</a>.
</div>

<!-- FOOTER AREA -->

	<div class="mainFooter no-print">


		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		  MyHRP Applicant Profiling System App 1.3 &middot; &copy; 2019 Newfoundland and Labrador English School District &middot; All Rights Reserved
		 </div>

		</div>


	</div>


</div>

<!-- ENABLE PRINT FORMATTING -->
		<script src="/MemberServices/Personnel/includes/js/jQuery.print.js"></script>

		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}

		</script>

</body>

</html>

<!-- END DECORATOR -->
