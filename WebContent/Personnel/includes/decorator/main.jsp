<!-- MyHRP DECORATOR FILE (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->
<!-- THIS CAOS BY GEOFF - Good Luck! -->

<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
        		 java.util.TreeMap,
        		 com.esdnl.personnel.jobs.bean.*,                 
                 com.esdnl.personnel.jobs.dao.*,
                 com.awsd.security.*, java.util.*"%>

<!-- LOAD JAVA TAG LIBRARIES -->
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<!-- PREVENT CACHE OF LOCAL JS AND CSS FROM AGING TOO LONG -->	
<c:set var="cacheBuster" value="<%=new java.util.Date()%>" />				 								
<fmt:formatDate value="${cacheBuster}" pattern="MMddyyyyHms" var="todayVer" />


<esd:SecurityCheck permissions="PERSONNEL-IT-VIEW-SCHOOL-EMPLOYEES,PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,ADMINISTRATOR,RTH-NEW-REQUEST,PERSONNEL-RTH-VIEW-APPROVALS,PERSONNEL-SUBMIT-REFERENCE,PERSONNEL-VIEW-SUBMITTED-REFERENCES,PERSONNEL-SEARCH-APPLICANTS-NON"/>

<%
User usr = (User) session.getAttribute("usr");
boolean isPrincipal = usr.checkRole("PRINCIPAL") || usr.checkRole("PRINCIPAL REPRESENTATIVE");
TreeMap<String,Integer> counts = RequestToHireManager.getRequestsToHireCount();
MyHrpSettingsBean rbean=MyHrpSettingsManager.getMyHrpSettings();
%>

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
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">  		
		<link rel="stylesheet" href="/MemberServices/Personnel/includes/css/msapp.css?ver=${todayVer}">
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
	   <script src="/MemberServices/Personnel/includes/js/personnel_ajax_v2.js?ver=${todayVer}"></script>
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.colVis.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>	
		<script src="https://kit.fontawesome.com/053757fa2e.js" crossorigin="anonymous"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>
		<decorator:head />

		<script type="text/javascript">
			$('document').ready(function(){
				$('#btn_search').click(function(){
					if( $('#txt_filter').val() == "") {
						$("#searchError").css("display","block").delay(5000).fadeOut();
						return false;

					} else {
					document.location.href = 'searchJob.html?term=' + $('#txt_filter').val() +'&type=' + $('input:radio[name=search_type]:checked').val();
					}
					return false;
				});

				$('#txt_filter').keypress(function(e){
					if(e.which == 13)
						$('#btn_search').click();
				});

			});
		</script>
  <style>
 .ui-button {background-color:none;}

  </style>
	</head>

	<body>
	<jsp:include page="/StaffRoom/includes/topmenu.jsp" />
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/Personnel/includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>

	<!-- TOP PANEL -->
		<div class="mainContainer">

			<div class="container-fluid no-print" id="noPrintThis" style="max-height:200px;min-height:100px;padding-top:10px;padding-bottom:10px;height:auto;">
				<div class="row">
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<div align="center" class="no-print"><img src="/employment/includes/img/myhrportallogo-small.png" style="max-height:100px;" border=0></div>
				  </div>
				</div>
			</div>
			
				<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
			<div class="container-fluid no-print" id="noPrintThis" style="padding-top:10px;">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center">

					<form class="form-inline">
					<div class="input-group">
		    			<span class="input-group-addon">SEARCH FOR:</span>
		    					<input id="txt_filter" type="text" class="form-control input-sm" placeholder="Enter Search Term(s)..." autocomplete="off">
		    			<div class="input-group-btn">
		      				<button class="btn btn-default btn-md btn-success" id="btn_search" type="submit">
		        				<i class="glyphicon glyphicon-search" style="height:16px;"></i>
		      				</button>
		    			</div>

		  			</div>
		  			<div class="form-group" style="font-size:10px;"> &nbsp;
					    	<label class="radio-inline"><input type="radio" id='search_type_1' name='search_type' value='1' checked>Competition #</label>
					   		<label class="radio-inline"><input type="radio" id='search_type_3' name='search_type' value='3'>Applicant</label>
					   		
					   		<esd:SecurityAccessRequired roles="PERSONNEL-SEARCH-SDS-ID">
					   		<label class="radio-inline"><input type="radio" id='search_type_4' name='search_type' value='4'>SDS Id</label>
					   		</esd:SecurityAccessRequired>
					</div>
					</form>
					<div id="searchError" class="alert alert-danger" style="display:none;">ERROR: No search terms entered. Please try again.</div>
					</div>
				</div>
			</div>
		</esd:SecurityAccessRequired>	
			
			
			
<!-- START NAVIGATION BAR navbar-fixed-top-->

<nav class="navbar navbar-inverse" data-spy="affix" data-offset-top="200" style="font-size:12px;transform:translateZ(0);max-width:1420px;">
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

		<li><a href="/MemberServices/navigate.jsp"  onclick="loadingData()" title="Back to StaffRoom"><span class="glyphicon glyphicon-step-backward"></span> SR</a></li>
		<li><a href="/MemberServices/Personnel/admin_index.jsp"  onclick="loadingData()" title="HR Home"><span class="glyphicon glyphicon-home"></span>&nbsp;</a></li>
		<li><a href='#' title='Print this page (pre-formatted)' title="Print this page" onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});"><span class="glyphicon glyphicon-print"></span>&nbsp;</a></li>
                       

