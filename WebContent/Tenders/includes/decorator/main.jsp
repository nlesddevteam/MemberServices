<!-- NLESD.CA TENDER ADMIN DECORATOR FILE (C) 2021  -->	
<!-- AUTHOR: Geoff Taylor geofftaylor@nlesd.ca -->
<!-- HTML 5 BOOTSTRAP 4 JQUERY 3.5 JAVA 8.0 -->

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
    	<meta name="generator" content="Eclipse IDE for Enterprise Java Developers.Version: 2020-12">
	    <meta name="dcterms.created" content="31 March 2021">    	     				
		<meta name="description" content="The Newfoundland and Labrador English School District represents all English speaking students and schools in Newfoundland and Labrador. The District includes approximately 65,300 students, 252 schools and six alternate sites, and over 8,000 employees." />	
		<meta name="keywords" content="NLESD,Newfoundland and Labrador English School District,Eastern School District,English School District,Avalon School District,Vista School District,Burin School District,NOVA Central School District,Western School District,Labrador School District,Newfoundland Education,Education newfoundland,Schools in Newfoundland,Newfoundland Schools,St. John's,Corner Brook,Gander,Happy Valley-Goose Bay,School Board,School Board Trustees,Newfoundland School Board">  
		<meta name="google-site-verification" content="4Miuw9m-R9EmQ7GZUE1KP0gpOp91TlWr2KU9FRPqlO4" />
		<meta name="google-translate-customization" content="fc4e6bf392424f3-26adbaed3174ebd4-g7130b514bfc120b1-c">
		
		<title><decorator:title default="Tender Posting System" /></title>
	
<!-- CSS STYLESHEET FILES CDN -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.0/animate.min.css"/>	
		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css"/> 		
				
		<link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/dataTables.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/autofill/2.3.5/css/autoFill.bootstrap4.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.6.4/css/buttons.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/colreorder/1.5.2/css/colReorder.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/fixedcolumns/3.3.1/css/fixedColumns.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/fixedheader/3.1.7/css/fixedHeader.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/keytable/2.5.3/css/keyTable.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/responsive/2.2.6/css/responsive.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/rowgroup/1.1.2/css/rowGroup.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/rowreorder/1.2.7/css/rowReorder.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/scroller/2.0.3/css/scroller.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/searchbuilder/1.0.0/css/searchBuilder.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/searchpanes/1.2.0/css/searchPanes.bootstrap4.min.css"/>
		<link rel="stylesheet" href="https://cdn.datatables.net/select/1.3.1/css/select.bootstrap4.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto"/>		

 <!--  CSS LOCAL FILES - ANY MODIFICATIONS DO HERE-->  		
  		<link rel="stylesheet" href="includes/css/tenders.css?ver=${todayVer}"/>				
	
