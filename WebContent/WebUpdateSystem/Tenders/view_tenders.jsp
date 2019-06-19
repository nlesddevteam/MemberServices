<%@ page language="java" session="true" isThreadSafe="false" contentType="text/html"%>
<%@ page import="com.awsd.security.*,
					java.util.*,
					java.io.*,
					java.text.*,		
					java.Util.Date.*,
					com.esdnl.webupdatesystem.tenders.bean.*,					
					com.esdnl.webupdatesystem.tenders.dao.*,
					com.esdnl.webupdatesystem.tenders.constants.*, 
					com.esdnl.util.*"%>   
<%@ page import='org.apache.commons.lang.StringUtils' %>    
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
 
%>
<c:set var="currentlyOpen" value="0" /> 
<c:set var="closedThisYear" value="0" /> 
<c:set var="awardedThisYear" value="0" /> 
<c:set var="cancelledThisYear" value="0" />
<c:set var="archivedClosed" value="0" /> 
<c:set var="archivedAwarded" value="0" />
<c:set var="archivedCancelled" value="0" />
<c:set var="now" value="0" />
<c:set var="todayHour" value="0" />
<c:set var="todayMinute" value="0" />
<c:set var="todayYear" value="0" />
<c:set var="todayDay" value="0" />
<c:set var="dayClosed" value="0" />
<c:set var="yearClosed" value="0" />
<c:set var="dayAdded" value="0" />
<c:set var="dayCheck" value="0" />
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

