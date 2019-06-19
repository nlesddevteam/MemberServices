<%@ page language ="java" 
         session = "true"
         import = "java.util.*,
         					 com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*"
         isThreadSafe="false"%><%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
		<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />				
		<script type="text/javascript" src="includes/js/jquery.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="includes/js/bootstrap-multiselect.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		<link rel="stylesheet" href="includes/css/jquery-ui.css" />
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
		<script src="includes/js/jquery-ui.js"></script>
		<script>
			$(document).ready(function() {
				$("#chkall").click(function(){
				    $('input:checkbox.normalcheck').not(this).prop('checked', this.checked);
				});
		        $('#lstregion').change(function(){
		        	populateschoolschecklist();

		        });
		        $('#lstfunding').change(function(){
		        	populatefundingcontacts();

		        });
	    		
			});
		</script>

	</head>

	<body>
		<form id="pol_cat_frm" action="runCustomReport.html" method="post">
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
	
									<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
										<tr><td class='fund3header' align='center'>Create Report</td></tr>
									</table>
								</td>
							</tr>
							<tr><td colspan='3'><br /></td></tr>
							<tr>
								<td align="center">
								<table cellspacing="5" cellpadding="5" border="1" width="100%">
							<tr>
								<td class="fund3tableheader" align='center' rowspan='3'>Field</td>
								<td class="fund3tableheader" align='center'>Display</td>
								<td class="fund3tableheader" align='center'>Search Criteria</td>
							</tr>
							<tr>
								<td class="fund3tableheader">(What Fields Do You Want To <br />Be Displayed In Report</td>
								<td class="fund3tableheader">(What Projects Do You Want To  
								<br />
								Be Included In The Report)</td>
							</tr>
							<tr>
								<td class="fund3tableheader" colspan='2' align='center'><input id='chkall' name='chkall' type="checkbox">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Check All</td>
							</tr>
							<tr>
								<td class='fund3formtext' align="left">Project Name</td>
								<td align='center'><input id='chkprojectname' name='chkprojectname' type='checkbox' class='normalcheck'></td>
								<td class='fund3formfield'>
									<select id='lstproject' name='lstproject' size='1'>
										<option value="-1">Please Select</option>
										<c:forEach var="item" items="${projects}" >
											<option value="${item.key}">${fn:toUpperCase(item.value)}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td  class='fund3formtextsds' colspan='3' align='left'>From SDS:</td>
							</tr>
							<tr>
								<td  class='fund3formtextsds' align="left">Expended:</td>
								<td align='center'><input id='chkexpended' name='chkexpended' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtextsds' align="left">Encumbered:</td>
								<td align='center'><input id='chkencumbered' name='chkencumbered' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtextsds' align="left">Budget:</td>
								<td align='center'><input id='chkbudget' name='chkbudget' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtextsds' align="left">Balance:</td>
								<td align='center'><input id='chkbalance' name='chkbalance' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtextsds' colspan='3' align='left'>&nbsp;</td>
							</tr>						
							<tr>
								<td  class='fund3formtext' align="left">Region:</td>
								<td align='center'><input id='chkregion' name='chkregion' type='checkbox'  class='normalcheck'></td>
								<td class='fund3formfield'>
									<select id='lstregion' name='lstregion' multiple  class="form-control">
										<c:forEach var="item" items="${regions}" >
	                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
	            						</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">School:</td>
								<td align='center'><input id='chkschool' name='chkschool' type='checkbox'  class='normalcheck'></td>
	
								<td class='fund3formfield'>
									<select id='lstschools' name='lstschools' multiple  class="form-control">
										<c:forEach var="item" items="${schools}" >
	                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
	            						</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext'align="left">Funding Partner:</td>
								<td align='center'><input id='chkfundingp' name='chkfundingp' type='checkbox'  class='normalcheck'></td>
								<td class='fund3formfield'>
									<select id='lstfunding' name='lstfunding' multiple  class="form-control">
										<c:forEach var="item" items="${funditems}" >
	             									<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
	         								</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Sponsor Contact Name:</td>
								<td align='center'><input id='chksponsor' name='chksponsor' type='checkbox'  class='normalcheck'></td>
								<td class='fund3formfield'>
									<input id='txtsponsor' name='txtsponsor' type='text'>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Date Range Funding Received:</td>
								<td align='center'><input id='chkdaterange' name='chkdaterange' type='checkbox'  class='normalcheck'></td>
								<td class='fund3formfield'>
									<input type='text' id='daterangestart'  name='daterangestart'>&emsp;And&emsp;<input type='text' id='daterangeend'  name='daterangeend'>
							
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Project Start Date:</td>
								<td align='center'><input id='chkstartdate' name='chkstartdate' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Project End Date:</td>
								<td align='center'><input id='chkenddate' name='chkenddate' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>						
							<tr>
								<td  class='fund3formtext' align="left">Category:</td>
								<td align='center'><input id='chkcategory' name='chkcategory' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
										<select id="lstcategory" name="lstcategory"  style="max-width:50%;" multiple>
											<c:forEach var="catitem" items="${catitems}" >
	              									<option value="${catitem.id}">${fn:toUpperCase(catitem.ddText)}</option>
	          									</c:forEach>
										</select>	
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Position Responsible For Project:</td>
								<td align='center'><input id='chkposition' name='chkposition' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
											<select id="lstposition" name="lstposition"   style="max-width:50%;" multiple>
												<c:forEach var="positem" items="${positems}" >
	               									<option value="${positem.id}">${fn:toUpperCase(positem.ddText)}</option>
	           									</c:forEach>
											</select>	
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Employee Name:</td>
								<td align='center'><input id='chkemployee' name='chkemployee' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
									<input id='txtemployee' name='txtemployee' type='text'>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Employee Email Address:</td>
								<td align='center'><input id='chkemployeeemail' name='chkemployeeemail' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Employee Telephone Number:</td>
								<td align='center'><input id='chkemployeetelephone' name='chkemployeetelephone' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext'align="left">Description Of Program/Project:</td>
								<td align='center'><input id='chkdescription' name='chkdescription' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
									<input id='txtdescription' name='txtdescription' type='text'>
								</td>
							</tr>																				
							<tr>
							<tr>
								<td  class='fund3formtext' align="left">Special Requirements:</td>
								<td align='center'><input id='chkrequirements' name='chkrequirements' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
									<input id='txtspecial' name='txtspecial' type='text'>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Reporting Criteria:</td>
								<td align='center'><input id='chkcriteria' name='chkcriteria' type='checkbox'  class='normalcheck'></td>
								<td>
								</td>
							</tr>
							<tr>
								<td  class='fund3formtext' align="left">Project Status:</td>
								<td align='center'><input id='chkstatus' name='chkstatus' type='checkbox'  class='normalcheck'></td>
								<td  class='fund3formfield'>
											<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="1" checked>&nbsp;&nbsp;&nbsp;Active&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="0">&nbsp;&nbsp;&nbsp;Inactive							
								</td>
							</tr>						
							
																											
							<tr>
							<tr>
								<td colspan='3' align='center'>
								<br />
								<input type="submit" value="Run Report" class="btnExample">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" value="Save Report" id="savereport" class="btnExample" onclick="opensavecustomreport();"><input type="hidden" id="hidreport" name="hidreport">
								<br />
								<br />
								</td>
							</tr>																																																																																														
						</table>

					</table>
							<div style="display:none;">
								Account Number:
								<input id='chkaccountnumber' name='chkaccountnumber' type='checkbox'  class='normalcheck'>
								<input id='txtaccountnumber' name='txtaccountnumber' type='text'>
								Funding:
								<input id='chkfunding' name='chkfunding' type='checkbox'  class='normalcheck'>
							</div>
								

			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		<div id="myModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
		<!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Save Custom Report</h4>
	      </div>
	      <div class="modal-body">
	      	<table border='0'>
				<tr>
					<td class="fund3tableheader">Report Name</td>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="txtreportname" name="txtreportname">
					</td>
				</tr>			
			</table>
	      </div>
	      <div class="modal-footer">
	        	<input type="button" value="Save" onclick="savecustomreport();"/>
				<input type="button" value="Cancel" onclick="$('#myModal').modal('toggle');"/>
	      </div>
	    </div>
	
	  </div>
	</div>
		
		
		
		
		
		</form>
	<script>
		$('select[multiple]').multiselect({
		});
	</script>
			<script>
			$(document).ready(function() {
		    		 $( "#daterangestart" ).datepicker({
		      		      changeMonth: true,//this option for allowing user to select month
		      		      changeYear: true, //this option for allowing user to select from year range
		      		      dateFormat: "yy-mm-dd"
		      		 });
		    		 $( "#daterangeend" ).datepicker({
		      		      changeMonth: true,//this option for allowing user to select month
		      		      changeYear: true, //this option for allowing user to select from year range
		      		      dateFormat: "yy-mm-dd"
		      		 });

		    });

			</script>
	</body>
</html>