<esd:SecurityAccessRequired permissions="PERSONNEL-IT-VIEW-SCHOOL-EMPLOYEES">
    <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">IT ACCESS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
								<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_pp_school_employees.jsp">View School Employees</a></li>
					          	</ul>
	</esd:SecurityAccessRequired>
	
	<!-- 
	<esd:SecurityAccessRequired roles="COVID19-REPORT-VIEWER">
								<li class="dropdown" id="menuNormal">
					          	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-cog"></span> Reports<span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
					          	 <esd:SecurityAccessRequired roles="COVID19-REPORT-VIEWER">
								<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_counts_report.jsp">View COVID19 Counts Report</a></li>
								</esd:SecurityAccessRequired>
								</ul>
	</esd:SecurityAccessRequired>
	-->
	<esd:SecurityAccessRequired permissions="PERSONNEL-SEARCH-APPLICANTS-NON">
									<li class="dropdown" id="menuNormal">
					          		<a class="dropdown-toggle" data-toggle="dropdown" href="#">REPORTS <span class="caret"></span></a>
					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-SEARCH-APPLICANTS-NON">
					          	 	<ul class="dropdown-menu multi-level">					          	
									<li class="dropdown-submenu">
					          	 			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Code of Ethics/Conduct</a>
					          	 			<ul class="dropdown-menu">
					          	 			<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMissingCoeReport.html">Missing COE Report</a></li>
											<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_ethics_latest_report.jsp">View Latest Report</a></li>
											<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewEthicsSummary.html">View Summary Report</a></li>
											<li><a onclick="loadingData()" href="/MemberServices/Personnel/search_applicants_non.jsp">Search Profile(s)</a></li>
					          				</ul>
					          			</li>								
					          		</ul>
					          		</esd:SecurityAccessRequired>
    							
	</esd:SecurityAccessRequired>

<!-- ADMINISTRATION MENU ------------------------------------------------------------------------------>

<%if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")){%>
  <!--  HIDE COVID
							<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19,PERSONNEL-ADMIN-VIEW-ETHICS-DEC,PERSONNEL-SEARCH-APPLICANTS-NON">
							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-cog"></span> Reports<span class="caret"></span></a>
					          	 <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19">
					          	 <ul class="dropdown-menu multi-level">
					          	 	<li class="dropdown-submenu">
					          	 	<a href="#" class="dropdown-toggle" data-toggle="dropdown">COVID19</a>
					          	 	<ul class="dropdown-menu">
									<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19">
										<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_counts_report.jsp">View Counts Report</a></li>
										<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_exemptions.jsp">View Exemptions Report</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_school_report.jsp">View By Location</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_latest_report.jsp">View Latest Report</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_covid19_ss.jsp">View Special Status Report</a></li>
					          		</esd:SecurityAccessRequired>
					          		<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-COVID19-EMAIL">
					          			<li class="divider"></li>
					          			<li><a onclick="loadingData()" href="admin_send_covid19_warning.jsp">Send COVID19 Reminder</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewCovid19Dashboard.html">View Dashboard Report</a></li>
					          		</esd:SecurityAccessRequired>
					          		</ul>
					          		</li>

					          		
					          		
					          	</ul>
					          	</esd:SecurityAccessRequired>
					          	
					          	
					        </li>
							</esd:SecurityAccessRequired>