%>
<esd:SecurityCheck permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW"/>
<!DOCTYPE html> 
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    		<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
     		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   			<link href="../includes/css/ms.css" rel="stylesheet">
   			<link rel="stylesheet" href="../fancybox/jquery.fancybox.css?v=2.1.5"/>
   			<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
   			<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" /> 
   				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   				<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
				<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>		
    			<script src="../fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
    			<script src="../fancybox/jquery.fancybox.js?v=2.1.5"></script>
		 		<script src="../fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
				<script src="../fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
				<script src="../fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
				<script src="js/changepopup.js"></script>	
			
				<style>	
					.textStatusCancelled {color:Grey;text-decoration: line-through;}
					.textStatusClosed {color:Grey;}
					.textStatusNormal {color:Black;}
				</style>
			
			
		
	
				<script>
			    $(document).ready(			    		  
			    		  /* This is the function that will get executed after the DOM is fully loaded */
			    		  function () {
			    		    $( "#closing_date" ).datepicker({
			    		      changeMonth: true,//this option for allowing user to select month
			    		      changeYear: true, //this option for allowing user to select from year range
			    		      dateFormat: "dd/mm/yy"
			    		    });
			    		  }			
			    		);			
				</script>
	<style>
	.openTenderHeading {color:#1F4279;font-weight:bold;}
	.closedTenderHeading {color:Red;font-weight:bold;}
	.cancelledTenderHeading {color:#4D4C4E;font-weight:bold;}
	.awardedTenderHeading {color:#007F01;font-weight:bold;}
	.archivedTenderHeading {color:Grey;font-weight:bold;}
	</style>			
				

	
	</head>

  <body>
  
								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
								<fmt:formatDate value="${now}" pattern="kk" var="todayHour" />
								<fmt:formatDate value="${now}" pattern="mm" var="todayMinute" />
                                
			                    
			                    
			                    
			                    
			                    
								
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">
										
<esd:SecurityAccessRequired permissions="TENDER-VIEW">
				 
				
                      <div class="pageBody">
					<div align="center" style="margin-bottom:10px;">
					
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
					 	<a class="btn btn-sm btn-success" style="color:white;margin-top:5px;" role="button" href="addNewTender.html">Add a Tender</a> &nbsp; 
					</esd:SecurityAccessRequired>
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT,TENDER-VIEW">  
                      	<a class="btn btn-sm btn-primary" style="color:white;margin-top:5px;" role="button" href="viewTenders.html">Current Tenders</a> &nbsp;
                      	<a class="btn btn-sm btn-danger" style="color:white;margin-top:5px;" role="button" href="../../navigate.jsp">Exit to MS</a>
                    </esd:SecurityAccessRequired>
                    <esd:SecurityAccessRequired roles="ADMINISTRATOR">
                       &nbsp; <a class="btn btn-sm btn-warning" style="color:white;margin-top:5px;" role="button" href="../index.jsp">Back to Main Menu</a>
                     </esd:SecurityAccessRequired>
                      
                      </div>
					
					<br>										
					Closed tenders will only display up to 5 days after they close on the public website and below under Open tenders and automatically go to the closed section thereafter. 
					<br/><br/>
					You will notice <span style="color:blue;">(update)</span> under the CLOSED status of those listings that require you to manually CLOSE the tender or 
					set the tender to AWARDED if you know the details of the successful bidder.
					
					<br/><br/>Staff with <b>Tender Admin</b> rights can edit/delete CLOSED/AWARDED/CANCELLED and ARCHIVED tenders as well as add/edit/delete current OPEN/AMMENDED tenders. 
					Staff with <b>Tender Edit</b> rights can add/edit/delete currently OPEN/AMENDED tenders only.
					 
					<br/><br/>Some earlier closed tenders will not display AWARDED status and details at this time. These tenders will still show under CLOSED untile they are updated. 
					
					<br/><br/>All new tenders posted from May 2018 forward will eventually include all documentation, and successful bidder information.
					</div>
						<p>

									
									<%if(request.getAttribute("msg")!=null){%>
									  <div class="alert alert-warning alert-dismissible"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>*** <%=(String)request.getAttribute("msg")%> ***</div>
                  
                             		 <%} else {%>   
									
<!-- CURRENT TENDER LIST------------------------------------------------------- -->									
<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
<div class="panel-group" id="accordion">


  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
        <span class="openTenderHeading"><span class="glyphicon glyphicon-file"></span>
        Currently Open Tenders for ${todayYear} (and recently closed) (<span class="currentOpenCount"></span>)</span>
        </a>
      </h4>
    </div>
    <div id="collapse1" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  						
		  				  					
			  					<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		<div class="column header tenStatus">STATUS</div>	
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class='column header tenClose'>CLOSING</div>
											<div class="column header tenOpen">OPENING AT</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT">
											<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>	       
									    </div>
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  		
                                  	      
                                  		      
                                  		 <fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />  
                                  		 <fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />                             		
                                  		 <fmt:parseNumber value="${-(now.time - g.closingDate.time) / (1000*60*60*24) }" integerOnly="true" var="daysToClose"/> 
                                  		 <fmt:parseNumber value="${(now.time - g.dateAdded.time) / (1000*60*60*24) }" integerOnly="true" var="dayAdded"/> 
                                  		
                                  		
		
                     				
<c:if test="${((daysToClose gt -5 ) and (yearClosed ge todayYear)) and ((g.tenderStatus.description eq 'OPEN') or (g.tenderStatus.description eq 'AMMENDED')) }">


                                  		<c:set var="currentlyOpen" value="${currentlyOpen + 1}" />
                                  		
                            		<c:choose>
										<c:when test="${ g.tenderStatus.description eq 'OPEN' }">
												<c:set var="statusColorText" value="textStatusNormal"/>
										</c:when>
										<c:when test="${ g.tenderStatus.description eq 'CLOSED' }">
												<c:set var="statusColorText" value="textStatusClosed"/>	
										</c:when>	
										<c:when test="${ g.tenderStatus.description eq 'CANCELLED'}">
												<c:set var="statusColorText" value="textStatusCancelled"/>	
										</c:when>																								
										<c:otherwise>
												<c:set var="statusColorText" value="textStatusNormal"/>	
										</c:otherwise>
									</c:choose>			
                                  	
                                  	
                                  	    <c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
					        				
                            		<c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
				                    <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
				                    <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
				                    <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
				                    <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
				                    <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  							<c:choose>																
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
																		<c:set var="openRegionColor" value="region2"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
																		<c:set var="openRegionColor" value="region3"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
																		<c:set var="openRegionColor" value="region4"/>
																</c:when>
																<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
																		<c:set var="openRegionColor" value="region5"/>
																</c:when>
																<c:otherwise>
																		<c:set var="openRegionColor" value="region1"/>
																</c:otherwise>
															</c:choose>
															
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  		
                                  		
                                  		            <div class='column tenStatus ${statusColor} ${regionColor}'>                                  					
                                  					<div style="display:none;">Days to Close: ${daysToClose}   - A17<br/>
                                  						TodayHour: ${todayHour}</div> 
                                  						<c:if test="${g.tenderStatus.description eq 'OPEN'}">     
                                  						
                                  						                   					
			                                  					<c:choose>
			                                  						    			                                  						   
			                                  								                                  						
			                                  						               
				                                  					<c:when test="${dayAdded lt 3}">		                                  						                                  					
					                                  					<div style="background-color:Green;color:White;font-weight:bold;text-align:center;width:100%;">NEW</div>
					                                  					<div style="background-color:none;color:Black;font-weight:bold;text-align:center;width:100%;">OPEN</div>
				                                  					</c:when> 
				                                  					                 
				                                  					<c:when test="${((daysToClose lt 0) or ((daysToClose eq 0) and (todayHour gt 14 or (todayHour eq 14 and todayMinute gt 29))))}">											
															        	<div style="background-color:#DC143C;color:White;font-weight:bold;text-align:center;width:100%;">CLOSED</div>
				                                  					</c:when>  
				                                  										
				                                  					<c:when test="${(daysToClose le 2) and (daysToClose ge 0)}">											
																        <div class="tenNew" style="background-color:#FF4500;font-weight:bold;text-align:center;width:100%;">CLOSING SOON</div>
			                                  							<div style="background-color:none;color:Black;font-weight:bold;text-align:center;width:100%;">OPEN</div>
				                                  					</c:when>  	                                  					
				                                  							                                  					
				                                  					
				                                  					
				                                  					
				                                  					<c:otherwise>
				                                  					<div style="background-color:none;color:Black;font-weight:bold;text-align:center;width:100%;">OPEN</div>
				                                  					
				                                  					</c:otherwise>		                                  					                                					
			                                  					</c:choose>   
		                                  					</c:if> 
		                                  					
		                                  					<c:if test="${g.tenderStatus.description eq 'AMMENDED'}">                                					
			                                  							 
			                                  							 
			                                  					<c:choose>                                  						                
				                                  					<c:when test="${(daysToClose lt 0) or (daysToClose eq 0 and (todayHour gt 14 or (todayHour eq 14 and todayMinute gt 29)))}">											
															        	<div style="background-color:#DC143C;color:White;font-weight:bold;text-align:center;width:100%;">CLOSED</div>
				                                  					</c:when> 
				                                  					<c:when test="${(daysToClose le 2) and (daysToClose ge 0)}">												
																        <div class="tenNew" style="background-color:#FF4500;font-weight:bold;text-align:center;width:100%;">CLOSING SOON</div>
																        <div style="background-color:#0000FF;color:White;font-weight:bold;text-align:center;width:100%;">AMENDED</div>
			                                  							<div style="background-color:none;color:Black;font-weight:bold;text-align:center;width:100%;">OPEN</div>
				                                  					</c:when>  	                                  					
				                                  					<c:otherwise>
				                                  					<div style="background-color:#0000FF;color:White;font-weight:bold;text-align:center;width:100%;">AMENDED</div>
				                                  					<div style="background-color:none;color:Black;font-weight:bold;text-align:center;width:100%;">OPEN</div>
				                                  					</c:otherwise>		                                  					                                					
			                                  					</c:choose>   
			                                  				</c:if> 
                                  					
                                  					</div>
                                  		
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>                              					
                                  					                                					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       <span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}</span>                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								<span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}</span>
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								<span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}</span>
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								<span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}</span>
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								<span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}</span>
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								<span class="tenderNumberDisplay">${fn:substringAfter(g.tenderNumber, "NLESD-")}</span>
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					<span class="tenderNumberDisplay">${g.tenderNumber}</span>                                  					
                                  					</c:otherwise>
                                  					</c:choose>
                                  					</div>
                                  					
                                  					
                                  					
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													
													<ul>
													
												
													
													<c:choose>  
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" target="_blank" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                                					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
													<span style="font-size:9px;">Added/Edited by: <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>
														
														
													</div>	
													
																									
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													
													
												
													<div class='column tenClose'> ${g.closingDateFormatted}</div>
													<div class='column tenRegion ${openRegionColor}'>
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderOpeningLocation.zoneName}</c:otherwise>
														</c:choose>  
														Regional Office
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT">
                                  		            	<div class='column tenOptions' align="center">
                                  		            	<a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      			<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>	
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
					</div>   
      
      
      
      
      
      
      
      
      
      
      </div>
    </div>
  </div>
  