<!-- JAVASCRIPT FILES CDN-->			
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>		
		<script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
		<script src="https://cdn.datatables.net/1.10.22/js/dataTables.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/autofill/2.3.5/js/dataTables.autoFill.min.js"></script>
		<script src="https://cdn.datatables.net/autofill/2.3.5/js/autoFill.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/dataTables.buttons.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.colVis.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.flash.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.html5.min.js"></script>
		<script src="https://cdn.datatables.net/buttons/1.6.4/js/buttons.print.min.js"></script>
		<script src="https://cdn.datatables.net/colreorder/1.5.2/js/dataTables.colReorder.min.js"></script>
		<script src="https://cdn.datatables.net/fixedcolumns/3.3.1/js/dataTables.fixedColumns.min.js"></script>
		<script src="https://cdn.datatables.net/fixedheader/3.1.7/js/dataTables.fixedHeader.min.js"></script>
		<script src="https://cdn.datatables.net/keytable/2.5.3/js/dataTables.keyTable.min.js"></script>
		<script src="https://cdn.datatables.net/responsive/2.2.6/js/dataTables.responsive.min.js"></script>
		<script src="https://cdn.datatables.net/responsive/2.2.6/js/responsive.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/rowgroup/1.1.2/js/dataTables.rowGroup.min.js"></script>
		<script src="https://cdn.datatables.net/rowreorder/1.2.7/js/dataTables.rowReorder.min.js"></script>
		<script src="https://cdn.datatables.net/scroller/2.0.3/js/dataTables.scroller.min.js"></script>
		<script src="https://cdn.datatables.net/searchbuilder/1.0.0/js/dataTables.searchBuilder.min.js"></script>
		<script src="https://cdn.datatables.net/searchbuilder/1.0.0/js/searchBuilder.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/searchpanes/1.2.0/js/dataTables.searchPanes.min.js"></script>
		<script src="https://cdn.datatables.net/searchpanes/1.2.0/js/searchPanes.bootstrap4.min.js"></script>
		<script src="https://cdn.datatables.net/select/1.3.1/js/dataTables.select.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.19/api/fnReloadAjax.js"></script>				
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.22/sorting/date-eu.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>	
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/jQuery.print@1.5.1/jQuery.print.min.js"></script> 
		<script src="https://kit.fontawesome.com/053757fa2e.js" crossorigin="anonymous"></script>			
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/jquery.validate.min.js" crossorigin="anonymous"></script>
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-confirmation/1.0.7/bootstrap-confirmation.min.js" crossorigin="anonymous"></script>
	  	<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.15/js/bootstrap-multiselect.min.js" crossorigin="anonymous"></script>
		<script src="https://cdn.datatables.net/plug-ins/1.10.21/api/order.neutral().js"></script>		
		
		
