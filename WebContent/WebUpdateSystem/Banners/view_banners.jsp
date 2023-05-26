<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>

<html>

	<head>
		<title>Web Banner Posting System</title>					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		aTable = $(".bannerTable").dataTable({
			"order" : [[ 4, "desc" ],[1,"asc"]],			
			  "paging":   false,
			  "searching": true,			 
				responsive: true,
				"pageLength": 25,
				"lengthMenu": [[25, 50, 75, 100, -1], [25, 50, 75, 100, "All"]],
				"lengthChange": true,
				"columnDefs": [
					 {
			             
			            
			                "targets": [0,5,2,3],			               
			                "sortable": false,
			                "visible": true,
			            },
			        ]
		});
		
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
	});

	
	
	
		
		</script>
	
	
	
	
	</head>

  <body>
  <div class="siteHeaderGreen">Web Banner Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
   
  
                      <div align="center">
					<a class="btn btn-sm btn-primary" href="addNewBanner.html" onclick="loadingData();">Add Banner</a>&nbsp;					
					<a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();">Back to StaffRoom</a>
					</div>
					
					
					
					 <table class="bannerTable table table-sm table-hover table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr>
						<th>BANNER</th>						
						<th>ORDER</th>		
						<th>DATE DISPLAY</th>	
						<th>REPEAT?</th>					
						<th>STATUS</th>																
						<th>OPTIONS</th>
					</tr>
					</thead>
					<tbody>
					<c:choose>
                     	<c:when test='${fn:length(banners) gt 0}'>
                    		<c:forEach items='${banners}' var='g'>
								<tr>
								<td><img class="thumbnail" src="/includes/files/banners/img/${g.bannerFile}" style="width:100%;max-width:800px;"></td>
								<c:choose>
								<c:when test="${g.bannerStatus eq '0'}"><td style="text-align:center;color:silver;">N/A</td></c:when>
								<c:when test="${g.bannerStatus eq '1'}"><td style="text-align:center;color:Black;font-size:14px;">${g.bannerRotation}</td></c:when>
								<c:otherwise><td style="text-align:center;color:Silver;">N/A</td></c:otherwise>
								</c:choose>
								<td style="text-align:center;">${g.bStartDate eq null? 'N/A': g.bStartDateFormatted } to ${g.bEndDate eq null? 'N/A': g.bEndDateFormatted}</td>
								<td style="text-align:center;">${g.bRepeat eq null?"<span style='color:Silver;'>N/A</span>":g.bRepeat eq "N"? "<span style='color:Red;'>NO</span>":"<span style='color:Green;'>YES</span>"}</td>								
								<c:choose>
								<c:when test="${g.bannerStatus eq '0'}"><td style="text-align:center;color:white;background-color:Red;">DISABLED</td></c:when>
								<c:when test="${g.bannerStatus eq '1'}"><td style="text-align:center;color:white;background-color:Green;">ENABLED</td></c:when>
								<c:otherwise><td style="text-align:center;color:Silver;">N/A</td></c:otherwise>
								</c:choose>
								
								<td style="text-align:center;">
								<a href="${g.bannerLink}" class="btn btn-sm btn-primary" target="_blank" title="${g.bannerLink}">LINK</a>
								<a class="btn btn-sm btn-warning" onclick="loadingData();" href="viewBannerDetails.html?id=${g.id}">EDIT</a> 
								<a class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to DELETE this BANNER?');loadingData();" href='deleteBanner.html?bid=${g.id}'>DEL</a></td>
								</tr>
					</c:forEach>
					</c:when>
					</c:choose>
					
					</tbody>
									
					
					</table>
					
					
					
					
					
					
					
					
									


</div>


  </body>

</html>	
			