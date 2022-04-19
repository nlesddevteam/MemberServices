<%@ page language="java" session="true" isThreadSafe="false" contentType="text/html"%>
<%@ page import="com.awsd.security.*,
					java.util.*,
					java.io.*,
					java.text.*,		
					java.util.Date.*,
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
		<title>NLESD - Tender Administration</title>
		<script>
	$('document').ready(function(){
			mTable = $(".tenderTable").dataTable({
				"order" : [[0,"desc"]],		
				"bPaginate": false,
				responsive: true,
								
				 "columnDefs": [
					 {
			                "targets": [4],			               
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

<div class="siteHeaderRed">CANCELLED Tenders for ${todayYear} (<span class="cancelledThisYearCount"></span>)</div>
  
Below is a list of currently CANCELLED tenders sorted by date (newest) and tender number (highest). 
You can sort by clicking on the column header or search using the search tool below right. 


 <esd:SecurityAccessRequired permissions="TENDER-VIEW">
 
  									<%if(request.getAttribute("msg")!=null){%>
									  <div class="alert alert-warning alert-dismissible"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>*** <%=(String)request.getAttribute("msg")%> ***</div>
                  					 <%} else {%>   
                  					 
 
  <br/>
  <table class="tenderTable table table-sm table-bordered responsive" width="100%" style="font-size:12px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:Black;font-size:12px;">							
					<th width="10%" style="border-right:1px solid white;">TENDER #</th>						    												
					<th width="55%" style="border-right:1px solid white;">TITLE</th>	
					<th width="10%" style="border-right:1px solid white;">REGION</th>		
					<th width="10%" style="border-right:1px solid white;">STATUS</th>							
					<esd:SecurityAccessRequired permissions="TENDER-ADMIN,TENDER-EDIT">
						<th width="15%" style="border-right:1px solid white;">OPTIONS</th>	
					</esd:SecurityAccessRequired>
					</tr>
					</thead>
<tbody>		
  <c:choose>
	<c:when test='${fn:length(tenders) gt 0}'>  
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
                                  		
                                  		<tr zone='${ contact.zone.zoneId }'>
                                  					<td class='${statusColorText} ${regionColor}' style="border-bottom:1px solid black;">
                                  					                                  					
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
													
													<td class='${statusColor}' style="vertical-align:top;border-bottom:1px solid black;">													
													<span style="color:white;background-color:Red;width:100%;font-weight:bold;">&nbsp;<i class="fas fa-times"></i>&nbsp;${g.tenderStatus.description}&nbsp;</span>
													</td>													
													
<esd:SecurityAccessRequired permissions="TENDER-ADMIN">	
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
<td>No Awarded Tenders Found.</td>
<td></td>
<td></td>
<td></td>
</tr>
</c:otherwise>
</c:choose>  

 </tbody>
 </table>
 
 
 <%} %>
 </esd:SecurityAccessRequired>
   
  </div></div>							


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

  </body>

</html>
			
			