-->

							<esd:SecurityAccessRequired roles="ADMINISTRATOR">
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">ADMIN <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
									
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin/viewSubjectGroups.html">Subject Groups</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin/addSubjectGroup.html">Add Subject Group</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPTRSettings.html">Post Transfer Round Settings</a></li>
					          	 <esd:SecurityAccessRequired roles="ADMINISTRATOR">	
					          	 	<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyHrpSettings.html">MyHrp Settings</a></li>
					          	 	</esd:SecurityAccessRequired>
					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
					          	 		<li><a onclick="loadingData()" href="/MemberServices/Personnel/getSoftDeletedApplicants.html">View Deleted Applicants</a></li>
					          	 	</esd:SecurityAccessRequired>
					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-RELOAD-TABLES">
					          			<li class="divider"></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/startSync.html"><span class="glyphicon glyphicon-refresh"></span> Start Sync</a></li>
					          		</esd:SecurityAccessRequired>
					          		
					          	</ul>
					        </li>
							</esd:SecurityAccessRequired>

					   <esd:SecurityAccessRequired permissions="PERSONNEL-EMP-OPPS-VIEW">
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">GUIDES <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-INTERVIEW-GUIDES-VIEW">
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/addInterviewGuide.html">Add Interview Guide</a></li>
	                                    <li><a onclick="loadingData()" href="/MemberServices/Personnel/listInterviewGuides.html?status=active">Active Guides</a></li>
	                                    <li><a onclick="loadingData()" href="/MemberServices/Personnel/listInterviewGuides.html?status=inactive">Inactive Guides</a></li>
					          		</esd:SecurityAccessRequired>

					          	</ul>
					        </li>

					     	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-VIEW">
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">ADS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
                                     <li class="dropdown-submenu">
					          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Educational</a>
						                            <ul class="dropdown-menu">
										          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
						                                           <li><a onclick="loadingData()" href="/MemberServices/Personnel/request_ad.jsp">Request Advertisement</a></li>
						                                </esd:SecurityAccessRequired>
					
					                                    <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
						                                           <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.SUBMITTED.getId()%>">Pending Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.SUBMITTED)%>)</a></li>
					                                    </esd:SecurityAccessRequired>
					
					           	                        <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST">
						                                           <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.APPROVED.getId()%>">Approved Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.APPROVED)%>)</a></li>
						                                </esd:SecurityAccessRequired>
					
						        	                    <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-APPROVE">
						                                           <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_list_ad_requests.jsp?status=<%=RequestStatus.PREDISPLAYED.getId()%>">Pre-Display Requests (<%=AdRequestManager.getAdRequestBeanCount(RequestStatus.PREDISPLAYED)%>)</a></li>
						                                </esd:SecurityAccessRequired>
	                                				</ul>
	                                	</li>
	                                				<li class="dropdown-submenu">
					          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Support/Management</a>
						                            <ul class="dropdown-menu">
                                  
                                   						<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
							                            	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addRequestToHire.html">Add New Request</a></li>
							                            </esd:SecurityAccessRequired>
							                            <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=0">Pending Requests (<%=counts.get("SUBMITTED") %>)</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=5">Approved Requests (<%=counts.get("APPROVED") %>)</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=7">Rejected Requests (<%=counts.get("REJECTED") %>)</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=6">Competition Posted (<%=counts.get("POSTED") %>)</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="RTH-VIEW-MY-REQUESTS">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyRequests.html">View My Requests</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-RTH-VIEW-APPROVALS">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyApprovals.html">View My Approvals</a></li>
	                                      				</esd:SecurityAccessRequired>	                                      				
	                                 </ul></li>     			

					          	</ul>
					        </li>
				       		</esd:SecurityAccessRequired>


							 <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">POSITIONS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">

					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-POSITION-PLANNING-VIEW">
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning View</a></li>
					          			<li class="divider"></li>
					          		</esd:SecurityAccessRequired>
									<esd:SecurityAccessRequired permissions="PERSONNEL-VIEW-SCHOOL-EMPLOYEES">
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_pp_school_employees.jsp">View School Employees</a></li>
					          			<li class="divider"></li>
					          		</esd:SecurityAccessRequired>
					          	        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-JOBS">
					          	 	 	<li class="dropdown-submenu">
					          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Educational</a>
						                            <ul class="dropdown-menu">
						                            <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-STAFFING-STATS">
					          	  						<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin/stats/admin_staffing_statistics.jsp">View Staffing Statistics</a></li>
					          	  					</esd:SecurityAccessRequired>
							          	  			<esd:SecurityAccessRequired roles="ADMINISTRATOR">
				                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addSEOStaffingAssignment.html">SEO Staffing Assignments</a></li>
				                                    </esd:SecurityAccessRequired>
				                                    
					                                    <li class="divider"></li>
					                                    <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=All&zoneid=0">View All Posts</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Open&zoneid=0">Open</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Unadvertised&zoneid=0">Unadvertised</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Closed&zoneid=0">Closed</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=NoShortlist&zoneid=0">Closed No Shortlist</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=RECAPPROVAL&zoneid=0">Pending Recommendation Approval</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=RECACCEPT&zoneid=0">Pending Recommendation Acceptance</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Cancelled&zoneid=0">Cancelled</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Awarded&zoneid=0">Awarded</a></li>
									                 </ul>
								       	</li>
								       	</esd:SecurityAccessRequired>


								       	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUPPORT-JOBS">
								       	<li class="dropdown-submenu">
						          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Support/Management</a>
							                            <ul class="dropdown-menu">
							                           
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=All&zoneid=0">View All Posts</a></li>
					                                  		<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Open&zoneid=0">Open</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Unadvertised&zoneid=0">Unadvertised</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Closed&zoneid=0">Closed</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=NoShortlist&zoneid=0">Closed No Shortlist</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=RECAPPROVAL&zoneid=0">Pending Recommendation Approval</a></li>
					                                   		<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=RECACCEPT&zoneid=0">Pending Recommendation Acceptance</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Cancelled&zoneid=0">Cancelled</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Awarded&zoneid=0">Awarded</a></li>
							                            </ul>
							               	</li>
								       	</esd:SecurityAccessRequired>
								      	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTOTHERJOB">
								      		<li class="dropdown-submenu">
						          	 	  		<a href="#" class="dropdown-toggle" data-toggle="dropdown">Other Job Posts</a>
						          	 	  		<ul class="dropdown-menu">
								      				<!-- <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_post_job_other.jsp">Post Job</a></li>-->
													<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other_o.jsp?zoneid=0">View Posts</a></li>
												</ul>
											</li>
								       </esd:SecurityAccessRequired>
						               	  <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUBLISTS">
					          	 		<li class="dropdown-submenu">
					          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Substitute Lists</a>
						                            <ul class="dropdown-menu">
						                            <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-CREATE-SUBLIST">
						                            	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_create_sub_list.jsp">Create List</a></li>
						                            </esd:SecurityAccessRequired>
						                            <li class="divider"></li>
						                             <%for(SubstituteListConstant sc : SubstituteListConstant.ALL){%>


		                                        		<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_sub_lists.jsp?type=<%=sc.getValue()%>"><%=sc.getDescription()%>s</a></li>

						                             <%}%>
													<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewSubListBySchool.html">View Shortlist By School</a></li>
						                            

						                            </ul>
						               </li>
					          	 		</esd:SecurityAccessRequired>
					          	</ul>
					        </li>



							 <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-JOBS">
							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">APPLICANT LISTS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu scrollable-menu">


						          	 				  <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=A">A</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=B">B</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=C">C</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=D">D</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=E">E</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=F">F</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=G">G</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=H">H</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=I">I</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=J">J</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=K">K</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=L">L</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=M">M</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=N">N</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=O">O</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=P">P</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=Q">Q</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=R">R</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=S">S</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=T">T</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=U">U</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=V">V</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=W">W</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=X">X</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=Y">Y</a></li>
				                                      <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_applicant_list.jsp?surname_part=Z">Z</a></li>


					          	</ul>
					        </li>
					        </esd:SecurityAccessRequired>

					        <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW">
							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">REFERENCES <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">

					          	  				<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDAdminReference.html">Add Administrator Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDGuideReference.html">Add Guidance Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDTeacherReference.html">Add Teacher Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDExternalReference.html">Add External Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDSupportReference.html">Add Support Staff Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDManageReference.html">Add Management Reference</a></li>
		                                      	<%if(usr.checkRole("SENIOR EDUCATION OFFICIER") ){%>
													<li class="divider"></li>
						        				<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalNLESDCompletedReferences.html">Completed Reference(s)</a></li>
					    						<%}%>
					          	</ul>
					        </li>

							</esd:SecurityAccessRequired>