<!-- JAVASCRIPT FILES LOCAL- ANY MODIFICATIONS DO HERE-->		
		<script src="includes/js/multiselect.js"></script>		
		<script src="includes/js/tenders.js?ver=${todayVer}"></script>			 	
        <script src="includes/ckeditor/ckeditor.js"></script>

								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
								<fmt:formatDate value="${now}" pattern="kk" var="todayHour" />
								<fmt:formatDate value="${now}" pattern="mm" var="todayMinute" />


		<decorator:head />

	</head>

	<body>			
	
    <jsp:include page="/StaffRoom/includes/topmenu.jsp" />		
	<!-- Get the loading data animation ready, in front and hidden -->
	<div id="loadingSpinner" style="display:none;z-index:99999;"><div id="spinner"><img src="/MemberServices/Tenders/includes/img/tenderanimated.gif" width="300" border=0><br/>Loading data, please wait...</div></div>
	

	<!-- PAGE BODY -->	
			<div class="mainContainer">
				<div class="container-fluid">	
				 <img src="includes/img/header.png" style="width:100%;"/>
				 
				 				<div class="msgerr alert alert-danger no-print details_error_message" style="display:none;"></div>         
         				    	<div class="msgok alert alert-success no-print details_success_message" style="display:none;"></div>
         				    	<div class="msginfo alert alert-info details_info_message no-print" style="display:none;"></div>   
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
				 		
				 		<br/><br/>
				 		
				 		<div class="alert alert-warning" style="text-align:center;"><b>NOTICE:</b> Tenders are now posted via the MERX system. Please click on the links below for more information.<br/><br/>
				 		
				 		<a class="btn btn-primary btn-sm" href="http://www.merx.com/newfoundlandlabradorenglishschooldistrict" target="_blank">NLESD TENDERS</a> 
				 		<a class="btn btn-primary btn-sm" href="/business/" target="_blank">BUSINESS WITH NLESD</a> 
				 		</div>
				 		
				 		 Welcome to the Tender posting/review system. Current list of ${todayYear} tenders and options are listed below. Archived tenders for ${todayYear -1} and previous can be found under the Archived options.
				 			
				 			<div class="menuFooter">
											  					<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
																 		<a title="Add a Tender" class="btn btn-xs btn-info" style="color:white;margin-top:5px;" role="button" href="addNewTender.html" onclick="loadingData();"><i class="fas fa-plus"></i> ADD</a> &nbsp; 
																</esd:SecurityAccessRequired>
																<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">  
												                      	<a title="Current Tenders" class="btn btn-xs btn-primary" style="color:white;margin-top:5px;" role="button" href="viewTenders.html" onclick="loadingData();"><i class="fas fa-file-alt"></i> CURRENT</a> &nbsp;
												                      	<a title="Closed Tenders" class="btn btn-xs btn-danger" style="color:white;margin-top:5px;" role="button" href="viewClosedTenders.html" onclick="loadingData();"><i class="fas fa-times"></i> CLOSED</a> &nbsp;
												                      	<a title="Awarded Tenders" class="btn btn-xs btn-success" style="color:white;margin-top:5px;" role="button" href="viewAwardedTenders.html" onclick="loadingData();"><i class="fas fa-star"></i> AWARDED</a> &nbsp;
												                      	<a title="Not Awarded Tenders" class="btn btn-xs btn-danger" style="color:white;margin-top:5px;" role="button" href="viewNotAwardedTenders.html" onclick="loadingData();"><i class="fas fa-star"></i> NOT AWARDED</a> &nbsp;
												                      	<a title="Cancelled Tenders" class="btn btn-xs btn-danger" style="color:white;margin-top:5px;" role="button" href="viewCancelledTenders.html" onclick="loadingData();"><i class="fas fa-times"></i> CANCELLED</a> &nbsp;												                      	
												                   		<a title="Exceptions to Open Calls" class="btn btn-xs btn-dark" style="color:white;margin-top:5px;" role="button" href="viewExceptionTenders.html" onclick="loadingData();"><i class="fas fa-times"></i> EXCEPTIONS</a> &nbsp;
												                   		<a title="Archived Closed Tenders" class="btn btn-xs btn-secondary" style="color:white;margin-top:5px;" role="button" href="viewArchivedClosedTenders.html" onclick="loadingData();"><i class="fas fa-times"></i> ARC. CLOSED</a>  &nbsp;
												                   		<a title="Archived Awarded tenders" class="btn btn-xs btn-secondary" style="color:white;margin-top:5px;" role="button" href="viewArchivedAwardedTenders.html" onclick="loadingData();"><i class="fas fa-star"></i>ARC. AWARDED</a> &nbsp;
												                      	<a title="Archived Not Awarded tenders" class="btn btn-xs btn-secondary" style="color:white;margin-top:5px;" role="button" href="viewArchivedNotAwardedTenders.html" onclick="loadingData();"><i class="fas fa-star"></i> ARC. NOT AWARDED</a> &nbsp;
												                      	<a title="Archived Cancelled Tenders" class="btn btn-xs btn-secondary" style="color:white;margin-top:5px;" role="button" href="viewArchivedCancelledTenders.html" onclick="loadingData();"><i class="fas fa-times"></i> ARC. CANCELLED</a> &nbsp;												                       
												                      	<a title="Tenders that need Status Update!" class="btn btn-xs btn-warning" style="color:black;margin-top:5px;" role="button" href="viewUnsetTenders.html" onclick="loadingData();"><i class="fas fa-exclamation"></i> UPDATE</a> &nbsp;
												                      	<a title="Exit to MS" class="btn btn-xs btn-danger" style="color:white;margin-top:5px;" role="button" href="../navigate.jsp"><i class="fas fa-sign-out-alt"></i> EXIT</a>
												                 </esd:SecurityAccessRequired>
											                    <esd:SecurityAccessRequired roles="ADMINISTRATOR">
											                     	<!-- EMPTY for now -->
											                    </esd:SecurityAccessRequired>
 						</div>
				 		
				 						
						<div id="printJob">						
						<decorator:body />						
						</div>										
				</div>
			</div>				


	<div class="mainFooter">	
		
		 
		    <div class="row" >
		    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">   	 		
		  <div class="copyright">Tender Administration System 2.3 &copy; 2021 NLESD &middot; All Rights Reserved.</div>		
		 	  
		</div> 
	 	</div>
		
	</div>

			




<script>
		function loadingData() {
			$("#loadingSpinner").css("display","block");
		}
</script>
</body>

</html>

<!-- END DECORATOR -->