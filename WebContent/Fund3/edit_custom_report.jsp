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
	    		  $(function() {
	    			    $( "#savecustomreport" ).dialog({
	    			    	dialogClass: "no-close",
	    			    	appendTo: "form",
	    			        autoOpen: false,
	    			        modal:true,
	    			        width:600,
	    			        title: 'SAVE CUSTOM REPORT',
	    			        buttons: {
			            		SAVE: function() {
  			              		//call ajax to update status
  			              		//ajaxUpdateProjectStatus();
  			              		//$("#hidreport").val($("#txtreportname").val());
  			              		$("#pol_cat_frm").attr('action', 'saveCustomReport.html');
  			              		$("#pol_cat_frm").submit();
			            			$( this ).dialog( "close" );
  			            	},
	    			            CLOSE: function() {
	    			              $( this ).dialog( "close" );
	    			            }

	    			        }
	    			    });
	    			  });
	    		  $("#savereport").click(function(){
  			  		//$(this).hide();
	  			  		$( "#savecustomreport" ).dialog('open');
	  			    });
	    			$('select[multiple]').multiselect({
	    				maxHeight: '250px',
	    				buttonWidth: '350px'
	    			});
	    			if($('#lstcategoryvalues').val().length > 0){
	    				var catlist =  $('#lstcategoryvalues').val().split(",");
	    				if(catlist.length > 0){
		    				$('#lstcategory').multiselect('select', catlist);
		    			}
	    			}
	    			if($('#lstpositionvalues').val().length > 0){
	    				var poslist =  $('#lstpositionvalues').val().split(",");
	    				if(poslist.length > 0){
	    					$('#lstposition').multiselect('select', poslist);
		    			}
		    		}
	    			if($('#lstregionvalues').val().length > 0){
	    				var reglist =  $('#lstregionvalues').val().split(",");
	    				if(reglist.length > 0){
	    					$('#lstregion').multiselect('select', reglist);
		    			}
		    		}
	    			if($('#lstschoolvalues').val().length > 0){
	    				var schoollist =  $('#lstschoolvalues').val().split(",");
	    				if(schoollist.length > 0){
	    					$('#lstschools').multiselect('select', schoollist);
		    			}
		    		}
	    			if($('#lstfundingvalues').val().length > 0){
	    				var fundlist =  $('#lstfundingvalues').val().split(",");
	    				if(fundlist.length > 0){
	    					$('#lstfunding').multiselect('select', fundlist);
		    			}
		    		}		    			
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

	</head>

	<body>
		<form id="pol_cat_frm" action="updateCustomReport.html" method="post">
		<input type="hidden" id="op" name="op" value="CONFIRM">	
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
					<table width="90%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td align="center">

								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="fund3header" align='center'>Edit Report</td></tr>
								</table>
							</td>
						</tr>
						<tr><td colspan='3'><br /></td></tr>
						<tr>
							<td align='center' style="color:red;">
								<c:if test="${msg ne null}">
            							<div class="alert alert-success">
  											<strong>${msg}</strong>
										</div>
        						</c:if>
                    			
							</td>
						</tr>
						<tr>
							<td align='center'>
							
								<table align="center" cellspacing="5" cellpadding="5" border="1">
									<tr>
										<td class="fund3tableheader" align='center' rowspan='3'>Field</td>
										<td class="fund3tableheader" align='center'>Display</td>
										<td class="fund3tableheader" align='center'>Search Criteria</td>
									</tr>
									<tr>
										<td class="fund3tableheader">(What Fields Do You Want To 
											<br />
											Be Displayed In Report)</td>
										<td class="fund3tableheader">(What Projects Do You Want To 
										<br />
										Be Included In Report)</td>
									</tr>
									<tr>
										<td class='fund3formtext' colspan='2' align='center'><input id='chkall' name='chkall' type="checkbox">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Check All</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Report Name:</td>
											<td colspan='2'  class='fund3formfield'>
												<input id='txtreportname' name='txtreportname' type='text' value='${report.reportTitle}'>
												<input type='hidden' id='reportid' name='reportid' value="${report.id}">
										</td>
									</tr>						
									<tr>
										<td class='fund3formtext' align="left">Project Name:</td>
										<td align='center'>
												<c:choose>
			  										<c:when test="${reportfields['chkprojectname'].fieldUsed eq 1}">
			  											<input id='chkprojectname' name='chkprojectname' type='checkbox' class='normalcheck' checked>
			  										</c:when>
			  										<c:otherwise>
			  											<input id='chkprojectname' name='chkprojectname' type='checkbox' class='normalcheck'>
			  										</c:otherwise>
												</c:choose>
										<td  class='fund3formfield'>
											<select id='lstproject' name='lstproject' size='1'>
												<option value="-1">Please Select</option>
												<c:forEach var="item" items="${projects}" >
													<c:choose>
			  											<c:when test="${reportfields['chkprojectname'].fieldCriteria eq item.key}">
			  												<option value="${item.key}" selected>${fn:toUpperCase(item.value)}</option>
			  											</c:when>
			  											<c:otherwise>
			  												<option value="${item.key}">${fn:toUpperCase(item.value)}</option>
			  											</c:otherwise>
												</c:choose>
												</c:forEach>
											</select>
											<input type='hidden' id='lstprojectvalues' value="${reportfields['chkprojectname'].fieldCriteria}">
										</td>
									</tr>

									<tr>
										<td class='fund3formtextsds' colspan='3' align='left'>From SDS:</td>
									</tr>
									<tr>
										<td class='fund3formtextsds' align="left">Expended:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkexpended'].fieldUsed eq 1}">
														<input id='chkexpended' name='chkexpended' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkexpended' name='chkexpended' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>							
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtextsds' align="left">Encumbered:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkencumbered'].fieldUsed eq 1}">
														<input id='chkencumbered' name='chkencumbered' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkencumbered' name='chkencumbered' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtextsds' align="left">Budget:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkbudget'].fieldUsed eq 1}">
														<input id='chkbudget' name='chkbudget' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkbudget' name='chkbudget' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtextsds' align="left">Balance:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkbalance'].fieldUsed eq 1}">
														<input id='chkbalance' name='chkbalance' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkbalance' name='chkbalance' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtextsds' colspan='3' align='left'>&nbsp;</td>
									</tr>						
									<tr>
										<td class='fund3formtext' align="left">Region:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkregion'].fieldUsed eq 1}">
														<input id='chkregion' name='chkregion' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkregion' name='chkregion' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>									
										</td>
										<td  class='fund3formfield'>
											<select id='lstregion' name='lstregion' multiple  class="form-control">
												<c:forEach var="item" items="${regions}" >
			                						<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
			            						</c:forEach>
											</select>
											<input type='hidden' id='lstregionvalues' value="${reportfields['chkregion'].fieldCriteria}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">School:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkschool'].fieldUsed eq 1}">
														<input id='chkschool' name='chkschool' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkschool' name='chkschool' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>	
										</td>
			
										<td  class='fund3formfield'>
											<select id='lstschools' name='lstschools' multiple  class="form-control">
												<c:forEach var="entry" items="${schools}">
			  										<option value="${entry.value}">${fn:toUpperCase(entry.key)}</option>
												</c:forEach>
											</select>
											<input type='hidden' id='lstschoolvalues' value="${selectedschools}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Funding Partner:</td>
										<td align='center'>
										<c:choose>
													<c:when test="${reportfields['fundingp'].fieldUsed eq 1}">
														<input id='chkfundingp' name='chkfundingp' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkfundingp' name='chkfundingp' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<select id='lstfunding' name='lstfunding' multiple  class="form-control">
												<c:forEach var="item" items="${funditems}" >
			             									<option value="${item.id}">${fn:toUpperCase(item.ddText)}</option>
			         								</c:forEach>
											</select>
											<input type='hidden' id='lstfundingvalues' value="${reportfields['chkfundingp'].fieldCriteria}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Sponsor Contact Name:</td>
										<td align='center'>
										<c:choose>
													<c:when test="${reportfields['chksponsor'].fieldUsed eq 1}">
														<input id='chksponsor' name='chksponsor' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chksponsor' name='chksponsor' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<input id='txtsponsor' name='txtsponsor' type='text' value="${reportfields['chksponsor'].fieldCriteria}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Date Range Funding Received:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkdaterange'].fieldUsed eq 1}">
														<input id=chkdaterange name='chkdaterange' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkdaterange' name='chkdaterange' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
												<c:choose>
													<c:when test="${fn:length(reportfields['chkdaterange'].fieldCriteria) gt 0}">
														<c:forEach var="item" items="${fn:split(reportfields['chkdaterange'].fieldCriteria,' ')}" varStatus="stat">
			        										    <c:if test="${stat.first}">
			        												<input type='text' id='daterangestart'  name='daterangestart' value="${item}">
			    												</c:if>
			    												<c:if test="${stat.last}">
			        												&nbsp;And&nbsp;<input type='text' id='daterangeend'  name='daterangeend' value="${item}">
			    												</c:if>
														</c:forEach>
													</c:when>
													<c:otherwise>
															<input type='text' id='daterangestart'  name='daterangestart'>
														&nbsp;And&nbsp;<input type='text' id='daterangeend'  name='daterangeend'>
													</c:otherwise>
												</c:choose>
			
									
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Project Start Date:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkstartdate'].fieldUsed eq 1}">
														<input id=chkstartdate name='chkstartdate' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkstartdate' name='chkstartdate' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>							
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Project End Date:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkenddate'].fieldUsed eq 1}">
														<input id=chkenddate name='chkenddate' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkenddate' name='chkenddate' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>						
									<tr>
										<td class='fund3formtext' align="left">Category:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkcategory'].fieldUsed eq 1}">
														<input id=chkcategory name='chkcategory' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkcategory' name='chkcategory' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>							
										</td>
										<td  class='fund3formfield'>
												<select id="lstcategory" name="lstcategory"  style="max-width:50%;" multiple>
													<c:forEach var="catitem" items="${catitems}" >
			              									<option value="${catitem.id}">${fn:toUpperCase(catitem.ddText)}</option>
			          									</c:forEach>
												</select>
												<input type='hidden' id='lstcategoryvalues' value="${reportfields['chkcategory'].fieldCriteria}">	
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Position Responsible For Project:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkposition'].fieldUsed eq 1}">
														<input id=chkposition name='chkposition' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkposition' name='chkposition' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>							
										</td>
										<td>
													<select id="lstposition" name="lstposition"   style="max-width:50%;" multiple>
														<c:forEach var="positem" items="${positems}" >
			               									<option value="${positem.id}">${fn:toUpperCase(positem.ddText)}</option>
			           									</c:forEach>
													</select>
													<input type='hidden' id='lstpositionvalues' value="${reportfields['chkposition'].fieldCriteria}">	
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Employee Name:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkemployee'].fieldUsed eq 1}">
														<input id=chkemployee name='chkemployee' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkemployee' name='chkemployee' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<input id='txtemployee' name='txtemployee' type='text' value="${reportfields['chkemployee'].fieldCriteria}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Employee Email Address:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkemployeeemail'].fieldUsed eq 1}">
														<input id=chkemployeeemail name='chkemployeeemail' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkemployeeemail' name='chkemployeeemail' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>	
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Employee Telephone Number:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkemployeetelephone'].fieldUsed eq 1}">
														<input id=chkemployeetelephone name='chkemployeetelephone' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkemployeetelephone' name='chkemployeetelephone' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Description Of Program/Project:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkdescription'].fieldUsed eq 1}">
														<input id=chkdescription name='chkdescription' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkdescription' name='chkdescription' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<input id='txtdescription' name='txtdescription' type='text' value="${reportfields['chkdescription'].fieldCriteria}">
										</td>
									</tr>																				
									<tr>
									<tr>
										<td class='fund3formtext' align="left">Special Requirements:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkrequirements'].fieldUsed eq 1}">
														<input id=chkrequirements name='chkrequirements' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkrequirements' name='chkrequirements' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<input id='txtspecial' name='txtspecial' type='text' value="${reportfields['chkrequirements'].fieldCriteria}">
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Reporting Criteria:</td>
										<td align='center'>
											<c:choose>
													<c:when test="${reportfields['chkcriteria'].fieldUsed eq 1}">
														<input id=chkcriteria name='chkcriteria' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkcriteria' name='chkcriteria' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td class='fund3formtext' align="left">Project Status:</td>
										<td align='center'>
										<c:choose>
													<c:when test="${reportfields['chkstatus'].fieldUsed eq 1}">
														<input id=chkstatus name='chkstatus' type='checkbox' class='normalcheck' checked>
													</c:when>
													<c:otherwise>
														<input id='chkstatus' name='chkstatus' type='checkbox' class='normalcheck'>
													</c:otherwise>
											</c:choose>								
										</td>
										<td  class='fund3formfield'>
											<c:choose>
												<c:when test="${reportfields['chkstatus'].fieldCriteria eq 1}">
													<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="1" checked>&nbsp;&nbsp;&nbsp;Active&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="0">&nbsp;&nbsp;&nbsp;Inactive	
												</c:when>
												<c:otherwise>
													<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="1">&nbsp;&nbsp;&nbsp;Active&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<INPUT TYPE="radio" NAME="isactive" ID="isactive" VALUE="0" checked>&nbsp;&nbsp;&nbsp;Inactive	
												</c:otherwise>
												</c:choose>		
									
										</td>
									</tr>						
									<tr>
									<tr>
										<td colspan='3' align='center'>
											<br />
											<input type="submit" value="Save Report" id="savereport" class="btnExample">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="button" value="Cancel" id="cancelreport" class="btnExample">
											<input type="hidden" id="hidreport" name="hidreport">
											<br />
											<br />
										</td>
									</tr>																																																																																														
								</table>
							</td>
						</tr>
						</table>
						<div style="display:none;">
								Account Number:
								<input id='chkaccountnumber' name='chkaccountnumber' type='checkbox'  class='normalcheck'>
								<input id='txtaccountnumber' name='txtaccountnumber' type='text'>
								Funding:
								<input id='chkfunding' name='chkfunding' type='checkbox'  class='normalcheck'>
							</div>
					<br>
			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>

		</form>
	</body>
</html>
