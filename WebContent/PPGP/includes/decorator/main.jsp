<!-- PPGP DECORATOR FILE (C) 2018  -->	
<!-- APPLICATION FOR NLESD STAFF (MEMBER) SERVICES -->
<!-- HTML 5 BOOTSTRAP 3.3.7 JQUERY 3.3.1 -->
<!-- THIS CAOS BY GEOFF - Good Luck! -->

<%@ page language="java"
         session="true"
         import="com.awsd.ppgp.*,com.awsd.security.*,
                 java.text.*,
                 java.util.*"
        isThreadSafe="false"%> 


<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
        <%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	User usr = (User) session.getAttribute("usr");
  	HashMap<String, PPGP> ppgps = PPGPDB.getPPGPMap(usr.getPersonnel());
%>

<esd:SecurityCheck permissions='PPGP-VIEW' />



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
		
		<title><decorator:title default="Professional Learning Plan System" /></title>
	
	<!-- CSS STYLESHEET FILES -->	
		<link href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.dataTables.min.css">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">		
		<link rel="stylesheet" href="/MemberServices/PPGP/includes/css/ppgp.css">			
		<link rel="shortcut icon" href="/MemberServices/PPGP/includes/img/favicon.ico">	
		<link href="/MemberServices/PPGP/includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link href="/MemberServices/PPGP/includes/css/hover_drop_2.css" rel="stylesheet" media="all" type="text/css" />				
		


	
	<!-- JAVASCRIPT FILES -->
		
		<script src="/MemberServices/PPGP/includes/ckeditor/ckeditor.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>	
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  		
  		<script src="/MemberServices/PPGP/includes/js/bootstrap-confirmation.min.js"></script> 			
		<script src="/MemberServices/PPGP/includes/js/jquery-ui.js"></script>
		<script src="/MemberServices/PPGP/includes/js/jquery.cookie.js"></script>		
		<script src="/MemberServices/PPGP/includes/js/bootstrap-multiselect.js"></script>
		<script src="/MemberServices/PPGP/includes/js/iefix.js"></script>
	    <script src="/MemberServices/PPGP/includes/js/jquery.validate.js"></script>
	    <script src="/MemberServices/PPGP/includes/js/ppgp.js"></script> 
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

			
		
		
		
  <style>
 .ui-button {background-color:none;}
 	
 	.cke_skin_v2 input.cke_dialog_ui_input_text, .cke_skin_v2 input.cke_dialog_ui_input_password {
 	position: relative;
    z-index: 9999; 
    }
    
  </style>
	</head>

	<body>
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:9999;"><div id="spinner"><img src="/MemberServices/PPGP/includes/img/loading4.gif" width="200" border=0><br/>Loading data, please wait...</div></div>
		
	<!-- TOP PANEL -->
		<div class="mainContainer">	
		
		
		
			<div class="container-fluid" style="max-height:230px;min-height:120px;height:auto;">		
				<div class="row">				  
				  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="text-align:center;">
				   	<img class="topLogoImg" src="/MemberServices/PPGP/includes/img/ppgpheader-educator.png" title="Newfoundland and Labrador English School District">
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
		      	<a class="navbar-brand" href="/index.html" title="NLESD Home"><img src="/MemberServices/PPGP/includes/img/logomin.png" id="logoTag" border=0 /></a>
		    </div>    
    
  		<div class="collapse navbar-collapse" id="myNavbar" style="padding-left:0px;">
    
			<ul class="nav navbar-nav"> 
			
<!-- HOME MENU-->   
					    	<li class="dropdown" id="menuNormal">
					    	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-file"></span> File <span class="caret"></span></a>
					    	 <ul class="dropdown-menu multi-level">
					    	 
					          <li><a onclick="loadingData()" href="/MemberServices/PPGP/policy.jsp">Home</a></li>
					          <li><a href='#' title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center style=margin-bottom:15px;><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>Professional Learning Plan</b></div><br/><br/>'});">Print Page</a></li>
                              <li class="divider"></li>
                               <li><a onclick="loadingData()" href="/MemberServices/navigate.jsp">Back to MS</a></li>
					          	</ul>
					        </li> 


<!-- LEARNING PLANS-->   
  
                   
                  

					    	<li class="dropdown" id="menuNormal">
					    	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-apple"></span> Your Learning Plan(s) <span class="caret"></span></a>
					    	 <ul class="dropdown-menu multi-level">
					    	
					    	 
					    	 <%if(ppgps.size() < 1){%> 
					    			<li><a onclick="loadingData()" href='viewGrowthPlan.html?sy=<%=PPGP.getCurrentGrowthPlanYear()%>'>Create <%=PPGP.getCurrentGrowthPlanYear()%> PLP</a></li>	
					    	<%}else{
	            				if(ppgps.containsKey(PPGP.getCurrentGrowthPlanYear())){
	            					
	            					if(!ppgps.containsKey(PPGP.getNextGrowthPlanYear())){
		            				PPGP ppgp = ppgps.get(PPGP.getCurrentGrowthPlanYear());
		            			
		            					if(ppgp.isSelfReflectionComplete()) { %>	            			
		            					<li><a onclick="loadingData()" href='viewGrowthPlan.html?sy=<%=PPGP.getNextGrowthPlanYear()%>'>Create <%=PPGP.getNextGrowthPlanYear()%> PLP</a></li>	            			
		            					<%} else { %>	           			
		            					<div style="font-size:10px;text-align:center;">You will be able to create your <%=PPGP.getNextGrowthPlanYear()%> PLP after you complete the self reflections in your <%=ppgp.getSchoolYear()%> PLP.</div>
		            					<%}
	            					}		
	            				} else {
            			
	            					if(ppgps.containsKey(PPGP.getPreviousGrowthPlanYear())){
            						PPGP ppgp = ppgps.get(PPGP.getPreviousGrowthPlanYear());
            							
            						if(ppgp.isSelfReflectionComplete()) { %>
              						<li><a onclick="loadingData()" href='viewGrowthPlan.html?sy=<%=PPGP.getCurrentGrowthPlanYear()%>'>Create <%=PPGP.getCurrentGrowthPlanYear()%> PLP</a></li>
              						<% } else { %>
              						<div style="font-size:10px;text-align:center;">You will be able to create your <%=PPGP.getCurrentGrowthPlanYear()%> PLP after you complete the self reflections in your <%=ppgp.getSchoolYear()%> PLP.</div>
		            				<%}} else { %>
            						<li><a onclick="loadingData()" href='viewGrowthPlan.html?sy=<%=PPGP.getCurrentGrowthPlanYear()%>'>Create <%=PPGP.getCurrentGrowthPlanYear()%> PLP</a></li>
            			
            					<% }}
            		
              }%>		       
					         </ul>
					        </li> 


