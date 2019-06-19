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
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />				
		<script type="text/javascript" src="includes/js/jquery.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap-multiselect.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		<!-- Add mousewheel plugin (this is optional) -->
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script type="text/javascript">
		$(document).ready(function()
		{
			$('#lstproject').change(function(){
				ajaxGetFiles();
			 });

		});
		</script>

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
				<td  align='center'>
				<form id="pol_cat_frm" action="quicksearchresults.html" method="post">
					<table border='0' width='90%'>
						<tr>
							<td colspan='2' align='center' class='fund3header'>Add Project Documents<br /></td>
						</tr>
						<tr><td><br /><br /></td></tr>
						<tr>
							<td class='fund3formtext'>Project Name</td>
							<td>
								<select id='lstproject' name='lstproject' class='fund3dropdown'>
									<option value='0'>Select Project</option>
									<c:forEach var="item" items="${projects}" >
                						<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan='2'><hr width="100%" size='3' color='black'></td>
						</tr>
						<tr>
							<td colspan='2'>
							<br />
							<br />
								<table align="center" id="showlists" border="1" width="100%">
									<tr>
										<th class="fund3tableheader">Document</th>
										<th class="fund3tableheader">Added By</th>
										<th class="fund3tableheader">Date Added</th>
										<th style="padding: 10px;text-align:center;"><a href="#" title="Add New Document" onclick="OpenPopUpAddDoc();">Add New Document</a></th>
									</tr>
									<tr>
										<td colspan='4'><hr width="100%" size='3' color='black'></td>
									</tr>
								</table>
								<br />
								<br />
							</td>
						</tr>		
					</table>
				</form>
				</td>
			</tr>
			<jsp:include page="fund3links.jsp" />	

			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		<div id="modaladd" class="modal fade" role="dialog">
  			<div class="modal-dialog">
  				<div class="modal-content">
      				<div class="modal-header">
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
        				<h4 class="modal-title">Add Project Document</h4>
      				</div>
      				<div class="modal-body">
       						<table width="400px;" cellpadding="2" cellspacing="3" align="center" border="0" style="padding: 5px;">
       							<tr>
       								<td  colspan='2'>
       									<div class="alert alert-danger" id="divadddoc" style="display:none;">
    										<strong><span id="spanadddoc"></span></strong>
  										</div>
       								</td>
								</tr>
								<tr>
									<td class="displayHeaderTitle" valign="middle" width='125px'>
										Project
									</td>
									<td class="displayHeaderTitle" valign="middle">
										<span id="projectname" id="projectname"></span><input type='hidden' id="projectid" name ="projectid">
									</td>					
								</tr>
								<tr><td colspan='2'><br/></td></tr>
								<tr>
									<td class="displayHeaderTitle" valign="middle" width='125px'>
										Document Name
									</td>
									<td>
										<input type="text" name="documentname" id="documentname" style="width: 175px;" class="requiredInputBox">
									</td>
								</tr>
								<tr><td colspan='2'><br /></td></tr>							
								<tr>
									<td>Region</td>
										<td>
											<select id='lstregion' name='lstregion' multiple  class="form-control">
												<c:forEach var="item" items="${regions}" >
				                					<option value="${item.id}">${item.ddText}</option>
				            					</c:forEach>
											</select>
										</td>
								</tr>
								<tr><td colspan='2'><br /></td></tr>
								<tr>
									<td>Document</td>
										<td>
											<input type='file' name='newdocument' id='newdocument' style="width:40%;min-width:175px;"/>
										</td>
								</tr>
								
							</table>
						</div>
						<div class="modal-footer">
        						<input type="button" value="Submit" onclick="ajaxAddFile();"/>
								<input type="button" value="Cancel" onclick="$('#modaladd').modal('toggle');"/>
      					</div>
      				</div>
  		</div>
	</div>
				<script>
			$('select[multiple]').multiselect({
			});
			
		</script>
	</body>
</html>
