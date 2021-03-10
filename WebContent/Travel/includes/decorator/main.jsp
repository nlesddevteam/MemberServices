<!-- TRAVEL CLAIM APP DECORATOR FILE (C) 2020  -->	
<!-- AUTHOR: Geoff Taylor geofftaylor@nlesd.ca -->
<!-- HTML 5 BOOTSTRAP 4.4.1 JQUERY 3.4.1 JAVA 8.0 -->

<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.personnel.*,
         		 com.awsd.personnel.profile.*,
                 com.awsd.travel.*,
                 com.awsd.travel.bean.*,
                 com.awsd.common.*,
                 com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>


<!-- LOAD JAVA TAG LIBRARIES -->
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>		
		<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		
<!-- PREVENT CACHE OF LOCAL JS AND CSS FROM AGING TOO LONG -->		
		<c:set var="cacheBuster" value="<%=new java.util.Date()%>" />				 								
		<fmt:formatDate value="${cacheBuster}" pattern="MMddyyyyH" var="todayVer" />	
 
<esd:SecurityCheck permissions="TRAVEL-EXPENSE-VIEW" />
<%
User usr = null;
TravelClaims claims = null;
TreeMap year_map = null;
TreeMap pending_approval = null;
TreeMap approved = null;

LinkedHashMap rejected_claims = null;

LinkedHashMap payment_pending = null;
LinkedHashMap pre_submission=null;
LinkedHashMap rejected=null;																																																																																				
TreeMap paid_today = null; 
TravelClaim claim = null;
Iterator iter = null;
Iterator y_iter = null;
Map.Entry item = null;
DecimalFormat df = null;
DecimalFormat dollar_f =  null;
TravelBudget budget = null;
Iterator p_iter = null;

  int c_cnt = 0;
  int counter=0;
  usr = (User) session.getAttribute("usr");
  
  claims = usr.getPersonnel().getTravelClaims();
  budget = usr.getPersonnel().getCurrrentTravelBudget();
	//populate initial objects from database
  if(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW")){
    pending_approval = usr.getPersonnel().getTravelClaimsPendingApproval();    
  }
  else
  {
    pending_approval = null;    
  
  }
  if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))
  {
	    approved = usr.getPersonnel().getTravelClaimsApproved();
	    payment_pending = usr.getPersonnel().getTravelClaimsPaymentPending();
	    paid_today = usr.getPersonnel().getTravelClaimsPaidToday();
	    pre_submission = usr.getPersonnel().getTravelClaimsPreSubmission();
	    rejected = usr.getPersonnel().getTravelClaimsRejected();
  }
  else
  {
    approved = null;
    payment_pending = null;
    paid_today = null;
    pre_submission = null;
    rejected = null;
  }

  df = new DecimalFormat("#,##0");
  dollar_f = new DecimalFormat("$#,##0");
  
  ArrayList<TravelClaimKMRate> rates = TravelClaimKMRateDB.getTravelClaimKMRates(); 
  
%>

<html lang="en">
  <head>
	
	<!-- META TAGS  -->			
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">				
    	<meta name="generator" content="Eclipse IDE for Enterprise Java Developers.Version: 2019-09 R (4.13.0)">
	    <meta name="dcterms.created" content="Fri, 07 Feb 2020 16:59:07 GMT">    	     				
		<meta name="description" content="The Newfoundland and Labrador English School District represents all English speaking students and schools in Newfoundland and Labrador. The District includes approximately 65,300 students, 252 schools and six alternate sites, and over 8,000 employees." />	
		<meta name="keywords" content="NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		<meta name="google-site-verification" content="4Miuw9m-R9EmQ7GZUE1KP0gpOp91TlWr2KU9FRPqlO4" />
		<meta name="google-translate-customization" content="fc4e6bf392424f3-26adbaed3174ebd4-g7130b514bfc120b1-c">
				
		<title><decorator:title default="Newfoundland and Labrador English School District" /></title>

	<!-- CSS STYLESHEET FILES -->	   
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.dataTables.min.css">
		<link rel="stylesheet" href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css">
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">      
  	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.7.2/animate.css">		
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
  		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
		
	<!--  CSS LOCAL FILES -->
		<link rel="stylesheet" href="/MemberServices/Travel/includes/css/travel.css?ver=${todayVer}">			
		<link rel="shortcut icon" href="/MemberServices/Travel/includes/img/favicon.ico">	
		
	<!-- CDN JAVASCRIPT> -->	
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
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
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/jQuery.print@1.5.1/jQuery.print.min.js"></script> 
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js" integrity="sha512-d4KkQohk+HswGs6A1d6Gak6Bb9rMWtxjOa0IiY49Q3TeFd5xAzjWXDCBW9RS7m86FQ4RzM2BdHmdJnnKRYknxw==" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js" crossorigin="anonymous"></script>
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-confirmation/1.0.7/bootstrap-confirmation.min.js" crossorigin="anonymous"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js" crossorigin="anonymous"></script>

	 <!-- LOCAL JAVASCRIPT FILES-->		
	 	<script src="/MemberServices/Travel/includes/ckeditor/ckeditor.js"></script>	 	
		<script src="/MemberServices/Travel/includes/js/travel.js?ver=${todayVer}"></script>	 	  			

	<!-- GOOGLE ANALYTICS -->
		<script>
				  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
				  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
				  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
				  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');		
				  ga('create', 'UA-74660544-1', 'auto');
				  ga('send', 'pageview');
		</script>			
		
		<decorator:head />