<!-- ARCHIVE-->   
					    	<li class="dropdown" id="menuNormal">
					    	<a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-folder-close"></span> Your Plan Archive <span class="caret"></span></a>
					    	 <ul class="dropdown-menu multi-level">
					    	<%if(ppgps.size() > 0){   
					        	ArrayList<PPGP> sorted = new ArrayList<PPGP>();
            					sorted.addAll(ppgps.values());
            					Collections.sort(sorted, new SchoolYearComparator());  			
	            				for(PPGP ppgp : sorted) {%>
				                  <li><a onclick="loadingData()" href="viewGrowthPlanSummary.html?ppgpid=<%= ppgp.getPPGPID()%>"><%= ppgp.getSchoolYear()%></a></li>
								<%}}%>	         
					
                             
					          	</ul>
					        </li> 


<!-- If Program Specialist/DOS/or Principal, check to display proper access to PLP summaries in main menu -->
<%if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY") || usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST") ) { %>
			 		
				
 							<li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-cog"></span> Administration<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          			<%if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY")) { %>
											<li><a onclick="loadingData()" href="viewGrowthPlanPrincipalSummary.html?syear=CUR"><%=PPGP.getCurrentGrowthPlanYear()%> Summaries</a></li>
											<li><a onclick="loadingData()" href="viewGrowthPlanPrincipalSummary.html?syear=PREV"><%=PPGP.getPreviousGrowthPlanYear()%> Summaries</a></li>
										<%} if(usr.getUserPermissions().containsKey("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST")) { %>
											<li><a onclick="loadingData()" href="viewGrowthPlanProgramSpecialistSummary.html?syear=CUR"><%=PPGP.getCurrentGrowthPlanYear()%> Summaries</a></li>
											<li><a onclick="loadingData()" href="viewGrowthPlanProgramSpecialistSummary.html?syear=PREV"><%=PPGP.getPreviousGrowthPlanYear()%> Summaries</a></li>	
											<%if(usr.getUserRoles().containsKey("SENIOR EDUCATION OFFICIER") || 
												 usr.getUserRoles().containsKey("SENIOR EDUCATION OFFICER") || 
												 usr.getUserRoles().containsKey("DIRECTOR OF SCHOOLS")){ %>
											<li><a onclick="loadingData()" href="printProgramSpecialistSummary.jsp?syear=CUR">Print <%=PPGP.getCurrentGrowthPlanYear()%> Summaries</a></li>
											<li><a onclick="loadingData()" href="printProgramSpecialistSummary.jsp?syear=PREV">Print <%=PPGP.getPreviousGrowthPlanYear()%> Summaries</a></li>
											<%} %>
											<li><a onclick="loadingData()" href="searchPGP.html">Search All Summaries</a></li>
										<%} %>
					         	        	
					         	</ul>
					        </li> 

<%}%>
				
				        
<!-- HELP MENU -->					        
					        <li class="dropdown" id="menuNormal">
					          <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-question-sign"></span> Help<span class="caret"></span></a>
					          	<ul class="dropdown-menu multi-level">
					          	
										<li><a href="/contact/stafffinderresults.jsp?pos=Director of Schools&region=" target="_blank">Contacts</a></li>
										<li><a href="/MemberServices/" >Exit to MS</a></li>						            
					         	
					         	</ul>
					        </li> 

   			</ul>
   			 
 		</div>
   </div>
</nav>

<!-- PAGE BODY -->



			
				<div class="container-fluid">				
					
					
					<div id="printJob">			
					
								<decorator:body />	
					</div>

				</div>



<div class="alert alert-info no-print" style="text-align:center;font-size:11px;">
Any questions or concerns relating to a profile should be directed to the 
proper contact(s) found under the Help menu above, your regional Director of Schools, or your local school administrator.<br/> 
If you are experiencing technical difficulties with this system, email <a href="mailto:mssupport@nlesd.ca?subject=PPGP System">mssupport@nlesd.ca</a>.
</div>

<!-- FOOTER AREA -->		
	
	<div class="mainFooter no-print"> 
	 
	
		<div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">		
		  Professional Learning Plan App 2.0 &copy; 2019 Newfoundland and Labrador English School District &middot; All Rights Reserved 
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

