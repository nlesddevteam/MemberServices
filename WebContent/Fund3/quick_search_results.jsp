<%@ page language ="java" 
         session = "true"
         import = "java.util.*,com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*"
         isThreadSafe="false"%>
         <%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<script src="includes/js/jquery.min.js"></script>
		<script src="includes/js/bootstrap.min.js"></script>
		<link href="includes/css/bootstrap.min.css" rel="stylesheet" type="text/css">
		<script language="Javascript" src="includes/js/jquery.dataTables.min.js"></script>
		<script language="Javascript" src="includes/js/dataTables.bootstrap.min.js"></script>
		<script language="Javascript" src="includes/js/buttons.print.min.js"></script>
		<script language="Javascript" src="includes/js/buttons.html5.min.js"></script>
		<script language="Javascript" src="includes/js/dataTables.buttons.min.js"></script>
		<script language="Javascript" src="includes/js/jszip.min.js"></script>
		<script language="Javascript" src="includes/js/pdfmake.min.js"></script>
		<script language="Javascript" src="includes/js/vfs_fonts.js"></script>
		<link rel="stylesheet" type="text/css" href="includes/css/dataTables.bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="includes/css/buttons.dataTables.min.css">
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		<link rel="stylesheet" href="includes/css/jquery-ui.css" />
		
		<script type="text/javascript">
		$(document).ready(function() {
				$('#reportdata').DataTable({
    				  dom: 'Bfrtip',
    				  buttons: [
    				    'copyHtml5', 'excelHtml5', 'pdfHtml5', 'csvHtml5','print'
    				  ]
    				} );
				});
		</script>
	</head>

	<body style="margin:20px;background-color: White;">
			
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr>

				<td align="center">
					<table width="95%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>

								<table align="center" width="95%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="fund3header" align="center" colspan='2'>Quick Search Results</td></tr>
									<tr>
									<td class="messageText" align='center' colspan='2'>
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
									</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
						<td align="center">
						<div id="printarea">
						<br />
						
						<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap">
								<thead>
									<tr>
										<th>Project Name</th>
										<th>Project Number</th>
										<th>Region</th>
										<th>Employee Responsible For Project</th>
										<th>Expense Account Budget</th>
										<th>Expense Account Balance</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(projects) gt 0}'>
                                  		<c:forEach items='${projects}' var='g'>
                                  			<tr>
                                  				<td>${g.projectName}</td>
                                  				<td>
                                  				<a href='editProject.html?id=${g.projectId}'>${g.projectNumber}</a>
                                  				</td>
		                                      	
		                                      	<td>${g.projectRegionsList}</td>
		                                      	<td>${g.positionResponsibleText}</td>
		                                      	<td>
		                                      	<fmt:setLocale value = "en_US"/>
         										<fmt:formatNumber value = "${g.currentBudget}" type = "currency"/>
		                                      	</td>
		                                      	<td>
		                                      	<fmt:formatNumber value = "${g.currentBalance}" type = "currency"/>
		                                      	</td>
		                                   </tr>
                                  		</c:forEach>
                                  		
                                  		</c:when>
										<c:otherwise>
											<tr><td>No Projects Found</td></tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							</div>
						</td>
						</tr>
					</table>
									
					
					<br>
			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>