<c:set var="now" value="<%=new java.util.Date() %>" /> 	
<c:set var="theExpiredDate" value="<%=rates.get(0).getEffectiveEndDate() %>" /> 						
<fmt:formatDate value="${now}" pattern="yyyyMMdd" var="todayDate" />
<fmt:formatDate value="${theExpiredDate}" pattern="yyyyMMdd" var="expiredDate" />


	</head>

	<body>	
	<div class="mainContainer">
	
<!-- TOP PANEL -->
<div class="container-fluid headerBackground">
<div align="center"><img src="includes/img/nlesdwhitelogolg.png" class="headerLogo" />
<br/>

<!-- SEARCH In HEADER WHEN SMALL SCREEN -->
<esd:SecurityAccessRequired	permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-CLAIM-SEARCH,TRAVEL-EXPENSE-VIEW-REPORTS,TRAVEL-EXPENSE-SDS-EXPORT"> 
		        <form class="form-inline searchFormHeader" style="font-size:11px;">
		        <div class="input-group">
		        <div class="form-check-inline">
				  	<label class="form-check-label">
				    	<input type="radio"  name="searchtype" id="searchTypeHead" value="NAME" class="form-check-input mr-sm-2">NAME
				  	</label>
				</div>
				<div class="form-check-inline">
				  	<label class="form-check-label">
				    	<input type="radio"  name="searchtype" value="VENDOR" class="form-check-input mr-sm-2" >VENDOR #
				  	</label>
				</div>        
		    	<input type="text" name="srch_txt" id="search-text-top" placeholder="Enter Search Term(s)" value="" onfocus="this.select();" class="form-control form-control-sm"> 
				<div class="input-group-append">
				<input class="btn btn-success  btn-sm" type="button" value="GO" onclick="searchclaims();">      
				</div>
				</div>
		   	 </form>     	 
   	 </esd:SecurityAccessRequired>
</div>
</div>
	
<!-- START NLESD MAIN NAVIGATION BAR navbar-fixed-top-->
<nav class="navbar navbar-expand-md navbar-dark sticky-top" id="main_navbar">
     <a class="navbar-brand" href="#" title="Newfoundland and labrador English School District"><img src="/MemberServices/Travel/includes/img/nltopleftlogo.png" id="logoTag" class="navbar-img" border=0 /></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarDD" aria-controls="navbarDD" aria-expanded="false" aria-label="Navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarDD">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp" onclick="loadingData()"><i class="fas fa-home"></i> HOME <span class="sr-only">(current)</span></a>
            </li>  
            <li class="nav-item">
                <a class="nav-link" href="myProfile.html"  onclick="loadingData();loadMainDivPage('myProfile.html');return false;"><i class="fas fa-user"></i> PROFILE</a>
            </li>        
            <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-file-invoice"></i> CLAIMS</a>
                <ul class="dropdown-menu">        
					<li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('myclaims.jsp');return false;"><i class="fa fa-fw fa-user"></i> My Previous Claims</a></li>  
              <c:if test="${todayDate le expiredDate}"> 
                    <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('addTravelClaim.html');return false;"><i class="fa fa-fw fa-plus"></i> Start New Claim</a></li>
             </c:if>                                      
                </ul>
            </li>
            
 <esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-SUPERVISOR-VIEW">	               
             <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-users-cog"></i> SUPERVISOR</a>
                <ul class="dropdown-menu"> 
                <%
                c_cnt = 0;	                
				if ((pending_approval != null) && (pending_approval.size() > 0)) {
					//count pending approval claims
					iter = pending_approval.entrySet().iterator();
					while (iter.hasNext()) {
						c_cnt += ((Vector) ((Map.Entry) iter.next()).getValue()).size();
					}}
					%>           
                	<li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('supervisor_approval.jsp');return false;"><i class="far fa-clock"></i> Claims Pending Approval <span style="color:Red;font-weight:bold;">(<%=c_cnt%>)</span></a></li> 
                    <li class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('viewPreviousApproved.html');return false;"><i class="far fa-check-square"></i> Claims I Approved</a></li>                        
                    <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('viewPreviousApproved.html?sid=5');return false;"><i class="far fa-window-close"></i> Claims I Rejected</a></li>                     
                </ul>
            </li>