</esd:SecurityAccessRequired>


 <!-- PRINCIPAL MENU ------------------------------------------------------------------------------>

 <%} else if(usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW")){ %>


 				<%if(isPrincipal){%>


 				<%if(usr.checkRole("PRINCIPAL")){%>
 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">POSITIONS <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
   
     
   							<% if(!rbean.isPpBlockSchools()) { %>  
								<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning</a></li>
    						<% } else { %>
								<li><a href="#">Position Planning Disabled</a></li>
      						<%} %>

					         	</ul>
					        </li>
				<%}%>
 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">REFERENCES <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDAdminReference.html">Add Administrator Reference</a></li>
                          		<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDGuideReference.html">Add Guidance Reference</a></li>
                          		<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDTeacherReference.html">Add Teacher Reference</a></li>
						<%if(usr.checkRole("PRINCIPAL")){%>
								<li class="divider"></li>
						        <li><a onclick="loadingData()" href="/MemberServices/Personnel/principalNLESDCompletedReferences.html">Completed Reference(s)</a></li>
					    <%}%>
					         	</ul>
					        </li>


 				<%}%>

 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">SHORT LISTS <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					    <%if(isPrincipal){%>
								<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalJobShortlists.html">Job Short List(s)</a></li>
						<%}%>
						<%if(isPrincipal || usr.checkRole("TEACHER SUB LIST ACCESS")){%>
                            	<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalSubListShortlists.html">Substitutes</a></li>
                        <%}%>


					         	</ul>
					        </li>



 <!-- VP MENU ------------------------------------------------------------------------------>

 <%} else if(usr.checkPermission("PERSONNEL-VICEPRINCIPAL-VIEW")){ %>

							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">POSITIONS <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
							<% if(!rbean.isPpBlockSchools()) { %>  
								<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning</a></li>
    						<% } else { %>
								<li><a href="#">Position Planning Disabled</a></li>
      						<%} %>
					         	</ul>
					        </li>
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">REFERENCES <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          		<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDAdminReference.html">Add Administrator Reference</a></li>
                         			<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDGuideReference.html">Add Guidance Reference</a></li>
                          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDTeacherReference.html">Add Teacher Reference</a></li>
                          			<li class="divider"></li>
									<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalNLESDCompletedReferences.html">Completed Reference(s)</a></li>
					         	</ul>
					        </li>

					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">SHORT LISTS <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

										<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalJobShortlists.html">Job Short Lists</a></li>
                          				<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalSubListShortlists.html">Substitutes</a></li>



					         	</ul>
					        </li>

 <%} else if(usr.checkPermission("RTH-NEW-REQUEST")){%>
 					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">ADVERTISEMENTS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
                                     <li class="dropdown-submenu">
					          	 	  		<a href="#" class="dropdown-toggle" data-toggle="dropdown">Support/Management</a>
						                           <ul class="dropdown-menu">
                                  						<li><a onclick="loadingData()" href="/MemberServices/Personnel/addRequestToHire.html">Add New Request</a></li>
                                  						<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyRequests.html">View My Requests</a></li>
                                  						<%if(usr.checkRole("RTH-SUBMIT-REQUEST")){%>
							         						<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyShortlists.html">View My Job Posts</a></li>
							         					<%} %>
							         				<%if(usr.checkPermission("PERSONNEL-RTH-VIEW-APPROVALS")){%>
							         					<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyApprovals.html">View My Approvals</a></li>
							         				<%} %>
							         				<%if(usr.checkPermission("PERSONNEL-RTH-BC-APPROVED")){%>
							         					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=0">Pending Requests</a></li>
							         					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=5">Approved Requests</a></li>
							         					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=6">Competition Posted</a></li>
							         				<%} %>
							         				
							         				
							         				</ul>
							         </li>     			

					          	</ul>
					        </li>
 
 <%} else if(usr.checkPermission("PERSONNEL-RTH-VIEW-APPROVALS")){%>
 						<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">ADVERTISEMENTS <span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">
                                     <li class="dropdown-submenu">
					          	 	  		<a href="#" class="dropdown-toggle" data-toggle="dropdown">Support/Management</a>
						                           <ul class="dropdown-menu">
                                  						<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewMyApprovals.html">View My Approvals</a></li>
							         				</ul>
							         </li>     			

					          	</ul>
					        </li>
<%} else if(usr.checkPermission("PERSONNEL-VIEW-SUBMITTED-REFERENCES")){%>
						<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">REFERENCES <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          		<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalNLESDCompletedReferences.html">Completed Reference(s)</a></li>
					    		</ul>
					        </li>

					        
 <%} %>


