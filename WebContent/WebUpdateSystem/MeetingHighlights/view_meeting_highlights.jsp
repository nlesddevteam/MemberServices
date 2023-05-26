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
		<title>Meeting Highlights Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	<script>	
		
	$('document').ready(function(){		
		
		$("tr").not(':first').hover(
				  function () {
				    $(this).css("background","yellow");
				  }, 
				  function () {
				    $(this).css("background","");
				  }
				);			
		
		aTable = $(".highlightsTable").dataTable({
			"order" : [[ 1, "desc" ]],			
			  "paging":   true,
			  "searching": true,			 
				responsive: true,
				"pageLength": 10,
				"lengthMenu": [[10, 25, 50, 75, 100, -1], [10, 25, 50, 75, 100, "All"]],
				"lengthChange": true,
				"columnDefs": [
					 {
			                "targets": [4],			               
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
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Meeting Highlights</div>
   
    Below are the current Meeting Highlights posted on our web site sorted by HIGHLIGHT DATE. 
    If you add special documents (in the Other Files Section when editing a posting) to a Highlight, please use keywords in their title like form or presentation, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
			
			
			<p><div align="center">
					<a href="addNewMeetingHighlights.html" class="btn btn-sm btn-primary" onclick="loadingData();">ADD HIGHLIGHT</a> &nbsp; 
					<a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();">Back to StaffRoom</a>
					</div>
			
                     
								
					<p>					
				<table class="highlightsTable table table-sm responsive" style="width:100%;font-size:11px;">
				<thead class="thead-dark">
				<tr>
				<th>TITLE</th>
				<th>HIGHLIGHT DATE</th>
				<th>DATE ADDED</th>
				<th>ADDED BY</th>
				<th>OPTIONS</th>
				</tr>
				</thead>
				<tbody>

									
									<c:choose>
	                                  	<c:when test='${fn:length(mhs) gt 0}'>
                                  		<c:forEach items='${mhs}' var='g'>                                  		
                                  		<tr class='datalist'>                                  			                           			
                                  			<td>${g.mHTitle}</td>
                                  			<td><fmt:formatDate pattern="yyyy/MM/dd"  value="${g.mHDate}" /></td> 
                                  			<td><fmt:formatDate pattern="yyyy/MM/dd"  value="${g.dateAdded}" /></td> 
		                                    <td style="text-transform:Capitalize;">${g.addedBy}</td> 
		                                    <td>
		                                    <a class="btn btn-xs btn-info" href="/includes/files/highlights/doc/${g.mHDoc}" target="_blank">VIEW</a> &nbsp; 
		                                    <a class="btn btn-xs btn-warning" href="viewMeetingHighlightsDetails.html?id=${g.id}" onclick="loadingData();">EDIT</a> &nbsp;  
		                                      <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE these MEETING HIGHLIGHTS?');" href='deleteMeetingHighlights.html?mhid=${g.id}'>DEL</a>
		                				</tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr>
										<td>No Highlights Found.</td>
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
			

			