</esd:SecurityAccessRequired>    
<esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-EXPENSE-VIEW-REPORTS,TRAVEL-EXPENSE-SDS-EXPORT">            
             <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user-cog"></i> ADMIN</a>
                <ul class="dropdown-menu">  
                <esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW">            
					                    <li><a class="dropdown-item dropdown-toggle" href="#"><i class="fa fa-fw fa-money"></i> Payments</a>
					                        <ul class="dropdown-menu">		
					                        <%
									   		
											c_cnt=0;										
											if((approved != null)&& (approved.size() > 0)) {
												//count approved claims
												iter = approved.entrySet().iterator();
										        while(iter.hasNext()){
										          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
										        }														                        
					                        %>                        
					                          <li>
					                          <a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('claimsApprovedByRegion.html');return false;"><i class='fa fa-fw fa-check'></i> Approved (<%=c_cnt%>)</a>
					                          </li>
					                        <%
					                        }
												counter=0;
												c_cnt=0;									
												if((paid_today != null)&& (paid_today.size() > 0)) {
													//get count of paid claims today
													iter = paid_today.entrySet().iterator();
											        while(iter.hasNext()){
											          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
											        }    %>
					                             <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('claimsPaidTodayLetter.html');return false;"><i class='fa fa-fw fa-money'></i> Processed Today (<%=c_cnt %>)</a></li>  
					                            <%
					                            }
											   		counter=0;
													c_cnt=0;												
													if((payment_pending != null)&& (payment_pending.size() > 0)) {
														//get count of payment pending claims
														iter = payment_pending.entrySet().iterator();
												        while(iter.hasNext()){
												          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
												        }
												        %>
					                             <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('claimsPaymentPendingLetter.html');return false;"><i class='fa fa-fw fa-warning'></i> Pending Info (<%=c_cnt %>)</a></li>    
					                            <%}%>                        
					                        </ul>
					                    </li>
       </esd:SecurityAccessRequired>  
      
      <esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-ADMIN">        
                    <li><a class="dropdown-item dropdown-toggle" href="#"><i class="fa fa-fw fa-users"></i> Rules</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('listSupervisorRules.html');return false;"><i class="fa fa-fw fa-file-text-o"></i> Current Rules</a></li>
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('addSupervisorRule.html');return false;"><i class="fa fa-fw fa-user-plus"></i> Add/Edit Rule</a></li>
                        </ul>
                    </li>
                    
                     <!--  ********************* HIDE TRAVEL BUDGETS - FUTURE USE / NOT USED
					<li><a class="dropdown-item dropdown-toggle" href="#"><i class="fa fa-fw fa-bar-chart"></i> Budgets</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('listTravelBudgets.html');"><i class="fa fa-fw fa-file-text-o"></i> List Budgets</a></li>
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('addTravelBudget.html');"><i class="fa fa-fw fa-bus"></i> New Budget</a></li>
                        </ul>
                    </li>
                    END HIDE TRAVEL BUDGETS -->
                    
                     <li><a class="dropdown-item dropdown-toggle" href="#"><i class="fa fa-fw fa-users"></i> KM Rates</a>
                        <ul class="dropdown-menu">
                        
                            
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('listKmRates.html');return false;"><i class="fa fa-fw fa-file-text-o"></i> List Current Rates</a></li>
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('addKmRate.html');return false;"><i class="fa fa-fw fa-user-plus"></i> Set New Rate</a></li>              
                             <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('travel_rates.jsp');return false;"><i class="fa fa-fw fa-file-text-o"></i> View/Set Approved Rates</a></li>
                            <li class="dropdown-divider"></li>              
                            <li><a class="dropdown-item" href="https://www.gov.nl.ca/exec/hrs/working-with-us/auto-reimbursement/" target="_blank"><i class="fa fa-fw fa-file-text-o"></i> Gov NL Rates</a></li>
                        </ul>
                    </li>
        </esd:SecurityAccessRequired>
       <esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-VIEW-REPORTS">                 
                    <li><a class="dropdown-item dropdown-toggle" href="#"><i class="fa fa-fw fa-book"></i> Reports</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('yearly_kms_detail.jsp');"><i class="fa fa-fw fa-car"></i> Yearly KM Details</a></li>
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('yearly_claims_detail.jsp');"><i class="fa fa-fw fa-calendar-minus-o"></i> Yearly Details</a></li>
                            <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('yearly_claims_detail_top.jsp');"><i class="fa fa-fw fa-calendar-minus-o"></i> Top User Usage Report</a></li>
        <esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW">        
        					<li class="dropdown-divider"></li>
         					<%
					   		counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((pre_submission != null)&& (pre_submission.size() > 0)) {
								//get count of payment pending claims
								iter = pre_submission.entrySet().iterator();
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
								%>
								<li><a class="dropdown-item" href='#' onclick="loadingData();loadMainDivPage('claimsPreSubmissionLetter.html');"><i class='fa fa-fw fa-warning'></i> Claims in Pre-Submission (<%=c_cnt%>)</a></li>
								<%
							}
							}					
					   		counter=0;
							c_cnt=0;
							if(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW")){
							if((rejected != null)&& (rejected.size() > 0)) {
								//get count of payment pending claims
								iter = rejected.entrySet().iterator();								
						        while(iter.hasNext()){
						          c_cnt += ((Vector)((Map.Entry)iter.next()).getValue()).size();
						        }
						        %>
								<li><a href='#' class="dropdown-item" onclick="loadingData();loadMainDivPage('claimsRejectedLetter.html');"><i class='fa fa-fw fa-warning'></i> Claims Rejected (<%=c_cnt %>)</a></li>
								<%
									}
									}
								%>			
                                         
         </esd:SecurityAccessRequired>                                    
                        </ul>
                    </li>
           </esd:SecurityAccessRequired>   
           <esd:SecurityAccessRequired permissions="TRAVEL-EXPENSE-SDS-EXPORT">      
                       <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('exportPaidTravelClaims.html');"><i class="fas fa-file-export"></i> Export to Data File</a></li>
            </esd:SecurityAccessRequired>           
                </ul>
            </li>