<!-- HELP MENU -->
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#">HELP <span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
										<li><a href="https://sites.google.com/nlesd.ca/myhrphelp/home" target="_blank">MyHRP Help Guide</a></li>
										<li><a href="/contact/districtstaffdirectory.jsp?search=Human Resources" target="_blank">HR Contacts</a></li>
										<li><a href="https://forms.gle/zudb87zxbJW9QTVg8" target="_blank">Technical Support</a></li>
										<li><a href="/MemberServices/" onclick="loadingData()">Exit to StaffRoom</a></li>

					         	</ul>
					        </li>

   			</ul>

 		</div>
   </div>
</nav>

<!-- PAGE BODY -->




				<div class="container-fluid" style="font-size:12px;">

<!-- MESSAGE AREA-->
						<div class="msgok alert alert-success" align="center" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
						<div class="msgerr alert alert-danger" align="center" style="display:none;"><b>ERROR:</b> ${errmsg}</div>	

					<div class="alert alert-success" id="divsuccess" style="display:none;">
    						<span id="spansuccess"></span>
  					</div>

  					<div class="alert alert-danger" id="diverror" style="display:none;">
    						<span id="spanerror"></span>
  					</div>

					<div id="printJob">                                  
								<decorator:body />
					</div>

			




<hr>
	Any questions or concerns relating to a profile should be directed to the proper contact(s) found under the Help menu above or your regional HR staff at the offices below:<br/>
	<br/>
														<div style="width:100%;text-align:center;">
														<div class="containerPanel region4">
																<div class="containerPanelHeader region4solid">LABRADOR REGION</div>
																<div class="containerPanelBody">
																<b>Labrador Regional Office</b><br/>
																Human Resources Division<br/>
													 			<a href="mailto:hrlabrador@nlesd.ca?subject=Labrador Jobs" title="hrlabrador@nlesd.ca">hrlabrador@nlesd.ca</a><br/>
													 			Fax: (709) 896-5629 
																</div>
														</div>
														<div class="containerPanel region3">
																<div class="containerPanelHeader region3solid">WESTERN REGION</div>
																<div class="containerPanelBody">
																<b>Western Regional Office</b><br/>
																Human Resources Division<br/>
																<a href="mailto:hrwest@nlesd.ca?subject=Western Jobs" title="hrwest@wnlesd.ca">hrwest@nlesd.ca</a><br/>
																Fax: (709) 637-6674
																</div>
														</div>
														<div class="containerPanel region2">
																<div class="containerPanelHeader region2solid">CENTRAL REGION</div>
																<div class="containerPanelBody">
																<b>Central Regional Office</b><br/>
																Human Resources Division<br/>
													 			<a href="mailto:hrcentral@nlesd.ca?subject=Central Jobs" title="hrcentral@nlesd.ca">hrcentral@nlesd.ca</a><br/>
													 			Fax: (709) 651-3044
																</div>
														</div>
														<div class="containerPanel region1">
																<div class="containerPanelHeader region1solid">AVALON REGION</div>
																<div class="containerPanelBody">
																<b>Avalon Regional Office</b><br/>
																Human Resources Division<br/>
																<a href="mailto:hravalon@nlesd.ca?subject=Avalon Jobs" title="hravalon@nlesd.ca">hravalon@nlesd.ca</a><br/>	
																 Fax: (709) 758-1052
																</div>
														</div>
														</div>
														<div style="clear:both;"></div>
														<br/><br/>
