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
		<title>Web Policy Posting System</title>
					

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
		
		
		aTable = $(".policyTable").dataTable({
			"order" : [[ 1, "asc" ]],			
			  "paging":   false,
			  "searching": true,			 
				responsive: true,
				"pageLength": 25,
				"lengthMenu": [[25, 50, 75, 100, -1], [25, 50, 75, 100, "All"]],
				"lengthChange": true,
				"columnDefs": [
					 {
			             
			            
			                "targets": [6],			               
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
<div class="siteHeaderGreen">View Policies</div>

  Below are all our approved policies for public viewing on our website. 
  If you add special documents (in the Other Files Section when editing a posting) to a Policy, please use keywords in their title like form or presentation, 
                       so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
			
<br/><br/>		
			<div align="center">
					<a class="btn btn-sm btn-primary" href="addNewPolicy.html" onclick="loadingData();">Add Policy</a>&nbsp;					
					<a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();">Back to StaffRoom</a>
			</div>
					
<br/>		
					<table class="policyTable table table-sm responsive" style="width:100%;font-size:11px;">
								<thead class="thead-dark">	
									<tr>
									<th width="30%">TITLE</th>
									<th width="20%">CATEGORY</th>	
									<th width="10%">NUMBER</th>									
									<th width="10%">STATUS</th>
									<th width="10%">ADDED ON</th>
									<th width="10%">ADDED BY</th>
									<th width="10%">OPTIONS</th>
									</tr>
								</thead>	
								<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(policies) gt 0}'>
                                  		<c:forEach items='${policies}' var='g'>
                                  		
                                  			<tr>
                                  			  <td>${g.policyTitle}</td>	
                                  			  <td>${g.policyCategory.description}</td>	
                                  			  <td>
                                  			  <c:choose>
                                  			  <c:when test="${g.policyCategory.description eq 'BOARD'}">GOV-${g.policyNumber}</c:when>
                                  			  <c:when test="${g.policyCategory.description eq 'DEPARTMENT OF EDUCATION'}">EECD-${g.policyNumber}</c:when>
                                  			  <c:when test="${g.policyCategory.description eq 'FINANCE AND ADMINISTRATION'}">FIN-${g.policyNumber}</c:when>
                                  			  <c:when test="${g.policyCategory.description eq 'HUMAN RESOURCES'}">HR-${g.policyNumber}</c:when>
                                  			  <c:when test="${g.policyCategory.description eq 'OPERATIONS'}">OPER-${g.policyNumber}</c:when>
                                  			  <c:when test="${g.policyCategory.description eq 'PROGRAMS'}">PROG-${g.policyNumber}</c:when>
                                  			  <c:otherwise>${g.policyNumber}</c:otherwise>
                                  			  </c:choose>
                                  			  </td> 
                                  			  <c:choose>
                                  			   <c:when test="${g.policyStatus.description eq 'DISABLED'}"><td style="color:White;background-color:Red;text-align:Center;">${g.policyStatus.description}</td></c:when>
                                  			   <c:when test="${g.policyStatus.description eq 'PUBLIC'}"><td style="color:White;background-color:Green;text-align:Center;">${g.policyStatus.description}</td></c:when>
                                  			   <c:when test="${g.policyStatus.description eq 'DRAFT'}"><td style="color:Black;background-color:Yellow;text-align:Center;">${g.policyStatus.description}</td></c:when>
                                  			   <c:otherwise>${g.policyStatus.description}</c:otherwise>
                                  			  </c:choose>                                 			
		                                                       
		                                      <td><fmt:formatDate pattern="yyyy/MM/dd" value="${g.dateAdded}" /></td>
		                                      <td style="text-transform:Capitalize;">${g.addedBy}</td>
		                                      <td>		                                      
		                                      <a class="btn btn-xs btn-warning" href="viewPolicyDetails.html?id=${g.id}" onclick="loadingData();">EDIT</a>	                                      
		                                      <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this Policy?');loadingData();" href='deletePolicy.html?pid=${g.id}'>DEL</a>
		                					  </td>
		                                    </tr>
		                                
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
										<tr>
										<td>No Policies Found.</td>
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