</esd:SecurityAccessRequired>                
            
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-question-circle"></i> HELP</a>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="#" onclick="loadingData();loadMainDivPage('calculatedistance.jsp');return false;"><i class="fa fa-fw fa-car"></i> Distance Calculator</a></li>
                    <li><a class="dropdown-item" href="/about/policies.jsp#FIN-401" target="_blank"><i class="fa fa-fw fa-calendar-minus-o"></i> Travel Policy (FIN-401)</a></li>                 
               		<li><a class="dropdown-item" href="https://forms.gle/xCxyaPMgTDcY18Tm6" target="_blank"><i class="fa fa-fw fa-calendar-minus-o"></i> Feedback Form</a></li>                 
               		
                </ul>
            </li>
          </ul>
        
    </div>   
    
    <!--  SEARCH BAR IN NAV BAR -->
    <esd:SecurityAccessRequired	permissions="TRAVEL-CLAIM-ADMIN,TRAVEL-CLAIM-SEARCH,TRAVEL-EXPENSE-VIEW-REPORTS,TRAVEL-EXPENSE-SDS-EXPORT"> 
		        <form class="form-inline searchFormNav" style="font-size:11px;margin-top:5px;">
		        <div class="input-group">
		        <div class="form-check-inline">
				  	<label class="form-check-label">
				    	<input type="radio"  name="searchtype" id="searchTypeNav" value="NAME" class="form-check-input mr-sm-2">NAME
				  	</label>
				</div>
				<div class="form-check-inline">
				  	<label class="form-check-label">
				    	<input type="radio"  name="searchtype" value="VENDOR" class="form-check-input mr-sm-2" >VENDOR #
				  	</label>
				</div>        
		    	<input type="text" name="srch_txt"  id="search-text-nav" placeholder="Enter Search Term(s)" value="" onfocus="this.select();" class="form-control form-control-sm"> 
				<div class="input-group-append">
				<input class="btn btn-success  btn-sm" type="button" value="GO" onclick="searchclaims();">      
				</div>
				</div>   
		   	 </form>     	 
   	 </esd:SecurityAccessRequired>