<!-- CLOSED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
        <span class="closedTenderHeading"><span class="glyphicon glyphicon-envelope"></span> Closed Tenders for ${todayYear} (<span class="closedThisYearCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse2" class="panel-collapse collapse">
      <div class="panel-body">
      Below is a list of currently CLOSED tenders. If you notice a <span style="color:Blue;">(UPDATE)</span> after a CLOSED tender in the status field, means the tender is closed automatically and needs updating to actual closed status and/or an awarded state.			 
		<p>							
					
      <div class="tender-list">
  						
		  							
									
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>									        
									        <div class='column header tenClose'>CLOSED</div>
											 <div class="column header tenOpen">OPENED AT</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  		<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />  
                                  		 <fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />                             		
                                  		 <fmt:parseNumber value="${-(now.time - g.closingDate.time) / (1000*60*60*24) }" integerOnly="true" var="daysToClose"/> 
                                  		 <fmt:parseNumber value="${(now.time - g.dateAdded.time) / (1000*60*60*24) }" integerOnly="true" var="dayAdded"/>	


                            				
<c:if test="${(((daysToClose lt 0 ) and (yearClosed ge todayYear)) or ((g.tenderStatus.description eq 'CLOSED') and (yearClosed eq todayYear))) and (g.tenderStatus.description ne 'CANCELLED') and (g.tenderStatus.description ne 'AWARDED') }">

                    <c:set var="closedThisYear" value="${closedThisYear + 1}" />
                    <c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                        <c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
							
					
                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
	                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                    <c:choose>																
							<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
									<c:set var="openRegionColor" value="region2"/>
							</c:when>
							<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
									<c:set var="openRegionColor" value="region3"/>
							</c:when>
							<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
									<c:set var="openRegionColor" value="region4"/>
							</c:when>
							<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
									<c:set var="openRegionColor" value="region5"/>
							</c:when>
							<c:otherwise>
									<c:set var="openRegionColor" value="region1"/>
							</c:otherwise>
					</c:choose>
                                  		
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>                                  					
                                  					  
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					</c:choose>
                                  					</div>
                                  					
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													<ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" target="_blank" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                                					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
														
														<span style="font-size:9px;">Added/Edited by: <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>
														
													</div>
																													
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													
													<div class='column tenStatus ${statusColor}'>													
													
													<c:choose>
													<c:when test="${g.tenderStatus.description ne 'CLOSED'}">
													
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-envelope"></span>&nbsp;CLOSED&nbsp;</span>
													<span style="color:Blue;">(Update)</span>														
													</c:when>
													<c:otherwise>
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-envelope"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													</c:otherwise>
													</c:choose>
													</div>
													
													<div class='column tenClose'> ${g.closingDateFormatted}</div>
													<div class='column tenRegion ${openRegionColor}'>
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderOpeningLocation.zoneName}</c:otherwise>
														</c:choose>  
														Regional Office
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenOptions' align="center">                                 		            
                                  		            
                                  		            
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>
  
