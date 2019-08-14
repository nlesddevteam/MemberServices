<!-- MyHRP DECORATOR FILE (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->
<!-- THIS CAOS BY GEOFF - Good Luck! -->

<%@ page language="java"
         import="com.esdnl.personnel.jobs.constants.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.awsd.security.*"%>

<!-- LOAD JAVA TAG LIBRARIES -->
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW,ADMINISTRATOR"/>

<%
User usr = (User) session.getAttribute("usr");
boolean isPrincipal = usr.checkRole("PRINCIPAL") || usr.checkRole("PRINCIPAL REPRESENTATIVE");
%>

<html lang="en">

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
	   <script src="/MemberServices/Personnel/includes/js/personnel_ajax_v2.js"></script>
		<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>
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
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/Personnel/includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>

	<!-- TOP PANEL -->
		<div class="mainContainer">

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
					</div>
					</form>
					<div id="searchError" class="alert alert-danger" style="display:none;">ERROR: No search terms entered. Please try again.</div>
					</div>
				</div>
			</div>
		</esd:SecurityAccessRequired>

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

					          <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_index.jsp">Home</a></li>
					          <li><a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Human Resources Profile System</b></div><br/><br/>'});">Print Page</a></li>
                              <li class="divider"></li>
                               <li><a onclick="loadingData()" href="/MemberServices/navigate.jsp">Back to MS</a></li>
					          	</ul>
					        </li>



<!-- ADMINISTRATION MENU ------------------------------------------------------------------------------>

<%if(usr.checkPermission("PERSONNEL-ADMIN-VIEW")){%>


					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-cog"></span> Admin<span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">


					          	 	<esd:SecurityAccessRequired roles="ADMINISTRATOR">
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin/viewSubjectGroups.html">Subject Groups</a></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin/addSubjectGroup.html">Add Subject Group</a></li>
					          	 	</esd:SecurityAccessRequired>
					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-SUBSTITUTES-RELOAD-TABLES">
					          			<li class="divider"></li>
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/startSync.html"><span class="glyphicon glyphicon-refresh"></span> Start Sync</a></li>
					          		</esd:SecurityAccessRequired>
					          	</ul>
					        </li>


					   <esd:SecurityAccessRequired permissions="PERSONNEL-EMP-OPPS-VIEW">
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-folder-open"></span> Guides<span class="caret"></span></a>
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
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-modal-window"></span> Advertisements<span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">

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
				       		</esd:SecurityAccessRequired>


							 <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-list-alt"></span> Positions<span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">

					          	 	<esd:SecurityAccessRequired permissions="PERSONNEL-POSITION-PLANNING-VIEW">
					          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning View</a></li>
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
				                                    <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTJOB">
				                                      	<!-- <li><a href="/MemberServices/Personnel/admin_post_job.jsp">Post Job</a></li>-->
				                                    </esd:SecurityAccessRequired>
					                                    <li class="divider"></li>
					                                    <li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=All&zoneid=0">View All Posts</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Open&zoneid=0">Open</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Unadvertised&zoneid=0">Unadvertised</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Closed&zoneid=0">Closed</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=NoShortlist&zoneid=0">Closed No Shortlist</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Cancelled&zoneid=0">Cancelled</a></li>
					                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts.jsp?status=Awarded&zoneid=0">Awarded</a></li>
									                 </ul>
								       	</li>
								       	</esd:SecurityAccessRequired>


								       	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-SUPPORT-JOBS">
								       	<li class="dropdown-submenu">
						          	 	  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Support Staff/Management</a>
							                            <ul class="dropdown-menu">
							                            <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
							                            	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addRequestToHire.html">Add New Request</a></li>
							                            </esd:SecurityAccessRequired>
							                            <esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=0">Pending Requests</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=5">Approved Requests</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=7">Rejected requests</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<esd:SecurityAccessRequired permissions="PERSONNEL-ADREQUEST-REQUEST">
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/adminViewRequestsToHire.html?status=6">Competition Posted</a></li>
	                                      				</esd:SecurityAccessRequired>
	                                      				<li class="divider"></li>
	                                      					<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=All&zoneid=0">View All Posts</a></li>
					                                  		<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Open&zoneid=0">Open</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Unadvertised&zoneid=0">Unadvertised</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Closed&zoneid=0">Closed</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=NoShortlist&zoneid=0">Closed No Shortlist</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Cancelled&zoneid=0">Cancelled</a></li>
						                                   	<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_view_job_posts_other.jsp?status=Awarded&zoneid=0">Awarded</a></li>
							                            </ul>
							               	</li>
								       	</esd:SecurityAccessRequired>
								      	<esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-POSTOTHERJOB">
								      		<li class="dropdown-submenu">
						          	 	  		<a href="#" class="dropdown-toggle" data-toggle="dropdown">Other Job Posts</a>
						          	 	  		<ul class="dropdown-menu">
								      				<li><a onclick="loadingData()" href="/MemberServices/Personnel/admin_post_job_other.jsp">Post Job</a></li>
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


						                            </ul>
						               </li>
					          	 		</esd:SecurityAccessRequired>
					          	</ul>
					        </li>



							 <esd:SecurityAccessRequired permissions="PERSONNEL-ADMIN-VIEW-JOBS">
							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> Applicant List<span class="caret"></span></a>
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
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> References<span class="caret"></span></a>
					          	 <ul class="dropdown-menu multi-level">

					          	  				<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDAdminReference.html">Add Administrator Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDGuideReference.html">Add Guidance Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDTeacherReference.html">Add Teacher Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDExternalReference.html">Add External Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDSupportReference.html">Add Support Staff Reference</a></li>
		                                      	<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDManageReference.html">Add Management Reference</a></li>
					          	</ul>
					        </li>

							</esd:SecurityAccessRequired>

</esd:SecurityAccessRequired>


 <!-- PRINCIPAL MENU ------------------------------------------------------------------------------>

 <%} else if(usr.checkPermission("PERSONNEL-PRINCIPAL-VIEW")){ %>


 				<%if(isPrincipal){%>


 				<%if(usr.checkRole("PRINCIPAL")){%>
 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-list-alt"></span> Positions<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

								<li><a onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning</a></li>

					         	</ul>
					        </li>
				<%}%>
 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> References<span class="caret"></span></a>
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
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span> Short Lists<span class="caret"></span></a>
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
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-list-alt"></span> Positions<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

										<li><a onclick="loadingData()" onclick="loadingData()" href="/MemberServices/Personnel/viewPositionPlanning.html">Position Planning</a></li>

					         	</ul>
					        </li>
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> References<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          		<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDAdminReference.html">Add Administrator Reference</a></li>
                         			<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDGuideReference.html">Add Guidance Reference</a></li>
                          			<li><a onclick="loadingData()" href="/MemberServices/Personnel/addNLESDTeacherReference.html">Add Teacher Reference</a></li>
                          			<li class="divider"></li>
									<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalNLESDCompletedReferences.html">Completed Reference(s)</a></li>
					         	</ul>
					        </li>

					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-th-list"></span> Short Lists<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

										<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalJobShortlists.html">Job Short Lists</a></li>
                          				<li><a onclick="loadingData()" href="/MemberServices/Personnel/principalSubListShortlists.html">Substitutes</a></li>



					         	</ul>
					        </li>

 <%}%>


<!-- HELP MENU -->
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-question-sign"></span> Help<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">

										<li><a onclick="loadingData()" href="/contact/stafffinderresults.jsp?pos=Human+Resources&region=" target="_blank">HR Contacts</a></li>
										<li><a href="/MemberServices/" onclick="loadingData()">Exit to MS</a></li>

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
If you are experiencing technical difficulties with this system, email Geoff Taylor <a href="mailto:geofftaylor@nlesd.ca?subject=MyHRP System">geofftaylor@nlesd.ca</a> (Avalon Region) or Rodney Batten <a href="mailto:rodneybatten@nlesd.ca?subject=MyHRP System">rodneybatten@nlesd.ca</a> (Central/Western/Labrador).
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