</nav>

	<!-- PAGE BODY -->
			
				<div class="container-fluid">	
				<!-- Get the loading data animation ready, in front and hidden -->
				<div id="loadingSpinner" style="display:none;z-index:9999;">
				<div id="spinner">
				<img src="/MemberServices/Travel/includes/img/processing5.gif" width="250" border=0>
				<br/>Loading data, please wait...
				<span id="spinnerWarning"></span>
				</div>
				</div>				
						<div id="printJob">					
						<div class="alert alert-danger no-print" id="claimRateMessage" style="text-align:center;display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>  	
						
						<div class="alert alert-danger no-print" id="claimNoticeMessage" style="display:none;margin-top:10px;margin-bottom:10px;padding:5px;"></div>  	
						
							  
                        <%if(request.getAttribute("msg") != null){%>                    
                         <div class="alert alert-danger"><%=request.getAttribute("msg")%></div>                            		
                       <%}%>
							
																		
   								<div class="msgerr alert alert-danger no-print details_error_message" style="display:none;"></div>         
         				    	<div class="msgok alert alert-success no-print details_success_message" style="display:none;"></div>
         				    	<div class="msginfo alert alert-info details_info_message no-print" style="display:none;"></div>   
										
								<decorator:body />	
								
						<br/>	
						 <div class="alert alert-info no-print">
						 <span style='float:left;font-size:20px;padding-right:5px;'><i class="fas fa-comment"></i></span>						 
						<b>NOTE:</b> This is a travel claim system ONLY. 
						All other	expenditures MUST be handled through the District's purchasing system(s) or through school accounts. 
						Where other expenditures are included, claim processing may be delayed.
						</div>						
    					<div class="alert alert-warning no-print" >    					
    					<span style='float:left;font-size:20px;padding-right:5px;'><i class="far fa-question-circle"></i></span>
						<b>SUPPORT:</b> If you are have any questions regarding a travel claim entry or payments, please contact Travel Claim Support 
						 <a href="mailto:crystalwhitten@nlesd.ca?Travel Claim Support">Crystal Whitten</a> at (709) 758-2397. <br/>For technical problems, email <a href="mailto:geofftaylor@nlesd.ca?Travel Claim Support">geofftaylor@nlesd.ca</a>.
						 <!-- or   <a href="mailto:sherrymiller@nlesd.ca?Travel Claim Support">Sherry Miller</a> at (709) 758-2373.-->
						
						</div>		
								
						</div>					
				</div>
			</div>				

<!-- FOOTER AREA -->		

  <div align="center" class="no-print navBottom">
   		<div style="display:inline-block;margin-top:5px;"><a href="index.jsp" class="btn btn-primary btn-sm" role="button" onclick="loadingData();">Home</a></div>
    	
  <c:if test="${todayDate le expiredDate}">  	
    	<div style="display:inline-block;margin-top:5px;"><a href="#" class="btn btn-danger btn-sm" role="button" onclick="loadingData();loadMainDivPage('addTravelClaim.html');return false;">Start Claim</a></div>
  </c:if>  	
    	<div style="display:inline-block;margin-top:5px;"><a href="#" class="btn btn-success btn-sm" role="button" onclick="loadingData();loadMainDivPage('myclaims.jsp');return false;">My Claims</a></div>
    	<div style="display:inline-block;margin-top:5px;"><a href="#" class="btn btn-info btn-sm" role="button" onclick="loadingData();loadMainDivPage('myProfile.html');return false;">My Profile</a></div>
 		<esd:SecurityAccessRequired permissions="TRAVEL-CLAIM-SUPERVISOR-VIEW">	 
 		<div style="display:inline-block;margin-top:5px;"><a href="#" class="btn btn-warning btn-sm" role="button" onclick="loadingData();loadMainDivPage('supervisor_approval.jsp');return false;"><i class="fas fa-lock"></i> Claims to Approve</a></div>
 	</esd:SecurityAccessRequired>
		</div>
		