<!-- AWARDED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
        <span class="awardedTenderHeading"><span class="glyphicon glyphicon-ok"></span> Awarded Tenders for ${todayYear} (<span class="awardedThisYearCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse3" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  								  															
									
								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />	
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<!-- Insert counter to check for tenders and if not count, no list -->
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>									        
									        <div class='column header tenClose'>CLOSED</div>
											 <div class="column header tenOpen">OPENED AT</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  				<fmt:formatDate value="${g.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="DDD" var="dayAdded" />		
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />	

		
                            				
<c:if test="${(g.tenderStatus.description eq 'AWARDED') and (yearClosed eq todayYear)}">

                                 				
                            				
<c:set var="awardedThisYear" value="${awardedThisYear + 1}" />
                                  		
                                  			<c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                  		<c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
					      		
															
										                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
											                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
											                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
											                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
											                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
											                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  		<c:choose>																
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
																		<c:set var="openRegionColor" value="region2"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
																		<c:set var="openRegionColor" value="region3"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
																		<c:set var="openRegionColor" value="region4"/>
																</c:when>
																<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
																		<c:set var="openRegionColor" value="region5"/>
																</c:when>
																<c:otherwise>
																		<c:set var="openRegionColor" value="region1"/>
																</c:otherwise>
															</c:choose>
															
                                  		
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					
                                  					</c:choose>
                                  					  
                                  					
                                  					                              					
                                  					</div>
                                  					
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													<b>${g.tenderTitle}</b>
													 <ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                               					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>														
														<c:choose>
														<c:when test="${g.contractValue gt 0 }">
															Awarded to Company(s):<br/><b>${g.awardedTo}</b><br/>on ${g.awardedDateFormatted} for the total amount of <b>$${g.contractValueFormatted}</b>.
															</c:when>
														<c:otherwise>
														Awarding details not currently available.
														</c:otherwise>
														
														</c:choose>
														<br/><br/>
													<span style="font-size:9px;text-align:right;"><b>Added/Edited by:</b> <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>	
													</div>		
																										
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													
													<div class='column tenStatus'>													
													<span style="color:white;background-color:Green;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-ok"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													</div>
													
													<div class='column tenClose'> ${g.closingDateFormatted}</div>
													<div class='column tenRegion ${openRegionColor}'>
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderOpeningLocation.zoneName}</c:otherwise>
														</c:choose>  
														Regional Office
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenOptions' align="center">
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>


