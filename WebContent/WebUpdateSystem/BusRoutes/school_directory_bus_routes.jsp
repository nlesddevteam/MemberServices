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


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr"); 
  Collection<School> schools = null;
  schools = SchoolDB.getSchools();	
%>
<c:set var='schools' value="<%= schools %>" />
<c:set var="zonePerm" value='all' />


<c:set var="avalonSchools" value="0" />
<c:set var="centralSchools" value="0" />
<c:set var="westernSchools" value="0" />
<c:set var="labradorSchools" value="0" />


<c:set var="avalonDocs" value="0" />
<c:set var="centralDocs" value="0" />
<c:set var="westernDocs" value="0" />
<c:set var="labradorDocs" value="0" />




<esd:SecurityCheck permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-BUSROUTES" />
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
	
	<style>
	
.schoollistDocLinkContainer {float:left;width:160px;text-align:center;}
.schoollistHeader {color: #007F01;font-weight: bold;}
.schoollist .row .email a { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:visited { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:hover {	color: #FF0000;	 text-decoration: underline;}
.schoollist .row .email a:active { color: #FF0000; text-decoration: none;}
.schoollist {display:table;  width:100%;font-size:11px;}
.schoollist .table {display:table;}
.schoollist .rowCaption {caption-side: top; display: table-caption;width:100%;text-align:left; font-weight:bold;color:#007F01;margin-bottom:3px;}
.schoollist .row { display:table-row; width:100%;}
.schoollist .column { display: table-cell;border-bottom:1px solid grey;padding:2px;}
.schoollist .row .header {text-align:left; color:white; font-weight:bold; }
.schoollist .row .SchoolName {width:35%;vertical-align: text-top;padding-left:5px;}
.schoollist .row .SchoolRegion { width:15%;border-left:1px solid grey;vertical-align: middle;padding-left:5px;}
.schoollist .row .SchoolLocation { width:20%;border-left:1px solid grey;vertical-align: middle;padding-left:5px;}
.schoollist .row .SchoolBusDoc { width:10%;border-left:1px solid grey;vertical-align: middle;text-align:center;}
.schoollist .row .SchoolOptions { width:10%;border-left:1px solid grey;vertical-align: middle;text-align:center;}
.schoollist .row .SchoolID {width:10%;border-left:1px solid grey;vertical-align: middle;text-align:center;}
div.schoollist > div:nth-of-type(odd) {background: #FDF5E6;}
	
.regionDefault {background-color: #1F4279;}
.region1 {background-color:rgba(191, 0, 0, 0.1);}
.region1a {border-left:10px solid rgba(191, 0, 0, 0.3);}
.region1solid {background-color:rgba(191, 0, 0, 1);}
.region1half {background-color:rgba(191, 0, 0, 0.5);}
.region2 {background-color:rgba(0, 191, 0, 0.1);}
.region2a {border-left:10px solid rgba(0, 191, 0, 0.3);}
.region2solid {background-color:rgba(0, 191, 0, 1);}
.region2half {background-color:rgba(0, 191, 0, 0.5);}
.region3 {background-color:rgba(255, 132, 0, 0.1);}
.region3solid {background-color:rgba(255, 132, 0, 1);}
.region3half {background-color:rgba(255, 132, 0, 0.5);}
.region3a {border-left:10px solid rgba(255, 132, 0, 0.3);}
.region4 {background-color:rgba(127, 130, 255, 0.1);}
.region4a {border-left:10px solid rgba(127, 130, 255, 0.3);}
.region4solid {background-color:rgba(127, 130, 255,1);}
.region4half {background-color:rgba(127, 130, 255, 0.5);}	
	</style>
	
	
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="/MemberServices/WebUpdateSystem/BusRoutes/img/header.png" alt="" width="100%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">School Profile Bus Route Posting</div>
                      <div class="pageBody">
                      
                      <p>
					
					  Below is a complete list of all district schools sorted alphabetically and catagorized by region (depending on your permissions). Not all regions are available to all users.<p>
                 <p>
                 You currently have permissions to edit the following schools in these regions ONLY:
                 
                 <ul>
                 <span class="regionsAllowed"></span>
                 </ul>
					
					  <div align="center">
					  <a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services">Back to Member Services</a>
					  </div>
                 <p>
                      
									<%if(request.getAttribute("msg")!=null){%>								
															
									
									  <div id="fadeMessage" style="background-color: #e6ffe6;color:Green;margin-top:10px;margin-bottom:10px;padding:5px;text-align:center;">${ msg } </div>   									
									
                             		 <%}%>   
									
									<p>
							 
  					
	     								
  <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES">  
  	
 
	 <div class="schoollist">	
  	
  	
   <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-AVALON,WEBMAINTENANCE-BUSROUTES-PROVINCIAL">   
   
      
   
   <c:set var="region1Allowed" value="<li><a href=#avalon>Avalon Regional Schools</a></li>"/>
   
     <div class="row">
									   		<div class="column header SchoolName region1solid"><span style="font-size:16px;"><a name='avalon'></a>AVALON SCHOOLS (<span class="avalonCount"></span>)</span><br/>
									   		# Docs: <span class="avalonDocCount"></span> &nbsp; % Schools with routes: <span class="avalonPercent"></span>
									   		</div>
									   		 <div class="column header SchoolID region1solid">DEPT ID</div>	
									        <div class="column header SchoolRegion region1solid">REGION</div>									        
											<div class="column header SchoolLocation region1solid">TOWN/CITY</div>    
											<div class="column header SchoolBusDoc region1solid">ROUTE DOC </div>
											<div class="column header SchoolOptions region1solid">OPTIONS</div>   
	</div>
	
								<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${!fn:containsIgnoreCase(school.schoolName, 'former') and (fn:containsIgnoreCase(school.zone.zoneName, 'avalon') or fn:containsIgnoreCase(school.zone.zoneName, 'eastern'))}">
               							<div class='row contact-row'> 
	     									<div class="column SchoolName"> 											
	     									${ school.schoolName }
	     									</div>	
	     									<div class="column SchoolID">${ school.schoolDeptID }</div>  
									        <div class="column SchoolRegion">
									        <span style="text-transform:capitalize;"> ${ school.region.name }</span>
									        </div>									        
											<div class="column SchoolLocation">${ school.townCity }</div>    
											<div class="column SchoolBusDoc">
											<c:choose>
												<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }' style="color:white;" class="btn btn-xs btn-warning" target="_blank">View</a>	
												<c:set var="avalonDocs" value="${avalonDocs + 1}" />
												</c:when>
												<c:otherwise>
													<span style="color:red;font-weight:bold;">N/A</span>
												</c:otherwise>
						  					</c:choose>											
											</div>
											<div class="column SchoolOptions">
												<a href="/MemberServices/WebUpdateSystem/BusRoutes/school_directory_bus_routes_edit.jsp?id=${school.schoolID}" style="color:white;" class="btn btn-xs btn-success">Add/Edit</a>
											</div>   
										</div>
										<c:set var="avalonSchools" value="${avalonSchools + 1}" />
									</c:when>
									</c:choose>
									
								</c:forEach> 
   
  
      

   
   
   </esd:SecurityAccessRequired>
   
     <br/>&nbsp;<br/>   
   <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-CENTRAL,WEBMAINTENANCE-BUSROUTES-PROVINCIAL"> 
   
   
  
  
   
   
   <c:set var="region2Allowed" value="<li><a href=#central>Central Regional Schools</a></li>"/>
   
  		<div class="row">
									   		<div class="column header SchoolName region2solid"><span style="font-size:16px;"><a name='central'></a> CENTRAL SCHOOLS (<span class="centralCount"></span>)</span><br/>
									   		# Docs: <span class="centralDocCount"></span> &nbsp; % Schools with routes: <span class="centralPercent"></span>
									   		</div>
									   		 <div class="column header SchoolID region2solid">DEPT ID</div>	
									        <div class="column header SchoolRegion region2solid">REGION</div>									        
											<div class="column header SchoolLocation region2solid">TOWN/CITY</div>    
											<div class="column header SchoolBusDoc region2solid">ROUTE DOC </div>
											<div class="column header SchoolOptions region2solid">OPTIONS</div> 
	</div>
								<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${!fn:containsIgnoreCase(school.schoolName, 'former') and fn:containsIgnoreCase(school.zone.zoneName, 'central')}">
               							<div class='row contact-row'> 
	     									<div class="column SchoolName"> 											
	     									${ school.schoolName }
	     									</div>	
	     									<div class="column SchoolID">${ school.schoolDeptID }</div>
									        <div class="column SchoolRegion">
									        <span style="text-transform:capitalize;"> ${ school.region.name }</span>
									        </div>									        
											<div class="column SchoolLocation">${ school.townCity }</div>    
											<div class="column SchoolBusDoc">
											<c:choose>
												<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }' style="color:white;" class="btn btn-xs btn-warning" target="_blank">View</a>	
												<c:set var="centralDocs" value="${centralDocs + 1}" />
												</c:when>
												<c:otherwise>
													<span style="color:red;font-weight:bold;">N/A</span>
												</c:otherwise>
						  					</c:choose>											
											</div>
											<div class="column SchoolOptions">
												<a href="/MemberServices/WebUpdateSystem/BusRoutes/school_directory_bus_routes_edit.jsp?id=${school.schoolID}" style="color:white;" class="btn btn-xs btn-success">Add/Edit</a>
											</div>   
										</div>
										<c:set var="centralSchools" value="${centralSchools + 1}" />
									</c:when>
									</c:choose>
									
								</c:forEach> 
   
     
   
   </esd:SecurityAccessRequired> 
   
   <br/>&nbsp;<br/>
    
   <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-WESTERN,WEBMAINTENANCE-BUSROUTES-PROVINCIAL">
   
   
 
  
      
   <c:set var="region3Allowed" value="<li><a href=#western>Western Regional Schools</a></li>"/>
     
 		<div class="row">
									   		<div class="column header SchoolName region3solid"><span style="font-size:16px;"><a name='western'></a>WESTERN SCHOOLS (<span class="westernCount"></span>)</span><br/>
									   		# Docs: <span class="westernDocCount"></span> &nbsp; % Schools with routes: <span class="westernPercent"></span>
									   		</div>
									   		 <div class="column header SchoolID region3solid">DEPT ID</div>	
									        <div class="column header SchoolRegion region3solid">REGION</div>									        
											<div class="column header SchoolLocation region3solid">TOWN/CITY</div>    
											<div class="column header SchoolBusDoc region3solid">ROUTE DOC </div>
											<div class="column header SchoolOptions region3solid">OPTIONS</div> 
	</div>
								<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${!fn:containsIgnoreCase(school.schoolName, 'former') and fn:containsIgnoreCase(school.zone.zoneName, 'western')}">
               							<div class='row contact-row'> 
	     									<div class="column SchoolName"> 											
	     									${ school.schoolName }
	     									</div>	
	     									<div class="column SchoolID">${ school.schoolDeptID }</div> 
									        <div class="column SchoolRegion">
									        <span style="text-transform:capitalize;"> ${ school.region.name }</span>
									        </div>									        
											<div class="column SchoolLocation">${ school.townCity }</div>    
											<div class="column SchoolBusDoc">
											<c:choose>
												<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }' style="color:white;" class="btn btn-xs btn-warning" target="_blank">View</a>	
												<c:set var="westernDocs" value="${westernDocs + 1}" />
												</c:when>
												<c:otherwise>
													<span style="color:red;font-weight:bold;">N/A</span>
												</c:otherwise>
						  					</c:choose>											
											</div>
											<div class="column SchoolOptions">
												<a href="/MemberServices/WebUpdateSystem/BusRoutes/school_directory_bus_routes_edit.jsp?id=${school.schoolID}" style="color:white;" class="btn btn-xs btn-success">Add/Edit</a>
											</div>   
										</div>
										<c:set var="westernSchools" value="${westernSchools + 1}" />
									</c:when>
									</c:choose>
								</c:forEach> 
								
					
								
   </esd:SecurityAccessRequired>  
   
  
  
   <br/>&nbsp;<br/>
  
  
   
   <esd:SecurityAccessRequired permissions="WEBMAINTENANCE-BUSROUTES-LABRADOR,WEBMAINTENANCE-BUSROUTES-PROVINCIAL"> 
   
  
  
  
   <c:set var="region4Allowed" value="<li><a href=#labrador>Labrador Regional Schools</a></li>"/>
    
									   		
									       
	
    
    
      <div class="row">
									   		<div class="column header SchoolName region4solid"><span style="font-size:16px;"><a name='labrador'></a>LABRADOR SCHOOLS (<span class="labradorCount"></span>)</span><br/>
									   		# Docs: <span class="labradorDocCount"></span> &nbsp; % Schools with routes: <span class="labradorPercent"></span>
									   		</div>
									   		 <div class="column header SchoolID region4solid">DEPT ID</div>	
									        <div class="column header SchoolRegion region4solid">REGION</div>									        
											<div class="column header SchoolLocation region4solid">TOWN/CITY</div>    
											<div class="column header SchoolBusDoc region4solid">ROUTE DOC </div>
											<div class="column header SchoolOptions region4solid">OPTIONS</div> 
	</div>
								<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${!fn:containsIgnoreCase(school.schoolName, 'former') and fn:containsIgnoreCase(school.zone.zoneName, 'labrador')}">
               							<div class='row contact-row'> 
	     									<div class="column SchoolName"> 											
	     									${ school.schoolName }
	     									</div>	
	     									<div class="column SchoolID"> 											
	     									${ school.schoolDeptID }
	     									</div>
									        <div class="column SchoolRegion">
									        <span style="text-transform:capitalize;"> ${ school.region.name }</span>
									        </div>									        
											<div class="column SchoolLocation">${ school.townCity }</div>    
											<div class="column SchoolBusDoc">
											<c:choose>
												<c:when test="${ not empty school.details.busRoutesFilename }">
													<a href='http://www.nlesd.ca/schools/doc/${ school.details.busRoutesFilename }' style="color:white;" class="btn btn-xs btn-warning" target="_blank">View</a>	
												<c:set var="labradorDocs" value="${labradorDocs + 1}" />
												</c:when>
												<c:otherwise>
													<span style="color:red;font-weight:bold;">N/A</span>
												</c:otherwise>
						  					</c:choose>											
											</div>
											<div class="column SchoolOptions">
												<a href="/MemberServices/WebUpdateSystem/BusRoutes/school_directory_bus_routes_edit.jsp?id=${school.schoolID}" style="color:white;" class="btn btn-xs btn-success">Add/Edit</a>
											</div>   
										</div>
										<c:set var="labradorSchools" value="${labradorSchools + 1}" />
									</c:when>
									</c:choose>
								</c:forEach>
								
							
								 
   </esd:SecurityAccessRequired> 
 
  
     
     
      			
     							
     							
 </esd:SecurityAccessRequired>    								
    
     
     
     							
  			
					

								
</div></div>
    
    	
	
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2018 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>



<script>
$(document).ready(function(){
	
//Write the variables to divs	
	$('.regionsAllowed').html('${region1Allowed} ${region2Allowed} ${region3Allowed} ${region4Allowed}');
	$('#fadeMessage').delay('3000').fadeOut();

});
</script>


 <script>
  			$(".avalonCount").html("${avalonSchools}");   			
  			$(".centralCount").html("${centralSchools}"); 
  			$(".westernCount").html("${westernSchools}"); 
  			$(".labradorCount").html("${labradorSchools}"); 
  			$(".avalonDocCount").html("${avalonDocs}");   			
  			$(".centralDocCount").html("${centralDocs}"); 
  			$(".westernDocCount").html("${westernDocs}"); 
  			$(".labradorDocCount").html("${labradorDocs}"); 
    		
  			var avalonCalc = ${(avalonDocs/avalonSchools)*100};
  			var avalonPercent = avalonCalc.toFixed(2);
  			$(".avalonPercent").html(avalonPercent +"%");
  			
  			var centralCalc = ${(centralDocs/centralSchools)*100};
  			var centralPercent = centralCalc.toFixed(2);
  			$(".centralPercent").html(centralPercent +"%");
  			
  			var westernCalc = ${(westernDocs/westernSchools)*100};
  			var westernPercent = westernCalc.toFixed(2);
  			$(".westernPercent").html(westernPercent +"%");
  			
  			var labradorCalc = ${(labradorDocs/labradorSchools)*100};
  			var labradorPercent = labradorCalc.toFixed(2);
  			$(".labradorPercent").html(labradorPercent +"%");
  			
  			
    </script>

</html>	
			

			