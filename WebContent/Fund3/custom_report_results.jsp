<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<c:set var="now" value="<%=new java.util.Date()%>" />
	</head>

	<body style="margin:20px;">
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
					<table align="center" width="95%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
						<tr><td class="fund3header" align="center" colspan='2'>CUSTOM REPORT RESULTS</td></tr>
						<tr><td class="fund3headerwhite" align="center" colspan='2'>${reportname }</td></tr>
						<tr><td class="fund3headerwhite" align="center" colspan='2'>Report Ran: <fmt:formatDate value="${now}" pattern="dd-MM-yyyy hh:mm:ss a" /></td></tr>
						<tr>
							<td colspan='2' align='center'>
							
							</td>
						</tr>
						<tr>
							<td align="center" colspan='2'>
							<div id="printarea">
							<br />
								<table id="reportdata" class="table table-striped table-bordered dt-responsive nowrap">
				 					<thead>
				   						<tr>
				     						<c:forEach items="${rows[0]}" var="column">
				       							<th align='center'><c:out value="${column.key}" /></th>
				     						</c:forEach>
				   						</tr>
				 					</thead>
				 					<tbody>
				   						<c:forEach items="${rows}" var="columns">
				     						<tr>
				       							<c:forEach items="${columns}" var="column">
				         							<td><c:out value="${column.value}" /></td>
				       							</c:forEach>
				     						</tr>
				   						</c:forEach>
				  					</tbody>
								</table>
								</div>
							</td>
							
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