<!-- CANCELLED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse4">
        <span class="cancelledTenderHeading"><span class="glyphicon glyphicon-remove"></span> CANCELLED Tenders for ${todayYear} (<span class="cancelledThisYearCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse4" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  						
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>								        
									       
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  				<fmt:formatDate value="${g.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="DDD" var="dayAdded" />		
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />	




		
                            				
<c:if test="${(g.tenderStatus.description eq 'CANCELLED') and (yearClosed eq todayYear)}">





          				

  
                                  				
                            				<c:set var="cancelledThisYear" value="${cancelledThisYear + 1}" />

                                  		
                                  			<c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                  		<c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
										
											
                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
	                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  		<c:choose>																
											<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
													<c:set var="openRegionColor" value="region2"/>
											</c:when>
											<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
													<c:set var="openRegionColor" value="region3"/>
											</c:when>
											<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
													<c:set var="openRegionColor" value="region4"/>
											</c:when>
											<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
													<c:set var="openRegionColor" value="region5"/>
											</c:when>
											<c:otherwise>
													<c:set var="openRegionColor" value="region1"/>
											</c:otherwise>
										</c:choose>
                                  		
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					
                                  					</c:choose>
                                  					  
                                  					</div>
                                  					
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													 <ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                               					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
														<span style="font-size:9px;">Added/Edited by: <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>
													</div>													
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													<div class='column tenStatus ${statusColor}'>													
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-remove"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenOptions' align="center">
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>



<!-- ARCHIVED CLOSED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse5">
        <span class="archivedTenderHeading"><span class="glyphicon glyphicon-briefcase"></span> Archived Closed Tenders ( ${todayYear - 1} and previous ) (<span class="archivedClosedCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse5" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  						
		  													
									
								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />	
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>									        
									        <div class='column header tenClose'>CLOSED</div>
											 <div class="column header tenOpen">OPENED AT</div>
											 
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  				<fmt:formatDate value="${g.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="DDD" var="dayAdded" />		
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />	

		
                            				
<c:if test="${(g.tenderStatus.description eq 'CLOSED') and (yearClosed lt todayYear)}">





          				

  
                                  				
                            				<c:set var="archivedClosed" value="${archivedClosed + 1}" />

                                  		
                                  			<c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                        <c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
					             
										
                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
	                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  		<c:choose>																
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
																		<c:set var="openRegionColor" value="region2"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
																		<c:set var="openRegionColor" value="region3"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
																		<c:set var="openRegionColor" value="region4"/>
																</c:when>
															    <c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
																		<c:set var="openRegionColor" value="region5"/>
																</c:when>
																<c:otherwise>
																		<c:set var="openRegionColor" value="region1"/>
																</c:otherwise>
															</c:choose>
															
															
															
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					
                                  					</c:choose>
                                  					  
                                  					
                                  					                              					
                                  					</div>
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													<ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" target="_blank" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                                					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
														<span style="font-size:9px;">Added/Edited by: <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>
													</div>														
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													<div class='column tenStatus ${statusColor}'>													
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-envelope"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													
													</div>
													<div class='column tenClose'> ${g.closingDateFormatted}</div>
													<div class='column tenRegion ${openRegionColor}'>
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderOpeningLocation.zoneName}</c:otherwise>
														</c:choose>  
														Regional Office
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenOptions' align="center">
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>



<!-- ARCHIVED AWARDED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a  data-toggle="collapse" data-parent="#accordion" href="#collapse6">
        <span class="archivedTenderHeading"><span class="glyphicon glyphicon-briefcase"></span> Archived Awarded Tenders ( ${todayYear - 1} and previous ) (<span class="archivedAwardedCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse6" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  						
		  												
									
								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />	
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>									        
									        <div class='column header tenClose'>CLOSEDG</div>
											 <div class="column header tenOpen">OPENED AT</div>
											 
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  				<fmt:formatDate value="${g.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="DDD" var="dayAdded" />		
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />	

		
                            				
<c:if test="${(g.tenderStatus.description eq 'AWARDED') and (yearClosed lt todayYear)}">





          				
<c:set var="archivedAwarded" value="${archivedAwarded + 1}" />
  
                                  				
                            				

                                  		
                                  			<c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                        <c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
					      		
									
                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
	                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  		<c:choose>																
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
																		<c:set var="openRegionColor" value="region2"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
																		<c:set var="openRegionColor" value="region3"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
																		<c:set var="openRegionColor" value="region4"/>
																</c:when>
																<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
																		<c:set var="openRegionColor" value="region5"/>
																</c:when>
																<c:otherwise>
																		<c:set var="openRegionColor" value="region1"/>
																</c:otherwise>
															</c:choose>
                                  		
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					
                                  					</c:choose>
                                  					  
                                  					
                                  					                              					
                                  					</div>
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													 <ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                               					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
														<c:choose>
														<c:when test="${g.contractValue gt 0 }">
														
														Awarded to Company(s):<br/><b>${g.awardedTo}</b><br/>on ${g.awardedDateFormatted} for the total amount of <b>$${g.contractValueFormatted}</b>.	
															</c:when>
														<c:otherwise>
														Awarding details not currently available.
														</c:otherwise>
														
														</c:choose>
														<br/><br/>
													<span style="font-size:9px;text-align:right;"><b>Added/Edited by:</b> <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>	
													</div>														
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													<div class='column tenStatus ${statusColor}'>													
													<span style="color:white;background-color:Green;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-ok"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													</div>
													<div class='column tenClose'> ${g.closingDateFormatted}</div>
													<div class='column tenRegion ${openRegionColor}'>
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderOpeningLocation.zoneName}</c:otherwise>
														</c:choose>  
														Regional Office
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenOptions' align="center">
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='13'>No Tenders Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>


<!-- ARCHIVED CANCELLED TENDERS----------------------------------------------------------- -->  
  
  
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-parent="#accordion" href="#collapse7">
        <span class="archivedTenderHeading"><span class="glyphicon glyphicon-briefcase"></span> Archived Cancelled Tenders ( ${todayYear - 1} and previous ) (<span class="archivedCancelledCount"></span>)</span>
       </a>
      </h4>
    </div>
    <div id="collapse7" class="panel-collapse collapse">
      <div class="panel-body">
      
      <div class="tender-list">
  						
		  													
									
								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />	
									
									<c:choose>
	                                  	<c:when test='${fn:length(tenders) gt 0}'>
	                                  	
	                                  	<div class="row">
									   		
									        <div class="column header tenNumber">TENDER #</div>
									        <div class="column header tenTitle">TITLE / ADDENDUM(s)</div>									         
									        <div class="column header tenRegion">REGION</div>
									        <div class="column header tenStatus">STATUS</div>							        
									      
											 
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">
											 	<div class="column header tenOptions">OPTIONS</div>
</esd:SecurityAccessRequired>      
									    </div>
	                                  	
	                                  	
	                                  	
	                                  	
                                  		<c:forEach items='${tenders}' var='g'>
                                  		
                                  				
                                  				<fmt:formatDate value="${g.closingDate}" pattern="DDD" var="dayClosed" />
                                  				<fmt:formatDate value="${g.closingDate}" pattern="yyyy" var="yearClosed" />
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="DDD" var="dayAdded" />		
                                  				<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" />	



		
                            				
<c:if test="${(g.tenderStatus.description eq 'CANCELLED') and (yearClosed lt todayYear)}">


<c:set var="archivedCancelled" value="${archivedCancelled + 1}" />

                                  		
                                  			<c:set var="statusColorText" value="textStatusClosed"/>	
																	
                                        <c:if test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon') }"><c:set var="regionColor" value="bgcolor1"/></c:if>	
					   					<c:if test="${ g.tenderZone.zoneName eq 'central' }"><c:set var="regionColor" value="bgcolor2"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'western' }"><c:set var="regionColor" value="bgcolor3"/></c:if>	
										<c:if test="${ g.tenderZone.zoneName eq 'labrador' }"><c:set var="regionColor" value="bgcolor4"/></c:if>	
										<c:if test="${ (g.tenderZone.zoneName eq 'nlesd - provincial') or (g.tenderZone.zoneName eq 'provincial') }"><c:set var="regionColor" value="bgcolor5"/></c:if>	
										
					   
										
                    <c:if test="${ g.tenderStatus.description eq 'OPEN' }"><c:set var="statusColor" value="openColor"/></c:if>	
	                <c:if test="${ g.tenderStatus.description eq 'CLOSED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'CANCELLED' }"><c:set var="statusColor" value="closedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AMMENDED' }"><c:set var="statusColor" value="ammendedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'AWARDED' }"><c:set var="statusColor" value="awardedColor"/></c:if>
	                <c:if test="${ g.tenderStatus.description eq 'ON HOLD' }"><c:set var="statusColor" value="hold"/></c:if>
															
															
                                  		<c:choose>																
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'central' }">
																		<c:set var="openRegionColor" value="region2"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'western' }">
																		<c:set var="openRegionColor" value="region3"/>
																</c:when>
																<c:when test="${ g.tenderOpeningLocation.zoneName eq 'labrador' }">
																		<c:set var="openRegionColor" value="region4"/>
																</c:when>
																<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'nlesd - provincial') or (g.tenderOpeningLocation.zoneName eq 'provincial')  }">
																		<c:set var="openRegionColor" value="region5"/>
																</c:when>
																<c:otherwise>
																		<c:set var="openRegionColor" value="region1"/>
																</c:otherwise>
															</c:choose>
                                  		
                                  		<div class='row contact-row' id="" zone='${ contact.zone.zoneId }'>
                                  					<div class='column tenNumber ${statusColorText} ${regionColor}'>
                                  					
                                  					                                  					
                                  					<c:choose>
	                                  					<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-AR-')}"> 
	                                  					       ${fn:substringAfter(g.tenderNumber, "NLESD-AR-")}                          					
	                                  					</c:when>                                  					
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-ER-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-ER-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-CR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-CR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-WR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-WR-")}
                                  						</c:when>
                                  						<c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-LR-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-LR-")}
                                  						</c:when>
                                  					    <c:when test="${fn:containsIgnoreCase(g.tenderNumber, 'NLESD-')}">
                                  								${fn:substringAfter(g.tenderNumber, "NLESD-")}
                                  						</c:when>
                                  					
                                  					<c:otherwise>                                  					
                                  					${g.tenderNumber}                                  					
                                  					</c:otherwise>
                                  					
                                  					</c:choose>
                                  					  
                                  					
                                  					                              					
                                  					</div>
													<div class='column tenTitle ${statusColorText} ${regionColor}'>
													${g.tenderTitle}
													<ul>
													<c:choose>
		                                  					<c:when test="${not empty g.docUploadName}">
		                                  					<li><a href="/includes/files/tenders/doc/${g.docUploadName}" target="_blank" title="${g.tenderTitle}">Tender Document</a>
		                                  					</c:when>   
		                                  					<c:otherwise><li><span style="color:Red;">No document(s) available.</span></c:otherwise>                                					
	                                  					</c:choose>   
													
													
													 <c:if test='${fn:length(g.otherTendersFiles) gt 0}'>
                   										
															<c:forEach var="p" items="${g.otherTendersFiles}" varStatus="counter">
																<li><a href="/includes/files/tenders/doc/${p.tfDoc}" target="_blank" title="${tfTitle}">${p.tfTitle} (${p.addendumDateFormatted})</a></li>
															</c:forEach>
														</c:if>
														</ul>
													<span style="font-size:9px;">Added/Edited by: <span style="text-transform: capitalize;">${g.addedBy} on ${postedDate}</span></span>	
													</div>											
													<div class='column tenRegion ${regionColor}'>
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise>${g.tenderZone.zoneName}</c:otherwise>
														</c:choose>  
													
													</div>
													<div class='column tenStatus ${statusColor}'>													
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<span class="glyphicon glyphicon-remove"></span>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													
													
													</div>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
                                  		            <div class='column tenStatus' align="center">
                                  		            <a href="viewTenderDetails.html?id=${g.id}" class="btn btn-xs btn-info" style="color:white;">Edit</a> 		                                      
		                                      		<a class="btn btn-xs btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</div>
</esd:SecurityAccessRequired>
                                  		</div>
                                  		
                                  		                                  		
                                  		
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											No Tenders Found.
										</c:otherwise>
									</c:choose>
									
</div>
      
      
      
      </div>
    </div>
  </div>






</div>







 								
					<p>











<script>
$('document').ready(function(){
	$('.collapse').collapse();
	
});

</script>



 <script>
  			$(".currentOpenCount").html("${currentlyOpen}");   			
  			$(".closedThisYearCount").html("${closedThisYear}"); 
  			$(".awardedThisYearCount").html("${awardedThisYear}"); 
  			$(".cancelledThisYearCount").html("${cancelledThisYear}"); 
  			$(".archivedClosedCount").html("${archivedClosed}"); 
  			$(".archivedAwardedCount").html("${archivedAwarded}");   			  			
  			$(".archivedCancelledCount").html("${archivedCancelled}"); 			
    		
  		
    </script>


			<% } %>
	
	
</esd:SecurityAccessRequired>		
	</div>
    
    
		
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

</html>
			
			