<div class="alert alert-info no-print" id="noPrintThis" style="text-align:center;font-size:11px;">
If you are experiencing difficulties with this system, check out the Help Guide link below or you may also submit a Support Request:<br/><br/>

<a href="https://sites.google.com/nlesd.ca/myhrphelp/home" target="_blank" class="btn btn-sm btn-primary">MyHRP Help Guide</a>
<a href="https://forms.gle/zudb87zxbJW9QTVg8" target="_blank" class="btn btn-sm btn-danger">Support Request Form</a>

</div>
<div align="center" class='no-print'><img src="includes/img/nlesd-colorlogo.png" border=0 style="width:100%;max-width:250px;"/></div>
<!-- FOOTER AREA -->
<br/><br/>
	<div class="mainFooter no-print">


		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		 MyHR Career Application System Portal 1.8 &copy; 2019-2023 Newfoundland and Labrador English School District &middot; All Rights Reserved
		 </div>

		</div>


	</div>


</div>
	</div>
<!-- ENABLE PRINT FORMATTING -->
		<script src="/MemberServices/Personnel/includes/js/jQuery.print.js"></script>
		<script src="/MemberServices/Personnel/includes/js/msapp.js?ver=${todayVer}"></script>
		<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}

		</script>

   


</body>

</html>

<!-- END DECORATOR -->
