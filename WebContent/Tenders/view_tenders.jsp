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

<html>

	<head>
		<title>NLESD - Tender Administration</title>
		<script>
	$('document').ready(function(){
			mTable = $(".tenderTable").dataTable({
				"order" : [[4,"asc"],[1,"asc"]],		
				"bPaginate": false,
				responsive: true,
								
				 "columnDefs": [
					 {
			                "targets": [0,6],			               
			                "searchable": false,
			                "orderable": false
			            }
			        ]
			});		
			
					
			$("tr").not(':first').hover(
			  function () {
			    $(this).css("background","yellow");
			  }, 
			  function () {
			    $(this).css("background","");
			  }
			);			
		
			$(".loadPage").show();
			$(".loadingTable").css("display","none");
			
			
	    	    			

		});
	</script>

	</head>

								<c:set var="now" value="<%=new java.util.Date()%>" /> 								
								<fmt:formatDate value="${now}" pattern="DDD" var="todayDay" />		
								<fmt:formatDate value="${now}" pattern="yyyy" var="todayYear" />
								<fmt:formatDate value="${now}" pattern="kk" var="todayHour" />
								<fmt:formatDate value="${now}" pattern="mm" var="todayMinute" />

<body>

<div class="row pageBottomSpace">
<div class="col siteBodyTextBlack">

<div class="siteHeaderGreen">Currently Open Tenders for ${todayYear} (and recently closed) (<span class="currentOpenCount"></span>)</div>
  

 <ul>
<li>Closed tenders will only display up to 5 days after they close on the public website and below under Current Open tenders and automatically go to the closed section thereafter. 
<li>If you notice <span style="color:red;">Please Update Status</span> under the CLOSED status of those listings that require you to manually CLOSE the tender or 
set the tender to AWARDED if you know the details of the successful bidder. 
<li>Staff with <b>Tender Admin</b> rights can edit/delete CLOSED/AWARDED/CANCELLED and ARCHIVED tenders as well as add/edit/delete current OPEN/AMMENDED tenders. 
<li>Staff with <b>Tender Edit</b> rights can add/edit/delete currently OPEN/AMENDED tenders only.
<li>Some earlier closed tenders will not display AWARDED status and details at this time. These tenders will still show under CLOSED until they are updated. 
<li>All new tenders posted from May 2018 forward will eventually include all documentation, and successful bidder information.
</ul>

You can sort by clicking on the column header or search using the search tool below right. 
 
 <esd:SecurityAccessRequired permissions="TENDER-VIEW">
 
  	<%if(request.getAttribute("msg")!=null){%>
									  <div class="alert alert-warning alert-dismissible"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>*** <%=(String)request.getAttribute("msg")%> ***</div>
                  
                             		 <%} else {%>   

   <br/>
    <table class="tenderTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">
					<th width="10%" style="border-right:1px solid white;">STATUS</th>			
					<th width="10%" style="border-right:1px solid white;">TENDER #</th>						    												
					<th width="30%" style="border-right:1px solid white;">TITLE</th>	
					<th width="10%" style="border-right:1px solid white;">REGION</th>			
					<th width="10%" style="border-right:1px solid white;">CLOSING</th>			
					<th width="15%" style="border-right:1px solid white;">OPENING @</th>	
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT">
						<th width="15%" style="border-right:1px solid white;">OPTIONS</th>	
					</esd:SecurityAccessRequired>
					</tr>
					</thead>
<tbody>		
  <c:choose>
	<c:when test='${fn:length(tenders) gt 0}'>  
  	<c:forEach items='${tenders}' var='g'>
  											<fmt:formatDate value="${g.dateAdded}" pattern="dd/MM/yyyy" var="postedDate" /> 
  											<fmt:formatDate value="${g.closingDate}" pattern="yyyy/MM/dd" var="closingDate" />  
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
															
                                  		<tr zone='${ contact.zone.zoneId }'>                                  		
                                  		
                                  		            <td class='column tenStatus ${statusColor} ${regionColor}' style="vertical-align:top;border-bottom:1px solid black;">                                  					
                                  					
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
				                                  					<c:when test="${(daysToClose lt 0) or ((daysToClose lt 0) and (todayHour gt 14 or (todayHour eq 14 and todayMinute gt 29)))}">											
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
                                  					
                                  					</td>
                                  		
                                  					<td class='${statusColorText} ${regionColor}' style="border-bottom:1px solid black;">                              					
                                  					                                					
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
                                  					</td>
                                  					
                                  					
                                  					
													<td class='${statusColorText} ${regionColor}' style="text-align:left;border-bottom:1px solid black;">										
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
														
														
													</td>	
													
																									
													<td class='${regionColor}' style="border-bottom:1px solid black;">
													
														<c:choose>
															<c:when test="${ (g.tenderZone.zoneName eq 'eastern') or (g.tenderZone.zoneName eq 'avalon')}">														
																Avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderZone.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise><span style="text-transform:Capitalize;">${g.tenderZone.zoneName}</span></c:otherwise>
														</c:choose>  
													
													</td>
													
													
												
													<td style="border-bottom:1px solid black;">${closingDate}</td>
													<td class='${openRegionColor}' style="border-bottom:1px solid black;">
													  	<c:choose>
															<c:when test="${ (g.tenderOpeningLocation.zoneName eq 'eastern') or (g.tenderOpeningLocation.zoneName eq 'avalon')}">														
																Avalon
															</c:when>
															<c:when test="${fn:containsIgnoreCase(g.tenderOpeningLocation.zoneName, 'NLESD')}">
																Provincial
															</c:when>
															<c:otherwise><span style="text-transform:Capitalize;">${g.tenderOpeningLocation.zoneName}</span></c:otherwise>
														</c:choose>  
														Regional Office
													</td>
<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT">
                                  		            	<td align="center" style="border-bottom:1px solid black;">
                                  		            	<a href="viewTenderDetails.html?id=${g.id}" class="btn btn-sm btn-info" style="color:white;">Edit</a> 		                                      
		                                      			<a class="btn btn-sm btn-danger" style="color:white;" onclick="return confirm('Are you sure you want to DELETE this Tender?');" href='deleteTender.html?dtid=${g.id}'>Del</a>
		                                      		</td>
</esd:SecurityAccessRequired>	
                                  		</tr>

  </c:if>
  
  </c:forEach>  
  </c:when>
<c:otherwise>
<tr>
<td></td>
<td></td>
<td>No Tenders Found.</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
</c:otherwise>
</c:choose>  

 </tbody>
 </table>

</div>


<script>
$('document').ready(function(){
		$(".currentOpenCount").html("${currentlyOpen}");   			
		$(".closedThisYearCount").html("${closedThisYear}"); 
		$(".awardedThisYearCount").html("${awardedThisYear}"); 
		$(".cancelledThisYearCount").html("${cancelledThisYear}"); 
		$(".archivedClosedCount").html("${archivedClosed}"); 
		$(".archivedAwardedCount").html("${archivedAwarded}");   			  			
		$(".archivedCancelledCount").html("${archivedCancelled}"); 			
		$(".needStatusSetCount").html("${needStatusSet}"); 
	
});
</script>

<% } %>
	
	
</esd:SecurityAccessRequired>		
	
   
    
  </body>

</html>
			
			