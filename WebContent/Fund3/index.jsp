<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>NLESD FUND 3</title>
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />				
		<script type="text/javascript" src="includes/js/jquery.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap-multiselect.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		
		
		<script type="text/javascript">
    		$(document).ready(function() {
        		$('#lstregion').multiselect();
        		$('#lstcategory').multiselect();
        		$('#lstfiscal').multiselect();
        		$('#lstposition').multiselect();
    		});
		</script>
	</head>

	<body style="margin:20px;" >
			
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr>
				<td  align='center'>
				<form id="pol_cat_frm" action="quicksearchresults.html" method="post">
					<table border='1' width='75%'>
						<tr>
							<td colspan='2' align="center" class='fund3header'>
							Quick Search
							<br />
							(Report Includes Budget And Balance Figures From SDS)
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Project Name</td>
							<td class='fund3formfield'>
								<select id='lstproject' name='lstproject' multiple="multiple" size='1'>
									<c:forEach var="item" items="${projects}" >
                						<option value="${item.key}">${fn:toUpperCase(item.value)}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Project Number (max. 4 digits)</td>
							<td class='fund3formfield'>
								<input type='text' id='txtproject' name='txtproject'>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Region</td>
							<td class='fund3formfield'>
								<select id='lstregion' name='lstregion' multiple="multiple" class="form-control">
									<c:forEach var="item" items="${regions}" >
                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            						</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Category</td>
							<td class='fund3formfield'>
								<select id='lstcategory' name='lstcategory' multiple="multiple"  size='1'>
									<c:forEach var="item" items="${categories}" >
                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            						</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Position Responsible For Project(s)</td>
							<td class='fund3formfield'>
								<select id='lstposition' name='lstposition' multiple="multiple" size='1'>
									<c:forEach var="item" items="${positions}" >
                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            						</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Fiscal Year</td>
							<td class='fund3formfield'>
								<select id='lstfiscal' name='lstfiscal' multiple="multiple"  size='1'>
									<c:forEach var="item" items="${fiscalyears}" >
                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
            						</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class='fund3formtext'>Include Active Projects</td>
							<td class='fund3formfield'>
								<input type='checkbox' id='chkinactive' name='chkinactive'>
							</td>
						</tr>
						<tr><td colspan='2' align='center' class='fund3formbutton'><input type="submit" value="Search Projects" class="btnExample"></td></tr>																						
					</table>
				</form>
				<br />
				<br />
				</td>
			</tr>

			<jsp:include page="fund3links.jsp" />	

			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		<script>
			$('select[multiple]').multiselect({
			});
			
		</script>
	</body>
</html>
