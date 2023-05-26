<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                java.util.*,
                java.io.*,
                java.text.*,
                com.esdnl.webupdatesystem.newspostings.bean.*,
			    com.esdnl.webupdatesystem.newspostings.dao.*,
			 	com.esdnl.webupdatesystem.newspostings.constants.*,
			 	com.esdnl.util.*"%>
                 
            
                 
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>                
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />
<%
  User usr = (User) session.getAttribute("usr");
%>
<html> 
<head>
<title>News Posting System</title>
<meta name="viewport" content="width=device-width, initial-scale=1"/>

<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		aTable = $(".newsTable").dataTable({
			"order" : [[ 2, "desc" ]],			
			  "paging":   true,
			  "searching": true,			 
				responsive: true,
				"pageLength": 25,
				"lengthMenu": [[25, 50, 75, 100, -1], [25, 50, 75, 100, "All"]],
				"lengthChange": true,
				"columnDefs": [
					 {
			                "targets": [6],			               
			                "searchable": false,
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
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="/MemberServices/StaffRoom/includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting Staff Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 


<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">School News and Announcements</div>
		Listed below are all the school news and announcements sorted by NEWS DATE by default. To sort by any other category, click on the table header. You can also Search for a story.
		 To edit an item use the options at right of each listed story. 
		Once you delete a item it is removed from the database permanently. If you wish to not remove a story, it is best to Archive or Disabled it. 
 		If you add special documents (in the Other Files Section when editing a posting) to a Story or Announcement, please use keywords in their title like form or presentation, 
 		so the display icon will match the appropriate file on the public site. 
 		If no keyword, then a default doc icon will show for any doc attachments.
				 
				
	<p><div align="center">
		<a href="addNewNewsPostings.html" class="btn btn-sm btn-primary" onclick="loadingData();">Add News</a>&nbsp;		
		<a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" onclick="loadingData();">Back to StaffRoom</a>
		</div>
	<p>
		
					
    								 
    <c:set var='newspostings' value='<%=NewsPostingsManager.getNewsPostings()%>' />
    								<table class="newsTable table table-sm responsive" style="width:100%;">
									<thead class="thead-dark">
									<tr>
									<th width="35%">TITLE</th>	
									<th width="10%">CATEGORY</th>										
									<th width="10%">NEWS DATE</th>									
									<th width="10%">ADDED ON</th>
									<th width="15%">ADDED BY</th>
									<th width="10%">STATUS</th>
									<th width="10%">OPTIONS</th>
									</tr>
									</thead>
									<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<td width="35%">${g.newsTitle}</td>	  
		                                    <td width="10%">${g.newsCategory.description}</td>                                 
		                                    <td width="10%"><fmt:formatDate pattern="yyyy/MM/dd" value="${g.newsDate}" /></td>
		                                    <td width="10%"><fmt:formatDate pattern="yyyy/MM/dd" value="${g.dateAdded}" /></td>		                                   
		                                   	<td  width="15%" style="text-transform:Capitalize;">${g.addedBy}</td>
		                                    <c:choose>
                                  			   <c:when test="${g.newsStatus eq 'DISABLED'}"><td width="10%" style="color:White;background-color:Red;text-align:Center;">${g.newsStatus}</td></c:when>
                                  			   <c:when test="${g.newsStatus eq 'ENABLED'}"><td width="10%" style="color:White;background-color:Green;text-align:Center;">${g.newsStatus}</td></c:when>
                                  			   <c:when test="${g.newsStatus eq 'ARCHIVED'}"><td width="10%" style="color:Black;background-color:Yellow;text-align:Center;">${g.newsStatus}</td></c:when>
                                  			   <c:otherwise><td width="10%">${g.newsStatus}</td></c:otherwise>
                                  			</c:choose>   
		                                    
		                                    <td width="10%"><a class="btn btn-xs btn-warning" href="viewNewsPostingsDetails.html?id=${g.id}" onclick="loadingData();">EDIT</a>
		                                    <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>DEL</a>
		                                   	</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
										<tr>
										<td>No News Postings Found.</td>
										<td></td>
										<td></td>
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
</div>
</div>

</body>
</html>