<br/>&nbsp;<br/>

	<div class="mainFooter">	
		
		    <!-- SEARCH TERMS -->
		    <div class="row" >
		  <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                   
	 		<div class="gTranslate">
		  	<div id="google_translate_element" style="display:inline-block;"></div>
			    <script type="text/javascript">
					function googleTranslateElementInit() {new google.translate.TranslateElement({pageLanguage: 'en',
						includedLanguages: 'en,fr,es', 
						layout: google.translate.TranslateElement.InlineLayout.SIMPLE, 
						autoDisplay: false}, 'google_translate_element');}
				</script>
				<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
		  		
         <span class="navBottomFontSize">
         		<div style="display:inline-block;"><a title="Decrease Page Font Size" href="#" class="btn btn-secondary btn-xs" id="btn-decrease">A-</a></div>
 				<div style="display:inline-block;"><a title="Reset Page Font Size" href="#" class="btn btn-secondary btn-xs" id="btn-orig">A</a></div>
 				<div style="display:inline-block;"><a title="Increase Page Font Size" href="#" class="btn btn-secondary btn-xs" id="btn-increase">A+</a></div>
         </span>
         </div>
		  <div class="copyright">Travel Claim App 3.6 &copy; 2021 NLESD &middot; All Rights Reserved. &nbsp;&nbsp;</div>		
		 </div>		  
		</div> 
	 	
		
	</div>
<!-- WELCOME MODAL -->
<div id="welcomeMessage" class="modal fade mainView" style="z-index:99999;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="text-align:center;">
            <h4 class="modal-title"><img src="includes/img/header.png" border=0 style="max-width:100%;"/></h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            </div>
            <div class="modal-body" style="font-size:11px;">
				Welcome to the updated Travel Claim System. Please take sometime to familiarize yourself with the new layout and features. 
				<br/><br/>Depending on your access level, there are a wide variety of options available. 
				<br/><br/>This new system is  more mobile friendly and is linked to the PD calendar to automatically link your attended PD events to a related travel claim you may start.
				<br/><br/>Feel free to fill out the <a href="https://forms.gle/xCxyaPMgTDcY18Tm6" target="_blank">Feedback Form</a> for comments and suggestions. 
				<br/>
			
               <div style="text-align:center;"><img class="topLogoImg" src="includes/img/welcomepd.png" border=0 style="padding-top:5px;"/></div>
            </div>
             <div class="modal-footer">
        <button type="button" class="btn btn-sm btn-success" data-dismiss="modal">Continue</button>       
      </div>
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
					 $(".navbar-brand").css("display","block")	;
				} else if (($window.scrollTop() < distance) && (x >= 768)) {					
					 $(".navbar-brand").css("display","none");
				} else if (x<768){
					 $(".navbar-brand").css("display","block");	
				} else {
					$(".navbar-brand").css("display","none");					
				}				
				});
				
				$("#searchTypeHead").prop("checked", true);
				$("#searchTypeNav").prop("checked", true);
		</script>	


<!-- ENABLE NAVBAR -->
<script src="/MemberServices/Travel/includes/js/bootstrap-4-navbar.js"></script>

<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}

		</script>


<!-- ENABLE OPEING WELCOM, SHOW ONCE SET WITH COOKIE -->
<script>
$(document).ready(function(){
//Give NL cookie monster a cookie so modal welocme only displays ONCE.	Seems like google is demanding the SameSite and secure options now too...
var displayTCWelcomeModalCookie = $.cookie("displayTCWelcomeModalCookie");
$.cookie("displayTCWelcomeModalCookie", "hideWelcome", { 
	expires: 365, 
	path: "/MemberServices/Travel/",
	SameSite: "Lax", 
	secure  : true 	
});

if (displayTCWelcomeModalCookie=="hideWelcome") {
$("#welcomeMessage").modal('hide').css("display","none");
}	else {		
$("#welcomeMessage").modal('show');
}	
});
</script>


</body>

</html>

<!-- END DECORATOR -->

